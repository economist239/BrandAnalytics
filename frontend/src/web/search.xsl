<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>
    <xsl:include href="common.xsl"/>

    <xsl:template name="main">
        <xsl:apply-templates select="page/data[@id='search']" mode="show"/>
        <xsl:apply-templates select="page/data[@id='search']/collection" mode="show"/>
    </xsl:template>

    <xsl:template match="page/data[@id='search']/collection" mode="show">
        <xsl:for-each select="simply-brand-for-web">
            <h2><a>
                <xsl:attribute name="href">showbrand.xml?id=<xsl:value-of select="@id"/></xsl:attribute>
                <xsl:value-of select="name"/><br/>
            </a></h2>
            <h5><xsl:value-of select="description"/></h5>
            <a>
                <xsl:attribute name="href">http://<xsl:value-of select="website"/></xsl:attribute>
                visit official website of company<br/>
            </a>
            <hr align="left"/>
        </xsl:for-each>
    </xsl:template>

    <xsl:template match="data" mode="show">
        <h4>
            <xsl:value-of select="answer"/>
        </h4>
    </xsl:template>
</xsl:stylesheet>
