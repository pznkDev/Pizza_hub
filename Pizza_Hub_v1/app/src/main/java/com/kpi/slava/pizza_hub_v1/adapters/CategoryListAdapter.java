package com.kpi.slava.pizza_hub_v1.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kpi.slava.pizza_hub_v1.R;
import com.kpi.slava.pizza_hub_v1.entity.Category;

import java.util.ArrayList;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder> {

    ArrayList<Category> categoryList;

    private final String ID = "Id : ";
    private final String NAME = "Name : ";

    public CategoryListAdapter(ArrayList<Category> categoryList){
        this.categoryList = categoryList;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        holder.tvId.setText(ID + categoryList.get(position).getIdCategory());
        holder.tvName.setText(NAME + categoryList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        TextView tvId, tvName;

        public CategoryViewHolder(final View itemView) {
            super(itemView);

            tvId = (TextView) itemView.findViewById(R.id.tv_category_id);
            tvName = (TextView) itemView.findViewById(R.id.tv_category_name);
        }

    }

}
