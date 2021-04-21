package com.tw.interview.service.impl;

import com.tw.interview.service.EvaluationSheetService;
import com.tw.interview.domain.EvaluationSheet;
import com.tw.interview.repository.EvaluationSheetRepository;
import com.tw.interview.service.dto.EvaluationSheetDTO;
import com.tw.interview.service.mapper.EvaluationSheetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link EvaluationSheet}.
 */
@Service
@Transactional
public class EvaluationSheetServiceImpl implements EvaluationSheetService {

    private final Logger log = LoggerFactory.getLogger(EvaluationSheetServiceImpl.class);

    private final EvaluationSheetRepository evaluationSheetRepository;

    private final EvaluationSheetMapper evaluationSheetMapper;

    public EvaluationSheetServiceImpl(EvaluationSheetRepository evaluationSheetRepository, EvaluationSheetMapper evaluationSheetMapper) {
        this.evaluationSheetRepository = evaluationSheetRepository;
        this.evaluationSheetMapper = evaluationSheetMapper;
    }

    @Override
    public EvaluationSheetDTO save(EvaluationSheetDTO evaluationSheetDTO) {
        log.debug("Request to save EvaluationSheet : {}", evaluationSheetDTO);
        EvaluationSheet evaluationSheet = evaluationSheetMapper.toEntity(evaluationSheetDTO);
        evaluationSheet = evaluationSheetRepository.save(evaluationSheet);
        return evaluationSheetMapper.toDto(evaluationSheet);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EvaluationSheetDTO> findAll() {
        log.debug("Request to get all EvaluationSheets");
        return evaluationSheetRepository.findAll().stream()
            .map(evaluationSheetMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<EvaluationSheetDTO> findOne(Long id) {
        log.debug("Request to get EvaluationSheet : {}", id);
        return evaluationSheetRepository.findById(id)
            .map(evaluationSheetMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EvaluationSheet : {}", id);
        evaluationSheetRepository.deleteById(id);
    }
}
