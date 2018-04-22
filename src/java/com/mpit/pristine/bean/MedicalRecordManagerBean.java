package com.mpit.pristine.bean;

import com.mpit.pristine.ejb.HospitalSessionEJB;
import com.mpit.pristine.ejb.PatientManagerEJB;
import com.mpit.pristine.persistence.entity.Appointment;
import com.mpit.pristine.persistence.entity.Birth;
import com.mpit.pristine.persistence.entity.Patient;
import com.mpit.pristine.persistence.entity.PatientID;
import com.mpit.pristine.qualifiers.SelectedBirth;
import com.mpit.pristine.utility.CharFilter;
import com.mpit.pristine.utility.Constants;
import com.mpit.pristine.utility.DepartmentTreeGenerator;
import com.mpit.pristine.utility.Utility;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.TreeNode;

/**
 *
 * @author najim
 */
//Logout need to be implemented
@Named(value = "medicalRecordManagerBean")
@SessionScoped
public class MedicalRecordManagerBean implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    private PatientManagerEJB patientManagerEJB;
    /**
     * Creates a new instance of MedicalRecordManagerBean
     */
    
    private String HospitalName = null;
    private String HospitalCode = null;
    
    // Parameters from login application
    private String loggedIntoDept ;
    private String loggedIntoUnit ;
    private String loggedInToSessionType ;
    private String loggedIntoProfessionalType ;
    
    private String Emessage = "No Patients Opened";
    private boolean outputPanelContentRendered;
    private static final int MAX_OPENABLE_PATIENT = 30;
    private int numOpenedPatient;
    private List<Patient> openedPatients = null;  //Patients opened by the application
    private List<Patient> openedPatientsAppointment = null;
    private String userID;
    private Patient[] selectedPatients = null;  //remember that this variable now bwlongs to patients table not appointments table ensure to make necessary amends  later 
    private Patient[] appointmentSelectedPatients;
    private Patient selectedPatient;  //Contains the first and only patient in selectedpatiens variable
    private Patient targetSelection = null;
    @Inject
    private PatientRegistrationBean prb;
    private String activeTab = "Patients";
    
     
    private Logger LOG = Logger.getLogger(MedicalRecordManagerBean.class.getName());
    private String dialogConversationID = null;
    private TreeNode departmentRoot;
    private TreeNode departmentTreeNodeNoClinic;
    private String todayDateString ;
    public Patient[] getAppointmentSelectedPatients() {
        return appointmentSelectedPatients;
    }

    public void setAppointmentSelectedPatients(Patient[] appointmentSelectedPatients) {
        this.appointmentSelectedPatients = appointmentSelectedPatients;
    }

    public String getLoggedIntoDept() {
        return loggedIntoDept;
    }

    public String getTodayDateString() {
        return todayDateString;
    }

    public void setTodayDateString(String todayDateString) {
        this.todayDateString = todayDateString;
    }

    
    public void setLoggedIntoDept(String loggedIntoDept) {
        this.loggedIntoDept = loggedIntoDept;
    }

    public String getLoggedIntoUnit() {
        return loggedIntoUnit;
    }

    public void setLoggedIntoUnit(String loggedIntoUnit) {
        this.loggedIntoUnit = loggedIntoUnit;
    }

    public String getLoggedInToSessionType() {
        return loggedInToSessionType;
    }

    public void setLoggedInToSessionType(String loggedInToSessionType) {
        this.loggedInToSessionType = loggedInToSessionType;
    }

    
    
    public String getActiveTab() {
        return activeTab;
    }

    public void setActiveTab(String activeTab) {
        this.activeTab = activeTab;
    }

    public TreeNode getDepartmentRoot() {
        return departmentRoot;
    }

    public void setDepartmentRoot(TreeNode departmentRoot) {
        this.departmentRoot = departmentRoot;
    }

    public TreeNode getDepartmentTreeNodeNoClinic() {
        return departmentTreeNodeNoClinic;
    }

    public void setDepartmentTreeNodeNoClinic(TreeNode departmentTreeNodeNoClinic) {
        this.departmentTreeNodeNoClinic = departmentTreeNodeNoClinic;
    }

    public String getDialogConversationID() {
        return dialogConversationID;
    }

    public List<Patient> getOpenedPatientsAppointment() {
        return openedPatientsAppointment;
    }

    public void setOpenedPatientsAppointment(List<Patient> openedPatientsAppointment) {
        this.openedPatientsAppointment = openedPatientsAppointment;
    }

    public void setDialogConversationID(String dialogConversationID) {
        this.dialogConversationID = dialogConversationID;
    }

    public String getHospitalCode() {
        return HospitalCode;
    }

    public void setHospitalCode(String HospitalCode) {
        this.HospitalCode = HospitalCode;
    }

    public Patient getSelectedPatient() {
        return selectedPatient;
    }

    public Patient getTargetSelection() {
        return targetSelection;
    }

    public void setTargetSelection(Patient targetSelection) {
        this.targetSelection = targetSelection;
    }

    public void setSelectedPatient(Patient selectedPatient) {
        this.selectedPatient = selectedPatient;
    }

    public String getHospitalName() {
        return HospitalName;
    }

    public void setHospitalName(String HospitalName) {
        this.HospitalName = HospitalName;
    }

    public Patient[] getSelectedPatients() {
        return selectedPatients;
    }

    public void setSelectedPatients(Patient[] selectedPatients) {
        this.selectedPatients = selectedPatients;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    
    /**
     * Get the value of openedPatients
     *
     * @return the value of openedPatients
     */
    public List<Patient> getOpenedPatients() {
        return openedPatients;
    }

    /**
     * Set the value of openedPatients
     *
     * @param openedPatients new value of openedPatients
     */
    public void setOpenedPatients(List<Patient> openedPatients) {
        this.openedPatients = openedPatients;
    }

    public boolean isOutputPanelContentRendered() {
        return outputPanelContentRendered;
    }

    public void setOutputPanelContentRendered(boolean outputPanelContentRendered) {
        this.outputPanelContentRendered = outputPanelContentRendered;
    }

    public String getEmessage() {
        return Emessage;
    }

    public void setEmessage(String Emessage) {
        this.Emessage = Emessage;
    }

    public void initDepartmentResources() {
        FacesContext fcon = FacesContext.getCurrentInstance();
        DepartmentTreeGenerator dtg = new DepartmentTreeGenerator();
        this.departmentRoot = dtg.generateTree(fcon, this.HospitalCode);
        this.departmentTreeNodeNoClinic = dtg.generateTree(fcon, this.HospitalCode);
        Utility.removeClinicNodes(departmentTreeNodeNoClinic);

    }

    @PostConstruct
    public void init() {
        FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        //FacesContext context =FacesContext.getCurrentInstance() ;
        //Map<String,Object> session =context.getExternalContext().getSessionMap() ;
        //this.UserName = (String)session.get("useriD");
        //String hospitalCode = (String)session.get("hospitalCode") ;
        // get Hospital name from ejb  
        // the below bariables are needed bcos for some reason i cannot access session information from conversationscoped beans ie:sessionmap .
        // i plan on moving  both variables to this bean permanently for use from various bean .
        //this.HospitalCode = "1234556";
        //this.HospitalName = "PristineHealth Care";
        
        
        FacesContext fcon = FacesContext.getCurrentInstance() ;
        ExternalContext econ = fcon.getExternalContext();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("EEE, MMM d, ''yy") ;
        this.todayDateString = df.format(cal.getTime());
        this.setRequestParameters(econ);
        if(this.loggedInToSessionType.equals(Constants.CLINIC_SESSION_TYPE)){
         this.SessionTag = this.HospitalName + Constants.UNDERSCORE + this.HospitalCode + Constants.UNDERSCORE + this.loggedIntoDept + Constants.UNDERSCORE + this.loggedIntoUnit + Constants.UNDERSCORE + Constants.CLINIC_SESSION_TYPE + Constants.UNDERSCORE + Utility.createStandardizedDate(Calendar.getInstance());
         if(!this.hsEjb.doesClinicSessionExist(SessionTag))
             this.initClinicSession();
         
         this.hsEjb.addToSessionPersonnelMap(this.loggedIntoProfessionalType, this.userID, SessionTag);
         this.clinicAppointment_Appointments =  this.hsEjb.getAppointments(SessionTag);
         this.createOpenStatusFilterOptions();
        }
        
            
    }

    private void setRequestParameters(ExternalContext econ){
        Map<String, String> requestParameterMap = econ.getRequestParameterMap();
        this.userID = requestParameterMap.get(Constants.USER_ID_QUERY) ;
        this.HospitalCode = requestParameterMap.get(Constants.HOSPITAL_CODE_QUERY);
        this.HospitalName = requestParameterMap.get(Constants.HOSPITAL_NAME_QUERY);
        this.loggedIntoDept = requestParameterMap.get(Constants.DEPARTMENT_QUERY);
        this.loggedIntoUnit = requestParameterMap.get(Constants.UNIT_QUERY) ;
        this.loggedInToSessionType = requestParameterMap.get(Constants.SESSION_TYPE_QUERY);
        this.loggedIntoProfessionalType = requestParameterMap.get(Constants.PROFESSIONALTYPE_QUERY);
    }
    private void addToList(List<Patient> list, Patient p) {
        if (list == null) {
            list = new ArrayList<>();
        }

        list.add(p);
    }

    private String evaluateELExpression(FacesContext context, String expre) {

        ELContext elContext = context.getELContext();
        ExpressionFactory ef = context.getApplication().getExpressionFactory();
        ValueExpression valueExpression = ef.createValueExpression(elContext, expre, String.class);
        return (String) valueExpression.getValue(elContext);

    }

    private void listCopy(List<Patient> sourceList, List<Patient> destinationList) {
        destinationList.addAll(destinationList.size() - 1, sourceList);
    }

    public boolean medicalRecordManager_AddPatient(Patient p) {
        if (this.openedPatients != null || !this.openedPatients.isEmpty() || this.openedPatients.size() < MedicalRecordManagerBean.MAX_OPENABLE_PATIENT) {
            this.openedPatients.add(p);
            return true;
        }

        return false;
    }

    //TODo: Confusing Function
    public void patientAppointment_PatientAppointmentsDialogRequestedListener() { // called as listener from menu that show patienty appointments dialog
        RequestContext context = RequestContext.getCurrentInstance();
        if (this.selectedPatients.length == 1) {
            this.selectedPatient = this.selectedPatients[0];
            context.execute("dlgSchAppoint.show()");
        } else {
            context.execute("noDeptSelectedConfirmDlg.show()");
        }
    }

    public void scheduleAppointment_ScheduleAppointmentsDialogRequestedListener() { // called as listener from menu that show schedule appointments dialog
        this.dialogRequestHelper("dlgSchAppoint.show()");
    }

    public void admission_AdmitDialogRequestListener() {

        this.dialogRequestHelper("admitDlg.show()");
    }

    public void patientAppointments_PatientAppointmentsDialogRequestedListener() {
        this.dialogRequestHelper("patientAppointmentDlg.show()");
    }

    private void dialogRequestHelper(String displayDialogScript) {

        RequestContext rcon = RequestContext.getCurrentInstance();
        LOG.log(Level.SEVERE, "Called");
        LOG.log(Level.SEVERE, this.activeTab);
        if (this.activeTab.equals("Patients")) {
            if (this.selectedPatients != null && this.selectedPatients.length == 1) {
                LOG.log(Level.SEVERE, "Not null and One");
                this.selectedPatient = this.selectedPatients[0];
                rcon.execute(displayDialogScript);
            } else {
                rcon.execute("selectSinglePatient.show()");
            }
        } else if (this.activeTab.equals("Appointments")) {
            if (this.appointmentSelectedPatients != null && this.appointmentSelectedPatients.length == 1) {
                this.selectedPatient = this.appointmentSelectedPatients[0];
                rcon.execute(displayDialogScript);
            } else {
                rcon.execute("selectSinglePatient.show()");
            }

        }
    }

    public void mainTabChangedListener(TabChangeEvent evt) {
        this.activeTab = evt.getTab().getTitle();;
    }
    /*
     *
     * OPEN PATIENT VARIABLES AND FUNCTION BEGIN
     * *************************************************************************************
     *
     *
     */
    private List<Patient> openPatient_OpenedPatientList = new ArrayList<>();
    private int openPatient_Num = 0;
    private String openPatient_IDInput = null;
    private boolean openPatient_AddPatientButtonDisabled = true;
    private String openPatient_EmptyMessage = "";

    public boolean isOpenPatient_AddPatientButtonDisabled() {
        return openPatient_AddPatientButtonDisabled;
    }

    public String getOpenPatient_EmptyMessage() {
        return openPatient_EmptyMessage;
    }

    public void setOpenPatient_EmptyMessage(String openPatient_EmptyMessage) {
        this.openPatient_EmptyMessage = openPatient_EmptyMessage;
    }

    public void setOpenPatient_AddPatientButtonDisabled(boolean openPatient_AddPatientButtonDisabled) {
        this.openPatient_AddPatientButtonDisabled = openPatient_AddPatientButtonDisabled;
    }

    public String getOpenPatient_IDInput() {
        return openPatient_IDInput;
    }

    public void setOpenPatient_IDInput(String openPatient_IDInput) {
        this.openPatient_IDInput = openPatient_IDInput;
    }

    public int getOpenPatient_Num() {
        return openPatient_Num;
    }

    public void setOpenPatient_Num(int openPatient_Num) {
        this.openPatient_Num = openPatient_Num;
    }

    public List<Patient> getOpenPatient_OpenedPatientList() {
        return openPatient_OpenedPatientList;
    }

    public void setOpenPatient_OpenedPatientList(List<Patient> openPatient_OpenedPatientList) {
        this.openPatient_OpenedPatientList = openPatient_OpenedPatientList;
    }

    public void openPatient_OpenButtonClickedAjaxListener() {

        LOG.log(Level.SEVERE, "Open Called");
        FacesContext context = FacesContext.getCurrentInstance();
        String filteredInput = CharFilter.filerAlphaNumHyph(openPatient_IDInput);
        if (filteredInput != null) {
            LOG.log(Level.SEVERE, "Input NULL");
            PatientID pid = new PatientID("PristineHealth Care", "1234556", filteredInput); // JUST FOR TEST
            if (!this.patientManagerEJB.doesHospitalNumberExist(pid)) {
                LOG.log(Level.SEVERE, "ID Non Existent");
                String value = this.evaluateELExpression(context, "#{mrmp.invalidID}");
                LOG.log(Level.SEVERE, value);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, value, value));
                return;
            }
            Patient pt = this.patientManagerEJB.openPatient(pid);
            if (this.openPatient_OpenedPatientList.contains(pt)) {
                return;
            }

            if (this.openPatient_Num <= 5) {
                LOG.log(Level.SEVERE, "Patient to be added");
                if (this.openPatient_AddPatientButtonDisabled) {
                    this.openPatient_AddPatientButtonDisabled = false;
                }

                LOG.log(Level.SEVERE, pt.getName());
                this.addToList(openPatient_OpenedPatientList, pt);
            } else {
                String value = this.evaluateELExpression(context, "#{mrmp.openErrorMax}");
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, value, value));
            }
        }
    }

    public void openPatient_AddButtonClickedAjaxListener() {
        if (MAX_OPENABLE_PATIENT - this.numOpenedPatient > 5 && this.openPatient_Num > 0 && this.openPatient_Num <= 5) {

            this.listCopy(openedPatients, this.openPatient_OpenedPatientList);
        }

    }

    public void openPatient_OncloseEventListener(CloseEvent ce) {
        this.openPatient_NormaliseFields();
    }

    private void openPatient_NormaliseFields() {
        this.openPatient_Num = 0;
        this.openPatient_OpenedPatientList = new ArrayList<>();
        this.openPatient_IDInput = "";
        this.openPatient_AddPatientButtonDisabled = true;
        RequestContext.getCurrentInstance().reset("openPatientForm:openPatientPanel");
    }
    
   
    public void saveAndOpenPatient() {
        if (this.openedPatients != null) {
            if (this.openedPatients.size() < MedicalRecordManagerBean.MAX_OPENABLE_PATIENT) {
                this.openedPatients.add(0, this.prb.getPatient());
            }
        } else {
            this.openedPatients = new ArrayList<>();
            this.openedPatients.add(0, this.prb.getPatient());
            this.prb.getPatient().calculateAge();
        }

        this.prb.savePatient();
    }
    // patients apoint remark would be classified as scheduled missed or attended
    // remember to add function for adding patient to attanded category 
    // manage patient that are opened during clinic sessions
    // patient reffrered to a clinic should be placed in the appointment of that clinic  
    //appointment tag to distinguisg between patients on appointment and those not on appointment but opened in a clinic session

    /**
     * Births Variables and functions
     */
    private Birth birthForEdit;

    @Produces
    @SelectedBirth
    public Birth getBirthForEdit() {
        return birthForEdit;
    }

    public void setBirthForEdit(Birth birthForEdit) {
        this.birthForEdit = birthForEdit;
    }

    public void birth_BirthRegistrationDialogRequestedListener() {

        RequestContext rcon = RequestContext.getCurrentInstance();

        if (this.activeTab.equals("Patients")) {
            rcon.execute("patientTabOnly.show()");
            return;
        }
        this.resolveSelectedPatientNotFromAppointment();
        if (this.selectedPatient == null) {
            rcon.execute("selectSinglePatient.show()");
            return;

        }
        if (!this.selectedPatient.getPinfo().getSex().equals(Constants.FEMALE)) {
            rcon.execute("notFemaleErrorDialog.show");
            return;
        }

        rcon.execute("birthRegDlg.show()");

    }

    //UTILITY METHODS //
    
    private void resolveSelectedPatientNotFromAppointment() {

        if (this.selectedPatients == null || this.selectedPatients.length == 1) {
            this.selectedPatient = null;
            return;
        }

        this.selectedPatient = this.selectedPatients[0];

    }

    
    //CLINIC APPOINTMENT//
    
    private String SessionTag ;
    private HospitalSessionEJB hsEjb ;
    private List<Appointment> clinicAppointment_Appointments ; 
    private boolean clinicAppointment_openPatientButtonEnabled ;
    private List<Appointment> clinicAppointment_SelectedAppointments ;
    private List<String> clinicAppointment_FilterOptions ;
    private String clinicAppointment_SelectionMode = Constants.SINGLE_SELECTION ;

    public String getClinicAppointment_SelectionMode() {
        return clinicAppointment_SelectionMode;
    }

    public void setClinicAppointment_SelectionMode(String clinicAppointment_SelectionMode) {
        this.clinicAppointment_SelectionMode = clinicAppointment_SelectionMode;
    }

    public List<Appointment> getClinicAppointment_SelectedAppointments() {
        return clinicAppointment_SelectedAppointments;
    }

    public void setClinicAppointment_SelectedAppointments(List<Appointment> clinicAppointment_SelectedAppointments) {
        this.clinicAppointment_SelectedAppointments = clinicAppointment_SelectedAppointments;
    }

    public boolean isClinicAppointment_openPatientButtonEnabled() {
        return clinicAppointment_openPatientButtonEnabled;
    }

    public void setClinicAppointment_openPatientButtonEnabled(boolean clinicAppointment_openPatientButtonEnabled) {
        this.clinicAppointment_openPatientButtonEnabled = clinicAppointment_openPatientButtonEnabled;
    }
    
    
    
     
    public List<Appointment> getClinicAppointment_Appointments() {
        return clinicAppointment_Appointments;
    }

    public void setClinicAppointment_Appointments(List<Appointment> clinicAppointment_Appointments) {
        this.clinicAppointment_Appointments = clinicAppointment_Appointments;
    }
   
    private void initClinicSession(){
        this.hsEjb.initializeSession(HospitalCode, this.loggedIntoDept, this.loggedIntoUnit, this.loggedInToSessionType, this.SessionTag);
       
    }
    
    

    public List<String> getClinicAppointment_FilterOptions() {
        return clinicAppointment_FilterOptions;
    }

    public void setClinicAppointment_FilterOptions(List<String> clinicAppointment_FilterOptions) {
        this.clinicAppointment_FilterOptions = clinicAppointment_FilterOptions;
    }
    
    
    private void createOpenStatusFilterOptions(){
       
        this.clinicAppointment_FilterOptions = new ArrayList<>();
        this.clinicAppointment_FilterOptions.add(Constants.OPENED_APP_STATUS);
        this.clinicAppointment_FilterOptions.add(Constants.CLOSED_APP_STATUS);
        this.clinicAppointment_FilterOptions.add(Constants.ENQUEUED_APP_STATUS);
        this.clinicAppointment_FilterOptions.add(Constants.ATTENDED_TO_APP_STATUS);
    }
    
    public void clinicAppointment_ClinicAppointmentOpenedButtonListener(){
     this.clinicAppointment_Appointments = this.hsEjb.openedAppointments(SessionTag) ;
     this.clinicAppointment_openPatientButtonEnabled = true;
     this.clinicAppointment_SelectionMode = Constants.SINGLE_SELECTION ; 
    }
    
    public void clinicAppointment_ClinicAppointmentEnQueuedButtonListener(){
     this.clinicAppointment_Appointments = this.hsEjb.enQueuedAppointments(SessionTag) ;
     this.clinicAppointment_openPatientButtonEnabled = true;
     this.clinicAppointment_SelectionMode = Constants.SINGLE_SELECTION ; 
    }
    
    public void clinicAppointment_ClinicAppointmentAttendedButtonListener(){
      this.clinicAppointment_Appointments = this.hsEjb.attendedToAppointments(this.SessionTag) ;
      this.clinicAppointment_openPatientButtonEnabled = true;
      this.clinicAppointment_SelectionMode = Constants.SINGLE_SELECTION ; 
    }
    
    public void clinicAppointment_ClinicAppointmentAllButtonClickedListener(){
      this.clinicAppointment_Appointments = this.hsEjb.getAppointments(SessionTag);
      this.clinicAppointment_openPatientButtonEnabled = false;
      this.clinicAppointment_SelectionMode = Constants.MULTIPLE_SELECTION ; 
    }
    
    public void clinicAppointment_ClinicAppointmentOpenButtonClickedListener(){
       this.hsEjb.openAppointments(this.clinicAppointment_SelectedAppointments, userID, SessionTag);
    }
   
    public void clinicAppointment_clinicAppointmentReloadAppointmen(){
     this.hsEjb.reloadClinicSessionAppointments(SessionTag);
    }
}
