package com.charlesmoncada.madridguide.manager.net;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ActivityResponse {
    @SerializedName("result") List<ActivityEntity> result;
}
