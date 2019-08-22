function loadCanvasBrowser(solr_participant_id){
	
	$.getScript("/life/web/js/canvas/general/jquery.1.8.2.min.js", function(){});
	$.getScript("/life/web/js/canvas/general/underscore-min.js", function(){});
	$.getScript("/life/web/js/canvas/general/d3.v3.4.9.js", function(){});
	$.getScript("/life/web/js/canvas/general/jquery.blockUI.js", function(){});
			
	$.getScript("/life/web/js/canvas/general/html5slider.js", function(){});  
	$.getScript("/life/web/js/canvas/general/curveBelow.js", function(){});
	$.getScript("/life/web/js/canvas/general/tar.js", function(){});
	$.getScript("/life/web/js/canvas/general/visualizer17.js", function(){});
	$.getScript("/life/web/js/canvas/KEGG/KEGG_Pathway.js", function(){});
	$.getScript("/life/web/js/canvas/KEGG/kegg_pathways_dict.js", function(){});
	$.getScript("/life/web/js/table2CSV.js", function(){});
	
	setTimeout(function(){

		d3.select('.blockMsg').select('h1').text('Waiting for server response...');
		$.getJSON('/life/web/js/canvas/data/' + solr_participant_id + '.json',
			function (response) {
				canvasBrowser(response);
		});
		
		$.unblockUI();
		
		$.blockUI({ css: { 
			border: 'none', 
			padding: '15px', 
			backgroundColor: '#000', 
			'-webkit-border-radius': '10px', 
			'-moz-border-radius': '10px', 
			opacity: .5, 
			color: '#fff' 
		} }); 
		
		document.getElementById("selectCanvas").selectedIndex = 9;
		//getGMT(2);
		//getJSON(2, [0, 255, 255], [255, 255, 255])
		
		G_VAR = initializeIt(KEGG);
		G_VAR.scale = Math.log(0.25)/Math.log(G_VAR.avgWeight);
		
		for (var nI = 0; nI < G_VAR.nodes.length; nI++){
			G_VAR.nodes[nI].colorizer(G_VAR.scale, G_VAR.canvasRGB);
			//console.log(nI)
		}
		
		visualizeIt(G_VAR);
		document.getElementById("svgContainer").style.display="block";
		document.getElementById("mainSVG").style.display="inline";
		G_VAR.infos = KEGGGMT;
		uG = false;
		dG = false;
		
		storeFact = [0,0];
		REGLOCK = 'UP'
		
	},2000);		
}