package com.tw.interview.service.mapper;


import com.tw.interview.domain.*;
import com.tw.interview.service.dto.InterviewDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Interview} and its DTO {@link InterviewDTO}.
 */
@Mapper(componentModel = "spring", uses = {EvaluationSheetMapper.class})
public interface InterviewMapper extends EntityMapper<InterviewDTO, Interview> {

    @Mapping(source = "evaluationSheet.id", target = "evaluationSheetId")
    InterviewDTO toDto(Interview interview);

    @Mapping(source = "evaluationSheetId", target = "evaluationSheet")
    Interview toEntity(InterviewDTO interviewDTO);

    default Interview fromId(Long id) {
        if (id == null) {
            return null;
        }
        Interview interview = new Interview();
        interview.setId(id);
        return interview;
    }
}
