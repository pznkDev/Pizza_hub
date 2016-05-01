package com.kpi.slava.pizza_hub_v1.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kpi.slava.pizza_hub_v1.R;
import com.kpi.slava.pizza_hub_v1.entity.Item;

import java.util.ArrayList;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder> {

    ArrayList<Item> itemList;

    private final String ID = "Id : ";
    private final String NAME = "Name : ";
    private final String ID_CATEGORY= "Id_category: ";
    private final String DESCRIPTION = "Description : ";
    private final String WEIGHT = "Weight : ";
    private final String PRICE = "Price : ";

    public ItemListAdapter(ArrayList<Item> itemList){
        this.itemList = itemList;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.entity_item_list, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.tvId.setText(ID + itemList.get(position).getIdItem());
        holder.tvName.setText(NAME + itemList.get(position).getName());
        holder.tvIdCategory.setText(ID_CATEGORY + itemList.get(position).getIdCategory());
        holder.tvDescription.setText(DESCRIPTION + itemList.get(position).getDescription());
        holder.tvWeight.setText(WEIGHT + itemList.get(position).getWeight());
        holder.tvPrice.setText(PRICE + itemList.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvId, tvName, tvIdCategory, tvDescription, tvWeight, tvPrice;

        public ItemViewHolder(View itemView) {
            super(itemView);

            tvId = (TextView) itemView.findViewById(R.id.tv_item_list_id);
            tvName = (TextView) itemView.findViewById(R.id.tv_item_list_name);
            tvIdCategory = (TextView) itemView.findViewById(R.id.tv_item_list_id_category);
            tvDescription = (TextView) itemView.findViewById(R.id.tv_item_list_description);
            tvWeight = (TextView) itemView.findViewById(R.id.tv_item_list_weight);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_item_list_price);
        }
    }
}
