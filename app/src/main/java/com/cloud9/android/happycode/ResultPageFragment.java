package com.cloud9.android.happycode;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by paulvancappelle on 23-11-16.
 */
public class ResultPageFragment extends Fragment {

    private static final String TAG = "ResultPageFragment";
    private static final String KEY_CURRENT_STRENGTH = "currentStrength";

    private int mNr1Strength;
    private int mNr2Strength;
    private int mNr3Strength;
    private int mCurrentStrength; // which result is currently selected (0, 1, ...)
    private Result mResult;

    private ImageView mResultIcon1;
    private ImageView mResultIcon2;
    private ImageView mResultIcon3;
    private TextView mStrenghtText;
    private TextView mStrengthTitle;
    private Button mToMenuButton;
    private ImageView mResultLine1;
    private ImageView mResultLine2;
    private ImageView mResultLine3;
    private Strength[] mStrengths;

    /*
    * create new instance
    */
    public static Fragment newInstance() {
        return new ResultPageFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // code to initialize the three strengths
        mResult = Result.getInstance();
        mStrengths = QuestionFragment.newInstance().mStrengths;

        mNr1Strength = mResult.getNo1Strength();
        mNr2Strength = mResult.getNo2Strength();
        mNr3Strength = mResult.getNo3Strength();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);


        // set the references
        mResultIcon1 = (ImageView) view.findViewById(R.id.imageview_result_one);
        mResultIcon2 = (ImageView) view.findViewById(R.id.imageview_result_two);
        mResultIcon3 = (ImageView) view.findViewById(R.id.imageview_result_three);
        mStrenghtText = (TextView) view.findViewById(R.id.textview_result_strentgh_text);
        mStrengthTitle = (TextView) view.findViewById(R.id.textview_result_strenght_title);
        mToMenuButton = (Button) view.findViewById(R.id.button_result_to_menu);
        //mResultLine1 = (ImageView) view.findViewById(R.id.imageview_result_line);
        //mResultLine2 = (ImageView) view.findViewById(R.id.imageview_result_line_two);
        //mResultLine3 = (ImageView) view.findViewById(R.id.imageview_result_line_three);


        // set images for the results
        mResultIcon1.setImageResource(mStrengths[mNr1Strength].getIconID());
        mResultIcon2.setImageResource(mStrengths[mNr2Strength].getIconID());
        mResultIcon3.setImageResource(mStrengths[mNr3Strength].getIconID());


        // set listeners
        mResultIcon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentStrength = 0;
                setPickedResult();
            }
        });

        mResultIcon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentStrength = 1;
                setPickedResult();
            }
        });

        mResultIcon3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentStrength = 2;
                setPickedResult();
            }
        });

        mToMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = StartPageActivity.newIntent(getActivity());
                startActivity(i);
                mResult.deleteResult();
            }
        });


        // set the current strenght, if there use the savedInstanceState
        if (savedInstanceState != null) {
            mCurrentStrength = savedInstanceState.getInt(KEY_CURRENT_STRENGTH);
        } else {
            mCurrentStrength = 0;
        }
        setPickedResult();

        return view;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
        outState.putInt(KEY_CURRENT_STRENGTH, mCurrentStrength);
    }


    private void setPickedResult() {
        switch (mCurrentStrength) {
            case 0:
                mStrengthTitle.setText(mStrengths[mNr1Strength].getTitleID());
                mStrenghtText.setText(mStrengths[mNr1Strength].getDescriptionID());
                mResultIcon1.setAlpha(1.0f);
                mResultIcon2.setAlpha(0.4f);
                mResultIcon3.setAlpha(0.4f);
                return;
            case 1:
                mStrengthTitle.setText(mStrengths[mNr2Strength].getTitleID());
                mStrenghtText.setText(mStrengths[mNr2Strength].getDescriptionID());
                mResultIcon1.setAlpha(0.4f);
                mResultIcon2.setAlpha(1.0f);
                mResultIcon3.setAlpha(0.4f);
                return;
            case 2:
                mStrengthTitle.setText(mStrengths[mNr3Strength].getTitleID());
                mStrenghtText.setText(mStrengths[mNr3Strength].getDescriptionID());
                mResultIcon1.setAlpha(0.4f);
                mResultIcon2.setAlpha(0.4f);
                mResultIcon3.setAlpha(1.0f);
                return;
            default:
                Log.e(TAG, "Picked result " + mCurrentStrength + " does not exist");
        }
    }

}
