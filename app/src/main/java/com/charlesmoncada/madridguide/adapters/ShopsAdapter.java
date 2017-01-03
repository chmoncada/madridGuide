package com.charlesmoncada.madridguide.adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.charlesmoncada.madridguide.R;
import com.charlesmoncada.madridguide.model.Shop;
import com.charlesmoncada.madridguide.model.Shops;
import com.charlesmoncada.madridguide.util.OnElementClick;
import com.charlesmoncada.madridguide.views.ShopRowViewHolder;

import java.util.LinkedList;
import java.util.List;

public class ShopsAdapter extends RecyclerView.Adapter<ShopRowViewHolder> {

    private final LayoutInflater layoutInflater;
    private final Shops shops;

    private List<OnElementClick<Shop>> listeners;

    public ShopsAdapter(Shops shops, Context context) {
        this.shops = shops;
        this.layoutInflater = LayoutInflater.from(context);

    }

    @Override
    public ShopRowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.row_shop, parent, false);

        return new ShopRowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShopRowViewHolder row, final int position) {

        final Shop shop = shops.get(position);

        row.setShop(shop);

        row.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (OnElementClick<Shop> listener: getListeners()) {
                    listener.elementClicked(shop, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (int) shops.size();
    }

    public List<OnElementClick<Shop>> getListeners() {
        if (listeners == null) {
            listeners = new LinkedList<>();
        }
        return listeners;
    }

    public void  setOnElementClickListener(@NonNull final OnElementClick<Shop> listener) {
        getListeners().add(listener);
    }

}
