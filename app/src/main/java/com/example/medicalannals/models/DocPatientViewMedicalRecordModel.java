package com.example.medicalannals.models;

public class DocPatientViewMedicalRecordModel {

    public String tvDocNamePatientRecord;
    public String tvPatientNamePatientRecord;
    public String tvDatePatientRecord;
    public String tvRemarksPatientRecord;
    public String tvPrescriptionPatientRecord;
    public String patientBookedSlotId;
    public String doctorBookedSlotId;
    private String appointmentKey;
    private boolean isdeleted;
    private String patientRecordId;

    public DocPatientViewMedicalRecordModel(String tvDocNamePatientRecord ,String tvPatientNamePatientRecord, String tvDatePatientRecord, String tvRemarksPatientRecord , String tvPrescriptionPatientRecord,String patientBookedSlotId, String doctorBookedSlotId,String appointmentKey,boolean isdeleted,String patientRecordId)
    {
        this.tvPatientNamePatientRecord = tvPatientNamePatientRecord;
        this.tvDocNamePatientRecord = tvDocNamePatientRecord;
        this.tvDatePatientRecord=tvDatePatientRecord;
        this.tvRemarksPatientRecord=tvRemarksPatientRecord;
        this.tvPrescriptionPatientRecord=tvPrescriptionPatientRecord;
        this.patientBookedSlotId = patientBookedSlotId;
        this.doctorBookedSlotId = doctorBookedSlotId;
        this.appointmentKey = appointmentKey;
        this.isdeleted = isdeleted;
        this.patientRecordId = patientRecordId;
    }

    public String getTvDocNamePatientRecord() {
        return tvDocNamePatientRecord;
    }

    public void setTvDocNamePatientRecord(String tvDocNamePatientRecord) {
        this.tvDocNamePatientRecord = tvDocNamePatientRecord;
    }

    public String getTvPatientNamePatientRecord() {
        return tvPatientNamePatientRecord;
    }

    public void setTvPatientNamePatientRecord(String tvPatientNamePatientRecord) {
        this.tvPatientNamePatientRecord = tvPatientNamePatientRecord;
    }

    public String getTvDatePatientRecord() {
        return tvDatePatientRecord;
    }

    public void setTvDatePatientRecord(String tvDatePatientRecord) {
        this.tvDatePatientRecord = tvDatePatientRecord;
    }

    public String getTvRemarksPatientRecord() {
        return tvRemarksPatientRecord;
    }

    public void setTvRemarksPatientRecord(String tvRemarksPatientRecord) {
        this.tvRemarksPatientRecord = tvRemarksPatientRecord;
    }

    public String getTvPrescriptionPatientRecord() {
        return tvPrescriptionPatientRecord;
    }

    public void setTvPrescriptionPatientRecord(String tvPrescriptionPatientRecord) {
        this.tvPrescriptionPatientRecord = tvPrescriptionPatientRecord;
    }

    public String getPatientBookedSlotId() {
        return patientBookedSlotId;
    }

    public void setPatientBookedSlotId(String patientBookedSlotId) {
        this.patientBookedSlotId = patientBookedSlotId;
    }

    public String getDoctorBookedSlotId() {
        return doctorBookedSlotId;
    }

    public void setDoctorBookedSlotId(String doctorBookedSlotId) {
        this.doctorBookedSlotId = doctorBookedSlotId;
    }

    public DocPatientViewMedicalRecordModel() {
    }

    public void setAppointmentKey(String appointmentKey) {
        this.appointmentKey = appointmentKey;
    }

    public String getAppointmentKey() {
        return appointmentKey;
    }

    public boolean getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    public String getPatientRecordId() {
        return patientRecordId;
    }

    public void setPatientRecordId(String patientRecordId) {
        this.patientRecordId = patientRecordId;
    }
}
