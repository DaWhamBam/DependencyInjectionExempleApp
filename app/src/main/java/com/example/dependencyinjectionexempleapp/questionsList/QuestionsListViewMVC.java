package com.example.dependencyinjectionexempleapp.questionsList;

import com.example.dependencyinjectionexempleapp.Question;

import java.util.List;

public interface QuestionsListViewMVC extends ObservableViewMVC<QuestionsListViewMVC.Listener> {

    interface Listener {
        void onQuestionClicked(Question question);
    }

    void bindQuestins(List<Question> questions);
}
