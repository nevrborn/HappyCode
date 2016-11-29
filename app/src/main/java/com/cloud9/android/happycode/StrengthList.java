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
