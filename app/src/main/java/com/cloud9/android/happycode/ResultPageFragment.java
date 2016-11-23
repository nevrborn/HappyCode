package com.cloud9.android.happycode;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

    private int mNr1Strength;
    private int mNr2Strength;
    private int mNr3Strength;
    private Result mResult;

    private ImageView mResultIcon1;
    private ImageView mResultIcon2;
    private ImageView mResultIcon3;
    private TextView mStrenghtText;
    private TextView mStrengthTitle;
    private Button mRetakeButton;
    private ImageView mResultLine;
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

        mNr1Strength = mResult.getNo1Strength();
        mNr2Strength = mResult.getNo2Strength();
        mNr3Strength = mResult.getNo3Strength();

        mStrengths = QuestionFragment.newInstance().mStrengths;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        mResultIcon1 = (ImageView) view.findViewById(R.id.imageview_result_one);
        mResultIcon2 = (ImageView) view.findViewById(R.id.imageview_result_two);
        mResultIcon3 = (ImageView) view.findViewById(R.id.imageview_result_three);
        mStrenghtText = (TextView) view.findViewById(R.id.textview_result_strentgh_text);
        mStrengthTitle = (TextView) view.findViewById(R.id.textview_result_strentgh_title);
        mRetakeButton = (Button) view.findViewById(R.id.button_result_retake);
        mResultLine = (ImageView) view.findViewById(R.id.imageview_result_line);

        mStrenghtText.setText(mStrengths[mNr1Strength].getDescriptionID());

        mResultIcon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStrengthTitle.setText(mStrengths[mNr1Strength].getTitleID());
                mStrenghtText.setText(mStrengths[mNr1Strength].getDescriptionID());
            }
        });

        mResultIcon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStrengthTitle.setText(mStrengths[mNr2Strength].getTitleID());
                mStrenghtText.setText(mStrengths[mNr2Strength].getDescriptionID());
            }
        });

        mResultIcon3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStrengthTitle.setText(mStrengths[mNr3Strength].getTitleID());
                mStrenghtText.setText(mStrengths[mNr3Strength].getDescriptionID());
            }
        });

        return view;
    }

}
