package com.example.dependencyinjectionexempleapp.detailsQuestion;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dependencyinjectionexempleapp.questions.QuestionWithBody;
import com.example.dependencyinjectionexempleapp.R;
import com.example.dependencyinjectionexempleapp.questionsList.BaseViewMVC;

public class QuestionDetailsViewMVCImpl extends BaseViewMVC<QuestionDetailsViewMVC.Listener>
        implements QuestionDetailsViewMVC {

    private final TextView mTxtQuestionBody;

    public QuestionDetailsViewMVCImpl(LayoutInflater inflater, ViewGroup container) {
        setRootView(inflater.inflate(R.layout.activity_questions_detail, container, false));
        mTxtQuestionBody = findViewById(R.id.txt_question_body);
    }

    @Override
    public void bindQuestion(QuestionWithBody question) {
        String questionBody = question.getmBody();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            mTxtQuestionBody.setText(Html.fromHtml(questionBody, Html.FROM_HTML_MODE_LEGACY));
        } else {
            mTxtQuestionBody.setText(Html.fromHtml(questionBody));
        }
    }
}