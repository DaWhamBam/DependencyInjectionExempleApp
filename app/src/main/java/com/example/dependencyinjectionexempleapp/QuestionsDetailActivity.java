package com.example.dependencyinjectionexempleapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Window;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuestionsDetailActivity extends AppCompatActivity implements Callback<SingleQuestionResponseSchema> {

    public  static void start(Context context, String questionId) {
        Intent i = new Intent(context, QuestionsDetailActivity.class);
        i.putExtra(EXTRA_QUESTION_ID, questionId);
        context.startActivity(i);
    }

    public static final String EXTRA_QUESTION_ID = "EXTRA_QUESTION_ID";
    private TextView mTXTQuestionBody;
    private StackoverflowAPI stackoverflowAPI;
    private String mQuestionId;
    private Call<SingleQuestionResponseSchema> mCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_detail);

        mTXTQuestionBody = findViewById(R.id.txt_question_body);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        stackoverflowAPI = retrofit.create(StackoverflowAPI.class);
        mQuestionId = getIntent().getExtras().getString(EXTRA_QUESTION_ID);


    }

    @Override
    protected void onStart() {
        super.onStart();
        mCall = stackoverflowAPI.questionDetail(mQuestionId);
        mCall.enqueue(this);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mCall != null) {
            mCall.cancel();
        }
    }


    @Override
    public void onResponse(Call<SingleQuestionResponseSchema> call, Response<SingleQuestionResponseSchema> response) {
        SingleQuestionResponseSchema questionResponseSchema;
        if (response.isSuccessful() && (questionResponseSchema = response.body()) != null) {
            String questionBody = questionResponseSchema.getQuestions().getmBody();
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                mTXTQuestionBody.setText(Html.fromHtml(questionBody, Html.FROM_HTML_MODE_LEGACY));
            } else {
                mTXTQuestionBody.setText(Html.fromHtml(questionBody));
            }
        } else {
            onFailure(call, null);
        }
    }

    @Override
    public void onFailure(Call<SingleQuestionResponseSchema> call, Throwable throwable) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(ServerErrorDialogFragment.newInstance(), null)
                .commitAllowingStateLoss();

    }
}