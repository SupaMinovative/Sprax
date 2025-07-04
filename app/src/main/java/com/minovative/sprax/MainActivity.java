package com.minovative.sprax;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private RecyclerView recyclerView;
    private LevelCardAdapter levelCardAdapter;
    private ImageView swipeBtn;
    private List<Level> levelList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.navigation_view);
        Drawable navIcon = ContextCompat.getDrawable(this, R.drawable.ic_menu);
        assert navIcon != null;
        navIcon.setTint(ContextCompat.getColor(this, R.color.white));

        toolbar.setNavigationIcon(navIcon);

        toolbar.setNavigationOnClickListener(view -> {

            drawerLayout.openDrawer(GravityCompat.START);
        });

        navigationView.setNavigationItemSelectedListener(item -> {

            if (item.getItemId() == R.id.menu_item_1) {
                Intent intent = new Intent(MainActivity.this, LearnedWordsActivity.class);
                startActivity(intent);
            }

            if (item.getItemId() == R.id.menu_item_2) {

                Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                startActivity(intent);
            }
            return true;
        });

        swipeBtn = findViewById(R.id.swipeBtn);
        recyclerView = findViewById(R.id.levelRecyclerView);
        levelCardAdapter = new LevelCardAdapter(levelList, recyclerView, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(levelCardAdapter);

        shakeButton(swipeBtn);

        levelList.add(new Level("A1.1","A1_1VocabActivity", "Beginner"));
        levelList.add(new Level("A1.2", "A1_2VocabActivity", "Beginner"));
        levelList.add(new Level("A1.3", "A1_3VocabActivity", "Beginner"));
        levelList.add(new Level("A1.4", "A1_4VocabActivity", "Beginner"));
        levelList.add(new Level("A1.5", "A1_5VocabActivity", "Beginner"));
        levelList.add(new Level("A1.6", "A1_6VocabActivity", "Beginner"));
        levelList.add(new Level("A2.1", "A2_1VocabActivity", "Beginner"));
        levelList.add(new Level("A2.2", "A2_2VocabActivity", "Beginner"));
        levelList.add(new Level("A2.3", "A2_3VocabActivity", "Beginner"));
        levelList.add(new Level("A2.4", "A2_4VocabActivity", "Beginner"));
        levelList.add(new Level("B1.1", "B1_1VocabActivity", "Beginner"));
        levelList.add(new Level("B1.2", "B1_2VocabActivity", "Beginner"));

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