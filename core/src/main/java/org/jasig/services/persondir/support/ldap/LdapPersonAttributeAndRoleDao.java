/* Had to add to org.jasig.services.persondir.support.ldap package in order to
 * have access to the necessary classes.
 * (LogicalFilterWrapper is only package accessible and exists in this particular package.)
 */
package org.jasig.services.persondir.support.ldap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jasig.services.persondir.IPersonAttributes;
import org.jasig.services.persondir.support.CaseInsensitiveAttributeNamedPersonImpl;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;
/**
 * This class adds an "authorities" attribute to a person.  Otherwise all functionality
 * of the base class LdapPersonAttributeDao is retained.
 * @author daniel
 */
public class LdapPersonAttributeAndRoleDao extends LdapPersonAttributeDao{
    /*
     * The Spring Security Ldap class that fetches Authorities for us
     */
    private DefaultLdapAuthoritiesPopulator ldapAuthoritiesPopulator;
    
    @Override
    /**
     * Override the base class to provide "authorities" in the returned list
     */
    protected List<IPersonAttributes> getPeopleForQuery(LogicalFilterWrapper queryBuilder, String queryUserName) {
        //the list of users with the username as fetched from the base class (no role info here)
        List<IPersonAttributes> attribs = super.getPeopleForQuery(queryBuilder, queryUserName);

        //the list of users that we want to return
        final List<IPersonAttributes> peopleWithRoles = new ArrayList<IPersonAttributes>(attribs.size());

        //Fetch the Authorities from Ldap
        Collection<GrantedAuthority> authorities = null;
        try{
            /* This particular line definitely needs to be refactored if other
             * people want to use it.  It needs to be either configured in source
             * code, or made configurable...
             * I am not familiar enough with querybuilder to understand
             * exactly how this should look and generate a configurable dn.
             */
            String userDn = "cn=" + queryUserName + "," + getBaseDN() + ",dc=766,dc=com";
            //Utilize the Spring Security Ldap functionality to obtain granted authorities
            authorities = ldapAuthoritiesPopulator.getGrantedAuthorities(new DirContextAdapter(userDn), queryUserName);
        }catch (Exception nnfe){
            //we just won't add authorities if there was an error.
            logger.error("error looking up authorities", nnfe);
        }

        //add authorities in the format required, if there are any found
        List<Object> authoritiesList;
        if(null!=authorities){
            //transform the GrantedAuthority list into a List of Strings
            authoritiesList = new ArrayList<Object>();
            for(GrantedAuthority auth : authorities){
                authoritiesList.add(auth);
            }

            /* Edit the list coming from the base class.  A bunch of stuff is 
             * marked "final" in the base class, so we need to create new lists...
             *
             * I am not familiar enough with Ldap to understand why more than one
             * person object can exist for this dn...
             * However, we will add the authorities to all of them.
             */
            for(IPersonAttributes person : attribs){
                // the new list of attributes
                Map<String, List<Object>> attrs = new HashMap<String, List<Object>>();
                // add the old attributes
                attrs.putAll(person.getAttributes());
                // add the authorities
                attrs.put("authorities", authoritiesList);
                // add the person to the return list.
                peopleWithRoles.add(
                        new CaseInsensitiveAttributeNamedPersonImpl(
                        this.getConfiguredUserNameAttribute(), attrs));
            }
        }else{
            /* if no authorities exist, just add the person to the list without
             * trying to add the non-existent authorities.
             */
            peopleWithRoles.addAll(attribs);
        }

        return peopleWithRoles;
    }

    public DefaultLdapAuthoritiesPopulator getLdapAuthoritiesPopulator() {
        return ldapAuthoritiesPopulator;
    }

    public void setLdapAuthoritiesPopulator(DefaultLdapAuthoritiesPopulator ldapAuthoritiesPopulator) {
        this.ldapAuthoritiesPopulator = ldapAuthoritiesPopulator;
    }
}