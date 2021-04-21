package com.tw.interview.web.rest;

import com.tw.interview.InterviewappApp;
import com.tw.interview.config.TestSecurityConfiguration;
import com.tw.interview.domain.InterviewEvent;
import com.tw.interview.domain.Event;
import com.tw.interview.repository.InterviewEventRepository;
import com.tw.interview.service.InterviewEventService;
import com.tw.interview.service.dto.InterviewEventDTO;
import com.tw.interview.service.mapper.InterviewEventMapper;

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
 * Integration tests for the {@link InterviewEventResource} REST controller.
 */
@SpringBootTest(classes = { InterviewappApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class InterviewEventResourceIT {

    private static final Boolean DEFAULT_IS_FIXED = false;
    private static final Boolean UPDATED_IS_FIXED = true;

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private InterviewEventRepository interviewEventRepository;

    @Autowired
    private InterviewEventMapper interviewEventMapper;

    @Autowired
    private InterviewEventService interviewEventService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInterviewEventMockMvc;

    private InterviewEvent interviewEvent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InterviewEvent createEntity(EntityManager em) {
        InterviewEvent interviewEvent = new InterviewEvent()
            .isFixed(DEFAULT_IS_FIXED)
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION);
        // Add required entity
        Event event;
        if (TestUtil.findAll(em, Event.class).isEmpty()) {
            event = EventResourceIT.createEntity(em);
            em.persist(event);
            em.flush();
        } else {
            event = TestUtil.findAll(em, Event.class).get(0);
        }
        interviewEvent.setEvent(event);
        return interviewEvent;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InterviewEvent createUpdatedEntity(EntityManager em) {
        InterviewEvent interviewEvent = new InterviewEvent()
            .isFixed(UPDATED_IS_FIXED)
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION);
        // Add required entity
        Event event;
        if (TestUtil.findAll(em, Event.class).isEmpty()) {
            event = EventResourceIT.createUpdatedEntity(em);
            em.persist(event);
            em.flush();
        } else {
            event = TestUtil.findAll(em, Event.class).get(0);
        }
        interviewEvent.setEvent(event);
        return interviewEvent;
    }

    @BeforeEach
    public void initTest() {
        interviewEvent = createEntity(em);
    }

    @Test
    @Transactional
    public void createInterviewEvent() throws Exception {
        int databaseSizeBeforeCreate = interviewEventRepository.findAll().size();
        // Create the InterviewEvent
        InterviewEventDTO interviewEventDTO = interviewEventMapper.toDto(interviewEvent);
        restInterviewEventMockMvc.perform(post("/api/interview-events").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(interviewEventDTO)))
            .andExpect(status().isCreated());

        // Validate the InterviewEvent in the database
        List<InterviewEvent> interviewEventList = interviewEventRepository.findAll();
        assertThat(interviewEventList).hasSize(databaseSizeBeforeCreate + 1);
        InterviewEvent testInterviewEvent = interviewEventList.get(interviewEventList.size() - 1);
        assertThat(testInterviewEvent.isIsFixed()).isEqualTo(DEFAULT_IS_FIXED);
        assertThat(testInterviewEvent.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testInterviewEvent.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the id for MapsId, the ids must be same
        assertThat(testInterviewEvent.getId()).isEqualTo(testInterviewEvent.getEvent().getId());
    }

    @Test
    @Transactional
    public void createInterviewEventWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = interviewEventRepository.findAll().size();

        // Create the InterviewEvent with an existing ID
        interviewEvent.setId(1L);
        InterviewEventDTO interviewEventDTO = interviewEventMapper.toDto(interviewEvent);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInterviewEventMockMvc.perform(post("/api/interview-events").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(interviewEventDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InterviewEvent in the database
        List<InterviewEvent> interviewEventList = interviewEventRepository.findAll();
        assertThat(interviewEventList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void updateInterviewEventMapsIdAssociationWithNewId() throws Exception {
        // Initialize the database
        interviewEventRepository.saveAndFlush(interviewEvent);
        int databaseSizeBeforeCreate = interviewEventRepository.findAll().size();

        // Add a new parent entity
        Event event = EventResourceIT.createUpdatedEntity(em);
        em.persist(event);
        em.flush();

        // Load the interviewEvent
        InterviewEvent updatedInterviewEvent = interviewEventRepository.findById(interviewEvent.getId()).get();
        // Disconnect from session so that the updates on updatedInterviewEvent are not directly saved in db
        em.detach(updatedInterviewEvent);

        // Update the Event with new association value
        updatedInterviewEvent.setEvent(event);
        InterviewEventDTO updatedInterviewEventDTO = interviewEventMapper.toDto(updatedInterviewEvent);

        // Update the entity
        restInterviewEventMockMvc.perform(put("/api/interview-events").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedInterviewEventDTO)))
            .andExpect(status().isOk());

        // Validate the InterviewEvent in the database
        List<InterviewEvent> interviewEventList = interviewEventRepository.findAll();
        assertThat(interviewEventList).hasSize(databaseSizeBeforeCreate);
        InterviewEvent testInterviewEvent = interviewEventList.get(interviewEventList.size() - 1);

        // Validate the id for MapsId, the ids must be same
        // Uncomment the following line for assertion. However, please note that there is a known issue and uncommenting will fail the test.
        // Please look at https://github.com/jhipster/generator-jhipster/issues/9100. You can modify this test as necessary.
        // assertThat(testInterviewEvent.getId()).isEqualTo(testInterviewEvent.getEvent().getId());
    }

    @Test
    @Transactional
    public void getAllInterviewEvents() throws Exception {
        // Initialize the database
        interviewEventRepository.saveAndFlush(interviewEvent);

        // Get all the interviewEventList
        restInterviewEventMockMvc.perform(get("/api/interview-events?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(interviewEvent.getId().intValue())))
            .andExpect(jsonPath("$.[*].isFixed").value(hasItem(DEFAULT_IS_FIXED.booleanValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getInterviewEvent() throws Exception {
        // Initialize the database
        interviewEventRepository.saveAndFlush(interviewEvent);

        // Get the interviewEvent
        restInterviewEventMockMvc.perform(get("/api/interview-events/{id}", interviewEvent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(interviewEvent.getId().intValue()))
            .andExpect(jsonPath("$.isFixed").value(DEFAULT_IS_FIXED.booleanValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingInterviewEvent() throws Exception {
        // Get the interviewEvent
        restInterviewEventMockMvc.perform(get("/api/interview-events/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInterviewEvent() throws Exception {
        // Initialize the database
        interviewEventRepository.saveAndFlush(interviewEvent);

        int databaseSizeBeforeUpdate = interviewEventRepository.findAll().size();

        // Update the interviewEvent
        InterviewEvent updatedInterviewEvent = interviewEventRepository.findById(interviewEvent.getId()).get();
        // Disconnect from session so that the updates on updatedInterviewEvent are not directly saved in db
        em.detach(updatedInterviewEvent);
        updatedInterviewEvent
            .isFixed(UPDATED_IS_FIXED)
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION);
        InterviewEventDTO interviewEventDTO = interviewEventMapper.toDto(updatedInterviewEvent);

        restInterviewEventMockMvc.perform(put("/api/interview-events").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(interviewEventDTO)))
            .andExpect(status().isOk());

        // Validate the InterviewEvent in the database
        List<InterviewEvent> interviewEventList = interviewEventRepository.findAll();
        assertThat(interviewEventList).hasSize(databaseSizeBeforeUpdate);
        InterviewEvent testInterviewEvent = interviewEventList.get(interviewEventList.size() - 1);
        assertThat(testInterviewEvent.isIsFixed()).isEqualTo(UPDATED_IS_FIXED);
        assertThat(testInterviewEvent.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testInterviewEvent.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingInterviewEvent() throws Exception {
        int databaseSizeBeforeUpdate = interviewEventRepository.findAll().size();

        // Create the InterviewEvent
        InterviewEventDTO interviewEventDTO = interviewEventMapper.toDto(interviewEvent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInterviewEventMockMvc.perform(put("/api/interview-events").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(interviewEventDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InterviewEvent in the database
        List<InterviewEvent> interviewEventList = interviewEventRepository.findAll();
        assertThat(interviewEventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInterviewEvent() throws Exception {
        // Initialize the database
        interviewEventRepository.saveAndFlush(interviewEvent);

        int databaseSizeBeforeDelete = interviewEventRepository.findAll().size();

        // Delete the interviewEvent
        restInterviewEventMockMvc.perform(delete("/api/interview-events/{id}", interviewEvent.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InterviewEvent> interviewEventList = interviewEventRepository.findAll();
        assertThat(interviewEventList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
