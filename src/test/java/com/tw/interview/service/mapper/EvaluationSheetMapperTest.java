package com.tw.interview.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EvaluationSheetMapperTest {

    private EvaluationSheetMapper evaluationSheetMapper;

    @BeforeEach
    public void setUp() {
        evaluationSheetMapper = new EvaluationSheetMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(evaluationSheetMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(evaluationSheetMapper.fromId(null)).isNull();
    }
}
