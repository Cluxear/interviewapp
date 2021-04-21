package com.tw.interview.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.tw.interview.web.rest.TestUtil;

public class InterviewEventDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InterviewEventDTO.class);
        InterviewEventDTO interviewEventDTO1 = new InterviewEventDTO();
        interviewEventDTO1.setId(1L);
        InterviewEventDTO interviewEventDTO2 = new InterviewEventDTO();
        assertThat(interviewEventDTO1).isNotEqualTo(interviewEventDTO2);
        interviewEventDTO2.setId(interviewEventDTO1.getId());
        assertThat(interviewEventDTO1).isEqualTo(interviewEventDTO2);
        interviewEventDTO2.setId(2L);
        assertThat(interviewEventDTO1).isNotEqualTo(interviewEventDTO2);
        interviewEventDTO1.setId(null);
        assertThat(interviewEventDTO1).isNotEqualTo(interviewEventDTO2);
    }
}
