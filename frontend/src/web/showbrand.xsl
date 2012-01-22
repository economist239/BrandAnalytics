<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>
    <xsl:include href="common.xsl"/>

    <xsl:template name="main">
        <xsl:apply-templates select="page/data[@id='wideBrandInfo']" mode="show"/>
        <xsl:apply-templates select="page/data[@id='wideBrandInfo']/collection[1]" mode="show"/>
    </xsl:template>

    <xsl:template name="head">
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js"></script>
        <script type="text/javascript">
            function parameter( name )
            {
            name = name.replace(/[\[]/,"\\\[").replace(/[\]]/,"\\\]");
            var regexS = "[\\?&amp;]"+name+"=([^&amp;#]*)";
            var regex = new RegExp( regexS );
            var results = regex.exec( window.location.href );
            if( results == null )
            return "";
            else
            return results[1];
            }

            function plotGraph(data){
            var data=jQuery.parseJSON( data );

            window.chart = new Highcharts.StockChart({
            chart : {
            renderTo : 'chartContainer'
            },
            rangeSelector : {
            selected : 1
            },
            title : {
            text : data.brand.brand
            },
            series : [{
            name : 'Упоминаний',
            data : data.chart.graph,
            tooltip: {
            yDecimals: 2
            }
            }]
            });

            };

            function getGraph(){
            var ticker = document.getElementById("graph-select").value;

            <!--alert(ticker);-->
            <!--alert(parameter("id"));-->
            $.ajax({
            url: '/get-graphs.xml',
            dataType : "xml",

            data : { "brand" : parameter("id"), "ticker" : ticker},

            success: function (xml) {
            $(xml).find('data').each(function() {
                var data = $(this).text();
                plotGraph(data);
            });
            }

            });
            };


        </script>
    </xsl:template>

    <xsl:template match="page/data[@id='wideBrandInfo']" mode="show">
        <xsl:for-each select="brand">
            <div class="content">
                <h3>
                    <a>
                        <xsl:attribute name="href">
                            <xsl:value-of select="website"/>
                        </xsl:attribute>
                        <xsl:value-of select="name"/>
                    </a>
                </h3>
                <p>
                    <xsl:value-of select="description"/>
                    <br/>
                </p>
            </div>
        </xsl:for-each>

        <hr/>
        <h3>
            Анализ
        </h3>
        <script type="text/javascript">
            getGraph();
        </script>

        <xsl:for-each select="collection[2]">
            <select id="graph-select">
                <xsl:for-each select="ticker-pair">   <!--id,name-->
                    <option size="60">
                        <xsl:attribute name="value">
                            <xsl:value-of select="@id"/>
                        </xsl:attribute>
                        <xsl:value-of select="name"/>
                    </option>
                </xsl:for-each>
            </select>
            <button class="btn primary" type="submit" value="посмотреть" onclick="getGraph()">посмотреть</button>
        </xsl:for-each>

        <script type="text/javascript">
            window.onload=getGraph();
        </script>
        <script type="text/javascript" src="highstock/js/highstock.js"></script>
        <script type="text/javascript" src="highstock/js/modules/exporting.js"></script>
        <div id="chartContainer" style="height: 500px; min-width: 500px"/>

        <hr/>
        <script>
            function youtubePage(){
            if (document.location.href.indexOf("&amp;") == -1) {
            return parent.location.href = document.location.href.substring(0,
            document.location.href.indexOf("showbrand")) + "showyoutube.xml" +
            document.location.href.substring(document.location.href.indexOf("?id"), document.location.href.length);
            } else {
            return parent.location.href = document.location.href.substring(0,
            document.location.href.indexOf("showbrand")) + "showyoutube.xml" +
            document.location.href.substring(document.location.href.indexOf("?id"), document.location.href.indexOf("&amp;"));
            }
            }
        </script>
        <h3>
            Реклама с Youtube
        </h3>
        <button class="btn primary" type="submit" value="посмотреть" onclick="youtubePage()">посмотреть</button>
        <hr/>
    </xsl:template>

    <xsl:template match="page/data[@id='wideBrandInfo']/collection[1]" mode="show">
        <h3>Последние новости</h3>
        <table class="zebra-striped">
            <xsl:for-each select="simply-article-for-web">
                <div class="span5">
                    <th valign="top">
                        <a>
                            <xsl:attribute name="href">showarticle.xml?id=<xsl:value-of select="@id"/>
                            </xsl:attribute>
                            <xsl:value-of select="name"/>
                        </a>
                        <br/>
                        <xsl:value-of select="short-content"/>
                    </th>
                </div>
                <xsl:if test="position() mod 3 = 0">
                    <tr/>
                </xsl:if>
            </xsl:for-each>
        </table>
    </xsl:template>
</xsl:stylesheet>
