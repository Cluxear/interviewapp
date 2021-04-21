package com.tw.interview.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.tw.interview.domain.EvaluationSheet} entity.
 */
public class EvaluationSheetDTO implements Serializable {
    
    private Long id;

    private String atout;

    private String faibless;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAtout() {
        return atout;
    }

    public void setAtout(String atout) {
        this.atout = atout;
    }

    public String getFaibless() {
        return faibless;
    }

    public void setFaibless(String faibless) {
        this.faibless = faibless;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EvaluationSheetDTO)) {
            return false;
        }

        return id != null && id.equals(((EvaluationSheetDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EvaluationSheetDTO{" +
            "id=" + getId() +
            ", atout='" + getAtout() + "'" +
            ", faibless='" + getFaibless() + "'" +
            "}";
    }
}
