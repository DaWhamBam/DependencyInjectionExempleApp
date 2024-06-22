package com.example.dependencyinjectionexempleapp.detailsQuestion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.example.dependencyinjectionexempleapp.Constants;
import com.example.dependencyinjectionexempleapp.R;
import com.example.dependencyinjectionexempleapp.ServerErrorDialogFragment;
import com.example.dependencyinjectionexempleapp.networking.SingleQuestionResponseSchema;
import com.example.dependencyinjectionexempleapp.networking.StackoverflowAPI;
import com.example.dependencyinjectionexempleapp.questions.FetchQuestionDetailsUseCase;
import com.example.dependencyinjectionexempleapp.questions.QuestionWithBody;
import com.example.dependencyinjectionexempleapp.questionsList.QuestionsListViewMVC;
import com.example.dependencyinjectionexempleapp.questionsList.QuestionsListViewMVCImpl;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
    private  FetchQuestionDetailsUseCase fetchQuestionDetailsUseCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewMVC = new QuestionDetailsViewMVCImpl(LayoutInflater.from(this), null);
        setContentView(mViewMVC.getRootView());


        mQuestionId = getIntent().getExtras().getString(EXTRA_QUESTION_ID);

        // Networking
        fetchQuestionDetailsUseCase = new FetchQuestionDetailsUseCase();


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

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(ServerErrorDialogFragment.newInstance(), null)
                .commitAllowingStateLoss();
    }
}