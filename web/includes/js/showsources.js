document.getElementById("navbar_sources").setAttribute("class", "active");

var officialSourcesId = 1;
var publicSourcesId = 0;
function hideInfoSourcesBySphereId(sphereId){
    jQuery("tr.info-source-row[sphere-id=" + sphereId + "]").css("display","none")
}

function showInfoSourcesBySphereId(sphereId){
    jQuery("tr.info-source-row[sphere-id=" + sphereId + "]").css("display","table-row")
}

jQuery(document).ready(function(){
    hideInfoSourcesBySphereId(  officialSourcesId )
    showInfoSourcesBySphereId(  publicSourcesId )
    jQuery("#tab_public-sources").attr("class","active");
})

jQuery(document).ready(
    jQuery("#tab_public-sources").click(function(){
        hideInfoSourcesBySphereId(  officialSourcesId )
        showInfoSourcesBySphereId(  publicSourcesId )
    }
))
jQuery(document).ready(
    jQuery("#tab_official-sources").click(function(){
        hideInfoSourcesBySphereId(  publicSourcesId )
        showInfoSourcesBySphereId(  officialSourcesId )
    }
))
