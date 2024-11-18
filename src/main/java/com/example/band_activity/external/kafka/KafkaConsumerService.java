package com.example.band_activity.external.kafka;

import com.example.band_activity.activity.ActivityService;
import com.example.band_activity.activity.command.CancelActivity;
import com.example.band_activity.activity.command.CloseActivity;
import com.example.band_activity.activity.command.OpenActivity;
import com.example.band_activity.core.Command;
import com.example.band_activity.core.UnknownCommandException;
import com.example.band_activity.participant.ParticipantService;
import com.example.band_activity.participant.command.AttendActivity;
import com.example.band_activity.participant.command.NotAttendActivity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
@KafkaListener(topics = "activity-topic", groupId = "activity-consumer-group")
public class KafkaConsumerService {

    private final ActivityService activityService;
    private final ParticipantService participantService;

    @KafkaHandler
    public void consumeCommand(OpenActivity command){
        activityService.createActivity(command);
    }

    @KafkaHandler
    public void consumeCommand(CancelActivity command){
        activityService.cancelActivity(command);
    }

    @KafkaHandler
    public void consumeCommand(CloseActivity command){
        activityService.closeActivity(command);
    }

    @KafkaHandler
    public void consumeCommand(AttendActivity command){
        participantService.attendActivity(command);
    }

    @KafkaHandler
    public void consumeCommand(NotAttendActivity command){
        participantService.notAttendActivity(command);
    }

    @KafkaHandler(isDefault = true)
    public void consume(Command command, Acknowledgment acknowledgment){
        throw new UnknownCommandException("알 수 없는 명령", command.getUsername());
    }
}
