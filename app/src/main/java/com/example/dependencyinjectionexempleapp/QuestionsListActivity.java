package com.example.dependencyinjectionexempleapp;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.example.dependencyinjectionexempleapp.detailsQuestion.QuestionsDetailActivity;
import com.example.dependencyinjectionexempleapp.questionsList.QuestionsListViewMVC;
import com.example.dependencyinjectionexempleapp.questionsList.QuestionsListViewMVCImpl;

import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;


public class QuestionsListActivity extends AppCompatActivity implements Callback<QuestionsListResponseSchema>, QuestionsListViewMVC.Listener {



    private StackoverflowAPI mStackoverflowAPI;
    private Call<QuestionsListResponseSchema> mCall;
    private QuestionsListViewMVC mViewMVC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewMVC = new QuestionsListViewMVCImpl(LayoutInflater.from(this), null);

        setContentView(mViewMVC.getRootView());


        // Retrofit

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mStackoverflowAPI = retrofit.create(StackoverflowAPI.class);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewMVC.registerListener(this);
        mCall = mStackoverflowAPI.lastActiveQuestions(20);
        mCall.enqueue(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mViewMVC.unregisterListener(this);
        if(mCall !=null) {
            mCall.cancel();
        }
    }

    @Override
    public void onResponse(Call<QuestionsListResponseSchema> call, Response<QuestionsListResponseSchema> response) {
        QuestionsListResponseSchema responseSchema;
        if(response.isSuccessful() && (responseSchema = response.body()) != null) {
            mViewMVC.bindQuestins(responseSchema.getQuestions());
        } else {
            onFailure(call, null);
        }
    }

    @Override
    public void onFailure(Call<QuestionsListResponseSchema> call, Throwable throwable) {
        QuestionsListResponseSchema responseSchema;
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