<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>
    <xsl:include href="common.xsl"/>

    <xsl:template name="head"/>
    <xsl:template name="main">
        <!--<xsl:apply-templates select="page/data[@id='showYouTube']" mode="show"/>-->
        <xsl:apply-templates select="page/data[@id='showYouTube']/collection" mode="show"/>
    </xsl:template>

    <xsl:template match="page/data[@id='showYouTube']/collection" mode="show">
        <h2>Рекламные ролики с YouTube</h2>
        <xsl:for-each select="you-tube-video">
            <h3>
                <a>
                    <xsl:attribute name="href">
                        <xsl:value-of select="url"/>
                    </xsl:attribute>
                    <xsl:value-of select="title"/>
                    <br/>
                </a>
            </h3>

            <object style="height: 360px; width: 480px">
                <param name="movie" feature="player_embedded">
                    <xsl:attribute name="value">
                        <xsl:value-of select="player-code"/>
                    </xsl:attribute>
                </param>
                <param name="allowFullScreen" value="true"/>
                <param name="allowScriptAccess" value="always"/>
                <embed feature="player_embedded\"
                       type="application/x-shockwave-flash" allowfullscreen="true" allowScriptAccess="always"
                       width="480" height="360">
                    <xsl:attribute name="src">
                        <xsl:value-of select="player-code"/>
                    </xsl:attribute>
                </embed>
            </object>

            <hr/>
        </xsl:for-each>
    </xsl:template>

</xsl:stylesheet>