package com.minovative.sprax;

import static com.minovative.sprax.MethodHelper.loadWordFromDB;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class LearnedWordsActivity extends AppCompatActivity {

    private ListView wordsListView;
    private Button backButton;
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
        loadWordFromDB(this,wordsListView,wordCountDisplay);

    }
}