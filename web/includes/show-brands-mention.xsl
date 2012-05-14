<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">


    <xsl:template name="mentions-bad" match="page/data[@id='getLatestMentions']/collection">
        <script type="text/javascript" src="3rd-paty/jquery.tablesorter.js"></script>
        <div class="span5">
            <table class="table table-striped" id="up-lead">
                <thead>
                    <tr>
                        <th>
                            <div align="right">Лидеры роста</div>
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <xsl:for-each select="mention">
                        <xsl:sort select="dot/@value" order="descending" data-type="number"/>
                        <tr>
                            <td>
                                <a>
                                    <xsl:attribute name="href">
                                        <xsl:text>showbrand.xml?id=</xsl:text>
                                    </xsl:attribute>
                                    <xsl:value-of select="brand-name"/>
                                </a>
                            </td>
                            <td class="greencolor">
                                <xsl:value-of select='format-number(dot/@value,"#0.00%")'/>
                            </td>
                        </tr>
                    </xsl:for-each>
                </tbody>
            </table>
            <!--<button class="btn collapseController" type="submit" table-target="#up-lead">
                Collapse uppers!
            </button>-->
        </div>
        <div class="span5">
            <table class="table table-striped" id="down-lead">
                <thead>
                    <tr>
                        <th>
                            <div align="right">Лидеры падения</div>
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <xsl:for-each select="mention">
                        <xsl:sort select="dot/@value" order="ascending" data-type="number"/>
                        <tr>
                            <td>
                                <a>
                                    <xsl:attribute name="href">
                                        <xsl:text>showbrand.xml?id=</xsl:text>
                                    </xsl:attribute>
                                    <xsl:value-of select="brand-name"/>
                                </a>
                            </td>
                            <td class="redcolor">
                                <xsl:value-of select='format-number(dot/@value,"#0.00%")'/>
                            </td>
                        </tr>
                    </xsl:for-each>
                </tbody>
            </table>
            <!--
          <button class="btn collapseController" type="submit" table-target="#down-lead">
              Collapse downers!
          </button>  -->
        </div>

    </xsl:template>


</xsl:stylesheet>