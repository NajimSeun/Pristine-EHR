package com.mpit.pristine.persistence.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.*;

/**
 *
 * @author najim
 */
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="outcome_type",discriminatorType=DiscriminatorType.STRING)
public abstract class AdmissionOutcome implements Serializable {
   
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id ;
    @Temporal(TemporalType.DATE)
    private Date outcomeEventDate;
    private String description;
    @Temporal(TemporalType.DATE)
    private Date outcomeEventInstanceDate ;
    @OneToOne(mappedBy="outcome")
    private Admission  admission ;
    
    @Transient
    private String OutcomeEventDateString ;

    public String getOutcomeEventDateString() {
        return OutcomeEventDateString;
    }

    public void setOutcomeEventDateString(String OutcomeEventDateString) {
        this.OutcomeEventDateString = OutcomeEventDateString;
    }

     
     
    public Date getOutcomeEventInstanceDate() {
        return outcomeEventInstanceDate;
    }

    public void setOutcomeEventInstanceDate(Date outcomeEventInstanceDate) {
        this.outcomeEventInstanceDate = outcomeEventInstanceDate;
    }

    public Admission getAdmission() {
        return admission;
    }

    public void setAdmission(Admission admission) {
        this.admission = admission;
    }

    
    
    public Date getOutcomeEventDate() {
        return outcomeEventDate;
    }

    public void setOutcomeEventDate(Date outcomeEventDate) {
        this.outcomeEventDate = outcomeEventDate;
    }
    
    

    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AdmissionOutcome other = (AdmissionOutcome) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.outcomeEventDate);
        hash = 79 * hash + Objects.hashCode(this.description);
        return hash;
    }
    
     
}
