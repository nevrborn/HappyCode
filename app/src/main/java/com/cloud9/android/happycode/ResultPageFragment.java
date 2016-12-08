package com.cloud9.android.happycode;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by paulvancappelle on 23-11-16.
 */
public class ResultPageFragment extends Fragment {

    private static final String TAG = "ResultPageFragment";
    private static final String KEY_CURRENT_STRENGTH = "currentStrength";
    private static final String DIALOG_SHARE = "dialog_share";
    public static final String ARG_STRENGTH1 = "strength1_title";
    public static final String ARG_STRENGTH2 = "strength2_title";
    public static final String ARG_STRENGTH3 = "strength3_title";

    private static String mID;
    private Strength mNr1Strength;
    private Strength mNr2Strength;
    private Strength mNr3Strength;
    private int mCurrentStrength; // which result is currently selected (0, 1, ...)
    private TestResult mTestResult;
    private Map<String, Integer> mResultArray = new HashMap<>();
    private StrengthList mStrengths;
    private Boolean hasWrittenToFirebase = false;
    private static Boolean mIsFromQuestionPage = false;
    private Boolean mIsComingFromResultPage = false;
    private TestResultList mTestResultList;

    private ImageView mResultIcon1;
    private ImageView mResultIcon2;
    private ImageView mResultIcon3;
    private ImageView mLine;
    private TextView mStrenghtText;
    private TextView mStrengthTitle;
    private TextView mExplanation;
    private Button mToMenuButton;
    private Button mShareResultButton;
    private TextView mDateAndUser;

    private DatabaseReference mUserRef;

    /*
    * create new instance
    */
    public static Fragment newInstance(String testResultID, Boolean isFromQuestionPage) {
        mID = testResultID;
        mIsFromQuestionPage = isFromQuestionPage;
        return new ResultPageFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTestResultList = TestResultList.get(getContext());

        // If the intent is coming from QuestionPage, then look up
        if (mIsFromQuestionPage == true) {
            mTestResult = TestResultList.getTestResult("questionID");
            mIsComingFromResultPage = false;
        } else if (mIsFromQuestionPage == false) {
            mTestResult = TestResultList.getTestResult(mID);
            mIsComingFromResultPage = true;
        }

        mResultArray = mTestResult.getResultArray();
        mStrengths = StrengthList.get(getContext());


        String mNr1StrengthKey = mTestResult.getNo1StrengthKey();
        String mNr2StrengthKey = mTestResult.getNo2StrengthKey();
        String mNr3StrengthKey = mTestResult.getNo3StrengthKey();

        mNr1Strength = mStrengths.getStrengthFromKey(mNr1StrengthKey);
        mNr2Strength = mStrengths.getStrengthFromKey(mNr2StrengthKey);
        mNr3Strength = mStrengths.getStrengthFromKey(mNr3StrengthKey);

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
        mLine = (ImageView) view.findViewById(R.id.imageview_result_line);
        mStrenghtText = (TextView) view.findViewById(R.id.textview_result_strentgh_text);
        mStrengthTitle = (TextView) view.findViewById(R.id.textview_result_strenght_title);
        mToMenuButton = (Button) view.findViewById(R.id.button_result_to_menu);
        mShareResultButton = (Button) view.findViewById(R.id.button_share_result);
        mExplanation = (TextView) view.findViewById(R.id.textview_result_explanation);
        mDateAndUser = (TextView) view.findViewById(R.id.textview_result_date_and_user);

        // set images for the results
        mResultIcon1.setImageResource(mNr1Strength.getIconID());
        mResultIcon2.setImageResource(mNr2Strength.getIconID());
        mResultIcon3.setImageResource(mNr3Strength.getIconID());

        String userName = StartPageFragment.getNameFromKey(mTestResult.getUser());
        if (!userName.equals("")) {
            String date;

            if (mTestResult.getWrittenToFirebase() == false) {
                date = mTestResult.getDateAndTime(System.currentTimeMillis() / 1000L);
            } else {
                date = mTestResult.getDateAndTime(mTestResult.getDate());
            }
            mDateAndUser.setText(getString(R.string.date_and_user, date, userName));
            mDateAndUser.setVisibility(View.VISIBLE);
        } else {
            mDateAndUser.setVisibility(View.GONE);
        }

        // save the test result to the firebase
        if (hasWrittenToFirebase == false && User.get() != null) {
            writeToFirebase(mTestResult);
            hasWrittenToFirebase = true;
        }


        // set listeners
        mResultIcon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentStrength = 0;
                setPickedResult();
                mExplanation.setVisibility(View.GONE);
            }
        });

        mResultIcon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentStrength = 1;
                setPickedResult();
                mExplanation.setVisibility(View.GONE);
            }
        });

        mResultIcon3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentStrength = 2;
                setPickedResult();
                mExplanation.setVisibility(View.GONE);
            }
        });

        mToMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // save the test result to the firebase
                if (hasWrittenToFirebase == false && User.get() != null && mIsComingFromResultPage == false) {
                    writeToFirebase(mTestResult);
                    hasWrittenToFirebase = true;
                } else {
                    mTestResult.setDate(System.currentTimeMillis() / 1000L);
                    mTestResult.setTester("");
                }

                Intent i = StartPageActivity.newIntent(getActivity());
                startActivity(i);

                if (User.get() != null) {
                    mTestResultList.deleteTestResult(mTestResult);
                }
            }
        });

        mShareResultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                ShareDialogFragment shareDialogFragment = new ShareDialogFragment();

                Bundle bundle = new Bundle();
                bundle.putString(ARG_STRENGTH1, getString(mNr1Strength.getTitleID()));
                bundle.putString(ARG_STRENGTH2, getString(mNr2Strength.getTitleID()));
                bundle.putString(ARG_STRENGTH3, getString(mNr3Strength.getTitleID()));

                shareDialogFragment.setArguments(bundle);
                shareDialogFragment.show(fragmentManager, DIALOG_SHARE);
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
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mLine.getLayoutParams();
        switch (mCurrentStrength) {
            case 0:
                mStrengthTitle.setText(mNr1Strength.getTitleID());
                mStrenghtText.setText(mNr1Strength.getDescriptionID());
                mResultIcon1.setAlpha(1.0f);
                mResultIcon2.setAlpha(0.8f);
                mResultIcon3.setAlpha(0.8f);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    layoutParams.removeRule(RelativeLayout.CENTER_HORIZONTAL);
                    layoutParams.removeRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                }
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                mLine.setLayoutParams(layoutParams);
                return;
            case 1:
                mStrengthTitle.setText(mNr2Strength.getTitleID());
                mStrenghtText.setText(mNr2Strength.getDescriptionID());
                mResultIcon1.setAlpha(0.8f);
                mResultIcon2.setAlpha(1.0f);
                mResultIcon3.setAlpha(0.8f);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    layoutParams.removeRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    layoutParams.removeRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                }
                layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                mLine.setLayoutParams(layoutParams);
                return;
            case 2:
                mStrengthTitle.setText(mNr3Strength.getTitleID());
                mStrenghtText.setText(mNr3Strength.getDescriptionID());
                mResultIcon1.setAlpha(0.8f);
                mResultIcon2.setAlpha(0.8f);
                mResultIcon3.setAlpha(1.0f);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    layoutParams.removeRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    layoutParams.removeRule(RelativeLayout.CENTER_HORIZONTAL);
                }
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                mLine.setLayoutParams(layoutParams);
                return;
            default:
                Log.e(TAG, "Picked result " + mCurrentStrength + " does not exist");
        }
    }

    private void writeToFirebase(TestResult testResult) {
        String userID = mTestResult.getUser();
        String testerID = User.get().getUid();

        mTestResult.setDate(System.currentTimeMillis() / 1000L);
        mTestResult.setUser(userID);
        mTestResult.setTester(testerID);
        mTestResult.setWrittenToFirebase(true);

        if (testerID.equals(userID)) {
            mUserRef = FirebaseDatabase.getInstance().getReference("users").child(testerID);
        } else {
            mUserRef = FirebaseDatabase.getInstance().getReference("users").child(userID);
        }

        String key = mUserRef.child("results").push().getKey();
        mUserRef.child("results").child(key).setValue(testResult);
    }


}
