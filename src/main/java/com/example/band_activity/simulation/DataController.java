package com.example.band_activity.simulation;

import com.example.band_activity.simulation.command.RegisterParticipantDummy;
import com.example.band_activity.simulation.command.CreateActivityDummy;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/activity/dummy")
@RequiredArgsConstructor
public class DataController {

    private final DataService dataService;

    @PostMapping
    public ResponseEntity<?> createActivity(@Validated @RequestBody CreateActivityDummy command){
        Long activityId = dataService.createActivity(command);

        Map<String, Object> result = new HashMap<>();
        result.put("id", activityId);

        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/participant")
    public ResponseEntity<?> registerParticipant(@Validated @RequestBody RegisterParticipantDummy command){
        dataService.registerParticipant(command);

        return ResponseEntity.ok().build();
    }

}
