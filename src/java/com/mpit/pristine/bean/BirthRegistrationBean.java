package com.mpit.pristine.bean;

import com.mpit.pristine.ejb.VitalStatisticsEJB;
import com.mpit.pristine.persistence.entity.Birth;
import com.mpit.pristine.qualifiers.SelectedBirth;
import com.mpit.pristine.utility.Constants;
import com.mpit.pristine.utility.Utility;
import java.io.Serializable;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Named;
import javax.inject.Inject;
import org.jboss.weld.context.ConversationContext;
import org.jboss.weld.context.http.Http;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author najim
 */
@Named(value = "birthRegistrationBean")
@ConversationScoped
public class BirthRegistrationBean implements Serializable {

    /**
     * Creates a new instance of BirthRegistrationBean
     */
    @Inject
    @SelectedBirth
    private Birth birth;
    @Inject
    private VitalStatisticsEJB vsEjb;
    private boolean buttonDisabled = false;
    @Inject
    private MedicalRecordManagerBean mrmb;
    private static final String BIRTHREGBEANCID = "BRBCID";
    @Inject
    private Conversation conversation;
    private boolean apgarDisabled = true;
    @Http
    @Inject
    private ConversationContext cc;

    public MedicalRecordManagerBean getMrmb() {
        return mrmb;
    }
    
    public void setMrmb(MedicalRecordManagerBean mrmb) {
        this.mrmb = mrmb;
    }
    
    public boolean isApgarDisabled() {
        return apgarDisabled;
    }
    
    public void setApgarDisabled(boolean apgarDisabled) {
        this.apgarDisabled = apgarDisabled;
    }
    
    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }
    
    public void initConversation() {
        Utility.initConversation(conversation, mrmb, BIRTHREGBEANCID);
    }
    
    public boolean isButtonDisabled() {
        return buttonDisabled;
    }
    
    public void setButtonDisabled(boolean buttonDisabled) {
        this.buttonDisabled = buttonDisabled;
    }
    
    public VitalStatisticsEJB getVsEjb() {
        return vsEjb;
    }
    
    public void setVsEjb(VitalStatisticsEJB vsEjb) {
        this.vsEjb = vsEjb;
    }
    
    public BirthRegistrationBean() {
    }
    
    public Birth getBirth() {
        return birth;
    }
    
    public void setBirth(Birth birth) {
        this.birth = birth;
    }
    
    public void birth_BirthTimeOfBirthSelectAjaxListener(SelectEvent evt) {
        birth.computeBirthTimeString();
        
    }

    //TODO: Prevent Registration of same birth
    public void birth_BirthRegisterBirthButtonClickedListener() {
        RequestContext rcon = RequestContext.getCurrentInstance();
        FacesContext fcon = FacesContext.getCurrentInstance();
        this.birth.computeActualDateOfBirth();
        
        if (this.mrmb.getBirthForEdit() != null) {  //An Edit
            this.mrmb.setBirthForEdit(null);
            rcon.update("birthsForm:birthsDataTable");
            
        } else { //Not An Edit
            this.birth.setMother(this.mrmb.getSelectedPatient());
            if (this.vsEjb.hasRecentBirth(this.birth.getMother()) && this.birth.getGestationType().equals(Constants.SINGLE)) {
                rcon.execute("multipleBirthConfirmDialog.show()");
                return;
            }
            
        }
        
        if (!vsEjb.registerBirth(birth)) {
            rcon.execute("birthRegUnSuccessfulDlg.show()");
        } else {
            rcon.execute("birthRegSuccessDlg.show()");
            this.buttonDisabled = true;
            
        }
        
    }
    
    public void birt_BirthRegistrationDeliveredInputSelectAjaxListener(AjaxBehaviorEvent evt) {
        switch (this.birth.getDelivered()) {
            case Constants.DEAD:
                this.apgarDisabled = true;
                break;
            case Constants.LIVE:
                this.apgarDisabled = false;
                break;
        }
    }
    
    public void birth_BirthRegistrationCloseButtonClickedListener() {
        Utility.endConversations(BIRTHREGBEANCID, cc, conversation, mrmb);
    }
    
    public void birth_BirthRegistrationCloseButtonAjaxListener(CloseEvent evt) {
        Utility.endConversations(BIRTHREGBEANCID, cc, conversation, mrmb);
    }
}
