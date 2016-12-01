package com.cloud9.android.happycode;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by paulvancappelle on 02-11-16.
 */

public abstract class SingleFragmentActivity extends AppCompatActivity {

    @LayoutRes
    protected int getLayoutResId() {
        return R.layout.activity_fragment;
    }

    @LayoutRes
    protected int getLayoutRedId() {
        return R.layout.activity_fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(getLayoutResId());
        setContentView(getLayoutResId());

        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();

        // set the references
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        // fragment code
        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }


    protected abstract Fragment createFragment();

}
