package com.example.mathsgamingquiz;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class DifficultySelection extends AppCompatActivity {

    Button btnEasy;
    Button btnNormal;
    Button btnHard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_difficulty_selection);

        btnEasy = (Button) findViewById(R.id.btnEasy);
        btnEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EasyMode.class);
                startActivity(intent);
                finish();
            }
        });

        btnNormal = (Button) findViewById(R.id.btnNormal);
        btnNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NormalMode.class);
                startActivity(intent);
                finish();
            }
        });

        btnHard = (Button) findViewById(R.id.btnHard);
        btnHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HardMode.class);
                startActivity(intent);
                finish();
            }
        });
    }


}