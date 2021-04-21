package com.tw.interview.repository;

import com.tw.interview.domain.InterviewEvent;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the InterviewEvent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InterviewEventRepository extends JpaRepository<InterviewEvent, Long> {
}
