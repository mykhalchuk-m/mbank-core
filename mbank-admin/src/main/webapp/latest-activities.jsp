<script type="text/javascript">
	$(document).ready(function() {
		$.ajax({
			type : "GET",
			dataType : "json",
			url : "/mbank-admin/limit-acts",
			timeout : 2000,
			success : function(data) {
				var acts = "";
				for ( var index in data.activities) {
					var a = data.activities[index];
					acts += "<div>";
					acts += "<h4>" + a.client + "</h4>";
					acts += "<h5>" + a.activityDate + "</h5>";
					acts += "<p>Amount: " + a.amount + ", ";
					acts += "payed commission: " + a.commission + " ";
					acts += "for " + a.description + "</p>";
					acts += "</div>";
				}
				$("#latestActivities").html(acts);
				window.setTimeout(update, 300000); // 3 mins
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				$("#latestActivities").html('Timeout contacting server..');
				window.setTimeout(update, 600000); // 6 mins
			}
		});
	});
</script>

<h1>Latest Activities</h1>
<div id="latestActivities"></div>
<a href="/mbank-admin/activities">View All Activities</a>