package com.example.medicalannals.models;

public class DoctorSlotsBookedModel {

    public int patientBookedSlotImage;
    public String patientBookedSlotName;
    public String patientBookedSlotDate;
    public String patientBookedSlotTime;

    public DoctorSlotsBookedModel(int patientBookedSlotImage , String patientBookedSlotName, String patientBookedSlotDate , String patientBookedSlotTime )
    {
        this.patientBookedSlotImage = patientBookedSlotImage;
        this.patientBookedSlotName=patientBookedSlotName;
        this.patientBookedSlotDate=patientBookedSlotDate;
        this.patientBookedSlotTime=patientBookedSlotTime;
    }

    public int getPatientBookedSlotImage()
    {
        return patientBookedSlotImage;
    }

    public void setPatientBookedSlotImage(int patientBookedSlotImage)
    {
        this.patientBookedSlotImage = patientBookedSlotImage;
    }

    public String getPatientBookedSlotName() {
        return patientBookedSlotName;
    }

    public void setPatientBookedSlotName(String patientBookedSlotName) {
        this.patientBookedSlotName = patientBookedSlotName;
    }

    public String getPatientBookedSlotDate() {
        return patientBookedSlotDate;
    }

    public void setPatientBookedSlotDate(String patientBookedSlotDate) {
        this.patientBookedSlotDate = patientBookedSlotDate;
    }

    public String getPatientBookedSlotTime() {
        return patientBookedSlotTime;
    }

    public void setPatientBookedSlotTime(String patientBookedSlotTime) {
        this.patientBookedSlotTime = patientBookedSlotTime;
    }

}
