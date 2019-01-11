package com.example.csadmin.homework2;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



public class Exposition extends AppCompatActivity {

    private int page;
    TextView block1;
    TextView block2;
    TextView block3;
    TextView block4;
    TextView block5;
    TextView block6;
    Button butt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exposition);
        block1 = findViewById(R.id.Block1);
        block2 = findViewById(R.id.Block2);
        block3 = findViewById(R.id.Block3);
        block4 = findViewById(R.id.Block4);
        block5 = findViewById(R.id.Block5);
        block6 = findViewById(R.id.Block6);
        butt = findViewById(R.id.TurnPage);
        page = 1;
        pageOne();
    }
    public void pageOne(){
        block1.setText(R.string.Expo1);
        block2.setText(R.string.Expo2);
        block3.setText(R.string.Expo3);
        block4.setText(R.string.Expo4);
        block5.setText(R.string.Expo5);
        block6.setText(R.string.Expo6);
    }
    public void pageTwo(){
        block1.setText(R.string.Expo7);
        block2.setText(R.string.Expo8);
        block3.setText(R.string.Expo9);
        block4.setText(R.string.Expo10);
        block5.setText(R.string.Expo11);
        block6.setText(R.string.Expo12);
    }
    public void pageThree(){
        block1.setText(R.string.Expo13);
        block2.setText(R.string.Expo14);
        block3.setText(R.string.Expo15);
        block4.setText(R.string.Expo16);
        block5.setText(R.string.Blank);
        block6.setText(R.string.Blank);
    }
    public void pageTurn(View v){
        page++;
        if (page == 2) {
            pageTwo();
        }
        else if (page == 3){
            pageThree();
            butt.setText(R.string.titleScreenButton);
        }
        else if (page == 4){
            Intent i = new Intent(Exposition.this,CharacterCreation.class);
            startActivity(i);
            finish();
        }
    }
}