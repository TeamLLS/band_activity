package com.example.band_activity.participant.event;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantEventRepository extends JpaRepository<ParticipantEventJpo, String> {
}
