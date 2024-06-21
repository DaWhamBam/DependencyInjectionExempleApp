package com.example.dependencyinjectionexempleapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface StackoverflowAPI {


    @GET("questions?order=desc&sort=activity&site=stackoverflow")
    Call<QuestionsListResponseSchema> lastActiveQuestions(@Query("pagesize") Integer pageSize);

    @GET("questions/{questionId}?site=stackoverflow&filter=withbody")
    Call<SingleQuestionResponseSchema> questionDetail(@Path("questionId") String questionId);



}
