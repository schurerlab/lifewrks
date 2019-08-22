
var canvasData; 
$.blockUI({ css: { 
		border: 'none', 
		padding: '15px', 
		backgroundColor: '#000', 
		'-webkit-border-radius': '10px', 
		'-moz-border-radius': '10px', 
		opacity: .5, 
		color: '#fff' 
	} });

	
    //-------------------------------Global variable definitions----------------------------------

    var x; 
    var Id; 
   // var  upGenes; 
   // var downGenes; 
   // var conditions; 
   // var w; 
   // d3.select('#batchSelect').selectAll('option')
   //        .property('selected',function(d,i){return i==g_batchSelectIdx?true:false;});
  

	var g_rectIdx=-1;
	var g_cellType=0;
	var g_category = 0;
	var g_selectedRect;//??
	
	var legendMap = [];
	
	var size=300;
   
	var margin=size/17;
	var h_padding=63; //height of below panel
   
	var k;//k tiles in each row and column
	var tileLength; // length of each square tiles
   
	var avgChange_max;
	var avgChange_min;
	
	var infoAreaWidth = size/3;
	var infoAreaHeight = size;   
	var textPanel;
   
   
	var g_contrast=1; //??
	var avg_scale=d3.scale.pow().exponent(1).domain([0.01,1])
									.range(['black','red']);
	var grid;
				
	var tiles;
	
	var colorScheme;
	var timePoints;
	var compounds;
	var compoundsColors;
	
function canvasBrowser(tarDataJSON) {

	  canvasData = tarDataJSON;
	  canvasData.forEach(function(e){
		e.averageChange = parseFloat(e.averageChange);
		e.row = parseInt(e.row);
		e.column = parseInt(e.column);
		e.score = parseFloat(e.score);
	  });
	  canvasData.sort(function(a,b){
		if(a.row==b.row)
		  return a.column - b.column;
		else
		  return a.row - b.row;
	  });

		showCanvasBrowser();
		$.unblockUI();
	}

	function showCanvasBrowser() {
	
		timePoints = _.pluck(canvasData,'timePoint');
		//unique and sorted time points
		timePoints = d3.set(timePoints).values().sort(function(a,b){
			return parseFloat(a[a.length-1]) - parseFloat(b[b.length-1]);
		});

		timePoints = d3.set(timePoints).values().sort();
		
		timePointsColors = [];
		// generate random color for each timePoints
		for(var i=0; i<timePoints.length;i++) {
				var color = '#'+Math.floor(Math.random()*16777215).toString(16);
				if(_.indexOf(timePointsColors,color)==-1)
						timePointsColors.push(color);
		};
	
		var tempObj = {};
		tempObj.items = timePoints;
		tempObj.colors = timePointsColors;
		legendMap.push(tempObj);
	  
		compounds = _.pluck(canvasData, 'compound');
		//unique
		compounds = d3.set(compounds).values().sort();
		compoundsColors = [];
		// generate random color for each compound
		for(var i=0; i<compounds.length;i++){
		  // (16777215 == ffffff in decimal)
		  var color = '#'+Math.floor(Math.random()*16777215).toString(16);
		  if(_.indexOf(compoundsColors,color)==-1)
			compoundsColors.push(color);
		};

		var tempObj2 = {};
		tempObj2.items = compounds;
		tempObj2.colors = compoundsColors;
		legendMap.push(tempObj2);
	 
		k = Math.round(Math.sqrt(canvasData.length));//k tiles in each row and column
		tileLength = Math.round(size/k); // length of each square tiles
		avgChange_max = _.max(canvasData,function(e){return e.averageChange}).averageChange;
		avgChange_min = _.min(canvasData,function(e){return e.averageChange}).averageChange;
	
		if($("info_box:contains(svg)")){
			$("#info_box svg").remove();
		}
		textPanel = d3.select('#info_box').append('svg')
										   .attr('width',infoAreaWidth)
											 .attr('height',infoAreaHeight);
		if($("grid:contains(svg)")){
			$("#grid svg").remove();
		}
		
		grid = d3.select("#grid").append("svg")
									.attr("width",size)
									.attr("height",size)
									.attr("id",'grid')
									.append('g');
		tiles=grid.selectAll("rect").data(canvasData);
		tiles.enter().append("rect")
					 .attr("x",function(d,i){ return tileLength*(i%k);})
					 .attr("y",function(d,i){ return tileLength*Math.floor(i/k);})
					 .attr("width",tileLength)
					 .attr("height",tileLength)
					 .attr("fill",function(d){ return avg_scale(d.averageChange/avgChange_max);})
					 .on('click',showInfo)
					.on('mouseover',showInfoMouseOver)
					.on('mouseout', clearInfoMouseOut);
			 
		curveBelow(g_category); 
	}

//-------------------------Tar Functions------------------------------

 function showInfoMouseOver(d,i) {
	if (g_rectIdx != -1) return; // in clicked state
	var cx=this.x.animVal.value;
	var cy=this.y.animVal.value;
	addFocusi(grid,cx,cy);
	showTextPanelRight(this.__data__,cx,cy);   
 }
	 
function clearInfoMouseOut(d,i) {
   if (g_rectIdx != -1) return; // in clicked state
   d3.selectAll('[name=focusi]').remove();
   d3.select('#info_rect').remove(); // remove the information rect.
   d3.selectAll('[name=text_disp]').remove(); // remove all the elements whose name is text_disp, namely the four text lines.
} 
	
function showInfo(d,i) {
   tileInfo(this,i);
}
   
function showInfo2(i,thispassed) {
   tileInfo(thispassed,i);
}

function tileInfo(per_rect,idx) {
   if(g_rectIdx == idx) {
	  document.getElementById("mainCanvasSelector").checked = true;
	  selectAlternateView();
	  indicateClear(G_VAR.nodes);
	  uG = false;
	  dG = false;
	  clearTileInfo();
	  return;
   }

   clearTileInfo();
   g_rectIdx = idx;
   
   g_selectedRect=per_rect;
   var cx=per_rect.x.animVal.value;
   var cy=per_rect.y.animVal.value;
   
   
   addFocusi(grid,cx,cy);
   
   tile_arr= per_rect.__data__;
   
   showTextPanelRight(tile_arr,cx,cy);
   dG = tile_arr.dnGenes;
   uG = tile_arr.upGenes;
   
   //Note that getJSON sets upIdx and downIdx to false so that the "Up" / "Down" switch does not output an error.
   // interface to communicate with right canvas
   if (REGLOCK == "DOWN"){
	  geneFill(G_VAR.nodes, dG, G_VAR.indicatorColor, G_VAR.infoDict, G_VAR.infos);
   } else {
	  geneFill(G_VAR.nodes, uG, G_VAR.indicatorColor, G_VAR.infoDict, G_VAR.infos);
   //error "Uncaught TypeError: Cannot read property '1' of undefined" if 
   // the gene list is too short and there are no significant enrichment temrs.
   }
}
    
      
function clearTileInfo() {
   g_rectIdx = -1;
   d3.selectAll('[name=focusi]').remove();
   d3.select('#info_rect').remove(); // remove the information rect.
   d3.selectAll('[name=text_disp]').remove(); // remove all the elements whose name is text_disp, namely the four text lines.
   d3.selectAll('[name=frozeMask]').remove();
}
    
var addFocusi=function (svg,cx,cy) {
   
   svg.append('rect').attr('x',cx)
			   .attr('y',cy)
			   .attr("width",tileLength)
			   .attr("height",tileLength)
			   .attr('fill','none')
			   .style('stroke','Azure')
			   .style('stroke-width',1.5)
			   .attr('pointer-events','none')
			   .attr('name','focusi'); 
}

  
function showTextPanelRight(tile_arr,cx,cy) {
   var info_width=size/3.1; // the width of the information rect
   var info_height=size/6; // the height of the information rect
   var cursor_padding=36; // the padding between the position of tile and the positon of the information rect.
   var info_x=0;// x of information rect
   var info_y=cy-cursor_padding; // y of information rect. So it is 36 pixels up the tile.
   var left_padding=size/24; // The padding between the left edge of the text and the left edge of the information rect.
   var bottom_padding=size/20; // The padding between the bottom edge of the text and the top edge of the information rect.
	// Caution: the y of a text is where its bottom line lies, opposite to that of square.
   var line_space=size/23; // space between texts.

	// Adapt rect height according to length of drug name. So work out how many lines drug name occupys first.
   drugTxt = tile_arr.compound;
   drugTxtSplits = drugTxt.match(/.{1,18}/g);
   baseCount = drugTxtSplits.length;

   info_height = info_height/4*(3+baseCount);
   if(info_y<0)info_y=0; //y of information rect must be under the topline of the main square.
   if(info_y>size-info_height) info_y=size-info_height; // y of information rect must be above the bottom of the main square
   
   textPanel.append('rect').attr('width',info_width)
					 .attr('height',info_height)
					 .attr('rx',7)
					 .attr('ry',7)
					 .attr('x',info_x)
					 .attr('y',info_y)  
					 .attr('id','info_rect')
					 .attr('opacity',0.3)
					 .attr('fill','#D90000'); //set background color of the information rect.
	  // the above is to draw the information rect.
   var drugLabelLengthMargin = drugTxt.length - 13;
   var setoff = drugLabelLengthMargin > 0 ? -drugLabelLengthMargin:0;
   if (drugLabelLengthMargin > 7)
	 setoff = -9;
   
   function addTxt(element,idx,arr) {
	  textPanel.append('text').text(element)
					  .attr('x',info_x+left_padding+setoff*1.2+'')
					  .attr('y',info_y+bottom_padding+idx*line_space+'')
					  .attr('name','text_disp');
   }
   drugTxtSplits.forEach(addTxt);
	
   
   textPanel.append('text').text(tile_arr.timePoint+', '+tile_arr.dose)
					 .attr('x',info_x+left_padding+'')
					 .attr('y',info_y+bottom_padding+baseCount*line_space+'')
					 .attr('name','text_disp');
   textPanel.append('text').text('in  '+tile_arr.cellLine)
					 .attr('x',info_x+left_padding+'')
					 .attr('y',info_y+bottom_padding+(baseCount+1)*line_space+'')
					 .attr('name','text_disp');
}

function showCategory(idx) {  
	 
   d3.selectAll('#nav a').filter(function(d,i){ return i==g_category;})
						   .style('text-decoration','');
   g_category = idx;

   d3.selectAll('[name=newlyAppended]').attr('fill',
											 function(d,i){
												var outi = i;
												return tiles.filter(function(d,i){
												   return i == outi%50;
												}
											 ).attr('fill');
											 }).attr('name','');
   //above is for batch switch transition
 
   if (idx == 0)
	 tiles.transition().duration(500).attr('fill',function(d){ return avg_scale(d.averageChange/avgChange_max);});
   else if(idx == 1)
	 tiles.transition().duration(500) .attr('fill',function(d) { return timePointsColors[_.indexOf(timePoints,d.timePoint)];});
   else if(idx == 2)
	 tiles.transition().duration(500) .attr('fill',function(d) { return compoundsColors[_.indexOf(compounds,d.compound)];});
   else if(idx == 3)
	  curveBelow(g_category);
   d3.selectAll('#nav a').filter(function(d,i){ return i==idx;})
						   .style('text-decoration','OverLine');
}

function showCellType(i) {

   clearTileInfo();
   
   alert()
   /*
   w = x[i];
   g_cellType = i;
   w_max=d3.max(w, function(d){return d[5];});
   w_min=d3.min(w, function(d){return d[5];});
   k=Math.sqrt(w.length);
   tileLength = size/k;
   tiles = grid.selectAll("rect").data(w);
   tiles.exit().remove();
   tiles.enter().append('rect').attr('name','newlyAppended')
							   .on('click',showInfo)
							   .on('mouseover',showInfoMouseOver)
							   .on('mouseout', clearInfoMouseOut);
   tiles.attr("x",function(d,i){ return tileLength*(i%k);})
		.attr("y",function(d,i){ return tileLength*Math.floor(i/k);})
		.attr("width",tileLength)
		.attr("height",tileLength);
		
   showCategory(g_category);
   document.getElementById("mainCanvasSelector").checked = true;
   selectAlternateView();
   indicateClear(G_VAR.nodes);
   uG = false;
   dG = false;
   highLightDrug(0,selectedOption) */
}

   function showBatch(i) { /*
	 g_batch = i;
	 i = i==2?3:i;
	 x = ljp[i].x;
	 Id = ljp[i].Id;
	 upGenes = ljp[i].upGenes;
	 downGenes = ljp[i].downGenes;
	 conditions = batchMap[i];

	 grid.selectAll('line').remove();
	 selectedOption = 0;
	 showCellType(g_cellType); */
	 
   }
