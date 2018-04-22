package com.mpit.pristine.persistence.entity;

import com.mpit.pristine.utility.Constants;
import java.text.SimpleDateFormat;
import javax.persistence.*;

/**
 *
 * @author najim
 */

@Entity
@DiscriminatorValue("discharge")
@NamedQueries({@NamedQuery(name="findDischargesByDateRange",query="select dis from Discharge dis where dis.outcomeEventDate  Between :start And :End")})
public class Discharge  extends AdmissionOutcome  {
    private static final long serialVersionUID = 1L;

    private String advice;
    private String dischargedTo;
    private String medications;
    private String dischargeBy ;
    

    @PostLoad
    public void dischargePostLoadCallback(){
       SimpleDateFormat df = new SimpleDateFormat(Constants.DATE_FORMAT_ONE);
       this.setOutcomeEventDateString(df.format(this.getOutcomeEventDate()));
        
    }
    
    
    
    public String getDischargeBy() {
        return dischargeBy;
    }

    public void setDischargeBy(String dischargeBy) {
        this.dischargeBy = dischargeBy;
    }

    public String getDischargedTo() {
        return dischargedTo;
    }

    public void setDischargedTo(String dischargedTo) {
        this.dischargedTo = dischargedTo;
    }
    
    
    

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

     

    public String getMedications() {
        return medications;
    }

    public void setMedications(String medications) {
        this.medications = medications;
    }
}
