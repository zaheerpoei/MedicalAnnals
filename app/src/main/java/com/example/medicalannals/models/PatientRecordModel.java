package com.example.medicalannals.models;

public class PatientRecordModel {

    public String patientName;
    public int patientImage;

    public PatientRecordModel(int patientImage , String patientName)
    {
        this.patientImage = patientImage;
        this.patientName=patientName;
    }

    public int getPatientImage()
    {
        return patientImage;
    }

    public void setPatientImage(int patientImage)
    {
        this.patientImage = patientImage;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
}
