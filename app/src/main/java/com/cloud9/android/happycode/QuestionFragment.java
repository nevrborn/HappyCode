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
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by nevrborn on 22.11.2016.
 */

public class QuestionFragment extends Fragment {

    private static final String TAG = "QuestionFragment";
    private static final String CURRENT_INDEX = "current_index";
    private static final String LAST_REACHED_INDEX = "last_reached_index";
    private static final String STRENGTH_ARRAY = "strength_array";

    private Button mNextButton;
    private Button mPreviousButton;
    private TextView mStrengthText;
    private TextView mPercentageText;
    private ImageView mImage;
    private SeekBar mSeekBar;
    private TextView mProgressCount;

    //private ArrayList<Integer> mStrenghtArray = new ArrayList<Integer>();   // Temporary array to hold all the different Percentages from the Strenghts
    private Map<String, Integer> mResultArray = new HashMap<>();
    private TestResult mTestResult;
    private int mCurrentIndex = 0;
    private int mLastIndexReached = 0;
    private int mPercentage;
    private TestResultList mTestResultList;

    private StrengthList mStrengths;

    /* Method to create fragment */
    public static QuestionFragment newInstance() {
        return new QuestionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);

        mStrengths = StrengthList.get(getContext());
        mTestResult = TestResult.getInstance();

        // Setting up variables for the imageviews, buttons and textviews
        mImage = (ImageView) view.findViewById(R.id.imageview_question);
        mStrengthText = (TextView) view.findViewById(R.id.textview_question_text);
        mPercentageText = (TextView) view.findViewById(R.id.textView_question_percentage);
        mNextButton = (Button) view.findViewById(R.id.button_question_next);
        mPreviousButton = (Button) view.findViewById(R.id.button_question_previous);
        mSeekBar = (SeekBar) view.findViewById(R.id.seekBar_question);
        mProgressCount = (TextView) view.findViewById(R.id.textView_progress_counter);

        mProgressCount.setText(getString(R.string.question_progress_count, mCurrentIndex + 1));

        mPercentage = 50;
        if (mCurrentIndex == 0) {
            mPreviousButton.setAlpha(0.6f);
        } else {
            mPreviousButton.setAlpha(1.0f);
        }

        // Setting up what happens when the next button is pressed - go to next strength
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Increase index by one till all strengths have been viewed, then setStrenghts and move to result screen
                if (mCurrentIndex < mStrengths.getSize() - 1) {
                    setPercentage(mPercentage);
                    mPreviousButton.setAlpha(1.0f);
                    mCurrentIndex++;

                    mProgressCount.setText(getString(R.string.question_progress_count, mCurrentIndex + 1));

                    // add the last index reached if necessary
                    if (mCurrentIndex > mLastIndexReached) {
                        mLastIndexReached++;
                    }

                    // update the view for the new question
                    updateStrength();
                    if (mCurrentIndex == mLastIndexReached) {
                        mSeekBar.setProgress(50);
                    } else {
                        mSeekBar.setProgress(mResultArray.get(mStrengths.getStrengthFromIndex(mCurrentIndex).getID()));
                    }

                    // set NEXT button to finish if at the last question
                    if (mCurrentIndex == mStrengths.getSize() - 1) {
                        mNextButton.setText(R.string.button_finish);
                    }

                    // you're at the last question, so go the result page
                } else {
                    setPercentage(mPercentage);

                    mTestResultList = TestResultList.get(getContext());
                    mTestResult.setResultArray(mResultArray);
                    String tempID = "questionID";
                    mTestResultList.addTestresult(mTestResult, tempID);

                    // Go to TestResult page
                    Intent i = ResultPageActivity.newIntent(getActivity(), tempID, true);
                    startActivity(i);

                }
            }
        });


        // Setting up what happens when the previous button is pressed - go to previous strength if possible
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Decrease index by one till at first question
                if (mCurrentIndex > 0) {
                    setPercentage(mPercentage);
                    mCurrentIndex--;
                    updateStrength();

                    mSeekBar.setProgress(mResultArray.get(mStrengths.getStrengthFromIndex(mCurrentIndex).getID()));
                    mProgressCount.setText(getString(R.string.question_progress_count, mCurrentIndex + 1));
                    if (mCurrentIndex == 0) {
                        mPreviousButton.setAlpha(0.6f);
                    }

                }
            }
        });

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mPercentage = i;
                mPercentageText.setText(i + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        updateStrength();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(CURRENT_INDEX);
            mLastIndexReached = savedInstanceState.getInt(LAST_REACHED_INDEX);
            mResultArray = (Map<String, Integer>) savedInstanceState.getIntegerArrayList(STRENGTH_ARRAY);
        }

    }

    /*Method to update the Strength visible in the screen*/
    private void updateStrength() {
        int strength = mStrengths.getStrengthFromIndex(mCurrentIndex).getQuestionID();
        mStrengthText.setText(strength);
        mImage.setImageResource(mStrengths.getStrengthFromIndex(mCurrentIndex).getImageID());
    }

    // Set the percentage for each strength
    private void setPercentage(int percentage) {
        String strengthKey = mStrengths.getStrengthFromIndex(mCurrentIndex).getID();

        mResultArray.put(strengthKey, percentage);
        mPercentage = 50;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.i(TAG, "onSavedInstanceSTate");

        outState.putInt(CURRENT_INDEX, mCurrentIndex);
        outState.putInt(LAST_REACHED_INDEX, mLastIndexReached);
        outState.putIntegerArrayList(STRENGTH_ARRAY, (ArrayList<Integer>) mResultArray);

    }

}
