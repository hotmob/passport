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
        <div class="userinfo">
            <dl>
                <dd class="pic"><img src="/statics/images/userface.gif"></dd>
                <dt>Hi, hotmob</dt>
                <dd><fmt:message key="bind.message"><fmt:param value="QQ" /></fmt:message></dd>
            </dl>
        </div>
        <div class="bangding_tit"><fmt:message key="bind.heading"/></div>
        <form:form  id="signupForm" commandName="signupForm" method="post" onsubmit="return validateSignupForm(this)">
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
                <li class="btn_con">
                    <form:hidden path="service" value="" />
                    <input type="submit" class="btn btn-success" name="save" value="<fmt:message key="bind.title" />" tabindex="3" />
                </li>
            </ul>
        </form:form>
        <c:set var="scripts" scope="request">
            <v:javascript formName="signupForm" staticJavascript="false"/>
            <script type="text/javascript" src="<c:url value='${appConfig["resourcesUri"]}statics/scripts/validator.jsp' />"></script>
        </c:set>
    </div>
</div>