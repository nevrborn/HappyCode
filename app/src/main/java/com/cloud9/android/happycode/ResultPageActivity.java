package com.cloud9.android.happycode;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.UUID;

/**
 * Created by paulvancappelle on 23-11-16.
 */

public class ResultPageActivity extends SingleFragmentActivity {

    private static final String TEST_RESULT_ID = "test_result_id";

    private static UUID mTestResultID;

    /*
    * create Intent to start this activity
    */
    public static Intent newIntent(Context context, UUID testResultID) {
        Intent i = new Intent(context, ResultPageActivity.class);
        mTestResultID = testResultID;
        return new Intent(context, ResultPageActivity.class);
    }

    /*
    * Method to create fragment from Activity
    */
    @Override
    protected Fragment createFragment() {
        //mTestResultID = (UUID) getIntent().getSerializableExtra(TEST_RESULT_ID);
        return ResultPageFragment.newInstance(mTestResultID);
    }

}
