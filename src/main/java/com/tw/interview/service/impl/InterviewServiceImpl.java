package com.tw.interview.service.impl;

import com.tw.interview.service.InterviewService;
import com.tw.interview.domain.Interview;
import com.tw.interview.repository.InterviewRepository;
import com.tw.interview.service.dto.InterviewDTO;
import com.tw.interview.service.mapper.InterviewMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Interview}.
 */
@Service
@Transactional
public class InterviewServiceImpl implements InterviewService {

    private final Logger log = LoggerFactory.getLogger(InterviewServiceImpl.class);

    private final InterviewRepository interviewRepository;

    private final InterviewMapper interviewMapper;

    public InterviewServiceImpl(InterviewRepository interviewRepository, InterviewMapper interviewMapper) {
        this.interviewRepository = interviewRepository;
        this.interviewMapper = interviewMapper;
    }

    @Override
    public InterviewDTO save(InterviewDTO interviewDTO) {
        log.debug("Request to save Interview : {}", interviewDTO);
        Interview interview = interviewMapper.toEntity(interviewDTO);
        interview = interviewRepository.save(interview);
        return interviewMapper.toDto(interview);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InterviewDTO> findAll() {
        log.debug("Request to get all Interviews");
        return interviewRepository.findAll().stream()
            .map(interviewMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<InterviewDTO> findOne(Long id) {
        log.debug("Request to get Interview : {}", id);
        return interviewRepository.findById(id)
            .map(interviewMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Interview : {}", id);
        interviewRepository.deleteById(id);
    }
}
