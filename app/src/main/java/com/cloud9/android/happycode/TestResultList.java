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

    public void addResult(TestResult result) {
        mTestResultList.add(result);
    }

    public void deleteResult(TestResult result) {
        mTestResultList.remove(result);
    }

    public TestResult getResult(UUID id) {
        for (TestResult result : mTestResultList) {
            if (result.getID().equals(id)) {
                return result;
            }
        }
        return null;
    }

    public List<TestResult> getTestResultList() {
        return mTestResultList;
    }
}
