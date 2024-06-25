package com.example.dependencyinjectionexempleapp.dependencyInjection;

import androidx.annotation.UiThread;
import androidx.fragment.app.FragmentManager;

import com.example.dependencyinjectionexempleapp.common.Constants;
import com.example.dependencyinjectionexempleapp.common.DialogManager;
import com.example.dependencyinjectionexempleapp.common.DialogManagerFactory;
import com.example.dependencyinjectionexempleapp.networking.StackoverflowAPI;
import com.example.dependencyinjectionexempleapp.questions.FetchQuestionDetailsUseCase;
import com.example.dependencyinjectionexempleapp.questions.FetchQuestionsListUseCase;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@UiThread
public class CompositionRoot {

    private Retrofit retrofit;
    private StackoverflowAPI stackoverflowAPI;

    @UiThread
    private Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    @UiThread
    private StackoverflowAPI getStackoverflowAPI() {

        if (stackoverflowAPI == null) {
            stackoverflowAPI = getRetrofit().create(StackoverflowAPI.class);
        }
        return stackoverflowAPI;
    }

    @UiThread
    public FetchQuestionDetailsUseCase getFetchQuestionDetailsUseCase() {
        return new FetchQuestionDetailsUseCase(getStackoverflowAPI());
    }

    @UiThread
    public FetchQuestionsListUseCase getFetchQuestionsListUseCase() {
        return new FetchQuestionsListUseCase(getStackoverflowAPI());
    }

    public DialogManagerFactory getDialogManagerFactory() {
        return new DialogManagerFactory();
    }
}
