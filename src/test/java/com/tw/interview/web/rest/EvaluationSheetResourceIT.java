package com.tw.interview.web.rest;

import com.tw.interview.InterviewappApp;
import com.tw.interview.config.TestSecurityConfiguration;
import com.tw.interview.domain.EvaluationSheet;
import com.tw.interview.repository.EvaluationSheetRepository;
import com.tw.interview.service.EvaluationSheetService;
import com.tw.interview.service.dto.EvaluationSheetDTO;
import com.tw.interview.service.mapper.EvaluationSheetMapper;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link EvaluationSheetResource} REST controller.
 */
@SpringBootTest(classes = { InterviewappApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class EvaluationSheetResourceIT {

    private static final String DEFAULT_ATOUT = "AAAAAAAAAA";
    private static final String UPDATED_ATOUT = "BBBBBBBBBB";

    private static final String DEFAULT_FAIBLESS = "AAAAAAAAAA";
    private static final String UPDATED_FAIBLESS = "BBBBBBBBBB";

    @Autowired
    private EvaluationSheetRepository evaluationSheetRepository;

    @Autowired
    private EvaluationSheetMapper evaluationSheetMapper;

    @Autowired
    private EvaluationSheetService evaluationSheetService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEvaluationSheetMockMvc;

    private EvaluationSheet evaluationSheet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EvaluationSheet createEntity(EntityManager em) {
        EvaluationSheet evaluationSheet = new EvaluationSheet()
            .atout(DEFAULT_ATOUT)
            .faibless(DEFAULT_FAIBLESS);
        return evaluationSheet;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EvaluationSheet createUpdatedEntity(EntityManager em) {
        EvaluationSheet evaluationSheet = new EvaluationSheet()
            .atout(UPDATED_ATOUT)
            .faibless(UPDATED_FAIBLESS);
        return evaluationSheet;
    }

    @BeforeEach
    public void initTest() {
        evaluationSheet = createEntity(em);
    }

    @Test
    @Transactional
    public void createEvaluationSheet() throws Exception {
        int databaseSizeBeforeCreate = evaluationSheetRepository.findAll().size();
        // Create the EvaluationSheet
        EvaluationSheetDTO evaluationSheetDTO = evaluationSheetMapper.toDto(evaluationSheet);
        restEvaluationSheetMockMvc.perform(post("/api/evaluation-sheets").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(evaluationSheetDTO)))
            .andExpect(status().isCreated());

        // Validate the EvaluationSheet in the database
        List<EvaluationSheet> evaluationSheetList = evaluationSheetRepository.findAll();
        assertThat(evaluationSheetList).hasSize(databaseSizeBeforeCreate + 1);
        EvaluationSheet testEvaluationSheet = evaluationSheetList.get(evaluationSheetList.size() - 1);
        assertThat(testEvaluationSheet.getAtout()).isEqualTo(DEFAULT_ATOUT);
        assertThat(testEvaluationSheet.getFaibless()).isEqualTo(DEFAULT_FAIBLESS);
    }

    @Test
    @Transactional
    public void createEvaluationSheetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = evaluationSheetRepository.findAll().size();

        // Create the EvaluationSheet with an existing ID
        evaluationSheet.setId(1L);
        EvaluationSheetDTO evaluationSheetDTO = evaluationSheetMapper.toDto(evaluationSheet);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEvaluationSheetMockMvc.perform(post("/api/evaluation-sheets").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(evaluationSheetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EvaluationSheet in the database
        List<EvaluationSheet> evaluationSheetList = evaluationSheetRepository.findAll();
        assertThat(evaluationSheetList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEvaluationSheets() throws Exception {
        // Initialize the database
        evaluationSheetRepository.saveAndFlush(evaluationSheet);

        // Get all the evaluationSheetList
        restEvaluationSheetMockMvc.perform(get("/api/evaluation-sheets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(evaluationSheet.getId().intValue())))
            .andExpect(jsonPath("$.[*].atout").value(hasItem(DEFAULT_ATOUT)))
            .andExpect(jsonPath("$.[*].faibless").value(hasItem(DEFAULT_FAIBLESS)));
    }
    
    @Test
    @Transactional
    public void getEvaluationSheet() throws Exception {
        // Initialize the database
        evaluationSheetRepository.saveAndFlush(evaluationSheet);

        // Get the evaluationSheet
        restEvaluationSheetMockMvc.perform(get("/api/evaluation-sheets/{id}", evaluationSheet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(evaluationSheet.getId().intValue()))
            .andExpect(jsonPath("$.atout").value(DEFAULT_ATOUT))
            .andExpect(jsonPath("$.faibless").value(DEFAULT_FAIBLESS));
    }
    @Test
    @Transactional
    public void getNonExistingEvaluationSheet() throws Exception {
        // Get the evaluationSheet
        restEvaluationSheetMockMvc.perform(get("/api/evaluation-sheets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEvaluationSheet() throws Exception {
        // Initialize the database
        evaluationSheetRepository.saveAndFlush(evaluationSheet);

        int databaseSizeBeforeUpdate = evaluationSheetRepository.findAll().size();

        // Update the evaluationSheet
        EvaluationSheet updatedEvaluationSheet = evaluationSheetRepository.findById(evaluationSheet.getId()).get();
        // Disconnect from session so that the updates on updatedEvaluationSheet are not directly saved in db
        em.detach(updatedEvaluationSheet);
        updatedEvaluationSheet
            .atout(UPDATED_ATOUT)
            .faibless(UPDATED_FAIBLESS);
        EvaluationSheetDTO evaluationSheetDTO = evaluationSheetMapper.toDto(updatedEvaluationSheet);

        restEvaluationSheetMockMvc.perform(put("/api/evaluation-sheets").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(evaluationSheetDTO)))
            .andExpect(status().isOk());

        // Validate the EvaluationSheet in the database
        List<EvaluationSheet> evaluationSheetList = evaluationSheetRepository.findAll();
        assertThat(evaluationSheetList).hasSize(databaseSizeBeforeUpdate);
        EvaluationSheet testEvaluationSheet = evaluationSheetList.get(evaluationSheetList.size() - 1);
        assertThat(testEvaluationSheet.getAtout()).isEqualTo(UPDATED_ATOUT);
        assertThat(testEvaluationSheet.getFaibless()).isEqualTo(UPDATED_FAIBLESS);
    }

    @Test
    @Transactional
    public void updateNonExistingEvaluationSheet() throws Exception {
        int databaseSizeBeforeUpdate = evaluationSheetRepository.findAll().size();

        // Create the EvaluationSheet
        EvaluationSheetDTO evaluationSheetDTO = evaluationSheetMapper.toDto(evaluationSheet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEvaluationSheetMockMvc.perform(put("/api/evaluation-sheets").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(evaluationSheetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EvaluationSheet in the database
        List<EvaluationSheet> evaluationSheetList = evaluationSheetRepository.findAll();
        assertThat(evaluationSheetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEvaluationSheet() throws Exception {
        // Initialize the database
        evaluationSheetRepository.saveAndFlush(evaluationSheet);

        int databaseSizeBeforeDelete = evaluationSheetRepository.findAll().size();

        // Delete the evaluationSheet
        restEvaluationSheetMockMvc.perform(delete("/api/evaluation-sheets/{id}", evaluationSheet.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EvaluationSheet> evaluationSheetList = evaluationSheetRepository.findAll();
        assertThat(evaluationSheetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
