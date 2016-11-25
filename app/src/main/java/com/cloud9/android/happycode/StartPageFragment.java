package com.cloud9.android.happycode;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by paulvancappelle on 22-11-16.
 */

public class StartPageFragment extends Fragment {

    Button mStartButton;
    Button mAboutButton;
    Button mTestHistoryButton;
    Button mAllCodes;
    ActionBarDrawerToggle mDrawerToggle;
    DrawerLayout mDrawerLayout;


    /*
    * create new instance
    */
    public static Fragment newInstance() {
        return new StartPageFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Nullable
    @Override
    /*
    * onCreateView
    */
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start_page, container, false);

        // set the references
        mStartButton = (Button) view.findViewById(R.id.button_start);
        mAboutButton = (Button) view.findViewById(R.id.button_about);
        mAllCodes = (Button) view.findViewById(R.id.button_all_codes);
        mTestHistoryButton = (Button) view.findViewById(R.id.button_test_history);
        mDrawerLayout = (DrawerLayout) view;

        // set listeners
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mAboutButton.setText("");
//                mTestHistoryButton.setText("");
//                mAllCodes.setText("");
//                animateCircles();

                Intent i = QuestionActivity.newIntent(getActivity());
                startActivity(i);
            }
        });

        mAboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = AboutPageActivity.newIntent(getActivity());
                startActivity(i);
            }
        });

        mTestHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TestResultList.get(getActivity()).getTestResultList().size() != 0) {
                    Intent i = TestHistoryActivity.newIntent(getActivity());
                    startActivity(i);
                } else {
                    Toast.makeText(getActivity(), "Sorry, je hebt nog geen historie.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mAllCodes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = CodesActivity.newIntent(getActivity());
                startActivity(i);
            }
        });


        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Log.d(TAG, "onDrawerClosed: " + getTitle());

                getActivity().invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);


        return view;
    }

    private void animateCircles() {

        int mStartButtonHeight = mStartButton.getHeight() / 3;
        int mStartButtonWidth = mStartButton.getWidth() / 3;

        float Yend = mStartButton.getTop() - mStartButtonHeight;
        float Xend = mStartButton.getX() + mStartButtonWidth;

        float aboutYstart = mAboutButton.getY();
        float aboutXstart = mAboutButton.getX();
        float testHistoryYstart = mAboutButton.getY();
        float allCodesYstart = mAllCodes.getY();
        float allCodesXstart = mAllCodes.getX();
        float testHistoryXstart = mAboutButton.getX();


        ObjectAnimator aboutYSize = ObjectAnimator.ofFloat(mAboutButton, "scaleY", 1f, 0);
        ObjectAnimator aboutXSize = ObjectAnimator.ofFloat(mAboutButton, "scaleX", 1f, 0);
        ObjectAnimator testHistoryYSize = ObjectAnimator.ofFloat(mTestHistoryButton, "scaleY", 1f, 0);
        ObjectAnimator testHistoryXSize = ObjectAnimator.ofFloat(mTestHistoryButton, "scaleX", 1f, 0);
        ObjectAnimator allCodesYSize = ObjectAnimator.ofFloat(mAllCodes, "scaleY", 1f, 0);
        ObjectAnimator allCodesXSize = ObjectAnimator.ofFloat(mAllCodes, "scaleX", 1f, 0);

        ObjectAnimator aboutYPos = ObjectAnimator.ofFloat(mAboutButton, "y", aboutYstart, Yend);
        ObjectAnimator aboutXPos = ObjectAnimator.ofFloat(mAboutButton, "x", aboutXstart, Xend);
        ObjectAnimator testHistoryYPos = ObjectAnimator.ofFloat(mTestHistoryButton, "y", testHistoryYstart, Yend);
        ObjectAnimator allCodesYPos = ObjectAnimator.ofFloat(mAllCodes, "y", allCodesYstart, Yend);
        ObjectAnimator allCodesXPos = ObjectAnimator.ofFloat(mAllCodes, "x", allCodesXstart, Xend);

        AnimatorSet aboutButton = new AnimatorSet();
        aboutButton
                .setDuration(1500)
                .play(aboutYPos)
                .with(aboutXPos)
                .with(aboutYSize)
                .with(testHistoryXSize)
                .with(testHistoryYSize)
                .with(testHistoryYPos)
                .with(allCodesYSize)
                .with(allCodesYPos)
                .with(allCodesXPos)
        ;

        aboutButton.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                Intent i = QuestionActivity.newIntent(getActivity());
                startActivity(i);
            }
        });

        aboutButton.start();
    }


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Pass the event to ActionBarDrawerToggle
//        // If it returns true, then it has handled
//        // the nav drawer indicator touch event
//        if (mDrawerToggle.onOptionsItemSelected(item)) {
//            return true;
//        }
//
//        // Handle your other action bar items...
//
//        return super.onOptionsItemSelected(item);
//    }
}
