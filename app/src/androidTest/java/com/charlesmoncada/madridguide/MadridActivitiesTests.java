package com.charlesmoncada.madridguide;

import android.support.annotation.NonNull;
import android.test.AndroidTestCase;

import com.charlesmoncada.madridguide.model.MadridActivities;
import com.charlesmoncada.madridguide.model.MadridActivity;

import java.util.ArrayList;
import java.util.List;


public class MadridActivitiesTests extends AndroidTestCase {

    public void testCreatingAMadridActivitiesWithNullListReturnNonNullMadridActivities() {
        MadridActivities sut = MadridActivities.build(null);
        assertNotNull(sut);
        assertNotNull(sut.allActivities());
    }

    public void testCreatingAMadridActivitiesWithAListReturnsNonNullMadridActivities() {
        List<MadridActivity> data = getMadridActivities();

        MadridActivities sut = MadridActivities.build(data);
        assertNotNull(sut);
        assertNotNull(sut.allActivities());
        assertEquals(sut.allActivities(), data);
        assertEquals(sut.allActivities().size(), data.size());
    }

    @NonNull
    private List<MadridActivity> getMadridActivities() {
        List<MadridActivity> data = new ArrayList<>();
        data.add(new MadridActivity(1, "1").setAddress("AD 1"));
        data.add(new MadridActivity(2, "2").setAddress("AD 2"));
        return data;
    }

}
