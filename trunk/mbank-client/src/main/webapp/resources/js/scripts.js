$(document).ready(function() {
	$("form.activity input[type=submit]").click(function(e) {
		$(this).addClass("qqq");
	});
	
	var stop = true;
	$("form.activity").submit(function(e) {
		if (stop)
			e.preventDefault();
		else 
			return true;
		showConfirm();
		$("#confirm a").click(function() {
			$("#confirm").css("display", "none");
			if ($(this).attr("class") == "yes") {
				stop  = false;
				$("form.activity").append("<input type='hidden' name='action' value='" + $("form.activity .qqq").val() + "'/>");
				$("form.activity").submit();
				$("form.activity .qqq").removeClass("qqq");
			}
		});
	});
});

function showConfirm() {
	$("#confirm").css("display", "block");
}
