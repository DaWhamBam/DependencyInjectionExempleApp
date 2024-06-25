package com.example.dependencyinjectionexempleapp;

import android.app.Application;

import androidx.annotation.UiThread;

import com.example.dependencyinjectionexempleapp.common.Constants;
import com.example.dependencyinjectionexempleapp.dependencyInjection.CompositionRoot;
import com.example.dependencyinjectionexempleapp.networking.StackoverflowAPI;
import com.example.dependencyinjectionexempleapp.questions.FetchQuestionDetailsUseCase;
import com.example.dependencyinjectionexempleapp.questions.FetchQuestionsListUseCase;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyApplication extends Application {

    private CompositionRoot compositionRoot;

    @Override
    public void onCreate() {
        super.onCreate();
        compositionRoot = new CompositionRoot();
    }

    public CompositionRoot getCompositionRoot() {
        return  compositionRoot;
    }
}