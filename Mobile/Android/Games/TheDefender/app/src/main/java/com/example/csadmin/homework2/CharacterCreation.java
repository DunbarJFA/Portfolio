package com.example.csadmin.homework2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CharacterCreation extends Activity {
    Character hero;
    TextView hpView;
    TextView strView;
    TextView intView;
    TextView lckView;
    TextView gldView;

    Button hpPlus;
    Button hpMinus;
    Button strPlus;
    Button strMinus;
    Button intPlus;
    Button intMinus;
    Button lckPlus;
    Button lckMinus;
    Button combat;

    Toast emptyPurse;
    private int purse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_creation);
        Intent intent = getIntent();
        if (intent.getParcelableExtra("hero") != null){
            hero = intent.getParcelableExtra("hero");
        }
        else {
            hero = new Character("Umlaut", 10);
        }
        hpView = findViewById(R.id.HpView);
        hpView.append(Integer.valueOf(hero.getHealth()).toString());
        strView = findViewById(R.id.StrView);
        strView.append(Integer.valueOf(hero.getStrength()).toString());
        intView = findViewById(R.id.IntView);
        intView.append(Integer.valueOf(hero.getIntelligence()).toString());
        lckView = findViewById(R.id.LckView);
        lckView.append(Integer.valueOf(hero.getLuck()).toString());
        gldView = findViewById(R.id.GldView);
        gldView.append(Integer.valueOf(hero.getGold()).toString());

        purse = hero.getGold();
        emptyPurse = Toast.makeText(getApplicationContext(),R.string.EmptyPurse,Toast.LENGTH_SHORT);

        hpPlus = findViewById(R.id.hpUp);
        hpPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hpUpClick(v);
            }
        });
        hpMinus = findViewById(R.id.hpDown);
        hpMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hpDwnClick(v);
            }
        });
        strPlus = findViewById(R.id.strUp);
        strPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strUpClick(v);
            }
        });
        strMinus = findViewById(R.id.strDown);
        strMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strDwnClick(v);
            }
        });
        intPlus = findViewById(R.id.intUp);
        intPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intUpClick(v);
            }
        });
        intMinus = findViewById(R.id.intDown);
        intMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intDwnClick(v);
            }
        });
        lckPlus = findViewById(R.id.lckUp);
        lckPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lckUpClick(v);
            }
        });
        lckMinus = findViewById(R.id.lckDown);
        lckMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lckDwnClick(v);
            }
        });
        combat = findViewById(R.id.toCombat);
        combat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CharacterCreation.this, Combat.class);
                intent.putExtra("hero",hero);
                startActivity(intent);
                finish();
            }
        });
    }

    public void hpUpClick(View v){
        if(hero.getGold() > 0) {
            Integer temp = hero.getHealth()/20;
            temp++;
            temp = temp * 20;
            hpView.setText(R.string.HPView);
            hpView.append(temp.toString());
            hero.setHealth(temp);
            hero.setGold(hero.getGold() - 1);
        }
        else{
            emptyPurse.show();
        }
        gldView.setText(R.string.GldView);
        Integer goldcount = hero.getGold();
        gldView.append(goldcount.toString());
    }//end of method
    public void hpDwnClick(View v){
        if(hero.getGold() <= this.purse && hero.getHealth() > 60) {
            Integer temp = hero.getHealth()/20;
            temp--;
            temp = temp * 20;
            hpView.setText(R.string.HPView);
            hpView.append(temp.toString());
            hero.setHealth(temp);
            hero.setGold(hero.getGold() + 1);
        }
        gldView.setText(R.string.GldView);
        Integer goldcount = hero.getGold();
        gldView.append(goldcount.toString());
    }//end of method
    public void strUpClick(View v){
        if(hero.getGold() > 0) {
            Integer temp = hero.getStrength()/2;
            temp++;
            temp = temp * 2;
            strView.setText(R.string.StrView);
            strView.append(temp.toString());
            hero.setStrength(temp);
            Log.d("hero","Hero Strength: " + hero.getStrength());
            hero.setGold(hero.getGold() - 1);
        }
        else{
            emptyPurse.show();
        }
        gldView.setText(R.string.GldView);
        Integer goldcount = hero.getGold();
        gldView.append(goldcount.toString());
    }//end of method
    public void strDwnClick(View v){
        if(hero.getGold() <= this.purse && hero.getStrength() != 0) {
            Integer temp = hero.getStrength()/2;
            temp--;
            temp = temp * 2;
            strView.setText(R.string.StrView);
            strView.append(temp.toString());
            hero.setStrength(temp);
            hero.setGold(hero.getGold() + 1);
        }
        gldView.setText(R.string.GldView);
        Integer goldcount = hero.getGold();
        gldView.append(goldcount.toString());
    }//end of method
    public void intUpClick(View v){
        if(hero.getGold() > 0) {
            Integer temp = hero.getIntelligence()/2;
            temp++;
            temp = temp * 2;
            intView.setText(R.string.IntView);
            intView.append(temp.toString());
            hero.setIntelligence(temp);
            hero.setGold(hero.getGold() - 1);
        }
        else{
            emptyPurse.show();
        }
        gldView.setText(R.string.GldView);
        Integer goldcount = hero.getGold();
        gldView.append(goldcount.toString());
    }//end of method
    public void intDwnClick(View v){
        if(hero.getGold() <= this.purse && hero.getIntelligence() != 0) {
            Integer temp = hero.getIntelligence()/2;
            temp--;
            temp = temp * 2;
            intView.setText(R.string.IntView);
            intView.append(temp.toString());
            hero.setIntelligence(temp);
            hero.setGold(hero.getGold() + 1);
        }
        gldView.setText(R.string.GldView);
        Integer goldcount = hero.getGold();
        gldView.append(goldcount.toString());
    }//end of method
    public void lckUpClick(View v){
        if(hero.getGold() > 0) {
            Integer temp = hero.getLuck();
            temp++;
            lckView.setText(R.string.LckView);
            lckView.append(temp.toString());
            hero.setLuck(temp);
            hero.setGold(hero.getGold() - 1);
        }
        else{
            emptyPurse.show();
        }
        gldView.setText(R.string.GldView);
        Integer goldcount = hero.getGold();
        gldView.append(goldcount.toString());
    }//end of method
    public void lckDwnClick(View v){
        if(hero.getGold() <= this.purse && hero.getLuck() != 0) {
            Integer temp = hero.getLuck();
            temp--;
            lckView.setText(R.string.LckView);
            lckView.append(temp.toString());
            hero.setLuck(temp);
            hero.setGold(hero.getGold() + 1);
        }
        gldView.setText(R.string.GldView);
        Integer goldcount = hero.getGold();
        gldView.append(goldcount.toString());
    }//end of method

}

