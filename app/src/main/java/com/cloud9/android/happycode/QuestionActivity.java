package com.cloud9.android.happycode;

import android.support.v4.app.Fragment;

/**
 * Created by nevrborn on 22.11.2016.
 */

public class QuestionActivity extends SingleFragmentActivity {

    /* Method to create fragment from Activity */
    @Override
    protected Fragment createFragment() {
        return QuestionFragment.newInstance();
    }
}
