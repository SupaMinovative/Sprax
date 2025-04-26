package com.minovative.sprax;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SummaryAdapter extends RecyclerView.Adapter<SummaryAdapter.ViewHolder> {

    private List<Word> wordList;

    public SummaryAdapter(List<Word> wordList) {
        this.wordList = wordList;
    }

    @NonNull
    @Override
    public SummaryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.summary_word_item, parent, false);

        return new SummaryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Word currentWord = wordList.get(position);

        holder.summaryText.setText(currentWord.getGermanWord() + " - " + currentWord.getMeaning());

    }

    @Override
    public int getItemCount() {
        return wordList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView summaryText;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            summaryText = itemView.findViewById(R.id.summaryText);
        }
    }
}
