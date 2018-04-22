package com.mpit.pristine.model;

import java.util.List;

/**
 *
 * @author neemarh
 */
public class Department {

    private String Name;
    
    private List<Unit> Units;

    public Department(String Name, List<Unit> Units) {
        this.Name = Name;
        this.Units = Units;
    }
    
    
    public Department(String Name) {
        this.Name = Name;
    }

    /**
     * Get the value of Units
     *
     * @return the value of Units
     */
    
    public List<Unit> getUnits() {
        return Units;
    }

    /**
     * Set the value of Units
     *
     * @param Units new value of Units
     */
    public void setUnits(List<Unit> Units) {
        this.Units = Units;
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

    
    
}
