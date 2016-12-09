package com.cloud9.android.happycode;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by paulvancappelle on 23-11-16.
 */

public class ResultPageActivity extends SingleFragmentActivity implements ResultPageFragment.Callbacks {

    private static String mTestResultID;
    private static Boolean mIsFromQuestionPage;

    /*
    * create Intent to start this activity
    */
    public static Intent newIntent(Context context, String testResultID, Boolean isFromQuestionPage) {
        mTestResultID = testResultID;
        mIsFromQuestionPage = isFromQuestionPage;
        return new Intent(context, ResultPageActivity.class);
    }

    /*
    * Method to create fragment from Activity
    */
    @Override
    protected Fragment createFragment() {
        return ResultPageFragment.newInstance(mTestResultID, mIsFromQuestionPage);
    }

    @Override
    public void onTestResultDeleted() {

    }

    @Override
    public void onBackPressed() {
        /*
        * The QuestionFragment and the EqualScoreFragment start up the ResultPageActivity expecting a result.
        * Therefore when you press the backbutton getCallingActivity will not be empty, and the user
        * is send 'back' to the start Page
        */
        if (this.getCallingActivity() != null) {
            Intent i = StartPageActivity.newIntent(this);
            startActivity(i);
        }
        this.finish();
        return;
    }
}