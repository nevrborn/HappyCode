package com.cloud9.android.happycode;

import android.text.format.DateFormat;

import java.util.ArrayList;
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

    private Date mDate;
    private String mUser;
    private String mTester;
    private ArrayList<Integer> mTestResultArray;
    private Map<String, Integer> mResultArray = new HashMap<>();
    private ArrayList<String> mTop3StrengthKeys = new ArrayList<String>();

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

    private TestResult() {

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

    public Map<String, Integer> getResultArray() {
        return mResultArray;
    }

    public void setResultArray(Map<String, Integer> resultArray) {
        mResultArray = resultArray;
        findTop3StrengthsHash(mResultArray);
    }

    public void findTop3StrengthsHash(Map<String, Integer> resultArray) {
        Map<String, Integer> tempArray = new HashMap<>(resultArray);

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
}
