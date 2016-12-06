package com.cloud9.android.happycode;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by nevrborn on 06.12.2016.
 */

public class QuestionDialogFragment extends DialogFragment {

    public static final String TAG = "QuestionDialogFragment";
    private static final String USER_ID_FROM_TESTER = "user_id_from_tester";

    Button mTakeTestYourselfButton;
    Button mTakeTestForOthersButton;
    EditText mEditText;
    TextView mORText;
    TextView mEnterCodeText;

    Boolean wantsToEnterCode = true;
    private DatabaseReference mUserRef;
    Boolean checkedFBForCode = false;
    ArrayList<String> userIDsArray = new ArrayList<>();

    User mUser;
    String mUserID;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_fragment_question, null);

        mUser = User.get();

        // set the references
        mTakeTestYourselfButton = (Button) v.findViewById(R.id.button_take_test_for_me);
        mTakeTestForOthersButton = (Button) v.findViewById(R.id.button_take_test_for_another);
        mEditText = (EditText) v.findViewById(R.id.editText_question_dialog);
        mORText = (TextView) v.findViewById(R.id.textview_OR_text);
        mEnterCodeText = (TextView) v.findViewById(R.id.textview_question_text_description);


        if (mUser == null) {
            mEnterCodeText.setText(R.string.question_dialog_text_log_in);
            mEditText.setVisibility(View.GONE);
            mTakeTestForOthersButton.setVisibility(View.GONE);
        } else if (mUser != null) {
            getUserIDsFromFB();
            mEnterCodeText.setVisibility(View.GONE);
            mEditText.setVisibility(View.GONE);
            mTakeTestForOthersButton.setVisibility(View.VISIBLE);
        }

        mTakeTestYourselfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = QuestionActivity.newIntent(getActivity());

                if (mUser != null) {
                    i.putExtra(USER_ID_FROM_TESTER, mUser.getUid());
                } else {
                    i.putExtra(USER_ID_FROM_TESTER, "");
                }

                startActivity(i);

            }
        });

        mTakeTestForOthersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (wantsToEnterCode == true) {
                    mEditText.setVisibility(View.VISIBLE);
                    mEditText.setText("q7jA5CD0lgeiRRWjWKrigozZ93H2");
                    mEnterCodeText.setVisibility(View.VISIBLE);
                    mEnterCodeText.setText(R.string.question_dialog_enter_code);
                    mTakeTestForOthersButton.setText(R.string.question_dialog_button_test_for_another_start);
                    wantsToEnterCode = false;
                } else {
                    mUserID = mEditText.getText().toString();

                    if (validateUserID(mUserID) && checkedFBForCode == true) {
                        Intent i = QuestionActivity.newIntent(getActivity());
                        i.putExtra(USER_ID_FROM_TESTER, mUserID);
                        startActivity(i);
                    } else if (!validateUserID(mUserID)) {
                        Toast.makeText(getActivity(), "Wrong code used!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.question_dialog_title)
                .setNegativeButton(android.R.string.cancel, null)
                .create();
    }

    private void getUserIDsFromFB() {

        mUserRef = FirebaseDatabase.getInstance().getReference("users");
        mUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String userKeyinFB = child.getKey();

                    userIDsArray.add(userKeyinFB);
                }

                checkedFBForCode = true;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private Boolean validateUserID(String userID) {
        int i = 0;

        while (i < userIDsArray.size()) {
            if (userIDsArray.get(i).equals(userID)) {
                return true;
            }
            i += 1;
        }
        return false;
    }

}
