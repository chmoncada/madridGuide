package com.charlesmoncada.madridguide.model;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Shops implements Iterable, Updatable {

    List<Shop> shops;

    public static @NonNull Shops build(@NonNull final List<Shop> shopList) {
        Shops shops = new Shops(shopList);

        if (shopList == null) {
            shops.shops = new ArrayList<>();
        }

        return shops;
    }

    public static @NonNull Shops build() {
        return build(new ArrayList<Shop>());
    }

    private Shops(List<Shop> shops) {
        this.shops = shops;
    }

    private Shops() {
    }

    @Override
    public long size() {
        return shops.size();
    }

    @Override
    public Shop get(long index) {
        return shops.get((int) index);
    }

    @Override
    public List<Shop> all() {
        return shops;
    }

    @Override
    public void add(Object element) {
        Shop shop = (Shop) element;
        shops.add(shop);
    }

    @Override
    public void delete(Object element) {
        Shop shop = (Shop) element;
        shops.remove(shop);
    }

    @Override
    public void edit(Object newElement, long index) {
        Shop newShop = (Shop) newElement;
        shops.set((int) index, newShop);
    }

    public List<Shop> query(String query) {

        List<Shop> result = new LinkedList<>();

        for (Shop shop: shops) {
            if (containsIgnoreCase(shop.getName(), query)) {
                result.add(shop);
            }
        }

        return  result;

    }

    private static boolean containsIgnoreCase(String src, String what) {
        final int length = what.length();
        if (length == 0)
            return true; // Empty string is contained

        final char firstLo = Character.toLowerCase(what.charAt(0));
        final char firstUp = Character.toUpperCase(what.charAt(0));

        for (int i = src.length() - length; i >= 0; i--) {
            // Quick check before calling the more expensive regionMatches() method:
            final char ch = src.charAt(i);
            if (ch != firstLo && ch != firstUp)
                continue;

            if (src.regionMatches(true, i, what, 0, length))
                return true;
        }

        return false;
    }



}
