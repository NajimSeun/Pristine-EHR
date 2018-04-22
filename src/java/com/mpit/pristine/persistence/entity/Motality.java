package com.mpit.pristine.persistence.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author najim
 */
@Entity
@DiscriminatorValue("motality")
public class Motality extends AdmissionOutcome {

    private static final long serialVersionUID = 1L;
    private String certifiedBy;
    private String cause;

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getCertifiedBy() {
        return certifiedBy;
    }

    public void setCertifiedBy(String certifiedBy) {
        this.certifiedBy = certifiedBy;
    }
}
