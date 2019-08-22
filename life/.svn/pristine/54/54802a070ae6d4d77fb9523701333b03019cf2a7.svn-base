var ppConcentrationPieChart,ppTimePointPieChart,ppCellLinePieChart,ppPerturbationMeasureBarChart,ppSmallMoleculeRowChart,ppDataTable; 
var concentrationPieChart,timePointPieChart,activityPieChart,perturbationMeasureBarChart,cellLinePieChart,cfDataTable; 
var geneConcentrationPieChart,geneTimePointPieChart,geneActivityPieChart,geneCellLinePieChart,genePerturbationMeasureBarChart,geneDataTable;
var smallMoleculeConcentrationPieChart,smallMoleculeTimePointPieChart,smallMoleculeCellLinePieChart,smallMoleculePPNameRowChart,smallMoleculePerturbationMeasureBarChart,smallMoleculeDataTable; 
var cellConcentrationPieChart,cellActivityPieChart,cellSmallMoleculePieChart,cellPerturbationMeasureBarChart,cellDataTable; 
var smProteinSecretionConPieChart,smProteinSecretionCellPieChart,smProteinSecretionProteinPieChart,smProteinSecretionPMBarChart,smProteinSecretionDataTable;
var cellProteinSecretionConPieChart,cellProteinSecretionSmallMoleculePieChart,cellProteinSecretionProteinPieChart,cellProteinSecretionPMBarChart,cellProteinSecretionDataTable; 
var abProteinSecretionConPieChart,abProteinSecretionSmallMoleculePieChart,abProteinSecretionCellPieChart,abProteinSecretionPMBarChart,abProteinSecretionDataTable;


var chartColors = ["#1f77b4", "#ff7f0e", "#2ca02c", "#d62728", "#9467bd", "#8c564b", "#e377c2", "#7f7f7f", "#bcbd22", "#17becf"];
var cfTableId;

//1.Common Crossfilter code
function getCrossFilter(jsonData,mode){
	$("#downloadSummary").show();
	$("#downloadSummary").css( { marginLeft : "1080px"} );

	cfTableId = "cfDataTable";
	
	var data = eval(jsonData);
	var filter = crossfilter(data);
		
	concentrationPieChart = dc.pieChart("#concentrationPieChart");
	timePointPieChart  = dc.pieChart("#timePointPieChart");
	activityPieChart  = dc.pieChart("#activityPieChart");
	cellLinePieChart = dc.pieChart("#cellLinePieChart");
	
	perturbationMeasureBarChart = dc.barChart("#perturbationMeasureBarChart");
	cfDataTable = dc.dataTable("#cfDataTable");
	
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
	
	concentrationPieChart.width(300)
		  .height(200)
		  .transitionDuration(500)
		  .ordinalColors(chartColors)
		  .radius(80)
		  .innerRadius(30)
		  .dimension(concentrationDim)
		  .group(concentrationDimGrp)
		  .renderLabel(true)
		  .renderTitle(true);	
	
	timePointPieChart.width(300)
		  .height(200)
		  .transitionDuration(500)
		  .ordinalColors(chartColors)
		  .radius(80)
		  .innerRadius(30)
		  .dimension(timePointDim)
		  .group(timePointDimGrp)
		  .renderLabel(true)
		  .renderTitle(true);	
	
	activityPieChart.width(300)
		  .height(200)
		  .transitionDuration(500)
		  .ordinalColors(chartColors)
		  .radius(80)
		  .innerRadius(30)
		  .dimension(activityDim)
		  .group(activityDimGrp)
		  .renderLabel(true)
		  .renderTitle(true);	
	
	cellLinePieChart.width(300)
		  .height(200)
		  .transitionDuration(500)
		  .ordinalColors(chartColors)
		  .radius(80)
		  .innerRadius(30)
		  .dimension(cellLineDim)
		  .group(cellLineDimGrp)
		  .renderLabel(true)
		  .renderTitle(true);	
		  
	perturbationMeasureBarChart.width(600)
			.height(510)
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
	
	cfDataTable.width(350).height(10)
		.dimension(tableDimension)
		.group(function(d) { return ""})
		.columns([
			function(d) { return d.SmallMoleculeName; },
		    function(d) { return d.CellLineName; },
		    function(d) { return d.TimePoint; },
			function(d) { return d.Concentration; },
			function(d) { return d.Activity; },
			function(d) { return Math.round(d.PerturbationMeasure*1000)/1000; },
			function(d) {
				if(mode == "cdna" || mode == "shrna" || mode == "SmallMolecule" || mode == "cellline"){
					$("#summaryGeneThId").show();
					return d.GeneName; 
				}
				else
					$("#summaryGeneThId").hide();	
			}
		 ])
		.sortBy(function(d){ return d.PerturbationMeasure; })
		.order(d3.descending);
			
	dc.renderAll();
}
//2.Crossfilter for PhosphoProtein page with L1000 assay
function getPhosphoProteinCF(jsonData){
	$("#downloadSummary").show();
	
	cfTableId = "ppDataTable";
	
	var data = eval(jsonData);
	var filter = crossfilter(data);
		
	ppConcentrationPieChart = dc.pieChart("#ppConcentrationPieChart");
	ppTimePointPieChart  = dc.pieChart("#ppTimePointPieChart");
	ppCellLinePieChart  = dc.pieChart("#ppCellLinePieChart");
	
	ppPerturbationMeasureBarChart = dc.barChart("#ppPerturbationMeasureBarChart");
	ppSmallMoleculeRowChart = dc.rowChart("#ppSmallMoleculeRowChart");
	
	ppDataTable = dc.dataTable("#ppDataTable");
	
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
	
    ppConcentrationPieChart.width(300)
		  .height(200)
		  .transitionDuration(500)
		  .ordinalColors(chartColors)
		  .radius(80)
		  .innerRadius(30)
		  .dimension(concentrationDim)
		  .group(concentrationDimGrp)
		  .renderLabel(true)
		  .renderTitle(true);	
	
	ppTimePointPieChart.width(300)
		  .height(200)
		  .transitionDuration(500)
		  .ordinalColors(chartColors)
		  .radius(80)
		  .innerRadius(30)
		  .dimension(timePointDim)
		  .group(timePointDimGrp)
		  .renderLabel(true)
		  .renderTitle(true);	
	
	ppCellLinePieChart.width(300)
		  .height(200)
		  .transitionDuration(500)
		  .ordinalColors(chartColors)
		  .radius(80)
		  .innerRadius(30)
		  .dimension(cellLineDim)
		  .group(cellLineDimGrp)
		  .renderLabel(true)
		  .renderTitle(true);	
		  
	ppPerturbationMeasureBarChart.width(550)
			.height(505)
			.dimension(perturbationMeasureDim)
			.group(perturbationMeasureDimGrp)
			.transitionDuration(1500)
			.x(d3.scale.linear()
			.domain([d3.min(data, function(d) {return d.PerturbationMeasure })-0.1, d3.max(data, function(d) {return d.PerturbationMeasure })+0.1]))
			.xAxisLabel("Endpoint Value")
			.yAxisLabel("Frequency")	
			.xUnits(function(){return 6;})
			.elasticY(true)
			.gap(100);
	
	ppSmallMoleculeRowChart.width(500)
			.height(400)
			.dimension(smallMoleculeDim)
			.group(smallMoleculeDimGrp)
			.renderLabel(true)
			.ordinalColors(chartColors)
			.colorDomain([0, 0])
			
	ppDataTable.width(350).height(10)
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

//3.Crossfilter for SmallMolecule/cell page with PhosphoProtein assay
function getSmallMoleculePhosphoProteinCF(jsonData){
	$("#downloadSummary").show();
	$("#downloadSummary").css( { marginLeft : "1010px"} );
	
	cfTableId = "smallMoleculeDataTable";	
	
	var data = eval(jsonData);
	var filter = crossfilter(data);
	
	smallMoleculeConcentrationPieChart = dc.pieChart("#smallMoleculeConcentrationPieChart");
	smallMoleculeTimePointPieChart = dc.pieChart("#smallMoleculeTimePointPieChart");
	smallMoleculeCellLinePieChart = dc.pieChart("#smallMoleculeCellLinePieChart");
	
	smallMoleculePerturbationMeasureBarChart = dc.barChart("#smallMoleculePerturbationMeasureBarChart");
	smallMoleculeDataTable = dc.dataTable("#smallMoleculeDataTable");
	
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
	
	//for DataTable
	var tableDimension = filter.dimension(function (d) { return d.id; });

    smallMoleculeConcentrationPieChart.width(300)
		  .height(200)
		  .transitionDuration(500)
		  .ordinalColors(chartColors)
		  .radius(80)
		  .innerRadius(30)
		  .dimension(concentrationDim)
		  .group(concentrationDimGrp)
		  .renderLabel(true)
		  .renderTitle(true);	
	
	smallMoleculeTimePointPieChart.width(300)
		  .height(200)
		  .transitionDuration(500)
		  .ordinalColors(chartColors)
		  .radius(80)
		  .innerRadius(30)
		  .dimension(timePointDim)
		  .group(timePointDimGrp)
		  .renderLabel(true)
		  .renderTitle(true);	
	
	smallMoleculeCellLinePieChart.width(300)
		  .height(200)
		  .transitionDuration(500)
		  .ordinalColors(chartColors)
		  .radius(80)
		  .innerRadius(30)
		  .dimension(cellLineDim)
		  .group(cellLineDimGrp)
		  .renderLabel(true)
		  .renderTitle(true);	
	
	smallMoleculePerturbationMeasureBarChart.width(555)
			.height(530)
			.dimension(perturbationMeasureDim)
			.group(perturbationMeasureDimGrp)
			.transitionDuration(1500)
			.x(d3.scale.linear()
			.domain([d3.min(data, function(d) {return d.PerturbationMeasure })-0.1, d3.max(data, function(d) {return d.PerturbationMeasure })+0.1]))
			.xAxisLabel("Endpoint Value")
			.yAxisLabel("Frequency")
			.xUnits(function(){return 6;})
			.elasticY(true)
			.gap(100);
	
	smallMoleculeDataTable.width(350).height(10)
		.dimension(tableDimension)
		.group(function(d) { return ""})
		.columns([
		    function(d) { return d.SmallMoleculeName; },
		    function(d) { return d.PhosphoProteinName; },
		    function(d) { return d.CellLineName; },
		    function(d) { return d.TimePoint; },
			function(d) { return d.Concentration; },
			function(d) { return Math.round(d.PerturbationMeasure*1000)/1000; }
		 ])
		.sortBy(function(d){ return d.PerturbationMeasure; })
		.order(d3.descending);
			
	dc.renderAll();
}

//4.Crossfilter for Gene page with L1000 assay
function getGeneCF(jsonData){
	$("#downloadSummary").show();
	$("#downloadSummary").css( { marginLeft : "1080px"} );
	
	cfTableId = "geneDataTable";
	
	var data = eval(jsonData);
	var filter = crossfilter(data);
		
	geneConcentrationPieChart = dc.pieChart("#geneConcentrationPieChart");
	geneTimePointPieChart  = dc.pieChart("#geneTimePointPieChart");
	geneActivityPieChart  = dc.pieChart("#geneActivityPieChart");
	geneCellLinePieChart = dc.pieChart("#geneCellLinePieChart");
	
	genePerturbationMeasureBarChart = dc.barChart("#genePerturbationMeasureBarChart");
	geneDataTable = dc.dataTable("#geneDataTable");
	
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
	
    // for cell line name
    var cellLineDim = filter.dimension(function (d) {
		return d.CellLineName;
    });
    var cellLineDimGrp = cellLineDim.group();
    
	//for DataTable
	var tableDimension = filter.dimension(function (d) { return d.id; });
	
	geneConcentrationPieChart.width(300)
		  .height(200)
		  .transitionDuration(500)
		  .ordinalColors(chartColors)
		  .radius(80)
		  .innerRadius(30)
		  .dimension(concentrationDim)
		  .group(concentrationDimGrp)
		  .renderLabel(true)
		  .renderTitle(true);	
	
	geneTimePointPieChart.width(300)
		  .height(200)
		  .transitionDuration(500)
		  .ordinalColors(chartColors)
		  .radius(80)
		  .innerRadius(30)
		  .dimension(timePointDim)
		  .group(timePointDimGrp)
		  .renderLabel(true)
		  .renderTitle(true);	
	
	geneActivityPieChart.width(300)
		  .height(200)
		  .transitionDuration(500)
		  .ordinalColors(chartColors)
		  .radius(80)
		  .innerRadius(30)
		  .dimension(activityDim)
		  .group(activityDimGrp)
		  .renderLabel(true)
		  .renderTitle(true);	
	
	geneCellLinePieChart.width(300)
		  .height(200)
		  .transitionDuration(500)
		  .ordinalColors(chartColors)
		  .radius(80)
		  .innerRadius(30)
		  .dimension(cellLineDim)
		  .group(cellLineDimGrp)
		  .renderLabel(true)
		  .renderTitle(true);	
		  
	genePerturbationMeasureBarChart.width(560)
			.height(495)
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
	
	geneDataTable.width(350).height(10)
		.dimension(tableDimension)
		.group(function(d) { return ""})
		.columns([
			function(d) {
				$("#summaryPerturbagenThId").show();
				return d.Perturbation; 
			},
			function(d) {
				$("#summaryGeneThId").show();
				return d.GeneName; 
			},
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

//5.Crossfilter for Cell Line page with Kinativ assay
function getCellKinativCF(jsonData){
	$("#downloadSummary").show();
	cfTableId = "cellDataTable";
	
	var data = eval(jsonData);
	var filter = crossfilter(data);
		
	cellConcentrationPieChart = dc.pieChart("#cellConcentrationPieChart");
	cellActivityPieChart  = dc.pieChart("#cellActivityPieChart");
	cellSmallMoleculePieChart = dc.pieChart("#cellSmallMoleculePieChart");
	
	cellPerturbationMeasureBarChart = dc.barChart("#cellPerturbationMeasureBarChart");
	cellDataTable = dc.dataTable("#cellDataTable");
	
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
	
	cellConcentrationPieChart.width(300)
		  .height(200)
		  .transitionDuration(500)
		  .ordinalColors(chartColors)
		  .radius(80)
		  .innerRadius(30)
		  .dimension(concentrationDim)
		  .group(concentrationDimGrp)
		  .renderLabel(true)
		  .renderTitle(true);	
	
	cellActivityPieChart.width(300)
		  .height(200)
		  .transitionDuration(500)
		  .ordinalColors(chartColors)
		  .radius(80)
		  .innerRadius(30)
		  .dimension(activityDim)
		  .group(activityDimGrp)
		  .renderLabel(true)
		  .renderTitle(true);	
	
	cellSmallMoleculePieChart.width(300)
		  .height(200)
		  .transitionDuration(500)
		  .ordinalColors(chartColors)
		  .radius(80)
		  .innerRadius(30)
		  .dimension(smallMoleculeDim)
		  .group(smallMoleculeDimGrp)
		  .renderLabel(true)
		  .renderTitle(true);	
		  
	cellPerturbationMeasureBarChart.width(600)
			.height(400)
			.dimension(perturbationMeasureDim)
			.group(perturbationMeasureDimGrp)
			.transitionDuration(1500)
			.x(d3.scale.linear()
			.domain([d3.min(data, function(d) {return d.PerturbationMeasure })-0.1, d3.max(data, function(d) {return d.PerturbationMeasure })+0.1]))
			.xAxisLabel("Endpoint Value")
			.yAxisLabel("Frequency")
			.xUnits(function(){return 6;})
			.elasticY(true)
			.gap(100);
	
	cellDataTable.width(350).height(10)
		.dimension(tableDimension)
		.group(function(d) { return ""})
		.columns([
			function(d) { return d.SmallMoleculeName; },
			function(d) { return d.ProteinName; },
			function(d) { return d.CellLineName; },
			function(d) { return d.TimePoint; },
			function(d) { return d.Concentration; },
			function(d) { return d.Activity; },
			function(d) { return Math.round(d.PerturbationMeasure*1000)/1000; },
		 ])
		.sortBy(function(d){ return d.PerturbationMeasure; })
		.order(d3.descending);
			
	dc.renderAll();
}

//6.Crossfilter for Smallmolecule page with Single cell protein secretion profiling assay	
function getSmallMoleculeProteinSecretionCF(jsonData){
	$("#downloadSummary").show();
	$("#downloadSummary").css( { marginLeft : "1010px"} );
	
	cfTableId = "smProteinSecretionDataTable";	
	
	var data = eval(jsonData);
	var filter = crossfilter(data);
	
	smProteinSecretionConPieChart = dc.pieChart("#smProteinSecretionConPieChart");
	smProteinSecretionCellPieChart = dc.pieChart("#smProteinSecretionCellPieChart");
	smProteinSecretionProteinPieChart = dc.pieChart("#smProteinSecretionProteinPieChart");
	smProteinSecretionPMBarChart = dc.barChart("#smProteinSecretionPMBarChart");
	
	smProteinSecretionDataTable = dc.dataTable("#smProteinSecretionDataTable");
	
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

	smProteinSecretionConPieChart.width(300)
		  .height(200)
		  .transitionDuration(500)
		  .ordinalColors(chartColors)
		  .radius(80)
		  .innerRadius(30)
		  .dimension(concentrationDim)
		  .group(concentrationDimGrp)
		  .renderLabel(true)
		  .renderTitle(true);	
	
	smProteinSecretionCellPieChart.width(300)
		  .height(200)
		  .transitionDuration(500)
		  .ordinalColors(chartColors)
		  .radius(80)
		  .innerRadius(30)
		  .dimension(cellLineDim)
		  .group(cellLineDimGrp)
		  .renderLabel(true)
		  .renderTitle(true);	
	
	smProteinSecretionProteinPieChart.width(500)
		  .height(200)
		  .transitionDuration(500)
		  .ordinalColors(chartColors)
		  .radius(80)
		  .innerRadius(30)
		  .dimension(proteinNameDim)
		  .group(proteinNameDimGrp)
		  .renderLabel(true)
		  .renderTitle(true);	
	
	smProteinSecretionPMBarChart.width(555)
			.height(530)
			.dimension(perturbationMeasureDim)
			.group(perturbationMeasureDimGrp)
			.transitionDuration(1500)
			.x(d3.scale.linear()
			.domain([d3.min(data, function(d) {return d.PerturbationMeasure })-0.1, d3.max(data, function(d) {return d.PerturbationMeasure })+0.1]))
			.xAxisLabel("Endpoint Value")
			.yAxisLabel("Frequency")
			.xUnits(function(){return 6;})
			.elasticY(true)
			.gap(100);
	
	smProteinSecretionDataTable.width(350).height(10)
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

//6.Crossfilter for cell page with Single cell protein secretion profiling assay	
function getCellProteinSecretionCF(jsonData){
	$("#downloadSummary").show();
	$("#downloadSummary").css( { marginLeft : "1010px"} );
	
	cfTableId = "cellProteinSecretionDataTable";	
	
	var data = eval(jsonData);
	var filter = crossfilter(data);
	
	cellProteinSecretionConPieChart = dc.pieChart("#cellProteinSecretionConPieChart");
	cellProteinSecretionSmallMoleculePieChart = dc.pieChart("#cellProteinSecretionSmallMoleculePieChart");
	cellProteinSecretionProteinPieChart = dc.pieChart("#cellProteinSecretionProteinPieChart");
	cellProteinSecretionPMBarChart = dc.barChart("#cellProteinSecretionPMBarChart");
	
	cellProteinSecretionDataTable = dc.dataTable("#cellProteinSecretionDataTable");
	
	//for concentration
	var concentrationDim = filter.dimension(function (d) {
		return d.Concentration;
    });
    var concentrationDimGrp = concentrationDim.group();
	
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

	cellProteinSecretionConPieChart.width(300)
		  .height(200)
		  .transitionDuration(500)
		  .ordinalColors(chartColors)
		  .radius(80)
		  .innerRadius(30)
		  .dimension(concentrationDim)
		  .group(concentrationDimGrp)
		  .renderLabel(true)
		  .renderTitle(true);	
	
	cellProteinSecretionSmallMoleculePieChart.width(300)
		  .height(200)
		  .transitionDuration(500)
		  .ordinalColors(chartColors)
		  .radius(80)
		  .innerRadius(30)
		  .dimension(smallMoleculeDim)
		  .group(smallMoleculeDimGrp)
		  .renderLabel(true)
		  .renderTitle(true);	
	
	cellProteinSecretionProteinPieChart.width(500)
	 	  .height(200)
		  .transitionDuration(500)
		  .ordinalColors(chartColors)
		  .radius(80)
		  .innerRadius(30)
		  .dimension(proteinNameDim)
		  .group(proteinNameDimGrp)
		  .renderLabel(true)
		  .renderTitle(true);
	
	cellProteinSecretionPMBarChart.width(555)
			.height(530)
			.dimension(perturbationMeasureDim)
			.group(perturbationMeasureDimGrp)
			.transitionDuration(1500)
			.x(d3.scale.linear()
			.domain([d3.min(data, function(d) {return d.PerturbationMeasure })-0.1, d3.max(data, function(d) {return d.PerturbationMeasure })+0.1]))
			.xAxisLabel("Endpoint Value")
			.yAxisLabel("Frequency")
			.xUnits(function(){return 6;})
			.elasticY(true)
			.gap(100);
	
	cellProteinSecretionDataTable.width(350).height(10)
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

//7.Crossfilter for anitibody page with Single cell protein secretion profiling assay	
function getAntibodyProteinSecretionCF(jsonData){
	$("#downloadSummary").show();
	$("#downloadSummary").css( { marginLeft : "1010px"} );
	
	cfTableId = "abProteinSecretionDataTable";	
	
	var data = eval(jsonData);
	var filter = crossfilter(data);
	
	abProteinSecretionConPieChart = dc.pieChart("#abProteinSecretionConPieChart");
	abProteinSecretionSmallMoleculePieChart = dc.pieChart("#abProteinSecretionSmallMoleculePieChart");
	abProteinSecretionCellPieChart = dc.pieChart("#abProteinSecretionCellPieChart");
	abProteinSecretionPMBarChart = dc.barChart("#abProteinSecretionPMBarChart");
	
	abProteinSecretionDataTable = dc.dataTable("#abProteinSecretionDataTable");
	
	//for concentration
	var concentrationDim = filter.dimension(function (d) {
		return d.Concentration;
    });
    var concentrationDimGrp = concentrationDim.group();
	
   //for smallmolecule name
	var smallMoleculeDim = filter.dimension(function (d) {
		return d.SmallMoleculeName;
    });
    var smallMoleculeDimGrp = smallMoleculeDim.group();
    
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
	
	//for DataTable
	var tableDimension = filter.dimension(function (d) { return d.id; });

	abProteinSecretionConPieChart.width(300)
		  .height(200)
		  .transitionDuration(500)
		  .ordinalColors(chartColors)
		  .radius(80)
		  .innerRadius(30)
		  .dimension(concentrationDim)
		  .group(concentrationDimGrp)
		  .renderLabel(true)
		  .renderTitle(true);	
	
	abProteinSecretionSmallMoleculePieChart.width(300)
		  .height(200)
		  .transitionDuration(500)
		  .ordinalColors(chartColors)
		  .radius(80)
		  .innerRadius(30)
		  .dimension(smallMoleculeDim)
		  .group(smallMoleculeDimGrp)
		  .renderLabel(true)
		  .renderTitle(true);	
	
	abProteinSecretionCellPieChart.width(500)
	 	  .height(200)
		  .transitionDuration(500)
		  .ordinalColors(chartColors)
		  .radius(80)
		  .innerRadius(30)
		  .dimension(cellLineDim)
		  .group(cellLineDimGrp)
		  .renderLabel(true)
		  .renderTitle(true);
	
	abProteinSecretionPMBarChart.width(555)
			.height(530)
			.dimension(perturbationMeasureDim)
			.group(perturbationMeasureDimGrp)
			.transitionDuration(1500)
			.x(d3.scale.linear()
			.domain([d3.min(data, function(d) {return d.PerturbationMeasure })-0.1, d3.max(data, function(d) {return d.PerturbationMeasure })+0.1]))
			.xAxisLabel("Endpoint Value")
			.yAxisLabel("Frequency")
			.xUnits(function(){return 6;})
			.elasticY(true)
			.gap(100);
	
	abProteinSecretionDataTable.width(350).height(10)
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


function downloadCFData(){
	$('#' + cfTableId ).table2CSV();
}
