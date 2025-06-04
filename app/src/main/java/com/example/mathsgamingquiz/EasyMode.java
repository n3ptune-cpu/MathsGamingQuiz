package com.example.mathsgamingquiz;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EasyMode extends AppCompatActivity {

    private TextView txtQN, txtScore, txtQuestion;
    private EditText etAns;
    private Button btnSubmit;

    private int score, questionNumber;
    private String question;
    private int answer;

    private MediaPlayer mPlayer1, mPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            getSupportActionBar().hide();
        } catch (NullPointerException ignored) {}

        setContentView(R.layout.activity_easy_mode);

        // UI setup
        txtQN      = findViewById(R.id.txtQN);
        txtQuestion= findViewById(R.id.txtQuestion);
        txtScore   = findViewById(R.id.txtScore);
        etAns      = findViewById(R.id.etAns);
        btnSubmit  = findViewById(R.id.btnSubmit);

        // Load sounds
        mPlayer1 = MediaPlayer.create(this, R.raw.correct);
        mPlayer2 = MediaPlayer.create(this, R.raw.wrong);

        // Start game
        score = 0;
        questionNumber = 1;

        setQuestion();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer();
                questionNumber++;

                if (questionNumber <= 10) {
                    setQuestion();
                } else {
                    Intent intent = new Intent(getApplicationContext(), Final.class);
                    intent.putExtra("score", Integer.toString(score));
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min + 1)) + min);
    }

    private void setQuestion() {
        txtQN.setText("Question: " + questionNumber + " / 10");
        txtScore.setText("Score: " + score);

        generateRandomQuestion();
        txtQuestion.setText(question);
        etAns.setText(""); // Clear input for next question
    }

    private void checkAnswer() {
        String input = etAns.getText().toString().trim();

        if (input.isEmpty()) {
            mPlayer2.start();
            return;
        }

        int attempt;
        try {
            attempt = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            mPlayer2.start();
            return;
        }

        if (attempt == answer) {
            score++;
            mPlayer1.start();
        } else {
            mPlayer2.start();
        }

        txtScore.setText("Score: " + score);
    }

    private void generateRandomQuestion() {
        int num1 = getRandomNumber(1, 12);
        int num2 = getRandomNumber(1, 12);
        boolean isAddition = Math.random() < 0.5;

        if (isAddition) {
            question = num1 + " + " + num2 + " = ?";
            answer = num1 + num2;
        } else {
            int max = Math.max(num1, num2);
            int min = Math.min(num1, num2);
            question = max + " - " + min + " = ?";
            answer = max - min;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPlayer1 != null) mPlayer1.release();
        if (mPlayer2 != null) mPlayer2.release();
    }
}
