package com.cloud9.android.happycode;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by nevrborn on 22.11.2016.
 */

public class QuestionActivity extends SingleFragmentActivity {

    /*
    * create Intent to start this activity
    */
    public static Intent newIntent(Context context) {
        return new Intent(context, QuestionActivity.class);
    }


    /* Method to create fragment from Activity */
    @Override
    protected Fragment createFragment() {
        return QuestionFragment.newInstance();
    }

}
