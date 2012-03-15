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
                jQuery(".collapseController").click(function(){
                    var loc = jQuery(this).attr("table-target");
                    jQuery(loc + " tr.collapsable").toggle();
                })
            })