package com.kpi.slava.pizza_hub_v1.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kpi.slava.pizza_hub_v1.Activity.MainActivity;
import com.kpi.slava.pizza_hub_v1.R;
import com.kpi.slava.pizza_hub_v1.entity.Item;

public class ItemFragment extends Fragment {

    public final static String TAG = "ItemFragment";
    private final int LAYOUT = R.layout.fragment_item;

    private View view;

    private Item item;

    private TextView name, idCategory, description, weight, price;
    private Button btnAdd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        if (bundle != null) {
            item = bundle.getParcelable("item");
        }

        view = inflater.inflate(LAYOUT, container, false);

        name = (TextView) view.findViewById(R.id.tv_item_name);
        name.setText(item.getName());
        idCategory = (TextView) view.findViewById(R.id.tv_item_id_category);
        idCategory.setText(item.getIdCategory());
        description = (TextView) view.findViewById(R.id.tv_item_description);
        description.setText("Description : " + item.getDescription());
        weight = (TextView) view.findViewById(R.id.tv_item_weight);
        weight.setText("Weight : " + item.getWeight());
        price = (TextView) view.findViewById(R.id.tv_item_price);
        price.setText("Price : " + item.getPrice());

        btnAdd = (Button) view.findViewById(R.id.btn_item_add_item);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkAvailability(item)) {
                    MainActivity.itemListOrder.add(item);
                    Snackbar.make(view, "Successful added", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
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
