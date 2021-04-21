package com.tw.interview.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class InterviewMapperTest {

    private InterviewMapper interviewMapper;

    @BeforeEach
    public void setUp() {
        interviewMapper = new InterviewMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(interviewMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(interviewMapper.fromId(null)).isNull();
    }
}
