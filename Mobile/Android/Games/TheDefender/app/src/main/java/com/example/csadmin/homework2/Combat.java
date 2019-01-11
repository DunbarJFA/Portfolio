package com.example.csadmin.homework2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class Combat extends Activity {

    Character hero;
    Character enemy;
    private int heroHealthCap;
    private int enemyHealthCap;
    private int heroHealthLeft;
    private int enemyHealthLeft;
    private boolean encounter;
    Button passive;
    Button pasStr;
    Button pasInt;
    Button aggressive;
    Button aggStr;
    Button aggInt;
    ImageView leftCooldown;
    ImageView rightCooldown;
    ImageView heroPort;
    ImageView enemyPort;
    private int intimidateCooldown;
    private int reasonCooldown;
    private int attackCooldown;
    private int curseCooldown;
    TextView heroSpeech;
    TextView enemySpeech;
    TextView heroHealth;
    TextView enemyHealth;
    Random rand;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combat);

        Intent intent = getIntent();
        hero = intent.getParcelableExtra("hero");
        enemy = new Character("Journeyman Knight", "Longsword", 2);
        heroHealthCap = hero.getHealth();
        enemyHealthCap = enemy.getHealth();
        heroHealthLeft = heroHealthCap;
        enemyHealthLeft = enemyHealthCap;
        encounter = true;
        passive = findViewById(R.id.Btn_Combat_Passive);
        pasStr = findViewById(R.id.Btn_Combat_PasStr);
        pasInt = findViewById(R.id.Btn_Combat_PasInt);
        aggressive = findViewById(R.id.Btn_Combat_Aggro);
        aggStr = findViewById(R.id.Btn_Combat_AggStr);
        aggInt = findViewById(R.id.Btn_Combat_AggInt);
        heroSpeech = findViewById(R.id.HeroSpeech);
        enemySpeech = findViewById(R.id.EnemySpeech);
        leftCooldown = findViewById(R.id.CooldownLeft);
        rightCooldown = findViewById(R.id.CooldownRight);
        heroPort = findViewById(R.id.heroPortrait);
        enemyPort = findViewById(R.id.enemyPortrait);
        heroHealth = findViewById(R.id.heroHealth);
        enemyHealth = findViewById(R.id.enemyHealth);
        rand = new Random();
        runTimer();
    }
    private void runTimer(){
        final Handler handler = new Handler();
        handler.post(new Runnable(){
            @Override
            public void run() {
                if(encounter){
                    heroHealth.setText(hero.getHealth() + "/" + heroHealthCap);
                    enemyHealth.setText(enemy.getHealth() + "/" + enemyHealthCap);

                    if(enemy.getHealth() > 0 && hero.getHealth() > 0){
                        heroHealthLeft = hero.getHealth() - (enemy.getStrength()*2);
                        hero.setHealth(heroHealthLeft);
                        heroHealth.setText(hero.getHealth() + "/" + heroHealthCap);
                    }
                    else if(enemy.getHealth() <= 0){
                        encounter = false;
                        Toast winToast = Toast.makeText(getApplicationContext(),R.string.Win,Toast.LENGTH_SHORT);
                        winToast.show();
                        hero.setGold(hero.getGold() + enemy.getGold() + enemy.getDropValue());
                        Intent intent = new Intent(Combat.this, AggressiveResults.class);
                        intent.putExtra("hero",hero);
                        intent.putExtra("enemy",enemy);
                        startActivity(intent);
                        finish();
                    }
                    else if(hero.getHealth() <= 0){
                        encounter = false;
                        Spannable centered = new SpannableString(getString(R.string.Fail));
                        centered.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),0,getString(R.string.Fail).length() - 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        Toast failToast = Toast.makeText(getApplicationContext(),centered,Toast.LENGTH_LONG);
                        failToast.show();
                        Intent intent = new Intent(Combat.this, DeathActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    handler.postDelayed(this, 3000);
                    checkCoolDowns();
                }
            }
        });
    }

    private void checkCoolDowns(){
        if(intimidateCooldown > 0){
            intimidateCooldown--;
        }
        if(reasonCooldown > 0){
            reasonCooldown--;
        }
        if(attackCooldown > 0) {
            attackCooldown--;
        }
        if(curseCooldown > 0){
            curseCooldown--;
        }
    }

    public void heroMessage(String message){
        clearMessages();
        heroPort.setVisibility(View.VISIBLE);
        heroSpeech.setText(message);
    }
    public void enemyMessage(String message){
        clearMessages();
        enemyPort.setVisibility(View.VISIBLE);
        enemySpeech.setText(message);
    }

    public void clearMessages(){
        heroPort.setVisibility(View.GONE);
        enemyPort.setVisibility(View.GONE);
        heroSpeech.setText(R.string.Blank);
        enemySpeech.setText(R.string.Blank);
    }
    public void cooldownClick(View v){
        clearMessages();
        leftCooldown.setVisibility(View.GONE);
        rightCooldown.setVisibility(View.GONE);
        passive.setVisibility(View.VISIBLE);
        aggressive.setVisibility(View.VISIBLE);
        heroMessage(getString(R.string.Cooldown));
    }

    public void passiveClick(View v){
        clearMessages();
        passive.setVisibility(View.GONE);
        aggressive.setVisibility(View.GONE);
        if(intimidateCooldown == 0) {
            pasStr.setVisibility(View.VISIBLE);
        }
        else{
            leftCooldown.setVisibility(View.VISIBLE);
        }
        if(reasonCooldown == 0) {
            pasInt.setVisibility(View.VISIBLE);
        }
        else{
            rightCooldown.setVisibility(View.VISIBLE);
        }
    }//end of method passiveClick

    public void pasIntClick(View v){
        clearMessages();
        pasStr.setVisibility(View.GONE);
        pasInt.setVisibility(View.GONE);
        passive.setVisibility(View.VISIBLE);
        aggressive.setVisibility(View.VISIBLE);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                //post the heromsg
                int i = rand.nextInt();
                if(i%2 != 0){
                    heroMessage(getString(R.string.Reason1));
                }
                else{
                    heroMessage(getString(R.string.Reason2));
                }
            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //post delay everything else
                if (hero.getIntelligence() > enemy.getIntelligence()) {
                    enemyMessage(getString(R.string.ReasonSuccess));
                    //post delay to let the enemy speak
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            encounter = false;

                            Toast winToast = Toast.makeText(getApplicationContext(), "You Win!", Toast.LENGTH_SHORT);
                            winToast.show();

                            hero.setGold(hero.getGold() + enemy.getGold() + enemy.getDropValue());

                            Intent intent = new Intent(Combat.this, PassiveResults.class);
                            intent.putExtra("hero", hero);
                            intent.putExtra("enemy", enemy);

                            startActivity(intent);
                            finish();
                        }
                    },3000);
                }
                else{
                    enemyMessage(getString(R.string.ReasonFailure));

                    reasonCooldown = reasonCooldown + 10;
                    pasInt.setVisibility(View.GONE);
                }
            }
        },3000);
        rightCooldown.setVisibility(View.GONE);
        leftCooldown.setVisibility(View.GONE);
    }//end of method pasIntClick

    public void pasStrClick(View v){
        clearMessages();
        pasStr.setVisibility(View.GONE);
        pasInt.setVisibility(View.GONE);
        passive.setVisibility(View.VISIBLE);
        aggressive.setVisibility(View.VISIBLE);

        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                //post the heromsg
                int i = rand.nextInt();
                if(i%2 != 0){
                    heroMessage(getString(R.string.Intimidate1));
                }
                else{
                    heroMessage(getString(R.string.Intimidate2));
                }
            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (hero.getStrength() > enemy.getStrength()) {
                    enemyMessage(getString(R.string.IntimSuccess));

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            encounter = false;
                            Toast winToast = Toast.makeText(getApplicationContext(), "You Win!", Toast.LENGTH_SHORT);
                            winToast.show();
                            hero.setGold(hero.getGold() + enemy.getGold() + enemy.getDropValue());
                            Intent intent = new Intent(Combat.this, PassiveResults.class);
                            intent.putExtra("hero", hero);
                            intent.putExtra("enemy", enemy);
                            startActivity(intent);
                            finish();
                        }
                    },3000);
                }
                else{
                    enemyMessage(getString(R.string.IntimFailure));

                    intimidateCooldown = intimidateCooldown + 10;
                    pasStr.setVisibility(View.GONE);
                }
            }
        },3000);
        leftCooldown.setVisibility(View.GONE);
        rightCooldown.setVisibility(View.GONE);
    }//end of method pasStrClick

    public void aggressiveClick(View v){
        clearMessages();
        passive.setVisibility(View.GONE);
        aggressive.setVisibility(View.GONE);
        if(attackCooldown == 0) {
            aggStr.setVisibility(View.VISIBLE);
        }
        else{
            leftCooldown.setVisibility(View.VISIBLE);
        }
        if(curseCooldown == 0) {
            aggInt.setVisibility(View.VISIBLE);
        }
        else{
            rightCooldown.setVisibility(View.VISIBLE);
        }
    }//end of method aggressiveClick

    public void aggStrClick(View v){
        clearMessages();
        aggStr.setVisibility(View.GONE);
        aggInt.setVisibility(View.GONE);
        passive.setVisibility(View.VISIBLE);
        aggressive.setVisibility(View.VISIBLE);

        enemyHealthLeft = enemy.getHealth() - (hero.getStrength()*2);
        enemy.setHealth(enemyHealthLeft);
        enemyHealth.setText(enemy.getHealth() + "/" + enemyHealthCap);

        attackCooldown++;
        aggStr.setVisibility(View.GONE);
        leftCooldown.setVisibility(View.GONE);
        rightCooldown.setVisibility(View.GONE);
    }//end of method aggStrClick

    public void aggIntClick(View v){
        clearMessages();
        aggStr.setVisibility(View.GONE);
        aggInt.setVisibility(View.GONE);
        passive.setVisibility(View.VISIBLE);
        aggressive.setVisibility(View.VISIBLE);

        enemy.setStrength(enemy.getStrength() - hero.getIntelligence());
        curseCooldown = curseCooldown + 7;
        aggInt.setVisibility(View.GONE);
        rightCooldown.setVisibility(View.GONE);
        leftCooldown.setVisibility(View.GONE);
    }//end of method aggIntClick
}

