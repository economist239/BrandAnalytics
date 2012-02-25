<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">


    <xsl:template name="mentions-bad" match="page/data[@id='getLatestMentions']/collection">
        <script type="text/javascript" src="3rd-paty/jquery.tablesorter.js"></script>
        <table class="table table-bordered span4" id="up-lead">
            <thead>	<tr><th>Лидеры роста</th></tr></thead>
            <tbody>
                <xsl:for-each select="mention">
                    <xsl:sort select="dot/@value" order="descending" data-type="number"/>
                    <xsl:if test="@ticker-id = 2">
                        <tr>
                            <td>
                                <a>
                                    <xsl:attribute name="href">
                                        <xsl:text>showbrands.xml?id=</xsl:text><xsl:value-of select="brand-id"/>
                                    </xsl:attribute>
                                    <xsl:value-of select="brand"/>
                                </a>
                            </td>
                            <td class="greencolor">
                                <xsl:value-of select="dot/@value"/>
                            </td>
                        </tr>
                    </xsl:if>
                </xsl:for-each>
                <tr class="collapseController">
                   <td> <xsl:text>Collapse it!</xsl:text> </td>
                </tr>
            </tbody>

        </table>
        <table class="table table-bordered span4" id="down-lead">
            <thead><tr><th>Лидеры падения</th></tr></thead>
            <tbody>
                <xsl:for-each select="mention">
                    <xsl:sort select="dot/@value" order="descending" data-type="number"/>
                    <xsl:if test="@ticker-id = 4">
                        <tr>
                            <td>
                                <a>
                                    <xsl:attribute name="href">
                                        <xsl:text>showbrands.xml?id=</xsl:text><xsl:value-of select="brand-id"/>
                                    </xsl:attribute>
                                    <xsl:value-of select="brand"/>
                                </a>
                            </td>
                            <td class="redcolor">
                                <xsl:value-of select="dot/@value"/>
                            </td>
                        </tr>
                    </xsl:if>
                </xsl:for-each>
            </tbody>
        </table>
    </xsl:template>
    
    

</xsl:stylesheet>