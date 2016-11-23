package com.cloud9.android.happycode;

import java.util.Date;
import java.util.UUID;

/**
 * Created by nevrborn on 22.11.2016.
 */

public class Result {

    private Date mDate;
    private String mID;
    private String mName;
    private int mNo1Strength;
    private int mNo2Strength;
    private int mNo3Strength;

    // Adding all the Strenghts and descriptions to an array - mStrength
    private Strength[] mStrengths = new Strength[]{
            new Strength(R.string.strength_leader_title, R.string.strength_leader, R.string.strength_leader_description, R.mipmap.strength_leader),
            new Strength(R.string.strength_storyteller_title, R.string.strength_storyteller, R.string.strength_storyteller_description, R.mipmap.strength_leader),
            new Strength(R.string.strength_challenger_title, R.string.strength_challenger, R.string.strength_challenger_description, R.mipmap.strength_leader),
            new Strength(R.string.strength_networker_title, R.string.strength_networker, R.string.strength_networker_description, R.mipmap.strength_leader),
            new Strength(R.string.strength_moodmaker_title, R.string.strength_moodmaker, R.string.strength_moodmaker_description, R.mipmap.strength_leader),
            new Strength(R.string.strength_organiser_title, R.string.strength_organiser, R.string.strength_organiser_description, R.mipmap.strength_leader),
            new Strength(R.string.strength_guardian_title, R.string.strength_guardian, R.string.strength_guardian_description, R.mipmap.strength_leader),
            new Strength(R.string.strength_planner_title, R.string.strength_planner, R.string.strength_planner_description, R.mipmap.strength_leader),
            new Strength(R.string.strength_hardworker_title, R.string.strength_hardworker, R.string.strength_hardworker_description, R.mipmap.strength_leader),
            new Strength(R.string.strength_administrator_title, R.string.strength_administrator, R.string.strength_administrator_description, R.mipmap.strength_leader),
            new Strength(R.string.strength_motivator_title, R.string.strength_motivator, R.string.strength_motivator_description, R.mipmap.strength_leader),
            new Strength(R.string.strength_connector_title, R.string.strength_connector, R.string.strength_connector_description, R.mipmap.strength_leader),
            new Strength(R.string.strength_unifier_title, R.string.strength_unifier, R.string.strength_unifier_description, R.mipmap.strength_leader),
            new Strength(R.string.strength_inspirer_title, R.string.strength_inspirer, R.string.strength_inspirer_description, R.mipmap.strength_leader),
            new Strength(R.string.strength_feeler_title, R.string.strength_feeler, R.string.strength_feeler_description, R.mipmap.strength_leader),
            new Strength(R.string.strength_strategist_title, R.string.strength_strategist, R.string.strength_strategist_description, R.mipmap.strength_leader),
            new Strength(R.string.strength_visionary_title, R.string.strength_visionary, R.string.strength_visionary_description, R.mipmap.strength_leader),
            new Strength(R.string.strength_pathfinder_title, R.string.strength_pathfinder, R.string.strength_pathfinder_description, R.mipmap.strength_leader),
            new Strength(R.string.strength_researcher_title, R.string.strength_researcher, R.string.strength_researcher_description, R.mipmap.strength_leader),
            new Strength(R.string.strength_thinker_title, R.string.strength_thinker, R.string.strength_thinker_description, R.mipmap.strength_leader)
    };

    private static Result mResult;


    /* get singleton instance of the result*/
    public static Result getInstance() {
        if (mResult == null) {
            mResult = new Result();
        }
        return mResult;
    }


    private Result() {

        // UUID to be used when we hook up to Firebase
        mID = UUID.randomUUID().toString();
    }

    public void deleteResult() {
        mResult = null;
    }

    /*
    * getter to get a strength object based on the id of the first strength
    */
    public Strength getNoXStrength(int NoXStrengthId) {
        return mStrengths[NoXStrengthId];
    }


    public String getID() {
        return mID;
    }

    public int getNo1Strength() {
        return mNo1Strength;
    }

    public void setNo1Strength(int no1Strength) {
        mNo1Strength = no1Strength;
    }

    public int getNo2Strength() {
        return mNo2Strength;
    }

    public void setNo2Strength(int no2Strength) {
        mNo2Strength = no2Strength;
    }

    public int getNo3Strength() {
        return mNo3Strength;
    }

    public void setNo3Strength(int no3Strength) {
        mNo3Strength = no3Strength;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }
}
