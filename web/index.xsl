<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:import href="common.xsl"/>
    <!--<xsl:output method="html" indent="yes" encoding="UTF-8"/>-->


    <xsl:template name="head">
        <script type="text/javascript" src="3rd-party/jquery.js"></script>
        <script type="text/javascript" src="3rd-party/crawler.js"/>
        <style type="text/css">
            .redcolor{
            color:red;
            }
            .greencolor{
            color:green;
            }
            .separator{
            border: 0px;
            background-color: rgba(0, 0, 0, 0.05);
            }
        </style>
    </xsl:template>
    <xsl:template name="run">
        <div class="marquee" id="mycrawler" align="center" style="-khtml-border-radius:10px">
            <p>Running text line. Smth: +5; Anth: -1233; Smth: +5; Anth: -1233; Smth: +5; Anth: -1233; Smth: +5; Anth: -1233; Smth: +5; Anth: -1233; Smth: +5; Anth: -1233; Smth: +5; Anth: -1233;</p>
        </div>
        <script type="text/javascript">
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
        </script>
        <script type="text/javascript">
            document.getElementById("navbar_about").setAttribute("class", "active");
        </script>
        <script type="text/javascript">
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
            function makeCollapsable(tableLoc){
                var maxRows = 5;
                var locator = tableLoc + " > tbody tr";
                var rowsNum = jQuery(locator).size();
                if(rowsNum > maxRows){
                    jQuery(locator+ ":gt(5)").attr("class","collapsable");
                }
            }

            jQuery(document).ready(function(){
                makeCollapsable("table#down-lead");
                jQuery("tr.collapseController").click(function(){
                    jQuery("tr.collapsable").toggle();
                })
            })
        </script>

    </xsl:template>
    <xsl:template name="main"><!--
        <h4>Бренд Аналитик - инновация настоящего, парадигма будущего.</h4>
        <br/>
        <img src="captain-obvious.jpeg" width="400" height="400"/> -->
        <span align="right" id="a">Рейтинги упоминаемости по источнику
            <select class="input-small">
                <option>по всем</option>
                <option>Яндекс Новости  </option>
                <option>РИА Новости </option>
            </select>
        </span>

        <div>
        <xsl:apply-templates select="page/data[@id='getLatestMentions']/collection"/>
        </div>
    </xsl:template>
    <xsl:include href="includes/showbrandsmention.xsl"/>

    <xsl:template name="leftmenu">
        <table class="table table-bordered">
            <thead>
                <tr><th><xsl:text>Новости</xsl:text></th></tr>
            </thead>
            <tbody>
                <xsl:apply-templates select="page/data[@id='showArticle']" mode="show"/>
            </tbody>
        </table>
    </xsl:template>

    <xsl:template match="page/data[@id='showArticle']" mode="show">
        <xsl:for-each select="wide-article-for-web">
            <tr><td>
                <h3>
                    <a>
                        <xsl:attribute name="href">
                            <xsl:value-of select="link"/>
                        </xsl:attribute>
                        <xsl:value-of select="title"/>
                    </a>
                </h3>
                <p>
                    <xsl:value-of select="content"/>
                </p>
                <div align="right">
                    <p>
                        Дата публикации:
                        <xsl:value-of select="time"/>
                    </p>
                </div>
            </td></tr>
        </xsl:for-each>
    </xsl:template>

</xsl:stylesheet>
