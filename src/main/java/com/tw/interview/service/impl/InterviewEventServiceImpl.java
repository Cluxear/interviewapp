package com.tw.interview.service.impl;

import com.tw.interview.service.InterviewEventService;
import com.tw.interview.domain.InterviewEvent;
import com.tw.interview.repository.InterviewEventRepository;
import com.tw.interview.repository.EventRepository;
import com.tw.interview.service.dto.InterviewEventDTO;
import com.tw.interview.service.mapper.InterviewEventMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link InterviewEvent}.
 */
@Service
@Transactional
public class InterviewEventServiceImpl implements InterviewEventService {

    private final Logger log = LoggerFactory.getLogger(InterviewEventServiceImpl.class);

    private final InterviewEventRepository interviewEventRepository;

    private final InterviewEventMapper interviewEventMapper;

    private final EventRepository eventRepository;

    public InterviewEventServiceImpl(InterviewEventRepository interviewEventRepository, InterviewEventMapper interviewEventMapper, EventRepository eventRepository) {
        this.interviewEventRepository = interviewEventRepository;
        this.interviewEventMapper = interviewEventMapper;
        this.eventRepository = eventRepository;
    }

    @Override
    public InterviewEventDTO save(InterviewEventDTO interviewEventDTO) {
        log.debug("Request to save InterviewEvent : {}", interviewEventDTO);
        InterviewEvent interviewEvent = interviewEventMapper.toEntity(interviewEventDTO);
        Long eventId = interviewEventDTO.getEventId();
        eventRepository.findById(eventId).ifPresent(interviewEvent::event);
        interviewEvent = interviewEventRepository.save(interviewEvent);
        return interviewEventMapper.toDto(interviewEvent);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InterviewEventDTO> findAll() {
        log.debug("Request to get all InterviewEvents");
        return interviewEventRepository.findAll().stream()
            .map(interviewEventMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<InterviewEventDTO> findOne(Long id) {
        log.debug("Request to get InterviewEvent : {}", id);
        return interviewEventRepository.findById(id)
            .map(interviewEventMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete InterviewEvent : {}", id);
        interviewEventRepository.deleteById(id);
    }
}
