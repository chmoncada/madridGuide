package com.charlesmoncada.madridguide;

import android.test.AndroidTestCase;

import com.charlesmoncada.madridguide.model.MadridActivity;


public class MadridActivityTests extends AndroidTestCase {

    public static final String ACTIVITY = "activity";
    public static final long ID = 99;
    public static final String IMAGE_URL = "IMAGE_URL";
    public static final String LOGO_IMAGE_URL = "LOGO_IMAGE_URL";
    public static final String ADDRESS = "ADDRESS";
    public static final String URL = "URL";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final float LATITUDE = (float) -12.02;
    public static final float LONGITUDE = (float) -77.01;


    public void testCanCreateAMadridActivity() {
        MadridActivity sut = new MadridActivity(ID, ACTIVITY);
        assertNotNull(sut);
    }

    public void testANewMadridActivityStoresDataCorrectly(){
        MadridActivity sut = new MadridActivity(ID, ACTIVITY);
        assertEquals(ID, sut.getId());
        assertEquals(ACTIVITY, sut.getName());
    }

    public void testANewMadridActivityStoresDataInPropertiesCorrectly() {

        MadridActivity sut = new MadridActivity(ID, ACTIVITY)
                .setImageUrl(IMAGE_URL)
                .setLogoImgUrl(LOGO_IMAGE_URL)
                .setAddress(ADDRESS)
                .setUrl(URL)
                .setDescription(DESCRIPTION)
                .setLatitude(LATITUDE)
                .setLongitude(LONGITUDE);

        assertEquals(sut.getImageUrl(), IMAGE_URL);
        assertEquals(sut.getLogoImgUrl(), LOGO_IMAGE_URL);
        assertEquals(sut.getAddress(), ADDRESS);
        assertEquals(sut.getUrl(), URL);
        assertEquals(sut.getDescription(), DESCRIPTION);
        assertEquals(sut.getLatitude(), LATITUDE);
        assertEquals(sut.getLongitude(), LONGITUDE);




    }

}
