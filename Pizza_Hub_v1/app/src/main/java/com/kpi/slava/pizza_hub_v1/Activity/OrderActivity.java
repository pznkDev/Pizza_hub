package com.kpi.slava.pizza_hub_v1.Activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kpi.slava.pizza_hub_v1.R;
import com.kpi.slava.pizza_hub_v1.entity.Item;
import com.kpi.slava.pizza_hub_v1.fragments.ItemListOrderFragment;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {

    ArrayList<Item> itemListOrder = new ArrayList<Item>();

    FragmentManager fragmentManager;
    FragmentTransaction transaction;

    ItemListOrderFragment orderListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Intent transferIntent = getIntent();
        itemListOrder = transferIntent.getParcelableArrayListExtra("itemListOrder");

        orderListFragment = new ItemListOrderFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("itemListOrder", itemListOrder);
        orderListFragment.setArguments(bundle);

        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.layout_order_container, orderListFragment).commit();

    }
}
