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
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.ui.database.FirebaseRecyclerAdapter;


/**
 * Created by paulvancappelle on 24-11-16.
 */

public class TestHistoryFragment extends Fragment {

    private RecyclerView mTestRecyclerView;
    private FirebaseRecyclerAdapter mTestResultsAdapter;
    private TestResultList mTestResultList;

    private DatabaseReference mUserResultsRef;

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
        mTestRecyclerView.setHasFixedSize(false);
        mTestRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //RecyclerView recycler = (RecyclerView) findViewById(R.id.messages_recycler);
        //recycler.setHasFixedSize(true);
        //recycler.setLayoutManager(new LinearLayoutManager(this));

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
                        Toast.makeText(getActivity(), "Geklikt!", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getActivity(), position, Toast.LENGTH_SHORT).show();
                        Toast.makeText(getActivity(), (CharSequence) mTestResultsAdapter.getRef(position).getKey(), Toast.LENGTH_SHORT).show();
                        String testKey = mTestResultsAdapter.getRef(position).getKey();
                        Intent i = ResultPageActivity.newIntent(getActivity(), testResult);
                        startActivity(i);
                    }
                });
            }
        };
        mTestRecyclerView.setAdapter(mTestResultsAdapter);


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


    public void getDataFromFirebase() {
        mTestResultList.clearResults();

        mUserResultsRef.child("results").addValueEventListener(new ValueEventListener() {
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
    private static class TestResultHolder extends RecyclerView.ViewHolder {

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
            //mStrenghtIcon1.setAlpha(0.9f);
            //mStrenghtIcon2.setAlpha(0.9f);
            //mStrenghtIcon3.setAlpha(0.9f);

        }


        public void setResult(TestResult result) {
            mTestResult = result;
        }
    }


}
