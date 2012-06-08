package com.ammob.passport.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.ammob.passport.Constants;
import com.ammob.passport.enumerate.AttributeEnum;
import com.ammob.passport.model.User;

import org.jasig.services.persondir.IPersonAttributes;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

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
        assertEquals(1, user.getRoles().size());
    }

    @Test
    public void testSaveUser() throws Exception {
        user = mgr.getUserByUsername("mupeng");
        user.setPhoneNumber("303-555-1212");

        log.debug("saving user with updated phone number: " + user);

        user = mgr.saveUser(user);
        assertEquals("303-555-1212", user.getPhoneNumber());
        assertEquals(1, user.getRoles().size());
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

        try {
            user = mgr.getUserByUsername("john");
            fail("Expected 'Exception' not thrown");
        } catch (Exception e) {
            log.debug(e);
            assertNotNull(e);
        }
    }
    
    @Test
    public void testGetMemberByUsername() {
    	UserDetails user = mgr.loadUserDetails("hotmob");
		assertNotNull(user);
	}
    
    @Test
    public void testGetMemberFromRepository() {
    	IPersonAttributes attributes = mgr.getPersonAttributes("hotmob");
		log.debug(attributes.getAttributeValues(AttributeEnum.USER_AUTHORITIES.getValue()));
		assertNotNull(attributes);
	}
    
    @Test
    public void testGetPerson() {
    	User user = mgr.getPerson("hotmob");
    	System.out.println(user);
		assertNotNull(user);
	}
    
    @Test
    public void testSavePerson() throws Exception {
        user = new User();
        user = (User) populate(user);
        user.addRole(roleManager.getRole(Constants.USER_ROLE));
        user.setUsername("hotmob" + System.currentTimeMillis());
        user.setEmail(user.getUsername() + "@ammob.com");
    	user = mgr.savePerson(user);
    	user = mgr.getPerson(user.getUsername());
    	System.out.println(user);
		assertNotNull(user);
	}
    
    @Test
    public void testSearchPerson() throws Exception {
    	mgr.search(null);
    }
}
