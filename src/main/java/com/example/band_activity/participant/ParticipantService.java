package com.example.band_activity.participant;

import com.example.band_activity.activity.Activity;
import com.example.band_activity.activity.ActivityStore;
import com.example.band_activity.participant.command.*;
import com.example.band_activity.participant.form.ParticipantActivityItemDto;
import com.example.band_activity.participant.form.ParticipantDto;
import com.example.band_activity.policy.ActivityClubAccessPolicy;
import com.example.band_activity.policy.ActivityStatusAccessPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class ParticipantService {

    @Value("${spring.cloud.aws.cdn.url}")
    private String production;

    private final ParticipantStore participantStore;
    private final ActivityStore activityStore;

    public Long attendActivity(AttendActivity command){

        Participant participant;
        try {
            participant=participantStore.findByActivityIdAndMemberId(command.getActivityId(), command.getMemberId());
        } catch (NoSuchElementException e){
            participant=null;
        }

        if(participant == null){
            participant = registerParticipant(command);

        }else if(command.isAdditional()
                && (participant.getStatus()==ParticipantStatus.NOT_ATTEND || participant.getStatus()==ParticipantStatus.ADDITIONAL_NOT_ATTEND)){
            ActivityStatusAccessPolicy.isClosed(participant.getActivity());
            participantStore.saveEvent(participant.attend(command));
            participantStore.saveEvent(participant.confirmed(new ConfirmParticipant(command.getUsername(), command.getActivityId())));

        }else if(participant.getStatus()==ParticipantStatus.NOT_ATTEND){
            ActivityStatusAccessPolicy.isOpened(participant.getActivity());
            participantStore.saveEvent(participant.attend(command));
        }

        return participant.getId();
    }

    public Long notAttendActivity(NotAttendActivity command){

        Participant participant=participantStore.findByActivityIdAndMemberId(command.getActivityId(), command.getMemberId());

        if(command.isAdditional()
                && (participant.getStatus()==ParticipantStatus.ATTEND || participant.getStatus()==ParticipantStatus.ADDITIONAL_ATTEND)){
            ActivityStatusAccessPolicy.isClosed(participant.getActivity());
            participantStore.saveEvent(participant.notAttend(command));
            participantStore.saveEvent(participant.confirmed(new ConfirmParticipant(command.getUsername(), command.getActivityId())));

        }else if(participant.getStatus()==ParticipantStatus.ATTEND){
            ActivityStatusAccessPolicy.isOpened(participant.getActivity());
            participantStore.saveEvent(participant.notAttend(command));

}
        return participant.getId();
    }

    public Participant registerParticipant(AttendActivity command){
        Activity activity = activityStore.find(command.getActivityId());

        ActivityClubAccessPolicy.isInClub(activity, command.getClubId());

        if(command.isAdditional()){
            ActivityStatusAccessPolicy.isClosed(activity);
        }else {
            ActivityStatusAccessPolicy.isOpened(activity);
        }

        command.setActivity(activity);
        activity.participantNumIncreased();
        Participant participant = participantStore.save(command.getUsername(), new Participant(command));

        if(command.isAdditional()){
            participantStore.saveEvent(participant.confirmed(new ConfirmParticipant(command.getUsername(), command.getActivityId())));
        }

        return participant;
    }

    @Transactional(readOnly = true)
    public List<ParticipantActivityItemDto> getParticipantActivityList(Long clubId, String username, int pageNo){
        return participantStore.findListWithActivityByUsername(clubId, username, pageNo, 2).getContent()
                .stream().map(p -> new ParticipantActivityItemDto(p, production + "/" + p.getActivity().getImage())).toList();
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getParticipantList(Long activityId, String username, int pageNo){
        List<ParticipantDto> list = participantStore.findListByActivityId(activityId, pageNo, 2).getContent()
                .stream().map(ParticipantDto::new).toList();

        Boolean attend;
        try{
            attend = participantStore.findByActivityIdAndUsername(activityId, username).getStatus()==ParticipantStatus.ATTEND;
        }catch (NoSuchElementException e){
            attend = false;
        }

        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("attend", attend);

        return result;
    }

    public Integer confirmParticipants(ConfirmParticipant command){
        Page<Participant> page;

        int pageNo=0;

        do{
            page = participantStore.findListByActivityId(command.getActivityId(), pageNo, 2);
            page.getContent().forEach(p -> participantStore.saveEvent(p.confirmed(command)));

            pageNo++;
        }while (page.hasNext());

        return pageNo;
    }
}
