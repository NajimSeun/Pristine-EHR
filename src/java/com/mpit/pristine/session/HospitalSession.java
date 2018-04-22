
package com.mpit.pristine.session;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.SetMultimap;

/**
 *
 * @author najim
 */
public class HospitalSession {

    
    private String Type;
    private long CreationTime;
    private String Tag;
    private boolean ExpirationTime;
    private long TimeLasted;
    private String Specialty;
    private String Unit;
    private String HospitalCode;
    
    private SetMultimap<String, String> personnelTypeToIDMap;
   

    public HospitalSession() {
           
        this.personnelTypeToIDMap = LinkedHashMultimap.create();
        

    }

    public SetMultimap<String, String> getPersonnelTypeToIDMap() {
        return personnelTypeToIDMap;
    }

    public void setPersonnelTypeToIDMap(SetMultimap<String, String> personnelTypeToIDMap) {
        this.personnelTypeToIDMap = personnelTypeToIDMap;
    }
    
    

    public HospitalSession( String HospitalCode, String Specialty, String Unit , String Type , String tag ){
        this.Type = Type;
        this.Specialty = Specialty;
        this.Unit = Unit;
        this.HospitalCode = HospitalCode;
        this.personnelTypeToIDMap = Multimaps.synchronizedSetMultimap(HashMultimap.<String, String>create());
        this.Tag = tag;
    }

    public void addToPersonnelTypeYToIDMap(String type, String personnelID) {
        this.personnelTypeToIDMap.put(type, personnelID);
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
     * Get the value of Unit
     *
     * @return the value of Unit
     */
    public String getUnit() {
        return Unit;
    }

    /**
     * Set the value of Unit
     *
     * @param Unit new value of Unit
     */
    public void setUnit(String Unit) {
        this.Unit = Unit;
    }

    /**
     * Get the value of Specialty
     *
     * @return the value of Specialty
     */
    public String getSpecialty() {
        return Specialty;
    }

    /**
     * Set the value of Specialty
     *
     * @param Specialty new value of Specialty
     */
    public void setSpecialty(String Specialty) {
        this.Specialty = Specialty;
    }

    /**
     * Get the value of TimeLasted
     *
     * @return the value of TimeLasted
     */
    public long getTimeLasted() {
        return TimeLasted;
    }

    /**
     * Set the value of TimeLasted
     *
     * @param TimeLasted new value of TimeLasted
     */
    public void setTimeLasted(long TimeLasted) {
        this.TimeLasted = TimeLasted;
    }

    /**
     * Get the value of ExpirationTime
     *
     * @return the value of ExpirationTime
     */
    public boolean isExpirationTime() {
        return ExpirationTime;
    }

    /**
     * Set the value of ExpirationTime
     *
     * @param ExpirationTime new value of ExpirationTime
     */
    public void setExpirationTime(boolean ExpirationTime) {
        this.ExpirationTime = ExpirationTime;
    }

    public String getTag() {
        return Tag;
    }

    public void setTag(String Tag) {
        this.Tag = Tag;
    }

     

    /**
     * Get the value of creationTime
     *
     * @return the value of creationTime
     */
    public long getCreationTime() {
        return CreationTime;
    }

    /**
     * Set the value of creationTime
     *
     * @param creationTime new value of creationTime
     */
    public void setCreationTime(long creationTime) {
        this.CreationTime = creationTime;
    }

    /**
     * Get the value of Type
     *
     * @return the value of Type
     */
    public String getType() {
        return Type;
    }

    /**
     * Set the value of Type
     *
     * @param Type new value of Type
     */
    public void setType(String Type) {
        this.Type = Type;
    }
    
    
}
