package com.cloud9.android.happycode;

import android.text.format.DateFormat;

import com.google.android.gms.instantapps.InstantApps;
import com.google.firebase.database.ServerValue;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by nevrborn on 22.11.2016.
 */

public class TestResult {

    private String mDate;
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

    public TestResult(String date, String tester, Map<String, Integer> resultArray, String no1StrengthKey, String no2StrengthKey, String no3StrengthKey) {
        this.mDate = date;
        this.mTester = tester;
        this.mResultArray = resultArray;
        this.mNo1StrengthKey = no1StrengthKey;
        this.mNo2StrengthKey = no2StrengthKey;
        this.mNo3StrengthKey = no3StrengthKey;
    }

    public void deleteResult() {
        mTestResult = null;
    }

    public String getNo1StrengthKey() {
        return mNo1StrengthKey;
    }

    public void setNo1StrengthKey(String no1StrengthKey) {
        mNo1StrengthKey = no1StrengthKey;
    }

    public String getNo2StrengthKey() {
        return mNo2StrengthKey;
    }

    public void setNo2StrengthKey(String no2StrengthKey) {
        mNo2StrengthKey = no2StrengthKey;
    }

    public String getNo3StrengthKey() {
        return mNo3StrengthKey;
    }

    public void setNo3StrengthKey(String no3StrengthKey) {
        mNo3StrengthKey = no3StrengthKey;
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

    public String getDate() {
        return mDate;
    }

    public void setDate() {
        mDate = getCurrentTimeStamp();
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

    public void findTop3StrengthsHash(Map<String, Integer> resultArray) {
        Map<String, Integer> tempArray = new HashMap<>(resultArray);
        ArrayList<String> mTop3StrengthKeys = new ArrayList<String>();

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

    public static String getCurrentTimeStamp() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String currentDateTime = dateFormat.format(new Date());

            return currentDateTime;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
}
