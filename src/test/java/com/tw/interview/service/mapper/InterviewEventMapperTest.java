package com.tw.interview.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class InterviewEventMapperTest {

    private InterviewEventMapper interviewEventMapper;

    @BeforeEach
    public void setUp() {
        interviewEventMapper = new InterviewEventMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(interviewEventMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(interviewEventMapper.fromId(null)).isNull();
    }
}
