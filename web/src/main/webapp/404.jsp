<%@ include file="/WEB-INF/view/jsp/common/taglibs.jsp"%>
<page:applyDecorator name="default">

<head>
    <title><fmt:message key="404.title"/></title>
    <meta name="heading" content="<fmt:message key='404.title'/>"/>
</head>

<p>
    <fmt:message key="404.message">
        <fmt:param><c:url value="/home"/></fmt:param>
    </fmt:message>
</p>

</page:applyDecorator>