<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:import href="common.xsl"/>
    <!--<xsl:output method="html" indent="yes" encoding="UTF-8"/>-->


    <xsl:template name="head">
        <script type="text/javascript" src="3rd-party/jquery.js"></script>
        <script type="text/javascript" src="3rd-party/crawler.js"/>
        <script type="text/javascript" src="includes/js/index.js"/>
        <style type="text/css">
            .redcolor{
            color:red;
            }
            .greencolor{
            color:green;
            }
            .separator{
            border: 0px;
            background-color: rgba(0, 0, 0, 0.05);
            }
        </style>
    </xsl:template>
    <xsl:template name="run">
        <div class="marquee" id="mycrawler" align="center" style="-khtml-border-radius:10px">
            <p><a style="padding:0px 10px;" color="blue">Лидеры роста:</a> Smth: +5; Anth: -1233; Smth: +5; Anth: -1233; Smth: +5; Anth: -1233; Smth: +5; Anth: -1233; Smth: +5; Anth: -1233; Smth: +5; Anth: -1233; Smth: +5; Anth: -1233;</p>
        </div>


    </xsl:template>
    <xsl:template name="main"><!--
        <h4>Бренд Аналитик - инновация настоящего, парадигма будущего.</h4>
        <br/>
        <img src="captain-obvious.jpeg" width="400" height="400"/> -->

        <span align="right" id="a">Рейтинги упоминаемости по источнику
            <!--<xsl:apply-templates select="page/data[@id='getInformationSources']/collection"/>-->
            <select class="input-large">
                <option info-source-id="0"><xsl:text>все вместе</xsl:text></option>
                <option info-source-id="$official-sources-type-id">
                    <xsl:text>Официальные источники</xsl:text>
                </option>
                <option info-source-id="$public-sources-type-id">
                    <xsl:text>Публичные источники</xsl:text>
                </option>
            </select>
        </span>

        <div>
        <xsl:apply-templates select="page/data[@id='getLatestMentions']/collection"/>
        </div>
    </xsl:template>
    <xsl:include href="includes/show-brands-mention.xsl"/>

    <xsl:template name="leftmenu">
        <table class="table">
            <thead>
                <tr><th><xsl:text>Новости</xsl:text></th></tr>
            </thead>
            <tbody>
                <xsl:apply-templates select="page/data[@id='getNewestArticles']/collection" mode="show"/>
            </tbody>
        </table>
    </xsl:template>

    <xsl:template match="page/data[@id='getNewestArticles']/collection" mode="show">
        <xsl:for-each select="article">
            <tr><td>
                <h4>
                    <a>
                        <xsl:attribute name="href">
                            <xsl:value-of select="link"/>
                        </xsl:attribute>
                        <xsl:value-of select="title"/>
                    </a>
                </h4>
                <p>
                    <xsl:value-of select="content"/>
                </p>
                <div align="right">
                    <p>
                        Дата публикации:
                        <xsl:value-of select="date"/>
                    </p>
                </div>
            </td></tr>
        </xsl:for-each>
    </xsl:template>
    
    <!--<xsl:template name="info-sources" match="page/data[@id='getInformationSources']/collection">
        <select class="input-small">
            <option info-source-id="0"><xsl:text>все вместе</xsl:text></option>
            <xsl:for-each select="info-source">
                <option>
                    <xsl:attribute name="info-source-id">
                        <xsl:value-of select="@id"/>
                    </xsl:attribute>
                    <xsl:value-of select="title"/>
                </option>
            </xsl:for-each>
        </select>
    </xsl:template>  -->

    <xsl:template name="header"></xsl:template>
</xsl:stylesheet>
