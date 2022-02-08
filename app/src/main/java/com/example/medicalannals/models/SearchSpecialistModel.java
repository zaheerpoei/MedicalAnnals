package com.example.medicalannals.models;

public class SearchSpecialistModel {

    public String specialization;
    public int specializationImage;

    public SearchSpecialistModel(int specializationImage , String specialization)
    {
        this.specializationImage = specializationImage;
        this.specialization=specialization;
    }

    public int getSpecializationImage()
    {
        return specializationImage;
    }

    public void setSpecializationImage(int specializationImage)
    {
        this.specializationImage = specializationImage;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

}
