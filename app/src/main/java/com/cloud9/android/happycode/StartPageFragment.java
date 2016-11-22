package com.cloud9.android.happycode;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by paulvancappelle on 22-11-16.
 */

public class StartPageFragment extends Fragment {

    Button mStartButton;


    /*
    * create new instance
    */
    public static Fragment newInstance() {
        return new StartPageFragment();
    }


    @Nullable
    @Override
    /*
    * onCreateView
    */
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start_page, container, false);

        // set the references
        mStartButton = (Button) view.findViewById(R.id.button_start);

        // set listeners
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // add code here to start questions
            }
        });


        return view;
    }



}
