var viewDataConPieChart,viewDataTpPieChart,viewDataActivityPieChart,viewDataPerMeasureBarChart,viewDataCellPieChart,viewDataCFTable; 
var viewDataPPConPieChart,viewDataPPTpPieChart,viewDataPPActivityPieChart,viewDataPPPerMeasureBarChart,viewPPDataCellPieChart,viewPPDataCFTable; 
var viewDataKKConPieChart,viewDataKKActivityPieChart,viewDataKKSmallMoleculePieChart,viewDataKKPerMeasureBarChart,viewDataKKCFDataTable;
var viewDataProteinSecretionConPieChart,viewDataProteinSecretionCellPieChart,viewDataProteinSecretionSmallMoleculePieChart,viewDataProteinSecretionProteinPieChart,viewDataProteinSecretionPMBarChart,viewDataProteinSecretionDataTable;

var chartColors = ["#1f77b4", "#ff7f0e", "#2ca02c", "#d62728", "#9467bd", "#8c564b", "#e377c2", "#7f7f7f", "#bcbd22", "#17becf"];

function getViewDataCrossFilter(jsonData,mode){
	
	var data = eval(jsonData);
	var filter = crossfilter(data);
		
	viewDataConPieChart = dc.pieChart("#viewDataConPieChart");
	viewDataTpPieChart  = dc.pieChart("#viewDataTpPieChart");
	viewDataActivityPieChart  = dc.pieChart("#viewDataActivityPieChart");
	viewDataCellPieChart = dc.pieChart("#viewDataCellPieChart");
	
	viewDataPerMeasureBarChart = dc.barChart("#viewDataPerMeasureBarChart");
	viewDataCFTable = dc.dataTable("#viewDataCFTable");
	
	//for concentration
	var concentrationDim = filter.dimension(function (d) {
		return d.Concentration;
    });
    var concentrationDimGrp = concentrationDim.group();
	
	//for timepoint
	var timePointDim = filter.dimension(function (d) {
		return d.TimePoint;
    });
    var timePointDimGrp = timePointDim.group();
	
   //for activity name
	var activityDim = filter.dimension(function (d) {
		return d.Activity;
    });
    var activityDimGrp = activityDim.group();
    
	//for PerturbationMeasure
	var perturbationMeasureDim = filter.dimension(function (d) {
		return d.PerturbationMeasure;
    });
    var perturbationMeasureDimGrp = perturbationMeasureDim.group(function(d){
		return Math.round(d*10)/10;
    });
	
    // for cell line
    var cellLineDim = filter.dimension(function (d) {
		return d.CellLineName;
    });
    var cellLineDimGrp = cellLineDim.group();
    
	//for DataTable
	var tableDimension = filter.dimension(function (d) { return d.id; });
	
	viewDataConPieChart.width(300)
		  .height(200)
		  .transitionDuration(500)
		  .ordinalColors(chartColors)
		  .radius(80)
		  .innerRadius(30)
		  .dimension(concentrationDim)
		  .group(concentrationDimGrp)
		  .renderLabel(true)
		  .renderTitle(true);	
	
	viewDataTpPieChart.width(300)
		  .height(200)
		  .transitionDuration(500)
		  .ordinalColors(chartColors)
		  .radius(80)
		  .innerRadius(30)
		  .dimension(timePointDim)
		  .group(timePointDimGrp)
		  .renderLabel(true)
		  .renderTitle(true);	
	
	viewDataActivityPieChart.width(300)
		  .height(200)
		  .transitionDuration(500)
		  .ordinalColors(chartColors)
		  .radius(80)
		  .innerRadius(30)
		  .dimension(activityDim)
		  .group(activityDimGrp)
		  .renderLabel(true)
		  .renderTitle(true);	
	
	viewDataCellPieChart.width(300)
		  .height(200)
		  .transitionDuration(500)
		  .ordinalColors(chartColors)
		  .radius(80)
		  .innerRadius(30)
		  .dimension(cellLineDim)
		  .group(cellLineDimGrp)
		  .renderLabel(true)
		  .renderTitle(true);	
		  
	viewDataPerMeasureBarChart.width(600)
			.height(400)
			.dimension(perturbationMeasureDim)
			.group(perturbationMeasureDimGrp)
			.transitionDuration(1500)
			.x(d3.scale.linear()
			.domain([d3.min(data, function(d) {return d.PerturbationMeasure })-0.1, d3.max(data, function(d) {return d.PerturbationMeasure })+0.1]))
			.xAxisLabel("Endpoint Value")
			.yAxisLabel("Frequency")
			.xUnits(function(){return 7;})
			.elasticY(true)
			.gap(100);
	
	viewDataCFTable.width(550).height(10)
		.dimension(tableDimension)
		.group(function(d) { return ""})
		.columns([
			function(d) {
				if(mode == "L1000 transcriptional profiling assay")
					return d.perturbationName; 
				else
					return d.SmallMoleculeName;
			},
			function(d) { return d.CellLineName; },
			function(d) { return d.TimePoint; },
			function(d) { return d.Concentration; },
			function(d) { return d.Activity; },
			function(d) { return Math.round(d.PerturbationMeasure*1000)/1000; },
			function(d) {
				if(mode == "L1000 transcriptional profiling assay"){
					$("#geneThId").show();
					return d.GeneName; 
				}else
					$("#geneThId").hide();	
			}
		 ])
		.sortBy(function(d){ return d.PerturbationMeasure; })
		.order(d3.descending);
			
	dc.renderAll();
}

function getViewDataPPCrossFilter(jsonData){
	
	var data = eval(jsonData);
	var filter = crossfilter(data);
	
	viewDataPPConPieChart = dc.pieChart("#viewDataPPConPieChart");
	viewDataPPTpPieChart  = dc.pieChart("#viewDataPPTpPieChart");
	viewDataPPCellPieChart  = dc.pieChart("#viewDataPPCellPieChart");
	
	viewDataPPPerMeasureBarChart = dc.barChart("#viewDataPPPerMeasureBarChart");
	viewDataPPSmallMoleculeRowChart = dc.rowChart("#viewDataPPSmallMoleculeRowChart");
	
	viewPPDataCFTable = dc.dataTable("#viewPPDataCFTable");
	
	//for concentration
	var concentrationDim = filter.dimension(function (d) {
		return d.Concentration;
    });
    var concentrationDimGrp = concentrationDim.group();
	
	//for timepoint
	var timePointDim = filter.dimension(function (d) {
		return d.TimePoint;
    });
    var timePointDimGrp = timePointDim.group();
	
   //for cellline name
	var cellLineDim = filter.dimension(function (d) {
		return d.CellLineName;
    });
    var cellLineDimGrp = cellLineDim.group();
    
	//for PerturbationMeasure
	var perturbationMeasureDim = filter.dimension(function (d) {
		return d.PerturbationMeasure;
    });
    var perturbationMeasureDimGrp = perturbationMeasureDim.group(function(d){
		return Math.round(d*10)/10;
    });
	
    // for SmallMolecule name
    var smallMoleculeDim = filter.dimension(function (d) {
		return d.SmallMoleculeName;
    });
    var smallMoleculeDimGrp = smallMoleculeDim.group();
    
	//for DataTable
	var tableDimension = filter.dimension(function (d) { return d.id; });
	
	viewDataPPConPieChart.width(300)
		  .height(200)
		  .transitionDuration(500)
		  .ordinalColors(chartColors)
		  .radius(80)
		  .innerRadius(30)
		  .dimension(concentrationDim)
		  .group(concentrationDimGrp)
		  .renderLabel(true)
		  .renderTitle(true);	
	
	viewDataPPTpPieChart.width(300)
		  .height(200)
		  .transitionDuration(500)
		  .ordinalColors(chartColors)
		  .radius(80)
		  .innerRadius(30)
		  .dimension(timePointDim)
		  .group(timePointDimGrp)
		  .renderLabel(true)
		  .renderTitle(true);	
	
	viewDataPPCellPieChart.width(300)
		  .height(200)
		  .transitionDuration(500)
		  .ordinalColors(chartColors)
		  .radius(80)
		  .innerRadius(30)
		  .dimension(cellLineDim)
		  .group(cellLineDimGrp)
		  .renderLabel(true)
		  .renderTitle(true);	
		  
	viewDataPPPerMeasureBarChart.width(480)
			.height(405)
			.dimension(perturbationMeasureDim)
			.group(perturbationMeasureDimGrp)
			.transitionDuration(1500)
			.x(d3.scale.linear()
			.domain([d3.min(data, function(d) {return d.PerturbationMeasure })-0.1, d3.max(data, function(d) {return d.PerturbationMeasure })+0.1]))
			.xAxisLabel("Endpoint Value")
			.yAxisLabel("Frequency")
			.xUnits(function(){return 7;})
			.elasticY(true)
			.gap(100);
	
	viewDataPPSmallMoleculeRowChart.width(450)
			.height(400)
			.dimension(smallMoleculeDim)
			.group(smallMoleculeDimGrp)
			.renderLabel(true)
			.ordinalColors(chartColors)
			.colorDomain([0, 0])
			
	viewPPDataCFTable.width(350).height(10)
		.dimension(tableDimension)
		.group(function(d) { return ""})
		.columns([
		    function(d) { return d.SmallMoleculeName; },
		    function(d) { return d.PhosphoProteinName; },
		    function(d) { return d.CellLineName; },
		    function(d) { return d.TimePoint; },
			function(d) { return d.Concentration; },
			function(d) { return d.Activity; },
			function(d) { return Math.round(d.PerturbationMeasure*1000)/1000; }
		 ])
		.sortBy(function(d){ return d.PerturbationMeasure; })
		.order(d3.descending);
			
	dc.renderAll();
}

function getViewDataKKCrossFilter(jsonData,mode){
	
	var data = eval(jsonData);
	var filter = crossfilter(data);
		
	//viewDataKKConPieChart = dc.pieChart("#viewDataKKConPieChart");
	viewDataKKActivityPieChart  = dc.pieChart("#viewDataKKActivityPieChart");
	viewDataKKSmallMoleculePieChart = dc.pieChart("#viewDataKKSmallMoleculePieChart");
	
	viewDataKKPerMeasureBarChart = dc.barChart("#viewDataKKPerMeasureBarChart");
	viewDataKKCFDataTable = dc.dataTable("#viewDataKKCFDataTable");
	
	//for concentration
	var concentrationDim = filter.dimension(function (d) {
		return d.Concentration;
    });
    var concentrationDimGrp = concentrationDim.group();
	
   //for activity name
	var activityDim = filter.dimension(function (d) {
		return d.Activity;
    });
    var activityDimGrp = activityDim.group();
    
    // for SmallMolecule name
    var smallMoleculeDim = filter.dimension(function (d) {
		return d.SmallMoleculeName;
    });
    var smallMoleculeDimGrp = smallMoleculeDim.group();
    
	//for PerturbationMeasure
	var perturbationMeasureDim = filter.dimension(function (d) {
		return d.PerturbationMeasure;
    });
    var perturbationMeasureDimGrp = perturbationMeasureDim.group(function(d){
		return Math.round(d*10)/10;
    });
	
	//for DataTable
	var tableDimension = filter.dimension(function (d) { return d.id; });
	
	/*viewDataKKConPieChart.width(300)
		  .height(200)
		  .transitionDuration(500)
		  .ordinalColors(chartColors)
		  .radius(80)
		  .innerRadius(30)
		  .dimension(concentrationDim)
		  .group(concentrationDimGrp)
		  .renderLabel(true)
		  .renderTitle(true);*/	
	
	viewDataKKActivityPieChart.width(300)
		  .height(200)
		  .transitionDuration(500)
		  .ordinalColors(chartColors)
		  .radius(80)
		  .innerRadius(30)
		  .dimension(activityDim)
		  .group(activityDimGrp)
		  .renderLabel(true)
		  .renderTitle(true);	
	
	viewDataKKSmallMoleculePieChart.width(300)
		  .height(200)
		  .transitionDuration(500)
		  .ordinalColors(chartColors)
		  .radius(80)
		  .innerRadius(30)
		  .dimension(smallMoleculeDim)
		  .group(smallMoleculeDimGrp)
		  .renderLabel(true)
		  .renderTitle(true);	
		  
	viewDataKKPerMeasureBarChart.width(500)
			.height(400)
			.dimension(perturbationMeasureDim)
			.group(perturbationMeasureDimGrp)
			.transitionDuration(1500)
			.x(d3.scale.linear()
			.domain([d3.min(data, function(d) {return d.PerturbationMeasure })-0.1, d3.max(data, function(d) {return d.PerturbationMeasure })+0.1]))
			.xAxisLabel("Endpoint Value")
			.yAxisLabel("Frequency")
			.xUnits(function(){return 7;})
			.elasticY(true)
			.gap(100);
	
	viewDataKKCFDataTable.width(350).height(10)
		.dimension(tableDimension)
		.group(function(d) { return ""})
		.columns([
		    function(d) { return d.SmallMoleculeName; },
		    function(d) { return d.ProteinName; },
		    function(d) {
				if(mode == "KINOMEscan assay"){
					$("#tpCellThId").html("TimePoint");
					return d.TimePoint; 
				}else if(mode == "KiNativ assay"){
					$("#tpCellThId").html("Cell");
					return d.CellLineName; 
				}
			},
			function(d) { return d.Concentration; },
			function(d) { return d.Activity; },
			function(d) { return Math.round(d.PerturbationMeasure*1000)/1000;}
		 ])
		.sortBy(function(d){ return d.PerturbationMeasure; })
		.order(d3.descending);
			
	dc.renderAll();
}

function getViewDataProteinSecretionCrossFilter(jsonData,mode){
	var data = eval(jsonData);
	var filter = crossfilter(data);
	
	viewDataProteinSecretionConPieChart = dc.pieChart("#viewDataProteinSecretionConPieChart");
	viewDataProteinSecretionCellPieChart = dc.pieChart("#viewDataProteinSecretionCellPieChart");
	viewDataProteinSecretionSmallMoleculePieChart = dc.pieChart("#viewDataProteinSecretionSmallMoleculePieChart");
	viewDataProteinSecretionProteinPieChart = dc.pieChart("#viewDataProteinSecretionProteinPieChart");
	viewDataProteinSecretionPMBarChart = dc.barChart("#viewDataProteinSecretionPMBarChart");
	
	viewDataProteinSecretionDataTable = dc.dataTable("#viewDataProteinSecretionDataTable");
	
	//for concentration
	var concentrationDim = filter.dimension(function (d) {
		return d.Concentration;
    });
    var concentrationDimGrp = concentrationDim.group();
	
   //for cellline name
	var cellLineDim = filter.dimension(function (d) {
		return d.CellLineName;
    });
    var cellLineDimGrp = cellLineDim.group();
   
    //for smallmolecule name
	var smallMoleculeDim = filter.dimension(function (d) {
		return d.SmallMoleculeName;
    });
    var smallMoleculeDimGrp = smallMoleculeDim.group();
    
    //for protein name
	var proteinNameDim = filter.dimension(function (d) {
		return d.ProteinName;
    });
    var proteinNameDimGrp = proteinNameDim.group();
    
    //for PerturbationMeasure
	var perturbationMeasureDim = filter.dimension(function (d) {
		return d.PerturbationMeasure;
    });
    var perturbationMeasureDimGrp = perturbationMeasureDim.group(function(d){
		return Math.round(d*10)/10;
    });
	
	//for DataTable
	var tableDimension = filter.dimension(function (d) { return d.id; });

	viewDataProteinSecretionConPieChart.width(300)
		  .height(200)
		  .transitionDuration(500)
		  .ordinalColors(chartColors)
		  .radius(80)
		  .innerRadius(30)
		  .dimension(concentrationDim)
		  .group(concentrationDimGrp)
		  .renderLabel(true)
		  .renderTitle(true);	
	
	viewDataProteinSecretionCellPieChart.width(300)
		  .height(200)
		  .transitionDuration(500)
		  .ordinalColors(chartColors)
		  .radius(80)
		  .innerRadius(30)
		  .dimension(cellLineDim)
		  .group(cellLineDimGrp)
		  .renderLabel(true)
		  .renderTitle(true);	
	
	viewDataProteinSecretionSmallMoleculePieChart.width(300)
		  .height(200)
		  .transitionDuration(500)
		  .ordinalColors(chartColors)
		  .radius(80)
		  .innerRadius(30)
		  .dimension(smallMoleculeDim)
		  .group(smallMoleculeDimGrp)
		  .renderLabel(true)
		  .renderTitle(true);	
	
	viewDataProteinSecretionProteinPieChart.width(500)
		  .height(200)
		  .transitionDuration(500)
		  .ordinalColors(chartColors)
		  .radius(80)
		  .innerRadius(30)
		  .dimension(proteinNameDim)
		  .group(proteinNameDimGrp)
		  .renderLabel(true)
		  .renderTitle(true);	
	
	viewDataProteinSecretionPMBarChart.width(655)
			.height(480)
			.dimension(perturbationMeasureDim)
			.group(perturbationMeasureDimGrp)
			.transitionDuration(1500)
			.x(d3.scale.linear()
			.domain([d3.min(data, function(d) {return d.PerturbationMeasure })-0.1, d3.max(data, function(d) {return d.PerturbationMeasure })+0.1]))
			.xAxisLabel("Endpoint Value")
			.yAxisLabel("Frequency")
			.xUnits(function(){return 8;})
			.elasticY(true)
			.gap(100);
	
	viewDataProteinSecretionDataTable.width(350).height(10)
		.dimension(tableDimension)
		.group(function(d) { return ""})
		.columns([
		    function(d) { return d.SmallMoleculeName; },
		    function(d) { return d.ProteinName; },
			function(d) { return d.CellLineName; },
			function(d) { return d.Concentration; },
			function(d) { return d.Activity; },
			function(d) { return Math.round(d.PerturbationMeasure*1000)/1000; }
		 ])
		.sortBy(function(d){ return d.PerturbationMeasure; })
		.order(d3.descending);
			
	dc.renderAll();
}