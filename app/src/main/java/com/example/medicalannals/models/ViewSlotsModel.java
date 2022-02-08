package com.example.medicalannals.models;

public class ViewSlotsModel {
    public String date;
    public String day;

    public ViewSlotsModel(String date ,  String day)
    {
        this.date=date;
        this.day=day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

}
