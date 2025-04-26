package com.minovative.sprax;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle; 
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity {

    TextView a1Part1;
    TextView a1Part2;
    TextView a1Part3;
    TextView a1Part4;
    TextView a1Part5;
    TextView a1Part6;
    TextView a2Part1;
    TextView a2Part2;
    TextView a2Part3;
    TextView a2Part4;
    TextView b1Part1;
    TextView b1Part2;
    Toolbar toolbar;
    private DrawerLayout drawerLayout;

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


        a1Part1 = findViewById(R.id.a1Part1);

        startVocabActivity(a1Part1, "A1_1VocabActivity");

        a1Part2 = findViewById(R.id.a1Part2);

        startVocabActivity(a1Part2, "A1_2VocabActivity");

        a1Part3 = findViewById(R.id.a1Part3);

        startVocabActivity(a1Part3, "A1_3VocabActivity");

        a1Part4 = findViewById(R.id.a1Part4);

        startVocabActivity(a1Part4, "A1_4VocabActivity");

        a1Part5 = findViewById(R.id.a1Part5);

        startVocabActivity(a1Part5, "A1_5VocabActivity");

        a1Part6 = findViewById(R.id.a1Part6);

        startVocabActivity(a1Part6, "A1_6VocabActivity");

        a2Part1 = findViewById(R.id.a2Part1);

        startVocabActivity(a2Part1, "A2_1VocabActivity");

        a2Part2 = findViewById(R.id.a2Part2);

        startVocabActivity(a2Part2, "A2_2VocabActivity");

        a2Part3 = findViewById(R.id.a2Part3);

        startVocabActivity(a2Part3, "A2_3VocabActivity");
        a2Part4 = findViewById(R.id.a2Part4);

        startVocabActivity(a2Part4, "A2_4VocabActivity");
        b1Part1 = findViewById(R.id.b1Part1);

        startVocabActivity(b1Part1, "B1_1VocabActivity");
        b1Part2 = findViewById(R.id.b1Part2);

        startVocabActivity(b1Part2, "B1_2VocabActivity");

    }

    private void startVocabActivity(TextView activityId, String activityClassName) {

        activityId.setOnClickListener(view -> {

            Class<?> activityClass = null;

            try {

                activityClass = Class.forName("com.minovative.sprax." + activityClassName);

            } catch (ClassNotFoundException e) {

                throw new RuntimeException(e);
            }

            Intent intent = new Intent(MainActivity.this, activityClass);
            startActivity(intent);
        });
    }
}