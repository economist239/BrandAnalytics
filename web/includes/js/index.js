marqueeInit({
    uniqueid: 'mycrawler',
    style: {
    'width': '700px',
    'background': 'lightgrey',
    'border': '0px solid #CC3300'
    },
    inc: 6,
    mouse: 'cursor driven',
    moveatleast: 2,
    neutral: 150,

    savedirection: true
});

function hideUp(){
    var tableLoc = "table#up-lead";
    var maxRows = 5;
    var locator = tableLoc + " > tbody tr";
    var rowsNum = jQuery(locator).size();
    if(rowsNum > maxRows){
        jQuery(locator+ ":gt(5)").css("display","none");
    }
}
function hideDown(){
    var tableLoc = "table#down-lead";
    var maxRows = 5;
    var locator = tableLoc + " > tbody tr";
    var rowsNum = jQuery(locator).size();
    if(rowsNum > maxRows){
        jQuery(locator+ ":gt(5)").css("display","none");
    }
}
function makeCollapsable(tableLoc,maxRows){
    var locator = tableLoc + " > tbody tr";
    var rowsNum = jQuery(locator).size();
    if(rowsNum > maxRows){
        jQuery(locator + ":gt(" + maxRows + ")").attr("class","collapsable");
    }
}

jQuery(document).ready(function(){
    document.getElementById("navbar_main").setAttribute("class", "active");
    makeCollapsable("table#down-lead",3);
    makeCollapsable("table#up-lead",3);
    jQuery(" tr.collapsable").toggle();
    /*jQuery(".collapseController").click(function(){
        var loc = jQuery(this).attr("table-target");
        jQuery(loc + " tr.collapsable").toggle();
    }) */
    jQuery("#select-ticker-for-mentions").change(function(){
        var val = $("#select-ticker-for-mentions :selected");
        BAgetMentions(val.attr("id"));
    })
    BAgetMentions(1);
})



/*
        this function requires jquery plugin for xslt transforms,
        located in file jquery.xslt.js
*/
function BAgetMentions(tickerId) {
    $.ajax({
        url:'/includes/get-mentions.xml',
        dataType:"xml",
        data:"ticker=" + tickerId,
        success:function (x) {
            var tt = $.xslt(x,"includes/show-brands-mention.xsl");
            $("#losers-and-rulers").text(tt);
        }
    });
}

