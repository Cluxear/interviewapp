package com.tw.interview.service;

import com.tw.interview.service.dto.EvaluationSheetDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.tw.interview.domain.EvaluationSheet}.
 */
public interface EvaluationSheetService {

    /**
     * Save a evaluationSheet.
     *
     * @param evaluationSheetDTO the entity to save.
     * @return the persisted entity.
     */
    EvaluationSheetDTO save(EvaluationSheetDTO evaluationSheetDTO);

    /**
     * Get all the evaluationSheets.
     *
     * @return the list of entities.
     */
    List<EvaluationSheetDTO> findAll();


    /**
     * Get the "id" evaluationSheet.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EvaluationSheetDTO> findOne(Long id);

    /**
     * Delete the "id" evaluationSheet.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
