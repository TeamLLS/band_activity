package com.example.band_activity.activity.event;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityEventRepository extends JpaRepository<ActivityEventJpo, String> {
}
