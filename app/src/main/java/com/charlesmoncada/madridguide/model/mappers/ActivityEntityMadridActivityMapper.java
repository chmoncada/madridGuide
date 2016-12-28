package com.charlesmoncada.madridguide.model.mappers;


import com.charlesmoncada.madridguide.manager.net.ActivityEntity;
import com.charlesmoncada.madridguide.model.MadridActivity;

import java.util.LinkedList;
import java.util.List;

public class ActivityEntityMadridActivityMapper {

    public List<MadridActivity> map(List<ActivityEntity> activityEntities) {
        List<MadridActivity> result = new LinkedList<>();

        for (ActivityEntity entity: activityEntities) {
            MadridActivity activity = new MadridActivity(entity.getId(), entity.getName());
            // detect current lang
            activity.setDescription(entity.getDescriptionEs());

            activity.setLogoImgUrl(entity.getLogoImg());
            activity.setImageUrl(entity.getImg());
            activity.setAddress(entity.getAddress());
            activity.setUrl(entity.getUrl());
            activity.setLatitude(entity.getLatitude());
            activity.setLongitude(entity.getLongitude());

            result.add(activity);
        }

        return result;
    }



}
