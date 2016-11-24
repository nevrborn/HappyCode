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
import java.util.Collections;
import java.util.Date;

/**
 * Created by nevrborn on 22.11.2016.
 */

public class QuestionFragment extends Fragment {

    private static final String TAG = "QuestionFragment";
    private static final String CURRENT_INDEX = "index";
    private static final String STRENGTH_ARRAY = "strength_array";

    private Button mNextButton;
    private TextView mStrengthText;
    private TextView mPercentageText;
    private ImageView mImage;
    private SeekBar mSeekBar;
    private ArrayList<Integer> mStrenghtArray = new ArrayList<Integer>();   // Temporary array to hold all the different Percentages from the Strenghts
    private ArrayList<Integer> mTempResultArray = new ArrayList<Integer>(); // Temporary array to hold the 3 strongest strengths.. Used to make a new Result
    private ArrayList<Result> mResults = new ArrayList<Result>();           // Array of all Results.
    private Result mResult;
    private int mCurrentIndex = 0;
    private int mPercentage;

    // Adding all the Strengths and descriptions to an array - mStrength
    public Strength[] mStrengths = new Strength[]{
            new Strength(R.string.strength_leader_title, R.string.strength_leader, R.string.strength_leader_description, R.mipmap.strength_leader, R.mipmap.icon_leader),
            new Strength(R.string.strength_storyteller_title, R.string.strength_storyteller, R.string.strength_storyteller_description, R.mipmap.strength_storyteller, R.mipmap.icon_storyteller),
            new Strength(R.string.strength_challenger_title, R.string.strength_challenger, R.string.strength_challenger_description, R.mipmap.strength_challenger, R.mipmap.icon_challenger),
            new Strength(R.string.strength_networker_title, R.string.strength_networker, R.string.strength_networker_description, R.mipmap.strength_networker, R.mipmap.icon_networker),
            new Strength(R.string.strength_moodmaker_title, R.string.strength_moodmaker, R.string.strength_moodmaker_description, R.mipmap.strength_moodmaker, R.mipmap.icon_networker),
            new Strength(R.string.strength_organiser_title, R.string.strength_organiser, R.string.strength_organiser_description, R.mipmap.strength_organiser, R.mipmap.icon_organiser),
            new Strength(R.string.strength_guardian_title, R.string.strength_guardian, R.string.strength_guardian_description, R.mipmap.strength_guardian, R.mipmap.icon_guardian),
            new Strength(R.string.strength_planner_title, R.string.strength_planner, R.string.strength_planner_description, R.mipmap.strength_planner, R.mipmap.icon_planner),
            new Strength(R.string.strength_hardworker_title, R.string.strength_hardworker, R.string.strength_hardworker_description, R.mipmap.strength_hardworker, R.mipmap.icon_hardworker),
            new Strength(R.string.strength_administrator_title, R.string.strength_administrator, R.string.strength_administrator_description, R.mipmap.strength_administrator, R.mipmap.icon_administrator),
            new Strength(R.string.strength_motivator_title, R.string.strength_motivator, R.string.strength_motivator_description, R.mipmap.strength_motivator, R.mipmap.icon_motivator),
            new Strength(R.string.strength_connector_title, R.string.strength_connector, R.string.strength_connector_description, R.mipmap.strength_connector, R.mipmap.icon_connector),
            new Strength(R.string.strength_unifier_title, R.string.strength_unifier, R.string.strength_unifier_description, R.mipmap.strength_unifier, R.mipmap.icon_unifier),
            new Strength(R.string.strength_inspirer_title, R.string.strength_inspirer, R.string.strength_inspirer_description, R.mipmap.strength_inspirer, R.mipmap.icon_inspirer),
            new Strength(R.string.strength_feeler_title, R.string.strength_feeler, R.string.strength_feeler_description, R.mipmap.strength_feeler, R.mipmap.icon_feeler),
            new Strength(R.string.strength_strategist_title, R.string.strength_strategist, R.string.strength_strategist_description, R.mipmap.strength_strategist, R.mipmap.icon_strategist),
            new Strength(R.string.strength_visionary_title, R.string.strength_visionary, R.string.strength_visionary_description, R.mipmap.strength_visionary, R.mipmap.icon_visionary),
            new Strength(R.string.strength_pathfinder_title, R.string.strength_pathfinder, R.string.strength_pathfinder_description, R.mipmap.strength_pathfinder, R.mipmap.icon_pathfinder),
            new Strength(R.string.strength_researcher_title, R.string.strength_researcher, R.string.strength_researcher_description, R.mipmap.strength_researcher, R.mipmap.icon_researcher),
            new Strength(R.string.strength_thinker_title, R.string.strength_thinker, R.string.strength_thinker_description, R.mipmap.strength_thinker, R.mipmap.icon_thinker)
    };

    /* Method to create fragment */
    public static QuestionFragment newInstance() {
        return new QuestionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);

        // Setting up variables for the imageviews, buttons and textviews
        mImage = (ImageView) view.findViewById(R.id.imageview_question);
        mStrengthText = (TextView) view.findViewById(R.id.textview_question_text);
        mPercentageText = (TextView) view.findViewById(R.id.textView_question_percentage);
        mNextButton = (Button) view.findViewById(R.id.button_question_next);
        mSeekBar = (SeekBar) view.findViewById(R.id.seekBar_question);

        mPercentage = 50;

        // Setting up what happens when the next button is pressed - go to next strength
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Increase index by one till all strengths have been view, then setStrenghts and move to result screen
                if (mCurrentIndex < mStrengths.length - 1) {
                    Log.d(TAG, getString(mStrengths[mCurrentIndex].getTitleID()) + " has " + mStrenghtArray.get(mCurrentIndex) + "%");
                    setPercentage(mPercentage);
                    mCurrentIndex += 1;
                    updateStrength();
                    mSeekBar.setProgress(50);

                    if (mCurrentIndex == mStrengths.length - 1) {
                        mNextButton.setText(R.string.button_finish);
                    }

                } else {
                    setPercentage(mPercentage);
                    Log.d(TAG, getString(mStrengths[mCurrentIndex].getTitleID()) + " has " + mStrenghtArray.get(mCurrentIndex) + "%");

                    findStrengths();

                    // Go to Result page
                    Intent i = ResultPageActivity.newIntent(getActivity());
                    startActivity(i);

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
            mStrenghtArray = savedInstanceState.getIntegerArrayList(STRENGTH_ARRAY);
        }

    }

    /*Method to update the Strength visible in the screen*/
    private void updateStrength() {
        int strength = mStrengths[mCurrentIndex].getStrengthID();
        mStrengthText.setText(strength);
        mImage.setImageResource(mStrengths[mCurrentIndex].getImageID());
    }

    // Set the percentage for each strength
    private void setPercentage(int percentage) {
        mStrenghtArray.add(percentage);
        mStrengths[mCurrentIndex].setPercentage(percentage);
        mPercentage = 50;
    }

    public void findStrengths() {
        int i = 0;
        int j = 0;

        // Import all the strengths from the mStrengthsBank
//        while (i < mStrengths.length - 1) {
//            mStrenghtArray.add(mStrengths[i].getPercentage());
//            i += 1;
//        }

        // Look through all the strength and find max. Add the index of where max is to a ResultArray and remove that index from the array
        // Do this 3 times to find three strengths
        // NEED TO ADJUST THIS IN ORDER TO ACCOUNT FOR STRENGTHS HAVING SIMILAR %
        while (j < 3) {
            int max = Collections.max(mStrenghtArray);
            int index = mStrenghtArray.indexOf(max);
            mTempResultArray.add(index);
            mStrenghtArray.set(index, 0);
            j += 1;
        }

        // Add a new Result to the Results array
        // "Jarle" here should be replaced by a user name of the person is logged in
        mResult = Result.getInstance();
        mResult.setDate(new Date());
        mResult.setNo1Strength(mTempResultArray.get(0));
        mResult.setNo2Strength(mTempResultArray.get(1));
        mResult.setNo3Strength(mTempResultArray.get(2));
        mResult.setName("Jarle");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.i(TAG, "onSavedInstanceSTate");

        outState.putInt(CURRENT_INDEX, mCurrentIndex);
        outState.putIntegerArrayList(STRENGTH_ARRAY, mStrenghtArray);

    }
}
