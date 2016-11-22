package com.cloud9.android.happycode;

/**
 * Created by nevrborn on 22.11.2016.
 */

public class Question {

    private String mTitle;
    private int mQuestionID;
    private int mDescriptionID;
    private int mPercentage;

    // Contstructor to make Questions
    public Question(String title, int questionId, int descriptionId) {
        this.mTitle = title;
        this.mQuestionID = questionId;
        this.mDescriptionID = descriptionId;
    }

    public int getQuestion() {
        return mQuestionID;
    }

    public int getDescription() {
        return mDescriptionID;
    }

    public int getPercentage() {
        return mPercentage;
    }

    public void setPercentage(int percentage) {
        mPercentage = percentage;
    }

    public String getTitle() {
        return mTitle;
    }
}
