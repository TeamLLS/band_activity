package com.example.band_activity.participant;

import com.example.band_activity.activity.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    @Query("SELECT p FROM Participant p WHERE p.activity.id = :activityId AND (p.status = 'ATTEND' OR p.status = 'ADDITIONAL_ATTEND')")
    public Page<Participant> findAllByActivityId(@Param("activityId") Long activityId, Pageable pageable);

    public Optional<Participant> findByActivityIdAndMemberId(Long activityId, Long memberId);
}
