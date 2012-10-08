package com.ammob.passport.service.impl;

import com.ammob.passport.Constants;
import com.ammob.passport.dao.UserDao;
import com.ammob.passport.model.Role;
import com.ammob.passport.model.User;
import com.ammob.passport.service.UserManager;
import com.ammob.passport.service.UserService;
import com.ammob.passport.util.StringUtil;
import com.ammob.passport.enumerate.AttributeEnum;
import com.ammob.passport.enumerate.StateEnum;
import com.ammob.passport.exception.UserExistsException;

import org.jasig.services.persondir.IPersonAttributes;
import org.jasig.services.persondir.support.MultivaluedPersonAttributeUtils;
import org.jasig.services.persondir.support.NamedPersonImpl;
import org.jasig.services.persondir.support.ldap.LdapPersonAttributeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.ldap.control.PagedResult;
import org.springframework.ldap.control.PagedResultsCookie;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.LikeFilter;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.ldap.userdetails.LdapUserDetailsManager;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.PagedResultsControl;
import javax.naming.ldap.PagedResultsResponseControl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of UserManager interface.
 * 
 * @author <a href="mailto:hotmob@gmail.com">Mob</a>
 *
 */
@Service("userManager")
@WebService(serviceName = "UserService", endpointInterface = "com.ammob.passport.service.UserService")
public class UserManagerImpl extends GenericManagerImpl<User, Long> implements UserManager, UserService {
	private PasswordEncoder passwordEncoder;
    private UserDao userDao;
	
	@Autowired
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.dao = userDao;
        this.userDao = userDao;
    }
    
	/**
     * {@inheritDoc}
     */
    public User getUser(String userId) {
    	return userDao.get(new Long(userId));
    }

    /**
     * {@inheritDoc}
     */
    public List<User> getUsers() {
        return userDao.getAllDistinct();
    }

    /**
     * {@inheritDoc}
     */
    public User saveUser(User user) throws UserExistsException {
        if (user.getVersion() == null) { // if new user, lowercase userId
            user.setUsername(user.getUsername().toLowerCase());
        }
        // Get and prepare password management-related artifacts
        boolean passwordChanged = false;
        if (passwordEncoder != null) {
            // Check whether we have to encrypt (or re-encrypt) the password
            if (user.getVersion() == null) {
                // New user, always encrypt
                passwordChanged = true;
            } else {
                // Existing user, check password in DB
                String currentPassword = userDao.getUserPassword(user.getUsername());
                if (currentPassword == null) {
                    passwordChanged = true;
                } else {
                    if (!currentPassword.equals(user.getPassword())) {
                        passwordChanged = true;
                    }
                }
            }
            // If password was changed (or new user), encrypt it
            if (passwordChanged) {
                user.setPassword(passwordEncoder.encodePassword(user.getPassword(), null));
            }
        } else {
            log.warn("PasswordEncoder not set, skipping password encryption...");
        }
        try {
            return userDao.saveUser(user);
        } catch (DataIntegrityViolationException e) {
            throw new UserExistsException("User '" + user.getUsername() + "' already exists! e : " + e);
        } catch (JpaSystemException e) { // needed for JPA
            throw new UserExistsException("User '" + user.getUsername() + "' already exists! e : " + e);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    public void removeUser(String userId) {
    	 log.debug("removing user: " + userId);
    	if(StringUtil.isNumeric(userId)) {
    		userDao.remove(new Long(userId));
    	} else {
    		ldapUserDetailsManager.deleteUser(userId);
    	}
    }
	
	/**
     * {@inheritDoc}
     *
     * @param username the login name of the human
     * @return User the populated user object
     * @throws UsernameNotFoundException thrown when username not found
     */
    public User getUserByUsername(String username) throws UsernameNotFoundException {
		try {
			User user = (User) ldapUserDetailsManager.loadUserByUsername(username);
			user.getRoles().add(new Role(Constants.USER_ROLE)); // 2012-07-12 FIXME
			return user;
		} catch (Exception e) {
			log.warn(username + ", e : " + e);
		}
    	return null;
    }

    /**
     * {@inheritDoc}
     */
	public List<User> search(String searchTerm) {
    	return super.search(searchTerm, User.class);
    }
	
    /**
     * {@inheritDoc}
     */
	public PagedResult getPersons(int current, int pageSize)
			throws NamingException {
		SearchControls searchControls = new SearchControls();
		// 返回属性
		byte[] cookie = null; 
		List<User> result = new ArrayList<User>();
		String[] returnAttrs = { "cn", "mail" };
		searchControls.setReturningAttributes(returnAttrs);
		searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		ContextSource contextSource = ldapTemplate.getContextSource();
		DirContext ctx = contextSource.getReadWriteContext();
		LdapContext lCtx = (LdapContext) ctx;
		try {
			lCtx.setRequestControls(new Control[] { new PagedResultsControl(
					pageSize, Control.CRITICAL) });
		} catch (IOException e) {
			e.printStackTrace();
		}
		int tmppage = 0;
		do {  
			AndFilter andF = new AndFilter();
			andF.and(new EqualsFilter("objectclass", "person")).and(new LikeFilter("cn", "*"));
			List<User> resultList = new ArrayList<User>();
			NamingEnumeration<SearchResult> results = lCtx.search("", andF.toString(), searchControls);
			while (results != null && results.hasMoreElements()) {
				SearchResult sr = results.next();
				Attributes attrs = sr.getAttributes();
				User user = new User(attrs.get("cn").get().toString());
				user.setEmail(attrs.get("mail").get().toString());
				user.setDisplayName(attrs.get("mail").get().toString());
				resultList.add(user);
			}
			cookie = parseControls(lCtx.getResponseControls());
			try {
				lCtx.setRequestControls(new Control[] { new PagedResultsControl(pageSize, cookie, Control.CRITICAL) });
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(tmppage >= current) {
				result.addAll(resultList);
				break;
			}
			tmppage++;
		} while ((cookie != null) && (cookie.length != 0));  
		lCtx.close();
		return new PagedResult(result, new PagedResultsCookie(cookie));
	}
	
	private static byte[] parseControls(Control[] controls) throws NamingException {
		byte[] cookie = null;
		if (controls != null) {
			for (int i = 0; i < controls.length; i++) {
				if (controls[i] instanceof PagedResultsResponseControl) {
					PagedResultsResponseControl prrc = (PagedResultsResponseControl) controls[i];
					cookie = prrc.getCookie();
				}
			}
		}
		return (cookie == null) ? new byte[0] : cookie;
	}

    /**
     * generate ldap query map seed.
     * @param username
     * @param email
     * @return
     */
    protected Map<String, Object> generateQuerySeed(String username, String email) {
    	Map<String, Object> seed = new HashMap<String, Object>();
		seed.put("username", username);
		seed.put("mail", email);
		return seed;
	}
    
    /**
     * {@inheritDoc}
     */
	public IPersonAttributes getPersonAttributes(String identifying) {
		final Set<IPersonAttributes> people = personAttributeRepository.getPeopleWithMultivaluedAttributes(
				MultivaluedPersonAttributeUtils.toMultivaluedMap(generateQuerySeed(identifying, identifying)));
        IPersonAttributes person = null;
		try {
			person = DataAccessUtils.singleResult(people);
		} catch (IncorrectResultSizeDataAccessException e) {
			log.warn("User " + identifying + ", query result size = " + people.size() + ", warn !!!!");
			person = people.iterator().next();
		}
        if (person == null) {
            return null;
        }
        //Force set the name of the returned IPersonAttributes if it isn't provided in the return object
        if (person.getName() == null) {
            person = new NamedPersonImpl(identifying, person.getAttributes());
        }
		return person;
    }
	
    /**
     * {@inheritDoc}
     */
    public User savePerson(User user) throws UserExistsException {
        if (user.getVersion() == null) { // New user, always encrypt
        	log.debug("Creat new user ! [ " + user.getUsername());
            if (passwordEncoder != null) 
                user.setPassword("{MD5}" + passwordEncoder.encodePassword(user.getPassword(), null));
            user.setUsername(user.getUsername().toLowerCase());// new user, lowercase userId
            if(this.getPersonAttributes(user.getUsername()) != null)
            	throw new UserExistsException(user.getUsername() + " is exists ! ", StateEnum.USERNAME_EXISTENCE);
            if(this.getPersonAttributes(user.getEmail()) != null)
            	throw new UserExistsException(user.getEmail() + " is exists ! ", StateEnum.EMAIL_EXISTENCE);
            user.getRoles().remove(new Role(Constants.USER_ROLE)); // 2012-07-12 FIXME
            ldapUserDetailsManager.createUser(user);
            user.getRoles().add(new Role(Constants.USER_ROLE)); // 2012-07-12 FIXME
        } else {// Existing user, check password in DB
        	String currentPassword = new String((byte[]) this.getPersonAttributes(user.getUsername()).getAttributeValue(AttributeEnum.USER_PASSWORD.getValue()));
            if (!currentPassword.equals(user.getPassword()) && passwordEncoder != null) {
            	user.setPassword("{MD5}" + passwordEncoder.encodePassword(user.getPassword(), null));
            }
            IPersonAttributes ipa = this.getPersonAttributes(user.getEmail());
            if(ipa != null && !ipa.getAttributeValue(AttributeEnum.USER_USERNAME.getValue()).equals(user.getUsername()))
            	throw new UserExistsException(user.getEmail() + " is exists ! ", StateEnum.EMAIL_EXISTENCE);
            user.getRoles().remove(new Role(Constants.USER_ROLE)); // 2012-07-12 FIXME
            ldapUserDetailsManager.updateUser(user);
            user.getRoles().add(new Role(Constants.USER_ROLE)); // 2012-07-12 FIXME
        }
        return user;
    }

    /**
     * {@inheritDoc}
     */
	public User creatUser(String username, String password, String email) throws UserExistsException {
		User user = new User(username);
		user.setPassword(password);
		user.setEmail(email);
		return savePerson(user);
	}
	
    /**
     * {@inheritDoc}
     */
    public void changePassword(final String oldPassword, final String newPassword) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String currentPassword = new String((byte[]) this.getPersonAttributes(authentication.getName()).getAttributeValue(AttributeEnum.USER_PASSWORD.getValue()));
    	if (currentPassword.equals(oldPassword)) {
    		ldapUserDetailsManager.changePassword(null, "{MD5}" + passwordEncoder.encodePassword(newPassword, null));
    	} else {
	    	log.warn(currentPassword + " : " + oldPassword);
	    	ldapUserDetailsManager.changePassword(oldPassword, "{MD5}" + passwordEncoder.encodePassword(newPassword, null));
    	}
    }
    
	@Autowired
	private LdapPersonAttributeDao personAttributeRepository;
	@Autowired
    private LdapUserDetailsManager ldapUserDetailsManager;
	@Autowired
	private LdapTemplate ldapTemplate;

}