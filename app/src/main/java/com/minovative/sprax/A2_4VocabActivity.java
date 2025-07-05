package com.minovative.sprax;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class A2_4VocabActivity extends AppCompatActivity implements FlashcardAdapter.OnFlashcardCompletionListener {

    private RecyclerView recyclerView;
    private FlashcardAdapter flashcardAdapter;
    private List<Word> wordList = new ArrayList<>();
    private String activityName;
    private AppDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_a11_vocab);

        activityName = this.getLocalClassName();
        recyclerView = findViewById(R.id.recyclerView);

        flashcardAdapter = new FlashcardAdapter(wordList, recyclerView, this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(flashcardAdapter);
        loadJsonAndInsert();

    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadJsonAndInsert() {

        new Thread(() -> {

            String jsonString = null;
            try {
                jsonString = JsonUtils.AssetJSONFile(this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Gson gson = new Gson();
            Type wordListType = new TypeToken<List<Word>>() {
            }.getType();
            List<Word> loadedWord = gson.fromJson(jsonString, wordListType);

            db = AppDatabase.getInstance(this);
            WordDao wordDao = db.wordDao();
            List<Word> unlearnedWord;
            List<Word> learnedWords = wordDao.getKnownWords(activityName);
            List<Word> allWords = wordDao.getAllWords(activityName);
            List<Word> wordsToInsert = new ArrayList<>();

            for (Word word : loadedWord) {
                boolean alreadyExists = false;
                for (Word existingWord : allWords) {
                    if (existingWord.getGermanWord().equals(word.getGermanWord())) {
                        alreadyExists = true;
                        break;
                    }
                }
                if (!alreadyExists) {
                    wordsToInsert.add(word);
                }
            }
            if (!wordsToInsert.isEmpty()) {
                wordDao.insertAll(wordsToInsert);
            }

            unlearnedWord = wordDao.getUnlearnedWords(activityName);
            if (!unlearnedWord.isEmpty()) {
                runOnUiThread(() -> {
                    wordList.clear();
                    wordList.addAll(unlearnedWord);
                    flashcardAdapter.notifyDataSetChanged();
                });
            } else if (learnedWords.size() == allWords.size()) {
                Intent intent = new Intent(this, SummaryActivity.class);
                intent.putExtra("ACTIVITY_NAME", activityName);
                startActivity(intent);
            }

        }).start();
    }

    @Override
    public void onLastCardReached() {

        Intent intent = new Intent(this, SummaryActivity.class);
        intent.putExtra("ACTIVITY_NAME", activityName);
        startActivity(intent);
    }

    @Override
    public void onDestroy(){

        super.onDestroy();

        if (db != null && db.isOpen()) {
            db.close();
        }
    }
}