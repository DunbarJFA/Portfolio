package com.example.csadmin.homework2;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import static android.view.View.GONE;

public class PassiveResults extends AppCompatActivity {
    ImageView baggie;
    ImageView gold;
    ImageView loot;
    TextView rewardText;
    TextView goldText;
    TextView lootText;

    Character hero;
    Character enemy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passive_results);
        Intent intent = getIntent();
        if (intent.getParcelableExtra("hero") != null){
            hero = intent.getParcelableExtra("hero");
            enemy = intent.getParcelableExtra("enemy");
        }
        baggie = findViewById(R.id.baggie);
        rewardText = findViewById(R.id.rewardTap);
        gold = findViewById(R.id.coins);
        goldText = findViewById(R.id.coinText);
        loot = findViewById(R.id.lootsword);
        lootText = findViewById(R.id.lootText);
    }
    public void baggieClick(View v){
        baggie.setVisibility(GONE);
        rewardText.setVisibility(GONE);
        gold.setVisibility(View.VISIBLE);
        goldText.setVisibility(View.VISIBLE);
        goldText.setText(Integer.valueOf(enemy.getGold()).toString());
        loot.setVisibility(View.VISIBLE);
        lootText.setVisibility(View.VISIBLE);
        lootText.setText("Worth: " + Integer.valueOf(enemy.getDropValue()).toString());
    }
    public void resultClick(View v){
        Intent intent = new Intent(PassiveResults.this, CharacterCreation.class);
        intent.putExtra("hero",hero);
        startActivity(intent);
        finish();
    }
}
