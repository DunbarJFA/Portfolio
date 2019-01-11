package com.example.csadmin.homework3;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class TreasureHunt extends Fragment {
    ArrayList<String> names;
    ArrayList<Hunter> hunterHolder;
    ArrayList<String> difficulties;
    String chosenHunter;
    String chosenDifficulty;
    static ArrayList<Treasure> treasureTypes;
    Treasure common = new Treasure("Pouch of Coins", 10, "common");
    Treasure uncommon = new Treasure("Pile of Gems", 30, "uncommon");
    Treasure rare = new Treasure("Ancient Idol",60,"rare");
    Treasure epic = new Treasure("Lost Royal Regalia",100,"epic");
    Treasure legendary = new Treasure("The Holy Grail",300,"legendary");
    private UpdateListener listener;
    ArrayAdapter<String> THAdapter;
    ArrayAdapter<String> DiffAdapter;

    public TreasureHunt() {
        //default empty constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        difficulties = new ArrayList<>();
        difficulties.add(getResources().getString(R.string.Easy));
        difficulties.add(getResources().getString(R.string.Medium));
        difficulties.add(getResources().getString(R.string.Hard));
        difficulties.add(getResources().getString(R.string.Nightmare));

        treasureTypes = new ArrayList<>();
        treasureTypes.add(common);
        treasureTypes.add(uncommon);
        treasureTypes.add(rare);
        treasureTypes.add(epic);
        treasureTypes.add(legendary);


        View v = inflater.inflate(R.layout.fragment_treasure_hunt, container, false);

        final Spinner nameSpinner = v.findViewById(R.id.treasure_hunt_spinner);
        final Spinner diffSpinner = v.findViewById(R.id.hunt_difficulty_spinner);

        THAdapter = new ArrayAdapter<>(this.getActivity(),android.R.layout.simple_spinner_item, names);
        THAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        DiffAdapter = new ArrayAdapter<>(this.getActivity(),android.R.layout.simple_spinner_item, difficulties);
        DiffAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        nameSpinner.setAdapter(THAdapter);
        nameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chosenHunter = nameSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        diffSpinner.setAdapter(DiffAdapter);
        diffSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chosenDifficulty = diffSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Button button = v.findViewById(R.id.huntButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkTreasure();
                if(listener != null){
                    listener.onTreasureUpdate();
                }
            }
        });

        // Inflate the layout for this fragment
        return v;
    }

    public interface UpdateListener{
        void onTreasureUpdate();
        void onHunterDeath(Hunter hunter);
    }


    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        this.listener = (UpdateListener)context;
    }

    public void setNames(ArrayList<Hunter> hunters){
        hunterHolder = hunters;
        names = new ArrayList<>();
        for (Hunter hunter : hunterHolder){
            names.add(hunter.getName());
        }
    }

    public void checkTreasure(){
        for(Hunter hunter : hunterHolder) {
            if (hunter.getName().equals(chosenHunter)) {
                //check stamina against Diff level
                if(chosenDifficulty.equals(getResources().getString(R.string.Easy))) {
                    if (hunter.getStamina() >= 1){
                        ArrayList<Treasure> hoard = hunter.getTreasure();
                        if (hoard.size() < 3) {
                            //subtract required stamina
                            hunter.setStamina(1);
                            //check hunter's treasure array for vacancy
                            getTreasure(hunter,0);
                        }
                        else{
                            Toast.makeText(getContext(),R.string.fullTreasure,Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getContext(), R.string.insufficientStam, Toast.LENGTH_SHORT).show();
                    }
                }//end of EASY HUNT
                if(chosenDifficulty.equals(getResources().getString(R.string.Medium))) {
                    if (hunter.getStamina() >= 3){
                        ArrayList<Treasure> hoard = hunter.getTreasure();
                        if (hoard.size() < 3) {
                            //subtract required stamina
                            hunter.setStamina(3);
                            //check hunter's treasure array for vacancy
                            getTreasure(hunter,1);
                        }
                        else{
                            Toast.makeText(getContext(),R.string.fullTreasure,Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getContext(), R.string.insufficientStam, Toast.LENGTH_SHORT).show();
                    }
                }//end of MEDIUM HUNT
                if(chosenDifficulty.equals(getResources().getString(R.string.Hard))) {
                    if (hunter.getStamina() >= 5){
                        ArrayList<Treasure> hoard = hunter.getTreasure();
                        if (hoard.size() < 3) {
                            //subtract required stamina
                            hunter.setStamina(5);
                            //check hunter's treasure array for vacancy
                            getTreasure(hunter,2);
                        }
                        else{
                            Toast.makeText(getContext(),R.string.fullTreasure,Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getContext(), R.string.insufficientStam, Toast.LENGTH_SHORT).show();
                    }
                }//end of HARD HUNT
                if(chosenDifficulty.equals(getResources().getString(R.string.Nightmare))) {
                    if (hunter.getStamina() >= 10){
                        ArrayList<Treasure> hoard = hunter.getTreasure();
                        if (hoard.size() < 3) {
                            //subtract required stamina
                            hunter.setStamina(10);
                            //check hunter's treasure array for vacancy
                            getTreasure(hunter,2);
                        }
                        else{
                            Toast.makeText(getContext(),R.string.fullTreasure,Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getContext(),R.string.insufficientStam, Toast.LENGTH_SHORT).show();
                    }
                }//end of HARD HUNT

            }
        }
    }

    public void getTreasure(Hunter hunter, int diff) {
        Random random = new Random();
        int chance = random.nextInt(101) + (hunter.getLuck()*4);
        if(hunter.getDead() != 1) {
            if (diff == 0) {
                //EASY loot table
                if (chance < 5) {
                    //didn't find any treasure
                    Toast treasurelessToast = Toast.makeText(getContext(), R.string.unluckyTreasure, Toast.LENGTH_SHORT);
                    treasurelessToast.show();
                } else if (11 <= chance && chance <= 60) {
                    hunter.getTreasure().add(common);
                } else if (61 <= chance && chance <= 101) {
                    hunter.getTreasure().add(uncommon);
                }
                //roll for damage
                chance = random.nextInt(100) + 10 + (hunter.getLuck() * 8);
                if (chance >= 100) {
                    Toast.makeText(getContext(), R.string.luckyDamage, Toast.LENGTH_SHORT).show();
                } else {
                    hunter.setHealth(3);
                    Toast.makeText(getContext(), R.string.unluckyDamage, Toast.LENGTH_SHORT).show();
                    if (hunter.getHealth() <= 0) {
                        listener.onHunterDeath(hunter);
                    }
                }
            } else if (diff == 1) {
                //MEDIUM loot table
                if (chance < 15) {
                    //didn't find any treasure
                    Toast treasurelessToast = Toast.makeText(getContext(), R.string.unluckyTreasure, Toast.LENGTH_SHORT);
                    treasurelessToast.show();
                } else if (26 <= chance && chance <= 55) {
                    hunter.getTreasure().add(common);
                } else if (56 <= chance && chance <= 80) {
                    hunter.getTreasure().add(uncommon);
                } else if (81 <= chance && chance <= 101) {
                    hunter.getTreasure().add(rare);
                }
                //roll for damage
                if (chance >= 100) {
                    Toast.makeText(getContext(), R.string.luckyDamage, Toast.LENGTH_SHORT).show();
                } else {
                    hunter.setHealth(5);
                    Toast.makeText(getContext(), R.string.unluckyDamage, Toast.LENGTH_SHORT).show();
                    if (hunter.getHealth() <= 0) {
                        listener.onHunterDeath(hunter);
                    }
                }
            } else if (diff == 2) {
                //HARD loot table
                if (chance < 20) {
                    //didn't find any treasure
                    Toast treasurelessToast = Toast.makeText(getContext(), R.string.unluckyTreasure, Toast.LENGTH_SHORT);
                    treasurelessToast.show();
                } else if (36 <= chance && chance <= 55) {
                    hunter.getTreasure().add(common);
                } else if (56 <= chance && chance <= 75) {
                    hunter.getTreasure().add(uncommon);
                } else if (76 <= chance && chance <= 90) {
                    hunter.getTreasure().add(rare);
                } else if (91 <= chance && chance <= 101) {
                    hunter.getTreasure().add(epic);
                }
                //roll for damage
                if (chance >= 100) {
                    Toast.makeText(getContext(), R.string.luckyDamage, Toast.LENGTH_SHORT).show();
                } else {
                    hunter.setHealth(9);
                    Toast.makeText(getContext(), R.string.unluckyDamage, Toast.LENGTH_SHORT).show();
                    if (hunter.getHealth() <= 0) {
                        listener.onHunterDeath(hunter);
                    }
                }
            } else if (diff == 3) {
                //NIGHTMARE loot table
                if (chance < 25) {
                    //didn't find any treasure
                    Toast treasurelessToast = Toast.makeText(getContext(), R.string.unluckyTreasure, Toast.LENGTH_SHORT);
                    treasurelessToast.show();
                } else if (26 <= chance && chance <= 30) {
                    hunter.getTreasure().add(common);
                } else if (31 <= chance && chance <= 40) {
                    hunter.getTreasure().add(uncommon);
                } else if (41 <= chance && chance <= 75) {
                    hunter.getTreasure().add(rare);
                } else if (76 <= chance && chance <= 95) {
                    hunter.getTreasure().add(epic);
                } else {
                    hunter.getTreasure().add(legendary);
                }
                //roll for damage
                if (chance >= 100) {
                    Toast.makeText(getContext(), R.string.luckyDamage, Toast.LENGTH_SHORT).show();
                } else {
                    hunter.setHealth(12);
                    Toast.makeText(getContext(), R.string.unluckyDamage, Toast.LENGTH_SHORT).show();
                    if (hunter.getHealth() <= 0) {
                        listener.onHunterDeath(hunter);
                    }
                }
            }
        }else{
            Toast.makeText(getContext(),R.string.deathNotice, Toast.LENGTH_SHORT).show();
        }
    }

        public void updateSpinner(){
            setNames(MainActivity.getHunters());
            THAdapter.clear();
            THAdapter.addAll(names);
            THAdapter.notifyDataSetChanged();
        }
}
