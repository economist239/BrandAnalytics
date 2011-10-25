<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>
    <xsl:include href="common.xsl"/>

    <xsl:template name="main">
        <xsl:apply-templates select="page/data[@id='showArticle']" mode="show"/>
    </xsl:template>

    <xsl:template match="page/data[@id='showArticle']" mode="show">
        <xsl:for-each select="article">
            <div class="content">
                <h3>
                        <xsl:value-of select="title"/>
                </h3>
                <p>
                    <xsl:value-of select="content"/>
                </p>
            </div>
        </xsl:for-each>
    </xsl:template>

</xsl:stylesheet>