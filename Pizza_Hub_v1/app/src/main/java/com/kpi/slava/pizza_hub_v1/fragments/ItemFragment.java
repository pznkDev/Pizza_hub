package com.kpi.slava.pizza_hub_v1.fragments;

import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kpi.slava.pizza_hub_v1.Activity.MainActivity;
import com.kpi.slava.pizza_hub_v1.R;
import com.kpi.slava.pizza_hub_v1.entity.Item;

public class ItemFragment extends Fragment {

    public final static String TAG = "ItemFragment";
    private final int LAYOUT = R.layout.fragment_item;

    private View view;

    private Item item;

    private TextView name, description, weight, price;
    private FloatingActionButton btnAdd;

    ImageView imageView;

    private String nameCategory;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        if (bundle != null) {
            item = bundle.getParcelable("item");
            nameCategory = bundle.getString("name_category");
        }

        view = inflater.inflate(LAYOUT, container, false);

        name = (TextView) view.findViewById(R.id.tv_item_name);
        name.setText(item.getName());
        description = (TextView) view.findViewById(R.id.tv_item_description);
        description.setText("Description : " + item.getDescription());
        weight = (TextView) view.findViewById(R.id.tv_item_weight);
        weight.setText("Weight : " + item.getWeight());
        price = (TextView) view.findViewById(R.id.tv_item_price);
        price.setText("Price : " + item.getPrice());

        imageView = (ImageView) view.findViewById(R.id.image_item);

        if(Integer.parseInt(item.getIdCategory())<=3) imageView.setImageResource(R.drawable.pizza);
        else imageView.setImageResource(R.drawable.beer);

        btnAdd = (FloatingActionButton) view.findViewById(R.id.fab_buy_item);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkAvailability(item)) {
                    item.setCategoryName(nameCategory);
                    MainActivity.itemListOrder.add(item);

                    Snackbar.make(view, "Successfully added", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                } else {
                    Snackbar.make(view, "Already added", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                }
            }
        });

        return view;
    }

    private boolean checkAvailability(Item item) {

        if (MainActivity.itemListOrder.size() > 0) {
            for (int i = 0; i < MainActivity.itemListOrder.size(); i++) {
                if (MainActivity.itemListOrder.get(i).getIdItem() == item.getIdItem())
                    return false;
            }
        }

        return true;
    }
}
