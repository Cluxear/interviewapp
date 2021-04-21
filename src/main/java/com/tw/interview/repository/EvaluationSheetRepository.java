package com.tw.interview.repository;

import com.tw.interview.domain.EvaluationSheet;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EvaluationSheet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EvaluationSheetRepository extends JpaRepository<EvaluationSheet, Long> {
}
