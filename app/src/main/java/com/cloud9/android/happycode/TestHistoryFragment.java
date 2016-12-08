package com.cloud9.android.happycode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by paulvancappelle on 24-11-16.
 */

public class TestHistoryFragment extends Fragment {

    public RecyclerView mTestRecyclerView;
    public FirebaseRecyclerAdapter mTestResultsAdapter;
    private TestResultList mTestResultList;
    private StrengthList mStrengths = StrengthList.get(getContext());
    public static Map<String, Integer> arrayOfTotalResults = new HashMap<>();
    public static ArrayList<String> mTotalTop3StrengthKeys = new ArrayList<String>();

    private ImageView mTotalTop1StrengthImage;
    private ImageView mTotalTop2StrengthImage;
    private ImageView mTotalTop3StrengthImage;
    private TextView mTotalTop1StrengthText;
    private TextView mTotalTop2StrengthText;
    private TextView mTotalTop3StrengthText;
    private RelativeLayout mTop3CodeLayout;
    public static String mTestResultKey;

    private DatabaseReference mUserResultsRef;

    public static Callbacks mCallbacks;

    public interface Callbacks {
        void onTestResultSelected(String testresultKey);
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

        mTestResultList = TestResultList.get(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view_test_history, container, false);
        mTestRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_test_history);
        mTestRecyclerView.setHasFixedSize(false);
        mTestRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mTotalTop1StrengthImage = (ImageView) view.findViewById(R.id.list_item_icon_1_top);
        mTotalTop2StrengthImage = (ImageView) view.findViewById(R.id.list_item_icon_2_top);
        mTotalTop3StrengthImage = (ImageView) view.findViewById(R.id.list_item_icon_3_top);
        mTotalTop1StrengthText = (TextView) view.findViewById(R.id.textview_code_1_top);
        mTotalTop2StrengthText = (TextView) view.findViewById(R.id.textview_code_2_top);
        mTotalTop3StrengthText = (TextView) view.findViewById(R.id.textview_code_3_top);
        mTop3CodeLayout = (RelativeLayout) view.findViewById(R.id.totalTopCodes_relativelayout);

        updateModel();

        // set the recyclerview adapter
        mTestResultsAdapter = new FirebaseRecyclerAdapter<TestResult, TestResultHolder>(TestResult.class, R.layout.list_item_result_history, TestResultHolder.class, mUserResultsRef) {
            @Override
            public void populateViewHolder(TestResultHolder testResultHolder, final TestResult testResult, final int position) {
                StrengthList strengths = StrengthList.get(getActivity());
                Strength mNr1Strength = strengths.getStrengthFromKey(testResult.getNo1StrengthKey());
                Strength mNr2Strength = strengths.getStrengthFromKey(testResult.getNo2StrengthKey());
                Strength mNr3Strength = strengths.getStrengthFromKey(testResult.getNo3StrengthKey());

                // mTesterIcon = testResult.get...;  icon of tester needs to be add to TestResult class
                testResultHolder.mStrenghtIcon1.setImageResource(mNr1Strength.getIconID());
                testResultHolder.mStrenghtIcon2.setImageResource(mNr2Strength.getIconID());
                testResultHolder.mStrenghtIcon3.setImageResource(mNr3Strength.getIconID());
                testResultHolder.mDateTime.setText(testResult.getDateAndTime(testResult.getDate())); // set date format and add getTime to TestResult

                testResultHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String key = mTestResultsAdapter.getRef(position).getKey();
                        Intent i = ResultPageActivity.newIntent(getActivity(), key, false); // key is used because in firebase the id is always "QuestionID"
                        startActivity(i);
                    }
                });
            }
        };
        mTestRecyclerView.setAdapter(mTestResultsAdapter);


        if (mTestResultList.getSize() > 1) {
            setTotalTop3Strengths();
            mTop3CodeLayout.setVisibility(View.VISIBLE);
        } else {
            mTop3CodeLayout.setVisibility(View.GONE);
        }

        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mTestResultsAdapter.cleanup();
    }


    public void updateModel() {
        if (User.get() != null) {
            // set the database reference to the current user's results
            String uid = User.get().getUid();
            mUserResultsRef = FirebaseDatabase.getInstance().getReference("users").child(uid).child("results");
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mTestResultList.getSize() != 0) {
            setTotalTop3Strengths();
            mTop3CodeLayout.setVisibility(View.VISIBLE);
        } else {
            mTop3CodeLayout.setVisibility(View.GONE);
        }
    }

    private void makeTotalResultArray(TestResultList testResultList) {

        arrayOfTotalResults.clear();
        int sizeOfArray = testResultList.getSize();
        int i = 0;
        int totalPercentage = 0;
        ArrayList<String> listOfAllCodes = mStrengths.getAllKeys();


        while (i < listOfAllCodes.size()) {
            String key = listOfAllCodes.get(i);

            int j = 0;
            totalPercentage = 0;

            while (j < sizeOfArray) {
                totalPercentage = totalPercentage + testResultList.getTestResultFromIndex(j).getResultArray().get(key);
                j += 1;
            }

            arrayOfTotalResults.put(key, totalPercentage);

            i += 1;
        }

        findTop3Strengths(arrayOfTotalResults);

    }

    public static void findTop3Strengths(Map<String, Integer> resultArray) {
        Map<String, Integer> tempArray = new HashMap<>(resultArray);

        mTotalTop3StrengthKeys.clear();

        String keyOfMaxValue = "";

        int j = 0;

        while (j < 3) {

            int maxValue = Collections.max(tempArray.values());

            for (Map.Entry<String, Integer> entry : tempArray.entrySet()) {
                if (entry.getValue() == maxValue) {
                    keyOfMaxValue = entry.getKey();
                }
            }

            mTotalTop3StrengthKeys.add(keyOfMaxValue);
            tempArray.remove(keyOfMaxValue);
            j += 1;
        }

        tempArray.clear();
    }

    public void setTotalTop3Strengths() {

        makeTotalResultArray(mTestResultList);

        Strength mTop1Strength = mStrengths.getStrengthFromKey(mTotalTop3StrengthKeys.get(0));
        Strength mTop2Strength = mStrengths.getStrengthFromKey(mTotalTop3StrengthKeys.get(1));
        Strength mTop3Strength = mStrengths.getStrengthFromKey(mTotalTop3StrengthKeys.get(2));

        mTotalTop1StrengthImage.setImageResource(mTop1Strength.getIconID());
        mTotalTop2StrengthImage.setImageResource(mTop2Strength.getIconID());
        mTotalTop3StrengthImage.setImageResource(mTop3Strength.getIconID());

        mTotalTop1StrengthText.setText(mTop1Strength.getTitleID());
        mTotalTop2StrengthText.setText(mTop2Strength.getTitleID());
        mTotalTop3StrengthText.setText(mTop3Strength.getTitleID());
    }


    /*
    * inner class ViewHolder
    */
    public static class TestResultHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TestResult mTestResult;
        private ImageView mStrenghtIcon1;
        private ImageView mStrenghtIcon2;
        private ImageView mStrenghtIcon3;
        private TextView mDateTime;
        //private ImageView mTesterIcon;

        public TestResultHolder(View itemView) {
            super(itemView);
            mStrenghtIcon1 = (ImageView) itemView.findViewById(R.id.list_item_icon_1);
            mStrenghtIcon2 = (ImageView) itemView.findViewById(R.id.list_item_icon_2);
            mStrenghtIcon3 = (ImageView) itemView.findViewById(R.id.list_item_icon_3);
            mDateTime = (TextView) itemView.findViewById(R.id.list_item_date_time);

        }

        @Override
        public void onClick(View view) {
            mCallbacks.onTestResultSelected(mTestResultKey);
        }

        public void setResult(TestResult result) {
            mTestResult = result;
        }
    }


}
