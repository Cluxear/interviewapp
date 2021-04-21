package com.tw.interview.service.dto;

import java.time.Instant;
import java.io.Serializable;
import com.tw.interview.domain.enumeration.InterviewType;
import com.tw.interview.domain.enumeration.InterviewResult;

/**
 * A DTO for the {@link com.tw.interview.domain.Interview} entity.
 */
public class InterviewDTO implements Serializable {
    
    private Long id;

    private Instant interviewDate;

    private Instant createdAt;

    private Instant modifiedAt;

    private Instant resultAttributedAt;

    private InterviewType type;

    private InterviewResult result;

    private Boolean isDateFixed;

    private String note;


    private Long evaluationSheetId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getInterviewDate() {
        return interviewDate;
    }

    public void setInterviewDate(Instant interviewDate) {
        this.interviewDate = interviewDate;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Instant modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Instant getResultAttributedAt() {
        return resultAttributedAt;
    }

    public void setResultAttributedAt(Instant resultAttributedAt) {
        this.resultAttributedAt = resultAttributedAt;
    }

    public InterviewType getType() {
        return type;
    }

    public void setType(InterviewType type) {
        this.type = type;
    }

    public InterviewResult getResult() {
        return result;
    }

    public void setResult(InterviewResult result) {
        this.result = result;
    }

    public Boolean isIsDateFixed() {
        return isDateFixed;
    }

    public void setIsDateFixed(Boolean isDateFixed) {
        this.isDateFixed = isDateFixed;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getEvaluationSheetId() {
        return evaluationSheetId;
    }

    public void setEvaluationSheetId(Long evaluationSheetId) {
        this.evaluationSheetId = evaluationSheetId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InterviewDTO)) {
            return false;
        }

        return id != null && id.equals(((InterviewDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InterviewDTO{" +
            "id=" + getId() +
            ", interviewDate='" + getInterviewDate() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", modifiedAt='" + getModifiedAt() + "'" +
            ", resultAttributedAt='" + getResultAttributedAt() + "'" +
            ", type='" + getType() + "'" +
            ", result='" + getResult() + "'" +
            ", isDateFixed='" + isIsDateFixed() + "'" +
            ", note='" + getNote() + "'" +
            ", evaluationSheetId=" + getEvaluationSheetId() +
            "}";
    }
}
