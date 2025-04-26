package com.minovative.sprax;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SummaryActivity extends AppCompatActivity{
    private RecyclerView learnedWordRecyclerView;
    private RecyclerView unlearnedWordRecyclerView;
    private SummaryAdapter learnedWordAdapter;
    private SummaryAdapter unlearnedWordAdapter;
    private List<Word> learnedWord = new ArrayList<>();
    private List<Word> unlearnedWord = new ArrayList<>();
    private Button backButton;
    private TextView learnedWordText;
    private TextView unlearnedWordText;
    private String activityName;
    private TextView summaryResult;
    private Button resetButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_summary);

        backButton = findViewById(R.id.buttonBack);

        backButton.setOnClickListener(view ->{

            Intent intent = new Intent(SummaryActivity.this, MainActivity.class);
            startActivity(intent);
        });

        activityName = getIntent().getStringExtra("ACTIVITY_NAME");

        learnedWordText = findViewById(R.id.learnedWord);
        unlearnedWordText = findViewById(R.id.unlearnedWord);
        summaryResult = findViewById(R.id.summaryResult);
        resetButton = findViewById(R.id.resetBtn);

        learnedWordRecyclerView = findViewById(R.id.learnedWordRecyclerView);
        unlearnedWordRecyclerView = findViewById(R.id.unlearnedWordRecyclerView);

        learnedWordAdapter = new SummaryAdapter(learnedWord);
        learnedWordRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        learnedWordRecyclerView.setAdapter(learnedWordAdapter);

        unlearnedWordAdapter = new SummaryAdapter(unlearnedWord);
        unlearnedWordRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        unlearnedWordRecyclerView.setAdapter(unlearnedWordAdapter);

        loadWord();
    }

    private void loadWord() {

        new Thread(() -> {

            AppDatabase db = AppDatabase.getInstance(this);
            WordDao wordDao = db.wordDao();

            List<Word> known = wordDao.getKnownWords(activityName);

            List<Word> unknown = wordDao.getUnlearnedWords(activityName);

            runOnUiThread(() -> {

                learnedWord.addAll(known);
                learnedWordAdapter.notifyDataSetChanged();
                learnedWordText.setText("You have learned " + known.size() + " words");

                unlearnedWord.addAll(unknown);
                unlearnedWordText.setText("You have " + unknown.size() + " words left to learn.");
                unlearnedWordAdapter.notifyDataSetChanged();

                if (unknown.isEmpty()) {

                    unlearnedWordText.setVisibility(View.INVISIBLE);
                    resetButton.setVisibility(View.VISIBLE);
                    summaryResult.setVisibility(View.VISIBLE);
                    summaryResult.setText("Congratulations! You have learned everything here!");

                    resetButton.setOnClickListener(view -> {

                        AlertDialog.Builder alert = new AlertDialog.Builder(SummaryActivity.this);
                        alert.setTitle("Reset lesson ?");
                        alert.setMessage("Do you want to reset the lesson?");

                        alert.setNegativeButton("No",(dialog,i) -> dialog.cancel());

                        alert.setPositiveButton("Yes",(dialog,i) -> {

                            new Thread(() -> {

                                wordDao.clearWords(activityName);

                                try {

                                    Class<?> activityClass = Class.forName("com.minovative.sprax." + activityName);
                                    Intent intent = new Intent(SummaryActivity.this,activityClass);
                                    startActivity(intent);

                                } catch (ClassNotFoundException e) {

                                    throw new RuntimeException(e);
                                }
                            }).start();
                        });

                        alert.show();

                    });
                }

            });
        }).start();
    }
}