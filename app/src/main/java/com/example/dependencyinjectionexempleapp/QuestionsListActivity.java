package com.example.dependencyinjectionexempleapp;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.example.dependencyinjectionexempleapp.common.DialogManager;
import com.example.dependencyinjectionexempleapp.common.ServerErrorDialogFragment;
import com.example.dependencyinjectionexempleapp.detailsQuestion.QuestionsDetailActivity;
import com.example.dependencyinjectionexempleapp.networking.StackoverflowAPI;
import com.example.dependencyinjectionexempleapp.questions.FetchQuestionsListUseCase;
import com.example.dependencyinjectionexempleapp.questions.Question;
import com.example.dependencyinjectionexempleapp.questionsList.QuestionsListViewMVC;
import com.example.dependencyinjectionexempleapp.questionsList.QuestionsListViewMVCImpl;

import java.util.List;

import retrofit2.Retrofit;


public class QuestionsListActivity extends AppCompatActivity implements QuestionsListViewMVC.Listener, FetchQuestionsListUseCase.Listener {



    private static final int NUM_OF_QUESTIONS_TO_FETCH = 20;
    private FetchQuestionsListUseCase fetchQuestionsListUseCase;
    private QuestionsListViewMVC mViewMVC;
    private DialogManager mDialogManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewMVC = new QuestionsListViewMVCImpl(LayoutInflater.from(this), null);

        setContentView(mViewMVC.getRootView());

        // Networking
        StackoverflowAPI stackoverflowAPI = ((MyApplication) getApplication()).getStackoverflowAPI();
        fetchQuestionsListUseCase = new FetchQuestionsListUseCase(stackoverflowAPI);

        // Dialog Manager
        mDialogManager = new DialogManager(getSupportFragmentManager());

    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewMVC.registerListener(this);
        fetchQuestionsListUseCase.registerListener(this);
        fetchQuestionsListUseCase.fetchLastActiveQuestionsAndNotify(NUM_OF_QUESTIONS_TO_FETCH);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mViewMVC.unregisterListener(this);
        fetchQuestionsListUseCase.unregisterListener(this);
    }

    @Override
    public void onFetchOfQuestionSucceeded(List<Question> questions) {
        mViewMVC.bindQuestins(questions);

    }

    @Override
    public void onFetchOfQuestionFailed() {

        mDialogManager.showRetainedDialogWithId(ServerErrorDialogFragment.newInstance(), "");

    }

    @Override
    public void onQuestionClicked(Question question) {

        QuestionsDetailActivity.start(QuestionsListActivity.this, question.getmId());

    }
}