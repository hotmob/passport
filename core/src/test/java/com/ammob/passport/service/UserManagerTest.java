package com.ammob.passport.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ammob.passport.Constants;
import com.ammob.passport.enumerate.AttributeEnum;
import com.ammob.passport.model.User;

import org.junit.Test;
import org.jasig.services.persondir.IPersonAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.control.PagedResult;

import static org.junit.Assert.*;

public class UserManagerTest extends BaseManagerTestCase {
	
    private Log log = LogFactory.getLog(UserManagerTest.class);
    
    @Autowired
    private UserManager mgr;
    @Autowired
    private RoleManager roleManager;
	
    private User user;

    @Test
    public void testGetUser() throws Exception {
        user = mgr.getUserByUsername("mupeng");
        assertNotNull(user);
        log.debug(user);
        assertTrue(user.getRoles().size() > 0);
    }

    @Test
    public void testSaveUser() throws Exception {
        user = mgr.getUser("-1");
        user.setPhoneNumber("303-555-1212");
        log.debug("saving user with updated phone number: " + user);
        user = mgr.saveUser(user);
        assertEquals("303-555-1212", user.getPhoneNumber());
        assertTrue(user.getRoles().size() > 0);
    }

    @Test
    public void testAddAndRemoveUser() throws Exception {
        user = new User();
        // call populate method in super class to populate test data
        // from a properties file matching this class name
        user = (User) populate(user);
        user.addRole(roleManager.getRole(Constants.USER_ROLE));
        user = mgr.saveUser(user);
        assertEquals("john", user.getUsername());
        assertEquals(1, user.getRoles().size());
        log.debug("removing user...");
        mgr.removeUser(user.getId().toString());
        mgr.removeUser(user.getUsername());
        try {
            user = mgr.getUserByUsername("john");
            fail("Expected 'Exception' not thrown");
        } catch (Exception e) {
            log.debug(e);
            assertNotNull(e);
        }
    }
    
    @Test
    public void testGetMemberFromRepository() {
    	IPersonAttributes attributes = mgr.getPersonAttributes("hotmob");
		log.debug(attributes.getAttributeValues(AttributeEnum.USER_AUTHORITIES.getValue()));
		assertNotNull(attributes);
	}
    
    @Test
    public void testSavePerson() throws Exception {
        user = new User();
        user = (User) populate(user);
        user.addRole(roleManager.getRole(Constants.USER_ROLE));
        user.setUsername("hotmob" + System.currentTimeMillis());
        user.setEmail(user.getUsername() + "@ammob.com");
    	user = mgr.savePerson(user);
		assertNotNull(user);
	}
    
    @Test
    public void testSearchPerson() throws Exception {
    	List<User> users = mgr.search(null);
    	for(User user : users)
    		System.out.println(user);
    }
    
    @SuppressWarnings("unchecked")
	@Test
    public void testGetPersons() throws Exception {
    	PagedResult pr = mgr.getPersons(0, 20);
    	List<User> users = pr.getResultList();
    	for(User user : users)
    		System.out.print(user.getUsername() + ", ");
    	for(byte bb : pr.getCookie().getCookie())
    		System.out.print(bb + " ");
    	System.out.println("");
    	pr = mgr.getPersons(2, 20);
    	users = pr.getResultList();
    	for(User user : users)
    		System.out.print(user.getUsername() + ", ");
    	System.out.println("");
    	for(byte bb : pr.getCookie().getCookie())
    		System.out.print(bb + " ");
    }
}
