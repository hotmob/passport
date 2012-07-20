package com.ammob.passport.webapp.controller;

import javax.inject.Inject;

import org.jasig.cas.adaptors.ldap.BindLdapAuthenticationHandler;
import org.jasig.cas.authentication.principal.UsernamePasswordCredentials;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ldap.core.ContextSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = "classpath:/applicationContext-resources.xml")
public class SigninControllerTest extends AbstractJUnit4SpringContextTests {

    protected BindLdapAuthenticationHandler bindAuthHandler;
	
	@Inject
	@Qualifier("contextSource")
	protected ContextSource contextSource;
	
	@Inject
	@Qualifier("pooledContextSource")
	protected ContextSource searchContextSource;
	
    @Before
    public void onSetUp() {
    	bindAuthHandler = new BindLdapAuthenticationHandler();
    	bindAuthHandler.setContextSource(contextSource);
    	bindAuthHandler.setSearchContextSource(searchContextSource);
    	bindAuthHandler.setFilter("(|(mail=%u)(cn=%u))");
    	//bindAuthHandler.setFilter("%u");
    	bindAuthHandler.setSearchBase("ou=Users");
    	bindAuthHandler.setTimeout(3000);
    	bindAuthHandler.setIgnorePartialResultException(true);
    }
    
    @Test
    public void testSuccessUsernamePassword() throws Exception {
        final UsernamePasswordCredentials c = new UsernamePasswordCredentials();
        c.setUsername("hotmob");
        //DefaultPasswordEncoder dd = new DefaultPasswordEncoder("MD5");
        c.setPassword("121212");
        //c.setPassword("{MD5}kyeeMwi9u+7ZRvyWUBf2eg==");
        //org.junit.Assert.assertNotNull(this.contextSource.getContext("cn=hotmob,ou=Users,dc=766,dc=com", "121212"));
        //org.junit.Assert.assertTrue(this.bindAuthHandler.authenticate(c));
    }
}
