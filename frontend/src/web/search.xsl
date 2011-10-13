<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>
    <xsl:include href="common.xsl"/>

    <xsl:template name="main">
        <xsl:apply-templates select="page/data" mode="show"/>
        <xsl:apply-templates select="page/data/collection" mode="show"/>
    </xsl:template>

    <xsl:template match="collection" mode="show">
        <xsl:for-each select="brand-for-web">
            <h2><a>
                <xsl:attribute name="href">showbrand.xml?name=<xsl:value-of select="name"/></xsl:attribute>
                <xsl:value-of select="name"/><br/>
            </a></h2>
            <a><xsl:value-of select="description"/><br/></a>
            <a>
                <xsl:attribute name="href"><xsl:value-of select="website"/></xsl:attribute>
                visit official website of company<br/>
            </a>
            <hr align="left"/>
        </xsl:for-each>
    </xsl:template>

    <xsl:template match="data" mode="show">
        <h2>
            <xsl:value-of select="answer"/>
        </h2>
    </xsl:template>



</xsl:stylesheet>
