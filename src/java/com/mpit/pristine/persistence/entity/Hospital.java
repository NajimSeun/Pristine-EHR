package com.mpit.pristine.persistence.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;

/**
 *
 * @author Hp
 */


@Entity
public class Hospital implements Serializable {
    @Id 
    private  String HospitalCode  ;
    private String HospitalName  ;
    
    @Basic(fetch=FetchType.LAZY)
    @Column(nullable=false)
    private String HospitalLocation  ;
    
    @Basic(fetch=FetchType.LAZY)
    @Column(nullable=false)
    private String HospitalContactNumber ;
    
    @Basic(fetch=FetchType.LAZY)
    @Column(nullable=true)
    private String HospitalEMail ;
    
    @Basic(fetch=FetchType.LAZY)
    @Column(nullable=true)
    private String HospitalWSiteAddress ;
    
    @Basic(fetch=FetchType.LAZY)
    @Column(nullable=true)
    private String HospitalLocationCountry ;
    
    @Basic(fetch=FetchType.LAZY)
    @Column(nullable=true)
    private String HospitalLocationState ;
    
    @Basic(fetch=FetchType.LAZY)
    @Column(nullable=true)
    private String HospitalLocationlocGov ;

    public String getHospitalLocationCountry() {
        return HospitalLocationCountry;
    }

    public void setHospitalLocationCountry(String HospitalLocationCountry) {
        this.HospitalLocationCountry = HospitalLocationCountry;
    }

    public String getHospitalLocationState() {
        return HospitalLocationState;
    }

    public void setHospitalLocationState(String HospitalLocationState) {
        this.HospitalLocationState = HospitalLocationState;
    }

    public String getHospitalLocationlocGov() {
        return HospitalLocationlocGov;
    }

    public void setHospitalLocationlocGov(String HospitalLocationlocGov) {
        this.HospitalLocationlocGov = HospitalLocationlocGov;
    }

    
    
    public String getHospitalCode() {
        return HospitalCode;
    }

    public void setHospitalCode(String HospitalCode) {
        this.HospitalCode = HospitalCode;
    }

    public String getHospitalContactNumber() {
        return HospitalContactNumber;
    }

    public void setHospitalContactNumber(String HospitalContactNumber) {
        this.HospitalContactNumber = HospitalContactNumber;
    }

    public String getHospitalEMail() {
        return HospitalEMail;
    }

    public void setHospitalEMail(String HospitalEMail) {
        this.HospitalEMail = HospitalEMail;
    }

    public String getHospitalLocation() {
        return HospitalLocation;
    }

    public void setHospitalLocation(String HospitalLocation) {
        this.HospitalLocation = HospitalLocation;
    }

    public String getHospitalName() {
        return HospitalName;
    }

    public void setHospitalName(String HospitalName) {
        this.HospitalName = HospitalName;
    }

    public String getHospitalWSiteAddress() {
        return HospitalWSiteAddress;
    }

    public void setHospitalWSiteAddress(String HospitalWSiteAddress) {
        this.HospitalWSiteAddress = HospitalWSiteAddress;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Hospital other = (Hospital) obj;
        if (!Objects.equals(this.HospitalCode, other.HospitalCode)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.HospitalCode);
        return hash;
    }

    public Hospital(String HospitalCode, String HospitalName, String HospitalLocation, String HospitalContactNumber, String HospitalEMail, String HospitalWSiteAddress, String HospitalLocationCountry, String HospitalLocationState, String HospitalLocationlocGov) {
        this.HospitalCode = HospitalCode;
        this.HospitalName = HospitalName;
        this.HospitalLocation = HospitalLocation;
        this.HospitalContactNumber = HospitalContactNumber;
        this.HospitalEMail = HospitalEMail;
        this.HospitalWSiteAddress = HospitalWSiteAddress;
        this.HospitalLocationCountry = HospitalLocationCountry;
        this.HospitalLocationState = HospitalLocationState;
        this.HospitalLocationlocGov = HospitalLocationlocGov;
    }
    
    public Hospital(){
    }
}
