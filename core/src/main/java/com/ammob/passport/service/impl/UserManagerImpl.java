package com.ammob.passport.service.impl;

import com.ammob.passport.dao.UserDao;
import com.ammob.passport.model.User;
import com.ammob.passport.service.UserManager;
import com.ammob.passport.service.UserService;
import com.ammob.passport.enumerate.StateEnum;
import com.ammob.passport.exception.UserExistsException;

import org.jasig.services.persondir.support.ldap.LdapPersonAttributeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.jws.WebService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jasig.services.persondir.IPersonAttributeDao;
import org.jasig.services.persondir.IPersonAttributes;

/**
 * Implementation of UserManager interface.
 * 
 * @author Mob
 *
 */
@SuppressWarnings("restriction")
@Service("userManager")
@WebService(serviceName = "UserService", endpointInterface = "com.ammob.passport.service.UserService")
public class UserManagerImpl extends GenericManagerImpl<User, Long> implements UserManager, UserService, IPersonAttributeDao, AuthenticationUserDetailsService<Authentication> {
	
    private PasswordEncoder passwordEncoder;
    private UserDao userDao;
    private UserDao ldapUserDao;
    private IPersonAttributeDao personAttributeRepository;
    
	@Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    @Qualifier("userDao")
    public void setUserDao(UserDao userDao) {
        this.dao = userDao;
        this.userDao = userDao;
    }

    @Autowired
    @Qualifier("ldapUserDao")
    public void setMemberDao(UserDao userDao) {
        this.ldapUserDao = userDao;
    }
    
    @Autowired
	public void setPersonAttributeRepository(LdapPersonAttributeDao personAttributeRepository) {
		this.personAttributeRepository = personAttributeRepository;
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
    public User saveMember(User user) throws UserExistsException {
        if (user.getVersion() == null) { // if new user, lowercase userId
            user.setUsername(user.getUsername().toLowerCase());
        }
        boolean passwordChanged = false; // Get and prepare password management-related artifacts
        if (passwordEncoder != null) {   // Check whether we have to encrypt (or re-encrypt) the password
            if (user.getVersion() == null) { // New user, always encrypt
            	log.info("Creat new user !");
                passwordChanged = true;
                Map<String, Object> queryParams = new HashMap<String, Object>();
                queryParams.put("cn", user.getUsername());
                queryParams.put("mail", user.getEmail());
                List<User> queryResult = ldapUserDao.findByNamedQuery("queryUsernameOrEmail", queryParams);
                if(queryResult != null && !queryResult.isEmpty()) {
                	log.info("Creat new user is fail, user exists ! : exists num" + queryResult.size());
                	Set<StateEnum> types = new HashSet<StateEnum>();
                	for(User resultUser : queryResult) {
                		if(resultUser.getUsername().equals(user.getUsername()))
                			types.add(StateEnum.USERNAME_EXISTENCE);
                		if(resultUser.getEmail().equals(user.getEmail()))
                			types.add(StateEnum.EMAIL_EXISTENCE);
                	}
                	throw new UserExistsException("User'" + user.getUsername() + " Or " + user.getEmail() + "' already exists! ", types);
                }
            } else {// Existing user, check password in DB
                String currentPassword = ldapUserDao.getUserPassword(user.getUsername());
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
        return ldapUserDao.saveUser(user);
    }
    
    /**
     * {@inheritDoc}
     */
    public User saveUser(User user) throws UserExistsException {

        if (user.getVersion() == null) {
            // if new user, lowercase userId
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
        userDao.remove(new Long(userId));
    }
    
    /**
     * {@inheritDoc}
     *
     * @param identifying the login name or login email of the human
     * @return User the populated user object
     * @throws UsernameNotFoundException thrown when user not found
     * @throws UserExistsException 
     */
    public User getMemberByUsernameOrEmail(String identifying) throws UsernameNotFoundException {
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("cn", identifying);
        queryParams.put("mail", identifying);
		try {
			List<User> queryResult = ldapUserDao.findByNamedQuery("queryUsernameOrEmail", queryParams);
	        if(queryResult == null || queryResult.isEmpty()) {
	        	throw new UsernameNotFoundException("User Identifying : '" + identifying + "' is not found...");
	        } else if(queryResult.size() > 1) {
	        	log.error("User Identifying : '" + identifying + "' size is : " + queryResult.size());
	        }
		    return  (User) ldapUserDao.loadUserByUsername(queryResult.get(0).getUsername());
		} catch (Exception e) {
			throw new UsernameNotFoundException("User Identifying : '" + identifying + "' Is Not Found, ");
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
        return (User) userDao.loadUserByUsername(username);
    }

    /**
     * {@inheritDoc}
     */
    public List<User> search(String searchTerm) {
        return super.search(searchTerm, User.class);
    }

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.AuthenticationUserDetailsService#loadUserDetails(org.springframework.security.core.Authentication)
	 */
	public UserDetails loadUserDetails(Authentication authentication)
			throws UsernameNotFoundException {
		try {
			personAttributeRepository.getPerson(authentication.getName());
//			personAttributeRepository.
			log.info(authentication.getName() + " is login !!!!");
			UserDetails userDetails = ldapUserDao.loadUserByUsername(authentication.getName());
			return userDetails;
		} catch (Exception e) {
			throw new UsernameNotFoundException(
					"find user:[" + authentication.getName() + "] is error! message:" + e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see org.jasig.services.persondir.IPersonAttributeDao#getAvailableQueryAttributes()
	 */
	public Set<String> getAvailableQueryAttributes() {
		return personAttributeRepository.getAvailableQueryAttributes();
	}

	/* (non-Javadoc)
	 * @see org.jasig.services.persondir.IPersonAttributeDao#getMultivaluedUserAttributes(java.util.Map)
	 */
	@SuppressWarnings("deprecation")
	public Map<String, List<Object>> getMultivaluedUserAttributes(
			Map<String, List<Object>> arg0) {
		return personAttributeRepository.getMultivaluedUserAttributes(arg0);
	}

	/* (non-Javadoc)
	 * @see org.jasig.services.persondir.IPersonAttributeDao#getMultivaluedUserAttributes(java.lang.String)
	 */
	@SuppressWarnings("deprecation")
	public Map<String, List<Object>> getMultivaluedUserAttributes(String arg0) {
		return personAttributeRepository.getMultivaluedUserAttributes(arg0);
	}

	/* (non-Javadoc)
	 * @see org.jasig.services.persondir.IPersonAttributeDao#getPeople(java.util.Map)
	 */
	public Set<IPersonAttributes> getPeople(Map<String, Object> arg0) {
		return personAttributeRepository.getPeople(arg0);
	}

	/* (non-Javadoc)
	 * @see org.jasig.services.persondir.IPersonAttributeDao#getPeopleWithMultivaluedAttributes(java.util.Map)
	 */
	public Set<IPersonAttributes> getPeopleWithMultivaluedAttributes(
			Map<String, List<Object>> arg0) {
		return personAttributeRepository.getPeopleWithMultivaluedAttributes(arg0);
	}

	/* (non-Javadoc)
	 * @see org.jasig.services.persondir.IPersonAttributeDao#getPerson(java.lang.String)
	 */
	public IPersonAttributes getPerson(String arg0) {
		return personAttributeRepository.getPerson(arg0);
	}

	/* (non-Javadoc)
	 * @see org.jasig.services.persondir.IPersonAttributeDao#getPossibleUserAttributeNames()
	 */
	public Set<String> getPossibleUserAttributeNames() {
		return personAttributeRepository.getPossibleUserAttributeNames();
	}

	/* (non-Javadoc)
	 * @see org.jasig.services.persondir.IPersonAttributeDao#getUserAttributes(java.util.Map)
	 */
	@SuppressWarnings("deprecation")
	public Map<String, Object> getUserAttributes(Map<String, Object> arg0) {
		return personAttributeRepository.getUserAttributes(arg0);
	}

	/* (non-Javadoc)
	 * @see org.jasig.services.persondir.IPersonAttributeDao#getUserAttributes(java.lang.String)
	 */
	@SuppressWarnings("deprecation")
	public Map<String, Object> getUserAttributes(String arg0) {
		return personAttributeRepository.getUserAttributes(arg0);
	}
}