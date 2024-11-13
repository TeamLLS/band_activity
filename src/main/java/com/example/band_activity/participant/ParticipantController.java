package com.example.band_activity.participant;

import com.example.band_activity.participant.form.ParticipantActivityItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/participant")
@RequiredArgsConstructor
public class ParticipantController {

    private final ParticipantService participantService;

    @GetMapping("/{activityId}/list")
    public ResponseEntity<?> getParticipantList(@RequestHeader String username, @PathVariable Long activityId, @RequestParam int pageNo){
        return ResponseEntity.ok().body(participantService.getParticipantList(activityId, username, pageNo));
    }

    @GetMapping("/{clubId}/activity/list")
    public ResponseEntity<?> getParticipantActivityList(@RequestHeader String username, @PathVariable Long clubId, @RequestParam int pageNo){
        List<ParticipantActivityItemDto> participantActivityList = participantService.getParticipantActivityList(clubId, username, pageNo, 2);

        Map<String, Object> response = new HashMap<>();
        response.put("list", participantActivityList);

        return ResponseEntity.ok().body(response);
    }
}
