package com.example.dependencyinjectionexempleapp.networking;

import com.example.dependencyinjectionexempleapp.questions.QuestionWithBody;
import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

public class SingleQuestionResponseSchema {

    @SerializedName("items")
    private final List<QuestionWithBody> mQuestions;

    public SingleQuestionResponseSchema(QuestionWithBody questions) {
        mQuestions = Collections.singletonList(questions);
    }

    public QuestionWithBody getQuestions() {
        return mQuestions.get(0);
    }
}
