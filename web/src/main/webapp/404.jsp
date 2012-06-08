<%@ include file="/WEB-INF/view/jsp/common/taglibs.jsp"%>
<page:applyDecorator name="default">

<head>
    <title><fmt:message key="404.title"/></title>
    <meta name="heading" content="<fmt:message key='404.title'/>"/>
</head>

<p>
    <fmt:message key="404.message">
        <fmt:param><c:url value="/"/></fmt:param>
    </fmt:message>
</p>
<p style="text-align: center; margin-top: 20px">
    <img src="<c:url value='${appConfig["resourcesUri"]}statics/images/404.jpg'/>" alt="<fmt:message key="404.title"/>" />
</p>
</page:applyDecorator>