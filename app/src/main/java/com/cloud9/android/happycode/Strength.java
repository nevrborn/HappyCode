package com.cloud9.android.happycode;

/**
 * Created by nevrborn on 22.11.2016.
 */

public class Strength {

    private String mID;
    private int mTitleID;
    private int mQuestionID;
    private int mDescriptionID;
    private int mImageID;
    private int mIconID;

    // Contstructor to make Strengths
    public Strength(String id, int titleId, int strengthId, int descriptionId, int imageId, int iconId) {
        this.mID = id;
        this.mTitleID = titleId;
        this.mQuestionID = strengthId;
        this.mDescriptionID = descriptionId;
        this.mImageID = imageId;
        this.mIconID = iconId;

    }

    public String getID() {
        return mID;
    }

    public int getQuestionID() {
        return mQuestionID;
    }

    public int getDescriptionID() {
        return mDescriptionID;
    }

    public int getTitleID() {
        return mTitleID;
    }

    public int getImageID() {
        return mImageID;
    }

    public int getIconID() {
        return mIconID;
    }
}
