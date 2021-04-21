package com.tw.interview.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.tw.interview.web.rest.TestUtil;

public class InterviewDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InterviewDTO.class);
        InterviewDTO interviewDTO1 = new InterviewDTO();
        interviewDTO1.setId(1L);
        InterviewDTO interviewDTO2 = new InterviewDTO();
        assertThat(interviewDTO1).isNotEqualTo(interviewDTO2);
        interviewDTO2.setId(interviewDTO1.getId());
        assertThat(interviewDTO1).isEqualTo(interviewDTO2);
        interviewDTO2.setId(2L);
        assertThat(interviewDTO1).isNotEqualTo(interviewDTO2);
        interviewDTO1.setId(null);
        assertThat(interviewDTO1).isNotEqualTo(interviewDTO2);
    }
}
