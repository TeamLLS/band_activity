package com.example.band_activity.activity;

import com.example.band_activity.activity.form.ActivityDto;
import com.example.band_activity.activity.form.ActivityItemDto;
import com.example.band_activity.participant.ParticipantService;
import com.example.band_activity.participant.form.ParticipantActivityItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/activity")
public class ActivityController {

    private final ActivityService activityService;

    @GetMapping("/{activityId}")
    public ResponseEntity<?> getActivityInfo(@PathVariable Long activityId){
        return ResponseEntity.ok().body(activityService.getActivityInfo(activityId));
    }

    @GetMapping("/{clubId}/list")
    public ResponseEntity<?> getActivityList(@PathVariable Long clubId, @RequestParam int pageNo){
        List<ActivityItemDto> activityList = activityService.getActivityList(clubId, pageNo, 50);

        Map<String, Object> response = new HashMap<>();
        response.put("list", activityList);

        return ResponseEntity.ok().body(response);
    }

}
