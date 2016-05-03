
package com.kpi.slava.pizza_hub_v1.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.kpi.slava.pizza_hub_v1.R;
import com.kpi.slava.pizza_hub_v1.entity.Item;
import com.kpi.slava.pizza_hub_v1.fragments.MenuFragment;
import com.kpi.slava.pizza_hub_v1.fragments.MainFragment;
import com.kpi.slava.pizza_hub_v1.fragments.RegistrationFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private DrawerLayout drawer;

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    private MainFragment mainFragment;
    private RegistrationFragment registrationFragment;
    private MenuFragment menuFragment;

    SharedPreferences sharedPreferences;
    final String SAVED_NAME = "Name Saved";
    final String SAVED_NUMBER = "Number Saved";

    public static ArrayList<Item> itemListOrder = new ArrayList<Item>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentManager = getSupportFragmentManager();
        initFragments();

        if(checkName()) {
            registrationFragment.show(fragmentManager, RegistrationFragment.TAG);
        };

        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Main");
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(itemListOrder.size()>0) {
                    Intent orderIntent = new Intent(getApplicationContext(), OrderActivity.class);

                    orderIntent.putExtra("itemListOrder", itemListOrder);

                    startActivity(orderIntent);
                }
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        launchMainFragment();

    }

    private boolean checkName() {
        sharedPreferences = getPreferences(MODE_PRIVATE);
        String name = sharedPreferences.getString(SAVED_NAME, "");
        if(name.equals("")) return true;
        else return false;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        transaction = fragmentManager.beginTransaction();
        switch (item.getItemId()){
            case (R.id.nav_main):
                toolbar.setTitle("Main");
                break;
            case (R.id.nav_menu):
                toolbar.setTitle("Menu");
                if ((fragmentManager.findFragmentByTag(menuFragment.TAG) == null) &&
                        (!fragmentManager.popBackStackImmediate("Menu", 1))) {
                    transaction.replace(R.id.layout_container, menuFragment);
                }
                break;
            case (R.id.nav_news):
                toolbar.setTitle("News");
                break;
            case (R.id.nav_comment):
                toolbar.setTitle("Comments");
                break;
            case (R.id.nav_settings):
                toolbar.setTitle("Settings");
                break;
            case (R.id.nav_close):
                finish();
                break;
        }
        transaction.commit();
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initFragments() {
        mainFragment = new MainFragment();
        registrationFragment = new RegistrationFragment();
        menuFragment = new MenuFragment();
    }

    private void launchMainFragment() {
        transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.layout_container, mainFragment);
        transaction.commit();
    }
}
