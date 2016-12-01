package com.cloud9.android.happycode;

import android.content.ContentValues;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by nevrborn on 24.11.2016.
 */

public class TestResultList {

    private static TestResultList sTestResultList;
    private static List<TestResult> mTestResultList;
    private Context mContext;

    public TestResultList() {
        // constructor with no arguments to use for Firebase
    }

    public static TestResultList get(Context context) {
        if (sTestResultList == null) {
            sTestResultList = new TestResultList(context);
        }
        return sTestResultList;
    }

    private TestResultList(Context context) {
        mContext = context.getApplicationContext();
        mTestResultList = new ArrayList<>();
    }

    public void addTestresult(TestResult testResult, String ID) {
        testResult.setID(ID);
        mTestResultList.add(testResult);
    }

    public static List<TestResult> getTestResultList() {
        return mTestResultList;
    }

    public void clearResults() {
        mTestResultList.clear();
    }

    public void deleteTestResult(TestResult testresult) {
        mTestResultList.remove(testresult.getID());
    }

    public static TestResult getTestResult(String ID) {

        int i = 0;
        TestResult testresult = null;

        while (i < mTestResultList.size()) {

            if (mTestResultList.get(i).getID().equals(ID)) {
                testresult = mTestResultList.get(i);
                return testresult;
            }

            i += 1;
        }

        return testresult;
    }

    public int getSize() {
        return mTestResultList.size();
    }

    public TestResult getTestResultFromIndex(int index) {
        return mTestResultList.get(index);
    }
}
