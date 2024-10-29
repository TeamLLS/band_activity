package com.example.band_activity.policy;

import com.example.band_activity.activity.Activity;
import com.example.band_activity.activity.ActivityStatus;
import com.example.band_activity.activity.exception.ActivityNotClosedException;
import com.example.band_activity.activity.exception.ActivityNotOpenedException;

public class ActivityStatusAccessPolicy {
    public static void isOpened(Activity activity){
        if(activity.getStatus() != ActivityStatus.OPENED){
            throw new ActivityNotOpenedException("유효 활동 아님", activity.getId(), activity.getStatus().getDisplay());
        }
    }


    public static void isClosed(Activity activity){
        if(activity.getStatus() != ActivityStatus.CLOSED){
            throw new ActivityNotClosedException("유효 활동 아님", activity.getId(), activity.getStatus().getDisplay());
        }
    }
}
