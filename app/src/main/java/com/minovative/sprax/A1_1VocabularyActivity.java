package com.minovative.sprax;

import android.content.Context;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class A1_1VocabularyActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FlashcardAdapter flashcardAdapter;
    List<Word> wordList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_a11_vocabulary);


        recyclerView = findViewById(R.id.recyclerView);
        flashcardAdapter = new FlashcardAdapter(wordList, recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(flashcardAdapter);



//        wordList.add( new Word("die Milch", "Milk", "Ich trinke jeden Morgen ein Glas Milch.", "I drink a glass of milk every morning."));
        wordList.add( new Word("der Apfel", "Apple", "Der Apfel ist rot.", "The apple is red."));
        wordList.add(new Word("die Katze", "Cat", "Die Katze schläft auf dem Sofa.", "The cat is sleeping on the sofa."));
        wordList.add(new Word("der Hund", "Dog", "Der Hund läuft im Park.", "The dog is running in the park."));
        flashcardAdapter.notifyDataSetChanged();


    }



        }