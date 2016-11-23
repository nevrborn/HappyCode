package com.cloud9.android.happycode;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by paulvancappelle on 23-11-16.
 */

public class ResultPageActivity extends SingleFragmentActivity {


    /*
    * create Intent to start this activity
    */
    public static Intent newIntent(Context context, String nr1Strength, String nr2Strength, String nr3Strength) {
        Intent i = new Intent(context, StartPageActivity.class);

        return new Intent(context, StartPageActivity.class);
    }


    /*
    * Method to create fragment from Activity
    */
    @Override
    protected Fragment createFragment() {
        return ResultPageFragment.newInstance();
    }

}
