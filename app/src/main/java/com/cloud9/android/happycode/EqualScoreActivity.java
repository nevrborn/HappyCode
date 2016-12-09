package com.cloud9.android.happycode;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import java.util.ArrayList;

public class EqualScoreActivity extends SingleFragmentActivity {

    private static TestResult mTestResult;
    private static ArrayList<String> mEqualScoreKeys;
    private static int mEqualScoresInTopThree;


    /*
    * create Intent to start this activity
    */
    public static Intent newIntent(Context context, TestResult testResult, ArrayList<String> sortedStrengthKeys, int equalScoresInTopThree) {
        mTestResult = testResult;
        mEqualScoreKeys = sortedStrengthKeys;
        mEqualScoresInTopThree = equalScoresInTopThree;
        return new Intent(context, EqualScoreActivity.class);
    }

    @Override
    protected Fragment createFragment() {
        return EqualScoreFragment.newInstance(1, mTestResult, mEqualScoreKeys, mEqualScoresInTopThree);
    }
}
