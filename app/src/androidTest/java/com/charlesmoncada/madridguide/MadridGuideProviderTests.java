package com.charlesmoncada.madridguide;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.test.AndroidTestCase;

import com.charlesmoncada.madridguide.manager.db.DBConstants;
import com.charlesmoncada.madridguide.manager.db.ShopDAO;
import com.charlesmoncada.madridguide.manager.db.provider.MadridGuideProvider;
import com.charlesmoncada.madridguide.model.Shop;


public class MadridGuideProviderTests extends AndroidTestCase {

    public void testQueryAllShops() {
        ContentResolver cr = getContext().getContentResolver();

        Cursor c = cr.query(MadridGuideProvider.SHOPS_URI, DBConstants.TABLE_SHOP_ALL_COLUMNS, null, null, null);
        assertNotNull(c);

    }

    public void testInsertAShop() {

        final ContentResolver cr = getContext().getContentResolver();

        final Cursor beforeCursor = cr.query(MadridGuideProvider.SHOPS_URI, DBConstants.TABLE_SHOP_ALL_COLUMNS, null, null, null);
        final int beforeCount = beforeCursor.getCount();

        final Shop shop = new Shop(1, "Little shop of horrors!");
        final Uri insertedUri = cr.insert(MadridGuideProvider.SHOPS_URI, ShopDAO.getContentValues(shop));
        assertNotNull(insertedUri);

        final Cursor afterCursor = cr.query(MadridGuideProvider.SHOPS_URI, DBConstants.TABLE_SHOP_ALL_COLUMNS, null, null, null);
        final int afterCount = afterCursor.getCount();

        assertEquals(beforeCount + 1, afterCount);
    }

    public void testDeleteAShop() {

        final ContentResolver cr = getContext().getContentResolver();

        final Shop shop = new Shop(1, "Star Wars");
        final Uri insertedUri = cr.insert(MadridGuideProvider.SHOPS_URI, ShopDAO.getContentValues(shop));

        final Cursor beforeCursor = cr.query(MadridGuideProvider.SHOPS_URI, DBConstants.TABLE_SHOP_ALL_COLUMNS, null, null,null);
        final int beforeCount = beforeCursor.getCount();

        cr.delete(insertedUri, null, null );

        final Cursor afterCursor = cr.query(MadridGuideProvider.SHOPS_URI, DBConstants.TABLE_SHOP_ALL_COLUMNS, null, null,null);
        final int afterCount = afterCursor.getCount();

        assertEquals(afterCount + 1, beforeCount);

    }


}
