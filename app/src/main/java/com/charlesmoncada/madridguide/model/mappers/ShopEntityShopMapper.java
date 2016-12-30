package com.charlesmoncada.madridguide.model.mappers;


import com.charlesmoncada.madridguide.manager.net.ShopEntity;
import com.charlesmoncada.madridguide.model.Shop;

import java.util.LinkedList;
import java.util.List;

public class ShopEntityShopMapper {
    public List<Shop> map(List<ShopEntity> shopEntities) {
        List<Shop> result = new LinkedList<>();

        for (ShopEntity entity: shopEntities) {
            Shop shop = new Shop(entity.getId(), entity.getName());
            // detect current lang
            shop.setDescriptionEs(entity.getDescriptionEs());
            shop.setDescriptionEn(entity.getDescriptionEn());
            shop.setLogoImgUrl(entity.getLogoImg());
            shop.setImageUrl(entity.getImg());
            shop.setAddress(entity.getAddress());
            shop.setUrl(entity.getUrl());
            shop.setLatitude(entity.getLatitude());
            shop.setLongitude(entity.getLongitude());

            result.add(shop);
        }

        return result;
    }
}
