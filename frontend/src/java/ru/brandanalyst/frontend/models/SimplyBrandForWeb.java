package ru.brandanalyst.frontend.models;

public class SimplyBrandForWeb {
       private long id;
       private String name;
       private String description;
       private String website;

       public SimplyBrandForWeb(long id, String name, String description, String website) {
           this.id = id;
           this.name = name;
           this.description = description;
           this.website = website;
       }

       public long getId() {
           return id;
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