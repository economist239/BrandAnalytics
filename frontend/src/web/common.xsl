<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>

    <xsl:key match="/page/items/item" name="item" use="@uid"/>

    <xsl:template match="/">
        <html>
        <head>
            <title><xsl:text>BrandAnalyst</xsl:text></title>
            <link type="text/css" rel="stylesheet" href="basket.css"/>
            <script language="javascript" src="javascripts/js-class.js" type="text/javascript"></script>
            <script language="javascript" src="javascripts/bluff.js" type="text/javascript"></script>
            <script language="javascript" src="javascripts/excanvas.js" type="text/javascript"></script>
        </head>
        <body>
            <table border="2">
                <tr>
                    <xsl:call-template name="find"/>
                </tr>
                <tr>
                    <td width="20%" valign="top">
                        <xsl:call-template name="leftmenu"/>
                    </td>
                    <td width="60%" valign="top">
                        <xsl:call-template name="main"/>
                    </td>
                    <td>
                        Здесь может быть ваша реклама
                    </td>
                </tr>
            </table>
        </body>
        </html>
    </xsl:template>

    <xsl:template name="find">
        <tr align="center">
            <td colspan="3">
                <form method="get" action="/search.xml">
                    <input type="text" name="query" size="30"/>
                    <input type="submit" value="побрендить"/>
                </form>
            </td>
        </tr>
    </xsl:template>

    <xsl:template name="leftmenu">
        <xsl:apply-templates select="page/data[@id='showLeftMenu' ]" mode="show"/>
        <xsl:apply-templates select="page/data[@id='showLeftMenu' ]/collection" mode="show"/>
    </xsl:template>

    <xsl:template match="page/data[@id='showLeftMenu' ]" mode="show">
        <h3>Все, что есть у нас:</h3>
    </xsl:template>
    <xsl:template match="page/data[@id='showLeftMenu' ]/collection" mode="show">
        <xsl:for-each select="simply-brand-for-web">
            <bold><a>
                <xsl:attribute name="href">showbrand.xml?name=<xsl:value-of select="name"/></xsl:attribute>
                <xsl:value-of select="name"/>
            </a></bold><br/>
            <a><xsl:value-of select="description"/></a> <br/>
        </xsl:for-each>
    </xsl:template>

</xsl:stylesheet>