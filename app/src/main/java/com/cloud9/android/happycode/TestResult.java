package com.cloud9.android.happycode;

import android.text.format.DateFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

/**
 * Created by nevrborn on 22.11.2016.
 */

public class TestResult {

    private Date mDate;
    private UUID mID;
    private String mUser;
    private String mTester;
    private ArrayList<Integer> mTestResultArray;

    private int mNo1Strength;
    private int mNo2Strength;
    private int mNo3Strength;

    private static TestResult mTestResult;


    /* get singleton instance of the result*/
    public static TestResult getInstance() {
        if (mTestResult == null) {
            mTestResult = new TestResult();
        }

        return mTestResult;
    }

    private TestResult() {

        // UUID to be used when we hook up to Firebase
        mID = UUID.randomUUID();
    }

    public void deleteResult() {
        mTestResult = null;
    }


    public UUID getID() {
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

    public String getUser() {
        return mUser;
    }

    public void setUser(String user) {
        mUser = user;
    }

    public Date getDate() {
        return mDate;
    }

    public String getDateTime() {
        Date date = this.getDate();
        String dateFormat = "EEE, dd-MM-yyyy hh:mm";
        String dateString = DateFormat.format(dateFormat, date).toString();
        return dateString;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getTester() {
        return mTester;
    }

    public void setTester(String tester) {
        mTester = tester;
    }

    public ArrayList<Integer> getTestResultArray() {
        return mTestResultArray;
    }

    public void setTestResult(ArrayList<Integer> testResultArray) {
        mTestResultArray = testResultArray;
        mDate = new Date();
        findTop3Strengths();
    }

    public void findTop3Strengths() {
        ArrayList<Integer> mTempTop3Array = new ArrayList<Integer>();
        ArrayList<Integer> mTempResultsArray = mTestResultArray;

        int j = 0;

        while (j < 3) {
            int max = Collections.max(mTempResultsArray);
            int index = mTempResultsArray.indexOf(max);
            mTempTop3Array.add(index);
            mTempResultsArray.set(index, 0);
            j += 1;
        }

        setNo1Strength(mTempTop3Array.get(0));
        setNo2Strength(mTempTop3Array.get(1));
        setNo3Strength(mTempTop3Array.get(2));

        mTempResultsArray.clear();
        mTempTop3Array.clear();
    }
}
