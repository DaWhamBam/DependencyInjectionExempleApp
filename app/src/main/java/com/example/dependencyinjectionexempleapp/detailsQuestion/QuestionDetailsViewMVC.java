package com.example.dependencyinjectionexempleapp.detailsQuestion;

import com.example.dependencyinjectionexempleapp.questions.QuestionWithBody;
import com.example.dependencyinjectionexempleapp.questionsList.ObservableViewMVC;

public interface QuestionDetailsViewMVC extends ObservableViewMVC<QuestionDetailsViewMVC.Listener> {
    interface Listener{

    }

    void bindQuestion(QuestionWithBody question);
}
