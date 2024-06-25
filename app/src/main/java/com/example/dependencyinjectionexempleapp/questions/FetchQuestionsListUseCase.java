package com.example.dependencyinjectionexempleapp.questions;

import androidx.annotation.Nullable;

import com.example.dependencyinjectionexempleapp.common.Constants;
import com.example.dependencyinjectionexempleapp.networking.QuestionsListResponseSchema;
import com.example.dependencyinjectionexempleapp.networking.StackoverflowAPI;
import com.example.dependencyinjectionexempleapp.questionsList.BaseObservable;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FetchQuestionsListUseCase extends BaseObservable<FetchQuestionsListUseCase.Listener> {


    public interface Listener {
        void onFetchOfQuestionSucceeded(List<Question> questions);
        void onFetchOfQuestionFailed();
    }

    private final StackoverflowAPI stackoverflowAPI;

    @Nullable
    Call<QuestionsListResponseSchema> call;

    public FetchQuestionsListUseCase(Retrofit retrofit) {

        stackoverflowAPI = retrofit.create(StackoverflowAPI.class);
    }

    public  void fetchLastActiveQuestionsAndNotify(int numOfQuestions) {
        cancelCurrentFetchIfActive();

        call = stackoverflowAPI.lastActiveQuestions(numOfQuestions);
        call.enqueue(new Callback<QuestionsListResponseSchema>() {
            @Override
            public void onResponse(Call<QuestionsListResponseSchema> call, Response<QuestionsListResponseSchema> response) {
                if (response.isSuccessful()) {
                    notifySucceeded(response.body().getQuestions());
                } else {
                    notifyFailed();
                }

            }

            @Override
            public void onFailure(Call<QuestionsListResponseSchema> call, Throwable throwable) {
                notifyFailed();
            }
        });

    }

    private void cancelCurrentFetchIfActive(){
        if (call != null && !call.isCanceled() && !call.isExecuted()) {
            call.cancel();
        }
    }

    private void notifySucceeded(List<Question> questions) {
        List<Question> unmodifiableQuestions = Collections.unmodifiableList(questions);
        for (Listener listener : getListeners()) {
            listener.onFetchOfQuestionSucceeded(unmodifiableQuestions);
        }
    }

    private  void notifyFailed() {
        for (Listener listener : getListeners()) {
            listener.onFetchOfQuestionFailed();
        }
    }


}
