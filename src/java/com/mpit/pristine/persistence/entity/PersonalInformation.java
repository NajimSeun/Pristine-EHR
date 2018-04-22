package com.mpit.pristine.persistence.entity;

import java.io.Serializable;
import java.util.logging.Logger;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 * @author najim
 */
@Entity
public class PersonalInformation implements  Serializable{
    
    
    
    private static final Logger LOG = Logger.getLogger(PersonalInformation.class.getName());
    private static final long serialVersionUID = 1L;
    
    @Id @GeneratedValue
    private Long piID;
    private String occupation;
    private String sex;
    private String religion;
    private String country;
    private String stateOfOrigin;
    private String localGovArea;
    private String maritalStatus;
    private String ethnicity ;

    public String getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }
    
    
    /**
     * Get the value of maritalStatus
     *
     * @return the value of maritalStatus
     */
    public String getMaritalStatus() {
        return maritalStatus;
    }

    /**
     * Set the value of maritalStatus
     *
     * @param maritalStatus new value of maritalStatus
     */
    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    

    public String getStateOfOrigin() {
        return stateOfOrigin;
    }

    public void setStateOfOrigin(String stateOfOrigin) {
        this.stateOfOrigin = stateOfOrigin;
    }

    
    
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    

    

    public String getLocalGovArea() {
        return localGovArea;
    }

    public void setLocalGovArea(String localGovArea) {
        this.localGovArea = localGovArea;
    }

    

    

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    
    
    

    public Long getPiID() {
        return piID;
    }

    public void setPiID(Long piID) {
        this.piID = piID;
    }
    
    
    
    
}
