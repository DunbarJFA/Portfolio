package com.example.csadmin.homework3;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class HunterDetailAdapter extends RecyclerView.Adapter<HunterDetailAdapter.ViewHolder> {
    private ArrayList<Hunter> hunters;
    private Listener listener;

    public HunterDetailAdapter(ArrayList<Hunter> hunters){
        this.hunters = hunters;
    }

    @Override
    public int getItemCount(){
        return hunters.size();
    }

    @Override
    public HunterDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate((R.layout.hunter_detail_card),parent, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position){
        CardView card = holder.getCard();
        String loot = "no_loot";
        int lootSize = hunters.get(position).getTreasure().size();
        if (lootSize == 0){
            loot = "no_loot";
        }else if(lootSize == 1){
            loot = "one_loot";
        }else if(lootSize == 2){
            loot = "two_loot";
        }else if(lootSize == 3){
            loot = "three_loot";
        }
    //Determine proper treasure gauge image
        ImageView lootGauge = card.findViewById(R.id.treasure_inventory);
        lootGauge.setImageResource(card.getContext().getResources().getIdentifier(loot,"drawable","com.example.csadmin.homework3"));
    //Determine proper sprite image
        ImageView sprite = card.findViewById(R.id.card_sprite);
        sprite.setImageResource(card.getContext().getResources().getIdentifier(hunters.get(position).getSpriteID(),"drawable","com.example.csadmin.homework3"));
    //Determine proper name for display
        TextView name = card.findViewById(R.id.card_name_text);
        name.setText(hunters.get(position).getName());
    //Determine proper stamina for display
        TextView stamina = card.findViewById(R.id.card_stamina_text);
        String staminaGauge = "Stamina: " + hunters.get(position).getStamina().toString();
        stamina.setText(staminaGauge);
    //setting up a click listener
        card.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v){
               if(listener != null){
                   listener.onClick(hunters.get(position));
               }
           }
        });


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

    interface Listener{
        void onClick(Hunter hunter);
    }

    public void setListener(Listener listener){
        this.listener = listener;
    }
}
