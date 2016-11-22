package com.cloud9.android.happycode;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class StartPageActivity extends SingleFragmentActivity {


    /*
    * create Intent to start this activity
    */
    public Intent newIntent(Context context) {
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
