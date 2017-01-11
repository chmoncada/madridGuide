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
    public static final String DESCRIPTION_ES = "DESCRIPTION_ES";
    public static final String DESCRIPTION_EN = "DESCRIPTION_EN";
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

        MadridActivity sut = new MadridActivity(ID, ACTIVITY);
        sut.setImageUrl(IMAGE_URL);
        sut.setLogoImgUrl(LOGO_IMAGE_URL);
        sut.setAddress(ADDRESS);
        sut.setUrl(URL);
        sut.setDescriptionEs(DESCRIPTION_ES);
        sut.setDescriptionEn(DESCRIPTION_EN);
        sut.setLatitude(LATITUDE);
        sut.setLongitude(LONGITUDE);

        assertEquals(sut.getImageUrl(), IMAGE_URL);
        assertEquals(sut.getLogoImgUrl(), LOGO_IMAGE_URL);
        assertEquals(sut.getAddress(), ADDRESS);
        assertEquals(sut.getUrl(), URL);
        assertEquals(sut.getDescriptionEn(), DESCRIPTION_EN);
        assertEquals(sut.getDescriptionEs(), DESCRIPTION_ES);
        assertEquals(sut.getLatitude(), LATITUDE);
        assertEquals(sut.getLongitude(), LONGITUDE);




    }

}
