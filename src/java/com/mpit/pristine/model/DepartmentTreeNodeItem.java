package com.mpit.pristine.model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author najim
 */
public class DepartmentTreeNodeItem  implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private String name;
    private String schedule;
    private String personnel;
    

    public DepartmentTreeNodeItem(String Name, String Schedule, String Personnel) {
        this.name = Name;
        this.schedule = Schedule;
        this.personnel = Personnel;
    }
    
    
    /**
     * Get the value of Personnel
     *
     * @return the value of Personnel
     */
    public String getPersonnel() {
        return personnel;
    }

    /**
     * Set the value of Personnel
     *
     * @param Personnel new value of Personnel
     */
    public void setPersonnel(String Personnel) {
        this.personnel = Personnel;
    }

    /**
     * Get the value of Schedule
     *
     * @return the value of Schedule
     */
    public String getSchedule() {
        return schedule;
    }

    /**
     * Set the value of Schedule
     *
     * @param Schedule new value of Schedule
     */
    public void setSchedule(String Schedule) {
        this.schedule = Schedule;
    }

    /**
     * Get the value of Name
     *
     * @return the value of Name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the value of Name
     *
     * @param Name new value of Name
     */
    public void setName(String Name) {
        this.name = Name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DepartmentTreeNodeItem other = (DepartmentTreeNodeItem) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.schedule, other.schedule)) {
            return false;
        }
        if (!Objects.equals(this.personnel, other.personnel)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.name);
        hash = 97 * hash + Objects.hashCode(this.schedule);
        hash = 97 * hash + Objects.hashCode(this.personnel);
        return hash;
    }

    
}
