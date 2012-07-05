<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/view/jsp/common/taglibs.jsp"%>
<html lang="zh">
	<head>
		<%@ include file="/WEB-INF/view/jsp/common/meta.jsp" %>
		<title><decorator:title/> | <fmt:message key="webapp.name"/></title>
		<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${appConfig["resourcesUri"]}statics/styles/${appConfig["csstheme"]}/theme.css'/>"/>
		<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${appConfig["resourcesUri"]}statics/styles/lib/bootstrap.min.css'/>" />
		<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${appConfig["resourcesUri"]}statics/styles/lib/bootstrap-responsive.min.css'/>" />
		<decorator:head/>
	</head>
	<body<decorator:getProperty property="body.id" writeEntireProperty="true"/><decorator:getProperty property="body.class" writeEntireProperty="true"/>>
	    <c:set var="currentMenu" scope="request"><decorator:getProperty property="meta.menu"/></c:set>
	    <div class="navbar navbar-fixed-top">
	        <div class="navbar-inner">
	            <div class="container-fluid">
	                <jsp:include page="/WEB-INF/view/jsp/common/menu.jsp"/>
	                <a class="brand" href="<c:url value='/'/>">Passport</a>
	            </div>
	        </div>
	    </div>
	
	    <div class="container-fluid">
	        <%@ include file="/WEB-INF/view/jsp/common/messages.jsp" %>
	        <div class="row-fluid">
	            <decorator:body/>
	            <c:if test="${currentMenu == 'AdminMenu'}">
	                <div class="span2">
		                <menu:useMenuDisplayer name="Velocity" config="navlistMenu.vm" permissions="rolesAdapter">
		                    <menu:displayMenu name="AdminMenu"/>
		                </menu:useMenuDisplayer>
	                </div>
	            </c:if>
	            <c:if test="${currentMenu == 'EmployeeMenu'}">
                    <div class="span2">
                        <menu:useMenuDisplayer name="Velocity" config="navlistMenu.vm" permissions="rolesAdapter">
                            <menu:displayMenu name="EmployeeMenu"/>
                        </menu:useMenuDisplayer>
                    </div>
                </c:if>
	        </div>
	    </div>
	
	    <div id="footer" class="footer"><jsp:include page="/WEB-INF/view/jsp/common/footer.jsp"/></div>
		<script type="text/javascript" src="<c:url value='${appConfig["resourcesUri"]}statics/scripts/lib/jquery.min.js'/>"></script>
		<script type="text/javascript" src="<c:url value='${appConfig["resourcesUri"]}statics/scripts/lib/bootstrap.min.js'/>"></script>
		<script type="text/javascript" src="<c:url value='${appConfig["resourcesUri"]}statics/scripts/lib/jquery.cookie.js'/>"></script>
		<script type="text/javascript" src="<c:url value='${appConfig["resourcesUri"]}statics/scripts/script.js'/>"></script>
		<script type="text/javascript" src="<c:url value='${appConfig["resourcesUri"]}statics/scripts/clear.js'/>"></script>
		<script type="text/javascript" src="<c:url value='${appConfig["resourcesUri"]}statics/scripts/analytics.js'/>"></script>
		<%= (request.getAttribute("scripts") != null) ?  request.getAttribute("scripts") : "" %>
	</body>
</html>