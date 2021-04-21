package com.tw.interview.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A InterviewEvent.
 */
@Entity
@Table(name = "interview_event")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class InterviewEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column(name = "is_fixed")
    private Boolean isFixed;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @OneToOne

    @MapsId
    @JoinColumn(name = "id")
    private Event event;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isIsFixed() {
        return isFixed;
    }

    public InterviewEvent isFixed(Boolean isFixed) {
        this.isFixed = isFixed;
        return this;
    }

    public void setIsFixed(Boolean isFixed) {
        this.isFixed = isFixed;
    }

    public String getTitle() {
        return title;
    }

    public InterviewEvent title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public InterviewEvent description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Event getEvent() {
        return event;
    }

    public InterviewEvent event(Event event) {
        this.event = event;
        return this;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InterviewEvent)) {
            return false;
        }
        return id != null && id.equals(((InterviewEvent) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InterviewEvent{" +
            "id=" + getId() +
            ", isFixed='" + isIsFixed() + "'" +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
