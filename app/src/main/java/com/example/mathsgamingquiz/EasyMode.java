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

    TextView txtQN, txtScore, txtQuestion;
    EditText etAns;
    Button btnSubmit;
    int score, questionNumber;

    String question;
    int answer;

    MediaPlayer mPlayer1, mPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {}

        setContentView(R.layout.activity_easy_mode);

        txtQN = findViewById(R.id.txtQN);
        txtQuestion = findViewById(R.id.txtQuestion);
        txtScore = findViewById(R.id.txtScore);
        etAns = findViewById(R.id.etAns);
        btnSubmit = findViewById(R.id.btnSubmit);

        mPlayer1 = MediaPlayer.create(this, R.raw.correct);
        mPlayer2 = MediaPlayer.create(this, R.raw.wrong);

        questionNumber = 1;
        score = 0;

        setQuestion();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = etAns.getText().toString().trim();
                checkAnswer();

                questionNumber++;
                if (questionNumber <= 10) {
                    setQuestion();
                } else {
                    Intent intent = new Intent(getApplicationContext(), Final.class);
                    intent.putExtra("score", Integer.toString(score));
                    startActivity(intent);
                }
            }
        });
    }

    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    private void setQuestion() {
        txtQN.setText("Question: " + questionNumber + " / 10");
        txtScore.setText("Score: " + score);

        generateRandomQuestion();
        txtQuestion.setText(question);
    }

    private void checkAnswer() {
        int attempt;
        try {
            attempt = Integer.parseInt(etAns.getText().toString().trim());
        } catch (NumberFormatException e) {
            mPlayer2.start();
            etAns.setText("");
            return;
        }

        if (attempt == answer) {
            score++;
            mPlayer1.start();
        } else {
            mPlayer2.start();
        }

        etAns.setText("");
        txtScore.setText("Score: " + score);
    }

    private void generateRandomQuestion() {
        int num1 = getRandomNumber(1, 20);
        int num2 = getRandomNumber(1, 20);

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
}
