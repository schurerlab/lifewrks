var q;
q = getURLParameter('q');
if (q != null)
	q = q.replace(/&quot;/g, '"');

function loadParallelChart(selectedAssay) { // load data and create the chart
	$('#dynamic').html('');
	var img = "<img src = '/life/web/images/parallel.png'>";
	var html = "</div><img id='preloader-parallel' class ='preloader' src = '/life/web/images/preloader.gif'><div id='example' class='parcoords' style='height:240px;'></div><div id='grid'></div><div id='pager'></div><script id='brushing' src='/life/web/js/parallel/parallel.js'></"
			+ "script>";
	var red = Math.floor((Math.random() * 250) + 1);
	var blu = Math.floor((Math.random() * 250) + 1);
	var green = Math.floor((Math.random() * 250) + 1);
	$('#dynamic').html(html);
	/*
	 * static
	 * 
	 * $.ajax({ url : "/life/web/js/parallel/parallel-json3.js", success :
	 * function(response) { createChart(response); }, async : false, contentType :
	 * "application/json", dataType : "json", // error :
	 * $('#example').html('error parallel data'), error : console.log('error
	 * parallel data'), type : "GET" }); // dynamic $.ajax({ // url : //
	 * "http://lincs.ccs.miami.edu:8080/life/web/js/parallel-json.js", // url : //
	 * "/life/view-data-cell?q=&diseasename=melanoma&assayTypeName=Apoptosis%20Assay",
	 * url : "http://lincs.ccs.miami.edu:8080/life/view-data?q=" + q, // url :
	 * "/life/view-data?q=" + q, success : function(response) {
	 * createChart(response); }, async : false, jsonpCallback : 'jsonCallback',
	 * contentType : "application/json", dataType : "jsonp", // error :
	 * $('#example').html('error parallel data'), error : console.log('error
	 * parallel data'), type : "GET" });
	 */

	if (selectedAssay == "KINOMEscan assay") {
		$.ajax({
			url : "/life/view-data-kinomescan?q=" + q,
			success : function(response) {
				createChart(response);
			},
			async : false,
			jsonpCallback : 'jsonCallback',
			contentType : "application/json",
			dataType : "jsonp",
			error : console.log('error parallel data'),
			type : "GET"
		});
	} else if (selectedAssay == "Cell Cycle State Assay") {
		loadDiseases(selectedAssay);
	} else if (selectedAssay == "Apoptosis Assay") {
		loadDiseases(selectedAssay);
	} else if (selectedAssay == "Cell Growth Inhibition Assay") {
		loadDiseases(selectedAssay);
	} else if (selectedAssay == "L1000 Transcriptional Response Assay") {
		$.ajax({
			url : "http://lincs.ccs.miami.edu/life/view-data?q=" + q,
			success : function(response) {
				createChart(response);
			},
			async : false,
			jsonpCallback : 'jsonCallback',
			contentType : "application/json",
			dataType : "jsonp",
			error : console.log('error parallel data'),
			type : "GET"
		});
	}
}

function loadDiseases(selectedAssay) {
	$('#sigLinkDropDown').remove();
	$
			.ajax({
				url : "http://lincs.ccs.miami.edu:8080/solr-example/select?start=0&rows=0&indent=on&wt=json&facet=true&facet.field=Disease&facet.mincount=1&q="+ q +" AssayTypeName:\""+selectedAssay +"\"",
				success : function(response) {
					response = eval(response);
					var sigLinks = response.facet_counts.facet_fields.Disease;
					var linkStr = "<select  id ='sigLinkDropDown' class='sigLinks' onChange='loadDiseasesData(selectedAssay);' ><option value = \"default\" >-- Select --</option>";
					$('.sigLinks').html();
					for ( var i = 0; i < sigLinks.length; i++) {
						linkStr += "<option value = \"" + sigLinks[i] + "\">"
								+ sigLinks[i] + "</option>";
						i++;
					}
					linkStr += "</select><br>";
					if (selectedAssay == "Cell Cycle State Assay") {
						$("#sigCellCycleLinks").html(linkStr);
					} else if (selectedAssay == "Apoptosis Assay") {
						$("#sigApoptosisLinks").html(linkStr);
						console.log(selectedAssay);
					} else if (selectedAssay == "Cell Growth Inhibition Assay") {
						$("#sigCellGrowthLinks").html(linkStr);
					}

				},
				dataType : "jsonp",
				type : "GET",
				'jsonp' : 'json.wrf'
			});
}

function loadDiseasesData(selectedAssay) {
	
	var diseaseName = $('#sigLinkDropDown :selected').val();
	$('#example').html('');
	$.ajax({
		url : "/life/view-data-cell?q=" + q + "&diseasename=" + diseaseName
				+ "&assayTypeName=" + selectedAssay,
		success : function(response) {
			createChart(response);
		},
		async : false,
		jsonpCallback : 'jsonCallback',
		contentType : "application/json",
		dataType : "jsonp",
		error : console.log('error parallel data'),
		type : "GET"
	});

}

function createChart(data) {
	
	$('#preloader-parallel').hide();  
	
	data = data.data
	// slickgrid needs each data element to have an id
	// set up colors
	var colorgen = d3.scale.category20();
	var colors = {};
	_(data).chain().pluck('LincsSMId').uniq().each(function(d, i) {
		colors[d] = colorgen(i);
	});

	var color = function(d) {
		return colors[d.LincsSMId];
	};

	var parcoords = d3.parcoords()("#example").height(350).color(color).data(
			data).mode("queue").render().brushable() // enable
	// brushing
	.interactive().margin({
		top : 36,
		left : 150,
		right : 0,
		bottom : 16
	}).reorderable();
	data.forEach(function(d, i) {
		d.id = d.id || i;
	});

	// setting up grid
	var column_keys = d3.keys(data[0]);
	var columns = column_keys.map(function(key, i) {
		return {
			name : key,
			field : key,
			sortable : true
		}
	});

	var options = {
		enableCellNavigation : true,
		enableColumnReorder : false,
		multiColumnSort : false
	};

	var dataView = new Slick.Data.DataView();
	var grid = new Slick.Grid("#grid", dataView, columns, options);
	var pager = new Slick.Controls.Pager(dataView, grid, $("#pager"));

	// wire up model events to drive the grid
	dataView.onRowCountChanged.subscribe(function(e, args) {
		grid.updateRowCount();
		grid.render();
	});

	dataView.onRowsChanged.subscribe(function(e, args) {
		grid.invalidateRows(args.rows);
		grid.render();
	});

	// column sorting
	var sortcol = column_keys[0];
	var sortdir = 1;

	function comparer(a, b) {
		var x = a[sortcol], y = b[sortcol];
		return (x == y ? 0 : (x > y ? 1 : -1));
	}

	// click header to sort grid column
	grid.onSort.subscribe(function(e, args) {
		sortdir = args.sortAsc ? 1 : -1;
		sortcol = args.sortCol.field;

		if ($.browser.msie && $.browser.version <= 8) {
			dataView.fastSort(sortcol, args.sortAsc);
		} else {
			dataView.sort(comparer, args.sortAsc);
		}
	});

	// highlight row in chart
	grid.onMouseEnter.subscribe(function(e, args) {
		var i = grid.getCellFromEvent(e).row;
		var d = parcoords.brushed() || data;
		parcoords.highlight([ d[i] ]);
	});
	grid.onMouseLeave.subscribe(function(e, args) {
		parcoords.unhighlight();
	});

	// fill grid with data
	gridUpdate(data);

	// update grid on brush
	parcoords.on("brush", function(d) {
		gridUpdate(d);
	});

	function gridUpdate(data) {
		dataView.beginUpdate();
		dataView.setItems(data);
		dataView.endUpdate();
		
		//remove zmin and zmax rows in the table
		setTimeout(function(){	$('.grid-canvas .ui-widget-content:last').remove();
						$('.grid-canvas .ui-widget-content:last').remove();},1500);
	}
	;

	/*
	 * remove id column data.forEach(function(d, i) { delete d.id; }); $( "svg
	 * g.dimension:last-child" ).hide();
	 */

}

function getURLParameter(name) {
	return decodeURIComponent((new RegExp('[?|&]' + name + '='
			+ '([^&;]+?)(&|#|;|$)').exec(location.search) || [ , "" ])[1]
			.replace(/\+/g, '%20'))
			|| null
}
