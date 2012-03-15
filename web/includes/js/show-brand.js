$(document).ready(function(){
    $("li#branch-select li").click(function(){
        var branchId = $(this).children("a").attr("id");
        if(branchId=="0"){
            $("li#brand-select li").css("display","block");
        }
        else{
            $("li#brand-select li").css("display","none");
            $("li#brand-select li[branch-id=" + branchId + "]").css("display","block");
        }
    })
})