package com.ammob.passport.mapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import com.ammob.passport.model.Role;

public class RoleMapper implements ContextMapper {

    private final Log logger = LogFactory.getLog(RoleMapper.class);
	
	/**
	 * get context 获取用户组信息
	 * @param ctx
	 * @return
	 */
	public Role mapFromContext(Object ctx) {
		DirContextAdapter context = (DirContextAdapter) ctx;
		Role role = new Role();
		role.setName(context.getStringAttribute("cn"));
		role.setDescription(context.getStringAttribute("description"));
		if(logger.isDebugEnabled())
			logger.debug("into class RoleMapper.mapFromContext");
		return role;
	}
	
	/**
	 * set context 添加用户组
	 * @param group
	 * @param context 属性访问及操作
	 * @return
	 */
//	public DirContextAdapter mapToContext(Role role, DirContextAdapter context) {
//		context.setAttributeValues("objectclass", new String[]{"groupOfUniqueNames"});
//		if (StringUtils.hasText(role.getName())) {//组名
//			context.setAttributeValue("cn", role.getName());
//		}
//		if (StringUtils.hasText(role.getDescription())) {//组信息描述
//			context.setAttributeValue("description", role.getDescription());
//		}
//		logger.info("add user group !");
//		return context;
//	}
	
	/**
	 * 
	 * @param group
	 * @return
	 */
//	public DirContextAdapter mapToContext(Role role) {
//		DistinguishedName dn = new DistinguishedName("ou=Groups");
//		dn.add("cn", role.getName());
//		return mapToContext(role, new DirContextAdapter(dn));
//	}
}