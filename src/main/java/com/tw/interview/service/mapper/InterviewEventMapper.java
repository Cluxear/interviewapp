package com.tw.interview.service.mapper;


import com.tw.interview.domain.*;
import com.tw.interview.service.dto.InterviewEventDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link InterviewEvent} and its DTO {@link InterviewEventDTO}.
 */
@Mapper(componentModel = "spring", uses = {EventMapper.class})
public interface InterviewEventMapper extends EntityMapper<InterviewEventDTO, InterviewEvent> {

    @Mapping(source = "event.id", target = "eventId")
    InterviewEventDTO toDto(InterviewEvent interviewEvent);

    @Mapping(source = "eventId", target = "event")
    InterviewEvent toEntity(InterviewEventDTO interviewEventDTO);

    default InterviewEvent fromId(Long id) {
        if (id == null) {
            return null;
        }
        InterviewEvent interviewEvent = new InterviewEvent();
        interviewEvent.setId(id);
        return interviewEvent;
    }
}
