package com.cloud9.android.happycode;

/**
 * Created by nevrborn on 22.11.2016.
 */

public class Question {

    private int mQuestionID;
    private int mDescriptionID;

    // Contstructor to make Questions
    public Question(int questionId, int descriptionId) {
        this.mQuestionID = questionId;
        this.mDescriptionID = descriptionId;
    }

    public int getQuestion() {
        return mQuestionID;
    }

    public int getDescription() {
        return mDescriptionID;
    }

}
