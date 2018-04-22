package com.mpit.pristine.persistence.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author najim
 */
@Entity
@DiscriminatorValue("dama")
public class DischargeAgainstMedicalAdvice extends AdmissionOutcome {
    private static final long serialVersionUID = 1L;

    private String reasonFor;
    private String stateBefore;

    public String getReasonFor() {
        return reasonFor;
    }

    public void setReasonFor(String reasonFor) {
        this.reasonFor = reasonFor;
    }

    public String getStateBefore() {
        return stateBefore;
    }

    public void setStateBefore(String stateBefore) {
        this.stateBefore = stateBefore;
    }
    
    
}
