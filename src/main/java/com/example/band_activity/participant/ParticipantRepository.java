package com.example.band_activity.participant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    @Query("SELECT p FROM Participant p JOIN FETCH p.activity a WHERE a.clubId =:clubId AND p.username =:username AND (p.status='ATTEND' OR p.status = 'ADDITIONAL_ATTEND')")
    public Page<Participant> findAllWithActivityByUsername(@Param("clubId") Long clubId, @Param("username") String username, Pageable pageable);

    @Query("SELECT p FROM Participant p WHERE p.activity.id =:activityId AND (p.status='ATTEND' OR p.status = 'ADDITIONAL_ATTEND')")
    public Page<Participant> findAllByActivityId(@Param("activityId") Long activityId, Pageable pageable);

    public Optional<Participant> findByActivityIdAndUsername(Long activityId, String username); //조회용 조회
    public Optional<Participant> findByActivityIdAndMemberId(Long activityId, Long memberId);   //수정용 조회

}
