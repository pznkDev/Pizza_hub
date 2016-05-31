package com.kpi.slava.pizza_hub_v1.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kpi.slava.pizza_hub_v1.R;
import com.kpi.slava.pizza_hub_v1.entity.Item;

import java.sql.SQLOutput;
import java.util.ArrayList;


public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.OrderListViewHolder> {

    ArrayList<Item> itemListOrder;

    public OrderListAdapter(ArrayList<Item> itemListOrder){
        this.itemListOrder = itemListOrder;
    }

    @Override
    public OrderListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_order_list, parent, false);
        return new OrderListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final OrderListViewHolder holder, final int position) {
        holder.tvName.setText(itemListOrder.get(position).getName());
        holder.tvCategory.setText(itemListOrder.get(position).getCategoryName());
        holder.tvQuantity.setText(String.valueOf(itemListOrder.get(position).getQuantity()));
        holder.tvPrice.setText(itemListOrder.get(position).getPrice());

        if(Integer.parseInt(itemListOrder.get(position).getIdCategory())<=3) holder.imageView.setImageResource(R.drawable.pizza);
        else holder.imageView.setImageResource(R.drawable.beer);
    }

    @Override
    public int getItemCount() {
        return itemListOrder.size();
    }

    public class OrderListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvName, tvCategory, tvQuantity, tvPrice;

        ImageButton btnAdd, btnDeduct, btnDelete;

        ImageView imageView;

        public OrderListViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.tv_order_item_name);
            tvCategory = (TextView) itemView.findViewById(R.id.tv_order_item_category);
            tvQuantity = (TextView) itemView.findViewById(R.id.tv_order_item_quantity);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_order_item_price);

            btnAdd = (ImageButton) itemView.findViewById(R.id.ib_order_item_add);
            btnDeduct = (ImageButton) itemView.findViewById(R.id.ib_order_item_deduct);
            btnDelete = (ImageButton) itemView.findViewById(R.id.ib_order_item_delete);

            imageView = (ImageView) itemView.findViewById(R.id.image_order_item);

            btnAdd.setOnClickListener(this);
            btnDeduct.setOnClickListener(this);
            btnDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int quantity = itemListOrder.get(getAdapterPosition()).getQuantity();
            switch(v.getId()){
                case (R.id.ib_order_item_add):
                    quantity++;
                    setValues(quantity);
                    break;

                case (R.id.ib_order_item_deduct):
                    if(quantity>1)
                    quantity--;
                    setValues(quantity);
                    break;

                case (R.id.ib_order_item_delete):
                    itemListOrder.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(), itemListOrder.size());
                    notifyItemRangeChanged(getAdapterPosition(), itemListOrder.size());
            }

            System.out.println("QUANTITY OF ITEMS" + itemListOrder.size());

        }

        public void setValues(int quantity){
            itemListOrder.get(getAdapterPosition()).setQuantity(quantity);
            tvQuantity.setText(String.valueOf(itemListOrder.get(getAdapterPosition()).getQuantity()));
            tvPrice.setText(String.valueOf(Integer.parseInt(itemListOrder.get(getAdapterPosition()).getPrice()) * quantity));
        }
    }
}
