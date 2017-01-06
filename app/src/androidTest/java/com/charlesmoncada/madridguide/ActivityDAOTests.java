package com.charlesmoncada.madridguide;

import android.database.Cursor;
import android.test.AndroidTestCase;

import com.charlesmoncada.madridguide.manager.db.ActivityDAO;
import com.charlesmoncada.madridguide.model.MadridActivity;


public class ActivityDAOTests extends AndroidTestCase {

    public static final String MADRID_ACTIVITY_TESTING_NAME = "MADRID_ACTIVITY_TESTING_NAME";
    public static final String ADDRESS_TESTING = "ADDRESS_TESTING";

    public void testCanInsertANewMadridActivity() {
        final ActivityDAO sut = new ActivityDAO(getContext());

        final int count = getCount(sut);

        final long id = insertTestMadridActivity(sut);

        assertTrue(id>0);
        assertTrue(count + 1 == sut.queryCursor().getCount());

    }

    public void testCanDeleteAMadridActivity() {
        final ActivityDAO sut = new ActivityDAO(getContext());

        final long id = insertTestMadridActivity(sut);

        final int count = getCount(sut);

        assertEquals(1, sut.delete(id));
        assertTrue(count - 1 == sut.queryCursor().getCount());
    }

    private long insertTestMadridActivity(ActivityDAO sut) {
        final MadridActivity activity = new MadridActivity(1, MADRID_ACTIVITY_TESTING_NAME).setAddress(ADDRESS_TESTING);
        return sut.insert(activity);
    }


    private int getCount(ActivityDAO sut) {
        final Cursor cursor = sut.queryCursor();
        return cursor.getCount();
    }

}
