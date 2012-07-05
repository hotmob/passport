<%@ include file="/WEB-INF/view/jsp/common/taglibs.jsp" %>
<h3 class="get_password_tit"></h3>
<div class="flowsteps">
    <ol class="num4">
        <li class="done">
            <span class="first">&nbsp;&nbsp;&nbsp;<fmt:message key="hint.flowsteps.1" />&nbsp;&nbsp;&nbsp;</span>
        </li>
        <li class="current">
            <span>&nbsp;&nbsp;&nbsp;<fmt:message key="hint.flowsteps.2" />&nbsp;&nbsp;&nbsp;</span>
        </li>
        <li class="next">
            <span>&nbsp;&nbsp;&nbsp;<fmt:message key="hint.flowsteps.3" />&nbsp;&nbsp;&nbsp;</span>
        </li>
        <li>
            <span class="last">&nbsp;&nbsp;&nbsp;<fmt:message key="hint.flowsteps.4" />&nbsp;&nbsp;&nbsp;</span>
        </li>
    </ol>
</div>
<div>
	    <ul style="padding:30px 0 0;">
                <li class="alert alert-success  fade in" style="margin-right: 28px;">
                    <fmt:message key="hint.password.email.send.success.tipinfo" ><fmt:param value="${userForm.email}" /></fmt:message>
                </li>
        </ul>
	    <div class="send_helps">
	        <h4><fmt:message key="hint.password.send.receive.fail" /></h4>
	        <p><fmt:message key="hint.password.send.send.retry" /></p>
	    </div>
    <%-- </form:form> --%>
</div>