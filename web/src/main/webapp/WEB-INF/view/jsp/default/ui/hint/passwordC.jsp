<%@ include file="/WEB-INF/view/jsp/common/taglibs.jsp" %>
<%@ page import="java.util.List" %>
<h3 class="get_password_tit"></h3>
<div class="flowsteps">
    <ol class="num4">
        <li class="done">
            <span class="first">&nbsp;&nbsp;&nbsp;<fmt:message key="hint.flowsteps.1" />&nbsp;&nbsp;&nbsp;</span>
        </li>
        <li class="done">
            <span>&nbsp;&nbsp;&nbsp;<fmt:message key="hint.flowsteps.2" />&nbsp;&nbsp;&nbsp;</span>
        </li>
        <li class="current">
            <span>&nbsp;&nbsp;&nbsp;<fmt:message key="hint.flowsteps.3" />&nbsp;&nbsp;&nbsp;</span>
        </li>
        <li class="next">
            <span class="last">&nbsp;&nbsp;&nbsp;<fmt:message key="hint.flowsteps.4" />&nbsp;&nbsp;&nbsp;</span>
        </li>
    </ol>
</div>
<div>
    <c:set var="actionUrl">hint?<%=request.getQueryString()%></c:set>
    <form:form id="hintForm" commandName="hintForm" method="post" action="${actionUrl}" onsubmit="return validateHintForm(this)">
        <ul style="padding:30px 0 0;">
           <c:if test="${not empty errorMessages}">
               <li>
		          <p><fmt:message key="errors.token" /></p>
	           </li>
	       </c:if>
           <c:if test="${errorMessages == null}">
	            <li>
	               <label><fmt:message key="label.username" /><fmt:message key="label.double.colon" /></label><c:out value="${hintForm.username}" />
	            </li>
	            <li>
                    <label class="desc" for="password"><fmt:message key="label.password"/><fmt:message key="label.double.colon"/></label>
                    <form:password path="password" id="password" cssClass="plubic_input1 text_password" showPassword="true" tabindex="1"/>
                    <div class="reg_tips">
                        <form:errors id="password1TipError" path="password" cssClass="onError" />
                        <span id="password1Tip" class="onShow"><fmt:message key="signup.help.password"/></span>
                    </div>
                </li>
                <li>
                    <label class="desc" for="confirmPassword"><fmt:message key="label.password.confirm"/><fmt:message key="label.double.colon"/></label>
                    <form:password path="confirmPassword" id="confirmPassword" cssClass="plubic_input1 text_password" showPassword="true" tabindex="2"/>
                    <div class="reg_tips">
                        <form:errors id="password2TipError" path="confirmPassword" cssClass="onError" />
                        <span id="password2Tip" class="onShow"><fmt:message key="signup.help.repeat"/></span>
                    </div>
                </li>
                <li>
                    <label class="desc" for="captcha"><fmt:message key="label.captcha"/><fmt:message key="label.double.colon"/></label>
                    <form:input path="captcha" id="captcha"  cssClass="plubic_input2" tabindex="3" />
                    <div class="reg_tips">
                        <span class="avilid"><img id="captchaImg" src="captcha.jpg" ></span>
                        <form:errors id="captchaTipError" path="captcha" cssClass="onError" />
                        <span id="captchaTip">&nbsp;<fmt:message key="signup.help.captcha.tipinfo"/></span>&nbsp;
                        <a href="javascript:;" onClick="captchaImg.src='captcha.jpg?'+new Date().getTime()"><fmt:message key="signup.help.captcha.replace"/></a> 
                    </div>
                </li>
                <li class="btn_con">
                    <form:hidden path="step"/>
                    <form:hidden path="version" />
                    <form:hidden path="oldPassword" />
                    <input type="submit" class="btn btn-success" name="save" value="<fmt:message key="label.flows.step.next" />" tabindex="4" />
                </li>
             </c:if>
        </ul>
    </form:form>
    <c:set var="scripts" scope="request">
        <v:javascript formName="hintForm" staticJavascript="false"/>
        <script type="text/javascript" src="<c:url value='${appConfig["resourcesUri"]}statics/scripts/validator.jsp' />"></script>
    </c:set>
</div>