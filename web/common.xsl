<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

    <xsl:output method="html" doctype-system="http://www.w3.org/TR/html4/strict.dtd"
                doctype-public="-//W3C//DTD HTML 4.01//EN" indent="yes"/>

    <xsl:key match="/page/items/item" name="item" use="@uid"/>

    <xsl:template match="/">

        <html>
            <head>
                <xsl:call-template name="head"/>

                <title>
                    <xsl:text>Brand Analytics</xsl:text>
                </title>
                <link href="3rd-party/bootstrap2/bootstrap/css/bootstrap.css" rel="stylesheet"/>
                <link href="3rd-party/bootstrap2/bootstrap/css/bootstrap-responsive.css" rel="stylesheet"/>
                <style type="text/css">
                    body {
                    padding-top: 35px;
                    }
                </style>

            </head>
            <body>
                <div class="navbar navbar-fixed-top" data-scrollspy="scrollspy">
                    <div class="navbar-inner">
                        <div class="container">
                            <h4><a class="brand" href="index.xml">Brand Analytic</a></h4>
                            <div class="nav-collapse">
                                <ul class="nav">
                                    <li id="navbar_about"><a href="index.xml">О проекте</a></li>
                                    <li id="navbar_main"><a href="index.xml">Главная</a></li>
                                    <li id="navbar_analysis"><a href="showbrand.xml?id=1">Анализ</a></li>
                                    <li id="navbar_sources"><a href="showsources.xml">Источники информации</a></li>
                                </ul>

                                <form class="navbar-search pull-right" id="form-search" method="get" action="/search.xml">
                                    <input type="text" class="search-query" placeholder="поиск по брендам" name="query"/>
                                </form>
                            </div>

                        </div>
                    </div>
                </div>
                <div class="container">
                <xsl:call-template name="run"/>
                <div class="page-header" style="padding-top:50px">
                    <xsl:call-template name="header"/>
                </div>
                <div class="row show-grid">
                    <div class="span3">
                        <xsl:call-template name="leftmenu"/>
                    </div>
                    <div class="span9">
                        <xsl:call-template name="main"/>
                    </div>
                </div>
                </div>


            </body>
        </html>
    </xsl:template>

    <xsl:template name="leftmenu">
        <xsl:apply-templates select="page/data[@id='showLeftMenu' ]" mode="show"/>
        <xsl:apply-templates select="page/data[@id='showLeftMenu' ]/collection" mode="show"/>
    </xsl:template>

    <xsl:template match="page/data[@id='showLeftMenu']" mode="show">
        <h5>Бренды для анализа:</h5>
    </xsl:template>
    <xsl:template match="page/data[@id='showLeftMenu']/collection" mode="show">
        <ul>
            <xsl:for-each select="simply-brand-for-web">
                <li>
                    <a>
                        <xsl:attribute name="href">showbrand.xml?id=<xsl:value-of select="@id"/>
                        </xsl:attribute>
                        <xsl:value-of select="name"/>
                    </a>
                </li>
            </xsl:for-each>
        </ul>
    </xsl:template>
    <xsl:template name="run"/>
</xsl:stylesheet>