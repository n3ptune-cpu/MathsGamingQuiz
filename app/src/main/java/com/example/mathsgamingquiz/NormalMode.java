package com.example.mathsgamingquiz;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NormalMode extends AppCompatActivity {


    private TextView txtQN2, txtScore2, txtQuestion2, txtTimer;
    private EditText etAns2;
    private Button btnSubmit2;


    private int score, questionNumber;
    private String question;
    private int answer;


    private MediaPlayer mPlayer1, mPlayer2;


    private CountDownTimer questionTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            getSupportActionBar().hide();
        } catch (NullPointerException ignored) {}

        setContentView(R.layout.activity_normal_mode);

        txtQN2      = findViewById(R.id.txtQN2);
        txtQuestion2= findViewById(R.id.txtQuestion2);
        txtScore2   = findViewById(R.id.txtScore2);
        txtTimer    = findViewById(R.id.txtTimer);
        etAns2      = findViewById(R.id.etAns2);
        btnSubmit2  = findViewById(R.id.btnSubmit2);


        mPlayer1 = MediaPlayer.create(this, R.raw.correct);
        mPlayer2 = MediaPlayer.create(this, R.raw.wrong);


        score = 0;
        questionNumber = 1;
        setQuestion();


        btnSubmit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (questionTimer != null) questionTimer.cancel();


                checkAnswer();


                questionNumber++;
                if (questionNumber <= 10) {
                    setQuestion();        // automatically restarts 20 s timer
                } else {
                    endQuiz();
                }
            }
        });
    }

    private int getRandomNumber(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }

    private void setQuestion() {


        txtQN2.setText("Question: " + questionNumber + " / 10");
        txtScore2.setText("Score: " + score);


        generateRandomQuestion();
        txtQuestion2.setText(question);


        if (questionTimer != null) questionTimer.cancel();

        questionTimer = new CountDownTimer(20_000, 1_000) {
            @Override
            public void onTick(long millisUntilFinished) {
                txtTimer.setText("Time: " + millisUntilFinished / 1_000 + "s");
            }

            @Override
            public void onFinish() {
                // treat timeout like wrong answer
                mPlayer2.start();
                etAns2.setText("");

                questionNumber++;
                if (questionNumber <= 10) {
                    setQuestion();        // next Q, new timer
                } else {
                    endQuiz();
                }
            }
        }.start();
    }

    private void checkAnswer() {
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

    private void generateRandomQuestion() {
        boolean isMultiplication = Math.random() < 0.5;

        int num1 = getRandomNumber(1, 12);
        int num2 = getRandomNumber(1, 12);

        if (isMultiplication) {
            question = num1 + " × " + num2 + " = ?";
            answer   = num1 * num2;
        } else {
            answer   = getRandomNumber(1, 12);   // ensure clean division
            num2     = getRandomNumber(1, 12);
            num1     = answer * num2;
            question = num1 + " ÷ " + num2 + " = ?";
        }
    }

    /** Finish the quiz and go to the results screen */
    private void endQuiz() {
        Intent intent = new Intent(getApplicationContext(), Final.class);
        intent.putExtra("score", Integer.toString(score));
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (questionTimer != null) questionTimer.cancel();
        if (mPlayer1 != null) mPlayer1.release();
        if (mPlayer2 != null) mPlayer2.release();
    }
}
