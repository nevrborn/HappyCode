package com.cloud9.android.happycode;

/**
 * Created by nevrborn on 22.11.2016.
 */

public class Strength {

    private int mTitleID;
    private int mStrengthID;
    private int mDescriptionID;
    private int mPercentage;
    private int mImageID;

    // Contstructor to make Strengths
    public Strength(int titleId, int strengthId, int descriptionId, int image) {
        this.mTitleID = titleId;
        this.mStrengthID = strengthId;
        this.mDescriptionID = descriptionId;
        this.mImageID = image;


    }

    public int getStrengthID() {
        return mStrengthID;
    }

    public int getDescriptionID() {
        return mDescriptionID;
    }

    public int getPercentage() {
        return mPercentage;
    }

    public void setPercentage(int percentage) {
        mPercentage = percentage;
    }

    public int getTitleID() {
        return mTitleID;
    }

    public int getImageID() {
        return mImageID;
    }
}
