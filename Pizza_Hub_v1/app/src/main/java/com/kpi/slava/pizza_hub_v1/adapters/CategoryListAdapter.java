package com.kpi.slava.pizza_hub_v1.adapters;

import android.support.annotation.IntegerRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kpi.slava.pizza_hub_v1.R;
import com.kpi.slava.pizza_hub_v1.entity.Category;

import java.util.ArrayList;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder> {

    ArrayList<Category> categoryList;

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
        holder.tvName.setText(categoryList.get(position).getName());
        if(Integer.parseInt(categoryList.get(position).getIdCategory())<=3) holder.imageView.setImageResource(R.drawable.pizza);
        else holder.imageView.setImageResource(R.drawable.beer);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;

        ImageView imageView;

        public CategoryViewHolder(final View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.tv_category_name);
            imageView = (ImageView) itemView.findViewById(R.id.image_category);
        }

    }

}
