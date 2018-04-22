package com.mpit.pristine.model;

/**
 *
 * @author neemarh
 */
public class Clinic {
    
    private String DayOfWeek;
    private String Start;

    public Clinic(String DayOfWeek, String Start) {
        this.DayOfWeek = DayOfWeek;
        this.Start = Start;
    }
    
    /**
     * Get the value of Start
     *
     * @return the value of Start
     */
    public String getStart() {
        return Start;
    }

    /**
     * Set the value of Start
     *
     * @param Start new value of Start
     */
    public void setStart(String Start) {
        this.Start = Start;
    }

    /**
     * Get the value of DayOfWeek
     *
     * @return the value of DayOfWeek
     */
    public String getDayOfWeek() {
        return DayOfWeek;
    }

    /**
     * Set the value of DayOfWeek
     *
     * @param DayOfWeek new value of DayOfWeek
     */
    public void setDayOfWeek(String DayOfWeek) {
        this.DayOfWeek = DayOfWeek;
    }

}
