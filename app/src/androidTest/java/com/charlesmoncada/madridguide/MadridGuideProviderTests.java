package com.charlesmoncada.madridguide;

import android.content.ContentResolver;
import android.database.Cursor;
import android.test.AndroidTestCase;

import com.charlesmoncada.madridguide.manager.db.DBConstants;
import com.charlesmoncada.madridguide.manager.db.provider.MadridGuideProvider;


public class MadridGuideProviderTests extends AndroidTestCase {

    public void testQueryAllShops() {
        ContentResolver cr = getContext().getContentResolver();

        Cursor c = cr.query(MadridGuideProvider.SHOPS_URI, DBConstants.ALL_COLUMNS, null, null, null);
        assertNotNull(c);

    }


}
