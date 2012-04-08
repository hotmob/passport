<%@ include file="common/taglibs.jsp"%>
<head>
<title><fmt:message key="hint.title" /></title>
<meta name="heading" content="<fmt:message key='hint.heading'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value='${appConfig["resourcesUri"]}statics/styles/${appConfig["csstheme"]}/flowsteps.css'/>"/>
</head>
<body id="hint" />
<div class="main wrapper">
    <spring:bind path="hintForm.*">
        <c:if test="${not empty status.errorMessages}">
            <div class="alert alert-error fade in">
                <a href="#" data-dismiss="alert" class="close">&times;</a>
                <c:forEach var="error" items="${status.errorMessages}">
                    <c:out value="${error}" escapeXml="false"/><br/>
                </c:forEach>
            </div>
        </c:if>
    </spring:bind>
    <div class="hint_con">
        <h3 class="get_password_tit"></h3>
        <c:choose>
            <c:when test="${hintForm.getStep() eq 1}">
                <%@ include file="default/ui/hint/passwordB.jsp"%>
            </c:when>
            <c:when test="${hintForm.getStep() eq 2}">
                <%@ include file="default/ui/hint/passwordC.jsp"%>
            </c:when>
            <c:when test="${hintForm.getStep() eq 3}">
                <%@ include file="default/ui/hint/passwordD.jsp"%>
            </c:when>
            <c:otherwise>
                <%@ include file="default/ui/hint/passwordA.jsp"%>
            </c:otherwise>      
        </c:choose>
    </div>
</div>