<%@ include file="/WEB-INF/view/jsp/common/taglibs.jsp"%>
<page:applyDecorator name="default">

<head>
    <title><fmt:message key="403.title"/></title>
    <meta name="heading" content="<fmt:message key='403.title'/>"/>
</head>

<p>
    <fmt:message key="403.message">
        <fmt:param><c:url value="/"/></fmt:param>
    </fmt:message>
</p>
<p style="text-align: center; margin-top: 20px">
    <img src="<c:url value='${appConfig["resourcesUri"]}statics/images/403.jpg'/>" alt="<fmt:message key="403.title"/>" />
</p>
</page:applyDecorator>