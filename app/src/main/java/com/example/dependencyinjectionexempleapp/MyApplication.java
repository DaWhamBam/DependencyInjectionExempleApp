package com.example.dependencyinjectionexempleapp;

import android.app.Application;

import androidx.annotation.UiThread;

import com.example.dependencyinjectionexempleapp.common.Constants;
import com.example.dependencyinjectionexempleapp.networking.StackoverflowAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyApplication extends Application {

    private Retrofit retrofit;
    private StackoverflowAPI stackoverflowAPI;

    @UiThread
    public Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    @UiThread
    public StackoverflowAPI getStackoverflowAPI() {

        if (stackoverflowAPI == null) {
            stackoverflowAPI = getRetrofit().create(StackoverflowAPI.class);
        }
        return stackoverflowAPI;

    }



}