<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <!-- <xsl:output method="html" indent="yes" encoding="UTF-8"/>         -->


    <xsl:output method="html" doctype-system="http://www.w3.org/TR/html4/strict.dtd"
                doctype-public="-//W3C//DTD HTML 4.01//EN" indent="yes"/>

    <xsl:key match="/page/items/item" name="item" use="@uid"/>

    <xsl:template match="/">

        <html>
            <head>
                <xsl:call-template name="head"/>

                <title>
                    <xsl:text>Brand Analytics</xsl:text>
                </title>
                <link href="3rd-party/bootstrap2/bootstrap/css/bootstrap.css" rel="stylesheet"/>
                <link href="3rd-party/bootstrap2/bootstrap/css/bootstrap-responsive.css" rel="stylesheet"/>
                <style type="text/css">
                    body {
                    padding-top: 30px;
                    }
                </style>
            </head>
            <body>
                <div class="navbar navbar-fixed-top" data-scrollspy="scrollspy">
                    <div class="navbar-inner">
                        <div class="container">
                            <a class="brand" href="index.xml">Brand Analytics</a>
                            <div class="nav-collapse">
                                <ul class="nav">
                                    <li class="active" valign="center">
                                        <p>Инновация настоящего. Парадигма будущего.
                                        </p>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="container">
                <div class="page-header" align="center">
                    <xsl:call-template name="find"/>
                </div>
                <div class="row show-grid">
                    <div class="span3">
                        <xsl:call-template name="leftmenu"/>
                    </div>
                    <div class="span9">
                        <xsl:call-template name="main"/>
                    </div>
                </div>
                </div>
            </body>
        </html>
    </xsl:template>

    <xsl:template name="find">
        <form class="well form-search" name="1" method="get" action="/search.xml" align="center">
            <input class="input-medium search-query" type="text" name="query" size="50"/>
            <input class="btn" type="submit" value="поиск"/>
            <!--      <input type="checkbox" name="query_type" value="news"/>
                  <span>искать по новостям</span>
            -->
        </form>
    </xsl:template>

    <xsl:template name="leftmenu">
        <xsl:apply-templates select="page/data[@id='showLeftMenu' ]" mode="show"/>
        <xsl:apply-templates select="page/data[@id='showLeftMenu' ]/collection" mode="show"/>
    </xsl:template>

    <xsl:template match="page/data[@id='showLeftMenu']" mode="show">
        <h5>Бренды для анализа:</h5>
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