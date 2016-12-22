package com.charlesmoncada.madridguide;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.charlesmoncada.madridguide.adapters.ShopsAdapter;
import com.charlesmoncada.madridguide.model.Shop;
import com.charlesmoncada.madridguide.model.Shops;


public class ShopsFragment extends Fragment {
    private RecyclerView shopsRecyclerView;
    private ShopsAdapter adapter;
    // ANADIDO CLASE 4.1
    private Shops shops;

    public ShopsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shops, container, false);
        shopsRecyclerView = (RecyclerView) view.findViewById(R.id.shops_recycler_view);
        shopsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //updateUI();

        return view;
    }

    private void updateUI() {
        //Shops shops = Shops.build(getShops());

        adapter = new ShopsAdapter(shops, getActivity());
        shopsRecyclerView.setAdapter(adapter);

        adapter.setOnElementClickListener(new ShopsAdapter.OnElementClick() {

            @Override
            public void clickedOn(Shop shop, int position) {
                // TODO: show shop detail
                Log.v("FRAGMENT", "ME DEBERIA IR A LA OTRA VISTA DE DETALLE");
            }
        });

    }

//    private List<Shop> getShops() {
//        List<Shop> data = new ArrayList<>();
//
//        for (int i = 0; i<100; i++) {
//            data.add(new Shop(i, "Tienda " + i));
//        }
//
//        return data;
//    }

    private Shops getShops() {
        return shops;
    }

    public void setShops(Shops shops) {
        this.shops = shops;

        updateUI();
    }

}
