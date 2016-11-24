package com.cloud9.android.happycode;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by nevrborn on 24.11.2016.
 */

public class CodesActivity extends SingleFragmentActivity {

    /*
    * create Intent to start this activity
    */
    public static Intent newIntent(Context context) {
        return new Intent(context, CodesActivity.class);
    }


    /* Method to create fragment from Activity */
    @Override
    protected Fragment createFragment() {
        return CodeFragment.newInstance();
    }

}
