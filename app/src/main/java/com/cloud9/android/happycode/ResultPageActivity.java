package com.cloud9.android.happycode;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by paulvancappelle on 23-11-16.
 */

public class ResultPageActivity extends SingleFragmentActivity {
    // mNo1Strength
    private static final String EXTRA_NR_1_STRENGTH = "com.cloud9.android.happycode.result.1";
    private static final String EXTRA_NR_2_STRENGTH = "com.cloud9.android.happycode.result.2";
    private static final String EXTRA_NR_3_STRENGTH = "com.cloud9.android.happycode.result.3";


    /*
    * create Intent to start this activity
    */
    public static Intent newIntent(Context context, String nr1Strength, String nr2Strength, String nr3Strength) {
        Intent i = new Intent(context, StartPageActivity.class);
        i.putExtra(EXTRA_NR_1_STRENGTH, nr1Strength);
        i.putExtra(EXTRA_NR_2_STRENGTH, nr2Strength);
        i.putExtra(EXTRA_NR_3_STRENGTH, nr3Strength);
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
