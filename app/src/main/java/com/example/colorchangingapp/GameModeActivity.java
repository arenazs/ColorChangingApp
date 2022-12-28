package com.example.colorchangingapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;

public class GameModeActivity extends AppCompatActivity {

    private int roundCount;
    private int currentTriesAvailable;
    private int score;
    private int gameRed;
    private int gameGreen;
    private int gameBlue;
    private Integer userRed;
    private Integer userBlue;
    private Integer userGreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_mode);
        startNewGame();
    }

    private void startNewGame(){
        roundCount = 1;
        score = 0;
        currentTriesAvailable = 3;
        userRed = 0;
        userGreen = 0;
        userBlue = 0;
        setGameColors();
        setText();
    }

    private void setGameColors(){
        gameRed = getRandomRGB();
        gameBlue = getRandomRGB();
        gameGreen = getRandomRGB();
        findViewById(R.id.gameColorBox).setBackgroundColor(Color.rgb(gameRed, gameGreen, gameBlue));
    }

    @SuppressLint("SetTextI18n")
    public void newRound(View v){
        int redScore = getScore(userRed, gameRed);
        int greenScore = getScore(userGreen, gameGreen);
        int blueScore = getScore(userBlue, gameBlue);

        findViewById(R.id.backgroundLayout).setBackgroundColor(Color.rgb(userRed, userGreen, userBlue));
        score += redScore + greenScore + blueScore;
        setScoreText(findViewById(R.id.redPercent), "Score: " + redScore);
        setScoreText(findViewById(R.id.greenPercent), "Score: " + greenScore);
        setScoreText(findViewById(R.id.bluePercent), "Score: " + blueScore);
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            Log.d("Error", e.getMessage());
        }

        if(score >= 150){
            setContentView(R.layout.winner_splash);
            ((TextView)findViewById(R.id.finalScoreText)).setText("Final Score: " + score + "      Total Rounds: " + roundCount);
        }else{
            roundCount++;
            currentTriesAvailable = 3;
            setGameColors();
            setText();
            ((EditText)findViewById(R.id.redNum)).setText("0");
            ((EditText)findViewById(R.id.greenNum)).setText("0");
            ((EditText)findViewById(R.id.blueNum)).setText("0");
        }
    }

    @SuppressLint("SetTextI18n")
    private void setText(){
        ((TextView)findViewById(R.id.pageTitle)).setText("Score: " + score + "     Round: " + roundCount);
        ((Button)findViewById(R.id.tryBtn)).setText("Tries: " + currentTriesAvailable);
    }

    @SuppressLint("SetTextI18n")
    public void tryAColor(View v){
        if(((EditText)findViewById(R.id.redNum)).getText().toString().isEmpty()){
            userRed = -1;
        }else{
            userRed = Integer.valueOf(((EditText)findViewById(R.id.redNum)).getText().toString());
        }

        if(((EditText)findViewById(R.id.blueNum)).getText().toString().isEmpty()){
            userBlue = -1;
        }else{
            userBlue = Integer.valueOf(((EditText)findViewById(R.id.blueNum)).getText().toString());
        }

        if(((EditText)findViewById(R.id.greenNum)).getText().toString().isEmpty()){
            userGreen = -1;
        }else{
            userGreen = Integer.valueOf(((EditText)findViewById(R.id.greenNum)).getText().toString());
        }

        ConstraintLayout cl = findViewById(R.id.backgroundLayout);

        if(userRed > 255 || userGreen > 255 || userBlue > 255 || userRed < 0 || userGreen < 0 || userBlue < 0){
            errorPopup("Please enter a value between 0-255 for each color");
        }else if(currentTriesAvailable < 1){
            errorPopup("No more tries left, time to make your guess!");
        }else{
            cl.setBackgroundColor(Color.rgb(userRed, userGreen, userBlue));
            setScoreText(findViewById(R.id.redPercent), getHint(userRed, gameRed));
            setScoreText(findViewById(R.id.greenPercent), getHint(userGreen, gameGreen));
            setScoreText(findViewById(R.id.bluePercent), getHint(userBlue, gameBlue));
            currentTriesAvailable--;
            setText();
        }
    }

    private void setScoreText(TextView tv, String message){
        tv.setText(message);
    }

    public Integer getRandomRGB(){
        return (int) (Math.random() * 256);
    }

    private int getScore(int userColor, int gameColor){
        int difference = userColor - gameColor;
        if(difference < 0){
            difference *= -1;
        }

        int diffAmount = 0;
        for(int i = 10; i > 1; i--){
            if(difference <= diffAmount){
                return i;
            }
            diffAmount += 25;
        }
        return 1;
    }

    public void errorPopup(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    private String getHint(int userColor, int gameColor){
        int difference = userColor - gameColor;

        if(difference == 0){
            return "Correct!";
        }else if(difference < 0){
            return "Too Low!";
        }else{
            return "Too High!";
        }
    }

    public void goToMainMenu(View v){
        Intent i = new Intent(this, WelcomeSplash.class);
    }

    public void playAgain(View v){
        setContentView(R.layout.activity_game_mode);
        startNewGame();
    }

    public void changeTextColor(View v){
        int redText = getRandomRGB();
        int greenText = getRandomRGB();
        int blueText = getRandomRGB();

        ((TextView)findViewById(R.id.pageTitle)).setTextColor(Color.rgb(redText, greenText, blueText));
        ((TextView)findViewById(R.id.redText)).setTextColor(Color.rgb(redText, greenText, blueText));
        ((TextView)findViewById(R.id.greenText)).setTextColor(Color.rgb(redText, greenText, blueText));
        ((TextView)findViewById(R.id.blueText)).setTextColor(Color.rgb(redText, greenText, blueText));
        ((TextView)findViewById(R.id.redPercent)).setTextColor(Color.rgb(redText, greenText, blueText));
        ((TextView)findViewById(R.id.greenPercent)).setTextColor(Color.rgb(redText, greenText, blueText));
        ((TextView)findViewById(R.id.bluePercent)).setTextColor(Color.rgb(redText, greenText, blueText));
        ((EditText)findViewById(R.id.redNum)).setTextColor(Color.rgb(redText, greenText, blueText));
        ((EditText)findViewById(R.id.greenNum)).setTextColor(Color.rgb(redText, greenText, blueText));
        ((EditText)findViewById(R.id.blueNum)).setTextColor(Color.rgb(redText, greenText, blueText));
    }
}