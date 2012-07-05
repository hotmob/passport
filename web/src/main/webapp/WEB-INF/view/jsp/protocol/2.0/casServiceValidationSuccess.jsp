<%@ page session="false" %><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<cas:serviceResponse xmlns:cas='http://passport.766.com/auth'>
    <cas:authenticationSuccess>

        <cas:user>${fn:escapeXml(assertion.chainedAuthentications[fn:length(assertion.chainedAuthentications)-1].principal.id)}</cas:user>
        <c:if test="${not empty pgtIou}">
            <cas:proxyGrantingTicket>${pgtIou}</cas:proxyGrantingTicket>
        </c:if>
        
        <c:if test="${fn:length(assertion.chainedAuthentications) > 1}">
            <cas:proxies>
                <c:forEach var="proxy" items="${assertion.chainedAuthentications}" varStatus="loopStatus" begin="0" end="${fn:length(assertion.chainedAuthentications)-2}" step="1">
                    <cas:proxy>${fn:escapeXml(proxy.principal.id)}</cas:proxy>
                </c:forEach>
            </cas:proxies>
        </c:if>
        <!-- Begin Ldap Attributes -->
		<c:if test="${fn:length(assertion.chainedAuthentications) > 0}">
			<cas:attributes>
				<c:forEach var="auth" items="${assertion.chainedAuthentications}">
                    <c:forEach var="attr" items="${auth.principal.attributes}">
                        <c:choose>
                            <c:when test="${fn:escapeXml(attr.key) eq 'authorities'}">
                                <cas:authorities>
                                    <c:choose>
                                        <c:when test="${fn:contains(attr.value,'[')}">
                                            <c:forEach var="authority" items="${attr.value}">
                                                <cas:authority>${fn:escapeXml(authority)}</cas:authority>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <cas:authority>${fn:escapeXml(attr.value)}</cas:authority>
                                        </c:otherwise>
                                    </c:choose>
                                </cas:authorities>
                            </c:when>
                            <c:otherwise>
                                <cas:${fn:escapeXml(attr.key)}>${fn:escapeXml(attr.value)}</cas:${fn:escapeXml(attr.key)}>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </c:forEach>
			</cas:attributes>
		</c:if>
		<!-- End Ldap Attributes -->
    </cas:authenticationSuccess>
</cas:serviceResponse>