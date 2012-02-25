<!--
<script type="text/javascript">
    function hideRows(tableLoc, maxRows){
        var locator = tableLoc + " > tbody tr";
        var rowsNum = $(locator).size();
        if(rowsNum > maxRows){
            $(locator+ ":gt(5)").css("display","none");
        }
    }
    $(document).ready(function(){
        $(a).click(hideRows("table#up-lead",5))
    })
    $(document).ready(hideRows("table#down-lead",5))
</script>
-->

<!--
            function hideRows(tableLoc, maxRows){
                var locator = tableLoc + " > tbody tr";
                var rowsNum = $(locator).size();
                if(rowsNum > maxRows){
                    $(locator+ ":gt(5)").css("display","none");
                }
            }
            -->

            <!--
            $(document).ready(function(){
                $("a").click(hideRows("table#up-lead",5))
            })
            $(document).ready(hideRows("table#down-lead",5))
            -->

            function(){
                                var tableLoc = "table#down-lead";
                                var maxRows = 5;
                                var locator = tableLoc + " > tbody tr";
                                var rowsNum = jQuery(locator).size();
                                if(rowsNum > maxRows){
                                    jQuery(locator+ ":gt(5)").css("display","none");
                                }