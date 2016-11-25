package com.cloud9.android.happycode;

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
