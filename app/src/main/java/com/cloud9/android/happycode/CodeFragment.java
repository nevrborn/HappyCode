package com.cloud9.android.happycode;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import static android.R.layout.simple_list_item_1;

/**
 * Created by nevrborn on 24.11.2016.
 */

public class CodeFragment extends Fragment {

    private ListView mListView;
    private Strength[] mStrengths;
    private LayoutInflater mInflater;
    private Context mContext;

    /* Method to create fragment */
    public static CodeFragment newInstance() {
        return new CodeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getContext();
        mStrengths = QuestionFragment.newInstance().mStrengths;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_codes, container, false);

        mListView = (ListView) view.findViewById(R.id.listView_codes);

        CodeAdapter adapter = new CodeAdapter(this, mStrengths);
        mListView.setAdapter(adapter);
        return view;
    }

    private class CodeAdapter extends BaseAdapter {

        public CodeAdapter(CodeFragment codeFragment, Strength[] strengths) {
        }

        @Override
        public int getCount() {
            return mStrengths.length;
        }

        @Override
        public Object getItem(int i) {
            return mStrengths[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View rowView = mInflater.inflate(R.layout.list_item_code, viewGroup, false);

            ImageView mIconView = (ImageView) rowView.findViewById(R.id.list_item_icon);
            TextView mTitleView = (TextView) rowView.findViewById(R.id.list_item_title);
            TextView mDescriptionView = (TextView) rowView.findViewById(R.id.list_item_description);

            Strength mStrength = (Strength) getItem(i);

            mIconView.setImageResource(mStrength.getIconID());
            mTitleView.setText(mStrength.getTitleID());
            mDescriptionView.setText(mStrength.getDescriptionID());

            return rowView;
        }
    }
}
