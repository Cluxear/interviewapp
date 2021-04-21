package com.tw.interview.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A EvaluationSheet.
 */
@Entity
@Table(name = "evaluation_sheet")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EvaluationSheet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "atout")
    private String atout;

    @Column(name = "faibless")
    private String faibless;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAtout() {
        return atout;
    }

    public EvaluationSheet atout(String atout) {
        this.atout = atout;
        return this;
    }

    public void setAtout(String atout) {
        this.atout = atout;
    }

    public String getFaibless() {
        return faibless;
    }

    public EvaluationSheet faibless(String faibless) {
        this.faibless = faibless;
        return this;
    }

    public void setFaibless(String faibless) {
        this.faibless = faibless;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EvaluationSheet)) {
            return false;
        }
        return id != null && id.equals(((EvaluationSheet) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EvaluationSheet{" +
            "id=" + getId() +
            ", atout='" + getAtout() + "'" +
            ", faibless='" + getFaibless() + "'" +
            "}";
    }
}
