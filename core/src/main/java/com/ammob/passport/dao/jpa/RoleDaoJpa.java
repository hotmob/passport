package com.ammob.passport.dao.jpa;

import com.ammob.passport.dao.RoleDao;
import com.ammob.passport.model.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;


/**
 * This class interacts with Spring's HibernateTemplate to save/delete and
 * retrieve Role objects.
 *
 * @author <a href="mailto:bwnoll@gmail.com">Bryan Noll</a> 
 */
@Repository
public class RoleDaoJpa extends GenericDaoJpa<Role, Long> implements RoleDao {

    /**
     * Constructor to create a Generics-based version using Role as the entity
     */
    public RoleDaoJpa() {
        super(Role.class);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
	public Role getRoleByName(String rolename) {
        Query q = getEntityManager().createQuery("select r from Role r where r.name = ?");
        q.setParameter(1, rolename);
        List<Role> roles = q.getResultList();
        if (roles.isEmpty()) {
            return null;
        } else {
            return roles.get(0);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void removeRole(String rolename) {
        Object role = getRoleByName(rolename);
        getEntityManager().remove(role);
    }
}
