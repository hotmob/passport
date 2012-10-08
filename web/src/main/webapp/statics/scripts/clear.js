//write service value
$(document).ready(function(){
	var service = getUrlParam("service");
	if(service != null) {
		if($(this).val() == "" && service.length > 7){
			$("#service").val(service);
			$("#cancelUri").attr('href', $("#cancelUri").attr('href') + "?service=" + service);
		};
	};
});
// clear error tip
$(".onError").each(function(){
	if($(this).is(":visible")){
		$("#"+$(this).attr('id').substring(0, $(this).attr('id').length-5)).hide();
	}
});