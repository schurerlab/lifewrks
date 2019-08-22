"use strict";
$(document).ready(function() {
	var link =  '/life/web/js/chart-assays.js';
	displayChart(link, function() {
		displaySectorTextDelayed();
	});
	onChartLinkClick();
	$('#load').val('AssayTypeName');

}); // end doc ready

$(window).load(function() {
	// displaySectorText();
});

function onChartLinkClick() {
	
	$('div.chart-link a').click(function(){
		$('div.chart-link a').removeClass('current-link');
		$(this).addClass('current-link');
		var load= $(this).parent().attr('id');
		load = load.replace('chart-link-assay', 'AssayTypeName');
		load = load.replace('chart-link-cell-lines', 'CellLine');
		load = load.replace('chart-link-compounds', 'SmallMolecule');
		load = load.replace('chart-link-proteins', 'Protein');
		load = load.replace('chart-link-diseases', 'CellLine');
		$('#load').val(load);
	
	});
	
	$('#chart-link-assay').click(function() {
		var link = '/life/web/js/chart-assays.js';
		$('#chart svg').remove();
		$('#organ-chart-sector-name').hide();
		$('#disease-chart-sector-name').hide();
		$('#chart-sector-name').show();
		displayChart(link, function() {
			displaySectorTextDelayed();
			var div = document.getElementById("chart-sector-name");
		    div.textContent = "Bioassays";
		    var text = div.textContent;
		});
	});
	$('#chart-link-cell-lines').click(function() {
		
		var link = '/life/web/js/chart-cell-lines.js';
		var cellsByDiseases = '/life/web/js/chart-diseases.js';
		$('#chart svg').remove();
		$('#chart-sector-name').hide();
		$('#organ-chart-sector-name').show();
		$('#disease-chart-sector-name').show();
		displayChart_1(link, function() {
			var div = document.getElementById("organ-chart-sector-name");
		    div.textContent = "Cells By Organ";
		    var text = div.textContent;
			displaySectorTextDelayed_1()

		});
		displayChart_2(cellsByDiseases, function() {
			var div = document.getElementById("disease-chart-sector-name");
		    div.textContent = "Cells By Disease";
		    var text = div.textContent;
			displaySectorTextDelayed_2()
		});
	});
	$('#chart-link-compounds').click(function() {
		var link = '/life/web/js/chart-compounds.js';
		$('#chart svg').remove();
		$('#organ-chart-sector-name').hide();
		$('#disease-chart-sector-name').hide();
		$('#chart-sector-name').show();
		displayChart(link, function() {
			displaySectorTextDelayed();
			var div = document.getElementById("chart-sector-name");
		    div.textContent = "Compounds";
		    var text = div.textContent;
		});
	});
	$('#chart-link-proteins').click(function() {
		var link = '/life/web/js/chart-proteins.js';
		$('#chart svg').remove();
		$('#organ-chart-sector-name').hide();
		$('#disease-chart-sector-name').hide();
		$('#chart-sector-name').show();
		displayChart(link, function() {
			displaySectorTextDelayed();
			var div = document.getElementById("chart-sector-name");
		    div.textContent = "Kinase Proteins";
		    var text = div.textContent;
		});
	});
//	$('#chart-link-diseases').click(function() {
//		var link = '/life/web/js/chart-diseases.js';
//		$('#chart svg').remove();
//		displayChart(link, function() {
//			displaySectorTextDelayed();
//		});
//	});
}

function displayChart(link, callback) {
	var the_selector_id = 'chart';
	var w = 480, h = 350, r = Math.min(w, h) / 2, color = d3.scale.category10();

	var vis = d3.select("#"+the_selector_id).append("svg:svg").attr("width", w).attr(
			"height", h).append("svg:g").attr("transform",
			"translate(" + w / 2 + "," + h / 2 + ")");
	var partition = d3.layout.partition().sort(null).size(
			[ 2 * Math.PI, r * r ]).children(function(d) {
		return isNaN(d.value) ? d3.entries(d.value) : null;
	}).value(function(d) {
		return d.value;
	});

	var arc = d3.svg.arc().startAngle(function(d) {
		return d.x;
	}).endAngle(function(d) {
		return d.x + d.dx;
	}).innerRadius(function(d) {
		return Math.sqrt(d.y);
	}).outerRadius(function(d) {
		return Math.sqrt(d.y + d.dy);
	});

	d3.json(link, function(json) {
		var g = vis.data(d3.entries(json)).selectAll("g").data(partition)
				.enter().append("svg:g").attr("display", function(d) {
					return d.depth ? null : "none";
				}); // hide inner ring

		g.append("svg:path").attr("d", arc).attr("stroke", "#fff").on(
				"mouseover", function() {
					d3.select(this).style("filter", "alpha(opacity = 100)");
				}).on("mouseover", function() {
			d3.select(this).style("-khtml-opacity", "1");
		}).on("mouseover", function() {
			d3.select(this).style("-moz-opacity", "1");
		}).on("mouseover", function() {
			d3.select(this).style("opacity", "1");
		}).on("mouseout", function() {
			d3.select(this).style("filter", "alpha(opacity = 50)");
		}).on("mouseout", function() {
			d3.select(this).style("-khtml-opacity", "0.5");
		}).on("mouseout", function() {
			d3.select(this).style("-moz-opacity", "0.5");
		}).on("mouseout", function() {
			d3.select(this).style("opacity", "0.5");
		}).attr("fill", function(d) {
			return color((d.children ? d : d.parent).data.key);
		}).attr("fill-rule", "evenodd");

		g.append("svg:text").attr(
				"transform",
				function(d) {
					return "rotate(" + (d.x + d.dx / 2 - Math.PI / 2) / Math.PI
							* 180 + ")";
				}).attr("x", function(d) {
			return Math.sqrt(d.y);
		}).attr("dx", "6") // margin
		.attr("dy", ".35em") // vertical-align
		.text(function(d) {
			return d.data.key + '<br />' + d.value;
		});

	});
	callback();

}

/*
 * display chart sectors' text and on click search
 */
function displaySectorText() {

	$("svg g g").mouseover(function() {
		var name = $(this).children("text").text();
		$('#chart-sector-name').html(name);
	});
	$("svg g g").mouseout(function() {
		$('#chart-sector-name').html('');
	});

	$("svg g g").click(
			function() {
				var name = $(this).children("text").text();
				var name = name.split('<br')[0];
				$("#searchForm input[name=search]").val(
						'concept:&quot;' + name + '&quot;');
				submitForm();
			});
}

function displaySectorTextDelayed() {
	
	//alert(clicked);
	setTimeout(function() {
		displaySectorText();
	}, 1000);
}


function displayChart_1(link, callback) {
	var the_selector_id = 'chart-1';
	var w = 480, h = 350, r = Math.min(w, h) / 2, color = d3.scale.category10();

	var vis = d3.select("#"+the_selector_id).append("svg:svg").attr("width", w).attr(
			"height", h).append("svg:g").attr("transform",
			"translate(" + w / 2 + "," + h / 2 + ")");
	var partition = d3.layout.partition().sort(null).size(
			[ 2 * Math.PI, r * r ]).children(function(d) {
		return isNaN(d.value) ? d3.entries(d.value) : null;
	}).value(function(d) {
		return d.value;
	});

	var arc = d3.svg.arc().startAngle(function(d) {
		return d.x;
	}).endAngle(function(d) {
		return d.x + d.dx;
	}).innerRadius(function(d) {
		return Math.sqrt(d.y);
	}).outerRadius(function(d) {
		return Math.sqrt(d.y + d.dy);
	});

	d3.json(link, function(json) {
		var g = vis.data(d3.entries(json)).selectAll("g").data(partition)
				.enter().append("svg:g").attr("display", function(d) {
					return d.depth ? null : "none";
				}); // hide inner ring

		g.append("svg:path").attr("d", arc).attr("stroke", "#fff").on(
				"mouseover", function() {
					d3.select(this).style("filter", "alpha(opacity = 100)");
				}).on("mouseover", function() {
			d3.select(this).style("-khtml-opacity", "1");
		}).on("mouseover", function() {
			d3.select(this).style("-moz-opacity", "1");
		}).on("mouseover", function() {
			d3.select(this).style("opacity", "1");
		}).on("mouseout", function() {
			d3.select(this).style("filter", "alpha(opacity = 50)");
		}).on("mouseout", function() {
			d3.select(this).style("-khtml-opacity", "0.5");
		}).on("mouseout", function() {
			d3.select(this).style("-moz-opacity", "0.5");
		}).on("mouseout", function() {
			d3.select(this).style("opacity", "0.5");
		}).attr("fill", function(d) {
			return color((d.children ? d : d.parent).data.key);
		}).attr("fill-rule", "evenodd");

		g.append("svg:text").attr(
				"transform",
				function(d) {
					return "rotate(" + (d.x + d.dx / 2 - Math.PI / 2) / Math.PI
							* 180 + ")";
				}).attr("x", function(d) {
			return Math.sqrt(d.y);
		}).attr("dx", "6") // margin
		.attr("dy", ".35em") // vertical-align
		.text(function(d) {
			return d.data.key + '<br />' + d.value;
		});

	});
	callback();

}

function displayChart_2(link, callback) {
	var the_selector_id = 'chart-2';
	var w = 480, h = 350, r = Math.min(w, h) / 2, color = d3.scale.category10();

	var vis = d3.select("#"+the_selector_id).append("svg:svg").attr("width", w).attr(
			"height", h).append("svg:g").attr("transform",
			"translate(" + w / 2 + "," + h / 2 + ")");
	var partition = d3.layout.partition().sort(null).size(
			[ 2 * Math.PI, r * r ]).children(function(d) {
		return isNaN(d.value) ? d3.entries(d.value) : null;
	}).value(function(d) {
		return d.value;
	});

	var arc = d3.svg.arc().startAngle(function(d) {
		return d.x;
	}).endAngle(function(d) {
		return d.x + d.dx;
	}).innerRadius(function(d) {
		return Math.sqrt(d.y);
	}).outerRadius(function(d) {
		return Math.sqrt(d.y + d.dy);
	});

	d3.json(link, function(json) {
		var g = vis.data(d3.entries(json)).selectAll("g").data(partition)
				.enter().append("svg:g").attr("display", function(d) {
					return d.depth ? null : "none";
				}); // hide inner ring

		g.append("svg:path").attr("d", arc).attr("stroke", "#fff").on(
				"mouseover", function() {
					d3.select(this).style("filter", "alpha(opacity = 100)");
				}).on("mouseover", function() {
			d3.select(this).style("-khtml-opacity", "1");
		}).on("mouseover", function() {
			d3.select(this).style("-moz-opacity", "1");
		}).on("mouseover", function() {
			d3.select(this).style("opacity", "1");
		}).on("mouseout", function() {
			d3.select(this).style("filter", "alpha(opacity = 50)");
		}).on("mouseout", function() {
			d3.select(this).style("-khtml-opacity", "0.5");
		}).on("mouseout", function() {
			d3.select(this).style("-moz-opacity", "0.5");
		}).on("mouseout", function() {
			d3.select(this).style("opacity", "0.5");
		}).attr("fill", function(d) {
			return color((d.children ? d : d.parent).data.key);
		}).attr("fill-rule", "evenodd");

		g.append("svg:text").attr(
				"transform",
				function(d) {
					return "rotate(" + (d.x + d.dx / 2 - Math.PI / 2) / Math.PI
							* 180 + ")";
				}).attr("x", function(d) {
			return Math.sqrt(d.y);
		}).attr("dx", "6") // margin
		.attr("dy", ".35em") // vertical-align
		.text(function(d) {
			return d.data.key + '<br />' + d.value;
		});

	});
	callback();

}


/*
 * display chart sectors' text and on click search
 */
function displaySectorText_1() {

	$("#chart-1 svg g g").mouseover(function() {
		var name = $(this).children("text").text();
		$('#organ-chart-sector-name').html(name);
	});
	$("#chart-1 svg g g").mouseout(function() {
		$('#organ-chart-sector-name').html('Cells By Organ');
	});

	$("#chart-1 svg g g").click(
			function() {
				var name = $(this).children("text").text();
				var name = name.split('<br')[0];
				$("#searchForm input[name=search]").val(
						'concept:&quot;' + name + '&quot;');
				submitForm();
			});
}

function displaySectorTextDelayed_1() {
	
	//alert(clicked);
	setTimeout(function() {
		displaySectorText_1();
	}, 1000);
}


/*
 * display chart sectors' text and on click search
 */
function displaySectorText_2() {

	$("#chart-2 svg g g").mouseover(function() {
		var name = $(this).children("text").text();
		$('#disease-chart-sector-name').html(name);
	});
	$("#chart-2 svg g g").mouseout(function() {
		$('#disease-chart-sector-name').html('Cells By Disease');
	});

	$("#chart-2 svg g g").click(
			function() {
				var name = $(this).children("text").text();
				var name = name.split('<br')[0];
				$("#searchForm input[name=search]").val(
						'concept:&quot;' + name + '&quot;');
				submitForm();
			});
}

function displaySectorTextDelayed_2() {
	
	//alert(clicked);
	setTimeout(function() {
		displaySectorText_2();
	}, 1000);
}