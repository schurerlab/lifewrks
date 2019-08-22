var loadValue;

$(function() {
	$("#dynamic").jstree({
		"json_data" : treeJson,
		"plugins" : [ "themes", "json_data", "ui" ]
	}).bind("select_node.jstree", function(e, data) {
	}).bind("loaded.jstree", function(event, data) {
		onTreeLoaded();
	});
	$.jstree._themes = "/life/web/css/jstree-themes/";

});

function onTreeLoaded() {
	$("#animation").hide();
	$("#dynamic ul li a").click(function() {
		var search = $(this).text();
		search = search.replace(/\xa0/g, "");
			
		$.ajax({ url : 'web/js/browse_mapping.json',
			  success : function(response) {
		 		var json = eval(response);
		 		loadValue = json[search];
		 		$('#load').val(loadValue)
			  },
			  complete:function(){
				  $("#searchForm input[name=search]").val('concept:"'+search+'"');
					
					var searchTerm= $("#searchForm input[name=search]").val();
					searchTerm = searchTerm.replace(/\"/g, "&quot;");					//handle special characters
					$("#searchForm input[name=q]").val(searchTerm);
					$("#searchForm input[name=search]").val(searchTerm);			//handle special characters
					$("#searchForm").submit();
			  },
			  dataType : "json",
			  type : "GET",
			  json : 'json.wrf'
		});
	});
}