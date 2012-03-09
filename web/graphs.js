/**
 * Created by IntelliJ IDEA.
 * User: dima
 * Date: 2/4/12
 */

var DEFAULT_TICKER = 1;

$(document).ready(function () {
    //alert(ticker);
    getGraph(1);
});


function getParam(name) {
    name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
    var regexS = "[\\?&amp;]" + name + "=([^&amp;#]*)";
    var regex = new RegExp(regexS);
    var results = regex.exec(window.location.href);
    if (results == null)
        return "";
    else
        return results[1];
}

function plotGraph(jsonData) {
    var data = $.parseJSON(jsonData);

    window.chart = new Highcharts.StockChart({
        chart:{
            renderTo:'chartContainer'
        },
        rangeSelector:{
            selected:1
        },
        title:{
            text:data.brand.brand
        },
        series:[
            {
                name:'Упоминаний',
                data:data.chart.graph,
                tooltip:{
                    yDecimals:2
                }
            }
        ]
    });

}

function graph() {
    getGraph($("#graph-select").val());
}

function getGraph(tickerId) {
    $.ajax({
        url:'/get-graphs.xml',
        dataType:"xml",
        data:{ "brand": getParam("id"), "ticker":tickerId},
        success:function (xml) {
            $(xml).find('data').each(function () {
                var data = $(this).text();
                plotGraph(data);
            });
        }

    });
}