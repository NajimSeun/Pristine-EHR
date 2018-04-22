package com.mpit.pristine.persistence.entity;

import com.mpit.pristine.persistence.info.Information;
import java.io.Serializable;
import java.util.Map;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 * @author najim
 */
@Entity
public class ContactInformation implements  Serializable {
    private static final long serialVersionUID = 1L;
    
    
    @Id @GeneratedValue
    private Long ciID;
    private String address;
    private String phoneNumber;
    private String workAddress;
    private String email;
    private String workplaceNumber;
    private String nameNextOfKin;
    private String relWithNOK;
    private String numNextOfKin;
    private String addressNextOfKin;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressNextOfKin() {
        return addressNextOfKin;
    }

    public void setAddressNextOfKin(String addressNextOfKin) {
        this.addressNextOfKin = addressNextOfKin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNameNextOfKin() {
        return nameNextOfKin;
    }

    public void setNameNextOfKin(String nameNextOfKin) {
        this.nameNextOfKin = nameNextOfKin;
    }

    public String getNumNextOfKin() {
        return numNextOfKin;
    }

    public void setNumNextOfKin(String numNextOfKin) {
        this.numNextOfKin = numNextOfKin;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRelWithNOK() {
        return relWithNOK;
    }

    public void setRelWithNOK(String relWithNOK) {
        this.relWithNOK = relWithNOK;
    }

    public String getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
    }

    public String getWorkplaceNumber() {
        return workplaceNumber;
    }

    public void setWorkplaceNumber(String workplaceNumber) {
        this.workplaceNumber = workplaceNumber;
    }

     

    public Long getCiID() {
        return ciID;
    }

    public void setCiID(Long ciID) {
        this.ciID = ciID;
    }
    
    
    
}
