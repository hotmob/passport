<%@ include file="/WEB-INF/view/jsp/common/taglibs.jsp"%>
<head>
<title><fmt:message key="login.title" /></title>
<meta name="heading" content="<fmt:message key='login.heading'/>" />
<meta name="menu" content="Login" />
</head>
<body id="login">
    <div class="main wrapper">
        <div class="sidebar">
            <div class="side_bg1"></div>
        </div>
        <div class="login_con">
            <div id="span" class="login">
                <form:form method="post" id="fm1" commandName="${commandName}" htmlEscape="true">
                    <h3>
                        <fmt:message key="login.heading" />
                    </h3>
                    <ul>
                        <li><label for="username"><fmt:message key="label.username" /><fmt:message key="label.double.colon"/></label>
                            <c:if test="${not empty sessionScope.openIdLocalId}">
                                <strong>${sessionScope.openIdLocalId}</strong>
                                <input type="hidden" id="username" name="username" value="${sessionScope.openIdLocalId}" />
                            </c:if> <c:if test="${empty sessionScope.openIdLocalId}">
                                <spring:message code="screen.welcome.label.netid.accesskey" var="userNameAccessKey" />
                                <c:set var="tipinfo"><fmt:message key="login.tipinfo" /></c:set>
                                <form:input cssClass="plubic_input1" id="username" size="25" tabindex="1"
                                    accesskey="${userNameAccessKey}" value="${tipinfo}" tipinfo="${tipinfo}" path="username" autocomplete="false" htmlEscape="true" />
                            </c:if>
                        </li>
                        <li><label for="password"><fmt:message key="label.password" /><fmt:message key="label.double.colon"/></label>
                             <spring:message code="screen.welcome.label.password.accesskey" var="passwordAccessKey" />
                             <form:password cssClass="plubic_input1 text_password" id="password" size="25" tabindex="2" path="password"
                                accesskey="${passwordAccessKey}" htmlEscape="true" autocomplete="off" />
                        </li>
                        <form:errors path="*" id="msg" cssClass="error_tips" element="li" />
                        <li class="checkbox">
                            <input type="checkbox" id="rememberMe" name="rememberMe" tabindex="3"/>
                            <label for="rememberMe" class="checkbox"><fmt:message key="login.rememberMe"/></label><a href="/hint" ><fmt:message key="login.passwordHint"/></a>
                        </li>
                        <li class="btn_login">
                            <input type="hidden" name="lt" value="${loginTicket}" />
                            <input type="hidden" name="execution" value="${flowExecutionKey}" />
                            <input type="hidden" name="_eventId" value="submit" />
                            <input type="submit" class="btn btn-primary" name="login" value="<fmt:message key="login.heading" />" tabindex="4"/>
                            <input type="button" class="btn btn-toolbar" name="signup" value="<fmt:message key="signup.heading" />" tabindex="5" onclick="javascript:getRedirect('/signup', 'service');"/>
                            <%-- <fieldset class="form-actions">
                                <input type="submit" class="btn btn-primary" name="login" value="<fmt:message key='button.login'/>" tabindex="4"/>
                                <input class="btn_log" name="submit" accesskey="l" value="" tabindex="4" type="submit" />
                            </fieldset>
                            <input class="btn_reg" name="signup" accesskey="l" value="" tabindex="4" type="button" onclick="window.location.href='/signup'" > --%>
                        </li>
                    </ul>
                </form:form>
            </div>
            <div class="clear"></div>
            <div class="login_cooperate">
                <h4><fmt:message key="login.other" /></h4>
                <ul>
                    <li title='<fmt:message key="login.other.sina"/>' class="oauth_weibo">
                        <form action="<c:url value="/signin/weibo" />" method="POST">
                            <button type="submit" ></button>
                        </form>
                    </li>
                    <li title="<fmt:message key="login.other.tencent"/>"  class="oauth_txwb">
                        <form action="<c:url value="/signin/txwb" />" method="POST">
                            <button type="submit" ></button>
                        </form>
                    </li>
                    <li title="Facebook" class="oauth_facebook">
                        <form action="<c:url value="/signin/facebook" />" method="POST">
                            <input type="hidden" name="scope" value="email,publish_stream,offline_access" />
                            <button type="submit" ></button>       
                        </form>
                    </li>
                    <li title="Twitter" class="oauth_twitter">
                        <form id="tw_signin" action="<c:url value="/signin/twitter"/>" method="POST">
                            <button type="submit" ></button>
                        </form>
                    </li>
                    <li title="Linkedin" class="oauth_linkedin">
                        <form id="li_signin" action="<c:url value="/signin/linkedin"/>" method="POST">
                            <button type="submit" ></button>
                        </form>
                    </li>
                </ul>
            </div>
        </div>
        <div class="clear"></div>
    </div>
</body>