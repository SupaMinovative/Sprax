package com.minovative.sprax;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FlashcardAdapter extends RecyclerView.Adapter<FlashcardAdapter.FlashcardViewHolder> {

    private List<Word> wordList;
    private RecyclerView recyclerView;
    private OnFlashcardCompletionListener listener;
    private String activityName;
    private Context context;


    public FlashcardAdapter(List<Word> wordList, RecyclerView recyclerView, OnFlashcardCompletionListener listener) {
        this.wordList = wordList;
        this.recyclerView = recyclerView;

        if (context instanceof AppCompatActivity) {
        this.activityName = ((AppCompatActivity) context).getLocalClassName();
        }

        this.listener = listener;
    }

    public  interface OnFlashcardCompletionListener {

        void onLastCardReached();
    }

    @NonNull
    @Override
    public FlashcardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.word_card, parent, false);
        return new FlashcardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlashcardViewHolder holder, int position) {

        Word currentWord = wordList.get(position);
        holder.wordCount.setText("Word " + (position + 1));
        holder.vocabulary.setText(currentWord.getGermanWord());
        holder.exampleSentence.setText(currentWord.getExampleSentence());

        holder.vocabFlip.setOnClickListener(view -> {

            if (holder.vocabulary.getText().equals(currentWord.getGermanWord()) && currentWord.getMeaning() != null) {

                holder.vocabulary.setText(currentWord.getMeaning());

            } else holder.vocabulary.setText(currentWord.getGermanWord());
        });

        holder.sentenceFlip.setOnClickListener(view -> {

            if (holder.exampleSentence.getText().equals(currentWord.getExampleSentence()) && currentWord.getExampleMeaning() != null) {

                holder.exampleSentence.setText(currentWord.getExampleMeaning());

            } else holder.exampleSentence.setText(currentWord.getExampleSentence());
        });


        holder.check.setOnClickListener(view -> {

            currentWord.setKnown(true);
            currentWord.setUnlearned(false);
            notifyItemChanged(position);

            if (position < wordList.size() - 1) {

                recyclerView.smoothScrollToPosition(position + 1);

            } else if (position == wordList.size() - 1) {

                if (listener != null) {

                    listener.onLastCardReached();
                }

            }  saveWordsToDatabase(currentWord);

        });

        holder.cancel.setOnClickListener(view -> {

            currentWord.setUnlearned(true);
            currentWord.setKnown(false);
            notifyItemChanged(position);

            if (position < wordList.size() - 1) {

                recyclerView.smoothScrollToPosition(position + 1);

            }

            else if (position == wordList.size() - 1) {

                if (listener != null) {

                    listener.onLastCardReached();
                }

            } saveWordsToDatabase(currentWord);

        });
    }

    private void saveWordsToDatabase(Word word) {

        new Thread(() -> {
            try {
                AppDatabase db = AppDatabase.getInstance(context);
                WordDao wordDao = db.wordDao();

                wordDao.insert(word);

            } catch (Exception e) {
                Log.e("DB","Failed to insert word",e);
            }
        }).start();
    }

    @Override
    public int getItemCount() {

        return wordList.size();
    }

    public static class FlashcardViewHolder extends RecyclerView.ViewHolder {
        TextView wordCount;
        TextView vocabulary;
        TextView exampleSentence;
        ImageView vocabFlip;
        ImageView sentenceFlip;
        ImageView check;
        ImageView cancel;

        public FlashcardViewHolder(@NonNull View itemView) {

            super(itemView);

            wordCount = itemView.findViewById(R.id.wordCount);
            vocabulary = itemView.findViewById(R.id.vocabulary);
            exampleSentence = itemView.findViewById(R.id.exampleSentence);
            vocabFlip = itemView.findViewById(R.id.vocabFlip);
            sentenceFlip = itemView.findViewById(R.id.sentenceFlip);
            check = itemView.findViewById(R.id.check);
            cancel = itemView.findViewById(R.id.cancel);
        }
    }

}
