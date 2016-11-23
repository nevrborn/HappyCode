package com.cloud9.android.happycode;

import java.util.Date;
import java.util.UUID;

/**
 * Created by nevrborn on 22.11.2016.
 */

public class Result {

    private Date mDate;
    private String mID;
    private String mName;
    private int mNo1Strength;
    private int mNo2Strength;
    private int mNo3Strength;

    private static Result mResult;


    /* get singleton instance of the result*/
    public static Result getInstance() {
        if (mResult == null) {
            mResult = new Result();
        }
        return mResult;
    }


    private Result() {

        // UUID to be used when we hook up to Firebase
        mID = UUID.randomUUID().toString();
    }

    public void deleteResult() {
        mResult = null;
    }


    public String getID() {
        return mID;
    }

    public int getNo1Strength() {
        return mNo1Strength;
    }

    public void setNo1Strength(int no1Strength) {
        mNo1Strength = no1Strength;
    }

    public int getNo2Strength() {
        return mNo2Strength;
    }

    public void setNo2Strength(int no2Strength) {
        mNo2Strength = no2Strength;
    }

    public int getNo3Strength() {
        return mNo3Strength;
    }

    public void setNo3Strength(int no3Strength) {
        mNo3Strength = no3Strength;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }
}
