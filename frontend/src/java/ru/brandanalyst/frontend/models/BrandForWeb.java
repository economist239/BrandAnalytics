package ru.brandanalyst.frontend.models;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: dima
 * Date: 10/16/11
 * Time: 4:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class BrandForWeb {
    private String name;
       private String description;
       private String website;
       private String branch;
       private String picturePath;
       private ArrayList<GraphForWeb> graphList;

       public BrandForWeb(String name, String description, String website, String picturePath, ArrayList<GraphForWeb> graphList) {
           this.name = name;
           this.description = description;
           this.website = website;
           this.picturePath = picturePath;
           this.graphList = graphList;
       }

       public String getName() {
           return name;
       }

       public String getDescription() {
           return description;
       }
}
