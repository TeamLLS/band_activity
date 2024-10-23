package com.example.band_activity.participant;

import com.example.band_activity.participant.form.ParticipantDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/participant")
@RequiredArgsConstructor
public class ParticipantController {

    private final ParticipantService participantService;

    @GetMapping("/{activityId}/list")
    public ResponseEntity<?> getParticipantList(@PathVariable Long activityId, @RequestParam int pageNo){
        List<ParticipantDto> participantList = participantService.getParticipantList(activityId, pageNo);

        Map<String, Object> response = new HashMap<>();
        response.put("list", participantList);

        return ResponseEntity.ok().body(response);
    }
}
