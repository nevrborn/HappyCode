package com.cloud9.android.happycode;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class StartPageActivity extends SingleFragmentActivity {

    /*
    * create Intent to start this activity
    */
    public static Intent newIntent(Context context) {
        return new Intent(context, StartPageActivity.class);
    }


    /*
    * Method to create fragment from Activity
    */
    @Override
    protected Fragment createFragment() {
        return StartPageFragment.newInstance();
    }


}
