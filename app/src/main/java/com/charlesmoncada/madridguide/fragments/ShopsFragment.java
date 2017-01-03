package com.charlesmoncada.madridguide.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.charlesmoncada.madridguide.R;
import com.charlesmoncada.madridguide.adapters.ShopsAdapter;
import com.charlesmoncada.madridguide.model.Shop;
import com.charlesmoncada.madridguide.model.Shops;
import com.charlesmoncada.madridguide.util.OnElementClick;


public class ShopsFragment extends Fragment {
    private RecyclerView shopsRecyclerView;
    private ShopsAdapter adapter;
    private Shops shops;

    private OnElementClick<Shop> listener;

    public ShopsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shops, container, false);
        shopsRecyclerView = (RecyclerView) view.findViewById(R.id.shops_recycler_view);
        shopsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle(R.string.shops_activity_title);
        //activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //updateUI();

        return view;
    }

    private void updateUI() {

        adapter = new ShopsAdapter(shops, getActivity());
        shopsRecyclerView.setAdapter(adapter);

        adapter.setOnElementClickListener(new OnElementClick<Shop>() {

            @Override
            public void elementClicked(Shop shop, int position) {
                if (listener != null) {
                    listener.elementClicked(shop, position);
                }
            }

        });

    }

    private Shops getShops() {
        return shops;
    }

    public void setShops(Shops shops) {
        this.shops = shops;

        updateUI();
    }

    public OnElementClick<Shop> getListener() {
        return listener;
    }

    public  void setOnElementClickListener(@NonNull final OnElementClick<Shop> listener) {
        this.listener = listener;
    }

}
