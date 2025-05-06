package com.example.mathsgamingquiz;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Timer;
import java.util.TimerTask;
import android.media.MediaPlayer;

public class Final extends AppCompatActivity {

    TextView txtFinalScore;
    MediaPlayer mPlayer1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_final);
        mPlayer1 = MediaPlayer.create(this, R.raw.finish);
        txtFinalScore = (TextView) findViewById(R.id.txtFinalScore);

        Intent intent = getIntent();
        String finalScore = intent.getStringExtra("score").toString();
        txtFinalScore.setText("Final Score: " + finalScore);
        mPlayer1.start();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        }, 5000);

    }
}