$(document).ready(function() {
	updateTab();
});

var requestCount = 0;
var profileCount = 0;
var profileCountMax = 0;
var startPage = 0;
var noOfPages = 0;
var lastRecord = 0;
var firstRecord = 0;
var isSorted = "false"
	
	
jQuery.ajax({
    url: "https://bioassayontology.atlassian.net/s/en_USlij5o1-418945332/6001/107/1.3.2/_/download/batch/com.atlassian.jira.collector.plugin.jira-issue-collector-plugin:issuecollector-embededjs/com.atlassian.jira.collector.plugin.jira-issue-collector-plugin:issuecollector-embededjs.js?collectorId=ba9d811d",
    type: "get",
    cache: true,
    dataType: "script"
}); 


function highlightRow(cbox)
{
	if(cbox.checked)
		cbox.parentNode.parentNode.setAttribute('style', 'background: #39A4E1');
	else
		cbox.parentNode.parentNode.setAttribute('style', 'background: #FFFFFF');
}
function editList(){
	$.post("update-list", $('#worksetForm').serialize(), worksetResult);
//	var listItem= $('#worksetForm #itemId').val();
//	compoundList= compoundList.concat(','+listItem);
//	console.log(compoundList);
//	return compoundList;
	return false;
}	

function worksetResult(response){
	$('#message').show();
	setTimeout("$('#message').fadeOut('slow')", 4000);

	if($.trim(response).substr(0,5) == "ERROR")
		$('#message').removeClass().addClass('errorHighlight');
	else
		$('#message').removeClass().addClass('successHighlight');
	$('#message').html(response);
}

$(function() {
    $("#goConcept").click(function()
    {
		submitForm();
	});
    
    // launch method on keypress
    $("#search-box").keypress(function() {
   onSuggestion();
    	});
    
    // Custom Autocomplete for inserting the header concepts
    $.widget( "custom.conceptscomplete", $.ui.autocomplete, {
      
        _renderMenu: function( ul, items ) {
             var self = this;
             $.each( items, function( index, item ) {
                 if (index == 0)
                     ul.append( '<li class=\'list-divider ui-bar-b padding-vertical-s\'>Concepts</li>' );
                 self._renderItem( ul, item );
             });
         }
    });
    
  // Custom Autocomplete for inserting the header terms
    $.widget( "custom.termscomplete", $.ui.autocomplete, {
      
        _renderMenu: function( ul, items ) {
             var self = this;
             $.each( items, function( index, item ) {
                 if (index == 0)
                     ul.append( '<li class=\'list-divider ui-bar-b padding-vertical-s\'>Terms</li>' );
                 self._renderItem( ul, item );
             });
         }
    });
    
    $( ".qDefault" ).termscomplete({
		source: function( request, response ) {
        $.ajax({
            url: "http://lincs.ccs.miami.edu:8080/solr-example/suggest?wt=json&json.wrf=?",
            dataType: "jsonp",
            data: {
                q: request.term,
                max: 10
            },
            success: function( data ) {
                response( $.map( data.spellcheck.suggestions[1].suggestion, function( suggestion ) {
                    return {
                    	label: suggestion,
                        value: suggestion
                    }
                }));
            }
        });
    },
    appendTo: $('#suggestions'),
    minLength: 3
});
    
    $( ".qConcept" ).conceptscomplete({
        source: suggestJSON,
        minLength: 2,
        appendTo: $('#suggestions'),
        focus:function(e,ui) {
            $("input").val($("#ui-active-menuitem").val());
            q = $("#ui-active-menuitem").html();
            $("#ui-active-menuitem").attr('title', ui.item.tree);
    
        },
        
    
    });
    
 // Handle placeholder delete
    $('.qConcept').click(function(){
    	
    	if( $(this).val() == 'Search across all LINCS data: compounds,cells,genes...')
    		$(this).val('');
    	
    });
    
    // fix issue on list item click overlapping
    
    $("ul.ui-autocomplete").click(function()
    	    {
    			$("ul.ui-autocomplete").hide();
    		});


});

function toggleSuggest()
{
	$(".suggestContainer").toggle('slow');
	$(".qDefault").attr('name','qDefault');
	$(".qConcept").attr('name','q');
}



function validateSearch()
{
	/**
	 * if($("#q").val().length < 1) { alert("No search term selected. Please
	 * type in a search term and press enter"); return false; } return true;
	 */
}


/**
 * Utility function to remove all spaces in the string passed in and convert the
 * next character to uppercase this making it camelcase
 * 
 * @param (String)
 *            The string to be converted to camelcase
 */
function camelcase( s ) { s = $.trim(s);
	return ( /\S[A-Z]/.test( s ) ) ?
	s.replace( /(.)([A-Z])/g, function(t,a,b) { return a + ' ' + b.toLowerCase(); } ) :
	s.replace( /( )([a-z])/g, function(t,a,b) { return b.toUpperCase(); } );
}


function paginationCallback(oSettings, iStart, iEnd, iMax, iTotal, sPre){
	// for displaying page information
	noOfPages =  Math.ceil(iTotal / 25);
	startPage =  Math.ceil(iEnd / 25);
	
	$("#pageNum").html("Showing page " +startPage+" of " +noOfPages);
	
	// for giving call to data fetcher based on number of records displayed at a
	// time
	firstRecord = iStart;
	lastRecord = iEnd;
	
	var type = $("#type").val();
	//if( (type == "Protein" || type == "SmallMolecule" || type == "Gene" || type == "CellLine") && isSorted == "false"){
	//		fetchProfiles();
	//}
}


/*
 * do not call fetchProfiles() on column sort
 */
function fnSort(){
	isSorted = "true";
}

/*
 * call fetchProfiles() on page change i.e when user clicks on next/prev buttons
 */
function fnPage(){
	unCheckAll();
	isSorted = "false";
}

function checkAll(status) {
	$("#dynamic input").each( function() {
		$(this).attr("checked",status);
	})
}

/*
 * uncheck the check boxes on pagination
 */
function unCheckAll() {
	$("#dynamic input").each( function() {
		$(this).attr("checked",false);
	});
	
}

// submit form on suggestion click
function onSuggestion(){
	$("ul.ui-autocomplete").click(function()  {
	var loadParameter = 'AssayTypeName';
	$("#searchForm input[name=load]").val(loadParameter);	
	submitForm();
 		});
	
	$('#search-box').on('keyup', function(e){
		e.preventDefault();
		
		if(e.keyCode == 13){
			var loadParameter = 'AssayTypeName';
			$("#searchForm input[name=load]").val(loadParameter);	
			submitForm();
		}
			
	});

	}

//submit form

function submitForm(){
	var searchTerm= $("#searchForm input[name=search]").val();
	if(searchTerm!= undefined)
	searchTerm = searchTerm.replace(/\"/g, "&quot;");					//handle special characters
	$("#searchForm input[name=search]").val(searchTerm);			//handle special characters
	$("#searchForm input[name=q]").val(searchTerm);
	$("#searchForm").submit();
}

// update active status of nav tabs

function updateTab() {
	var sPath = window.location.pathname;
	var current = sPath.substring(sPath.lastIndexOf('/') + 1);
	if (current != '') {
		$('ul#navbar li').removeClass('active');
		$('#' + current + '-tab').addClass('active');
	}
	
function getURLParameter(name) {
		  return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search)||[,""])[1].replace(/\+/g, '%20'))||null
		}
}

function setCookie(){
    // check cookie
    var visited = $.cookie("visited");
    
    if (visited == null) {
    	openPresentation();
    }else {
    	$("#presentationDialog").html("");
    }
}

function openPresentation(){
	
	$("#presentationDialog").dialog({
		 modal: true,
		 height:454,
		 width:565,
		 closeOnEscape: true,
		 zIndex: 1030,
		 buttons: {
			 Close: function() {
				$(this).dialog( "close" );
			 }
		 }
	});
	
	// set session cookie
    $.cookie('visited', 'yes', {path: '/' });
}
