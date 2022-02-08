package com.example.medicalannals.models;

public class PatientMedicalRecordModel {
    public String tvDocNamePatientRecord;
    public String tvDatePatientRecord;
    public String tvRemarksPatientRecord;
    public String tvPrescriptionPatientRecord;

    public PatientMedicalRecordModel(String tvDocNamePatientRecord , String tvDatePatientRecord, String tvRemarksPatientRecord , String tvPrescriptionPatientRecord )
    {
        this.tvDocNamePatientRecord = tvDocNamePatientRecord;
        this.tvDatePatientRecord=tvDatePatientRecord;
        this.tvRemarksPatientRecord=tvRemarksPatientRecord;
        this.tvPrescriptionPatientRecord=tvPrescriptionPatientRecord;
    }

    public String getDocNamePatientRecord()
    {
        return tvDocNamePatientRecord;
    }

    public void setTvDocNamePatientRecord(String tvDocNamePatientRecord)
    {
        this.tvDocNamePatientRecord = tvDocNamePatientRecord;
    }

    public String getDatePatientRecord()
    {
        return tvDatePatientRecord;
    }

    public void setDatePatientRecord(String tvDatePatientRecord)
    {
        this.tvDatePatientRecord = tvDatePatientRecord;
    }

    public String getRemarksPatientRecord()
    {
        return tvRemarksPatientRecord;
    }

    public void setRemarksPatientRecord(String tvRemarksPatientRecord)
    {
        this.tvRemarksPatientRecord = tvRemarksPatientRecord;
    }

    public String getPrescriptionPatientRecord()
    {
        return tvPrescriptionPatientRecord;
    }

    public void setPrescriptionPatientRecord(String tvPrescriptionPatientRecord)
    {
        this.tvPrescriptionPatientRecord = tvPrescriptionPatientRecord;
    }
}
