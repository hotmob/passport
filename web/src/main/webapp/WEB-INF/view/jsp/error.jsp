<%@ include file="/WEB-INF/view/jsp/common/taglibs.jsp" %>
<head>
    <title><fmt:message key="mainMenu.heading"/></title>
    <meta name="heading" content="<fmt:message key='403.title'/>"/>
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
                <h3><spring:message code="403.title" /></h3>
                <div class="logined">
                    
                </div>
                <div class="login_manage">
                    <h4><fmt:message key="403.message"/></h4>
                </div>
                <div class="clear"></div>
            </div>
            <div class="clear"></div>
        </div>
    </div>
</body>