package com.example.mathsgamingquiz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Timer;
import java.util.TimerTask;
import android.media.MediaPlayer;

public class Final extends AppCompatActivity {

    TextView txtFinalScore, txtFinal;
    MediaPlayer mPlayer1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {}

        setContentView(R.layout.activity_final);

        mPlayer1 = MediaPlayer.create(this, R.raw.finish);

        txtFinalScore = findViewById(R.id.txtFinalScore);
        txtFinal = findViewById(R.id.txtFinal);  // Make sure this TextView exists in activity_final.xml

        Intent intent = getIntent();
        String finalScoreStr = intent.getStringExtra("score");
        int finalScore = Integer.parseInt(finalScoreStr);

        txtFinalScore.setText("Final Score: " + finalScore);

        if (finalScore <= 4) {
            txtFinal.setText("Good try! You can do better next time.");
        } else if (finalScore <= 8) {
            txtFinal.setText("You're close but not quite!");
        } else {
            txtFinal.setText("Congrats, you did it!!");
        }

        mPlayer1.start();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        }, 5000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPlayer1 != null) mPlayer1.release();
    }
}
