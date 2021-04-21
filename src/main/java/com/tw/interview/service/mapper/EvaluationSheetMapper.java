package com.tw.interview.service.mapper;


import com.tw.interview.domain.*;
import com.tw.interview.service.dto.EvaluationSheetDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EvaluationSheet} and its DTO {@link EvaluationSheetDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EvaluationSheetMapper extends EntityMapper<EvaluationSheetDTO, EvaluationSheet> {



    default EvaluationSheet fromId(Long id) {
        if (id == null) {
            return null;
        }
        EvaluationSheet evaluationSheet = new EvaluationSheet();
        evaluationSheet.setId(id);
        return evaluationSheet;
    }
}
