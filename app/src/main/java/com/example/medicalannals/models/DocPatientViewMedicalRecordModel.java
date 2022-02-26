package com.example.medicalannals.models;

public class DocPatientViewMedicalRecordModel {

    public String tvDocNamePatientRecord;
    public String tvPatientNamePatientRecord;
    public String tvDatePatientRecord;
    public String tvRemarksPatientRecord;
    public String tvPrescriptionPatientRecord;
    public String patientBookedSlotId;
    public String doctorBookedSlotId;

    public DocPatientViewMedicalRecordModel(String tvDocNamePatientRecord ,String tvPatientNamePatientRecord, String tvDatePatientRecord, String tvRemarksPatientRecord , String tvPrescriptionPatientRecord,String patientBookedSlotId, String doctorBookedSlotId)
    {
        this.tvPatientNamePatientRecord = tvPatientNamePatientRecord;
        this.tvDocNamePatientRecord = tvDocNamePatientRecord;
        this.tvDatePatientRecord=tvDatePatientRecord;
        this.tvRemarksPatientRecord=tvRemarksPatientRecord;
        this.tvPrescriptionPatientRecord=tvPrescriptionPatientRecord;
        this.patientBookedSlotId = patientBookedSlotId;
        this.doctorBookedSlotId = doctorBookedSlotId;
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
}
