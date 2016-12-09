package com.cloud9.android.happycode;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by paulvancappelle on 22-11-16.
 */

public class StartPageFragment extends Fragment {

    private static final String TAG = "StartPageFragment";
    private static final String DIALOG_INVITE = "dialog_invite";
    private static final String USER_ID_FROM_TESTER = "user_id_from_tester";

    Button mStartButton;
    Button mAboutButton;
    Button mTestHistoryButton;
    Button mAllCodes;
    Button mLogInButton;
    Button mInviteButton;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private TestResultList mTestResultList;
    private static StrengthList mStrengthList;
    public static Map<String, String> userKeyAndNameArray = new HashMap<>();

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
        mStrengthList = StrengthList.get(getContext());

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
        mInviteButton = (Button) view.findViewById(R.id.buttonOtherTester);

        if (mUser == null) {
            mInviteButton.setVisibility(View.GONE);
        }

        // set listeners
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    User.set();
                    Toast.makeText(getActivity(), "" + user.getUid(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                updateLogInButton();
            }
        };
        mAuth = FirebaseAuth.getInstance();
        mAuth.addAuthStateListener(mAuthStateListener);

        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStartButton.setBackgroundResource(R.drawable.button_pressed);

                FragmentManager fragmentManager = getFragmentManager();
                QuestionDialogFragment questionDialogFragment = new QuestionDialogFragment();
                questionDialogFragment.show(fragmentManager, USER_ID_FROM_TESTER);

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
                    Log.i(TAG, "There are " + mTestResultList.getSize() + " tests in total");
                    if (mTestResultList.getSize() != 0) {
                        mTestHistoryButton.setBackgroundResource(R.drawable.button_pressed);
                        Intent i = TestHistoryActivity.newIntent(getActivity());
                        startActivity(i);
                    } else {
                        Toast.makeText(getActivity(), R.string.history_not_availabe, Toast.LENGTH_SHORT).show();
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
                    mInviteButton.setVisibility(View.GONE);
                } else {
                    Intent i = LogInActivity.newIntent(getActivity());
                    startActivity(i);
                }
            }
        });

        mInviteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mUser == null) {
                    Toast.makeText(getActivity(), "You must be logged in to do this", Toast.LENGTH_SHORT).show();
                } else {
                    FragmentManager fragmentManager = getFragmentManager();
                    InviteTesterDialogFragment inviteTesterDialogFragment = new InviteTesterDialogFragment();
                    inviteTesterDialogFragment.show(fragmentManager, DIALOG_INVITE);
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
            getUserIDsFromFB();
            mInviteButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthStateListener != null) {
            mAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

    private void updateLogInButton() {
        mUser = User.get();
        if (mUser != null) {
            mLogInButton.setText(R.string.button_sign_out);
            mInviteButton.setVisibility(View.VISIBLE);
        } else {
            mLogInButton.setText(R.string.button_sign_in);
            mInviteButton.setVisibility(View.GONE);
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

    private void getUserIDsFromFB() {

        DatabaseReference mUserRef = FirebaseDatabase.getInstance().getReference("users");
        mUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String userKeyinFB = child.getKey();
                    String userName = child.child("name").getValue().toString();

                    userKeyAndNameArray.put(userKeyinFB, userName);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static String getNameFromKey(String userID) {
        int i = 0;
        String name = "";

        while (i < userKeyAndNameArray.size()) {

            if (userKeyAndNameArray.containsKey(userID)) {
                name = userKeyAndNameArray.get(userID);
            }
            i += 1;
        }
        return name;
    }

}
