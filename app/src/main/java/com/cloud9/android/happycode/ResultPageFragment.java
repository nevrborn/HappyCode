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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.UUID;

/**
 * Created by paulvancappelle on 23-11-16.
 */
public class ResultPageFragment extends Fragment {

    private static final String TAG = "ResultPageFragment";
    private static final String KEY_CURRENT_STRENGTH = "currentStrength";

    private static UUID mID;
    private int mNr1StrengthIndex;
    private int mNr2StrengthIndex;
    private int mNr3StrengthIndex;
    private int mCurrentStrength; // which result is currently selected (0, 1, ...)
    private TestResult mTestResult;
    //private TestResultList mTestResultList;
    private Strength[] mStrengths;
    private Boolean hasWrittenToFirebase = false;

    private ImageView mResultIcon1;
    private ImageView mResultIcon2;
    private ImageView mResultIcon3;
    private TextView mStrenghtText;
    private TextView mStrengthTitle;
    private Button mToMenuButton;
    private Button mSaveTestResultButton;

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    /*
    * create new instance
    */
    public static Fragment newInstance(UUID testResultID) {
        mID = testResultID;
        return new ResultPageFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // code to initialize the three strengths
        //mTestResultList = TestResultList.get(getContext());
        mTestResult = TestResult.getInstance();
        //mTestResult = mTestResultList.getResult(mID);
        mStrengths = QuestionFragment.newInstance().mStrengths;

        mNr1StrengthIndex = mTestResult.getNo1StrengthIndex();
        mNr2StrengthIndex = mTestResult.getNo2StrengthIndex();
        mNr3StrengthIndex = mTestResult.getNo3StrengthIndex();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        hasWrittenToFirebase = false;

        // set the references
        mResultIcon1 = (ImageView) view.findViewById(R.id.imageview_result_one);
        mResultIcon2 = (ImageView) view.findViewById(R.id.imageview_result_two);
        mResultIcon3 = (ImageView) view.findViewById(R.id.imageview_result_three);
        mStrenghtText = (TextView) view.findViewById(R.id.textview_result_strentgh_text);
        mStrengthTitle = (TextView) view.findViewById(R.id.textview_result_strenght_title);
        mToMenuButton = (Button) view.findViewById(R.id.button_result_to_menu);
        mSaveTestResultButton = (Button) view.findViewById(R.id.button_result_save_result);

        // set images for the results
        mResultIcon1.setImageResource(mStrengths[mNr1StrengthIndex].getIconID());
        mResultIcon2.setImageResource(mStrengths[mNr2StrengthIndex].getIconID());
        mResultIcon3.setImageResource(mStrengths[mNr3StrengthIndex].getIconID());


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
                mTestResult.deleteResult();
            }
        });

        mSaveTestResultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hasWrittenToFirebase == false) {
                    mTestResult.setUser("Jarle");
                    mTestResult.setTester("Paul");
                    //mTestResult.setDate();
                    writeToFirebase(mTestResult);
                    hasWrittenToFirebase = true;
                    mSaveTestResultButton.setEnabled(false);
                    mSaveTestResultButton.setClickable(false);
                }

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
                mStrengthTitle.setText(mStrengths[mNr1StrengthIndex].getTitleID());
                mStrenghtText.setText(mStrengths[mNr1StrengthIndex].getDescriptionID());
                mResultIcon1.setAlpha(1.0f);
                mResultIcon2.setAlpha(0.4f);
                mResultIcon3.setAlpha(0.4f);
                return;
            case 1:
                mStrengthTitle.setText(mStrengths[mNr2StrengthIndex].getTitleID());
                mStrenghtText.setText(mStrengths[mNr2StrengthIndex].getDescriptionID());
                mResultIcon1.setAlpha(0.4f);
                mResultIcon2.setAlpha(1.0f);
                mResultIcon3.setAlpha(0.4f);
                return;
            case 2:
                mStrengthTitle.setText(mStrengths[mNr3StrengthIndex].getTitleID());
                mStrenghtText.setText(mStrengths[mNr3StrengthIndex].getDescriptionID());
                mResultIcon1.setAlpha(0.4f);
                mResultIcon2.setAlpha(0.4f);
                mResultIcon3.setAlpha(1.0f);
                return;
            default:
                Log.e(TAG, "Picked result " + mCurrentStrength + " does not exist");
        }
    }

    private void writeToFirebase(TestResult testResult) {

        String key = mDatabase.child("testresults").push().getKey();
        mDatabase.child("testresults").child(key).setValue(testResult);
        mDatabase.child("testresults").child(key).setValue(mTestResult);
    }
}
