package com.minovative.sprax;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.List;

public class WordListAdapter extends ArrayAdapter<Word> {

    private Context context;
    private List<Word> learnedWordsList;

    public WordListAdapter(Context context, List<Word> learnedWordsList) {

        super(context, R.layout.words_list,learnedWordsList );
        this.context = context;
        this.learnedWordsList = learnedWordsList;
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {

        if (converView == null) {

            LayoutInflater inflater = LayoutInflater.from(context);
            converView = inflater.inflate(R.layout.words_list, parent, false);
        }

        TextView wordTextView = converView.findViewById(R.id.wordTextView);
        TextView meaningTextView = converView.findViewById(R.id.meaningTextView);

        Word currentWord = learnedWordsList.get(position);

        wordTextView.setText(currentWord.getGermanWord());
        meaningTextView.setText(currentWord.getMeaning());

        return converView;
    }
}
