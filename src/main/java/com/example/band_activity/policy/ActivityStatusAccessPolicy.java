package com.example.band_activity.policy;

import com.example.band_activity.activity.Activity;
import com.example.band_activity.activity.ActivityStatus;
import com.example.band_activity.activity.exception.NotOpenedActivityException;

public class ActivityStatusAccessPolicy {
    public static void isOpened(Activity activity){
        if(activity.getStatus() != ActivityStatus.OPENED){
            throw new NotOpenedActivityException("유효 활동 아님", activity.getId(), activity.getStatus().getDisplay());
        }
    }
}
