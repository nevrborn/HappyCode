package com.cloud9.android.happycode;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by paulvancappelle on 22-11-16.
 */

public class StartPageFragment extends Fragment {

    Button mStartButton;
    Button mAboutButton;
    Button mTestHistoryButton;
    Button mAllCodes;
    Button mLogInButton;

    User mUser;
    private DatabaseReference mDatabaseRef;

    /*
    * create new instance
    */
    public static Fragment newInstance() {
        return new StartPageFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        mLogInButton = (Button) view.findViewById(R.id.buttonLogInStartPage);

        // set listeners
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mAboutButton.setText("");
//                mTestHistoryButton.setText("");
//                mAllCodes.setText("");
                animateCircles();

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
//                    Toast.makeText(getActivity(), R.string.history_not_availabe, Toast.LENGTH_SHORT).show();
                    mDatabaseRef = FirebaseDatabase.getInstance().getReference(User.get().getUid());
                    Toast.makeText(getActivity(), mDatabaseRef.getKey(), Toast.LENGTH_SHORT).show();
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
        mLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUser = User.get();
                if (mUser != null) {
                    mUser.signOut();
                    Toast.makeText(getActivity(), R.string.sign_out_succesfull, Toast.LENGTH_LONG).show();
                    updateLogInButton();
                } else {
                    Intent i = LogInActivity.newIntent(getActivity());
                    startActivity(i);
                }
            }
        });



        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        updateLogInButton();
    }

    private void updateLogInButton() {
        mUser = User.get();
        if (mUser != null) {
            mLogInButton.setText(mUser.getEmail());
        } else {
            mLogInButton.setText(R.string.button_sign_in);
        }
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

}
