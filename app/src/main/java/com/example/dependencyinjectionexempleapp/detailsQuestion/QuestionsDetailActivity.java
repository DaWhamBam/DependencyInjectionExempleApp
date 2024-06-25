package com.example.dependencyinjectionexempleapp.detailsQuestion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.example.dependencyinjectionexempleapp.MyApplication;
import com.example.dependencyinjectionexempleapp.common.DialogManager;
import com.example.dependencyinjectionexempleapp.common.ServerErrorDialogFragment;
import com.example.dependencyinjectionexempleapp.networking.StackoverflowAPI;
import com.example.dependencyinjectionexempleapp.questions.FetchQuestionDetailsUseCase;
import com.example.dependencyinjectionexempleapp.questions.QuestionWithBody;

import retrofit2.Retrofit;

public class QuestionsDetailActivity extends AppCompatActivity implements QuestionDetailsViewMVC.Listener, FetchQuestionDetailsUseCase.Listener {

    public  static void start(Context context, String questionId) {
        Intent i = new Intent(context, QuestionsDetailActivity.class);
        i.putExtra(EXTRA_QUESTION_ID, questionId);
        context.startActivity(i);
    }

    public static final String EXTRA_QUESTION_ID = "EXTRA_QUESTION_ID";
    private TextView mTXTQuestionBody;
    private String mQuestionId;
    private QuestionDetailsViewMVC mViewMVC;
    private FetchQuestionDetailsUseCase fetchQuestionDetailsUseCase;
    private DialogManager mDialogManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewMVC = new QuestionDetailsViewMVCImpl(LayoutInflater.from(this), null);
        setContentView(mViewMVC.getRootView());


        mQuestionId = getIntent().getExtras().getString(EXTRA_QUESTION_ID);

        // Networking
        fetchQuestionDetailsUseCase = ((MyApplication) getApplication()).getFetchQuestionDetailsUseCase();

        // Dialog Manager
        mDialogManager = new DialogManager(getSupportFragmentManager());


    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewMVC.registerListener(this);
        fetchQuestionDetailsUseCase.registerListener(this);
        fetchQuestionDetailsUseCase.fetchQuestionDetailsAndNotify(mQuestionId);

    }

    @Override
    protected void onStop() {
        super.onStop();
        mViewMVC.unregisterListener(this);
        fetchQuestionDetailsUseCase.unregisterListener(this);

    }

    @Override
    public void onFetchOfQuestionDetailsSucceeded(QuestionWithBody question) {
        mViewMVC.bindQuestion(question);
    }

    @Override
    public void onFetchOfQuestionDetailsFailed() {

        mDialogManager.showRetainedDialogWithId(ServerErrorDialogFragment.newInstance(), "");

    }
}