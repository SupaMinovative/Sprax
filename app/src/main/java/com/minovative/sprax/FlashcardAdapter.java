package com.minovative.sprax;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

public class FlashcardAdapter extends RecyclerView.Adapter<FlashcardAdapter.FlashcardViewHolder> {



    private List<Word> wordList;
    private List<Word> unlearnedWord = new ArrayList<>();
    private RecyclerView recyclerView;

    Context context;

    public FlashcardAdapter(List<Word> wordList, RecyclerView recyclerView) {
        this.wordList = wordList;
        this.recyclerView = recyclerView;


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


        context = recyclerView.getContext();
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
            notifyItemChanged(position);
            if (position < wordList.size() - 1) {
                notifyItemChanged(position + 1);
                recyclerView.smoothScrollToPosition(position + 1);
            }
            saveWordsToDatabase(currentWord);
        });

        holder.cancel.setOnClickListener(view -> {
            currentWord.setUnlearned(true);
            unlearnedWord.add(currentWord);
            notifyItemChanged(position);
            if (position < wordList.size() - 1) {
                notifyItemChanged(position + 1);
                recyclerView.smoothScrollToPosition(position + 1);
            }
        });

//        if(position == wordList.size()-1) {
//
//            reinsertWord();
//        }
//    };
//        private void reinsertWord(){
//            int currentSize = wordList.size();
//            wordList.addAll(unlearnedWord);
//            notifyItemRangeInserted(currentSize, unlearnedWord.size());
//            recyclerView.smoothScrollToPosition(currentSize);
//        }


//    private void displayUnlearnedWord() {
//
//        new Thread(() -> {
//            int count;
//            AppDatabase db = AppDatabase.getInstance(context);
//            WordDao wordDao = db.wordDao();
//
//
//            List<Word> words = wordDao.getUnlearnedWords();
//            activity.runOnUiThread(() -> {
//                wordList.clear();
//                wordList.addAll(words);
//                notifyDataSetChanged();
//            });
//
//        }).start();
//    }
    private void saveWordsToDatabase(Word word) {

            new Thread(() -> {
                AppDatabase db = AppDatabase.getInstance(context);
                WordDao wordDao = db.wordDao();
                wordDao.insert(word);
            }).start();
        };


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
        ImageView audio;
        ImageView check;
        ImageView cancel;


        public FlashcardViewHolder(@NonNull View itemView) {
            super(itemView);

            wordCount = itemView.findViewById(R.id.wordCount);
            vocabulary = itemView.findViewById(R.id.vocabulary);
            exampleSentence = itemView.findViewById(R.id.exampleSentence);
            vocabFlip = itemView.findViewById(R.id.vocabFlip);
            sentenceFlip = itemView.findViewById(R.id.sentenceFlip);
            audio = itemView.findViewById(R.id.audioButton);
            check = itemView.findViewById(R.id.check);
            cancel = itemView.findViewById(R.id.cancel);
        }

    }

}
