package com.kpi.slava.pizza_hub_v1.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kpi.slava.pizza_hub_v1.R;

public class MainFragment extends Fragment {

    public static String TAG = "MainFragment";
    private final int LAYOUT = R.layout.fragment_main;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(LAYOUT, container, false);

        return view;
    }
}
