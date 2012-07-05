<%@ include file="taglibs.jsp"%>
<menu:useMenuDisplayer name="Velocity" config="navbarMenu.vm" permissions="rolesAdapter">
<ul class="nav">
    <c:if test="${empty pageContext.request.remoteUser}">
        <menu:displayMenu name="Login"/>
        <menu:displayMenu name="Signup"/>
    </c:if>
    <menu:displayMenu name="MainMenu"/>
    <menu:displayMenu name="UserMenu"/>
    <menu:displayMenu name="AdminMenu"/>
    <menu:displayMenu name="EmployeeMenu"/>
    <li class="divider-vertical"></li>
    <menu:displayMenu name="Logout"/>
</ul>
</menu:useMenuDisplayer>
