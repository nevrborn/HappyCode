package com.cloud9.android.happycode;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by nevrborn on 06.12.2016.
 */

public class InviteTesterDialogFragment extends DialogFragment {

    private static final String DIALOG_INVITE = "dialog_invite";
    public static final String ARG_WILL_INVITE = "dialog_will_invite";

    Button mInviteTester;
    EditText mEditText;
    User mUser;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_fragment_invite_tester, null);

        mUser = User.get();

        // set the references
        mInviteTester = (Button) v.findViewById(R.id.button_invite);
        mEditText = (EditText) v.findViewById(R.id.editText_invite_dialog);

        mInviteTester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] mailRecipient = new String[1];
                mailRecipient[0] = mEditText.getText().toString();
                Intent i = new Intent(Intent.ACTION_SENDTO);
                i.setType("text/plain");
                i.setData(Uri.parse("mailto:")); // only email apps should handle this
                i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_subject));
                i.putExtra(Intent.EXTRA_TEXT, getTextToShare());
                i.putExtra(Intent.EXTRA_EMAIL, mailRecipient);
                if (i.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(i);
                } else {
                    Toast.makeText(getActivity(), R.string.share_no_mail_app, Toast.LENGTH_SHORT);
                }
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.invite_dialog_title)
                .setNegativeButton(android.R.string.cancel, null)
                .create();
    }

    private String getTextToShare() {
        String usedID = mUser.getUid();

        String result = getString(R.string.invite_text, usedID);
        return result;
    }

}
