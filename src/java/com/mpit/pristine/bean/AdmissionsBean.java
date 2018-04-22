/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpit.pristine.bean;

import com.mpit.pristine.ejb.AdmissionEJB;
import com.mpit.pristine.ejb.AppointmentEJB;
import com.mpit.pristine.persistence.entity.*;
import com.mpit.pristine.utility.Constants;
import com.mpit.pristine.utility.ELResolver;
import com.mpit.pristine.utility.Utility;
import javax.inject.Named;
import javax.enterprise.context.ConversationScoped;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.Conversation;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import org.jboss.weld.context.ConversationContext;
import org.jboss.weld.context.http.Http;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;

/**
 *
 * @author najim
 */
@Named(value = "admissionsBean")
@ConversationScoped
public class AdmissionsBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private Date admissionDate;
    private Date admissionDate_Start;
    private Date admissionDate_End;
    private boolean fieldSetCollapsed = true;
    private boolean useRangeChecked = true;
    @Inject
    private MedicalRecordManagerBean mrmb;
    @Inject
    private ResourceLoadingHelperBean rlhb;
    @Inject
    private Conversation conversation;
    @Inject
    @Http
    private ConversationContext cc;
    private static final String ADMISSIONSCID = "ADMSCID";
    private List<Admission> admissions;
    @Inject
    private AdmissionEJB admEJB;
    private String admissionDataTableHeaderMsg;
    private Admission selectedAdmission;
    private Admission admissionForOutcome;
    private Date outcomeDate;

    private Date appointmentDate;
    private List<String> filterRemarkOptions = new ArrayList<>();
    @Inject
    private AppointmentEJB appEJB;
    private boolean buttonDisabled = false;
    private String mortality_CertifiedBy ;

    public String getMortality_CertifiedBy() {
        return mortality_CertifiedBy;
    }

    public void setMortality_CertifiedBy(String mortality_CertifiedBy) {
        this.mortality_CertifiedBy = mortality_CertifiedBy;
    }

     
    
    
    public boolean isButtonDisabled() {
        return buttonDisabled;
    }

    public void setButtonDisabled(boolean buttonDisabled) {
        this.buttonDisabled = buttonDisabled;
    }

    public AppointmentEJB getAppEJB() {
        return appEJB;
    }

    public void setAppEJB(AppointmentEJB appEJB) {
        this.appEJB = appEJB;
    }

    public Admission getAdmissionForOutcome() {
        return admissionForOutcome;
    }

    public void setAdmissionForOutcome(Admission admissionForOutcome) {
        this.admissionForOutcome = admissionForOutcome;
    }

    public List<String> getFilterRemarkOptions() {
        return filterRemarkOptions;
    }

    public void setFilterRemarkOptions(List<String> filterRemarkOptions) {
        this.filterRemarkOptions = filterRemarkOptions;
    }

    /**
     * Creates a new instance of AdmissionsBean
     */
    public AdmissionsBean() {
    }

    public void initConversation() {

        if (conversation.isTransient()) {
            if (this.mrmb.getDialogConversationID() == null) {
                conversation.begin(ADMISSIONSCID);
                this.mrmb.setDialogConversationID(ADMISSIONSCID);
                this.createRemarkFilterOption();
            }
        }
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Date getOutcomeDate() {
        return outcomeDate;
    }

    public void setOutcomeDate(Date outcomeDate) {
        this.outcomeDate = outcomeDate;
    }

    public Date getAdmissionDate() {
        return admissionDate;
    }

    public Admission getSelectedAdmission() {
        return selectedAdmission;
    }

    public void setSelectedAdmission(Admission selectedAdmission) {
        this.selectedAdmission = selectedAdmission;
    }

    public void setAdmissionDate(Date admissionDate) {
        this.admissionDate = admissionDate;
    }

    public Date getAdmissionDate_End() {
        return admissionDate_End;
    }

    public void setAdmissionDate_End(Date admissionDate_End) {
        this.admissionDate_End = admissionDate_End;
    }

    public Date getAdmissionDate_Start() {
        return admissionDate_Start;
    }

    public void setAdmissionDate_Start(Date admissionDate_Start) {
        this.admissionDate_Start = admissionDate_Start;
    }

    public boolean isUseRangeChecked() {
        return useRangeChecked;
    }

    public void setUseRangeChecked(boolean useRangeChecked) {
        this.useRangeChecked = useRangeChecked;
    }

    public void admission_AdmissionUseRangeValueChangeListener(AjaxBehaviorEvent evt) {

        if (this.useRangeChecked) {
            this.fieldSetCollapsed = true;

        } else {
            this.fieldSetCollapsed = false;
        }

    }

    public boolean isFieldSetCollapsed() {
        return fieldSetCollapsed;
    }

    public List<Admission> getAdmissions() {
        return admissions;
    }

    public void setAdmissions(List<Admission> admissions) {
        this.admissions = admissions;
    }

    public AdmissionEJB getAdmsEJB() {
        return admEJB;
    }

    public void setAdmsEJB(AdmissionEJB admsEJB) {
        this.admEJB = admsEJB;
    }

    public ConversationContext getCc() {
        return cc;
    }

    public void setCc(ConversationContext cc) {
        this.cc = cc;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public MedicalRecordManagerBean getMrmb() {
        return mrmb;
    }

    public void setMrmb(MedicalRecordManagerBean mrmb) {
        this.mrmb = mrmb;
    }

    public ResourceLoadingHelperBean getRlhb() {
        return rlhb;
    }

    public void setRlhb(ResourceLoadingHelperBean rlhb) {
        this.rlhb = rlhb;
    }

    public void setFieldSetCollapsed(boolean fieldSetCollapsed) {
        this.fieldSetCollapsed = fieldSetCollapsed;
    }

    public void admissions_AdmissionLoadAdmissions() {

        if (this.fieldSetCollapsed && this.useRangeChecked) {
            admissions = this.admEJB.findAdmissionsByDateRange();
            this.createAdmissionDataTableHeaderMsg(admissionDate_Start, admissionDate_End);
        } else if (!this.fieldSetCollapsed && !this.useRangeChecked) {
            admissions = this.admEJB.findAdmissionsByDate();
            this.createAdmissionDataTableHeaderMsg(admissionDate, null);
            this.fieldSetCollapsed = true;
            this.useRangeChecked = true;
        }
        RequestContext rcon = RequestContext.getCurrentInstance();
        if (admissions == null) {
            rcon.execute("noAdmissions.show()");
        }
    }

    public AdmissionEJB getAdmEJB() {
        return admEJB;
    }

    public void setAdmEJB(AdmissionEJB admEJB) {
        this.admEJB = admEJB;
    }

    public String getAdmissionDataTableHeaderMsg() {
        return admissionDataTableHeaderMsg;
    }

    public void setAdmissionDataTableHeaderMsg(String admissionDataTableHeaderMsg) {
        this.admissionDataTableHeaderMsg = admissionDataTableHeaderMsg;
    }

    private void createAdmissionDataTableHeaderMsg(Date date1, Date date2) {
        FacesContext fcon = FacesContext.getCurrentInstance();
        if (date2 == null) {
            String admsOn = ELResolver.resolve(fcon, "#{mrmp.admsOn}");
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            this.admissionDataTableHeaderMsg = admsOn + " " + df.format(date1);
        } else {
            String admsBetween = ELResolver.resolve(fcon, "#{mrmp.admsBetween}");
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            this.admissionDataTableHeaderMsg = admsBetween + " " + df.format(date1) + " And " + df.format(date2);

        }
    }

    public void admissions_AdmissionsCloseEventListener(CloseEvent evt) {
        Utility.endConversations(ADMISSIONSCID, cc, conversation, mrmb);
    }

    public void discharge_DischargeButtonClickedListener() {
        FacesContext fcon = FacesContext.getCurrentInstance();
        String clientID = fcon.getExternalContext().getRequestParameterMap().get("DBID");
        Discharge discharge =new  Discharge();
        this.registerOutcome(discharge, Constants.DISCHARGED);
        this.buttonDisabled = true;
        fcon.addMessage(clientID, new FacesMessage(FacesMessage.SEVERITY_INFO, ELResolver.resolve(fcon, "#{mrmp.dDischarged}"), ELResolver.resolve(fcon, "#{mrmp.dDischarged}")));
    }

    public void dama_DamaButtonClickedListener() {

        FacesContext fcon = FacesContext.getCurrentInstance();
        String clientID = fcon.getExternalContext().getRequestParameterMap().get("DAMABID");
        DischargeAgainstMedicalAdvice dama = new DischargeAgainstMedicalAdvice();
        this.registerOutcome(dama, clientID);
        this.buttonDisabled = true;
        fcon.addMessage(clientID, new FacesMessage(FacesMessage.SEVERITY_INFO, ELResolver.resolve(fcon, "#{mrmp.dama} #{mrmp.complete}"), ELResolver.resolve(fcon, "#{mrmp.dama} #{mrmp.complete}")));

    }

    public void discharge_DischargeScheduleAppointmentButtonClickedListener() {
        FacesContext fcon = FacesContext.getCurrentInstance();
        String clientID = fcon.getExternalContext().getRequestParameterMap().get("DABID");
        Discharge discharge = new Discharge();
        this.registerOutcome(discharge, Constants.DISCHARGED);
        this.buttonDisabled = true;
        fcon.addMessage(clientID, new FacesMessage(FacesMessage.SEVERITY_INFO, ELResolver.resolve(fcon, "#{mrmp.dDischarged}"), ELResolver.resolve(fcon, "#{mrmp.dDischarged}")));
        if (this.scheduleAppointment()) {
            fcon.addMessage(clientID, new FacesMessage(FacesMessage.SEVERITY_INFO, ELResolver.resolve(fcon, "#{mrmp.appScheduled}"), ELResolver.resolve(fcon, "#{mrmp.appScheduled}")));
        } else {

            fcon.addMessage(clientID, new FacesMessage(FacesMessage.SEVERITY_INFO, ELResolver.resolve(fcon, "#{mrmp.appnotscheduled}"), ELResolver.resolve(fcon, "#{mrmp.appnotscheduled}")));
        }

    }
    
    public void mortality_MortalityRegistrationButtonClickedListener(){
    FacesContext fcon = FacesContext.getCurrentInstance();
        String clientID = fcon.getExternalContext().getRequestParameterMap().get("MRBID");
        Motality mortality =  new Motality();
        mortality.setCertifiedBy(this.mortality_CertifiedBy);
        this.registerOutcome(mortality,Constants.MORTALITY );
        fcon.addMessage(clientID, new FacesMessage(FacesMessage.SEVERITY_INFO, ELResolver.resolve(fcon, "#{mrmp.mortality} #{mrmp.registered}"), ELResolver.resolve(fcon, "#{mrmp.mortality} #{mrmp.registered}")));
     
    }
    //TODO:Confirm if really necessary
    public void dama_DamaCloseButtonListener() {
        this.buttonDisabled = false;
    }

    public void discharge_DischargeCloseButtonListener() {
        this.buttonDisabled = false;
    }

    private void createRemarkFilterOption() {

        this.filterRemarkOptions.add(Constants.SELECT);
        this.filterRemarkOptions.add(Constants.ON_ADMISSION);
        this.filterRemarkOptions.add(Constants.SAMA);
        this.filterRemarkOptions.add(Constants.DEAD);
    }

    public void admission_AdmissionDischargeDialogRequested() {
        RequestContext rcon = RequestContext.getCurrentInstance();
        if(this.admission_AdmissionOutcomeRegistrationRequestHelper(rcon))
        rcon.execute("dischargeDlg.show()");
    }

    public void admission_AdmissionDAMADialogRequested() {
        RequestContext rcon = RequestContext.getCurrentInstance();
        if(this.admission_AdmissionOutcomeRegistrationRequestHelper(rcon))
        rcon.execute("damaDlg.show()");
    }

    public void admission_AdmissionMotalityRegistrationDialogRequested() {
        RequestContext rcon = RequestContext.getCurrentInstance();
        if(this.admission_AdmissionOutcomeRegistrationRequestHelper(rcon))
         rcon.execute("mortalityRegDlg.show()");
    }
  
    private  boolean admission_AdmissionOutcomeRegistrationRequestHelper(RequestContext rcon){
        if (this.admissionForOutcome == null) {
            rcon.execute("noAdmSelectedDlg.show()");
            return false;
        }
        if (!this.admEJB.isOnAdmission(this.admissionForOutcome.getPatient_Adm())) {
            rcon.execute("notOnAdmission.show()");
            return false;
        }
        
        return true ;
    }
    private void registerOutcome(AdmissionOutcome outcome, String outcomeType) {

        Calendar outcomeCalendar = Calendar.getInstance();
        outcomeCalendar.setTime(this.outcomeDate);
         
         
        
        outcome.setOutcomeEventDate(Utility.createStandardizedDate(outcomeCalendar));
        outcome.setOutcomeEventInstanceDate(new Date());
        outcome.setDescription(Constants.DISCHARGED);
        outcome.setAdmission(admissionForOutcome);
        this.admissionForOutcome.setOutCome(outcome);
        this.admissionForOutcome.setRemark(outcomeType);
        this.admEJB.merge(this.admissionForOutcome);
    }

    private boolean scheduleAppointment() {

        Calendar appointmentCalendar = Calendar.getInstance();
        appointmentCalendar.setTime(this.appointmentDate);
        Appointment app = new Appointment();
        app.setRemark(Constants.REMARK_SCHEDULED);
        app.setDepartment(this.admissionForOutcome.getDepartment());
        app.setUnit(this.admissionForOutcome.getUnit());
        app.setDateOfAppointment(Utility.createStandardizedDate(appointmentCalendar));
        app.setPatient(this.admissionForOutcome.getPatient_Adm());
        app.setHospitalCode(this.mrmb.getHospitalCode());
        return appEJB.scheduleAppointment(app);

    }

}
