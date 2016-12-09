package com.cloud9.android.happycode;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by paulvancappelle on 24-11-16.
 */

public class AboutPageFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        TextView njr_link = (TextView) view.findViewById(R.id.textview_about_link_to_njr_website);
        TextView cloud9_link = (TextView) view.findViewById(R.id.textview_about_link_to_cloud9_website);

        njr_link.setMovementMethod(LinkMovementMethod.getInstance());
        cloud9_link.setMovementMethod(LinkMovementMethod.getInstance());

        return view;
    }
}
