$(document).ready(function() {
	$('#main').click(function() {
		hideContainer();
	});

	$("#action .action-link").click(function(event) {
		hideContainer();
		$(this).css("background-position", "0 0");
		$(this).parent().css("background-color", "#3b3b3b");
		$(this).siblings(".action-container").fadeIn(0);
		event.stopPropagation();
	});

	$('#content table.clickable tr').bind('click', function() {
		var href = $(this).children(".hidden").children("a").attr('href');
		if (href)
			window.location = href;
	});

	$("#removeClient a").click(function(event) {
		event.preventDefault();
		hideContainer();
		showConfirm();
		event.stopPropagation();
		href = $(this).attr("href");
		$("#confirm a").click(function() {
			$("#confirm").css("display", "none");
			if ($(this).attr("class") == "yes") {
				window.location = href;
			}
		});
	});

	autoSave = false;
});

function showConfirm() {
	$("#confirm").css("display", "block");
}

function save(name, value) {
	// TODO fix to one load per page
	if (oldClient[name] != value) {
		$.post($("#edit").attr("action"), $("#edit").serialize());
		oldClient[name] = value;
	}
}

function hideContainer() {
	$("#action .action-container").fadeOut(0);
	var el = $("#action .action-link");
	el.css("background-position", "0 -17px");
	el.parent().css("background-color", "#E5E5DB");
}
