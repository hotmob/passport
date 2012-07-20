package com.ammob.passport.authentication.handler;

import org.jasig.cas.authentication.handler.AuthenticationException;
import org.jasig.cas.authentication.handler.UnsupportedCredentialsException;
import org.jasig.cas.authentication.handler.support.AbstractUsernamePasswordAuthenticationHandler;
import org.jasig.cas.authentication.principal.UsernamePasswordCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;

import com.ammob.passport.authentication.principal.InternalRememberMeUsernamePasswordCredentials;
import com.ammob.passport.enumerate.AttributeEnum;
import com.ammob.passport.service.UserManager;

public class CustomAuthenticationHandler extends AbstractUsernamePasswordAuthenticationHandler {

	@Autowired
	private UserManager userManager;
	
	@Autowired
    private PasswordEncoder passwordEncoder;

	@Override
	protected boolean authenticateUsernamePasswordInternal(final UsernamePasswordCredentials credentials) throws AuthenticationException {
		if(!(credentials instanceof UsernamePasswordCredentials))
			throw new UnsupportedCredentialsException();
		String userpassword = "";
		try {
			if((credentials instanceof InternalRememberMeUsernamePasswordCredentials) && ((InternalRememberMeUsernamePasswordCredentials) credentials).isInternal())
				return true;
			userpassword = new String((byte[]) userManager.getPersonAttributes(getPrincipalNameTransformer().transform(credentials.getUsername())).getAttributeValue(AttributeEnum.USER_PASSWORD.getValue()));
			if(userpassword.equals("{MD5}" + passwordEncoder.encodePassword(credentials.getPassword(), null)))
				return true;
		} catch (Exception e) {
			log.warn("Username : " + credentials.getUsername() + ", Password : " + credentials.getPassword() + ", Real Password : " + userpassword + ", e : " + e);
		}
		return false;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    
	/* Discuz 授权鉴定, 需要继承 AbstractJdbcUsernamePasswordAuthenticationHandler
    protected final boolean discuzAuthenticateInternal(final UsernamePasswordCredentials credentials) throws AuthenticationException {
		final String sql = "select uid,email,regip,regdate,isEmail,salt,password from users where name=?";
		final String username = getPrincipalNameTransformer().transform(credentials.getUsername()); // 本类Username未经变化
        try {
            final Map<String, Object> userInfo = getJdbcTemplate().queryForMap(this.sql, username);
            log.debug("user {} Info : [{}, {}, {}, {}]", new Object[] {username, userInfo.get("password"), 
            	userInfo.get("salt"), userInfo.get("email"), userInfo.get("uid")});
            log.debug("credentials Password : " + credentials.getPassword());
        	final String encryptedNewPassword = this.getPasswordEncoder().encode(
        			this.getPasswordEncoder().encode(credentials.getPassword()) + userInfo.get("salt"));
        	 log.debug("encryptedNewPassword : " + encryptedNewPassword);
        	return userInfo.get("password").equals(encryptedNewPassword);
        } catch (final IncorrectResultSizeDataAccessException e) {
            // this means the username was not found.
            return false;
        }
    }*/
}
