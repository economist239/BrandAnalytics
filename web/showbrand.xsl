<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:import href="common.xsl"/>
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>

    <xsl:template name="head">
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
        <script type="text/javascript" src="3rd-party/highstock/js/highstock.js"/>
        <script type="text/javascript" src="3rd-party/highstock/js/modules/exporting.js"/>
        <script type="text/javascript" src="3rd-party/bootstrap2/bootstrap/js/bootstrap.js"/>
        <script type="text/javascript" src="graphs.js"/>
        <script type="text/javascript" src="show-brand.js"/>
<!--
        <script type="text/javascript">
            $(document).ready(function(){
            $("li#branch-select li").click(function(){
                var branchId = $(this).children("a").attr("id");
                if(branchId=="0"){
                    $("li#brand-select li").css("display","block");
                }
                else{
                    $("li#brand-select li").css("display","none");
                    $("li#brand-select li[branch-id=" + branchId + "]").css("display","block");
                }
            })
            })
        </script>
-->

    </xsl:template>

    <xsl:template name="header">
        <h1 align="left"><xsl:value-of select="page/data[@id='getBrand']/brand/name"/></h1>
        <div class="pull-right">
            <ul class="nav nav-pills">
                <xsl:apply-templates select="page/data[@id='getBranches']/collection" mode="select"/>
                <xsl:apply-templates select="page/data[@id='getBrands']/collection" mode="select"/>
            </ul>
        </div>
    </xsl:template>
    
    <xsl:template name="main">
        <script type="text/javascript">
            document.getElementById("navbar_analysis").setAttribute("class", "active");
        </script>

        <div class="tab-content">
            <div class="tab-pane active" id="info">
                <xsl:apply-templates select="page/data[@id='getBrand']/brand" mode="show"/>
            </div>
            <div class="tab-pane" id="news">
                <xsl:apply-templates select="page/data[@id='getArticlesForBrand']/collection" mode="show"/>
            </div>
            <div class="tab-pane" id="analyse">
                <xsl:apply-templates select="page/data[@id='getTickers']/collection" mode="show"/>
            </div>
        </div>
    </xsl:template>

    <xsl:template name="brands-select" match="page/data[@id='getBrands']/collection" mode="select">
        <li id="brand-select" class="dropdown">
            <a class="dropdown-toggle" href="#brand-select" data-toggle="dropdown">
                <xsl:text>Брэнд</xsl:text>
                <b class="caret"></b>
            </a>
            <ul class="dropdown-menu">
                <xsl:for-each select="brand">
                    <li>
                        <xsl:attribute name="branch-id">
                            <xsl:value-of select="@branch-id"/>
                        </xsl:attribute>
                        <a>
                            <xsl:attribute name="href">
                                <xsl:text>showbrand.xml?id=</xsl:text><xsl:value-of select="@id"/>
                            </xsl:attribute>
                            <xsl:value-of select="name"/>
                        </a>
                    </li>
                </xsl:for-each>
            </ul>
        </li>
    </xsl:template>
    <!--<xsl:template name="brands-select" match="page/data[@id='getBrands']/collection" mode="select">
            <select id="brand-select">
                <xsl:for-each select="brand">
                    <option>
                        <xsl:attribute name="value">
                            <xsl:value-of select="@id"/>
                        </xsl:attribute>
                        <xsl:attribute name="branch-id">
                            <xsl:value-of select="@branch-id"/>
                        </xsl:attribute>
                        <xsl:value-of select="name"/>
                    </option>
                </xsl:for-each>
            </select>
        </xsl:template>-->

    <xsl:template match="page/data[@id='getBranches']/collection" mode="select">
        <li id="branch-select" class="dropdown">
            <a class="dropdown-toggle" href="#branch-select" data-toggle="dropdown">
                <xsl:text>Отрасли</xsl:text>
                <b class="caret"></b>
            </a>
            <ul class="dropdown-menu">
                <xsl:for-each select="branch">
                    <li>
                        <a data-toggle="pill">
                            <xsl:attribute name="href">
                                <xsl:text>#</xsl:text>
                            </xsl:attribute>
                            <xsl:attribute name="id">
                                <xsl:value-of select="@id"/>
                            </xsl:attribute>
                            <xsl:value-of select="name"/>
                        </a>
                    </li>
                </xsl:for-each>
                <li class="divider"></li>
                <li><a href="#" id="0">Все</a></li>
            </ul>
        </li>

    </xsl:template>

    <xsl:template match="page/data[@id='getBrand']/brand" mode="show">
            <div class="content">
                <h3>
                    <br/>
                    <a>
                        <xsl:attribute name="href">
                            <xsl:value-of select="website"/>
                        </xsl:attribute>
                        <xsl:value-of select="name"/>
                    </a>
                </h3>
                <p>
                    <b><xsl:text>О компании:</xsl:text></b>
                    <br/>
                    <xsl:value-of select="description"/>
                    <br/>
                </p>
            </div>
    </xsl:template> 

    <xsl:template match="page/data[@id='getTickers']/collection" mode="show">
        <h3><xsl:text>Анализ</xsl:text></h3>
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
            <button class="btn primary" type="submit" value="посмотреть" onclick="graph()">посмотреть</button>
        <div id="chartContainer" style="height: 500px; min-width: 500px"/>
    </xsl:template>

    <xsl:template match="page/data[@id='getArticlesForBrand']/collection" mode="show">
    <table class="table table-striped">
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
                        <xsl:value-of select="tstamp/@hours"/>:<xsl:value-of select="tstamp/@minutes"/>:<xsl:value-of select="tstamp/@seconds"/>
                    </p>
                </div>
            </td></tr>
        </xsl:for-each>
    </table>
    </xsl:template>


    <xsl:template name="leftmenu">
        <ul class="nav nav-tabs nav-stacked">
            <li id="tab_info" class="active"><a href="#info" data-toggle="tab">О компании</a></li>
            <li id="tab_news"><a href="#news" data-toggle="tab">Новости</a></li>
            <li id="tab_analyse"><a href="#analyse" data-toggle="tab">Анализ</a></li>
            <li id="tab_compare"><a href="#compare" data-toggle="tab">Сравнение</a></li>
            <li id="tab_review"><a href="#review" data-toggle="tab">Обзор</a></li>
        </ul>
    </xsl:template>
</xsl:stylesheet>
