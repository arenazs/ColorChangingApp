package com.example.colorchangingapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void changeBackground(View v){
        Integer red;
        Integer blue;
        Integer green;

        if(((EditText)findViewById(R.id.redNum)).getText().toString().isEmpty()){
            red = -1;
        }else{
            red = Integer.valueOf(((EditText)findViewById(R.id.redNum)).getText().toString());
        }

        if(((EditText)findViewById(R.id.blueNum)).getText().toString().isEmpty()){
            blue = -1;
        }else{
            blue = Integer.valueOf(((EditText)findViewById(R.id.blueNum)).getText().toString());
        }

        if(((EditText)findViewById(R.id.greenNum)).getText().toString().isEmpty()){
            green = -1;
        }else{
            green = Integer.valueOf(((EditText)findViewById(R.id.greenNum)).getText().toString());
        }

        ConstraintLayout cl = findViewById(R.id.backgroundLayout);

        if(red > 255 || green > 255 || blue > 255 || red < 0 || green < 0 || blue < 0){
            errorPopup("Please enter a value between 0-255 for each color");
        }else{
            cl.setBackgroundColor(Color.rgb(red, green, blue));
        }
    }

    @SuppressLint("SetTextI18n")
    public void generateRandomColor(View v){
        ((EditText)findViewById(R.id.redNum)).setText(getRandomRGB().toString());
        ((EditText)findViewById(R.id.greenNum)).setText(getRandomRGB().toString());
        ((EditText)findViewById(R.id.blueNum)).setText(getRandomRGB().toString());

        changeBackground(v);
    }

    public void changeTextColor(View v){
        int redText = getRandomRGB();
        int greenText = getRandomRGB();
        int blueText = getRandomRGB();

        ((TextView)findViewById(R.id.pageTitle)).setTextColor(Color.rgb(redText, greenText, blueText));
        ((TextView)findViewById(R.id.redText)).setTextColor(Color.rgb(redText, greenText, blueText));
        ((TextView)findViewById(R.id.greenText)).setTextColor(Color.rgb(redText, greenText, blueText));
        ((TextView)findViewById(R.id.blueText)).setTextColor(Color.rgb(redText, greenText, blueText));
        ((EditText)findViewById(R.id.redNum)).setTextColor(Color.rgb(redText, greenText, blueText));
        ((EditText)findViewById(R.id.greenNum)).setTextColor(Color.rgb(redText, greenText, blueText));
        ((EditText)findViewById(R.id.blueNum)).setTextColor(Color.rgb(redText, greenText, blueText));
    }

    public void errorPopup(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    public Integer getRandomRGB(){
        return (int) (Math.random() * 256);
    }
}