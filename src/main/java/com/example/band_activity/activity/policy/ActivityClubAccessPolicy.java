package com.example.band_activity.activity.policy;

import com.example.band_activity.activity.Activity;
import com.example.band_activity.activity.exception.ActivityNotInClubException;

public class ActivityClubAccessPolicy {

    public static void isInClub(Activity activity, Long clubId){
        if(!activity.getClubId().equals(clubId)){
            throw new ActivityNotInClubException("해당 모임의 활동 아님", activity.getId(), clubId);
        }
    }
}
