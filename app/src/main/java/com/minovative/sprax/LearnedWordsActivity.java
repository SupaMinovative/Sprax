package com.minovative.sprax;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class LearnedWordsActivity extends AppCompatActivity {

    private ListView wordsListView;
    private WordListAdapter adapter;
    private Button backButton;
    private int wordsCount;
    private TextView wordCountDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_learned_words);

        wordsListView = findViewById(R.id.wordsListView);
        backButton = findViewById(R.id.backBtn);
        wordCountDisplay = findViewById(R.id.wordCountDisplay);

        backButton.setOnClickListener(view -> {

            onBackPressed();
        });


        new Thread(() -> {

            AppDatabase db = AppDatabase.getInstance(this);
            WordDao wordDao = db.wordDao();
            List<Word> learnedWordsList = wordDao.getAllKnownWords();

            runOnUiThread(() -> {

                adapter = new WordListAdapter(this, learnedWordsList);
                wordsListView.setAdapter(adapter);
                wordsCount = learnedWordsList.size();

                if (wordsCount > 0) {

                    wordCountDisplay.setText("You know " + wordsCount + " words");

                } else if (wordsCount == 0) {

                    wordCountDisplay.setText("You haven't learned anything yet!");

                }
            });
        }).start();

    }
}