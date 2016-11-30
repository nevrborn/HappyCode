package com.cloud9.android.happycode;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by nevrborn on 28.11.2016.
 */

public class StrengthList {

    private static StrengthList sStrengthList;
    private static List<Strength> mStrengthList;
    private Context mContext;

    public static StrengthList get(Context context) {
        if (sStrengthList == null) {
            sStrengthList = new StrengthList(context);
        }
        return sStrengthList;
    }

    private StrengthList(Context context) {
        mContext = context.getApplicationContext();
        mStrengthList = new ArrayList<>();

        sStrengthList.addStrength(new Strength("100", R.string.strength_leader_title, R.string.strength_leader, R.string.strength_leader_description, R.mipmap.strength_leader, R.mipmap.icon_leader));
        sStrengthList.addStrength(new Strength("200", R.string.strength_leader_title, R.string.strength_leader, R.string.strength_leader_description, R.mipmap.strength_leader, R.mipmap.icon_leader));
        sStrengthList.addStrength(new Strength("300", R.string.strength_storyteller_title, R.string.strength_storyteller, R.string.strength_storyteller_description, R.mipmap.strength_storyteller, R.mipmap.icon_storyteller));
        sStrengthList.addStrength(new Strength("400", R.string.strength_challenger_title, R.string.strength_challenger, R.string.strength_challenger_description, R.mipmap.strength_challenger, R.mipmap.icon_challenger));
        sStrengthList.addStrength(new Strength("500", R.string.strength_networker_title, R.string.strength_networker, R.string.strength_networker_description, R.mipmap.strength_networker, R.mipmap.icon_networker));
        sStrengthList.addStrength(new Strength("600", R.string.strength_moodmaker_title, R.string.strength_moodmaker, R.string.strength_moodmaker_description, R.mipmap.strength_moodmaker, R.mipmap.icon_networker));
        sStrengthList.addStrength(new Strength("700", R.string.strength_organiser_title, R.string.strength_organiser, R.string.strength_organiser_description, R.mipmap.strength_organiser, R.mipmap.icon_organiser));
        sStrengthList.addStrength(new Strength("800", R.string.strength_guardian_title, R.string.strength_guardian, R.string.strength_guardian_description, R.mipmap.strength_guardian, R.mipmap.icon_guardian));
        sStrengthList.addStrength(new Strength("900", R.string.strength_planner_title, R.string.strength_planner, R.string.strength_planner_description, R.mipmap.strength_planner, R.mipmap.icon_planner));
        sStrengthList.addStrength(new Strength("1000", R.string.strength_hardworker_title, R.string.strength_hardworker, R.string.strength_hardworker_description, R.mipmap.strength_hardworker, R.mipmap.icon_hardworker));
        sStrengthList.addStrength(new Strength("1100", R.string.strength_administrator_title, R.string.strength_administrator, R.string.strength_administrator_description, R.mipmap.strength_administrator, R.mipmap.icon_administrator));
        sStrengthList.addStrength(new Strength("1200", R.string.strength_motivator_title, R.string.strength_motivator, R.string.strength_motivator_description, R.mipmap.strength_motivator, R.mipmap.icon_motivator));
        sStrengthList.addStrength(new Strength("1300", R.string.strength_connector_title, R.string.strength_connector, R.string.strength_connector_description, R.mipmap.strength_connector, R.mipmap.icon_connector));
        sStrengthList.addStrength(new Strength("1400", R.string.strength_unifier_title, R.string.strength_unifier, R.string.strength_unifier_description, R.mipmap.strength_unifier, R.mipmap.icon_unifier));
        sStrengthList.addStrength(new Strength("1500", R.string.strength_inspirer_title, R.string.strength_inspirer, R.string.strength_inspirer_description, R.mipmap.strength_inspirer, R.mipmap.icon_inspirer));
        sStrengthList.addStrength(new Strength("1600", R.string.strength_feeler_title, R.string.strength_feeler, R.string.strength_feeler_description, R.mipmap.strength_feeler, R.mipmap.icon_feeler));
        sStrengthList.addStrength(new Strength("1700", R.string.strength_strategist_title, R.string.strength_strategist, R.string.strength_strategist_description, R.mipmap.strength_strategist, R.mipmap.icon_strategist));
        sStrengthList.addStrength(new Strength("1800", R.string.strength_visionary_title, R.string.strength_visionary, R.string.strength_visionary_description, R.mipmap.strength_visionary, R.mipmap.icon_visionary));
        sStrengthList.addStrength(new Strength("1900", R.string.strength_pathfinder_title, R.string.strength_pathfinder, R.string.strength_pathfinder_description, R.mipmap.strength_pathfinder, R.mipmap.icon_pathfinder));
        sStrengthList.addStrength(new Strength("2000", R.string.strength_researcher_title, R.string.strength_researcher, R.string.strength_researcher_description, R.mipmap.strength_researcher, R.mipmap.icon_researcher));
        sStrengthList.addStrength(new Strength("2100", R.string.strength_thinker_title, R.string.strength_thinker, R.string.strength_thinker_description, R.mipmap.strength_thinker, R.mipmap.icon_thinker));

    }

    public void addStrength(Strength strength) {
        mStrengthList.add(strength);
    }

    public Strength getStrengthFromIndex(int id) {
        return mStrengthList.get(id);
    }

    public List<Strength> getStrengthList() {
        return mStrengthList;
    }

    public int getSize() {
        return mStrengthList.size();
    }

    public Strength getStrengthFromKey(String strengthKey) {

        int indexOfKey = 0;
        int i = 0;

        while (i < mStrengthList.size()) {

            if (mStrengthList.get(i).getID().equals(strengthKey)) {
                indexOfKey = i;
                i = mStrengthList.size();
            }

            i += 1;
        }

        return mStrengthList.get(indexOfKey);
    }

}
