<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>
    <xsl:include href="common.xsl"/>

    <xsl:template name="main">
        <xsl:apply-templates select="page/data[@id='showArticle']" mode="show"/>
    </xsl:template>

    <xsl:template match="page/data[@id='showArticle']" mode="show">
        <xsl:for-each select="wide-article-for-web">
            <div class="content">
                <h3><a>
                    <xsl:attribute name="href"><xsl:value-of select="link"/>
                    </xsl:attribute>
                    <xsl:value-of select="title"/>
                </a></h3>
                <p>
                    <xsl:value-of select="content"/>
                </p>
                <div align="right">
                <p>
                Дата публикации: <xsl:value-of select="time"/>
                </p>
                <p>Источник:
                <a><xsl:attribute name="href"><xsl:value-of select="source-link"/>
                    </xsl:attribute>
                <xsl:value-of select="source-name"/>
                </a>
                 </p>
                </div>

            </div>
        </xsl:for-each>
    </xsl:template>

</xsl:stylesheet>