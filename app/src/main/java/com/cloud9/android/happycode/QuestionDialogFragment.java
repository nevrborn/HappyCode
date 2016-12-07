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

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nevrborn on 06.12.2016.
 */

public class QuestionDialogFragment extends DialogFragment {

    public static final String TAG = "QuestionDialogFragment";
    private static final String USER_ID_FROM_TESTER = "user_id_from_tester";
    private static final String USER_NAME_FROM_TESTER = "user_name_from_tester";

    Button mTakeTestYourselfButton;
    Button mTakeTestForOthersButton;
    EditText mEditText;
    TextView mORText;
    TextView mEnterCodeText;

    Boolean wantsToEnterCode = true;
    private DatabaseReference mUserRef;
    public static Map<String, String> userKeyAndNameArray;

    User mUser;
    String mUserID;
    String mUserName;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_fragment_question, null);

        mUser = User.get();

        userKeyAndNameArray = StartPageFragment.userKeyAndNameArray;

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
                    mEditText.setText("3GlzlsmpHVaHXOEyN62BDp9tVHB3");
                    mEnterCodeText.setVisibility(View.VISIBLE);
                    mEnterCodeText.setText(R.string.question_dialog_enter_code);
                    mTakeTestForOthersButton.setText(R.string.question_dialog_button_test_for_another_start);
                    wantsToEnterCode = false;
                } else {
                    mUserID = mEditText.getText().toString();

                    if (validateUserID(mUserID)) {
                        Intent i = QuestionActivity.newIntent(getActivity());
                        i.putExtra(USER_ID_FROM_TESTER, mUserID);
                        i.putExtra(USER_NAME_FROM_TESTER, mUserName);
                        startActivity(i);
                        Toast.makeText(getActivity(), "You are taking the test for " + mUserName.toUpperCase(), Toast.LENGTH_SHORT).show();
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

    private Boolean validateUserID(String userID) {
        int i = 0;

        while (i < userKeyAndNameArray.size()) {

            if (userKeyAndNameArray.containsKey(userID)) {
                mUserName = userKeyAndNameArray.get(userID);
                return true;
            }
            i += 1;
        }
        return false;
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
