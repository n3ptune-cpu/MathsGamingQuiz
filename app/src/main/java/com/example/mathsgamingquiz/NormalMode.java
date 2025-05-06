package com.example.mathsgamingquiz;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NormalMode extends AppCompatActivity {

    TextView txtQN2, txtScore2, txtQuestion2, txtTimer;
    EditText etAns2;
    Button btnSubmit2;
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

        setContentView(R.layout.activity_normal_mode);

        // Initialize views
        txtQN2 = findViewById(R.id.txtQN2);
        txtQuestion2 = findViewById(R.id.txtQuestion2);
        txtScore2 = findViewById(R.id.txtScore2);
        etAns2 = findViewById(R.id.etAns2);
        btnSubmit2 = findViewById(R.id.btnSubmit2);
        txtTimer = findViewById(R.id.txtTimer);

        // Initialize MediaPlayer
        mPlayer1 = MediaPlayer.create(this, R.raw.correct);
        mPlayer2= MediaPlayer.create(this, R.raw.wrong);

        // Initialize game variables
        questionNumber = 1;
        score = 0;

        // Set the first question
        setQuestion();

        // Set the 40-second timer
        new android.os.CountDownTimer(40000, 1000) {
            public void onTick(long millisUntilFinished) {
                txtTimer.setText("Time: " + millisUntilFinished / 1000 + "s");
            }

            public void onFinish() {
                Intent intent = new Intent(getApplicationContext(), Final.class);
                intent.putExtra("score", Integer.toString(score));
                startActivity(intent);
                finish();
            }
        }.start();

        // Submit answer when button is clicked
        btnSubmit2.setOnClickListener(new View.OnClickListener() {
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
                }
            }
        });
    }

    private int getRandomNumber(int min, int max){
        return (int)((Math.random() * (max - min)) + min);
    }

    private void setQuestion(){
        txtQN2.setText("Question: " + questionNumber + " / 10");
        txtScore2.setText("Score: " + score);

        generateRandomQuestion();
        txtQuestion2.setText(question);
    }

    private void checkAnswer(){
        String input = etAns2.getText().toString().trim();

        if (input.isEmpty()) {
            mPlayer2.start();
            etAns2.setText("");
            return;
        }

        int attempt;
        try {
            attempt = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            mPlayer2.start();
            etAns2.setText("");
            return;
        }

        if (attempt == answer) {
            score++;
            txtScore2.setText("Score: " + score);
            mPlayer1.start();
        } else {
            mPlayer2.start();
        }

        etAns2.setText("");
    }

    private void generateRandomQuestion(){
        boolean isMultiplication = Math.random() < 0.5;

        int num1 = getRandomNumber(1, 12);
        int num2 = getRandomNumber(1, 12);

        if (isMultiplication) {
            question = num1 + " × " + num2 + " = ?";
            answer = num1 * num2;
        } else {
            answer = getRandomNumber(1, 12);
            num2 = getRandomNumber(1, 12);
            num1 = answer * num2;
            question = num1 + " ÷ " + num2 + " = ?";
        }
    }
}
