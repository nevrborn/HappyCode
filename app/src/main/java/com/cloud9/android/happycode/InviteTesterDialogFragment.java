package com.cloud9.android.happycode;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by nevrborn on 06.12.2016.
 */

public class InviteTesterDialogFragment extends DialogFragment {

    private Button mInviteTester;
    private EditText mEditText;
    private TextView mEmailText;

    private User mUser;
    private Boolean wantsToSendEmail = false;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_fragment_invite_tester, null);

        mUser = User.get();

        // set the references
        mInviteTester = (Button) v.findViewById(R.id.button_invite);
        Button copyKey = (Button) v.findViewById(R.id.button_copy_key);
        mEditText = (EditText) v.findViewById(R.id.editText_invite_dialog);
        mEmailText = (TextView) v.findViewById(R.id.textview_invite_text_description);
        TextView copyText = (TextView) v.findViewById(R.id.textview_copy_key);

        mEmailText.setVisibility(View.GONE);
        mEditText.setVisibility(View.GONE);

        copyKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                copyKey(mUser.getUid());
                Toast.makeText(getActivity(), R.string.invite_dialog_key_is_copied, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, mUser.getUid());

                startActivity(i);
            }
        });


        mInviteTester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!wantsToSendEmail) {
                    mEmailText.setVisibility(View.VISIBLE);
                    mEditText.setVisibility(View.VISIBLE);
                    mInviteTester.setText(R.string.invite_dialog_invite_button_send);
                    wantsToSendEmail = true;
                } else {
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
                        Toast.makeText(getActivity(), R.string.share_no_mail_app, Toast.LENGTH_SHORT).show();
                    }
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

        return getString(R.string.invite_text, usedID);
    }

    private void copyKey(String userID) {
        ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("userKey", userID);
        clipboard.setPrimaryClip(clip);
    }

}
