<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <!-- <xsl:output method="html" indent="yes" encoding="UTF-8"/>         -->


    <xsl:output method="html" doctype-system="http://www.w3.org/TR/html4/strict.dtd"
                doctype-public="-//W3C//DTD HTML 4.01//EN" indent="yes"/>

    <xsl:key match="/page/items/item" name="item" use="@uid"/>

    <xsl:template match="/">

        <html>
            <head>
                <script src="raphael/raphael.js"></script>
                <script src="raphael/popup.js"></script>
                <script src="raphael/jquery.js"></script>
                <script src="raphael/analytics.js"></script>

                <title>
                    <xsl:text>Бред Аналитик</xsl:text>
                </title>
                <link href="bootstrap/bootstrap.css" rel="stylesheet"/>
                <style type="text/css">
                    body {
                    padding-top: 30px;
                    }
                </style>
            </head>
            <body>
                <div class="topbar" data-scrollspy="scrollspy">
                    <div class="topbar-inner">
                        <div class="container">
                            <a class="brand" href="index.html">Brand Analytics</a>
                            <ul class="nav">
                                <li class="active" valign="center">
                                <p>Бред Аналитик - гомоморфный образ группы изоморфен фактор группе по ядру гомоморфизма
                                </p>
                                </li>
                            </ul>

                        </div>
                    </div>
                </div>


                <div class="content" align="center">
                    <div class="page-header" align="center">
                        <xsl:call-template name="find"/>
                    </div>
                </div>
                <div class="container-fluid">
                    <div class="sidebar">
                        <div class="well">
                            <xsl:call-template name="leftmenu"/>
                        </div>
                    </div>
                    <div class="content">
                        <!--                    <div class="hero-unit">-->
                        <xsl:call-template name="main"/>
                        <!--          </div>                        -->
                    </div>
                </div>
            </body>
        </html>
    </xsl:template>

    <xsl:template name="find">
        <table border="0" align="center">
            <!--      <tr align="center">
         <td id="logo_p1" onclick="location.href='index.xml';">
         </td>
         <td align="center">  -->
            <div>
                <form method="get" action="/search.xml" align="center">
                    <input class="xlarge" type="text" name="query" size="50"/>
                    <input class="btn primary" type="submit" value="побрендить"/>
                </form>
            </div>

            <!--          </td>
           </tr> -->
        </table>
    </xsl:template>

    <xsl:template name="leftmenu">
        <xsl:apply-templates select="page/data[@id='showLeftMenu' ]" mode="show"/>
        <xsl:apply-templates select="page/data[@id='showLeftMenu' ]/collection" mode="show"/>
    </xsl:template>

    <xsl:template match="page/data[@id='showLeftMenu']" mode="show">
        <h5>Все, что есть у нас:</h5>
    </xsl:template>
    <xsl:template match="page/data[@id='showLeftMenu']/collection" mode="show">
        <ul>
            <xsl:for-each select="simply-brand-for-web">
                <li>
                    <a>
                        <xsl:attribute name="href">showbrand.xml?id=<xsl:value-of select="@id"/>
                        </xsl:attribute>
                        <xsl:value-of select="name"/>
                    </a>
                </li>
            </xsl:for-each>
        </ul>
    </xsl:template>

</xsl:stylesheet>