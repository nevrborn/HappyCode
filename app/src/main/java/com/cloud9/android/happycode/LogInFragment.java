package com.cloud9.android.happycode;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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

import java.util.concurrent.Executor;


public class LogInFragment extends Fragment {

    private static final String TAG = "HappyCode.LogInFragment";

    Button mLogInButton;
    Button mCreateAccountButton;
    EditText mMailField;
    EditText mPasswordField;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    String mPassword;
    String mMail;


    public LogInFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    // TODO: Rename and change types and number of parameters
    public static LogInFragment newInstance() {
        LogInFragment fragment = new LogInFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

                    setMailAndPassword();

                    // proceed LogIn


                    // go to startPage
                    Intent i = StartPageActivity.newIntent(getActivity());
                    startActivity(i);

                } else {
                    Toast.makeText(getActivity(), R.string.auth_not_all_fields_filled, Toast.LENGTH_SHORT);
                }

            }
        });
        mCreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i(TAG, String.valueOf(mMailField.getText()));
                Log.i(TAG, String.valueOf(mPasswordField.getText()));

                if (fieldsAreFilled()) {

                    setMailAndPassword();

                    mAuth.createUserWithEmailAndPassword(mMail, mPassword).addOnCompleteListener((Executor) getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Toast.makeText(getActivity(), R.string.auth_failed, Toast.LENGTH_SHORT).show();
                            }

                            // ...
                        }
                    });

                    // go to startPage
                    Intent i = StartPageActivity.newIntent(getActivity());
                    startActivity(i);

                } else {
                    Toast.makeText(getActivity(), R.string.auth_not_all_fields_filled, Toast.LENGTH_SHORT);
                }

            }
        });

        return view;
    }

    private boolean fieldsAreFilled() {
        return String.valueOf(mMailField.getText()) != null && String.valueOf(mPasswordField.getText()) != null;
    }

    private void setMailAndPassword() {
        mMail = String.valueOf(mMailField.getText());
        Log.i(TAG, mMail);
        mPassword = String.valueOf(mPasswordField.getText());
        Log.i(TAG, mPassword);
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


}