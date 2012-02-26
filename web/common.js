$(function() {
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
});