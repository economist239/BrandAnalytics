<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>
    <xsl:include href="common.xsl"/>

    <xsl:template name="main">
        <xsl:apply-templates select="page/data[@id='wideBrandInfo']" mode="show"/>
        <xsl:apply-templates select="page/data[@id='wideBrandInfo']/collection[1]" mode="show"/>
    </xsl:template>

    <xsl:template name="head">
        <script src="raphael/raphael.js"></script>
        <script src="raphael/popup.js"></script>
        <script src="raphael/jquery.js"></script>
        <script src="raphael/analytics.js"></script>
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
        <script>
            function func(){
            var val = document.getElementById("graph-select").value;
            if(document.location.href.indexOf("&amp;") == -1) {
            return parent.location.href = document.location.href +"&amp;ticker_id=" + val;
            } else {
            return parent.location.href = document.location.href.substring(0, document.location.href.indexOf("ticker"))
            +"ticker_id=" + val ;
            }
            }
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

            <button class="btn primary" type="submit" value="посмотреть" onclick="func()">посмотреть</button>

        </xsl:for-each>


        <xsl:for-each select="graph-for-web">
            <h5>
                <xsl:value-of select="name"/>
            </h5>
            <table id="data">
                <tfoot>
                    <tr>
                        <xsl:for-each select="date/string">
                            <th>
                                <xsl:value-of select="text()"/>
                            </th>
                        </xsl:for-each>
                    </tr>
                </tfoot>
                <tbody>
                    <tr>
                        <xsl:for-each select="value/double">
                            <td>
                                <xsl:value-of select="text()"/>
                            </td>
                        </xsl:for-each>
                    </tr>
                </tbody>
            </table>
            <div id="holder" class="1"></div>
        </xsl:for-each>
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