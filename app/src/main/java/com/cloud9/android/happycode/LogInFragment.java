package com.cloud9.android.happycode;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LogInFragment extends Fragment {

    private static final String TAG = "HappyCode.LogInFragment";
    private static final String DIALOG_USER_NAME = "dialog_user_name";

    Button mLogInButton;
    Button mCreateAccountButton;
    EditText mMailField;
    EditText mPasswordField;

    private TestResultList mTestResultList;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference();

    String mUserName;
    String mPassword;
    String mMail;


    public LogInFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public static LogInFragment newInstance() {
        LogInFragment fragment = new LogInFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTestResultList = TestResultList.get(getContext());

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_log_in, container, false);


        // Fire up Firebase Authentication
        mAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };


        //Set the references
        mLogInButton = (Button) view.findViewById(R.id.buttonLogIn);
        mCreateAccountButton = (Button) view.findViewById(R.id.buttonNewAccount);

        mMailField = (EditText) view.findViewById(R.id.textField_mail);
        mPasswordField = (EditText) view.findViewById(R.id.textField_password);

        // set listeners
        mLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (fieldsAreFilled()) {

                    mMail = mMailField.getText().toString();
                    mPassword = mPasswordField.getText().toString();

                    // proceed LogIn
                    mAuth.signInWithEmailAndPassword(mMail, mPassword).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Log.w(TAG, "signInWithEmail:failed", task.getException());
                                Toast.makeText(getActivity(), R.string.auth_failed, Toast.LENGTH_SHORT).show();
                            } else if (task.isSuccessful()) {
                                Toast.makeText(getActivity(), R.string.sign_in_succesfull, Toast.LENGTH_SHORT).show();

                                // set user state to logged in
                                User.set();

                                // go to previous activity
                                getActivity().finish();

                                writeExcitingTestsToFirebase();
                                getDataFromFirebase();

                            }

                            // ...
                        }
                    });

                } else {
                    Toast.makeText(getActivity(), R.string.auth_not_all_fields_filled, Toast.LENGTH_SHORT);
                }

            }
        });

        mCreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (fieldsAreFilled()) {

                    FragmentManager fragmentManager = getFragmentManager();
                    LogInDialogUserName logInDialogUserName = new LogInDialogUserName();
                    logInDialogUserName.mMail = String.valueOf(mMailField.getText());
                    logInDialogUserName.mPassword = String.valueOf(mPasswordField.getText());

                    logInDialogUserName.show(fragmentManager, DIALOG_USER_NAME);

                } else {
                    Toast.makeText(getActivity(), R.string.auth_not_all_fields_filled, Toast.LENGTH_SHORT);
                }

            }
        });


        // set user abc@gmail.com ready to log in - JUST FOR TESTING!
        mMailField.setText("jarle.matland@gmail.com");
        mPasswordField.setText("ffffff");


        return view;
    }


    private boolean fieldsAreFilled() {
        return !String.valueOf(mMailField.getText()).equals("") && !String.valueOf(mPasswordField.getText()).equals("");
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }


    @Override
    public void onStop() {
        super.onStop();
        if (mAuthStateListener != null) {
            mAuth.removeAuthStateListener(mAuthStateListener);
        }
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


}
