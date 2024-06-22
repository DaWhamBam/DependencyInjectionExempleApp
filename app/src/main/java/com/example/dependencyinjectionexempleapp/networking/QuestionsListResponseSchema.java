package com.example.dependencyinjectionexempleapp.networking;

import com.example.dependencyinjectionexempleapp.questions.Question;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuestionsListResponseSchema {

    @SerializedName("items")
    private final List<Question> mQuestions;
    public QuestionsListResponseSchema(List<Question> questions) {
        mQuestions = questions;
    }

    public List<Question> getQuestions() {
        return mQuestions;
    }
}
