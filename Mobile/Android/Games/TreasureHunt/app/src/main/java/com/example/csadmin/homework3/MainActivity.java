package com.example.csadmin.homework3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements TreasureHunt.UpdateListener, HunterList.listClickListener, Shop.HunterStatusListener{
    HunterList hunterList;
    HunterDetails hunterDetails;
    TreasureHunt treasureHunt;
    Shop shop;
    static int goldCount;

    static ArrayList<Hunter> hunters;
    static ArrayList<Hunter> allHunters;
    static ArrayList<Treasure> treasureTypes;
    static int[] specialties;

    Hunter sergio = new Hunter("Sergio","sergio_portrait","sergio_still",18,10,3,1);
    Hunter marigold = new Hunter("Marigold","marigold_portrait","marigold_still",18,10,3,1);
    Hunter bargash = new Hunter("Bargash","bargash_portrait","bargash_still",30,7,3,2);
    Hunter keko = new Hunter("Keko","keko_portrait","keko_still",12,15,8,3);
    Hunter mugrus = new Hunter("Mugrus","mugrus_portrait","mugrus_still",25,20,4,4);
    Hunter phirda = new Hunter("Phirda","phirda_portrait","phirda_still",30,15,7,5);
    Hunter xekerm = new Hunter("Xekerm","xekerm_portrait","xekerm_still",30,15,7,6);
    Hunter mobos = new Hunter("Neera","neera_portrait","neera_still",12,23,7,7);
    Hunter nanova = new Hunter("Nanova","nanova_portrait","nanova_still",10,25,6,8);
    Hunter lupin = new Hunter("Lupin","lupin_portrait","lupin_still",10,25,11,9);

    Treasure common = new Treasure("Chest of Coins", 10, "Common");
    Treasure uncommon = new Treasure("Pouch of Gems", 30, "Uncommon");
    Treasure rare = new Treasure("Ancient Idol",60,"Rare");
    Treasure epic = new Treasure("Lost Royal Jewels",100,"Epic");
    Treasure legendary = new Treasure("The Holy Grail",300,"Legendary");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    //replace the Action Bar with our custom Toolbar
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    //attach the SectionsPagerAdapter to the ViewPager (allowing for swipable fragments)
        SectionsPagerAdapter pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager pager = findViewById(R.id.pager);
        pager.setAdapter(pagerAdapter);

    //attach the ViewPager to the TabLayout (allowing for custom tab names)
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);
    //create arrayList of treasure types
        treasureTypes = new ArrayList<>();
        treasureTypes.add(common);
        treasureTypes.add(uncommon);
        treasureTypes.add(rare);
        treasureTypes.add(epic);
        treasureTypes.add(legendary);
    //create arrayList of possible Hunters
//EDIT THE FOLLOWING ARRAY TO ACCOUNT FOR POSSIBLE NEW CHARACTERS!!!!
        allHunters = new ArrayList<>();
/////////////////////////////////////////////////////////////////////
    //populate allHunters with the possible hunters
        allHunters.add(bargash);
        allHunters.add(keko);
        allHunters.add(mugrus);
        allHunters.add(phirda);
        allHunters.add(xekerm);
        allHunters.add(mobos);
        allHunters.add(nanova);
        allHunters.add(lupin);
    //create the arrayList of the player's current hunters (begins the game with sergio and marigold)
        hunters = new ArrayList<>();
        hunters.add(sergio);
        hunters.add(marigold);

        //create fragments
        hunterList = new HunterList();
        hunterList.setNames(hunters);
        hunterDetails = new HunterDetails();
        treasureHunt = new TreasureHunt();
        treasureHunt.setNames(hunters);
        shop = new Shop();
        shop.setNames(hunters);

        goldCount = 0;
    }

    public static  ArrayList<Hunter> getHunters(){
        return hunters;
    }

    public static ArrayList<Hunter> getAllHunters(){
        return allHunters;
    }

    public void refreshDetails(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction swap = fragmentManager.beginTransaction();
        swap.setReorderingAllowed(false);
        swap.detach(hunterDetails).attach(hunterDetails).commit();
    }


    @Override
    public void onTreasureUpdate(){
        //refresh the HunterDetails fragment
        refreshDetails();
    }

    @Override
    public void onHunterDeath(Hunter hunter){
        for(Hunter h : hunters){
            if (h.getName().equals(hunter.getName())){
                h.setDead();
            }
        }
        refreshDetails();
        Toast.makeText(this, hunter.getName() + getResources().getText(R.string.hunterDeath), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void itemClicked(String name){
        Intent intent = new Intent(this,HunterPopup.class);
        for(Hunter hunter : hunters){
            if(hunter.getName().equals(name)){
                intent.putExtra("Hunter",hunter);
                startActivity(intent);
            }
        }
    }

    public static void setGoldCount(int gold){
        goldCount += gold;
    }

    public static Integer getGoldCount(){
        return goldCount;
    }

    @Override
    public void onHealOrRest(String name, int procedure){
        //take name from Shop
        for (Hunter h : hunters){
        //restore stats for the corresponding hunter
            if (h.getName().equals(name)){
                if(h.getDead() != 1) {
                    //if procedure == 1, heal
                    if (procedure == 1) {
                        if (h.getHealth() < h.getTotalHealth()) {
                            goldCount -= 100;
                            Shop.setGoldTally();
                            h.setHealed();
                        } else {
                            Toast.makeText(this, R.string.fullHealth, Toast.LENGTH_SHORT).show();
                        }
                    }
                    //if procedure == 0, rest
                    else if (procedure == 0) {
                        if (h.getStamina() < h.getTotalStamina()) {
                            goldCount -= 100;
                            Shop.setGoldTally();
                            h.setRested();
                        } else {
                            Toast.makeText(this, R.string.fullStamina, Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    Toast.makeText(this, R.string.deathNotice, Toast.LENGTH_SHORT).show();
                }
            }
        }
        //refresh the hunterDetails fragment
        refreshDetails();
    }

    @Override
    public void onGacha() {
        if(allHunters.size() == 0) {
            //if all hunters have already been purchased
            Toast.makeText(this, R.string.gachaEmpty, Toast.LENGTH_SHORT).show();
        }
        else if(allHunters.size() == 1){
            goldCount -= 500;
            Shop.setGoldTally();
            //no need to grab random number
            Hunter hunter = allHunters.get(0);
            hunters.add(hunter);
            allHunters.remove(0);
            //refresh the fragments
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction swap = fragmentManager.beginTransaction();
            swap.setReorderingAllowed(false);
            swap.detach(hunterDetails).attach(hunterDetails)
                    .detach(treasureHunt).attach(treasureHunt)
                    .detach(hunterList).attach(hunterList)
                    .commit();
            treasureHunt.updateSpinner();
            //Toast congratulating the player on their new hunter
            Toast.makeText(this, hunter.getName() + " " + getResources().getText(R.string.gachaCongrats), Toast.LENGTH_SHORT).show();

        }
        else{
            goldCount -= 500;
            Shop.setGoldTally();
            //take "random" hunter from allHunters and add to hunters
            Random random = new Random();
            int selection = random.nextInt(allHunters.size() - 1);
            Hunter hunter = allHunters.get(selection);
            hunters.add(hunter);
            allHunters.remove(selection);
            //refresh the fragments
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction swap = fragmentManager.beginTransaction();
            swap.setReorderingAllowed(false);
            swap.detach(hunterDetails).attach(hunterDetails)
                    .detach(treasureHunt).attach(treasureHunt)
                    .detach(hunterList).attach(hunterList)
                    .commit();
            treasureHunt.updateSpinner();
            hunterList.onListUpdate();
            shop.onListUpdate();
            //Toast congratulating the player on their new hunter
            Toast.makeText(this, hunter.getName() + " " + getResources().getText(R.string.gachaCongrats), Toast.LENGTH_SHORT).show();
        }
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter{
        public SectionsPagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public int getCount(){
            return 4;//one page per fragment
        }

        @Override
        public Fragment getItem(int position){
            if(position == 0){
                return hunterList;
            }
            else if(position == 1){
                return hunterDetails;
            }
            else if(position == 2){
                return treasureHunt;
            }
            else{
                return shop;
            }
        }

        @Override
        public CharSequence getPageTitle(int position){
            switch(position){
                case 0:
                    return getResources().getText(R.string.firstTab);
                case 1:
                    return getResources().getText(R.string.secondTab);
                case 2:
                    return getResources().getText(R.string.thirdTab);
                case 3:
                    return getResources().getText(R.string.fourthTab);
            }
            return null;
        }

    }
}

