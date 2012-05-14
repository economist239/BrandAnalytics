<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:import href="common.xsl"/>
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>

    <!--Variables -->
    <!--    <xsl:variable name="official-sources-type-id">1</xsl:variable>
    <xsl:variable name="public-sources-type-id">0</xsl:variable>
    -->
    <xsl:template name="head">
        <script type="text/javascript" src="3rd-party/jquery.js"></script>
        <script type="text/javascript" src="3rd-party/bootstrap2/bootstrap/js/bootstrap.js"/>
    </xsl:template>

    <xsl:template name="main">
        <script type="text/javascript" src="includes/js/showsources.js"></script>

        <div class="tab-content">
            <div class="tab-pane active" id="official-sources">
                <xsl:apply-templates select="page/data[@id='getInformationSources']/collection" mode="show"/>
            </div>
            <div class="tab-pane" id="companies">
                <xsl:apply-templates select="page/data[@id='getBrands']/collection" mode="show"/>
            </div>
            <div class="tab-pane" id="public-sources">
                <xsl:apply-templates select="page/data[@id='getInformationSources']/collection" mode="show"/>
            </div>
        </div>
    </xsl:template>

    <xsl:template name="show-companies" match="page/data[@id='getBrands']/collection" mode="show">
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>Компания</th>
                    <th>Официальный сайт</th>
                </tr>
            </thead>
            <tbody>
                <xsl:for-each select="brand">
                    <tr class="brand-row">
                        <td>
                            <a>
                                <xsl:attribute name="href">
                                    <xsl:text>showbrand.xml?id=</xsl:text><xsl:value-of select="@id"/>
                                </xsl:attribute>
                                <xsl:value-of select="name"/>
                            </a>
                        </td>
                        <td>
                            <a>
                                <xsl:attribute name="href">
                                    <xsl:value-of select="website"/>
                                </xsl:attribute>
                                <xsl:value-of select="website"/>
                            </a>
                        </td>
                    </tr>
                </xsl:for-each>
            </tbody>
        </table>
    </xsl:template>

    <xsl:template name="show-sources" match="page/data[@id='getInformationSources']/collection" mode="show">

        <table class="table table-striped">
            <thead>
                <tr>
                    <th>Название</th>
                    <th>Сайт</th>
                </tr>
            </thead>
            <tbody>
                <xsl:for-each select="info-source">
                    <tr class="info-source-row" style="display:table-row">
                        <xsl:attribute name="sphere-id">
                            <xsl:value-of select="@sphere-id"/>
                        </xsl:attribute>
                        <xsl:attribute name="source-id">
                            <xsl:value-of select="@id"/>
                        </xsl:attribute>
                        <td>
                            <xsl:value-of select="title"/>
                        </td>
                        <td>
                            <a>
                                <xsl:attribute name="href">
                                    <xsl:value-of select="website"/>
                                </xsl:attribute>
                                <xsl:value-of select="website"/>
                            </a>
                        </td>
                    </tr>
                </xsl:for-each>
            </tbody>
        </table>
    </xsl:template>

    <xsl:template name="leftmenu">
        <ul class="nav nav-tabs nav-stacked">
            <li id="tab_companies">
                <a href="#companies" data-toggle="tab">Компании</a>
            </li>
            <li id="tab_official-sources">
                <a href="#official-sources" data-toggle="tab">Официальные источники</a>
            </li>
            <li id="tab_public-sources">
                <a href="#public-sources" data-toggle="tab">Публичные источники</a>
            </li>
        </ul>
    </xsl:template>

    <xsl:template name="header"></xsl:template>

</xsl:stylesheet>
