<%@ include file="common/taglibs.jsp" %>
<head>
    <title><fmt:message key="mainMenu.heading"/></title>
    <meta name="heading" content="<fmt:message key='mainMenu.heading'/>"/>
    <meta name="menu" content="MainMenu"/>
    <!-- <link rel="stylesheet" type="text/css" media="all" href="/statics/styles/default/theme.css" /> -->
</head>
<body id="login">
    <div class="main wrapper">
        <div class="sidebar">
            <div class="side_bg1"></div>
        </div>
        <div class="login_con">
            <div class="login" id="login">
                <h3><spring:message code="screen.success.header" /></h3>
                <div class="logined">
                    <dl>
                        <dd class="pic">
                            <img src="/statics/images/userface.gif">
                        </dd>
                        <dt>
                            <strong><%=request.getUserPrincipal().getName()%></strong> <span><fmt:message key="mainMenu.heading" /></span>
                        </dt>
<%--                         <dt><%=PropertyUtil.getPersonAttributeRepository().getUserAttributes(request.getUserPrincipal().getName()).get("mail")%></dt> --%>
                    </dl>
                </div>
                <div class="login_manage">
                    <h4><fmt:message key="mainMenu.message"/></h4>
                    <p>
                            <a href="<c:url value='/userform'/>"><fmt:message key="menu.user"/></a>&nbsp;&nbsp;&nbsp;&nbsp;
                            <a href="<c:url value='/fileupload'/>"><fmt:message key="menu.selectFile"/></a>&nbsp;&nbsp;&nbsp;&nbsp;
                            <a href="/logout"><fmt:message key="user.logout"/></a>&nbsp;&nbsp;&nbsp;&nbsp;
                    </p>
                </div>
                <div class="login_record">
	                <h4><fmt:message key="home.login.record"/></h4>
	                <p><a href="#">http://bbs.766.com/thread-5958743-1-1.html</a></p>
	                <p><a href="#">http://wow.766.com/</a></p>
	                <p><a href="#">http://bbs.766.com/thread-5958743-1-1.html</a></p>
                    <p><a href="#">http://wow.766.com/</a></p>
                </div>
                <div class="clear"></div>
            </div>
            <div class="clear"></div>
        </div>
    </div>
</body>