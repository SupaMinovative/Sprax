package com.minovative.sprax;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private LinearLayout wordContainer;
    private TextView result;
    private TextView scoreText;
    private TextView scoreTextWrong;
    private Button checkCorrect;
    private Button restartButton;
    private Button backMainButton;
    private List<Word> wordList = new ArrayList<>();
    private List<Word> wordToGuess = new ArrayList<>();
    private ArrayList<EditText> editTextList = new ArrayList<>();
    private Word currentWord;
    private int wordCounter = 0;
    private boolean correct;
    private GuessingWord guessingWord;
    private int scoreCount;
    private int wrongChoice;


    @SuppressLint("SuspiciousIndentation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz);

        wordContainer = findViewById(R.id.wordContainer);
        checkCorrect = findViewById(R.id.checkCorrect);
        result = findViewById(R.id.result);
        scoreText = findViewById(R.id.scoreText);
        scoreTextWrong = findViewById(R.id.scoreTextWrong);
        restartButton = findViewById(R.id.restartBtn);
        backMainButton = findViewById(R.id.backMain);

        startGame();

        backMainButton.setOnClickListener(view -> {

            Intent intent = new Intent(QuizActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void showGuessUI(GuessingWord word) {

        String masked = word.maskedOriginalWord();
        TextView showHintWord = findViewById(R.id.showHintWord);

        showHintWord.setText("💡 Hint: " + currentWord.getMeaning());

        for (int i = 0; i < masked.length(); i++) {

            char c = masked.charAt(i);
            if (c == '_') {

                EditText editText = new EditText(this);
                editText.setWidth(125);
                editText.setGravity(Gravity.CENTER);
                editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
                editText.setPadding(5, 5, 5, 5);
                editText.setTextSize(40);
                editText.setInputType(InputType.TYPE_CLASS_TEXT);
                editTextList.add(editText);
                wordContainer.addView(editText);

                int currentIndex = editTextList.size() - 1;

                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

                        if (charSequence.length() == 1) {

                            if (currentIndex + 1 < editTextList.size()) {

                                editTextList.get(currentIndex + 1).requestFocus();
                            }
                        }
                        editText.setOnKeyListener(( v,keyCode,event ) -> {

                            if (keyCode == KeyEvent.KEYCODE_DEL && editText.getText().length() == 0) {

                                if (currentIndex - 1 >= 0) {

                                    editTextList.get(currentIndex - 1).requestFocus();
                                }
                            } return false;
                        });
                    }

                    @Override
                    public void afterTextChanged( Editable editable ) {

                    }
                });

            } else {

                TextView textView = new TextView(this);
                textView.setText(String.valueOf(c));
                textView.setPadding(5, 5, 5, 5);
                textView.setTextSize(40);
                wordContainer.addView(textView);
            }
        }

        checkCorrect.setOnClickListener(null);
        checkCorrect.setOnClickListener(view -> {

            List<String> userInput = new ArrayList<>();
            for (EditText et : editTextList) {
                userInput.add(et.getText().toString());
            }

            correct = word.isGuessCorrect(userInput);

            if (correct) {

                scoreCount += 1;
                result.setTextColor(getResources().getColor(R.color.green));
                result.setText("✓ Correct!");
                scoreText.setText(scoreCount + " 😀");
            }

            if (!correct) {

                wrongChoice += 1;
                result.setTextColor(getResources().getColor(R.color.red));
                result.setText("✗ Incorrect");
                scoreTextWrong.setText(wrongChoice + " 🥲");
            }

            wordCounter++;

            new android.os.Handler().postDelayed(() -> {
                if (wordCounter < wordToGuess.size()) {

                    currentWord = wordToGuess.get(wordCounter);
                    guessingWord = new GuessingWord(currentWord.getGermanWord().substring(4));

                    showNextWord();
                    showGuessUI(guessingWord);

                } else {

                    showNextWord();
                    showHintWord.setText("");
                    checkCorrect.setVisibility(View.INVISIBLE);
                    result.setText("You got " + (scoreCount) + " out of " + wordToGuess.size());
                    restartButton.setVisibility(View.VISIBLE);

                    restartButton.setOnClickListener(view1 -> {
                        scoreText.setText("");
                        scoreTextWrong.setText("");
                        showNextWord();
                        startGame();
                    });
                }

            }, 1500);
        });
    }

    private void showNextWord() {

        wordContainer.removeAllViews();
        editTextList.clear();
        result.setText("");
    }

    private void startGame() {

        checkCorrect.setVisibility(View.VISIBLE);
        restartButton.setVisibility(View.GONE);

        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(this);
            WordDao wordDao = db.wordDao();

            wordList = wordDao.getAllKnownWords();

            if (wordList.isEmpty()) {

                runOnUiThread(() -> {

                    result.setText("You haven't learned any words yet 🥲 \n\n Please visit a lesson to collect some!");
                    checkCorrect.setVisibility(View.INVISIBLE);
                });

            } else {

                Collections.shuffle(wordList);
                int maxIndex = Math.min(15, wordList.size());

                for (int i = 0; i < maxIndex; i++) {
                    wordToGuess.add(wordList.get(i));
                }

                currentWord = wordToGuess.get(wordCounter);
                guessingWord = new GuessingWord(currentWord.getGermanWord().substring(4));

                if (!isFinishing() && !isDestroyed()) {

                    runOnUiThread(() ->
                            showGuessUI(guessingWord));
                }
            }
        }).start();
    }
}


