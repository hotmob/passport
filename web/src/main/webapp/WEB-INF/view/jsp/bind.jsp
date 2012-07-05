<%@ include file="common/taglibs.jsp"%>
<head>
<title><fmt:message key="signup.title" /></title>
<meta name="heading" content="<fmt:message key='signup.heading'/>" />
<meta name="menu" content="Signup" />
</head>
<body id="signup" />
<div class="main wrapper">
    <div class="separator"></div>
    <div class="reg_con">
        <form:form  id="userForm" commandName="userForm" method="post" onsubmit="return validateUserForm(this)">
	        <div class="userinfo">
	            <dl>
	                <dd class="avatar">
	                   <c:choose>
	                       <c:when test="${not empty userForm.avataUrl}">
	                           <img src="${userForm.avataUrl}">
	                       </c:when>
	                       <c:when test="${not empty avataUrl}">
                               <img src="${avataUrl}">
                           </c:when>
	                       <c:otherwise>
	                           <img src="/statics/images/userface.gif">
	                       </c:otherwise>
	                   </c:choose>
	                </dd>
	                <dt>Hi, ${userForm.firstName}${userForm.lastName}</dt>
	                <dd><fmt:message key="bind.message"><fmt:param value="${userForm.providerId}" /></fmt:message></dd>
	                <dd><fmt:message key="bind.showMore"/></dd>
	            </dl>
	        </div>
	        <div class="bangding_tit"><fmt:message key="bind.heading"/></div>
	        <div class="bind_tit_info"><fmt:message key="bind.heading.info"/></div>
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
            <v:javascript formName="userForm" staticJavascript="false"/>
            <script type="text/javascript" src="<c:url value='${appConfig["resourcesUri"]}statics/scripts/validator.jsp' />"></script>
        </c:set>
    </div>
</div>