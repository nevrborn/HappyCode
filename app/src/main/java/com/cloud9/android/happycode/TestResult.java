package com.cloud9.android.happycode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nevrborn on 22.11.2016.
 */

public class TestResult {

    private long mDate;
    private String mUser;
    private String mTester;
    private Map<String, Integer> mResultArray = new HashMap<>();
    private String mID;
    private Boolean mIsWrittenToFirebase = false;

    private String mNo1StrengthKey;
    private String mNo2StrengthKey;
    private String mNo3StrengthKey;

    private static TestResult mTestResult;

    /* get singleton instance of the result*/
    public static TestResult getInstance() {
        if (mTestResult == null) {
            mTestResult = new TestResult();
        }
        return mTestResult;
    }

    public TestResult() {

    }

    public TestResult(Long date, String tester, Map<String, Integer> resultArray, String no1StrengthKey, String no2StrengthKey, String no3StrengthKey) {
        this.mDate = date;
        this.mTester = tester;
        this.mResultArray = resultArray;
        this.mNo1StrengthKey = no1StrengthKey;
        this.mNo2StrengthKey = no2StrengthKey;
        this.mNo3StrengthKey = no3StrengthKey;
    }

    public String getNo1StrengthKey() {
        return mNo1StrengthKey;
    }

    public String getNo2StrengthKey() {
        return mNo2StrengthKey;
    }

    public String getNo3StrengthKey() {
        return mNo3StrengthKey;
    }

    public String getUser() {
        return mUser;
    }

    public void setUser(String user) {
        mUser = user;
    }

    public String getID() {
        return mID;
    }

    public void setID(String ID) {
        mID = ID;
    }

    public long getDate() {
        return mDate;
    }

    public void setDate(long date) {
        mDate = date;
    }


    public String getDateAndTime(long date) {
        long tempDate = date * 1000L;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        return dateFormat.format(tempDate);
    }

    public String getTester() {
        return mTester;
    }

    public void setTester(String tester) {
        mTester = tester;
    }

    public Map<String, Integer> getResultArray() {
        return mResultArray;
    }

    public void setResultArray(Map<String, Integer> resultArray) {
        mResultArray = resultArray;
        findTop3StrengthsHash(mResultArray);
    }

    private void findTop3StrengthsHash(Map<String, Integer> resultArray) {
        Map<String, Integer> tempArray = new HashMap<>(resultArray);
        ArrayList<String> mTop3StrengthKeys = new ArrayList<>();

        String keyOfMaxValue = "";

        int j = 0;

        while (j < 3) {

            int maxValue = Collections.max(tempArray.values());

            for (Map.Entry<String, Integer> entry : tempArray.entrySet()) {
                if (entry.getValue() == maxValue) {
                    keyOfMaxValue = entry.getKey();
                }
            }

            mTop3StrengthKeys.add(keyOfMaxValue);
            tempArray.remove(keyOfMaxValue);
            j += 1;
        }

        mNo1StrengthKey = mTop3StrengthKeys.get(0);
        mNo2StrengthKey = mTop3StrengthKeys.get(1);
        mNo3StrengthKey = mTop3StrengthKeys.get(2);

        tempArray.clear();
    }

    public Boolean getWrittenToFirebase() {
        return mIsWrittenToFirebase;
    }

    public void setWrittenToFirebase(Boolean writtenToFirebase) {
        mIsWrittenToFirebase = writtenToFirebase;
    }

    public void setNo1StrengthKey(String no1StrengthKey) {
        mNo1StrengthKey = no1StrengthKey;
    }

    public void setNo2StrengthKey(String no2StrengthKey) {
        mNo2StrengthKey = no2StrengthKey;
    }

    public void setNo3StrengthKey(String no3StrengthKey) {
        mNo3StrengthKey = no3StrengthKey;
    }
}
