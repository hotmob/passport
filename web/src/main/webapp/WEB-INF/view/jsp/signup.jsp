<%@ include file="common/taglibs.jsp"%>
<head>
<title><fmt:message key="signup.title" /></title>
<meta name="heading" content="<fmt:message key='signup.heading'/>" />
<meta name="menu" content="Signup" />
</head>
<body id="signup" />
<div class="main wrapper">
    <spring:bind path="signupForm.*">
        <c:if test="${not empty status.errorMessages}">
            <div class="alert alert-error fade in">
                <a href="#" data-dismiss="alert" class="close">&times;</a>
                <c:forEach var="error" items="${status.errorMessages}">
                    <c:out value="${error}" escapeXml="false"/><br/>
                </c:forEach>
            </div>
        </c:if>
    </spring:bind>
	<div class="separator"></div>
	<div class="reg_con">
		<form:form  id="signupForm" commandName="signupForm" method="post" onsubmit="return validateSignupForm(this)">
			<h3 class="reg_tit"></h3>
			<ul>
				<li>
					<label class="desc" for="username"><fmt:message key="label.username"/><fmt:message key="label.double.colon"/></label>
					<form:input path="username" id="username" cssClass="plubic_input1" tabindex="1"/>
					<div class="reg_tips">
						<form:errors id="usernameTipError" path="username" cssClass="onError" />
						<span id="usernameTip" class="onShow" ><fmt:message key="signup.help.username"/></span>
					</div>
				</li>
				<li>
					<label class="desc" for="password"><fmt:message key="label.password"/><fmt:message key="label.double.colon"/></label>
					<form:password path="password" id="password" cssClass="plubic_input1 text_password" showPassword="true" tabindex="2"/>
					<div class="reg_tips">
						<form:errors id="password1TipError" path="password" cssClass="onError" />
						<span id="password1Tip" class="onShow"><fmt:message key="signup.help.password"/></span>
					</div>
				</li>
				<li>
					<label class="desc" for="confirmPassword"><fmt:message key="label.password.confirm"/><fmt:message key="label.double.colon"/></label>
					<form:password path="confirmPassword" id="confirmPassword" cssClass="plubic_input1 text_password" showPassword="true" tabindex="3"/>
					<div class="reg_tips">
						<form:errors id="password2TipError" path="confirmPassword" cssClass="onError" />
						<span id="password2Tip" class="onShow"><fmt:message key="signup.help.repeat"/></span>
					</div>
				</li>
				<li>
					<label class="desc" for="email"><fmt:message key="label.email"/><fmt:message key="label.double.colon"/></label>
					<form:input path="email" id="email" cssClass="plubic_input1" tabindex="4"/>
					<div class="reg_tips">
						<form:errors id="emailTipError" path="email" cssClass="onError" />
						<span id="emailTip" class="onShow"><fmt:message key="signup.help.email"/></span>
					</div>
				</li>
				<li>
					<label class="desc" for="captcha"><fmt:message key="label.captcha"/><fmt:message key="label.double.colon"/></label>
					<form:input path="captcha" id="captcha"  cssClass="plubic_input2" tabindex="5" />
					<div class="reg_tips">
						<span class="avilid"><img id="captchaImg" src="captcha.jpg" ></span>
						<form:errors id="captchaTipError" path="captcha" cssClass="onError" />
						<span id="captchaTip">&nbsp;<fmt:message key="signup.help.captcha.tipinfo"/></span>&nbsp;
						<a href="javascript:;" onClick="captchaImg.src='captcha.jpg?'+new Date().getTime()"><fmt:message key="signup.help.captcha.replace"/></a> 
					</div>
				</li>
				<li class="btn_con">
				    <form:checkbox path="enabled" class="input_checkbox" value="true" />
					<label class="checkbox"><fmt:message key="signup.help.terms.title"/></label>
				</li>
				<li class="btn_con">
				    <form:hidden path="service" value="" />
					<input type="submit" class="btn btn-success" name="save" value="<fmt:message key="signup.heading" />" tabindex="6" />
					<fmt:message key="signup.help.exist.user.tipinfo"/>&nbsp;<a id="cancelUri" href="/login" ><fmt:message key="signup.help.exist.user.login"/></a>
				</li>
			</ul>
		</form:form>
		<c:set var="scripts" scope="request">
			<v:javascript formName="signupForm" staticJavascript="false"/>
			<script type="text/javascript" src="<c:url value='${appConfig["resourcesUri"]}statics/scripts/validator.jsp' />"></script>
		</c:set>
	</div>
</div>