package com.tw.interview.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.tw.interview.web.rest.TestUtil;

public class EvaluationSheetTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EvaluationSheet.class);
        EvaluationSheet evaluationSheet1 = new EvaluationSheet();
        evaluationSheet1.setId(1L);
        EvaluationSheet evaluationSheet2 = new EvaluationSheet();
        evaluationSheet2.setId(evaluationSheet1.getId());
        assertThat(evaluationSheet1).isEqualTo(evaluationSheet2);
        evaluationSheet2.setId(2L);
        assertThat(evaluationSheet1).isNotEqualTo(evaluationSheet2);
        evaluationSheet1.setId(null);
        assertThat(evaluationSheet1).isNotEqualTo(evaluationSheet2);
    }
}
