/**
 * Created by IntelliJ IDEA.
 * User: dima
 * Date: 2/4/12
 */
                      /*
var DEFAULT_TICKER = 1;

$(document).ready(function () {
    getGraph(DEFAULT_TICKER);
});                -
                    */

function BAgetParam(name) {
    name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
    var regexS = "[\\?&amp;]" + name + "=([^&amp;#]*)";
    var regex = new RegExp(regexS);
    var results = regex.exec(window.location.href);
    if (results == null)
        return "";
    else
        return results[1];
}

function BAplotGraph(jsonData) {
    var data = $.parseJSON(jsonData);
    var tSeries = [];
    for(var i in data.charts){
        var temp = [];
        for(var j in data.charts[i].chart.graph){
            temp.push(data.charts[i].chart.graph[j])
        }
        tSeries.push({name:data.charts[i].chart.ticker,data:temp});
    }

    window.chart = new Highcharts.StockChart({
         chart: {
        		        renderTo: 'chartContainer'
        		    },

        		    rangeSelector: {
        		        selected: 4
        		    },

        		    yAxis: {
        		    	labels: {
        		    		formatter: function() {
        		    			return (this.value > 0 ? '+' : '') + this.value;
        		    		}
        		    	},
        		    	plotLines: [{
        		    		value: 0,
        		    		width: 2,
        		    		color: 'silver'
        		    	}]
        		    },
        		    tooltip: {
        		    	pointFormat: '<span style="color:{series.color}">{series.name}</span>: <b>{point.y}</b> <br/>',
        		    	valueDecimals: 2
        		    },

        		    series: tSeries
        		});

}

function BAgetGraph() {
    var tickerIds = {}
    $("#graph-select button.active").each(function(index){
        var b = 0;
        var cur = $(this).attr("value");
        for(var i in tickerIds){
            if(tickerIds[i]==cur) --b;
        }
        if(b==0){
            tickerIds[index] = cur;
        }
    });
    BAgetGraphArgMulti(tickerIds);
}

function BAgetGraphArgMulti(tickerIds){
    var dataRow = "";
    dataRow += "&brand=" + BAgetParam("id");
    for(var i in tickerIds){
        dataRow += "&ticker=" + tickerIds[i];
    }
    $.ajax({
        url:'/get-graphs.xml',
        dataType:"xml",
        data: dataRow,
        success:function (xml) {
            $(xml).find('data').each(function () {
                var data = $(this).text();
                BAplotGraph(data);
            });
        }

    });
}

function BAgetGraphArg(tickerId) {
    $.ajax({
        url:'/get-graphs.xml',
        dataType:"xml",
        data:{ "brand": BAgetParam("id"), "ticker":tickerId},
        success:function (xml) {
            $(xml).find('data').each(function () {
                var data = $(this).text();
                BAplotGraph(data);
            });
        }

    });
}