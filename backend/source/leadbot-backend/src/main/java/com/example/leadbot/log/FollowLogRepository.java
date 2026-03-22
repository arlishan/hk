package com.example.leadbot.log;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowLogRepository extends JpaRepository<FollowLog, Long> {

    List<FollowLog> findByLeadIdOrderByIdDesc(Long leadId);
}
