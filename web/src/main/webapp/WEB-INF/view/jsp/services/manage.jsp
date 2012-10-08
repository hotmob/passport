<%@ include file="/WEB-INF/view/jsp/common/taglibs.jsp"%>
<head>
<title><fmt:message key="service.manager" /></title>
<meta name="menu" content="AdminMenu" />
  <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.5/jquery-ui.min.js"></script>
  <script type="text/javascript" src="<c:url value='${appConfig["resourcesUri"]}statics/scripts/cas.js'/>"></script>

  <style type="text/css">
  #nav-main #${pageTitle} span {
      background:#fff;
      color: #000;
  }
@media screen {
    div#container {width:100%; min-width:952px; margin:0; padding:0;}
    table#headerTable {width:100%; min-width:952px; background:#999; margin:0; padding:0; border:0; border-collapse:collapse;}
    div.tableWrapper {width:100%; min-width:952px; max-height:250px; overflow:auto; overflow-x:hidden;}
        table#scrollTable {width:100%; min-width:935px;}
            table#scrollTable thead {display:none;}
                table#headerTable th, table#scrollTable td {padding:0 5px; border:0;}
                table#scrollTable td { border-bottom:1px solid #eee;}
                table#headerTable th {height:38px; border:0 !important;}

                th.th1, td.td1 {width:200px; overflow:hidden;}
                th.th2, td.td2 {overflow:hidden;}
                th.th3, td.td3 {width:150px}
                th.th4, td.td4 {width:150px}
                th.th5, td.td5 {width:150px}
                th.th6, td.td6 {width:70px; text-align:right !important;}
                th.th7, td.td7 {width:102px; text-align:right !important;}
                td.td7 {width:85px;}

                .hint {margin-left:9.5em; margin-bottom:1em; line-height:1.5;}
                .actions {margin:1.5em 0;}
                #allowedAttributes {height:150px;}
                }
}
</style>
</head>
<div class="span10">
    <c:if test="${fn:length(services) eq 0}">
        <div id="msg" class="alert alert-error fade in"><p><spring:message code="management.services.service.warn" arguments="${defaultServiceUrl}" /></p></div>
    </c:if>

	<c:if test="${not empty param.status}">
		<div id="msg" class="alert alert-success fade in"><spring:message code="management.services.status.${param.status}" arguments="${param.serviceName}" /></div>
	</c:if>

      <table id="headerTable" class="headerTable">
			<tr>
				<th class="th1"><spring:message code="management.services.manage.label.name" /></th>
				<th class="th2"><spring:message code="management.services.manage.label.serviceUrl" /></th>
				<th class="th3 ac"><spring:message code="management.services.manage.label.enabled" /></th>
				<th class="th4 ac"><spring:message code="management.services.manage.label.allowedToProxy" /></th>
				<th class="th5 ac"><spring:message code="management.services.manage.label.ssoParticipant" /></th>
				<th class="th6">&nbsp;</th>
				<th class="th7">&nbsp;</th>
			</tr>
		</table>
	
	
	<div id="tableWrapper" class="tableWrapper">
		<table id="scrollTable" class="scrollTable highlight large">
         <thead>
			<tr>
				<th><spring:message code="management.services.manage.label.name" /></th>
				<th><spring:message code="management.services.manage.label.serviceUrl" /></th>
				<th><spring:message code="management.services.manage.label.enabled" /></th>
				<th><spring:message code="management.services.manage.label.allowedToProxy" /></th>
				<th><spring:message code="management.services.manage.label.ssoParticipant" /></th>
				<th>&nbsp;</th>
				<th>&nbsp;</th>
			</tr>
			</thead>
			
			<tbody>
		<c:forEach items="${services}" var="service" varStatus="status">
		<tr id="row${status.index}"${param.id eq service.id ? ' class="added"' : ''}>
			<td id="${service.id}" class="td1">${service.name}</td>
			<td class="td2">${fn:length(service.serviceId) < 50 ? service.serviceId : fn:substring(service.serviceId, 0, 50)}</td>
			<td class="ac td3"><img src="<c:url value='${appConfig["resourcesUri"]}statics/images/services/${service.enabled}.gif'/>" alt="${service.enabled ? 'Enabled' : 'Disabled'}" /></td>
			<td class="ac td4"><img src="<c:url value='${appConfig["resourcesUri"]}statics/images/services/${service.allowedToProxy}.gif'/>" alt="${service.allowedToProxy ? 'Allowed to Proxy' : 'Not Allowed to Proxy'}" /></td>
			<td class="ac td5"><img src="<c:url value='${appConfig["resourcesUri"]}statics/images/services/${service.ssoEnabled}.gif'/>" alt="${service.ssoEnabled ? 'SSO Enabled' : 'SSO Disabled'}" /></td>

			<td class="td6" id="edit${status.index}"><a href="/manager/service/edit?id=${service.id}" class="edit"><spring:message code="management.services.manage.action.edit" /></a></td>
			<td class="td7" id="delete${status.index}"><a href="#" class="del" onclick="swapButtonsForConfirm('${status.index}','${service.id}'); return false;"><spring:message code="management.services.manage.action.delete" /></a></td>
		</tr>
		</c:forEach>
			</tbody>
		</table>
	</div>
<div class="add"><a href="add"><span style="text-transform: lowercase;"><spring:message code="addServiceView" /></span></a></div>
</div>