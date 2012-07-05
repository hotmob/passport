package com.ammob.passport.service.impl;

import com.ammob.passport.dao.RoleDao;
import com.ammob.passport.mapper.RoleMapper;
import com.ammob.passport.model.Role;
import com.ammob.passport.service.RoleManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.control.PagedResult;
import org.springframework.ldap.control.PagedResultsCookie;
import org.springframework.ldap.control.PagedResultsDirContextProcessor;
import org.springframework.ldap.core.CollectingNameClassPairCallbackHandler;
import org.springframework.ldap.core.ContextMapperCallbackHandler;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import javax.naming.directory.SearchControls;

/**
 * Implementation of RoleManager interface.
 *
 * @author <a href="mailto:dan@getrolling.com">Dan Kibler</a>
 */
@Service("roleManager")
public class RoleManagerImpl extends GenericManagerImpl<Role, Long> implements RoleManager {
	
    RoleDao roleDao;
	
    @Autowired
    public RoleManagerImpl(RoleDao roleDao) {
        super(roleDao);
        this.roleDao = roleDao;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
	public Set<Role> getRoles() {
    	Set<Role> roles = new HashSet<Role>();
    	try {
    		roles.addAll( dao.getAll());
			roles.addAll(this.getPageRoles(20, new PagedResultsCookie(null)).getResultList());
		} catch (Exception e) {
			e.printStackTrace();
		}
        return roles;
    }

    /**
     * {@inheritDoc}
     */
    public Role getRole(String rolename) {
    	Role role = null;
    	try {
			role = (Role) ldapTemplate.lookup("cn=" + rolename + ",ou=groups", new RoleMapper());
		} catch (Exception e) {
			role = roleDao.getRoleByName(rolename);
		}
        return role;
    }

    /**
     * {@inheritDoc}
     */
    public Role saveRole(Role role) {
        return dao.save(role);
    }

    /**
     * {@inheritDoc}
     */
    public void removeRole(String rolename) {
        roleDao.removeRole(rolename);
    }
    
    private PagedResult getPageRoles(int pageSize, PagedResultsCookie cookie) {
    	SearchControls searchControls = new SearchControls();
    	EqualsFilter filter = new EqualsFilter("objectclass", "groupOfUniqueNames");
    	searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
    	PagedResultsDirContextProcessor requestControl = new PagedResultsDirContextProcessor(pageSize,cookie);
    	CollectingNameClassPairCallbackHandler handler = new ContextMapperCallbackHandler(new RoleMapper()); 
    	ldapTemplate.search(DistinguishedName.EMPTY_PATH, filter.encode(), searchControls, handler, requestControl);
    	return new PagedResult(handler.getList(),requestControl.getCookie());
    }
    
	@Autowired
	private LdapTemplate ldapTemplate;
}