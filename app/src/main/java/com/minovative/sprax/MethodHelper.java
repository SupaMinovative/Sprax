package com.minovative.sprax;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MethodHelper {

    @SuppressLint("NotifyDataSetChanged")
    public static void loadJsonAndInsert(Activity activity,Context context,String activityName,AppDatabase db,
                                         List<Word> wordList,FlashcardAdapter adapter){
        db = AppDatabase.getInstance(context.getApplicationContext());
        WordDao wordDao = db.wordDao();

        new Thread(() -> {

            String jsonString = null;
            try {
                jsonString = JsonUtils.AssetJSONFile(context.getApplicationContext());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Gson gson = new Gson();
            Type wordListType = new TypeToken<List<Word>>() {
            }.getType();
            List<Word> loadedWord = gson.fromJson(jsonString, wordListType);


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
                activity.runOnUiThread(() -> {
                    wordList.clear();
                    wordList.addAll(unlearnedWord);
                    adapter.notifyDataSetChanged();
                });
            } else if (learnedWords.size() == allWords.size()) {
                Intent intent = new Intent(context, SummaryActivity.class);
                intent.putExtra("ACTIVITY_NAME", activityName);
                context.startActivity(intent);
            }

        }).start();
    }

    public static void loadWordFromDB(Activity activity,ListView wListView,TextView wordDisplay) {

        new Thread(() -> {

            AppDatabase db = AppDatabase.getInstance(activity.getApplicationContext());
            WordDao wordDao = db.wordDao();
            List<Word> learnedWordsList = wordDao.getAllKnownWords();

            activity.runOnUiThread(() -> {
               WordListAdapter adapter = new WordListAdapter(activity.getApplicationContext(), learnedWordsList);
                wListView.setAdapter(adapter);
                int wordsCount = learnedWordsList.size();

                if (wordsCount > 0) {

                    wordDisplay.setText("You know " + wordsCount + " words");

                } else if (wordsCount == 0) {

                    wordDisplay.setText("You haven't learned anything yet!");

                }
            });
        }).start();
    }

    public static void startVocabActivity(ImageView item,String activityClassName,Context activityName) {

        item.setOnClickListener(view -> {

            Class<?> activityClass = null;

            try {
                activityClass = Class.forName("com.minovative.sprax." + activityClassName);

            } catch (ClassNotFoundException e) {

                throw new RuntimeException(e);
            }

            Intent intent = new Intent(activityName, activityClass);
            activityName.startActivity(intent);
        });
    }

    public static void shakeButton(View view) {
        Animation shake = new TranslateAnimation(-300,120,0,0);
        shake.setDuration(15_000);

        shake.setInterpolator(new CycleInterpolator(5));
        shake.setRepeatMode(Animation.RESTART);
        shake.setRepeatCount(Animation.INFINITE);
        view.startAnimation(shake);
    };
}
