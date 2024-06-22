package com.example.dependencyinjectionexempleapp;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.example.dependencyinjectionexempleapp.detailsQuestion.QuestionsDetailActivity;
import com.example.dependencyinjectionexempleapp.networking.QuestionsListResponseSchema;
import com.example.dependencyinjectionexempleapp.networking.StackoverflowAPI;
import com.example.dependencyinjectionexempleapp.questions.FetchQuestionsListUseCase;
import com.example.dependencyinjectionexempleapp.questions.Question;
import com.example.dependencyinjectionexempleapp.questionsList.QuestionsListViewMVC;
import com.example.dependencyinjectionexempleapp.questionsList.QuestionsListViewMVCImpl;

import java.util.List;

import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;


public class QuestionsListActivity extends AppCompatActivity implements QuestionsListViewMVC.Listener, FetchQuestionsListUseCase.Listener {



    private static final int NUM_OF_QUESTIONS_TO_FETCH = 20;
    private FetchQuestionsListUseCase fetchQuestionsListUseCase;

    private QuestionsListViewMVC mViewMVC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewMVC = new QuestionsListViewMVCImpl(LayoutInflater.from(this), null);

        setContentView(mViewMVC.getRootView());

        // Networking
        fetchQuestionsListUseCase = new FetchQuestionsListUseCase();

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
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(ServerErrorDialogFragment.newInstance(), null)
                .commitAllowingStateLoss();
    }

    @Override
    public void onQuestionClicked(Question question) {

        QuestionsDetailActivity.start(QuestionsListActivity.this, question.getmId());

    }
}