package com.cloud9.android.happycode;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EqualScoreActivity extends SingleFragmentActivity {

    /*
    * create Intent to start this activity
    */
    public static Intent newIntent(Context context) {
        return new Intent(context, EqualScoreActivity.class);
    }

    @Override
    protected Fragment createFragment() {
        return EqualScoreFragment.newInstance(1);
    }
}
