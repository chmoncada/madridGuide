package com.charlesmoncada.madridguide.manager.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.charlesmoncada.madridguide.model.Shop;
import com.charlesmoncada.madridguide.model.Shops;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

import static com.charlesmoncada.madridguide.manager.db.DBConstants.*;


public class ShopDAO implements DAOPersistable<Shop> {

    private WeakReference<Context> context;
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public ShopDAO(Context context, DBHelper dbHelper) {
        this.context = new WeakReference<>(context);
        this.dbHelper = dbHelper;
        this.db = dbHelper.getDB();
    }

    public ShopDAO(Context context) {
        this(context, DBHelper.getInstance(context));
    }

    /**
     * Insert a shop in DB
     * @param shop shouldn´t be null
     * @return 0 is shop is null, id if insert is OK, INVALID_ID if insert fails
     */
    @Override
    public long insert(@NonNull Shop shop) {
        if (shop == null) {
            return 0;
        }

        db.beginTransaction();
        long id = DBHelper.INVALID_ID;
        try {
            id = db.insert(TABLE_SHOP, KEY_SHOP_NAME, this.getContentValues(shop));
            shop.setId(id);
            db.setTransactionSuccessful(); // COMMIT
        } finally {
            db.endTransaction();
        }

        return id;
    }

    public static @NonNull ContentValues getContentValues(final @NonNull Shop shop) {

        final ContentValues contentValues = new ContentValues();

        if (shop == null) {
            return contentValues;
        }

        contentValues.put(KEY_SHOP_ADDRESS, shop.getAddress());
        contentValues.put(KEY_SHOP_DESCRIPTION, shop.getDescription());
        contentValues.put(KEY_SHOP_IMAGE_URL, shop.getImageUrl());
        contentValues.put(KEY_SHOP_LOGO_IMAGE_URL, shop.getLogoImgUrl());
        contentValues.put(KEY_SHOP_NAME, shop.getName());
        contentValues.put(KEY_SHOP_LATITUDE, shop.getLatitude());
        contentValues.put(KEY_SHOP_LONGITUDE, shop.getLongitude());
        contentValues.put(KEY_SHOP_URL, shop.getUrl());

        return contentValues;
    }

    // EXTRAER A PROPIA CLASE DE MAPEO; METER EL ID CON TRY CATCH
    public static @NonNull Shop getShopFromContentValues(final @NonNull ContentValues contentValues) {
        final Shop shop = new Shop(1,"");

        //shop.setId(contentValues.getAsInteger(KEY_SHOP_ID));
        shop.setAddress(contentValues.getAsString(KEY_SHOP_ADDRESS));
        shop.setDescription(contentValues.getAsString(KEY_SHOP_DESCRIPTION));
        shop.setImageUrl(contentValues.getAsString(KEY_SHOP_IMAGE_URL));
        shop.setLogoImgUrl(contentValues.getAsString(KEY_SHOP_LOGO_IMAGE_URL));
        shop.setName(contentValues.getAsString(KEY_SHOP_NAME));
        shop.setLatitude(contentValues.getAsFloat(KEY_SHOP_LATITUDE));
        shop.setLongitude(contentValues.getAsFloat(KEY_SHOP_LONGITUDE));
        shop.setUrl(contentValues.getAsString(KEY_SHOP_URL));

        return shop;
    }

    @Override
    public void update(long id, @NonNull Shop data) {

    }

    @Override
    public int delete(long id) {

            //db.delete(TABLE_SHOP, KEY_SHOP_ID + " = " + id, null); // 1st way
            //db.delete(TABLE_SHOP, KEY_SHOP_ID + " = ? AND " + KEY_SHOP_NAME, new String[]{ ""+ id, "pepito" }); // 2nd way
            return db.delete(TABLE_SHOP, KEY_SHOP_ID + " = ?", new String[]{ ""+ id }); // 2nd way

    }

    @Override
    public void deleteAll() {

            db.delete(TABLE_SHOP, null,null); // 2nd way

    }

    @Nullable
    @Override
    public Cursor queryCursor() {
        Cursor c = db.query(TABLE_SHOP, ALL_COLUMNS, null, null, null, null, KEY_SHOP_ID);
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
        }
        return c;
    }

    @Override
    public @Nullable Shop query(final long id) {
        Cursor c = db.query(TABLE_SHOP, ALL_COLUMNS, KEY_SHOP_ID + " = " + id, null, null, null, KEY_SHOP_ID);
        if (c != null && c.getCount() == 1) {
            c.moveToFirst();
        } else {
            return null;
        }

        Shop shop = getShop(c);

        return shop;
    }

    @NonNull
    public static Shop getShop(Cursor c) {
        long identifier = c.getLong(c.getColumnIndex(KEY_SHOP_ID));
        String name = c.getString(c.getColumnIndex(KEY_SHOP_NAME));
        Shop shop = new Shop(identifier, name);

        shop.setAddress(c.getString(c.getColumnIndex(KEY_SHOP_ADDRESS)));
        shop.setDescription(c.getString(c.getColumnIndex(KEY_SHOP_DESCRIPTION)));
        shop.setImageUrl(c.getString(c.getColumnIndex(KEY_SHOP_IMAGE_URL)));
        shop.setLogoImgUrl(c.getString(c.getColumnIndex(KEY_SHOP_LOGO_IMAGE_URL)));
        shop.setLatitude(c.getFloat(c.getColumnIndex(KEY_SHOP_LATITUDE)));
        shop.setLongitude(c.getFloat(c.getColumnIndex(KEY_SHOP_LONGITUDE)));
        shop.setUrl(c.getString(c.getColumnIndex(KEY_SHOP_URL)));
        return shop;
    }

    @Nullable
    @Override
    public List<Shop> query() {
        Cursor c = queryCursor();

        if (c == null || !c.moveToFirst()) {
            return null;
        }

        List<Shop> shops = new LinkedList<>();

        c.moveToFirst();
        do {
            Shop shop = getShop(c);
            shops.add(shop);
        } while (c.moveToNext());

        return shops;
    }

    public Cursor queryCursor(long id) {
        Cursor c = db.query(TABLE_SHOP, ALL_COLUMNS, "ID = " + id, null, null, null, KEY_SHOP_ID);
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
        }
        return c;
    }

    @NonNull
    public static Shops getShops(Cursor data) {
        List<Shop> shopList = new LinkedList<>();

        while (data.moveToNext()) {
            Shop shop = ShopDAO.getShop(data);
            shopList.add(shop);
        }

        return Shops.build(shopList);
    }

}
