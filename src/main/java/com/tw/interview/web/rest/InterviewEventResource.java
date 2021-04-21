package com.tw.interview.web.rest;

import com.tw.interview.service.InterviewEventService;
import com.tw.interview.web.rest.errors.BadRequestAlertException;
import com.tw.interview.service.dto.InterviewEventDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link com.tw.interview.domain.InterviewEvent}.
 */
@RestController
@RequestMapping("/api")
public class InterviewEventResource {

    private final Logger log = LoggerFactory.getLogger(InterviewEventResource.class);

    private static final String ENTITY_NAME = "interviewappInterviewEvent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InterviewEventService interviewEventService;

    public InterviewEventResource(InterviewEventService interviewEventService) {
        this.interviewEventService = interviewEventService;
    }

    /**
     * {@code POST  /interview-events} : Create a new interviewEvent.
     *
     * @param interviewEventDTO the interviewEventDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new interviewEventDTO, or with status {@code 400 (Bad Request)} if the interviewEvent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/interview-events")
    public ResponseEntity<InterviewEventDTO> createInterviewEvent(@RequestBody InterviewEventDTO interviewEventDTO) throws URISyntaxException {
        log.debug("REST request to save InterviewEvent : {}", interviewEventDTO);
        if (interviewEventDTO.getId() != null) {
            throw new BadRequestAlertException("A new interviewEvent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (Objects.isNull(interviewEventDTO.getEventId())) {
            throw new BadRequestAlertException("Invalid association value provided", ENTITY_NAME, "null");
        }
        InterviewEventDTO result = interviewEventService.save(interviewEventDTO);
        return ResponseEntity.created(new URI("/api/interview-events/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /interview-events} : Updates an existing interviewEvent.
     *
     * @param interviewEventDTO the interviewEventDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated interviewEventDTO,
     * or with status {@code 400 (Bad Request)} if the interviewEventDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the interviewEventDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/interview-events")
    public ResponseEntity<InterviewEventDTO> updateInterviewEvent(@RequestBody InterviewEventDTO interviewEventDTO) throws URISyntaxException {
        log.debug("REST request to update InterviewEvent : {}", interviewEventDTO);
        if (interviewEventDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InterviewEventDTO result = interviewEventService.save(interviewEventDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, interviewEventDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /interview-events} : get all the interviewEvents.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of interviewEvents in body.
     */
    @GetMapping("/interview-events")
    public List<InterviewEventDTO> getAllInterviewEvents() {
        log.debug("REST request to get all InterviewEvents");
        return interviewEventService.findAll();
    }

    /**
     * {@code GET  /interview-events/:id} : get the "id" interviewEvent.
     *
     * @param id the id of the interviewEventDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the interviewEventDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/interview-events/{id}")
    public ResponseEntity<InterviewEventDTO> getInterviewEvent(@PathVariable Long id) {
        log.debug("REST request to get InterviewEvent : {}", id);
        Optional<InterviewEventDTO> interviewEventDTO = interviewEventService.findOne(id);
        return ResponseUtil.wrapOrNotFound(interviewEventDTO);
    }

    /**
     * {@code DELETE  /interview-events/:id} : delete the "id" interviewEvent.
     *
     * @param id the id of the interviewEventDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/interview-events/{id}")
    public ResponseEntity<Void> deleteInterviewEvent(@PathVariable Long id) {
        log.debug("REST request to delete InterviewEvent : {}", id);
        interviewEventService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
