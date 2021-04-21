package com.tw.interview.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.tw.interview.domain.InterviewEvent} entity.
 */
public class InterviewEventDTO implements Serializable {
    
    private Long id;

    private Boolean isFixed;

    private String title;

    private String description;


    private Long eventId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isIsFixed() {
        return isFixed;
    }

    public void setIsFixed(Boolean isFixed) {
        this.isFixed = isFixed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InterviewEventDTO)) {
            return false;
        }

        return id != null && id.equals(((InterviewEventDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InterviewEventDTO{" +
            "id=" + getId() +
            ", isFixed='" + isIsFixed() + "'" +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", eventId=" + getEventId() +
            "}";
    }
}
