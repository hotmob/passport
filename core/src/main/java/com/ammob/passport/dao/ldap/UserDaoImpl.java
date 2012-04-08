package com.ammob.passport.dao.ldap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import javax.naming.directory.SearchControls;
import javax.transaction.NotSupportedException;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ammob.passport.dao.UserDao;
import com.ammob.passport.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.AbstractContextMapper;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.BinaryLogicalFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.OrFilter;
import org.springframework.util.StringUtils;
import org.springframework.stereotype.Repository;

@Repository("ldapUserDao")
public class UserDaoImpl implements UserDao, UserDetailsService {
	
	private LdapTemplate ldapTemplate;
	private LdapContextSource ldapContextSource;
	private static List<String> USER_ATTRIBUTE = new ArrayList<String>();
    
	static {
		USER_ATTRIBUTE.add("mail");
		USER_ATTRIBUTE.add("displayName");
		USER_ATTRIBUTE.add("userPassword");
		USER_ATTRIBUTE.add("givenName");
		USER_ATTRIBUTE.add("cn");
		USER_ATTRIBUTE.add("sn");
		USER_ATTRIBUTE.add("description");
		USER_ATTRIBUTE.add("telephoneNumber");
		USER_ATTRIBUTE.add("st");
		USER_ATTRIBUTE.add("createTimestamp");
		USER_ATTRIBUTE.add("entryUUID");
		USER_ATTRIBUTE.add("sex");
		USER_ATTRIBUTE.add("birthday");
		USER_ATTRIBUTE.add("bloodType");
		USER_ATTRIBUTE.add("identifier");
		USER_ATTRIBUTE.add("country");
		USER_ATTRIBUTE.add("province");
		USER_ATTRIBUTE.add("city");
		USER_ATTRIBUTE.add("postalCode");
		USER_ATTRIBUTE.add("sex");
		USER_ATTRIBUTE.add("postalAddress");
	}

	@SuppressWarnings("unchecked")
	public List<User> getAll() {
		EqualsFilter filter = new EqualsFilter("objectclass", "inetOrgPerson");
		return ldapTemplate.search(DistinguishedName.EMPTY_PATH, filter.encode(), getContextMapper());
	}

	public List<User> getAllDistinct() {
		Collection<User> result = new LinkedHashSet<User>(getAll());
        return new ArrayList<User>(result);
	}

	public User get(Long id) {
		return null;
	}

	public boolean exists(Long id) {
		return false;
	}

	public User save(User object) {
		return saveUser(object);
	}

	public void remove(Long id) {
		try {
			throw new NotSupportedException();
		} catch (NotSupportedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Load User By Username
	 */
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		DirContextOperations context=  ldapTemplate.lookupContext(buildDn(username));
		User user = getContextMapper().doMapFromContext(context);
        if (user == null || user.getUsername() == null) {
            throw new UsernameNotFoundException("user '" + username + "' not found...");
        } else {
//    		Collection<? extends GrantedAuthority> authorities = ldapAuthoritiesPopulator.getGrantedAuthorities(context, username);
//    		for(GrantedAuthority authority : authorities){
//    			Role role = new Role();
//    			role.setName(authority.getAuthority());
//    			user.addRole(role);
//    		}
            return user;
        }
	}
	
    /**
     * {@inheritDoc}
     */
	public List<User> getUsers() {
		return getAll();
	}
	
    /**
     * {@inheritDoc}
     */
	public User saveUser(User user) {
		DirContextOperations context = null;
		try { // updata user
			context = ldapTemplate.lookupContext( buildDn(user.getUsername()));
			mapToContext(user,context);
			ldapTemplate.modifyAttributes(context);
		} catch (Exception e) { // creat new user
			context = new DirContextAdapter();
			mapToContext(user,context);
			ldapTemplate.bind(buildDn(user.getUsername()), context, null);
		}
		return user;
	}
	
    /**
     * {@inheritDoc}
     */
	public String getUserPassword(String username) {
		return loadUserByUsername(username).getPassword();
	}

	/**
	 * @param username
	 * @return
	 */
	private DistinguishedName buildDn(String username) {
		DistinguishedName dn = new DistinguishedName("ou=Users");
		if(username != null)
			dn.add("cn", username);
		return dn;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<User> findByNamedQuery(String queryName, Map<String, Object> queryParams) {
		/** scope - 搜索范围。为以下值之一：OBJECT_SCOPE、ONELEVEL_SCOPE 和 SUBTREE_SCOPE。
			 countlim - 要返回的最大项数。如果为 0，则返回符合过滤器的所有项。
			 timelim - 返回前要等待的毫秒数。如果为 0，则无限期地等待。
			 attrs - 要与项一起返回的属性的标识符。如果为 null，则返回所有属性。如果为空，则不返回任何属性。
			 retobj - 如果为 true，则返回绑定到项名称的对象；如果为 false，则不返回对象。
			 deref - 如果为 true，则在搜索期间取消对链接的引用。**/
		SearchControls controls = new SearchControls(SearchControls.ONELEVEL_SCOPE, 0, 3000, null, false, false);
		BinaryLogicalFilter binaryLogicalFilter = null;
		if(queryName.toLowerCase().contains("and")) {
			AndFilter filter = new AndFilter();
			for(String key : queryParams.keySet())
				filter.and(new EqualsFilter(key, (String) queryParams.get(key)));
			binaryLogicalFilter = filter;
		} else {
			OrFilter filter = new OrFilter();
			for(String key : queryParams.keySet())
				filter.or(new EqualsFilter(key, (String) queryParams.get(key)));
			binaryLogicalFilter = filter;
		}
		List<User> results = ldapTemplate.search(buildDn(null), binaryLogicalFilter.encode(), controls, getContextMapper());
		return results;
	}
	
	/**
	 * @return the ldapContextSource
	 */
	public LdapContextSource getLdapContextSource() {
		return ldapContextSource;
	}

	/**
	 * @param ldapContextSource the ldapContextSource to set
	 */
    @Autowired
    @Required
	public void setLdapContextSource(LdapContextSource ldapContextSource) {
		this.ldapContextSource = ldapContextSource;
		this.ldapTemplate = new LdapTemplate(ldapContextSource);
	}
    
    /**
     * Get User Context Mapper
     * @return
     */
    private PersonContextMapper getContextMapper() {
    	return new PersonContextMapper();
	}
    
    /**
	 * @param User user
	 * @param DirContextOperations context
	 */
	public void mapToContext(User person, DirContextOperations context){
		context.setAttributeValues("objectclass", new String[] {"top", "inetOrgPerson", "UserDetails"});
		if(StringUtils.hasText(person.getUsername())) 
			context.setAttributeValue("cn", person.getUsername());									// 用户名,登录名
		if(StringUtils.hasText(person.getPassword()))
			context.setAttributeValue("userPassword", person.getPassword());					// 密码
		if(StringUtils.hasText(person.getNickname()))
			context.setAttributeValue("displayName", person.getNickname());					// 用户昵称
		if(StringUtils.hasText(person.getFirstName()))
			context.setAttributeValue("sn", person.getFirstName());									// 用户姓氏
		else
			context.setAttributeValue("sn", person.getUsername());
		if(StringUtils.hasText(person.getLastName()))
			context.setAttributeValue("givenName", person.getLastName());						// 用户名字
		if(StringUtils.hasText(person.getDescription()))
			context.setAttributeValue("description", person.getDescription());						// 用户描述
		if(StringUtils.hasText(person.getBirthTime()))
			context.setAttributeValue("birthday", person.getBirthTime());							// 出生时间
		if(StringUtils.hasText(person.getBloodType()))
			context.setAttributeValue("bloodType", person.getBloodType());						// 用户血型
		if(StringUtils.hasText(person.getEthnicity()))
			context.setAttributeValue("ethnicity", person.getEthnicity());								// 用户种族
		if(StringUtils.hasText(person.getState()))
			context.setAttributeValue("st", person.getState());												// 用户状态
		if(StringUtils.hasText(person.getSex()))
			context.setAttributeValue("sex", person.getSex());												// 用户性别
		if(StringUtils.hasText(person.getUserId()))
			context.setAttributeValue("identifier", person.getUserId());								// 用户身份证
		if(StringUtils.hasText(person.getPhoneNumber()))
			context.setAttributeValue("telephoneNumber", person.getPhoneNumber());		// 用户电话
		if(StringUtils.hasText(person.getEmail()))
			context.setAttributeValue("mail", person.getEmail());											// 用户邮件
		if(StringUtils.hasText(person.getAddress().getCountry())) context.setAttributeValue("country", person.getAddress().getCountry());				// 用户国家
		if(StringUtils.hasText(person.getAddress().getProvince())) context.setAttributeValue("province", person.getAddress().getProvince());				// 用户省
		if(StringUtils.hasText(person.getAddress().getCity())) context.setAttributeValue("city", person.getAddress().getCity());									// 用户城市
		if(StringUtils.hasText(person.getAddress().getAddress())) context.setAttributeValue("postalAddress", person.getAddress().getAddress());		// 用户地址
		if(StringUtils.hasText(person.getAddress().getPostalCode())) context.setAttributeValue("postalCode", person.getAddress().getPostalCode());	// 用户邮编
	}
}

class PersonContextMapper extends AbstractContextMapper {
	@Override
	protected User doMapFromContext(DirContextOperations context) {
		User person = new User();
		person.setUsername(context.getStringAttribute("cn")); 													// 用户名
		person.setPassword(new String((byte[]) context.getObjectAttribute("userPassword")));		// 密码
		person.setNickname(context.getStringAttribute("displayName"));										// 用户昵称
		person.setFirstName(context.getStringAttribute("sn"));														// 用户姓氏
		person.setLastName(context.getStringAttribute("givenName"));										// 用户名字
		person.setDescription(context.getStringAttribute("description"));										// 用户描述
		person.setPhoneNumber(context.getStringAttribute("telephoneNumber"));						// 移动电话
		person.setEmail(context.getStringAttribute("mail"));															// 电子邮件
		person.setRegTime(context.getStringAttribute("createTimestamp"));									// 注册时间
		person.setUuid(context.getStringAttribute("entryUUID"));													// UUID
		person.setBirthTime(context.getStringAttribute("birthday"));												// 出生时间
		person.setBloodType(context.getStringAttribute("bloodType"));										// 用户血型
		person.setEthnicity(context.getStringAttribute("ethnicity"));												// 用户种族
		person.setState(context.getStringAttribute("st"));																// 用户状态
		person.setSex(context.getStringAttribute("sex"));																// 用户性别
		person.setUserId(context.getStringAttribute("identifier"));													// 用户身份证
		
		person.getAddress().setCountry(context.getStringAttribute("country"));			// 所在国家
		person.getAddress().setProvince(context.getStringAttribute("province"));			// 所在省
		person.getAddress().setCity(context.getStringAttribute("city"));							// 所在城市
		person.getAddress().setAddress(context.getStringAttribute("postalAddress"));	// 所在地址
		person.getAddress().setPostalCode(context.getStringAttribute("postalCode"));	// 邮政编码
		person.setEnabled(true);
		person.setVersion(1);
		return person;
	}
}
