/*
        this function requires jquery plugin for xslt transforms,
        located in file jquery.xslt.js
*/

function BAgetMentions(tickerId) {
    $.ajax({
        url:'/get-mentions.xml',
        dataType:"xml",
        data:{ "ticker":tickerId},
        success:function (mentions) {
            $("#losers-and-rulers").xslt({xml: mentions, xslUrl: 'show-brands-mention.xsl'});
        }
    });
}