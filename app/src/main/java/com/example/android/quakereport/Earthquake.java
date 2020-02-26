package com.example.android.quakereport;

public class Earthquake {

    private double mMag;
    private String mPlace;
    private long mTime;
    private String mUrl;

    public Earthquake(double mMag, String mPlace, long mTime, String mUrl) {
        this.mMag = mMag;
        this.mPlace = mPlace;
        this.mTime = mTime;
        this.mUrl= mUrl;
    }



    public double getmMag() {
        return mMag;
    }

    public String getmPlace() {
        return mPlace;
    }

    public long getmTime() {
        return mTime;
    }

    public String getmUrl() {
        return mUrl;
    }
}
