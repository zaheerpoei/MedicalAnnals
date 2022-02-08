package com.example.medicalannals.models;

public class PatientModel {
    private String name;
    private String email;
    private String gender;
    private int age;
    private String number;

    public PatientModel(String name, String email, String gender, int age, String number) {
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.age = age;
        this.number = number;
    }

    public PatientModel() {
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
