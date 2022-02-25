package com.example.medicalannals.models;

public class DocPatientViewMedicalRecordModel {

    public String tvDocNamePatientRecord;
    public String tvPatientNamePatientRecord;
    public String tvDatePatientRecord;
    public String tvRemarksPatientRecord;
    public String tvPrescriptionPatientRecord;

    public DocPatientViewMedicalRecordModel(String tvDocNamePatientRecord ,String tvPatientNamePatientRecord, String tvDatePatientRecord, String tvRemarksPatientRecord , String tvPrescriptionPatientRecord )
    {
        this.tvPatientNamePatientRecord = tvPatientNamePatientRecord;
        this.tvDocNamePatientRecord = tvDocNamePatientRecord;
        this.tvDatePatientRecord=tvDatePatientRecord;
        this.tvRemarksPatientRecord=tvRemarksPatientRecord;
        this.tvPrescriptionPatientRecord=tvPrescriptionPatientRecord;
    }

    public String getPatientNameRecord()
    {
        return tvDocNamePatientRecord;
    }

    public void setTvPatientNameRecord(String tvDocNamePatientRecord)
    {
        this.tvDocNamePatientRecord = tvDocNamePatientRecord;
    }

    public String getTvDocNamePatientRecord() {
        return tvDocNamePatientRecord;
    }

    public void setTvDocNamePatientRecord(String tvDocNamePatientRecord) {
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

    public String getRemarksDocPatientRecord()
    {
        return tvRemarksPatientRecord;
    }

    public void setRemarksDocPatientRecord(String tvRemarksPatientRecord)
    {
        this.tvRemarksPatientRecord = tvRemarksPatientRecord;
    }

    public String getPrescriptionDocPatientRecord()
    {
        return tvPrescriptionPatientRecord;
    }

    public void setPrescriptionDocPatientRecord(String tvPrescriptionPatientRecord)
    {
        this.tvPrescriptionPatientRecord = tvPrescriptionPatientRecord;
    }
}
