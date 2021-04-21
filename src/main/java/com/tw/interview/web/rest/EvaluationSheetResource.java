package com.tw.interview.web.rest;

import com.tw.interview.service.EvaluationSheetService;
import com.tw.interview.web.rest.errors.BadRequestAlertException;
import com.tw.interview.service.dto.EvaluationSheetDTO;

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
 * REST controller for managing {@link com.tw.interview.domain.EvaluationSheet}.
 */
@RestController
@RequestMapping("/api")
public class EvaluationSheetResource {

    private final Logger log = LoggerFactory.getLogger(EvaluationSheetResource.class);

    private static final String ENTITY_NAME = "interviewappEvaluationSheet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EvaluationSheetService evaluationSheetService;

    public EvaluationSheetResource(EvaluationSheetService evaluationSheetService) {
        this.evaluationSheetService = evaluationSheetService;
    }

    /**
     * {@code POST  /evaluation-sheets} : Create a new evaluationSheet.
     *
     * @param evaluationSheetDTO the evaluationSheetDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new evaluationSheetDTO, or with status {@code 400 (Bad Request)} if the evaluationSheet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/evaluation-sheets")
    public ResponseEntity<EvaluationSheetDTO> createEvaluationSheet(@RequestBody EvaluationSheetDTO evaluationSheetDTO) throws URISyntaxException {
        log.debug("REST request to save EvaluationSheet : {}", evaluationSheetDTO);
        if (evaluationSheetDTO.getId() != null) {
            throw new BadRequestAlertException("A new evaluationSheet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EvaluationSheetDTO result = evaluationSheetService.save(evaluationSheetDTO);
        return ResponseEntity.created(new URI("/api/evaluation-sheets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /evaluation-sheets} : Updates an existing evaluationSheet.
     *
     * @param evaluationSheetDTO the evaluationSheetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated evaluationSheetDTO,
     * or with status {@code 400 (Bad Request)} if the evaluationSheetDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the evaluationSheetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/evaluation-sheets")
    public ResponseEntity<EvaluationSheetDTO> updateEvaluationSheet(@RequestBody EvaluationSheetDTO evaluationSheetDTO) throws URISyntaxException {
        log.debug("REST request to update EvaluationSheet : {}", evaluationSheetDTO);
        if (evaluationSheetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EvaluationSheetDTO result = evaluationSheetService.save(evaluationSheetDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, evaluationSheetDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /evaluation-sheets} : get all the evaluationSheets.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of evaluationSheets in body.
     */
    @GetMapping("/evaluation-sheets")
    public List<EvaluationSheetDTO> getAllEvaluationSheets() {
        log.debug("REST request to get all EvaluationSheets");
        return evaluationSheetService.findAll();
    }

    /**
     * {@code GET  /evaluation-sheets/:id} : get the "id" evaluationSheet.
     *
     * @param id the id of the evaluationSheetDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the evaluationSheetDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/evaluation-sheets/{id}")
    public ResponseEntity<EvaluationSheetDTO> getEvaluationSheet(@PathVariable Long id) {
        log.debug("REST request to get EvaluationSheet : {}", id);
        Optional<EvaluationSheetDTO> evaluationSheetDTO = evaluationSheetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(evaluationSheetDTO);
    }

    /**
     * {@code DELETE  /evaluation-sheets/:id} : delete the "id" evaluationSheet.
     *
     * @param id the id of the evaluationSheetDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/evaluation-sheets/{id}")
    public ResponseEntity<Void> deleteEvaluationSheet(@PathVariable Long id) {
        log.debug("REST request to delete EvaluationSheet : {}", id);
        evaluationSheetService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
