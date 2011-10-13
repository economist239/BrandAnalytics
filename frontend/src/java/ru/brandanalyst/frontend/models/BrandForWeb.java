package ru.brandanalyst.frontend.models;

public class BrandForWeb {
       private String name;
       private String description;
       private String website;

       public BrandForWeb(String name, String description, String website) {
           this.name = name;
           this.description = description;
           this.website = website;
       }

       public String getName() {
           return name;
       }

       public void setName(String name) {
           this.name = name;
       }

       public String getDescription() {
           return description;
       }

       public void setDescription(String description) {
           this.description = description;
       }

       public String getWebsite() {
           return website;
       }

       public void setWebsite(String description) {
           this.description = description;
       }
}