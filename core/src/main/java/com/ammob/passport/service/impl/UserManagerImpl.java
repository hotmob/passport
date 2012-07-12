package com.ammob.passport.service.impl;

import com.ammob.passport.Constants;
import com.ammob.passport.dao.UserDao;
import com.ammob.passport.mapper.PersonMapper;
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
import org.springframework.ldap.control.PagedResultsDirContextProcessor;
import org.springframework.ldap.core.CollectingNameClassPairCallbackHandler;
import org.springframework.ldap.core.ContextMapperCallbackHandler;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.ldap.userdetails.LdapUserDetailsManager;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import javax.naming.ldap.Control;
import javax.naming.ldap.PagedResultsResponseControl;

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
    	if(StringUtil.isNumeric(userId)) {
    		return userDao.get(new Long(userId));
    	} else {
    		return getUserByUsername(userId);
    	}
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
            //e.printStackTrace();
            log.warn(e.getMessage());
            throw new UserExistsException("User '" + user.getUsername() + "' already exists!");
        } catch (JpaSystemException e) { // needed for JPA
            //e.printStackTrace();
            log.warn(e.getMessage());
            throw new UserExistsException("User '" + user.getUsername() + "' already exists!");
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
    	User user = null;
		try {
			user = (User) ldapUserDetailsManager.loadUserByUsername(username);
			user.getRoles().add(new Role(Constants.USER_ROLE)); // 2012-07-12 FIXME
		} catch (Exception e) {
			log.warn("LDAP not found username : " + username);
		}
    	if(user == null)
    		user = (User) userDao.loadUserByUsername(username);
    	return user;
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
	public PagedResult getPersons(int pageSize, byte[] cookie) throws NamingException {
    	SearchControls searchControls = new SearchControls();
    	searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
    	PagedResultsDirContextProcessor requestControl = new PagedResultsDirContextProcessor(pageSize, new PagedResultsCookie(cookie));
    	CollectingNameClassPairCallbackHandler handler = new ContextMapperCallbackHandler(new PersonMapper()); 
    	ldapTemplate.search(new DistinguishedName("ou=users"), "(objectclass=inetOrgPerson)", searchControls, handler, requestControl);
    	try {
			return new PagedResult(handler.getList(), new PagedResultsCookie(parseControls(new Control[] { requestControl.createRequestControl() })));
		} catch (NamingException e) {
			throw e;
		}
    }
	
	private static byte[] parseControls(Control[] controls) throws NamingException {
		byte[] cookie = null;
		if (controls != null) {
			for (int i = 0; i < controls.length; i++) {
				if (controls[i] instanceof PagedResultsResponseControl) {
					PagedResultsResponseControl prrc = (PagedResultsResponseControl) controls[i];
					cookie = prrc.getCookie();
					System.out.println(">>Next Page \n");
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
        	log.info("Creat new user ! [ " + user.getUsername());
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