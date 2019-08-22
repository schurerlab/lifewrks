jQuery.fn.table2CSV = function(options) {
    var options = jQuery.extend({
        separator: ',',
        header: [],
        delivery: 'text/csv' 
    },
    options);
    var csvData = [];
    var headerArr = [];
    var el = this;
    //header
    var numCols = options.header.length;
    var tmpRow = []; // construct header avalible array
    if (numCols > 0) {
        for (var i = 0; i < numCols; i++) {
            tmpRow[tmpRow.length] = formatData(options.header[i]);
        }
    } else {
        $(el).filter(':visible').find('th').each(function() {
            if ($(this).css('display') != 'none') tmpRow[tmpRow.length] = formatData($(this).html());
        });
    }
    row2CSV(tmpRow);
    // actual data
    $(el).find('tr').each(function() {
        var tmpRow = [];
        $(this).filter(':visible').find('td').each(function() {
            if ($(this).css('display') != 'none') tmpRow[tmpRow.length] = formatData($(this).html());
        });
        row2CSV(tmpRow);
    });
    var dataAsCSV = csvData.join('\n');
    generateReportCSV(dataAsCSV, "data.csv", options.delivery);
    return true;

    function row2CSV(tmpRow) {
        var tmp = tmpRow.join('') // to remove any blank rows
        if (tmpRow.length > 0 && tmp != '') {
            var mystr = tmpRow.join(options.separator);
            csvData[csvData.length] = mystr;
        }
    }
    function formatData(input) {
        // replace " with “
        var regexp = new RegExp(/["]/g);
        var output = input.replace(regexp, "“");
        //HTML
        var regexp = new RegExp(/\<[^\<]+\>/g);
        var output = output.replace(regexp, "");
        if (output == "") return '';
        return '"' + output + '"';
    }

    //convert report data string to csv file
    function generateReportCSV(strData, strFileName, strMimeType){         
            var D = document,
                    a = D.createElement("a");
                    strMimeType= strMimeType || "application/octet-stream";
            if (navigator.msSaveBlob) { // IE10+
                    return navigator.msSaveBlob(new Blob([strData], {type: strMimeType}), strFileName);
            } /* end if(navigator.msSaveBlob) */
            if ('download' in a) { //html5 A[download]
                    a.href = "data:" + strMimeType + "," + encodeURIComponent(strData);
                    a.setAttribute("download", strFileName);
                    a.innerHTML = "downloading...";
                    D.body.appendChild(a);
                    setTimeout(function() {
                            a.click();
                            D.body.removeChild(a);
                    }, 66);
                    return true;
            } /* end if('download' in a) */
            //do iframe dataURL download (old ch+FF):
            var f = D.createElement("iframe");
            D.body.appendChild(f);
            f.src = "data:" +  strMimeType   + "," + encodeURIComponent(strData);
            setTimeout(function() {
                    D.body.removeChild(f);
            }, 333);
            return true;
    } 
};