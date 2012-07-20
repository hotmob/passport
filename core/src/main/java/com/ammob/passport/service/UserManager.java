package com.ammob.passport.service;

import com.ammob.passport.dao.UserDao;
import com.ammob.passport.model.User;
import com.ammob.passport.exception.UserExistsException;

import org.jasig.services.persondir.IPersonAttributes;
import org.springframework.ldap.control.PagedResult;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

import javax.naming.NamingException;

/**
 * Business Service Interface to handle communication between web and
 * persistence layer.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 *  Modified by <a href="mailto:dan@getrolling.com">Dan Kibler </a> 
 */
public interface UserManager extends GenericManager<User, Long> {
	
    /**
     * Convenience method for testing - allows you to mock the DAO and set it on an interface.
     * @param userDao the UserDao implementation to use
     */
    void setUserDao(UserDao userDao);
    
    /**
     * Retrieves a user by userId.  An exception is thrown if user not found
     *
     * @param userId the identifier for the user
     * @return User
     */
    User getUser(String userId);
    
    /**
     * From ldap repository to finds a user by their identifying.
     * @param identifying username or mail
     * @return
     */
    IPersonAttributes getPersonAttributes(String identifying);
    
    /**
     * Finds a user by their username.
     * @param username the user's username used to login
     * @return User a populated user object
     * @throws org.springframework.security.core.userdetails.UsernameNotFoundException
     *         exception thrown when user not found
     */
    User getUserByUsername(String username) throws UsernameNotFoundException;
    
    /**
     * Retrieves a list of all users.
     * @return List
     */
    List<User> getUsers();

    /**
     * Saves a user's information.
     *
     * @param user the user's information
     * @throws UserExistsException thrown when user already exists
     * @return user the updated user object
     */
    User saveUser(User user) throws UserExistsException;
    
    /**
     * Saves a user's information to ldap.
     *
     * @param user the user's information
     * @throws UserExistsException thrown when user already exists
     * @return user the updated user object
     */
    User savePerson(User user) throws UserExistsException;
    
    /**
     * Removes a user from the database by their userId
     *
     * @param userId the user's id
     */
    void removeUser(String userId);
    
    /**
     * Search a user for search terms.
     * @param searchTerm the search terms.
     * @return a list of matches, or all if no searchTerm.
     */
    List<User> search(String searchTerm);
    
    /**
     * Search a user for search terms page result.
     * @param searchTerm the search terms.
     * @param pagesize result list.
     * @param cookie result cookie.
     * @return a list of matches, or all if no searchTerm.
     */
    PagedResult getPersons(int current, int pageSize) throws NamingException;
    
    /**
     * Changes the password for the current user. The username is obtained from the security context.
     * <p>
     * If the old password is supplied, the update will be made by rebinding as the user, thus modifying the password
     * using the user's permissions. If <code>oldPassword</code> is null, the update will be attempted using a
     * standard read/write context supplied by the context source.
     * </p>
     *
     * @param oldPassword the old password
     * @param newPassword the new value of the password.
     */
    void changePassword(final String oldPassword, final String newPassword);
}