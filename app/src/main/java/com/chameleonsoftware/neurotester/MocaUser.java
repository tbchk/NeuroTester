package com.chameleonsoftware.neurotester;

import android.graphics.Bitmap;

/**
 * Created by camaleon2 on 30/11/15.
 */
public class MocaUser {

    private String id;
    private String date;
    private String name;
    private String lastname;
    private String email;
    private String gender;
    private String phone;
    private String study;

    //Moca 1
    Bitmap moca1Bitmap;
    Bitmap moca2Bitmap;
    Bitmap moca3Bitmap;


    int moca1Time; //Seconds
    int moca2Time;
    int moca3Time;


    public MocaUser(String id,String date, String name, String lastname, String email, String gender, String phone,String study) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.gender = gender;
        this.phone = phone;
        this.study = study;

        moca1Bitmap=null;
    }

    //========================= MOCA 1 ========================================
    public void setMoca1Bitmap(Bitmap moca1Bitmap) {
        this.moca1Bitmap = moca1Bitmap;
    }
    public Bitmap getMoca1Bitmap() {
        return moca1Bitmap;
    }
    public void setMoca1Time(int moca1Time){
        this.moca1Time = moca1Time;
    }
    public int getMoca1Time() {
        return moca1Time;
    }

    //========================== MOCA 2 ======================================
    public void setMoca2Bitmap(Bitmap moca2Bitmap) {
        this.moca2Bitmap = moca2Bitmap;
    }
    public Bitmap getMoca2Bitmap() {
        return moca2Bitmap;
    }
    public void setMoca2Time(int moca2Time) {
        this.moca2Time = moca2Time;
    }
    public int getMoca2Time() {
        return moca2Time;
    }

    // ========================= MOCA 3 ========================================
    public void setMoca3Bitmap(Bitmap moca3Bitmap) {
        this.moca3Bitmap = moca3Bitmap;
    }
    public Bitmap getMoca3Bitmap() {
        return moca3Bitmap;
    }
    public int getMoca3Time() {
        return moca3Time;
    }
    public void setMoca3Time(int moca3Time) {
        this.moca3Time = moca3Time;
    }


    // PERSONAL DATA ACCESS
    public String getId() {
        return id;
    }
    public String getDate() {
        return date;
    }
    public String getName() {
        return name;
    }
    public String getLastname() {
        return lastname;
    }
    public String getEmail() {
        return email;
    }
    public String getGender() {
        return gender;
    }
    public String getPhone() {
        return phone;
    }
    public String getStudy() {
        return study;
    }
}
