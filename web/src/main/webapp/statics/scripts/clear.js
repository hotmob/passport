// clear html element value
$(document).ready(function(){
	$(".plubic_input1,.plubic_input2").focus(function(){
		$(this).addClass("plubic_input_on");
		if($(this).val() ==$(this).attr("tipinfo")){
			$(this).val("");
		}
	}).blur(function(){
		$(this).removeClass("plubic_input_on");	
		if($(this).val() == ""){
			if($(this).attr("tipinfo")){
				$(this).val($(this).attr("tipinfo"));
			}
		}
	});
});
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