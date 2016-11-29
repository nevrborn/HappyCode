package com.cloud9.android.happycode;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by nevrborn on 24.11.2016.
 */

public class CodeFragment extends Fragment {

    private ListView mListView;
    private StrengthList mStrengths;
    private LayoutInflater mInflater;
    private Context mContext;

    /* Method to create fragment */
    public static CodeFragment newInstance() {
        return new CodeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mStrengths = StrengthList.get(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_codes, container, false);

        mListView = (ListView) view.findViewById(R.id.listView_codes);

        CodeAdapter adapter = new CodeAdapter(this.getContext(), mStrengths);
        mListView.setAdapter(adapter);
        return view;
    }

    private class CodeAdapter extends BaseAdapter {

        public CodeAdapter(Context codeFragment, StrengthList strengths) {
            mContext = codeFragment;
            mStrengths = strengths;
            mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mStrengths.getSize();
        }

        @Override
        public Object getItem(int i) {
            return mStrengths.getStrengthFromIndex(i);
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
