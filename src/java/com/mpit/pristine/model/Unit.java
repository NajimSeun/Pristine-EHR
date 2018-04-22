package com.mpit.pristine.model;

import java.util.List;

/**
 *
 * @author neemarh
 */
public class Unit {
    
    
    private String HeadOfUnit;
    private List<Clinic> Clinics;
    private String Name;

    public Unit(String HeadOfUnit, List<Clinic> Clinics, String Name) {
        this.HeadOfUnit = HeadOfUnit;
        this.Clinics = Clinics;
        this.Name = Name;
    }
    
    
    
    /**
     * Get the value of Name
     *
     * @return the value of Name
     */
    public String getName() {
        return Name;
    }

    /**
     * Set the value of Name
     *
     * @param Name new value of Name
     */
    public void setName(String Name) {
        this.Name = Name;
    }

   

    /**
     * Get the value of Clinics
     *
     * @return the value of Clinics
     */
    
    public List<Clinic> getClinics() {
        return Clinics;
    }

    /**
     * Set the value of Clinics
     *
     * @param Clinics new value of Clinics
     */
    public void setClinics(List<Clinic> Clinics) {
        this.Clinics = Clinics;
    }

    /**
     * Get the value of HeadOfUnit
     *
     * @return the value of HeadOfUnit
     */
    public String getHeadOfUnit() {
        return HeadOfUnit;
    }

    /**
     * Set the value of HeadOfUnit
     *
     * @param HeadOfUnit new value of HeadOfUnit
     */
    public void setHeadOfUnit(String HeadOfUnit) {
        this.HeadOfUnit = HeadOfUnit;
    }

    
}
