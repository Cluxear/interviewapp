package com.tw.interview.web.rest;

import com.tw.interview.InterviewappApp;
import com.tw.interview.config.TestSecurityConfiguration;
import com.tw.interview.domain.Interview;
import com.tw.interview.repository.InterviewRepository;
import com.tw.interview.service.InterviewService;
import com.tw.interview.service.dto.InterviewDTO;
import com.tw.interview.service.mapper.InterviewMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tw.interview.domain.enumeration.InterviewType;
import com.tw.interview.domain.enumeration.InterviewResult;
/**
 * Integration tests for the {@link InterviewResource} REST controller.
 */
@SpringBootTest(classes = { InterviewappApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class InterviewResourceIT {

    private static final Instant DEFAULT_INTERVIEW_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INTERVIEW_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_MODIFIED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFIED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_RESULT_ATTRIBUTED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RESULT_ATTRIBUTED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final InterviewType DEFAULT_TYPE = InterviewType.TECHNICAL_INTERVIEW;
    private static final InterviewType UPDATED_TYPE = InterviewType.HR_INTERVIEW;

    private static final InterviewResult DEFAULT_RESULT = InterviewResult.POSITIVE;
    private static final InterviewResult UPDATED_RESULT = InterviewResult.NEGATIVE;

    private static final Boolean DEFAULT_IS_DATE_FIXED = false;
    private static final Boolean UPDATED_IS_DATE_FIXED = true;

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    @Autowired
    private InterviewRepository interviewRepository;

    @Autowired
    private InterviewMapper interviewMapper;

    @Autowired
    private InterviewService interviewService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInterviewMockMvc;

    private Interview interview;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Interview createEntity(EntityManager em) {
        Interview interview = new Interview()
            .interviewDate(DEFAULT_INTERVIEW_DATE)
            .createdAt(DEFAULT_CREATED_AT)
            .modifiedAt(DEFAULT_MODIFIED_AT)
            .resultAttributedAt(DEFAULT_RESULT_ATTRIBUTED_AT)
            .type(DEFAULT_TYPE)
            .result(DEFAULT_RESULT)
            .isDateFixed(DEFAULT_IS_DATE_FIXED)
            .note(DEFAULT_NOTE);
        return interview;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Interview createUpdatedEntity(EntityManager em) {
        Interview interview = new Interview()
            .interviewDate(UPDATED_INTERVIEW_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .modifiedAt(UPDATED_MODIFIED_AT)
            .resultAttributedAt(UPDATED_RESULT_ATTRIBUTED_AT)
            .type(UPDATED_TYPE)
            .result(UPDATED_RESULT)
            .isDateFixed(UPDATED_IS_DATE_FIXED)
            .note(UPDATED_NOTE);
        return interview;
    }

    @BeforeEach
    public void initTest() {
        interview = createEntity(em);
    }

    @Test
    @Transactional
    public void createInterview() throws Exception {
        int databaseSizeBeforeCreate = interviewRepository.findAll().size();
        // Create the Interview
        InterviewDTO interviewDTO = interviewMapper.toDto(interview);
        restInterviewMockMvc.perform(post("/api/interviews").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(interviewDTO)))
            .andExpect(status().isCreated());

        // Validate the Interview in the database
        List<Interview> interviewList = interviewRepository.findAll();
        assertThat(interviewList).hasSize(databaseSizeBeforeCreate + 1);
        Interview testInterview = interviewList.get(interviewList.size() - 1);
        assertThat(testInterview.getInterviewDate()).isEqualTo(DEFAULT_INTERVIEW_DATE);
        assertThat(testInterview.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testInterview.getModifiedAt()).isEqualTo(DEFAULT_MODIFIED_AT);
        assertThat(testInterview.getResultAttributedAt()).isEqualTo(DEFAULT_RESULT_ATTRIBUTED_AT);
        assertThat(testInterview.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testInterview.getResult()).isEqualTo(DEFAULT_RESULT);
        assertThat(testInterview.isIsDateFixed()).isEqualTo(DEFAULT_IS_DATE_FIXED);
        assertThat(testInterview.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    public void createInterviewWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = interviewRepository.findAll().size();

        // Create the Interview with an existing ID
        interview.setId(1L);
        InterviewDTO interviewDTO = interviewMapper.toDto(interview);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInterviewMockMvc.perform(post("/api/interviews").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(interviewDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Interview in the database
        List<Interview> interviewList = interviewRepository.findAll();
        assertThat(interviewList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInterviews() throws Exception {
        // Initialize the database
        interviewRepository.saveAndFlush(interview);

        // Get all the interviewList
        restInterviewMockMvc.perform(get("/api/interviews?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(interview.getId().intValue())))
            .andExpect(jsonPath("$.[*].interviewDate").value(hasItem(DEFAULT_INTERVIEW_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].modifiedAt").value(hasItem(DEFAULT_MODIFIED_AT.toString())))
            .andExpect(jsonPath("$.[*].resultAttributedAt").value(hasItem(DEFAULT_RESULT_ATTRIBUTED_AT.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT.toString())))
            .andExpect(jsonPath("$.[*].isDateFixed").value(hasItem(DEFAULT_IS_DATE_FIXED.booleanValue())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)));
    }
    
    @Test
    @Transactional
    public void getInterview() throws Exception {
        // Initialize the database
        interviewRepository.saveAndFlush(interview);

        // Get the interview
        restInterviewMockMvc.perform(get("/api/interviews/{id}", interview.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(interview.getId().intValue()))
            .andExpect(jsonPath("$.interviewDate").value(DEFAULT_INTERVIEW_DATE.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.modifiedAt").value(DEFAULT_MODIFIED_AT.toString()))
            .andExpect(jsonPath("$.resultAttributedAt").value(DEFAULT_RESULT_ATTRIBUTED_AT.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.result").value(DEFAULT_RESULT.toString()))
            .andExpect(jsonPath("$.isDateFixed").value(DEFAULT_IS_DATE_FIXED.booleanValue()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE));
    }
    @Test
    @Transactional
    public void getNonExistingInterview() throws Exception {
        // Get the interview
        restInterviewMockMvc.perform(get("/api/interviews/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInterview() throws Exception {
        // Initialize the database
        interviewRepository.saveAndFlush(interview);

        int databaseSizeBeforeUpdate = interviewRepository.findAll().size();

        // Update the interview
        Interview updatedInterview = interviewRepository.findById(interview.getId()).get();
        // Disconnect from session so that the updates on updatedInterview are not directly saved in db
        em.detach(updatedInterview);
        updatedInterview
            .interviewDate(UPDATED_INTERVIEW_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .modifiedAt(UPDATED_MODIFIED_AT)
            .resultAttributedAt(UPDATED_RESULT_ATTRIBUTED_AT)
            .type(UPDATED_TYPE)
            .result(UPDATED_RESULT)
            .isDateFixed(UPDATED_IS_DATE_FIXED)
            .note(UPDATED_NOTE);
        InterviewDTO interviewDTO = interviewMapper.toDto(updatedInterview);

        restInterviewMockMvc.perform(put("/api/interviews").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(interviewDTO)))
            .andExpect(status().isOk());

        // Validate the Interview in the database
        List<Interview> interviewList = interviewRepository.findAll();
        assertThat(interviewList).hasSize(databaseSizeBeforeUpdate);
        Interview testInterview = interviewList.get(interviewList.size() - 1);
        assertThat(testInterview.getInterviewDate()).isEqualTo(UPDATED_INTERVIEW_DATE);
        assertThat(testInterview.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testInterview.getModifiedAt()).isEqualTo(UPDATED_MODIFIED_AT);
        assertThat(testInterview.getResultAttributedAt()).isEqualTo(UPDATED_RESULT_ATTRIBUTED_AT);
        assertThat(testInterview.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testInterview.getResult()).isEqualTo(UPDATED_RESULT);
        assertThat(testInterview.isIsDateFixed()).isEqualTo(UPDATED_IS_DATE_FIXED);
        assertThat(testInterview.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingInterview() throws Exception {
        int databaseSizeBeforeUpdate = interviewRepository.findAll().size();

        // Create the Interview
        InterviewDTO interviewDTO = interviewMapper.toDto(interview);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInterviewMockMvc.perform(put("/api/interviews").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(interviewDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Interview in the database
        List<Interview> interviewList = interviewRepository.findAll();
        assertThat(interviewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInterview() throws Exception {
        // Initialize the database
        interviewRepository.saveAndFlush(interview);

        int databaseSizeBeforeDelete = interviewRepository.findAll().size();

        // Delete the interview
        restInterviewMockMvc.perform(delete("/api/interviews/{id}", interview.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Interview> interviewList = interviewRepository.findAll();
        assertThat(interviewList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
