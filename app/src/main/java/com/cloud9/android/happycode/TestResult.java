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

    private int mNo1StrengthIndex;
    private int mNo2StrengthIndex;
    private int mNo3StrengthIndex;

    private static TestResult mTestResult;


    /* set singleton instance of the result*/
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

    public int getNo1StrengthIndex() {
        return mNo1StrengthIndex;
    }

    public void setNo1StrengthIndex(int no1StrengthIndex) {
        mNo1StrengthIndex = no1StrengthIndex;
    }

    public int getNo2StrengthIndex() {
        return mNo2StrengthIndex;
    }

    public void setNo2StrengthIndex(int no2StrengthIndex) {
        mNo2StrengthIndex = no2StrengthIndex;
    }

    public int getNo3StrengthIndex() {
        return mNo3StrengthIndex;
    }

    public void setNo3StrengthIndex(int no3StrengthIndex) {
        mNo3StrengthIndex = no3StrengthIndex;
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

    public void setDate() {
        mDate = new Date();
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
        //mDate = new Date();
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

        setNo1StrengthIndex(mTempTop3Array.get(0));
        setNo2StrengthIndex(mTempTop3Array.get(1));
        setNo3StrengthIndex(mTempTop3Array.get(2));

        mTempResultsArray.clear();
        mTempTop3Array.clear();
    }
}
