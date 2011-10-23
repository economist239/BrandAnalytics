<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>

    <xsl:key match="/page/items/item" name="item" use="@uid"/>

    <xsl:template match="/">
        <html>
        <head>
            <title><xsl:text>BrandAnalyst</xsl:text></title>

            <link href="bootstrap/bootstrap.css" rel="stylesheet"/>
            <style type="text/css">
                body {
                    padding-top: 60px;
                }
            </style>
        </head>
        <body>
            <div class="content" align="center">
                <div class="page-header">
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
                    <div class="hero-unit">
                        <xsl:call-template name="main"/>
                    </div>
                </div>
            </div>
        </body>
        </html>
    </xsl:template>

    <xsl:template name="find">
        <form method="get" action="/search.xml" align="center">
            <input type="text" name="query" size="30"/>
            <input type="submit" value="побрендить"/>
        </form>
    </xsl:template>

    <xsl:template name="leftmenu">
        <xsl:apply-templates select="page/data[@id='showLeftMenu' ]" mode="show"/>
        <xsl:apply-templates select="page/data[@id='showLeftMenu' ]/collection" mode="show"/>
    </xsl:template>

    <xsl:template match="page/data[@id='showLeftMenu' ]" mode="show">
        <h5>Все, что есть у нас:</h5>
    </xsl:template>
    <xsl:template match="page/data[@id='showLeftMenu' ]/collection" mode="show">
        <ul>
            <xsl:for-each select="simply-brand-for-web">
                <li><a>
                    <xsl:attribute name="href">showbrand.xml?id=<xsl:value-of select="@id"/></xsl:attribute>
                    <xsl:value-of select="name"/>
                </a></li>
            </xsl:for-each>
        </ul>
    </xsl:template>

</xsl:stylesheet>