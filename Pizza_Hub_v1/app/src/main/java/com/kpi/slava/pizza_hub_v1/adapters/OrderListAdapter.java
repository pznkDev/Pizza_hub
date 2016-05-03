package com.kpi.slava.pizza_hub_v1.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
        holder.tvName.setText((position+1) + ") " + itemListOrder.get(position).getName());
        holder.tvQuantity.setText(String.valueOf(itemListOrder.get(position).getQuantity()));
        holder.tvPrice.setText(itemListOrder.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return itemListOrder.size();
    }

    public class OrderListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvName, tvQuantity, tvPrice;

        ImageButton btnAdd, btnDeduct, btnDelete;

        public OrderListViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.tv_order_item_name);
            tvQuantity = (TextView) itemView.findViewById(R.id.tv_order_item_quantity);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_order_item_price);

            btnAdd = (ImageButton) itemView.findViewById(R.id.ib_order_item_add);
            btnDeduct = (ImageButton) itemView.findViewById(R.id.ib_order_item_deduct);
            btnDelete = (ImageButton) itemView.findViewById(R.id.ib_order_item_delete);

            btnAdd.setOnClickListener(this);
            btnDeduct.setOnClickListener(this);
            btnDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int quantity = itemListOrder.get(getAdapterPosition()).getQuantity();
            int totalPrice = 0;
            switch(v.getId()){
                case (R.id.ib_order_item_add):
                    quantity++;
                    setValues(quantity);
                    break;

                case (R.id.ib_order_item_deduct):
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
