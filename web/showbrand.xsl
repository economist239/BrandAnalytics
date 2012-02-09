<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>
    <xsl:include href="common.xsl"/>

    <xsl:template name="main">
        <xsl:apply-templates select="page/data[@id='wideBrandInfo']" mode="show"/>
        <xsl:apply-templates select="page/data[@id='wideBrandInfo']/collection[1]" mode="show"/>
    </xsl:template>

    <xsl:template name="head">
        <script type="text/javascript" src="3rd-party/jquery.js"/>
        <script type="text/javascript" src="graphs.js"/>
        <script type="text/javascript" src="highstock/js/highstock.js"/>
        <script type="text/javascript" src="highstock/js/modules/exporting.js"/>
    </xsl:template>

    <xsl:template match="page/data[@id='wideBrandInfo']" mode="show">
        <xsl:for-each select="brand">
            <div class="content">
                <h3>
                    <a>
                        <xsl:attribute name="href">
                            <xsl:value-of select="website"/>
                        </xsl:attribute>
                        <xsl:value-of select="name"/>
                    </a>
                </h3>
                <p>
                    <xsl:value-of select="description"/>
                    <br/>
                </p>
            </div>
        </xsl:for-each>
        <hr/>
        <h3>Анализ</h3>
        <xsl:for-each select="collection[2]">
            <select id="graph-select">
                <xsl:for-each select="ticker-pair">   <!--id,name-->
                    <option size="60">
                        <xsl:attribute name="value">
                            <xsl:value-of select="@id"/>
                        </xsl:attribute>
                        <xsl:value-of select="name"/>
                    </option>
                </xsl:for-each>
            </select>
            <button class="btn primary" type="submit" value="посмотреть" onclick="getGraph()">посмотреть</button>
        </xsl:for-each>
        <div id="chartContainer" style="height: 500px; min-width: 500px"/>
    </xsl:template>

    <xsl:template match="page/data[@id='wideBrandInfo']/collection[1]" mode="show">
        <h3>Последние новости</h3>
        <table class="zebra-striped">
            <xsl:for-each select="simply-article-for-web">
                <div class="span5">
                    <th valign="top">
                        <a>
                            <xsl:attribute name="href">showarticle.xml?id=<xsl:value-of select="@id"/>
                            </xsl:attribute>
                            <xsl:value-of select="name"/>
                        </a>
                        <br/>
                        <xsl:value-of select="short-content"/>
                    </th>
                </div>
                <xsl:if test="position() mod 3 = 0">
                    <tr/>
                </xsl:if>
            </xsl:for-each>
        </table>
    </xsl:template>
</xsl:stylesheet>
