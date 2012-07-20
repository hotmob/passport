package com.ammob.passport.mapper;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.ppolicy.PasswordPolicyControl;
import org.springframework.security.ldap.ppolicy.PasswordPolicyResponseControl;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.ammob.passport.Constants;
import com.ammob.passport.model.Role;
import com.ammob.passport.model.User;

public class PersonMapper implements UserDetailsContextMapper, ContextMapper {
	//~ Instance fields ================================================================================================

    private final Log logger = LogFactory.getLog(PersonMapper.class);
    private String passwordAttributeName = "userPassword";
    private String rolePrefix = "ROLE_";
    private String[] roleAttributes = null;
    private boolean convertToUpperCase = true;

    //~ Methods ========================================================================================================

    public UserDetails mapUserFromContext(DirContextOperations ctx, String username, Collection<? extends GrantedAuthority> authorities) {
        String dn = ctx.getNameInNamespace();
        logger.debug("Mapping user details from context with DN: " + dn);
        User user = new User(username);
        Object passwordValue = ctx.getObjectAttribute(passwordAttributeName);
        if (passwordValue != null) {
            user.setPassword(mapPassword(passwordValue));
        }
        // Map the roles
        for (int i = 0; (roleAttributes != null) && (i < roleAttributes.length); i++) {
            String[] rolesForAttribute = ctx.getStringAttributes(roleAttributes[i]);
            if (rolesForAttribute == null) {
                logger.debug("Couldn't read role attribute '" + roleAttributes[i] + "' for user " + dn);
                continue;
            }
            for (String role : rolesForAttribute) {
                user.addRole(new Role(role));
            }
        }
        // Add the supplied authorities
        for (GrantedAuthority authority : authorities) {
            user.addRole(new Role(authority.getAuthority()));
            logger.debug(username + "'s authority.getAuthority() : " + authority.getAuthority());
        }
        // Check for PPolicy data
        PasswordPolicyResponseControl ppolicy = (PasswordPolicyResponseControl) ctx.getObjectAttribute(PasswordPolicyControl.OID);
        if (ppolicy != null) {
            user.setTimeBeforeExpiration(ppolicy.getTimeBeforeExpiration());
            user.setGraceLoginsRemaining(ppolicy.getGraceLoginsRemaining());
        }
     	user.setDisplayName(ctx.getStringAttribute("displayName"));
 		user.setFirstName(ctx.getStringAttribute("sn"));
 		user.setLastName(ctx.getStringAttribute("givenName"));
 		user.setDescription(ctx.getStringAttribute("description"));
 		user.setPhoneNumber(ctx.getStringAttribute("telephoneNumber"));
 		user.setEmail(ctx.getStringAttribute("mail"));
 		user.setAvataUrl(ctx.getStringAttribute("labeledURI"));
 		user.setState(ctx.getStringAttribute("st"));
 		user.setIdentity(ctx.getStringAttribute("uid"));
 		user.getAddress().setCountry(ctx.getStringAttribute("country"));
 		user.getAddress().setProvince(ctx.getStringAttribute("province"));
 		user.getAddress().setCity(ctx.getStringAttribute("city"));
 		user.getAddress().setPostalAddress(ctx.getStringAttribute("postalAddress"));
 		user.getAddress().setPostalCode(ctx.getStringAttribute("postalCode"));
 		
 		try {user.setVersion(Integer.decode(ctx.getStringAttribute("modifyTimestamp").substring(5, 14)));} catch (Exception e) {}
 		try {user.setRegTime(ctx.getStringAttribute("createTimestamp"));} catch (Exception e) {}
 		try {user.setUuid(((DirContextAdapter)ctx).getStringAttribute("entryUUID"));} catch (Exception e) {}
 		
 		user.setEnabled(true);
 		user.setAccountExpired(false);
 		user.setAccountLocked(false);
 		user.setCredentialsExpired(false);
        return user;
    }

    public void mapUserToContext(UserDetails userDetails, DirContextAdapter context) {
    	 Assert.isInstanceOf(User.class, userDetails, "UserDetails must be an User instance");
    	if(userDetails instanceof User) {
			context.setAttributeValues("objectclass", new String[] {"top", "inetOrgPerson", "UserDetails"});
			if(StringUtils.hasText(((User)userDetails).getUsername())) 
				context.setAttributeValue("cn", ((User)userDetails).getUsername());										// 用户名,登录名
			if(StringUtils.hasText(((User)userDetails).getPassword()))
				context.setAttributeValue("userPassword", ((User)userDetails).getPassword());						// 密码
			if(StringUtils.hasText(((User)userDetails).getDisplayName()))
				context.setAttributeValue("displayName", ((User)userDetails).getDisplayName());					// 用户昵称
			if(StringUtils.hasText(((User)userDetails).getFirstName()))
				context.setAttributeValue("sn", ((User)userDetails).getFirstName());										// 用户姓氏
			else
				context.setAttributeValue("sn", ((User)userDetails).getUsername());
			if(StringUtils.hasText(((User)userDetails).getAvataUrl()))
				context.setAttributeValue("labeledURI", ((User)userDetails).getAvataUrl());							// 用户头像
			if(StringUtils.hasText(((User)userDetails).getLastName()))
				context.setAttributeValue("givenName", ((User)userDetails).getLastName());						// 用户名字
			if(StringUtils.hasText(((User)userDetails).getDescription()))
				context.setAttributeValue("description", ((User)userDetails).getDescription());						// 用户描述
			if(StringUtils.hasText(((User)userDetails).getState()))
				context.setAttributeValue("st", ((User)userDetails).getState());												// 用户状态
			if(StringUtils.hasText(((User)userDetails).getPhoneNumber()))
				context.setAttributeValue("telephoneNumber", ((User)userDetails).getPhoneNumber());		// 用户电话
			if(StringUtils.hasText(((User)userDetails).getEmail()))
				context.setAttributeValue("mail", ((User)userDetails).getEmail());											// 用户邮件
			if(StringUtils.hasText(((User)userDetails).getIdentity()))
				context.setAttributeValue("uid", ((User)userDetails).getIdentity());										// 用户身份证
			if(StringUtils.hasText(((User)userDetails).getAddress().getCountry())) context.setAttributeValue("country", ((User)userDetails).getAddress().getCountry());									// 用户国家
			if(StringUtils.hasText(((User)userDetails).getAddress().getProvince())) context.setAttributeValue("province", ((User)userDetails).getAddress().getProvince());								// 用户省
			if(StringUtils.hasText(((User)userDetails).getAddress().getCity())) context.setAttributeValue("city", ((User)userDetails).getAddress().getCity());														// 用户城市
			if(StringUtils.hasText(((User)userDetails).getAddress().getPostalAddress())) context.setAttributeValue("postalAddress", ((User)userDetails).getAddress().getPostalAddress());		// 用户邮政地址
			if(StringUtils.hasText(((User)userDetails).getAddress().getPostalCode())) context.setAttributeValue("postalCode", ((User)userDetails).getAddress().getPostalCode());					// 用户邮编
    	} else {
    		throw new ClassCastException("userDetails must be an User instance");
    	}
    }

    /**
     * Extension point to allow customized creation of the user's password from
     * the attribute stored in the directory.
     *
     * @param passwordValue the value of the password attribute
     * @return a String representation of the password.
     */
    protected String mapPassword(Object passwordValue) {
        if (!(passwordValue instanceof String)) {
            // Assume it's binary
            passwordValue = new String((byte[]) passwordValue);
        }
        return (String) passwordValue;
    }

    /**
     * Creates a GrantedAuthority from a role attribute. Override to customize
     * authority object creation.
     * <p>
     * The default implementation converts string attributes to roles, making use of the <tt>rolePrefix</tt>
     * and <tt>convertToUpperCase</tt> properties. Non-String attributes are ignored.
     * </p>
     *
     * @param role the attribute returned from
     * @return the authority to be added to the list of authorities for the user, or null
     * if this attribute should be ignored.
     */
    protected GrantedAuthority createAuthority(Object role) {
        if (role instanceof String) {
            if (convertToUpperCase) {
                role = ((String) role).toUpperCase();
            }
            return new SimpleGrantedAuthority(rolePrefix + role);
        }
        return null;
    }

    /**
     * Determines whether role field values will be converted to upper case when loaded.
     * The default is true.
     *
     * @param convertToUpperCase true if the roles should be converted to upper case.
     */
    public void setConvertToUpperCase(boolean convertToUpperCase) {
        this.convertToUpperCase = convertToUpperCase;
    }

    /**
     * The name of the attribute which contains the user's password.
     * Defaults to "userPassword".
     *
     * @param passwordAttributeName the name of the attribute
     */
    public void setPasswordAttributeName(String passwordAttributeName) {
        this.passwordAttributeName = passwordAttributeName;
    }

    /**
     * The names of any attributes in the user's  entry which represent application
     * roles. These will be converted to <tt>GrantedAuthority</tt>s and added to the
     * list in the returned LdapUserDetails object. The attribute values must be Strings by default.
     *
     * @param roleAttributes the names of the role attributes.
     */
    public void setRoleAttributes(String[] roleAttributes) {
        Assert.notNull(roleAttributes, "roleAttributes array cannot be null");
        this.roleAttributes = roleAttributes;
    }

    /**
     * The prefix that should be applied to the role names
     * @param rolePrefix the prefix (defaults to "ROLE_").
     */
    public void setRolePrefix(String rolePrefix) {
        this.rolePrefix = rolePrefix;
    }

	@Override
	public User mapFromContext(Object ctx) {
		DirContextOperations context = (DirContextOperations) ctx;
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(Constants.USER_ROLE));
		return (User) mapUserFromContext(context, context.getStringAttribute("cn"), authorities);
	}
}
