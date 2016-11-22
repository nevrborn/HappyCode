package com.cloud9.android.happycode;

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

    private Button mNextButton;
    private TextView mQuestionText;
    private TextView mPercentageText;
    private ImageView mImage;
    private SeekBar mSeekBar;
    private ArrayList<Integer> mStrenghtArray = new ArrayList<Integer>();
    private ArrayList<Integer> mTempResultArray = new ArrayList<Integer>();
    private ArrayList<Result> mResults = new ArrayList<Result>();

    // Adding all the questions and descriptions to an array - mQuestions
    private Question[] mQuestions = new Question[]{
            new Question("Leader", R.string.question_leader, R.string.question_leader_description),
            new Question("Storyteller", R.string.question_storyteller, R.string.question_storyteller_description),
            new Question("Challenger", R.string.question_challenger, R.string.question_challenger_description),
            new Question("Networker", R.string.question_networker, R.string.question_networker_description),
            new Question("Moodmaker", R.string.question_moodmaker, R.string.question_moodmaker_description),
            new Question("Organiser", R.string.question_organiser, R.string.question_organiser_description),
            new Question("Guardian", R.string.question_guardian, R.string.question_guardian_description),
            new Question("Planner", R.string.question_planner, R.string.question_planner_description),
            new Question("Hardworker", R.string.question_hardworker, R.string.question_hardworker_description),
            new Question("Administrator", R.string.question_administrator, R.string.question_administrator_description),
            new Question("Motivator", R.string.question_motivator, R.string.question_motivator_description),
            new Question("Connector", R.string.question_connector, R.string.question_connector_description),
            new Question("Unifier", R.string.question_unifier, R.string.question_unifier_description),
            new Question("Inspirer", R.string.question_inspirer, R.string.question_inspirer_description),
            new Question("Feeler", R.string.question_feeler, R.string.question_feeler_description),
            new Question("Strategist", R.string.question_strategist, R.string.question_strategist_description),
            new Question("Visionary", R.string.question_visionary, R.string.question_visionary_description),
            new Question("Pathfinder", R.string.question_pathfinder, R.string.question_pathfinder_description),
            new Question("Researcher", R.string.question_researcher, R.string.question_researcher_description),
            new Question("Thinker", R.string.question_thinker, R.string.question_thinker_description)
    };

    private int mCurrentIndex = 1;

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
        mQuestionText = (TextView) view.findViewById(R.id.textview_question_text);
        mPercentageText = (TextView) view.findViewById(R.id.textView_question_percentage);
        mNextButton = (Button) view.findViewById(R.id.button_question_next);
        mSeekBar = (SeekBar) view.findViewById(R.id.seekBar_question);

        // Setting up what happens when the next button is pressed - go to next question
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Increase index by one till all questions have been view, then setStrenghts and move to result screen
                if (mCurrentIndex < mQuestions.length - 1) {
                    Log.d(TAG, mQuestions[mCurrentIndex].getTitle() + " has " + mQuestions[mCurrentIndex].getPercentage() + "%");
                    mCurrentIndex += 1;
                    updateQuestion();
                    mSeekBar.setProgress(50);

                } else {
                    findStrengths();

                    String title1 = mQuestions[mResults.get(mResults.size()).getNo1Strength()].getTitle();
                    String title2 = mQuestions[mResults.get(mResults.size()).getNo2Strength()].getTitle();
                    String title3 = mQuestions[mResults.get(mResults.size()).getNo3Strength()].getTitle();

                    Log.d(TAG, "Your strengths are: " + title1 + ", " + title2 + " and " + title3);

                    mCurrentIndex = 0;
                }

            }
        });

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                setPercentage(i);
                mPercentageText.setText(i + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        updateQuestion();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /*Method to update the question visible in the screen*/
    private void updateQuestion() {
        int question = mQuestions[mCurrentIndex].getQuestion();
        mQuestionText.setText(question);
    }

    // Set the percentage for each strength
    private void setPercentage(int percentage) {
        mQuestions[mCurrentIndex].setPercentage(percentage);
    }

    public void findStrengths() {
        int i = 0;
        int j = 0;

        // Import all the strengths from the mQUestionsBank
        while (i < mQuestions.length - 1) {
            mStrenghtArray.add(mQuestions[i].getPercentage());
            i += 1;
        }

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

        mResults.add(new Result(new Date(), "Jarle", mTempResultArray.get(0), mTempResultArray.get(1), mTempResultArray.get(2)));
    }

}
