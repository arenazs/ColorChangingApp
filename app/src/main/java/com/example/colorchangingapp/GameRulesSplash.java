package com.example.colorchangingapp;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class GameRulesSplash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_rules_splash);
    }

    public void startGame(View v){
        Intent i = new Intent(this, GameModeActivity.class);
        startActivity(i);
    }
}