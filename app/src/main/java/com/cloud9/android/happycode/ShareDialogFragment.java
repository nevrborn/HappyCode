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

import java.util.zip.Inflater;

/**
 * Created by paulvancappelle on 01-12-16.
 */

public class ShareDialogFragment extends DialogFragment {

    Button mShareMail;
    Button mShareGeneral;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_fragment_share, null);

        // set the references
        mShareGeneral = (Button) v.findViewById(R.id.button_share_general);
        mShareMail = (Button) v.findViewById(R.id.button_share_mail);

        // set the listeners
        mShareGeneral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] mailRecipient = new String[1];
                mailRecipient[0] = User.get().getEmail();
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_mail_subject));
                i.putExtra(Intent.EXTRA_TEXT, getTextToShare());
                i.putExtra(Intent.EXTRA_EMAIL, mailRecipient);
                startActivity(i);
            }
        });
        mShareMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] mailRecipient = new String[1];
                mailRecipient[0] = User.get().getEmail();
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_mail_subject));
                i.putExtra(Intent.EXTRA_TEXT, getTextToShare());
                i.putExtra(Intent.EXTRA_EMAIL, mailRecipient);
                startActivity(i);
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.share_dialog_title)
                .setNegativeButton(android.R.string.cancel, null)
                .create();
    }

    private String getTextToShare() {

        String strength1 = getArguments().getString(ResultPageFragment.ARG_STRENGTH1);
        String strength2 = getArguments().getString(ResultPageFragment.ARG_STRENGTH2);
        String strength3 = getArguments().getString(ResultPageFragment.ARG_STRENGTH3);

        String result = getString(R.string.share_text_short, strength1, strength2, strength3);
        return result;
    }
}
