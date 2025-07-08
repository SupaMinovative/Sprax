package com.minovative.sprax;

import static com.minovative.sprax.MethodHelper.loadJsonAndInsert;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class B1_1VocabActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FlashcardAdapter flashcardAdapter;
    private List<Word> wordList = new ArrayList<>();
    private String activityName;
    private AppDatabase db;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_a11_vocab);

        activityName = this.getLocalClassName();
        recyclerView = findViewById(R.id.recyclerView);

        flashcardAdapter = new FlashcardAdapter(wordList, recyclerView, this, () -> {
            Intent intent = new Intent(this, SummaryActivity.class);
            intent.putExtra("ACTIVITY_NAME", activityName);
            startActivity(intent);
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setOnTouchListener((v, event) -> true);
        recyclerView.setAdapter(flashcardAdapter);

        loadJsonAndInsert(this, this, activityName, db, wordList, flashcardAdapter);
    }

    @Override
    public void onDestroy(){

        super.onDestroy();

        if (db != null && db.isOpen()) {
            db.close();
        }
    }
}