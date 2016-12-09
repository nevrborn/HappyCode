package com.cloud9.android.happycode;

import android.app.Activity;
import android.app.AlertDialog;
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
    private static Boolean mIsFromQuestionPage = false;
    private TestResultList mTestResultList;
    public static String mTestResultKey;
    private Callbacks mCallbacks;
    public static Boolean isTablet = false;

    Map<String, Integer> mResultArray;

    private ImageView mResultIcon1;
    private ImageView mResultIcon2;
    private ImageView mResultIcon3;
    private ImageView mLine;
    private TextView mStrenghtText;
    private TextView mStrengthTitle;
    private TextView mExplanation;

    /*
    * create new instance
    */
    public static Fragment newInstance(String testResultID, Boolean isFromQuestionPage) {
        mID = testResultID;
        mIsFromQuestionPage = isFromQuestionPage;
        return new ResultPageFragment();
    }

    public interface Callbacks {
        void onTestResultDeleted();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTestResultList = TestResultList.get(getContext());

        // If the intent is coming from QuestionPage, then look up
        if (mIsFromQuestionPage) {
            mTestResult = TestResultList.getTestResult("questionID");
        } else if (!mIsFromQuestionPage) {
            mTestResult = TestResultList.getTestResult(mID);
        }

        mResultArray = mTestResult.getResultArray();
        StrengthList strengths = StrengthList.get(getContext());


        String mNr1StrengthKey = mTestResult.getNo1StrengthKey();
        String mNr2StrengthKey = mTestResult.getNo2StrengthKey();
        String mNr3StrengthKey = mTestResult.getNo3StrengthKey();

        mNr1Strength = strengths.getStrengthFromKey(mNr1StrengthKey);
        mNr2Strength = strengths.getStrengthFromKey(mNr2StrengthKey);
        mNr3Strength = strengths.getStrengthFromKey(mNr3StrengthKey);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        // set the references
        mResultIcon1 = (ImageView) view.findViewById(R.id.imageview_result_one);
        mResultIcon2 = (ImageView) view.findViewById(R.id.imageview_result_two);
        mResultIcon3 = (ImageView) view.findViewById(R.id.imageview_result_three);
        mLine = (ImageView) view.findViewById(R.id.imageview_result_line);
        mStrenghtText = (TextView) view.findViewById(R.id.textview_result_strentgh_text);
        mStrengthTitle = (TextView) view.findViewById(R.id.textview_result_strenght_title);
        Button toMenuButton = (Button) view.findViewById(R.id.button_result_to_menu);
        Button shareResultButton = (Button) view.findViewById(R.id.button_share_result);
        mExplanation = (TextView) view.findViewById(R.id.textview_result_explanation);
        TextView dateAndUser = (TextView) view.findViewById(R.id.textview_result_date_and_user);
        Button deleteButton = (Button) view.findViewById(R.id.delete_test_result);

        // set images for the results
        mResultIcon1.setImageResource(mNr1Strength.getIconID());
        mResultIcon2.setImageResource(mNr2Strength.getIconID());
        mResultIcon3.setImageResource(mNr3Strength.getIconID());

        if (isTablet) {
            TextView strength_percentage_1 = (TextView) view.findViewById(R.id.textview_strength_summary_percentage_1);
            TextView strength_percentage_2 = (TextView) view.findViewById(R.id.textview_strength_summary_percentage_2);
            TextView strength_percentage_3 = (TextView) view.findViewById(R.id.textview_strength_summary_percentage_3);
            TextView strength_percentage_4 = (TextView) view.findViewById(R.id.textview_strength_summary_percentage_4);
            TextView strength_percentage_5 = (TextView) view.findViewById(R.id.textview_strength_summary_percentage_5);
            TextView strength_percentage_6 = (TextView) view.findViewById(R.id.textview_strength_summary_percentage_6);
            TextView strength_percentage_7 = (TextView) view.findViewById(R.id.textview_strength_summary_percentage_7);
            TextView strength_percentage_8 = (TextView) view.findViewById(R.id.textview_strength_summary_percentage_8);
            TextView strength_percentage_9 = (TextView) view.findViewById(R.id.textview_strength_summary_percentage_9);
            TextView strength_percentage_10 = (TextView) view.findViewById(R.id.textview_strength_summary_percentage_10);
            TextView strength_percentage_11 = (TextView) view.findViewById(R.id.textview_strength_summary_percentage_11);
            TextView strength_percentage_12 = (TextView) view.findViewById(R.id.textview_strength_summary_percentage_12);
            TextView strength_percentage_13 = (TextView) view.findViewById(R.id.textview_strength_summary_percentage_13);
            TextView strength_percentage_14 = (TextView) view.findViewById(R.id.textview_strength_summary_percentage_14);
            TextView strength_percentage_15 = (TextView) view.findViewById(R.id.textview_strength_summary_percentage_15);
            TextView strength_percentage_16 = (TextView) view.findViewById(R.id.textview_strength_summary_percentage_16);
            TextView strength_percentage_17 = (TextView) view.findViewById(R.id.textview_strength_summary_percentage_17);
            TextView strength_percentage_18 = (TextView) view.findViewById(R.id.textview_strength_summary_percentage_18);
            TextView strength_percentage_19 = (TextView) view.findViewById(R.id.textview_strength_summary_percentage_19);
            TextView strength_percentage_20 = (TextView) view.findViewById(R.id.textview_strength_summary_percentage_20);

            strength_percentage_1.setText(mResultArray.get("100").toString() + "%");
            strength_percentage_2.setText(mResultArray.get("200").toString() + "%");
            strength_percentage_3.setText(mResultArray.get("300").toString() + "%");
            strength_percentage_4.setText(mResultArray.get("400").toString() + "%");
            strength_percentage_5.setText(mResultArray.get("500").toString() + "%");
            strength_percentage_6.setText(mResultArray.get("600").toString() + "%");
            strength_percentage_7.setText(mResultArray.get("700").toString() + "%");
            strength_percentage_8.setText(mResultArray.get("800").toString() + "%");
            strength_percentage_9.setText(mResultArray.get("900").toString() + "%");
            strength_percentage_10.setText(mResultArray.get("1000").toString() + "%");
            strength_percentage_11.setText(mResultArray.get("1100").toString() + "%");
            strength_percentage_12.setText(mResultArray.get("1200").toString() + "%");
            strength_percentage_13.setText(mResultArray.get("1300").toString() + "%");
            strength_percentage_14.setText(mResultArray.get("1400").toString() + "%");
            strength_percentage_15.setText(mResultArray.get("1500").toString() + "%");
            strength_percentage_16.setText(mResultArray.get("1600").toString() + "%");
            strength_percentage_17.setText(mResultArray.get("1700").toString() + "%");
            strength_percentage_18.setText(mResultArray.get("1800").toString() + "%");
            strength_percentage_19.setText(mResultArray.get("1900").toString() + "%");
            strength_percentage_20.setText(mResultArray.get("2000").toString() + "%");
        }

        String userName = StartPageFragment.getNameFromKey(mTestResult.getUser());
        if (!userName.equals("")) {
            String date;

            if (!mTestResult.getWrittenToFirebase()) {
                date = mTestResult.getDateAndTime(System.currentTimeMillis() / 1000L);
            } else {
                date = mTestResult.getDateAndTime(mTestResult.getDate());
            }
            dateAndUser.setText(getString(R.string.date_and_user, date, userName));
            dateAndUser.setVisibility(View.VISIBLE);
        } else {
            dateAndUser.setVisibility(View.GONE);
        }

        // save the test result to the firebase
        if (!mTestResult.getWrittenToFirebase() && User.get() != null) {
            writeToFirebase(mTestResult);
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

        toMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // save the test result to the firebase
                if (!mTestResult.getWrittenToFirebase() && User.get() != null) {
                    writeToFirebase(mTestResult);
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

        shareResultButton.setOnClickListener(new View.OnClickListener() {
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

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(getActivity())
                        .setView(view)
                        .setTitle(R.string.delete_test_result)
                        .setNegativeButton(android.R.string.cancel, null)
                        .setPositiveButton(android.R.string.ok, null)
                        .create();

                deleteFromFirebase();
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

        DatabaseReference userRef;
        if (testerID.equals(userID)) {
            userRef = FirebaseDatabase.getInstance().getReference("users").child(testerID);
        } else {
            userRef = FirebaseDatabase.getInstance().getReference("users").child(userID);
        }

        String key = userRef.child("results").push().getKey();
        userRef.child("results").child(key).setValue(testResult);
    }

    private void deleteFromFirebase() {
        String userID = User.get().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child(userID).child("results");
        ref.child(mTestResultKey).removeValue();
        getActivity().finish();
        mCallbacks.onTestResultDeleted();
    }


}
