package com.example.dependencyinjectionexempleapp.questions;

import androidx.annotation.Nullable;

import com.example.dependencyinjectionexempleapp.common.Constants;
import com.example.dependencyinjectionexempleapp.networking.SingleQuestionResponseSchema;
import com.example.dependencyinjectionexempleapp.networking.StackoverflowAPI;
import com.example.dependencyinjectionexempleapp.questionsList.BaseObservable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FetchQuestionDetailsUseCase extends BaseObservable<FetchQuestionDetailsUseCase.Listener> {

    public interface Listener {
        void onFetchOfQuestionDetailsSucceeded(QuestionWithBody question);
        void onFetchOfQuestionDetailsFailed();
        }

        private final StackoverflowAPI mStackoverflowAPI;

    @Nullable
    Call<SingleQuestionResponseSchema> call;

    public FetchQuestionDetailsUseCase(StackoverflowAPI stackoverflowAPI) {

        mStackoverflowAPI = stackoverflowAPI;

    }

    public  void fetchQuestionDetailsAndNotify(String questionId) {
        cancelCurrentFetchIfActive();

        call = mStackoverflowAPI.questionDetail(questionId);
        call.enqueue(new Callback<SingleQuestionResponseSchema>() {
            @Override
            public void onResponse(Call<SingleQuestionResponseSchema> call, Response<SingleQuestionResponseSchema> response) {
                if (response.isSuccessful()){
                    notifySucceeded(response.body().getQuestions());
                } else {
                    notifyFailed();
                }
            }

            @Override
            public void onFailure(Call<SingleQuestionResponseSchema> call, Throwable throwable) {
                if (call != null && !call.isCanceled() && !call.isExecuted()) {
                    call.cancel();
                }
            }


        });
    }

    private void cancelCurrentFetchIfActive() {
        if (call !=null && !call.isCanceled() && !call.isExecuted()) {
            call.cancel();
        }
    }

    private void notifySucceeded(QuestionWithBody question) {
        for (Listener listener : getListeners()) {
            listener.onFetchOfQuestionDetailsSucceeded(question);
        }
    }

    private void notifyFailed() {
        for (Listener listener : getListeners()) {
            listener.onFetchOfQuestionDetailsFailed();
        }
    }


}
