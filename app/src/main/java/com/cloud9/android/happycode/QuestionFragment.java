package com.cloud9.android.happycode;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by nevrborn on 22.11.2016.
 */

public class QuestionFragment extends Fragment {

    private Button mNextButton;
    private TextView mQuestionText;
    private ImageView mImage;
    private SeekBar mSeekBar;

    // Adding all the questions and descriptions to an array - mQuestions
    private Question[] mQuestions = new Question[]{
            new Question(R.string.question_leader, R.string.question_leader_description),
            new Question(R.string.question_storyteller, R.string.question_storyteller_description),
            new Question(R.string.question_challenger, R.string.question_challenger_description),
            new Question(R.string.question_networker, R.string.question_networker_description),
            new Question(R.string.question_moodmaker, R.string.question_moodmaker_description),
            new Question(R.string.question_organiser, R.string.question_organiser_description),
            new Question(R.string.question_guardian, R.string.question_guardian_description),
            new Question(R.string.question_planner, R.string.question_planner_description),
            new Question(R.string.question_hardworker, R.string.question_hardworker_description),
            new Question(R.string.question_administrator, R.string.question_administrator_description),
            new Question(R.string.question_motivator, R.string.question_motivator_description),
            new Question(R.string.question_connector, R.string.question_connector_description),
            new Question(R.string.question_unifier, R.string.question_unifier_description),
            new Question(R.string.question_inspirer, R.string.question_inspirer_description),
            new Question(R.string.question_feeler, R.string.question_feeler_description),
            new Question(R.string.question_strategist, R.string.question_strategist_description),
            new Question(R.string.question_visionary, R.string.question_visionary_description),
            new Question(R.string.question_pathfinder, R.string.question_pathfinder_description),
            new Question(R.string.question_researcher, R.string.question_researcher_description),
            new Question(R.string.question_thinker, R.string.question_thinker_description)
    };

    private int mCurrentIndex = 0;

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
        mNextButton = (Button) view.findViewById(R.id.button_question_next);
        mSeekBar = (SeekBar) view.findViewById(R.id.seekBar_question);

        // Setting up what happens when the next button is pressed - go to next question
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex += 1;
                updateQuestion();
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentIndex = 0;
        updateQuestion();
    }

    /*Method to update the question visible in the screen*/
    private void updateQuestion() {
        int question = mQuestions[mCurrentIndex].getQuestion();
        mQuestionText.setText(question);
    }
}
