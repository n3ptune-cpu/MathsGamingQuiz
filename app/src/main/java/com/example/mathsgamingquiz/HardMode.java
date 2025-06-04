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

public class HardMode extends AppCompatActivity {

    // UI components
    private TextView txtQN3, txtScore3, txtQuestion3, txtTimer2;
    private EditText etAns3;
    private Button btnSubmit3;

    // Game state
    private int score, questionNumber;
    private String question;
    private int answer;

    // Media
    private MediaPlayer mPlayer1, mPlayer2;

    // Timer
    private CountDownTimer questionTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            getSupportActionBar().hide();
        } catch (NullPointerException ignored) {}

        setContentView(R.layout.activity_hard_mode);

        // Initialize UI
        txtQN3       = findViewById(R.id.txtQN3);
        txtQuestion3 = findViewById(R.id.txtQuestion3);
        txtScore3    = findViewById(R.id.txtScore3);
        txtTimer2    = findViewById(R.id.txtTimer2);
        etAns3       = findViewById(R.id.etAns3);
        btnSubmit3   = findViewById(R.id.btnSubmit3);

        // Initialize media
        mPlayer1 = MediaPlayer.create(this, R.raw.correct);
        mPlayer2 = MediaPlayer.create(this, R.raw.wrong);

        // Initialize game
        score = 0;
        questionNumber = 1;
        setQuestion();  // show first Q & start timer

        // Submit button
        btnSubmit3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (questionTimer != null) questionTimer.cancel();
                checkAnswer();

                questionNumber++;
                if (questionNumber <= 10) {
                    setQuestion();
                } else {
                    endQuiz();
                }
            }
        });
    }

    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min + 1)) + min);
    }

    private void setQuestion() {
        txtQN3.setText("Question: " + questionNumber + " / 10");
        txtScore3.setText("Score: " + score);

        generateRandomQuestion();
        txtQuestion3.setText(question);

        // Cancel old timer and start new 10-second one
        if (questionTimer != null) questionTimer.cancel();

        questionTimer = new CountDownTimer(10_000, 1_000) {
            @Override
            public void onTick(long millisUntilFinished) {
                txtTimer2.setText("Time: " + millisUntilFinished / 1_000 + "s");
            }

            @Override
            public void onFinish() {
                // Time's up - count as wrong
                mPlayer2.start();
                etAns3.setText("");

                questionNumber++;
                if (questionNumber <= 10) {
                    setQuestion();
                } else {
                    endQuiz();
                }
            }
        }.start();
    }

    private void checkAnswer() {
        String input = etAns3.getText().toString().trim();

        if (input.isEmpty()) {
            mPlayer2.start();
            etAns3.setText("");
            return;
        }

        int attempt;
        try {
            attempt = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            mPlayer2.start();
            etAns3.setText("");
            return;
        }

        if (attempt == answer) {
            score++;
            txtScore3.setText("Score: " + score);
            mPlayer1.start();
        } else {
            mPlayer2.start();
        }

        etAns3.setText("");
    }

    private void generateRandomQuestion() {
        int operation = getRandomNumber(1, 4);
        int num1, num2;

        switch (operation) {
            case 1: // Addition
                num1 = getRandomNumber(1, 12);
                num2 = getRandomNumber(1, 12);
                question = num1 + " + " + num2 + " = ?";
                answer = num1 + num2;
                break;

            case 2: // Subtraction
                num1 = getRandomNumber(1, 12);
                num2 = getRandomNumber(1, 12);
                if (num2 > num1) { int temp = num1; num1 = num2; num2 = temp; }
                question = num1 + " - " + num2 + " = ?";
                answer = num1 - num2;
                break;

            case 3: // Multiplication
                num1 = getRandomNumber(1, 12);
                num2 = getRandomNumber(1, 12);
                question = num1 + " × " + num2 + " = ?";
                answer = num1 * num2;
                break;

            case 4: // Division
                answer = getRandomNumber(1, 12);
                num2 = getRandomNumber(1, 12);
                num1 = answer * num2;
                question = num1 + " ÷ " + num2 + " = ?";
                break;
        }
    }

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
