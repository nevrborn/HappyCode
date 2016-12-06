package com.cloud9.android.happycode;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EqualScores extends SingleFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_equal_scores);
    }

    @Override
    protected Fragment createFragment() {
        return null;
    }
}
