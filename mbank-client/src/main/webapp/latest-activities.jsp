<script type="text/javascript">
	$(document).ready(function() {
		$.ajax({
			type : "GET",
			dataType : "json",
			url : "/mbank-client/limit-acts",
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
				window.setTimeout(update, 300000); // 5 mins
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				$("#latestActivities").html('Timeout contacting server..');
				window.setTimeout(update, 600000); // 10 mins
			}
		});
	});
</script>
<h1>Hot Information</h1>
<h2 class="rss">News</h2>
<div class="news">
	<a href="/mbank-client/news"> <img alt="news"
		src="/mbank-client/resources/images/rss-icon.png" width="20"> <span>News</span>
	</a>
</div>
<h2>Latest Activities</h2>
<div id="latestActivities"></div>
<a href="/mbank-client/activities">View All Activities</a>