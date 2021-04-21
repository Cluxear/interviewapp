package com.tw.interview.web.rest;

import com.tw.interview.service.InterviewService;
import com.tw.interview.web.rest.errors.BadRequestAlertException;
import com.tw.interview.service.dto.InterviewDTO;

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
import java.util.Optional;

/**
 * REST controller for managing {@link com.tw.interview.domain.Interview}.
 */
@RestController
@RequestMapping("/api")
public class InterviewResource {

    private final Logger log = LoggerFactory.getLogger(InterviewResource.class);

    private static final String ENTITY_NAME = "interviewappInterview";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InterviewService interviewService;

    public InterviewResource(InterviewService interviewService) {
        this.interviewService = interviewService;
    }

    /**
     * {@code POST  /interviews} : Create a new interview.
     *
     * @param interviewDTO the interviewDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new interviewDTO, or with status {@code 400 (Bad Request)} if the interview has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/interviews")
    public ResponseEntity<InterviewDTO> createInterview(@RequestBody InterviewDTO interviewDTO) throws URISyntaxException {
        log.debug("REST request to save Interview : {}", interviewDTO);
        if (interviewDTO.getId() != null) {
            throw new BadRequestAlertException("A new interview cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InterviewDTO result = interviewService.save(interviewDTO);
        return ResponseEntity.created(new URI("/api/interviews/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /interviews} : Updates an existing interview.
     *
     * @param interviewDTO the interviewDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated interviewDTO,
     * or with status {@code 400 (Bad Request)} if the interviewDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the interviewDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/interviews")
    public ResponseEntity<InterviewDTO> updateInterview(@RequestBody InterviewDTO interviewDTO) throws URISyntaxException {
        log.debug("REST request to update Interview : {}", interviewDTO);
        if (interviewDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InterviewDTO result = interviewService.save(interviewDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, interviewDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /interviews} : get all the interviews.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of interviews in body.
     */
    @GetMapping("/interviews")
    public List<InterviewDTO> getAllInterviews() {
        log.debug("REST request to get all Interviews");
        return interviewService.findAll();
    }

    /**
     * {@code GET  /interviews/:id} : get the "id" interview.
     *
     * @param id the id of the interviewDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the interviewDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/interviews/{id}")
    public ResponseEntity<InterviewDTO> getInterview(@PathVariable Long id) {
        log.debug("REST request to get Interview : {}", id);
        Optional<InterviewDTO> interviewDTO = interviewService.findOne(id);
        return ResponseUtil.wrapOrNotFound(interviewDTO);
    }

    /**
     * {@code DELETE  /interviews/:id} : delete the "id" interview.
     *
     * @param id the id of the interviewDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/interviews/{id}")
    public ResponseEntity<Void> deleteInterview(@PathVariable Long id) {
        log.debug("REST request to delete Interview : {}", id);
        interviewService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
