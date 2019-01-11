package com.example.csadmin.homework3;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TreasureDetailAdapter extends RecyclerView.Adapter<TreasureDetailAdapter.ViewHolder> {
    private ArrayList<Treasure> treasures;


    public TreasureDetailAdapter(ArrayList<Treasure> treasures){
        this.treasures = treasures;
    }

    @Override
    public int getItemCount(){
        return 3;
    }

    @Override
    public TreasureDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate((R.layout.treasure_detail_card), parent,false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        CardView card = holder.getCard();

        String loot = "grey_x_box";
        //set treasure portrait
        ImageView treasurePortrait = card.findViewById(R.id.treasure_card_portrait);
        if(treasures.isEmpty()){
            loot = "grey_x_box";//leave empty portrait
        }else if(position < treasures.size()){
            loot = treasures.get(position).getRarity();
            treasurePortrait.setImageResource(card.getContext().getResources().getIdentifier(loot, "drawable", "com.example.csadmin.homework3"));
        }
        //set treasure name
        TextView treasureName = card.findViewById(R.id.treasure_card_content);
        if(treasures.isEmpty()){
            treasureName.setText("Empty!");
        }else if(position < treasures.size()){
            treasureName.setText(treasures.get(position).getName());
        }
        //set treasure value
        TextView treasureValue = card.findViewById(R.id.treasure_card_value_text);
        if (treasures.isEmpty()) {
            treasureValue.setText("");
        } else if(position < treasures.size()){
            treasureValue.setText("Value: " + treasures.get(position).getValue());
        }
        //set treasure rarity
        TextView treasureRarity = card.findViewById(R.id.treasure_card_rarity_text);
        if (treasures.isEmpty()) {
            treasureRarity.setText("");
        } else if (position < treasures.size()){
            treasureRarity.setText("Rarity: " + treasures.get(position).getRarity());
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        //define a view to be used for each data item
        private CardView card;

        public ViewHolder(CardView cv){
            super(cv);
            card = cv;
        }

        public CardView getCard(){
            return card;
        }
    }
}

