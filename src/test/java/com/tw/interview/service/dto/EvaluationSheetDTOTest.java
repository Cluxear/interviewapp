package com.tw.interview.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.tw.interview.web.rest.TestUtil;

public class EvaluationSheetDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EvaluationSheetDTO.class);
        EvaluationSheetDTO evaluationSheetDTO1 = new EvaluationSheetDTO();
        evaluationSheetDTO1.setId(1L);
        EvaluationSheetDTO evaluationSheetDTO2 = new EvaluationSheetDTO();
        assertThat(evaluationSheetDTO1).isNotEqualTo(evaluationSheetDTO2);
        evaluationSheetDTO2.setId(evaluationSheetDTO1.getId());
        assertThat(evaluationSheetDTO1).isEqualTo(evaluationSheetDTO2);
        evaluationSheetDTO2.setId(2L);
        assertThat(evaluationSheetDTO1).isNotEqualTo(evaluationSheetDTO2);
        evaluationSheetDTO1.setId(null);
        assertThat(evaluationSheetDTO1).isNotEqualTo(evaluationSheetDTO2);
    }
}
