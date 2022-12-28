package com.example.colorchangingapp;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class WelcomeSplash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_splash);
    }

    public void playWithColors(View v){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void startGameMode(View v){
        Intent i = new Intent(this, GameRulesSplash.class);
        startActivity(i);
    }
}