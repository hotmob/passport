<%@ include file="/WEB-INF/view/jsp/common/taglibs.jsp" %>
<h3 class="get_password_tit"></h3>
<div class="flowsteps">
    <ol class="num4">
        <li class="current">
            <span class="first">&nbsp;&nbsp;&nbsp;<fmt:message key="hint.flowsteps.1" />&nbsp;&nbsp;&nbsp;</span>
        </li>
        <li class="next">
            <span>&nbsp;&nbsp;&nbsp;<fmt:message key="hint.flowsteps.2" />&nbsp;&nbsp;&nbsp;</span>
        </li>
        <li>
            <span>&nbsp;&nbsp;&nbsp;<fmt:message key="hint.flowsteps.3" />&nbsp;&nbsp;&nbsp;</span>
        </li>
        <li>
            <span class="last">&nbsp;&nbsp;&nbsp;<fmt:message key="hint.flowsteps.4" />&nbsp;&nbsp;&nbsp;</span>
        </li>
    </ol>
</div>
<div>
    <form:form id="userForm" commandName="userForm" method="post" action="hint" onsubmit="return validateUserForm(this)">
        <ul>
            <li>
                <label class="desc" for="username"><fmt:message key="label.username" /><fmt:message key="label.double.colon" /></label>
                <c:set var="tipinfo"><fmt:message key="login.tipinfo" /></c:set>
                <form:input path="username" id="username" cssClass="plubic_input1" tipinfo="${tipinfo}" value="${tipinfo}" tabindex="1" />
                <div class="reg_tips">
                    <form:errors id="usernameTipError" path="username" cssClass="onError" />
                </div>
            </li>
            <li>
                <label class="desc" for="captcha"><fmt:message key="label.captcha" /><fmt:message key="label.double.colon" /></label>
                <form:input path="captcha" id="captcha" cssClass="plubic_input2" tabindex="2" />
                <div class="reg_tips">
                    <span class="avilid"><img id="captchaImg" src="captcha.jpg"></span>
                    <form:errors id="captchaTipError" path="captcha" cssClass="onError" />
                    <span id="captchaTip">&nbsp;<fmt:message key="signup.help.captcha.tipinfo" /></span>&nbsp; 
                    <a href="javascript:;" onClick="captchaImg.src='captcha.jpg?'+new Date().getTime()"><fmt:message key="signup.help.captcha.replace" /></a>
                </div>
            </li>
            <li class="btn_con">
                <form:hidden path="password" value="hidden"/>
                <form:hidden path="confirmPassword" value="hidden"/>
                <form:hidden path="email" value="hidden@766.com"/>
                <form:hidden path="enabled" value="true"/>
	            <form:hidden path="step"/>
	            <input type="submit" class="btn btn-info" name="save" value="<fmt:message key="label.flows.step.next" />"  tabindex="3" />
            </li>
        </ul>
    </form:form>
    <c:set var="scripts" scope="request">
        <v:javascript formName="userForm" staticJavascript="false"/>
        <script type="text/javascript" src="<c:url value='${appConfig["resourcesUri"]}statics/scripts/validator.jsp' />"></script>
    </c:set>
</div>