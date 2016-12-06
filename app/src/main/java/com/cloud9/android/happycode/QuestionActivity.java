package com.cloud9.android.happycode;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by nevrborn on 22.11.2016.
 */

public class QuestionActivity extends SingleFragmentActivity {

    private static final String USER_ID_FROM_TESTER = "user_id_from_tester";

    String tester_id;

    /*
    * create Intent to start this activity
    */
    public static Intent newIntent(Context context) {
        return new Intent(context, QuestionActivity.class);
    }


    /* Method to create fragment from Activity */
    @Override
    protected Fragment createFragment() {
        tester_id = (String) getIntent().getExtras().get(USER_ID_FROM_TESTER);
        return QuestionFragment.newInstance(tester_id);
    }

}
