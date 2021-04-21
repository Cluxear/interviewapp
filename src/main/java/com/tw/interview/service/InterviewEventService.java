package com.tw.interview.service;

import com.tw.interview.service.dto.InterviewEventDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.tw.interview.domain.InterviewEvent}.
 */
public interface InterviewEventService {

    /**
     * Save a interviewEvent.
     *
     * @param interviewEventDTO the entity to save.
     * @return the persisted entity.
     */
    InterviewEventDTO save(InterviewEventDTO interviewEventDTO);

    /**
     * Get all the interviewEvents.
     *
     * @return the list of entities.
     */
    List<InterviewEventDTO> findAll();


    /**
     * Get the "id" interviewEvent.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InterviewEventDTO> findOne(Long id);

    /**
     * Delete the "id" interviewEvent.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
