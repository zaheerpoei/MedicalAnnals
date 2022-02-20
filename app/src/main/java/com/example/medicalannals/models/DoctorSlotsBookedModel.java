package com.example.medicalannals.models;

public class DoctorSlotsBookedModel {

    public String patientBookedSlotId;
    public String doctorBookedSlotId;
    public String patientBookedSlotDate;
    public String patientBookedSlotTime;

    public DoctorSlotsBookedModel(String patientBookedSlotId, String doctorBookedSlotId, String patientBookedSlotDate , String patientBookedSlotTime )
    {
        this.patientBookedSlotId = patientBookedSlotId;
        this.doctorBookedSlotId = doctorBookedSlotId;
        this.patientBookedSlotId = patientBookedSlotId;
        this.patientBookedSlotDate=patientBookedSlotDate;
        this.patientBookedSlotTime=patientBookedSlotTime;
    }

    public DoctorSlotsBookedModel() {
    }

    public String getDoctorBookedSlotId() {
        return doctorBookedSlotId;
    }

    public void setDoctorBookedSlotId(String doctorBookedSlotId) {
        this.doctorBookedSlotId = doctorBookedSlotId;
    }

    public String getPatientBookedSlotId() {
        return patientBookedSlotId;
    }

    public void setPatientBookedSlotId(String patientBookedSlotId) {
        this.patientBookedSlotId = patientBookedSlotId;
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
