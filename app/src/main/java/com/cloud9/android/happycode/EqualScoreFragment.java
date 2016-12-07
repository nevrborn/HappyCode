package com.cloud9.android.happycode;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class EqualScoreFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String TAG = "EqualScoreFragment";

    private int mColumnCount = 1;
    private static int mEqualScoresInTopThree;
    private static TestResult mTestResult;
    private static ArrayList<String> mEqualScoreKeys;
    private static StrengthList sStrengthList;
    private int mCheckedBoxes = 0;
    private ArrayList<String> mCheckedStrenghts = new ArrayList<>();

    private View mRecyclerView;
    private TextView mTopTextView;
    private Button mFinishButton;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EqualScoreFragment() {
    }

    public static EqualScoreFragment newInstance(int columnCount, TestResult testResult, ArrayList<String> equalScoreKeys, int equalScoresInTopThree) {
        mTestResult = testResult;
        mEqualScoreKeys = equalScoreKeys;
        mEqualScoresInTopThree = equalScoresInTopThree;

        EqualScoreFragment fragment = new EqualScoreFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_equalscore_list, container, false);

        mTopTextView = (TextView) view.findViewById(R.id.textview_equal_score_top);
        Integer i = new Integer(mEqualScoresInTopThree);
        mTopTextView.setText(getString(R.string.top_equal_score_text_view, i.toString()));
        mFinishButton = (Button) view.findViewById(R.id.button_equal_score_finish);
        mFinishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCheckedBoxes == mEqualScoresInTopThree) {
                    // change the Result
                    switch (mEqualScoresInTopThree) {
                        case 1:
                            mTestResult.setNo3StrengthKey(mCheckedStrenghts.get(2));
                            break;
                        case 2:
                            mTestResult.setNo3StrengthKey(mCheckedStrenghts.get(2));
                            mTestResult.setNo2StrengthKey(mCheckedStrenghts.get(1));
                            break;
                        case 3:
                            mTestResult.setNo3StrengthKey(mCheckedStrenghts.get(2));
                            mTestResult.setNo2StrengthKey(mCheckedStrenghts.get(1));
                            mTestResult.setNo1StrengthKey(mCheckedStrenghts.get(0));
                            break;
                        default:
                            Log.e(TAG, "Error. mEqualScoresInTopThree = " + mEqualScoresInTopThree);
                    }

                    // go to Result Page
                    String key = mTestResult.getID();
                    Intent i = ResultPageActivity.newIntent(getActivity(), key, true); // key is "QuestionID" here because not saved to FireBase yet
                    startActivity(i);

                } else {
                    Toast.makeText(getActivity(), R.string.equal_score_uncorrect_nr_of_checkboxes, Toast.LENGTH_SHORT).show();
                }
            }
        });

        mRecyclerView = view.findViewById(R.id.recyclerview_equal_score);

        // Set the adapter
        if (mRecyclerView instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) mRecyclerView;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new EqualScoreRecyclerViewAdapter(mEqualScoreKeys));
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        sStrengthList = StrengthList.get(getActivity());
    }



    //
    //
    //
    public class EqualScoreRecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {

        private final List<String> mValues;

        public EqualScoreRecyclerViewAdapter(ArrayList<String> equalScoreKeys) {
            mValues = equalScoreKeys;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_equal_scores, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            final String strengthKey = mValues.get(position);
            Strength strength = sStrengthList.getStrengthFromKey(strengthKey);
            holder.mCheckBox.setText(getText(strength.getQuestionID()));

            holder.mCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.mCheckBox.isChecked()) {
                        mCheckedBoxes++;
                        mCheckedStrenghts.add(strengthKey);
                    } else {
                        mCheckedBoxes--;
                        mCheckedStrenghts.remove(strengthKey);
                    }
                    Toast.makeText(getActivity(), "" + mCheckedBoxes, Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

    }


    //
    //
    //
    private class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final CheckBox mCheckBox;
        //public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mCheckBox = (CheckBox) view.findViewById(R.id.checkbox_equal_score);
        }

    }
}