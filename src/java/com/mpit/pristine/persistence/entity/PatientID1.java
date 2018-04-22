package com.mpit.pristine.persistence.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author neemarh
 */
@Embeddable
public class PatientID1 implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name="hospitalname")
    private String HospitalName;
    @Column(name="hospitalcode")
    private String HospitalCode;
    @Column(name="pid")
    private String ID;

    public PatientID1() {
    }

    public PatientID1(String HospitalName, String HospitalCode, String ID) {
        this.HospitalName = HospitalName;
        this.HospitalCode = HospitalCode;
        this.ID = ID;
    }

    
    /**
     * Get the value of ID
     *
     * @return the value of ID
     */
    public String getID() {
        return ID;
    }

    /**
     * Set the value of ID
     *
     * @param ID new value of ID
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * Get the value of HospitalCode
     *
     * @return the value of HospitalCode
     */
    public String getHospitalCode() {
        return HospitalCode;
    }

    /**
     * Set the value of HospitalCode
     *
     * @param HospitalCode new value of HospitalCode
     */
    public void setHospitalCode(String HospitalCode) {
        this.HospitalCode = HospitalCode;
    }

    /**
     * Get the value of HospitalName
     *
     * @return the value of HospitalName
     */
    public String getHospitalName() {
        return HospitalName;
    }

    /**
     * Set the value of HospitalName
     *
     * @param HospitalName new value of HospitalName
     */
    public void setHospitalName(String HospitalName) {
        this.HospitalName = HospitalName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PatientID1 other = (PatientID1) obj;
        if (!Objects.equals(this.HospitalName, other.HospitalName)) {
            return false;
        }
        if (!Objects.equals(this.HospitalCode, other.HospitalCode)) {
            return false;
        }
        if (!Objects.equals(this.ID, other.ID)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.HospitalName);
        hash = 53 * hash + Objects.hashCode(this.HospitalCode);
        hash = 53 * hash + Objects.hashCode(this.ID);
        return hash;
    }

    @Override
    public String toString() {
        return "{" + "HospitalName=" + HospitalName + ", HospitalCode=" + HospitalCode + ", PatientID=" + ID + '}';
    }

    
}
