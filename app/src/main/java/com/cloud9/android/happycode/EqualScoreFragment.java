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
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class EqualScoreFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String TAG = "EqualScoreFragment";
    private static final String CHECKED_KEYS = "the keys of the strenghts that are checked";
    private static final String CHECKED_BOXES = "number_of_checked_boxes";
    private static final int REQUEST_TO_IDENTIFY = 1;

    private int mColumnCount;
    private static int mEqualScoresInTopThree;
    private static TestResult mTestResult;
    private static ArrayList<String> mEqualScoreKeys;
    private static StrengthList sStrengthList;
    private int mCheckedBoxes = 0;
    private ArrayList<String> mCheckedStrenghts = new ArrayList<>();

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

        if (savedInstanceState != null) {
            mCheckedBoxes = savedInstanceState.getInt(CHECKED_BOXES);
            mCheckedStrenghts = (ArrayList<String>) savedInstanceState.getSerializable(CHECKED_KEYS);
        }

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_equalscore_list, container, false);

        // Shuffle the elements in the array
        Collections.shuffle(mEqualScoreKeys);

        TextView topTextView = (TextView) view.findViewById(R.id.textview_equal_score_top);
        Integer i = mEqualScoresInTopThree;
        topTextView.setText(getString(R.string.top_equal_score_text_view, i.toString()));
        mFinishButton = (Button) view.findViewById(R.id.button_equal_score_finish);
        if (mCheckedBoxes != mEqualScoresInTopThree) {
            mFinishButton.setAlpha(0.6f);
        }
        mFinishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCheckedBoxes == mEqualScoresInTopThree) {
                    // change the Result
                    switch (mEqualScoresInTopThree) {
                        case 1:
                            mTestResult.setNo3StrengthKey(mCheckedStrenghts.get(0));
                            break;
                        case 2:
                            mTestResult.setNo3StrengthKey(mCheckedStrenghts.get(1));
                            mTestResult.setNo2StrengthKey(mCheckedStrenghts.get(0));
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
                    startActivityForResult(i, REQUEST_TO_IDENTIFY);


                } else {
                    Toast.makeText(getActivity(), R.string.equal_score_uncorrect_nr_of_checkboxes, Toast.LENGTH_SHORT).show();
                }
            }
        });

        View recyclerView1 = view.findViewById(R.id.recyclerview_equal_score);

        // Set the adapter
        if (recyclerView1 instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) recyclerView1;
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSavedInstanceSTate");
        outState.putSerializable(CHECKED_KEYS, mCheckedStrenghts);
        outState.putInt(CHECKED_BOXES, mCheckedBoxes);
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
            if (mCheckedStrenghts.contains(strengthKey)) {
                holder.mCheckBox.setChecked(true);
            }

            holder.mCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.mCheckBox.isChecked()) {
                        mCheckedBoxes++;
                        mCheckedStrenghts.add(strengthKey);
                        if (mCheckedBoxes > mEqualScoresInTopThree) {
                            Toast.makeText(getActivity(), R.string.equal_score_too_many_checked, Toast.LENGTH_SHORT).show();
                            mFinishButton.setAlpha(0.6f);
                        } else if (mCheckedBoxes == mEqualScoresInTopThree) {
                            mFinishButton.setAlpha(1.0f);
                        }
                    } else {
                        mCheckedBoxes--;
                        mCheckedStrenghts.remove(strengthKey);
                        if (mCheckedBoxes == mEqualScoresInTopThree) {
                            mFinishButton.setAlpha(1.0f);
                        } else {
                            mFinishButton.setAlpha(0.6f);
                        }
                    }
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