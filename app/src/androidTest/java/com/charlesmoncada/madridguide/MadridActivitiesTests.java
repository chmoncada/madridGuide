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
        assertNotNull(sut.all());
    }

    public void testCreatingAMadridActivitiesWithAListReturnsNonNullMadridActivities() {
        List<MadridActivity> data = getMadridActivities();

        MadridActivities sut = MadridActivities.build(data);
        assertNotNull(sut);
        assertNotNull(sut.all());
        assertEquals(sut.all(), data);
        assertEquals(sut.all().size(), data.size());
    }

    @NonNull
    private List<MadridActivity> getMadridActivities() {
        List<MadridActivity> data = new ArrayList<>();
        MadridActivity activity1 = new MadridActivity(1, "1");
        activity1.setAddress("AD 1");
        data.add(activity1);
        MadridActivity activity2 = new MadridActivity(2, "2");
        activity2.setAddress("AD 2");
        data.add(activity2);

        data.add(activity1);
        data.add(activity2);
        return data;
    }

}
