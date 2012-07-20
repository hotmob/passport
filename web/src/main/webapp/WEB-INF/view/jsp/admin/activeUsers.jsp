<%@ include file="/WEB-INF/view/jsp/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="activeUsers.title"/></title>
    <meta name="menu" content="AdminMenu"/>
</head>
<body id="activeUsers"/>

<div class="span10">
    <h2><fmt:message key="activeUsers.heading"/></h2>

    <input type="button" onclick="location.href='../home'" value="<fmt:message key="button.done"/>" class="btn right"/>

    <display:table name="applicationScope.userNames" id="user" cellspacing="0" cellpadding="0" defaultsort="1" class="table table-condensed" pagesize="50" requestURI="">
        <display:column property="username" escapeXml="true" sortable="true" titleKey="user.username" style="width: 25%" url="/userform?from=list" paramId="id" paramProperty="username"/>
        <display:column property="displayName" escapeXml="true" sortable="true" titleKey="activeUsers.displayName" style="width: 34%"/>
        <display:column property="email" sortable="true" titleKey="user.email" style="width: 25%" autolink="true" media="html"/>
        <display:column property="email" titleKey="user.email" media="csv xml excel pdf"/>

        <display:setProperty name="paging.banner.item_name" value="user"/>
        <display:setProperty name="paging.banner.items_name" value="users"/>
    </display:table>
</div>
