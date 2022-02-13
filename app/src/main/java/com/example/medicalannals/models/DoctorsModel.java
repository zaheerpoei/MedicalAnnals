package com.example.medicalannals.models;

public class DoctorsModel {
    public String name = "Dr. Ahmed";
    public String email;
    public String contact;
    public String age;
    public String gender;
    public String specialization;
    public String hospital;
    public String experience;
    public String qualification;
    public String fees;

    public DoctorsModel(String name, String email, String contact, String age, String gender, String specialization, String hospital, String experience, String qualification, String fees) {
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.age = age;
        this.gender = gender;
        this.specialization = specialization;
        this.hospital = hospital;
        this.experience = experience;
        this.qualification = qualification;
        this.fees = fees;
    }

    public DoctorsModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }
}
