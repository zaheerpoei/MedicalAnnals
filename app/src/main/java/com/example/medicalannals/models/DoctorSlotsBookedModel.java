package com.example.medicalannals.models;

import java.io.Serializable;

public class DoctorSlotsBookedModel implements Serializable {

    public String patientBookedSlotId;
    public String doctorBookedSlotId;
    public String patientBookedSlotDate;
    public String patientBookedSlotTime;
    public String patientName;
    public String appointmentKey;

    public DoctorSlotsBookedModel(String patientBookedSlotId, String doctorBookedSlotId, String patientBookedSlotDate
            , String patientBookedSlotTime, String patientName,String appointmentKey )
    {
        this.patientBookedSlotId = patientBookedSlotId;
        this.doctorBookedSlotId = doctorBookedSlotId;
        this.patientBookedSlotId = patientBookedSlotId;
        this.patientBookedSlotDate=patientBookedSlotDate;
        this.patientBookedSlotTime=patientBookedSlotTime;
        this.patientName=patientName;
        this.appointmentKey=appointmentKey;
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

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public void setAppointmentKey(String appointmentKey) {
        this.appointmentKey = appointmentKey;
    }

    public String getAppointmentKey() {
        return appointmentKey;
    }
}
