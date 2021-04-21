package com.tw.interview.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

import com.tw.interview.domain.enumeration.InterviewType;

import com.tw.interview.domain.enumeration.InterviewResult;

/**
 * A Interview.
 */
@Entity
@Table(name = "interview")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Interview implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "interview_date")
    private Instant interviewDate;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "modified_at")
    private Instant modifiedAt;

    @Column(name = "result_attributed_at")
    private Instant resultAttributedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private InterviewType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "result")
    private InterviewResult result;

    @Column(name = "is_date_fixed")
    private Boolean isDateFixed;

    @Column(name = "note")
    private String note;

    @OneToOne
    @JoinColumn(unique = true)
    private EvaluationSheet evaluationSheet;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getInterviewDate() {
        return interviewDate;
    }

    public Interview interviewDate(Instant interviewDate) {
        this.interviewDate = interviewDate;
        return this;
    }

    public void setInterviewDate(Instant interviewDate) {
        this.interviewDate = interviewDate;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Interview createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getModifiedAt() {
        return modifiedAt;
    }

    public Interview modifiedAt(Instant modifiedAt) {
        this.modifiedAt = modifiedAt;
        return this;
    }

    public void setModifiedAt(Instant modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Instant getResultAttributedAt() {
        return resultAttributedAt;
    }

    public Interview resultAttributedAt(Instant resultAttributedAt) {
        this.resultAttributedAt = resultAttributedAt;
        return this;
    }

    public void setResultAttributedAt(Instant resultAttributedAt) {
        this.resultAttributedAt = resultAttributedAt;
    }

    public InterviewType getType() {
        return type;
    }

    public Interview type(InterviewType type) {
        this.type = type;
        return this;
    }

    public void setType(InterviewType type) {
        this.type = type;
    }

    public InterviewResult getResult() {
        return result;
    }

    public Interview result(InterviewResult result) {
        this.result = result;
        return this;
    }

    public void setResult(InterviewResult result) {
        this.result = result;
    }

    public Boolean isIsDateFixed() {
        return isDateFixed;
    }

    public Interview isDateFixed(Boolean isDateFixed) {
        this.isDateFixed = isDateFixed;
        return this;
    }

    public void setIsDateFixed(Boolean isDateFixed) {
        this.isDateFixed = isDateFixed;
    }

    public String getNote() {
        return note;
    }

    public Interview note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public EvaluationSheet getEvaluationSheet() {
        return evaluationSheet;
    }

    public Interview evaluationSheet(EvaluationSheet evaluationSheet) {
        this.evaluationSheet = evaluationSheet;
        return this;
    }

    public void setEvaluationSheet(EvaluationSheet evaluationSheet) {
        this.evaluationSheet = evaluationSheet;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Interview)) {
            return false;
        }
        return id != null && id.equals(((Interview) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Interview{" +
            "id=" + getId() +
            ", interviewDate='" + getInterviewDate() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", modifiedAt='" + getModifiedAt() + "'" +
            ", resultAttributedAt='" + getResultAttributedAt() + "'" +
            ", type='" + getType() + "'" +
            ", result='" + getResult() + "'" +
            ", isDateFixed='" + isIsDateFixed() + "'" +
            ", note='" + getNote() + "'" +
            "}";
    }
}
