$(document).ready(function() {
	getParticipantsStats();
	getPerturbationStats();
});

function getParticipantsStats(){
	drawChart("participantChart","participant");
}

function getPerturbationStats(){
	drawChart("perturbationChart","perturbation");
}

function drawChart(chartId,csvFile){

		var margin = {top: 10, right: 20, bottom: 55, left: 70},
		    width = 570 - margin.left - margin.right,
		    height = 450 - margin.top - margin.bottom;
		
		var x = d3.scale.ordinal()
		     .rangeRoundBands([0, width], .3, .3);
		
		var y = d3.scale.linear()
		    .rangeRound([height, 0]);
		
		var color = d3.scale.ordinal()
		    .range(["#98abc5", "#8a89a6", "#7b6888", "#6b486b", "#a05d56", "#d0743c", "#ff8c00"]);
		
		var xAxis = d3.svg.axis()
		    .scale(x)
		    .orient("bottom");
		
		var yAxis = d3.svg.axis()
		    .scale(y)
		    .orient("left")
		
		var svg = d3.select("#"+chartId).append("svg")
		    .attr("width", width + margin.left + margin.right)
		    .attr("height", height + margin.top + margin.bottom)
		    .append("g")
		    .attr("transform", "translate(" + margin.left + "," + margin.top + ")");
		
		var tip = d3.tip()
		    .attr('class', 'd3-tip')
		    .offset([-10, 0])
		    .html(function(d) {
			   return "<strong>Count:</strong> <span style='color:red'>" + Math.round(Math.pow(10,(d.y1- d.y0))) + "</span>";
		    });
		 
		svg.call(tip);
		  
		d3.csv("/life/web/csv/" +csvFile + ".csv", function(error, data) {
		  color.domain(d3.keys(data[0]).filter(function(key) { return key !== "Participants"; }));
		
		  data.forEach(function(d) {
		    var y0 = 0;
		    d.ages = color.domain().map(function(name) { return {name: name, y0: y0, y1: y0 += +d[name]}; });
		    d.total = d.ages[d.ages.length - 1].y1;
		  });
		
		  data.sort(function(a, b) { return b.total - a.total; });
		
		  x.domain(data.map(function(d) { return d.Participants; }));
		  y.domain([0, d3.max(data, function(d) { return d.total; })]);
		
		  svg.append("g")
		      .attr("class", "x axis")
		      .attr("transform", "translate(0," + height + ")")
		      .call(xAxis)
			  .selectAll(".tick text")
			  .call(wrap, x.rangeBand());
		
		  svg.append("g")
		      .attr("class", "y axis")
		      .call(yAxis)
		      //.append("text")
		      .attr("transform", "rotate(-90)")
		      .attr("y", 6)
		      .attr("dy", ".71em")
		      .style("text-anchor", "end")
		      .text("Count");
		
			
		  var Participants = svg.selectAll(".Participants")
		      .data(data)
		      .enter().append("g")
		      .attr("class", "g")
		      .attr("transform", function(d) { return "translate(" + x(d.Participants) + ",0)"; });
		
		  Participants.selectAll("rect")
		      .data(function(d) { return d.ages; })
		      .enter().append("rect")
		      .attr("width", x.rangeBand())
		      .attr("y", function(d) { return y(d.y1); })
		      .attr("height", function(d) { return y(d.y0) - y(d.y1); })
		      .style("fill", function(d) { return color(d.name); })
		      .on('mouseover', tip.show)
		      .on('mouseout', tip.hide);
		
		  var legend = svg.selectAll(".legend")
		      .data(color.domain().slice().reverse())
		      .enter().append("g")
		      .attr("class", "legend")
		      .attr("transform", function(d, i) { return "translate(0," + i * 20 + ")"; });
		
		  legend.append("rect")
		      .attr("x", width - 18)
		      .attr("width", 18)
		      .attr("height", 18)
		      .style("fill", color);
		
		  legend.append("text")
		      .attr("x", width - 24)
		      .attr("y", 9)
		      .attr("dy", ".35em")
		      .style("text-anchor", "end")
		      .text(function(d) { return d; });
		
		});
		
		function wrap(text, width) {
		
		  text.each(function() {
		     var text = d3.select(this),
		        words = text.text().split(/\s+/).reverse(),
		        word,
		        line = [],
		        lineNumber = 0,
		        lineHeight = 1.1, // ems
		        y = text.attr("y"),
		        dy = parseFloat(text.attr("dy")),
		        tspan = text.text(null).append("tspan").attr("x", 0).attr("y", y).attr("dy", dy + "em");
		  
			while (word = words.pop()) {
		      line.push(word);
		      tspan.text(line.join(" "));
		      if (tspan.node().getComputedTextLength() > width) {
		        line.pop();
		        tspan.text(line.join(" "));
		        line = [word];
		        tspan = text.append("tspan").attr("x", 0).attr("y", y).attr("dy", ++lineNumber * lineHeight + dy + "em").text(word);
			}
		    }
		  });
	 }
}