package com.cloud9.android.happycode;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by paulvancappelle on 24-11-16.
 */

public class TestHistoryActivity extends SingleFragmentActivity implements TestHistoryFragment.Callbacks {

    /*
    * create Intent to start this activity
    */
    public static Intent newIntent(Context context) {
        return new Intent(context, TestHistoryActivity.class);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }

    /*
    * Method to create fragment from Activity
    */
    @Override
    protected Fragment createFragment() {
        return new TestHistoryFragment();
    }

    @Override
    public void onTestResultSelected(String testresultKey) {
        if (findViewById(R.id.detail_fragment_container) == null) {
            Intent intent = ResultPageActivity.newIntent(this, testresultKey, false);
            startActivity(intent);
        } else {
            Fragment newDetail = ResultPageFragment.newInstance(testresultKey, false);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, newDetail)
                    .commit();
        }
    }

    public void onTestResultDeleted(TestResult testResult) {
        TestHistoryFragment testResultFragment = (TestHistoryFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        testResultFragment.updateModel();
    }
}