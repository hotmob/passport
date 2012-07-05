package com.ammob.passport.service.impl;

import com.ammob.passport.dao.LookupDao;
import com.ammob.passport.model.LabelValue;
import com.ammob.passport.model.Role;
import com.ammob.passport.service.LookupManager;
import com.ammob.passport.service.RoleManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Implementation of LookupManager interface to talk to the persistence layer.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Service("lookupManager")
public class LookupManagerImpl implements LookupManager {
    @Autowired
    LookupDao dao;
    @Autowired
    RoleManager roleManager;
    /**
     * {@inheritDoc}
     */
    public List<LabelValue> getAllRoles() {
        Set<Role> roles = new HashSet<Role>();
        if(roleManager != null)
        	roles.addAll(roleManager.getRoles());
        roles.addAll(dao.getRoles());
        List<LabelValue> list = new ArrayList<LabelValue>();
        for (Role role1 : roles) {
            list.add(new LabelValue(role1.getDescription(), "ROLE_" + role1.getName().toUpperCase()));
        }
        return list;
    }
}
