package com.example.dependencyinjectionexempleapp.questionsList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dependencyinjectionexempleapp.questions.Question;
import com.example.dependencyinjectionexempleapp.R;

import java.util.ArrayList;
import java.util.List;

public class QuestionsListViewMVCImpl extends BaseViewMVC <QuestionsListViewMVC.Listener> implements QuestionsListViewMVC {

    private RecyclerView mRecyclerView;
    private QuestionsAdapter mQuestionAdapter;

    public QuestionsListViewMVCImpl(LayoutInflater inflater, ViewGroup container) {
        setRootView(inflater.inflate(R.layout.layout_question_list, container, false));

        // Initializing RecyclerView
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mQuestionAdapter = new QuestionsAdapter(new OnQuestionClickListener() {
            @Override
            public void onQuestionClicked(Question question) {
                for(Listener listener : getListeners()) {
                    listener.onQuestionClicked(question);
                }

            }
        });

        mRecyclerView.setAdapter(mQuestionAdapter);

    }



    @Override
    public void bindQuestins(List<Question> questions) {
        mQuestionAdapter.bindData(questions);

    }

    public interface OnQuestionClickListener{
        void onQuestionClicked(Question question);

    }

    public static class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.QuestionViewHolder> {

        private final OnQuestionClickListener mOnQuestionClickListener;
        private List<Question> mQuestionList = new ArrayList<>(0);


        // View holder
        public class QuestionViewHolder extends RecyclerView.ViewHolder {
            public TextView mTitle;

            public QuestionViewHolder(@NonNull View itemView) {
                super(itemView);
                mTitle = itemView.findViewById(R.id.txt_title);

            }
        }

        public QuestionsAdapter(OnQuestionClickListener onQuestionClickListener) {
            mOnQuestionClickListener = onQuestionClickListener;
        }

        // Binding Data
        public void bindData(List<Question> questions){
            mQuestionList = new ArrayList<>(questions);
            notifyDataSetChanged();
        }


        @NonNull
        @Override
        public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_question_list_item, parent, false);
            return new QuestionViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {

            holder.mTitle.setText(mQuestionList.get(position).getmTitle());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnQuestionClickListener.onQuestionClicked(mQuestionList.get(position));
                }
            });
        }

        @Override
        public int getItemCount() {
            return mQuestionList.size();
        }

    }

}
