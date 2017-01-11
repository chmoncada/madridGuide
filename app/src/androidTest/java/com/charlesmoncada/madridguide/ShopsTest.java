package com.charlesmoncada.madridguide;

import android.support.annotation.NonNull;
import android.test.AndroidTestCase;

import com.charlesmoncada.madridguide.model.Shop;
import com.charlesmoncada.madridguide.model.Shops;

import java.util.ArrayList;
import java.util.List;


public class ShopsTest extends AndroidTestCase {

    public void testCreatingAShopsWithNullListReturnsNonNullShops() {
        Shops sut = Shops.build(null);
        assertNotNull(sut);
        assertNotNull(sut.all());
    }

    public void testCreatingAShopsWithAListReturnsNonNullShops() {
        List<Shop> data = getShops();

        Shops sut = Shops.build(data);
        assertNotNull(sut);
        assertNotNull(sut.all());
        assertEquals(sut.all(), data);
        assertEquals(sut.all().size(), data.size());

    }

    @NonNull
    private List<Shop> getShops() {
        List<Shop> data = new ArrayList<>();
        Shop shop1 = new Shop(1, "1");
        shop1.setAddress("AD 1");
        data.add(shop1);
        Shop shop2 = new Shop(2, "2");
        shop2.setAddress("AD 2");
        data.add(shop2);
        return data;
    }
}
