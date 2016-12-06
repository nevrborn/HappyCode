package com.cloud9.android.happycode;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;
import java.util.List;

/**
 * Created by paulvancappelle on 24-11-16.
 */

public class TestHistoryFragment extends Fragment {

    private RecyclerView mTestRecyclerView;
    private TestResultsAdapter mTestResultsAdapter;
    private TestResultList mTestResultList;

    private DatabaseReference mUserRef;

    private Callbacks mCallbacks;

    public interface Callbacks {
        void onTestResultSelected(TestResult testresult);
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
        mTestRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    public void updateUI() {

        if (User.get() != null) {
            // set the database reference to the current user
            String uid = User.get().getUid();
            mUserRef = FirebaseDatabase.getInstance().getReference("users").child(uid);
            getDataFromFirebase();
        }

        mTestResultsAdapter = new TestResultsAdapter(TestResultList.getTestResultList());
        mTestRecyclerView.setAdapter(mTestResultsAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void getDataFromFirebase() {

        mTestResultList.clearResults();

        mUserRef.child("results").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    TestResult testResult = child.getValue(TestResult.class);
                    mTestResultList.addTestresult(testResult, child.getKey());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /*
    * inner class ViewHolder
    */
    private class TestResultHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TestResult mTestResult;
        private ImageView mTesterIcon;
        private ImageView mStrenghtIcon1;
        private ImageView mStrenghtIcon2;
        private ImageView mStrenghtIcon3;
        private TextView mDateTime;

        public TestResultHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            //mTesterIcon = (ImageView) itemView.findViewById(R.id.list_item_tester_icon);
            mStrenghtIcon1 = (ImageView) itemView.findViewById(R.id.list_item_icon_1);
            mStrenghtIcon2 = (ImageView) itemView.findViewById(R.id.list_item_icon_2);
            mStrenghtIcon3 = (ImageView) itemView.findViewById(R.id.list_item_icon_3);
            mDateTime = (TextView) itemView.findViewById(R.id.list_item_date_time);
            mStrenghtIcon1.setAlpha(0.9f);
            mStrenghtIcon2.setAlpha(0.9f);
            mStrenghtIcon3.setAlpha(0.9f);
        }

        @Override
        public void onClick(View view) {
            // Go to TestResult page
            mStrenghtIcon1.setAlpha(1f);
            mStrenghtIcon2.setAlpha(1f);
            mStrenghtIcon3.setAlpha(1f);
            Intent i = ResultPageActivity.newIntent(getActivity(), mTestResult.getID(), false);
            startActivity(i);
        }

        public void setResult(TestResult result) {
            mTestResult = result;
        }
    }

    /*
    * inner class Adapter
    */
    private class TestResultsAdapter extends RecyclerView.Adapter<TestResultHolder> {

        private List<TestResult> mTestResultList;
        private StrengthList mStrengths = StrengthList.get(getContext());


        public TestResultsAdapter(List<TestResult> testResultList) {
            mTestResultList = testResultList;
        }

        @Override
        public TestResultHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_result_history, parent, false);
            return new TestResultHolder(view);
        }

        @Override
        public void onBindViewHolder(TestResultHolder holder, int position) {
            TestResult testResult = mTestResultList.get(position);

            Strength mNr1Strength = mStrengths.getStrengthFromKey(testResult.getNo1StrengthKey());
            Strength mNr2Strength = mStrengths.getStrengthFromKey(testResult.getNo2StrengthKey());
            Strength mNr3Strength = mStrengths.getStrengthFromKey(testResult.getNo3StrengthKey());

            // mTesterIcon = testResult.get...;  icon of tester needs to be add to TestResult class
            holder.mStrenghtIcon1.setImageResource(mNr1Strength.getIconID());
            holder.mStrenghtIcon2.setImageResource(mNr2Strength.getIconID());
            holder.mStrenghtIcon3.setImageResource(mNr3Strength.getIconID());
            holder.mDateTime.setText(testResult.getDateAndTime(testResult.getDate())); // set date format and add getTime to TestResult

            holder.mStrenghtIcon1.setAlpha(0.9f);
            holder.mStrenghtIcon2.setAlpha(0.9f);
            holder.mStrenghtIcon3.setAlpha(0.9f);

            holder.setResult(testResult);
        }

        @Override
        public int getItemCount() {
            return mTestResultList.size();
        }

    }

}
