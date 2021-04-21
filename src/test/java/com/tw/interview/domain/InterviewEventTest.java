package com.tw.interview.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.tw.interview.web.rest.TestUtil;

public class InterviewEventTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InterviewEvent.class);
        InterviewEvent interviewEvent1 = new InterviewEvent();
        interviewEvent1.setId(1L);
        InterviewEvent interviewEvent2 = new InterviewEvent();
        interviewEvent2.setId(interviewEvent1.getId());
        assertThat(interviewEvent1).isEqualTo(interviewEvent2);
        interviewEvent2.setId(2L);
        assertThat(interviewEvent1).isNotEqualTo(interviewEvent2);
        interviewEvent1.setId(null);
        assertThat(interviewEvent1).isNotEqualTo(interviewEvent2);
    }
}
