package com.osamo.expensetrackingapp.Model;

public class Goals {

   public String description;
   public String id;
   public String date;

    public Goals() {
    }

    public Goals(String description, String id, String date) {
        this.description = description;
        this.id = id;
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
