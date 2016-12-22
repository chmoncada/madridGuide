package com.charlesmoncada.madridguide.manager.db.provider;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.charlesmoncada.madridguide.manager.db.ShopDAO;
import com.charlesmoncada.madridguide.model.Shop;

public class MadridGuideProvider extends ContentProvider {

    public static final String MADRIDGUIDE_PROVIDER = "com.charlesmoncada.madridguide.provider";

    public static final Uri SHOPS_URI = Uri.parse("content://" + MADRIDGUIDE_PROVIDER + "/shops");

    // Create the constants used to differentiate between the different URI requests.
    private static final int ALL_SHOPS = 1;
    private static final int SINGLE_SHOP = 2;

    private static final UriMatcher uriMatcher;
    // Populate the UriMatcher object, where a URI ending in ÔelementsÕ will correspond to a request for all items, and Ôelements/[rowID]Õ represents a single row.
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(MADRIDGUIDE_PROVIDER, "shops", ALL_SHOPS);
        uriMatcher.addURI(MADRIDGUIDE_PROVIDER, "shops/#", SINGLE_SHOP);
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {

        ShopDAO dao = new ShopDAO(getContext());

        Cursor cursor = null;
        switch (uriMatcher.match(uri)) {
            case SINGLE_SHOP :
                String rowID = uri.getPathSegments().get(1);
                cursor = dao.queryCursor(Long.parseLong(rowID));
                break;
            case ALL_SHOPS:
                cursor = dao.queryCursor();
                break;
            default: break;
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {

        ShopDAO dao = new ShopDAO(getContext());

        Shop shop = new Shop(contentValues.getAsInteger("id"), contentValues.getAsString("name"));
        shop.setImageUrl(contentValues.getAsString("imageURL"));

        long id = dao.insert(shop);
        // Construct and return the URI of the newly inserted row.
        if (id == -1) {
            return null;
        }
        // Construct and return the URI of the newly inserted row.
        Uri insertedUri = null;
        switch (uriMatcher.match(uri)) {
            case ALL_SHOPS:
                insertedUri = ContentUris.withAppendedId(SHOPS_URI, id);
                break;
            default:
                break;
        }

        // Notify any observers of the change in the data set.
        getContext().getContentResolver().notifyChange(uri, null);
        getContext().getContentResolver().notifyChange(insertedUri, null);

        return insertedUri;

    }

    @Override
    public int delete(Uri uri, String where, String[] whereSelection) {
        // Open a read / write database to support the transaction.

        ShopDAO dao = new ShopDAO(getContext());
        int deleteCount = 0;

        // If this is a row URI, limit the deletion to the specified row.
        switch (uriMatcher.match(uri)) {
            case SINGLE_SHOP:
                String rowID = uri.getPathSegments().get(1);
                deleteCount = dao.delete(Long.parseLong(rowID));
                break;
            case ALL_SHOPS:
                dao.deleteAll();
                break;
            default:
                break;
        }

        // Notify any observers of the change in the data set.
        getContext().getContentResolver().notifyChange(uri, null);

        // Return the number of deleted items.
        return deleteCount;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
