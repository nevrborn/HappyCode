package com.cloud9.android.happycode;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by nevrborn on 08.12.2016.
 */

public class LogInDialogUserName extends DialogFragment {

    private static final String TAG = "HappyCode.LogInFragment";

    private Button mCreateUser;
    public String mMail;
    public String mPassword;
    private String mUserName;
    private EditText mUserNameField;

    private TestResultList mTestResultList;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference();

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_enter_user_name, null);

        mTestResultList = TestResultList.get(getContext());

        // Fire up Firebase Authentication
        mAuth = FirebaseAuth.getInstance();
        FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
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

        // set the references
        mCreateUser = (Button) v.findViewById(R.id.button_make_new_user);
        mUserNameField = (EditText) v.findViewById(R.id.editText_enter_user_name);

        mCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mUserName = mUserNameField.getText().toString();

                if (!mUserName.equals("")) {

                    mAuth.createUserWithEmailAndPassword(mMail, mPassword).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Toast.makeText(getActivity(), R.string.auth_failed, Toast.LENGTH_SHORT).show();
                            } else if (task.isSuccessful()) {
                                Toast.makeText(getActivity(), R.string.auth_user_created, Toast.LENGTH_SHORT).show();
                                User.set();
                                User user = User.get();
                                user.setName(mUserName);
                                mDatabaseRef.child("users").child(user.getUid()).setValue(User.get());

                                // go to previous activity
                                getActivity().finish();

                                writeExcitingTestsToFirebase();

                            }
                        }
                    });
                }

            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.enter_name_dialog_title)
                .setNegativeButton(android.R.string.cancel, null)
                .create();
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

                if (!testresult.getWrittenToFirebase()) {
                    writeToFirebase(testresult);
                }

                i += 1;
            }

            mTestResultList.clearResults();
        }
    }

}
