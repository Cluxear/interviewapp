package com.tw.interview.service;

import com.tw.interview.service.dto.InterviewDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.tw.interview.domain.Interview}.
 */
public interface InterviewService {

    /**
     * Save a interview.
     *
     * @param interviewDTO the entity to save.
     * @return the persisted entity.
     */
    InterviewDTO save(InterviewDTO interviewDTO);

    /**
     * Get all the interviews.
     *
     * @return the list of entities.
     */
    List<InterviewDTO> findAll();


    /**
     * Get the "id" interview.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InterviewDTO> findOne(Long id);

    /**
     * Delete the "id" interview.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
