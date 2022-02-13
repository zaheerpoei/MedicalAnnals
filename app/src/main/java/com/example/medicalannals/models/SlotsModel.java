package com.example.medicalannals.models;

public class SlotsModel {
    private String date="";
    private String time="";
    private String docId="";

    public SlotsModel(String date, String time, String docId) {
        this.date = date;
        this.time = time;
        this.docId = docId;
    }

    public SlotsModel() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }
}
