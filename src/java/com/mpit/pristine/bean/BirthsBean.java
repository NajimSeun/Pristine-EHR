package com.mpit.pristine.bean;

import com.mpit.pristine.ejb.VitalStatisticsEJB;
import com.mpit.pristine.persistence.entity.Birth;
import com.mpit.pristine.utility.Constants;
import com.mpit.pristine.utility.ELResolver;
import com.mpit.pristine.utility.Utility;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.jboss.weld.context.ConversationContext;
import org.jboss.weld.context.http.Http;
import org.primefaces.context.RequestContext;

/**
 *
 * @author najim
 */
@Named(value = "birthsBean")
@ConversationScoped
public class BirthsBean  implements Serializable{

    /**
     * Creates a new instance of BirthsBean
     */
    private List<Birth> births;
    private MonthSelectBean msb;
    @Inject
    private VitalStatisticsEJB vsEjb;
    @Inject
    private MedicalRecordManagerBean mrmb;
    @Http
    @Inject
    private ConversationContext cc ; 
    private static final String BIRTHSBEANCID = "BBCID" ;
    @Inject
    private Conversation conversation ;
    public MedicalRecordManagerBean getMrmb() {
        return mrmb;
    }
    
    public void initComponent(){
      
        Utility.initConversation(conversation, mrmb, BIRTHSBEANCID);
        
    }
    public void setMrmb(MedicalRecordManagerBean mrmb) {
        this.mrmb = mrmb;
    }
    
    public VitalStatisticsEJB getVsEjb() {
        return vsEjb;
    }
    
    public void setVsEjb(VitalStatisticsEJB vsEjb) {
        this.vsEjb = vsEjb;
    }
    
    public MonthSelectBean getMsb() {
        return msb;
    }
    
    public void setMsb(MonthSelectBean msb) {
        this.msb = msb;
    }
    
    public BirthsBean() {
    }
    
    public List<Birth> getBirths() {
        return births;
    }
    
    public void setBirths(List<Birth> births) {
        this.births = births;
    }
    
    public void births_BirthsSearchButtonClickedListener() {
        FacesContext fcon = FacesContext.getCurrentInstance();
        RequestContext rcon = RequestContext.getCurrentInstance();
        String birthSearchButtonID = fcon.getExternalContext().getRequestParameterMap().get("BSBID");
        String birthMonthIntString = Constants.MONTHS_OF_YEAR_MAP.get(this.msb.getSelectedMonth());
        if (birthMonthIntString == null) {
            fcon.addMessage(birthSearchButtonID, new FacesMessage(FacesMessage.SEVERITY_INFO, ELResolver.resolve(fcon, "#{mrmp.noApprDateSelected}"), ELResolver.resolve(fcon, "#{mrmp.noApprDateSelected}")));
            return;
        }
        int birthYear = Integer.parseInt(this.msb.getSelectedYear());
        int birthNonth = Integer.parseInt(birthMonthIntString); //TODo:checkout
        Calendar monthStart = Calendar.getInstance();
        monthStart.set(birthYear, Integer.parseInt(birthMonthIntString), 1);
        Date monthStartDate = Utility.createStandardizedDate(monthStart);
        
        Calendar monthEnd = Calendar.getInstance();
        monthEnd.set(birthYear, birthYear, monthStart.getMaximum(Calendar.MONTH));
        Date monthEndDate = Utility.createStandardizedDate(monthEnd);
        
        births = this.vsEjb.findBirthByDateRange(monthStartDate, monthEndDate);
        if (births == null) {
            rcon.execute("noBirthsDlg.show()");
        }
        
    }
    
    public void births_BirthsEditBirthButtonClickedListener() {
        FacesContext fcon = FacesContext.getCurrentInstance();
        RequestContext rcon = RequestContext.getCurrentInstance();
        if (this.mrmb.getBirthForEdit() == null) {
            Utility.addMessage(fcon, "BEBID", "#{mrmp.nobirthselectedit}");
            return;
        }
        
        rcon.execute("birthRegDlg.show()");
        
    }
    
    public void births_BirthsCancelButtonClickedListener(){
      Utility.endConversations(BIRTHSBEANCID, cc, conversation, mrmb);
    }
}
