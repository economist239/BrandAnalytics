<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>
    <xsl:include href="common.xsl"/>

    <xsl:template name="main">
        <xsl:apply-templates select="page/data[@id='wideBrandInfo']" mode="show"/>
        <xsl:apply-templates select="page/data[@id='wideBrandInfo']/collection" mode="show"/>
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
    </xsl:template>

    <xsl:template match="page/data[@id='wideBrandInfo']/collection[1]" mode="show">
        <h3>Аналитика бренда</h3>
        <!--...-->

        <table id="data">
            <tfoot>
                <tr>
                    <th>1</th>
                    <th>2</th>
                    <th>3</th>
                    <th>4</th>
                    <th>5</th>

                    <th>6</th>
                    <th>7</th>
                    <th>8</th>
                    <th>9</th>
                    <th>10</th>
                    <th>11</th>

                    <th>12</th>
                    <th>13</th>
                    <th>14</th>
                    <th>15</th>
                    <th>16</th>
                    <th>17</th>

                    <th>18</th>
                    <th>19</th>
                    <th>19</th>
                    <th>20</th>
                    <th>22</th>
                    <th>23</th>

                    <th>24</th>
                    <th>25</th>
                    <th>26</th>
                    <th>27</th>
                    <th>28</th>
                    <th>29</th>

                    <th>30</th>
                    <th>31</th>
                </tr>
            </tfoot>
            <tbody>
                <tr>
                    <td>8</td>

                    <td>25</td>
                    <td>27</td>
                    <td>25</td>
                    <td>54</td>
                    <td>59</td>
                    <td>79</td>

                    <td>47</td>
                    <td>27</td>
                    <td>44</td>
                    <td>44</td>
                    <td>51</td>
                    <td>56</td>

                    <td>83</td>
                    <td>12</td>
                    <td>91</td>
                    <td>52</td>
                    <td>12</td>
                    <td>40</td>

                    <td>8</td>
                    <td>60</td>
                    <td>29</td>
                    <td>7</td>
                    <td>33</td>
                    <td>56</td>

                    <td>25</td>
                    <td>1</td>
                    <td>78</td>
                    <td>70</td>
                    <td>68</td>
                    <td>2</td>
                </tr>
            </tbody>
        </table>
        <div id="holder" class="1"></div>


        <!--hardcode-->
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