package com.cloud9.android.happycode;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by paulvancappelle on 24-11-16.
 */

public class TestHistoryFragment extends Fragment {

    private RecyclerView mTestRecyclerView;
    private TestResultsAdapter mTestResultsAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view_test_history, container, false);
        mTestRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_test_history);
        mTestRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI() {
        TestResultList testResultList = TestResultList.get(getActivity());
        mTestResultsAdapter = new TestResultsAdapter(testResultList.getTestResultList());
        mTestRecyclerView.setAdapter(mTestResultsAdapter);
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
            mTesterIcon = (ImageView) itemView.findViewById(R.id.list_item_tester_icon);
            mStrenghtIcon1 = (ImageView) itemView.findViewById(R.id.list_item_icon_1);
            mStrenghtIcon2 = (ImageView) itemView.findViewById(R.id.list_item_icon_2);
            mStrenghtIcon3 = (ImageView) itemView.findViewById(R.id.list_item_icon_3);
            mDateTime = (TextView) itemView.findViewById(R.id.list_item_date_time);
        }

        @Override
        public void onClick(View view) {
            // Go to TestResult page
            Intent i = ResultPageActivity.newIntent(getActivity(), mTestResult.getID());
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
        private Strength[] mStrengths = QuestionFragment.newInstance().mStrengths;

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

            // mTesterIcon = testResult.set...;  icon of tester needs to be add to TestResult class
            holder.mStrenghtIcon1.setImageResource(mStrengths[testResult.getNo1Strength()].getIconID());
            holder.mStrenghtIcon2.setImageResource(mStrengths[testResult.getNo2Strength()].getIconID());
            holder.mStrenghtIcon3.setImageResource(mStrengths[testResult.getNo3Strength()].getIconID());
            //holder.mDateTime.setText(testResult.getDate().toString() + " uu:mm"); // set date format and add getTime to TestResult
            holder.mDateTime.setText(testResult.getDateTime());

            holder.setResult(testResult);
        }

        @Override
        public int getItemCount() {
            return mTestResultList.size();
        }
    }

}
