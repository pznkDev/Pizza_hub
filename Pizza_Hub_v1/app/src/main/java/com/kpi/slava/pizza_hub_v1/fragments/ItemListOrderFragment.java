package com.kpi.slava.pizza_hub_v1.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kpi.slava.pizza_hub_v1.Activity.MainActivity;
import com.kpi.slava.pizza_hub_v1.R;
import com.kpi.slava.pizza_hub_v1.adapters.CategoryListAdapter;
import com.kpi.slava.pizza_hub_v1.adapters.OrderListAdapter;
import com.kpi.slava.pizza_hub_v1.entity.Item;

import java.util.ArrayList;

public class ItemListOrderFragment extends Fragment {

    public static final String TAG = "ItemListOrderFragment";

    private final int LAYOUT = R.layout.fragment_item_list_order;

    private View view;

    ArrayList<Item> itemListOrder;

    RecyclerView rvItemListOrder;

    FloatingActionButton fabNext, fabBack;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        if (bundle != null) {
            itemListOrder = bundle.getParcelableArrayList("itemListOrder");
        }

        for (int i = 0; i < itemListOrder.size(); i++) {
            System.out.println("id=" + itemListOrder.get(i).getIdItem() + "\n"+
                    "name=" + itemListOrder.get(i).getName() + "\n"+
                    "category=" + itemListOrder.get(i).getCategoryName());
        }

        view = inflater.inflate(LAYOUT, container, false);

        rvItemListOrder = (RecyclerView) view.findViewById(R.id.rv_order_list);
        rvItemListOrder.setLayoutManager(new LinearLayoutManager(getContext()));
        rvItemListOrder.setAdapter(new OrderListAdapter(itemListOrder));

        fabBack = (FloatingActionButton) view.findViewById(R.id.fab_order_back);
        fabNext = (FloatingActionButton) view.findViewById(R.id.fab_order_next);

        fabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentResult = new Intent();
                intentResult.putExtra("itemList", itemListOrder);
                getActivity().setResult(Activity.RESULT_OK, intentResult);
                getActivity().finish();
            }
        });

        fabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OrderPlaceFragment orderPlaceFragment = new OrderPlaceFragment();

                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("itemListOrder", itemListOrder);
                orderPlaceFragment.setArguments(bundle);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                orderPlaceFragment.show(fragmentManager, OrderPlaceFragment.TAG);
            }
        });

        return view;
    }
}
