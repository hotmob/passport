<%@ include file="/WEB-INF/view/jsp/common/taglibs.jsp"%>
<%@page import="org.springframework.util.StringUtils"%>
<%@ page import="org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices" %>
<%@ page import="javax.servlet.http.Cookie" %>
<%
if (request.getSession(false) != null) {
    session.invalidate();
}
Cookie terminate = new Cookie(TokenBasedRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY, null);
String contextPath = request.getContextPath();
terminate.setPath(contextPath != null && contextPath.length() > 0 ? contextPath : "/");
terminate.setMaxAge(0);
response.addCookie(terminate);
String returnUrl = request.getParameter("returnUrl");
if(!StringUtils.hasText(returnUrl)) {
	returnUrl = request.getParameter("service");
	returnUrl = "/login" + (StringUtils.hasText(returnUrl) ? "?service=" + returnUrl : "");
} else {
	if(!returnUrl.contains("http"))
		returnUrl = "http://" + returnUrl;
}
%>
<c:redirect url="<%=returnUrl%>" />