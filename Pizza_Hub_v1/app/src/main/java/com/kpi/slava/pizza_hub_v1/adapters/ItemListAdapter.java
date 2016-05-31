package com.kpi.slava.pizza_hub_v1.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.kpi.slava.pizza_hub_v1.R;
import com.kpi.slava.pizza_hub_v1.entity.Item;

import java.util.ArrayList;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder> {

    ArrayList<Item> itemList;

    private final String PRICE = "Cost:";

    public ItemListAdapter(ArrayList<Item> itemList){
        this.itemList = itemList;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_list, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.tvName.setText(itemList.get(position).getName());
        holder.tvPrice.setText(PRICE + itemList.get(position).getPrice());

        if(Integer.parseInt(itemList.get(position).getIdCategory())<=3) holder.imageView.setImageResource(R.drawable.pizza);
        else holder.imageView.setImageResource(R.drawable.beer);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvPrice;
        ImageView imageView;

        public ItemViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.tv_item_list_name);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_item_list_price);
            imageView = (ImageView) itemView.findViewById(R.id.image_item_list);
        }
    }
}
