package com.tw.interview.service;

import com.tw.interview.service.dto.EventDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.tw.interview.domain.Event}.
 */
public interface EventService {

    /**
     * Save a event.
     *
     * @param eventDTO the entity to save.
     * @return the persisted entity.
     */
    EventDTO save(EventDTO eventDTO);

    /**
     * Get all the events.
     *
     * @return the list of entities.
     */
    List<EventDTO> findAll();


    /**
     * Get the "id" event.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EventDTO> findOne(Long id);

    /**
     * Delete the "id" event.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
