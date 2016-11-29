package com.cloud9.android.happycode;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class StartPageActivity extends SingleFragmentActivity {

    /*
    * create Intent to start this activity
    */
    public static Intent newIntent(Context context) {
        return new Intent(context, StartPageActivity.class);
    }


    /*
    * Method to create fragment from Activity
    */
    @Override
    protected Fragment createFragment() {
        return StartPageFragment.newInstance();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrengthList mStrengthList = StrengthList.get(getApplicationContext());

        mStrengthList.addStrength(new Strength("100", R.string.strength_leader_title, R.string.strength_leader, R.string.strength_leader_description, R.mipmap.strength_leader, R.mipmap.icon_leader));
        mStrengthList.addStrength(new Strength("200", R.string.strength_leader_title, R.string.strength_leader, R.string.strength_leader_description, R.mipmap.strength_leader, R.mipmap.icon_leader));
        mStrengthList.addStrength(new Strength("300", R.string.strength_storyteller_title, R.string.strength_storyteller, R.string.strength_storyteller_description, R.mipmap.strength_storyteller, R.mipmap.icon_storyteller));
        mStrengthList.addStrength(new Strength("400", R.string.strength_challenger_title, R.string.strength_challenger, R.string.strength_challenger_description, R.mipmap.strength_challenger, R.mipmap.icon_challenger));
        mStrengthList.addStrength(new Strength("500", R.string.strength_networker_title, R.string.strength_networker, R.string.strength_networker_description, R.mipmap.strength_networker, R.mipmap.icon_networker));
        mStrengthList.addStrength(new Strength("600", R.string.strength_moodmaker_title, R.string.strength_moodmaker, R.string.strength_moodmaker_description, R.mipmap.strength_moodmaker, R.mipmap.icon_networker));
        mStrengthList.addStrength(new Strength("700", R.string.strength_organiser_title, R.string.strength_organiser, R.string.strength_organiser_description, R.mipmap.strength_organiser, R.mipmap.icon_organiser));
        mStrengthList.addStrength(new Strength("800", R.string.strength_guardian_title, R.string.strength_guardian, R.string.strength_guardian_description, R.mipmap.strength_guardian, R.mipmap.icon_guardian));
        mStrengthList.addStrength(new Strength("900", R.string.strength_planner_title, R.string.strength_planner, R.string.strength_planner_description, R.mipmap.strength_planner, R.mipmap.icon_planner));
        mStrengthList.addStrength(new Strength("1000", R.string.strength_hardworker_title, R.string.strength_hardworker, R.string.strength_hardworker_description, R.mipmap.strength_hardworker, R.mipmap.icon_hardworker));
        mStrengthList.addStrength(new Strength("1100", R.string.strength_administrator_title, R.string.strength_administrator, R.string.strength_administrator_description, R.mipmap.strength_administrator, R.mipmap.icon_administrator));
        mStrengthList.addStrength(new Strength("1200", R.string.strength_motivator_title, R.string.strength_motivator, R.string.strength_motivator_description, R.mipmap.strength_motivator, R.mipmap.icon_motivator));
        mStrengthList.addStrength(new Strength("1300", R.string.strength_connector_title, R.string.strength_connector, R.string.strength_connector_description, R.mipmap.strength_connector, R.mipmap.icon_connector));
        mStrengthList.addStrength(new Strength("1400", R.string.strength_unifier_title, R.string.strength_unifier, R.string.strength_unifier_description, R.mipmap.strength_unifier, R.mipmap.icon_unifier));
        mStrengthList.addStrength(new Strength("1500", R.string.strength_inspirer_title, R.string.strength_inspirer, R.string.strength_inspirer_description, R.mipmap.strength_inspirer, R.mipmap.icon_inspirer));
        mStrengthList.addStrength(new Strength("1600", R.string.strength_feeler_title, R.string.strength_feeler, R.string.strength_feeler_description, R.mipmap.strength_feeler, R.mipmap.icon_feeler));
        mStrengthList.addStrength(new Strength("1700", R.string.strength_strategist_title, R.string.strength_strategist, R.string.strength_strategist_description, R.mipmap.strength_strategist, R.mipmap.icon_strategist));
        mStrengthList.addStrength(new Strength("1800", R.string.strength_visionary_title, R.string.strength_visionary, R.string.strength_visionary_description, R.mipmap.strength_visionary, R.mipmap.icon_visionary));
        mStrengthList.addStrength(new Strength("1900", R.string.strength_pathfinder_title, R.string.strength_pathfinder, R.string.strength_pathfinder_description, R.mipmap.strength_pathfinder, R.mipmap.icon_pathfinder));
        mStrengthList.addStrength(new Strength("2000", R.string.strength_researcher_title, R.string.strength_researcher, R.string.strength_researcher_description, R.mipmap.strength_researcher, R.mipmap.icon_researcher));
        mStrengthList.addStrength(new Strength("2100", R.string.strength_thinker_title, R.string.strength_thinker, R.string.strength_thinker_description, R.mipmap.strength_thinker, R.mipmap.icon_thinker));



    }


}
