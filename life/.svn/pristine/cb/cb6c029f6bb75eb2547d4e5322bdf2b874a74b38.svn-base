<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html dir="ltr" lang="en-US">
<html>
<head>

<script type = "text/javascript" src = "/life/web/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" language="javascript" src="/life/web/js/jquery-ui-1.8.6.custom.min.js"></script>
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
    
<script language="javascript">
google.load('visualization', '1', {'packages':['corechart']});
function processPieChartResponse(data){
	 var response = eval(data);
	 var pieChartData = response.results;

	 $("#jsonText").html(JSON.stringify(data));
	 google.setOnLoadCallback(drawPieChartT(pieChartData));
}
function drawPieChartT(pieChartData){
	
	var pieData = new google.visualization.DataTable({
		cols: pieChartData.cols,
    	rows: pieChartData.rows
    });

	var pieChart = new google.visualization.PieChart(document.getElementById('pieChartT'))
	
	pieChart.draw(pieData, {title:"Element Summary" ,sliceVisibilityThreshold:1/1000000000});
	
}
<% 
out.print("var input = \""+request.getParameter("input")+"\";");
%>
$(function() { 
	$.ajax( {url: "ApiDemo", data: {input : input}, success: processPieChartResponse, dataType: "json", type: "GET" } );
});

</script>
<body>
<p align="center" style="font-size:100;" ><strong>API DEMO</strong></p>
<p> <strong>Json :</strong> </p>
<div id="jsonText"></div>
 <div  id="pieChartT"  style="width:300px;height:300px; padding-left:300px; padding-top:75px;" > </div>
</body>

</html>