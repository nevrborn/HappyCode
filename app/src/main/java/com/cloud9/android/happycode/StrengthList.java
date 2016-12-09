package com.cloud9.android.happycode;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nevrborn on 28.11.2016.
 */

public class StrengthList {

    private static StrengthList sStrengthList;
    private static List<Strength> mStrengthList;

    public static StrengthList get(Context context) {
        if (sStrengthList == null) {
            sStrengthList = new StrengthList(context);
        }
        return sStrengthList;
    }

    private StrengthList(Context context) {
        Context context1 = context.getApplicationContext();
        mStrengthList = new ArrayList<>();

        addStrength(new Strength("100", R.string.strength_leader_title, R.string.strength_leader, R.string.strength_leader_description, R.drawable.strength_leader, R.drawable.icon_leader));
        addStrength(new Strength("200", R.string.strength_storyteller_title, R.string.strength_storyteller, R.string.strength_storyteller_description, R.drawable.strength_storyteller, R.drawable.icon_storyteller));
        addStrength(new Strength("300", R.string.strength_challenger_title, R.string.strength_challenger, R.string.strength_challenger_description, R.drawable.strength_challenger, R.drawable.icon_challenger));
        addStrength(new Strength("400", R.string.strength_networker_title, R.string.strength_networker, R.string.strength_networker_description, R.drawable.strength_networker, R.drawable.icon_networker));
        addStrength(new Strength("500", R.string.strength_moodmaker_title, R.string.strength_moodmaker, R.string.strength_moodmaker_description, R.drawable.strength_moodmaker, R.drawable.icon_moodmaker));
        addStrength(new Strength("600", R.string.strength_organiser_title, R.string.strength_organiser, R.string.strength_organiser_description, R.drawable.strength_organiser, R.drawable.icon_organiser));
        addStrength(new Strength("700", R.string.strength_guardian_title, R.string.strength_guardian, R.string.strength_guardian_description, R.drawable.strength_guardian, R.drawable.icon_guardian));
        addStrength(new Strength("800", R.string.strength_planner_title, R.string.strength_planner, R.string.strength_planner_description, R.drawable.strength_planner, R.drawable.icon_planner));
        addStrength(new Strength("900", R.string.strength_hardworker_title, R.string.strength_hardworker, R.string.strength_hardworker_description, R.drawable.strength_hardworker, R.drawable.icon_hardworker));
        addStrength(new Strength("1000", R.string.strength_administrator_title, R.string.strength_administrator, R.string.strength_administrator_description, R.drawable.strength_administrator, R.drawable.icon_administrator));
        addStrength(new Strength("1100", R.string.strength_motivator_title, R.string.strength_motivator, R.string.strength_motivator_description, R.drawable.strength_motivator, R.drawable.icon_motivator));
        addStrength(new Strength("1200", R.string.strength_connector_title, R.string.strength_connector, R.string.strength_connector_description, R.drawable.strength_connector, R.drawable.icon_connector));
        addStrength(new Strength("1300", R.string.strength_unifier_title, R.string.strength_unifier, R.string.strength_unifier_description, R.drawable.strength_unifier, R.drawable.icon_unifier));
        addStrength(new Strength("1400", R.string.strength_inspirer_title, R.string.strength_inspirer, R.string.strength_inspirer_description, R.drawable.strength_inspirer, R.drawable.icon_inspirer));
        addStrength(new Strength("1500", R.string.strength_feeler_title, R.string.strength_feeler, R.string.strength_feeler_description, R.drawable.strength_feeler, R.drawable.icon_feeler));
        addStrength(new Strength("1600", R.string.strength_strategist_title, R.string.strength_strategist, R.string.strength_strategist_description, R.drawable.strength_strategist, R.drawable.icon_strategist));
        addStrength(new Strength("1700", R.string.strength_visionary_title, R.string.strength_visionary, R.string.strength_visionary_description, R.drawable.strength_visionary, R.drawable.icon_visionary));
        addStrength(new Strength("1800", R.string.strength_pathfinder_title, R.string.strength_pathfinder, R.string.strength_pathfinder_description, R.drawable.strength_pathfinder, R.drawable.icon_pathfinder));
        addStrength(new Strength("1900", R.string.strength_researcher_title, R.string.strength_researcher, R.string.strength_researcher_description, R.drawable.strength_researcher, R.drawable.icon_researcher));
        addStrength(new Strength("2000", R.string.strength_thinker_title, R.string.strength_thinker, R.string.strength_thinker_description, R.drawable.strength_thinker, R.drawable.icon_thinker));

    }

    private void addStrength(Strength strength) {
        mStrengthList.add(strength);
    }

    public Strength getStrengthFromIndex(int id) {
        return mStrengthList.get(id);
    }

    public String getStrengthKeyFromIndex(int id) {
        return mStrengthList.get(id).getID();
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

    public static ArrayList<String> getAllKeys() {
        ArrayList<String> listOfAllKeys = new ArrayList<>();

        int i = 0;

        while (i < mStrengthList.size()) {
            listOfAllKeys.add(i, mStrengthList.get(i).getID());
            i += 1;
        }

        return listOfAllKeys;
    }

}
