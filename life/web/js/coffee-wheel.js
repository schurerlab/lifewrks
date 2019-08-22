function getCoffeeWheel(coffeeWheelJson,endPoint) {
 
 if(endPoint == "pKd"){
	  $("#coffeePercentStartVal").html("2");
	  $("#coffeePercentEndVal").html("8.5");
 }else if(endPoint == "IC50"){
	  $("#coffeePercentStartVal").html("0");
	  $("#coffeePercentEndVal").html("10");
 }else{
	  $("#coffeePercentStartVal").html("0");
	  $("#coffeePercentEndVal").html("100");
 }		
	
 var width = 540,
      height = width,
      radius = width / 2,
      x = d3.scale.linear().range([0, 2 * Math.PI]),
      y = d3.scale.pow().exponent(1.3).domain([0, 1]).range([0, radius]),
      padding = 5,
      duration = 10000;

  if($( window ).width()<=500) // responsive for mobile devices
	  var width=325;
  
  var div = d3.select("#vis");
  
  div.select("img").remove();
  
  $("#vis").show(); 
  $("#legend").show();
  $( "svg" ).remove();
  
  var vis = div.append("svg")
      .attr("width", width + padding * 2)
      .attr("height", height + padding * 2)
    .append("g")
      .attr("transform", "translate(" + [radius + padding, radius + padding] + ")");
  
  var partition = d3.layout.partition()
      .sort(null)
      .value(function(d) { return 5.8 - d.depth; });
  
  var arc = d3.svg.arc()
      .startAngle(function(d) { return Math.max(0, Math.min(2 * Math.PI, x(d.x))); })
      .endAngle(function(d) { return Math.max(0, Math.min(2 * Math.PI, x(d.x + d.dx))); })
      .innerRadius(function(d) { return Math.max(0, d.y ? y(d.y) : d.y); })
      .outerRadius(function(d) { return Math.max(0, y(d.y + d.dy)); });
  
  	var json = coffeeWheelJson;
    
  	var nodes = partition.nodes({children: json});
    var path = vis.selectAll("path").data(nodes);

    path.enter().append("path")
        .attr("id", function(d, i) { return "path-" + i; })
        .attr("d", arc)
        .attr("fill-rule", "evenodd")
        .style("fill", colour)
        .on("click", click)
		.on("mouseover", mouseover)
		.on("mouseout", mouseout);
  
    var text = vis.selectAll("text").data(nodes);
    var textEnter = text.enter().append("text")
        .style("fill-opacity", 1)
        .style("fill", function(d) {
          return brightness(d3.rgb(colour(d))) < 125 ? "#eee" : "#000";
        })
        .attr("text-anchor", function(d) {
          return x(d.x + d.dx / 2) > Math.PI ? "end" : "start";
        })
        .attr("dy", ".2em")
        .attr("transform", function(d) {
          var multiline = (d.name || "").split(" ").length > 1,
              angle = x(d.x + d.dx / 2) * 180 / Math.PI - 90,
              rotate = angle + (multiline ? -.5 : 0);
          return "rotate(" + rotate + ")translate(" + (y(d.y) + padding) + ")rotate(" + (angle > 90 ? -180 : 0) + ")";
        })
        .on("click", click);
    textEnter.append("tspan")
        .attr("x", 0)
        .text(function(d) /* { return d.depth ? d.name.split(" ")[0] : ""; }); */ { return ''; });
    textEnter.append("tspan")
        .attr("x", 0)
        .attr("dy", "1em")
        .text(function(d) /* { return d.depth ? d.name.split(" ")[1] || "" : ""; }); */ { return ''; });
  
    function click(d) { 
      path.transition()
        .duration(duration)
        .attrTween("d", arcTween(d));
  
      // Somewhat of a hack as we rely on arcTween updating the scales.
      text.style("visibility", function(e) {
            return isParentOf(d, e) ? null : d3.select(this).style("visibility");
          })
        .transition()
          .duration(duration)
          .attrTween("text-anchor", function(d) {
            return function() {
              return x(d.x + d.dx / 2) > Math.PI ? "end" : "start";
            };
          })
          .attrTween("transform", function(d) {
            var multiline = (d.name || "").split(" ").length > 1;
            return function() {
              var angle = x(d.x + d.dx / 2) * 180 / Math.PI - 90,
                  rotate = angle + (multiline ? -.5 : 0);
              return "rotate(" + rotate + ")translate(" + (y(d.y) + padding) + ")rotate(" + (angle > 90 ? -180 : 0) + ")";
            };
          })
          .style("fill-opacity", function(e) { return isParentOf(d, e) ? 1 : 1e-6; })
          .each("end", function(e) {
            d3.select(this).style("visibility", isParentOf(d, e) ? null : "hidden");
          });
    }
	
	function mouseover(d) {
		$('#intro').append(d.name);
		$('#chart-coffee-sector-name').append(d.name);
	}

	function mouseout(d) {
		$('#intro').empty();
		$('#chart-coffee-sector-name').empty();
	}
//  });
  
  function isParentOf(p, c) {
    if (p === c) return true;
    if (p.children) {
      return p.children.some(function(d) {
        return isParentOf(d, c);
      });
    }
    return false;
  }
  
  function colour(d) { 
     return d.colour || "#fff";
  }
  
  // Interpolate the scales!
  function arcTween(d) {
    var my = maxY(d),
        xd = d3.interpolate(x.domain(), [d.x, d.x + d.dx]),
        yd = d3.interpolate(y.domain(), [d.y, my]),
        yr = d3.interpolate(y.range(), [d.y ? 20 : 0, radius]);
    return function(d) {
      return function(t) { x.domain(xd(t)); y.domain(yd(t)).range(yr(t)); return arc(d); };
    };
  }
  
  function maxY(d) {
    return d.children ? Math.max.apply(Math, d.children.map(maxY)) : d.y + d.dy;
  }
  
  // http://www.w3.org/WAI/ER/WD-AERT/#color-contrast
  function brightness(rgb) {
    return rgb.r * .299 + rgb.g * .587 + rgb.b * .114;
  }
}