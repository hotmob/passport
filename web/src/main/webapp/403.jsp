<%@ page import="org.jasig.cas.web.support.WebUtils"%>
<%@ page import="org.springframework.security.web.WebAttributes"%>
<%@ include file="/WEB-INF/view/jsp/common/taglibs.jsp"%>
<page:applyDecorator name="default">

<head>
    <title><fmt:message key="403.title"/></title>
    <meta name="heading" content="<fmt:message key='403.title'/>"/>
</head>

<p>
    <fmt:message key="403.message">
        <fmt:param><c:url value="/"/></fmt:param>
    </fmt:message>
</p>
<p style="text-align: center; margin-top: 20px">
    <div id="msg" class="errors">
	<h2><spring:message code="screen.blocked.header" /></h2>
	<%
        // Look for details of authorization failure in well-known request attributes.
        final String[] keys = new String[] {WebUtils.CAS_ACCESS_DENIED_REASON, WebAttributes.AUTHENTICATION_EXCEPTION};
        Object detail = null;
        for (String key : keys) {
            detail = request.getAttribute(key);
            if (detail == null) {
                detail = request.getSession().getAttribute(key);
                request.getSession().removeAttribute(key);
            }
            if (detail != null) {
                break;
            }
        }
        if (detail instanceof String) {
            request.setAttribute("messageKey", detail);
        } else if (detail instanceof Exception) {
            final Exception cause = (Exception) detail;
            final String message = String.format("%s::%s", cause.getClass().getSimpleName(), cause.getMessage());
            request.setAttribute("message", message);
        }
    %>
    <c:choose>
        <c:when test="${not empty messageKey}">
            <p><spring:message code="${messageKey}" /></p>
        </c:when>
        <c:when test="${not empty message}">
            <p><c:out value="${message}" escapeXml="true" /></p>
        </c:when>
        <c:otherwise>
        	<p><spring:message code="screen.blocked.message" /></p>
        </c:otherwise>
    </c:choose>
</div>
</p>
</page:applyDecorator>