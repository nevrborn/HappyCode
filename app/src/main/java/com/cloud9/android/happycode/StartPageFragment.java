package com.cloud9.android.happycode;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

/**
 * Created by paulvancappelle on 22-11-16.
 */

public class StartPageFragment extends Fragment {

    Button mStartButton;
    Button mAboutButton;
    Button mTestHistoryButton;
    Button mAllCodes;
    Button mLogInButton;

    private TestResultList mTestResultList;

    User mUser;
    private DatabaseReference mDatabaseRef;

    /*
    * create new instance
    */
    public static Fragment newInstance() {
        return new StartPageFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTestResultList = TestResultList.get(getContext());

        if (User.get() != null) {
            getDataFromFirebase();
        }
    }

    @Nullable
    @Override
    /*
    * onCreateView
    */
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start_page, container, false);

        // set the references
        mStartButton = (Button) view.findViewById(R.id.button_start);
        mAboutButton = (Button) view.findViewById(R.id.button_about);
        mAllCodes = (Button) view.findViewById(R.id.button_all_codes);
        mTestHistoryButton = (Button) view.findViewById(R.id.button_test_history);
        mLogInButton = (Button) view.findViewById(R.id.buttonLogInStartPage);

        // set listeners
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStartButton.setBackgroundResource(R.drawable.button_pressed);
                Intent i = QuestionActivity.newIntent(getActivity());
                startActivity(i);
            }
        });

        mAboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAboutButton.setBackgroundResource(R.drawable.button_pressed);
                Intent i = AboutPageActivity.newIntent(getActivity());
                startActivity(i);
            }
        });

        mTestHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUser = User.get();
                if (mUser != null) {
                    if (mTestResultList.getSize() != 0) {
                        mTestHistoryButton.setBackgroundResource(R.drawable.button_pressed);
                        Intent i = TestHistoryActivity.newIntent(getActivity());
                        startActivity(i);
                    } else {
                        Toast.makeText(getActivity(), R.string.no_results_in_history, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), R.string.must_be_logged_in, Toast.LENGTH_SHORT).show();
                }

            }
        });
        mAllCodes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAllCodes.setBackgroundResource(R.drawable.button_pressed);
                Intent i = CodesActivity.newIntent(getActivity());
                startActivity(i);
            }
        });

        mLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUser = User.get();
                if (mUser != null) {
                    User.signOut();
                    Toast.makeText(getActivity(), R.string.sign_out_succesfull, Toast.LENGTH_SHORT).show();
                    updateLogInButton();
                } else {
                    Intent i = LogInActivity.newIntent(getActivity());
                    startActivity(i);
                }
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateLogInButton();
        User user = User.get();

        mStartButton.setBackgroundResource(R.drawable.button_start);
        mAboutButton.setBackgroundResource(R.drawable.button_start);
        mTestHistoryButton.setBackgroundResource(R.drawable.button_start);
        mAllCodes.setBackgroundResource(R.drawable.button_start);

        if (user != null) {
            mDatabaseRef = FirebaseDatabase.getInstance().getReference(user.getUid());
            writeExcitingTestsToFirebase();
            getDataFromFirebase();
        }
    }

    private void updateLogInButton() {
        mUser = User.get();
        if (mUser != null) {
            mLogInButton.setText(mUser.getEmail());
        } else {
            mLogInButton.setText(R.string.button_sign_in);
        }
    }

    public void getDataFromFirebase() {
        mTestResultList.clearResults();

        String userID = User.get().getUid();
        DatabaseReference mUserRef = FirebaseDatabase.getInstance().getReference("users").child(userID);
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

    private void writeToFirebase(TestResult testResult) {
        // set the database reference to the current user
        String userID = User.get().getUid();
        testResult.setUser(userID);
        testResult.setTester(userID);
        testResult.setWrittenToFirebase(true);

        DatabaseReference mUserRef = FirebaseDatabase.getInstance().getReference("users").child(userID);
        String key = mUserRef.child("results").push().getKey();
        mUserRef.child("results").child(key).setValue(testResult);
    }

    private void writeExcitingTestsToFirebase() {

        mTestResultList = TestResultList.get(getContext());

        if (mTestResultList.getSize() != 0 && User.get() != null) {
            int i = 0;

            while (i < mTestResultList.getSize()) {

                TestResult testresult = mTestResultList.getTestResultFromIndex(i);

                if (testresult.getWrittenToFirebase() == false) {
                    writeToFirebase(testresult);
                }

                i += 1;
            }

            mTestResultList.clearResults();
        }
    }
}
