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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameModeActivity extends AppCompatActivity {

    private final int RED = 0;
    private final int GREEN = 1;
    private final int BLUE = 2;

    private int roundCount;
    private int currentTriesAvailable;
    private int score;
    private int gameRed;
    private int gameGreen;
    private int gameBlue;
    private Integer userRed;
    private Integer userBlue;
    private Integer userGreen;
    private final List<Integer> userColors = new ArrayList<>();
    private final List<EditText> userTextInfo = new ArrayList<>();
    private final Map<Button, Integer> upAndDownButtons = new HashMap<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_mode);
        startNewGame();
        userTextInfo.add(findViewById(R.id.redNum));
        userTextInfo.add(findViewById(R.id.greenNum));
        userTextInfo.add(findViewById(R.id.blueNum));
        upAndDownButtons.put((Button) findViewById(R.id.redDownBtn), RED);
        upAndDownButtons.put((Button) findViewById(R.id.redUpBtn), RED);
        upAndDownButtons.put((Button) findViewById(R.id.greenDownBtn), GREEN);
        upAndDownButtons.put((Button) findViewById(R.id.greenUpBtn), GREEN);
        upAndDownButtons.put((Button) findViewById(R.id.blueDownBtn), BLUE);
        upAndDownButtons.put((Button) findViewById(R.id.blueUpBtn), BLUE);
    }

    private void startNewGame(){
        roundCount = 1;
        score = 0;
        currentTriesAvailable = 3;
        userColors.add(0);
        userColors.add(0);
        userColors.add(0);
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
        int redScore = getScore(userColors.get(RED), gameRed);
        int greenScore = getScore(userColors.get(GREEN), gameGreen);
        int blueScore = getScore(userColors.get(BLUE), gameBlue);

        updateBackgroundColor();
        score += redScore + greenScore + blueScore;
        setScoreText(findViewById(R.id.redPercent), "Score: " + redScore);
        setScoreText(findViewById(R.id.greenPercent), "Score: " + greenScore);
        setScoreText(findViewById(R.id.bluePercent), "Score: " + blueScore);

        if(score >= 150){
            setContentView(R.layout.winner_splash);
            ((TextView)findViewById(R.id.finalScoreText)).setText("Final Score: " + score + "      Total Rounds: " + roundCount);
        }else{
            roundCount++;
            currentTriesAvailable = 3;
            setGameColors();
            setText();
            userTextInfo.get(RED).setText("0");
            userColors.set(RED, 0);
            userTextInfo.get(GREEN).setText("0");
            userColors.set(GREEN, 0);
            userTextInfo.get(BLUE).setText("0");
            userColors.set(BLUE, 0);
        }
    }cd 

    @SuppressLint("SetTextI18n")
    private void setText(){
        ((TextView)findViewById(R.id.pageTitle)).setText("Score: " + score + "     Round: " + roundCount);
        ((Button)findViewById(R.id.tryBtn)).setText("Tries: " + currentTriesAvailable);
    }

    public void updateColor(View v){
        Button btnColorAndType = findViewById(v.getId());
        int difference = Integer.parseInt(userTextInfo.get(upAndDownButtons.get(btnColorAndType)).getText().toString()) % 5;

        if(btnColorAndType.getText().toString().equals("+")){
            if(difference != 0){
                difference = (difference - 5) * -1;
                updateColorAndText(difference, btnColorAndType);
            }else{
                updateColorAndText(5, btnColorAndType);
            }
        }else{
            if(difference != 0){
                difference *= -1;
                updateColorAndText(difference, btnColorAndType);
            }else{
                updateColorAndText(-5, btnColorAndType);
            }
        }
    }

    private void updateColorAndText(int numToAdd, Button b){
        Integer currentNum = userColors.get(upAndDownButtons.get(b));
        currentNum += numToAdd;

        if(currentNum > 0 && currentNum < 255){
            userColors.set(upAndDownButtons.get(b), currentNum);
            updateBackgroundColor();
            userTextInfo.get(upAndDownButtons.get(b)).setText(currentNum.toString());
        }else{
            errorPopup("Color values limited to 0 - 255");
        }

    }

    private void updateBackgroundColor(){
        findViewById(R.id.backgroundLayout).setBackgroundColor(Color.rgb(userColors.get(RED), userColors.get(GREEN), userColors.get(BLUE)));
    }

    @SuppressLint("SetTextI18n")
    public void tryAColor(View v){
        if((userTextInfo.get(RED)).getText().toString().isEmpty()){
            userColors.set(RED, -1);
        }else{
            userColors.set(RED, Integer.valueOf(userTextInfo.get(RED).getText().toString()));
        }

        if((userTextInfo.get(BLUE)).getText().toString().isEmpty()){
            userColors.set(BLUE, -1);
        }else{
            userColors.set(BLUE, Integer.valueOf((userTextInfo.get(BLUE)).getText().toString()));
        }

        if((userTextInfo.get(GREEN)).getText().toString().isEmpty()){
            userColors.set(GREEN, -1);
        }else{
            userColors.set(GREEN, Integer.valueOf((userTextInfo.get(GREEN)).getText().toString()));
        }

        ConstraintLayout cl = findViewById(R.id.backgroundLayout);

        if(userColors.get(RED) > 255 || userColors.get(GREEN) > 255 || userColors.get(BLUE) > 255 || userColors.get(RED) < 0 || userColors.get(GREEN) < 0 || userColors.get(BLUE) < 0){
            errorPopup("Please enter a value between 0-255 for each color");
        }else if(currentTriesAvailable < 1){
            errorPopup("No more tries left, time to make your guess!");
        }else{
            cl.setBackgroundColor(Color.rgb(userColors.get(RED), userColors.get(GREEN), userColors.get(BLUE)));
            setScoreText(findViewById(R.id.redPercent), getHint(userColors.get(RED), gameRed));
            setScoreText(findViewById(R.id.greenPercent), getHint(userColors.get(GREEN), gameGreen));
            setScoreText(findViewById(R.id.bluePercent), getHint(userColors.get(BLUE), gameBlue));
            currentTriesAvailable--;
            setText();
        }
    }

    private void setScoreText(TextView tv, String message){
        tv.setText(message);
    }

    public Integer getRandomRGB(){
        int color = (int) (Math.random() * 256);

        return color - (color % 5);
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