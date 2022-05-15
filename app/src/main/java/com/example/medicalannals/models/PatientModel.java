package com.example.medicalannals.models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class PatientModel implements Serializable {
    private String name;
    private String email;
    private String gender;
    private String age;
    private String number;
    private String patientProfilePic;

    public PatientModel(String name, String email, String gender, String age, String number , String patientProfilePic) {
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.age = age;
        this.number = number;
        this.patientProfilePic = patientProfilePic;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPatientProfilePic() {
        return patientProfilePic;
    }

    public void setPatientProfilePic(String patientProfilePic) {
        this.patientProfilePic = patientProfilePic;
    }

}
