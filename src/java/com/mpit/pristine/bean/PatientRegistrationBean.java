package com.mpit.pristine.bean;

import com.mpit.pristine.ejb.HospitalEJB;
import com.mpit.pristine.persistence.entity.Hospital;
import com.mpit.pristine.ejb.PatientManagerEJB;
import com.mpit.pristine.location.Country;
import com.mpit.pristine.location.State;
import com.mpit.pristine.persistence.entity.*;
import com.mpit.pristine.utility.Constants;
import com.mpit.pristine.utility.PatientIDAutogenerator;
import com.mpit.pristine.utility.RegistrationUtility;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.FlowEvent;

/**
 * PatientRegistrationBean represents the model for the PatientRegistration page
 * ptRegistration.xhtml.
 *
 * @author najim
 */
// This bean class will be re written so that all that exist is a patient instance which will make it easy to
// incoporate edit features into it
@Named(value = "patientRegistrationBean")
@SessionScoped
public class PatientRegistrationBean implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    private PatientManagerEJB prb;
    @Inject
    private HospitalEJB hejb;
    private Patient patient;
    private String hospitalCode;
    private String hospitalName;
    private boolean hospitalNumberEntryReadOnly = false;
    private List<String> statesList;
    private List<String> localGovList;
    private List<String> countryList;
    private static final String InitStep = "personalInformationTab";
    private String step = InitStep;
    private boolean dialogHidden = false;
    private boolean dialogShown = false;

    public boolean isDialogHidden() {
        return dialogHidden;
    }

    public void setDialogHidden(boolean dialogHidden) {
        this.dialogHidden = dialogHidden;
    }

    public boolean isDialogShown() {
        return dialogShown;
    }

    public void setDialogShown(boolean dialogShown) {
        this.dialogShown = dialogShown;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    /**
     * Gets the patient instance
     *
     * @return Patient instance
     */
    public Patient getPatient() {
        return patient;
    }

    /**
     * Set the patient instance
     *
     * @param patient Patient instance
     */
    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    private static final String Select = "Select";
    private boolean skip = true;

    /**
     *
     * @return
     */
    public boolean isSkip() {
        return skip;
    }

    /**
     *
     * @param skip
     */
    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    /**
     * Returns a List of strings representing names of local government in a
     * given state
     *
     * @return List of Strings
     */
    public List<String> getLocalGovList() {
        return localGovList;
    }

    /**
     * Set the List containing local government
     *
     * @param localGovList
     */
    public void setLocalGovList(List<String> localGovList) {
        this.localGovList = localGovList;
    }

    /**
     * Returns a List of Strings containing state names
     *
     * @return List of Strings
     */
    public List<String> getStatesList() {
        return statesList;
    }

    /**
     * sets the state list
     *
     * @param statesList
     */
    public void setStatesList(List<String> statesList) {
        this.statesList = statesList;
    }

    /**
     *
     * @return
     */
    public boolean isHospitalNumberEntryReadOnly() {
        return hospitalNumberEntryReadOnly;
    }

    /**
     *
     * @param hospitalNumberEntryReadOnly
     */
    public void setHospitalNumberEntryReadOnly(boolean hospitalNumberEntryReadOnly) {
        this.hospitalNumberEntryReadOnly = hospitalNumberEntryReadOnly;
    }

    /**
     * Returns country List
     *
     * @return List
     */
    public List<String> getCountryList() {
        return countryList;
    }

    /**
     * Set country List
     *
     * @param countryList List
     */
    public void setCountryList(List<String> countryList) {
        this.countryList = countryList;
    }

    /**
     * Returns hospital name
     *
     * @return
     */
    public String getHospitalName() {
        return hospitalName;
    }

    /**
     * Set the value of hospitalName
     *
     * @param hospitalName new value of hospitalName
     */
    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    /**
     * Get the value of hospitalCode
     *
     * @return the value of hospitalCode
     */
    public String getHospitalCode() {
        return hospitalCode;
    }

    /**
     * Set the value of hospitalCode
     *
     * @param hospitalCode new value of hospitalCode
     */
    public void setHospitalCode(String hospitalCode) {
        this.hospitalCode = hospitalCode;
    }

    /**
     * Creates a new instance of PatientRegistratioBean
     */
    public PatientRegistrationBean() {
    }
    private Logger LOG = Logger.getLogger(PatientRegistrationBean.class.getName());

    /**
     * Validates patient's hospitalnumber to ensure uniquesness
     *
     * @param context FacesContext
     * @param component UIComponent
     * @param value Object
     */
    public void validatePatientHospitalNumber(FacesContext context, UIComponent component, Object value) {
        LOG.log(Level.SEVERE, "patient num validator");
        if (!this.hospitalNumberEntryReadOnly) {
            PatientID id = new PatientID(this.hospitalName, this.hospitalCode, this.patient.getId().getID());
            if (prb.doesHospitalNumberExist(id)) {
                String Message = "Hospital Number Exists";
                LOG.log(Level.SEVERE, "Hospital Number exists");
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, Message, null);
                context.addMessage(component.getClientId(), msg); //to be checked
                throw new ValidatorException(msg);
            } else {
                LOG.log(Level.SEVERE, "Hospital Number does not exists");
            }
        }

    }

    /**
     * Autogenerates number ensuring that number is unique to given hospital and
     * a given patient within the hospital . Implementation is a bit complex as
     * it uses reflection to call the appropriate method for each hospital. The
     * name of the appropriate method for a given hospital is appended with the
     * hospital code .
     */
    public void autogenerateListener() {
        String[] args = {this.hospitalName, this.hospitalCode};
        this.hospitalNumberEntryReadOnly = true;
        Class<PatientIDAutogenerator> patientIDAutogenClass = PatientIDAutogenerator.class;
        Method method = null;
        Object autogen = null;
        try {
            autogen = patientIDAutogenClass.newInstance();
            Method[] methods = patientIDAutogenClass.getDeclaredMethods();
            for (Method m : methods) {
                String methodName = m.getName();
                if (methodName.endsWith(this.hospitalCode)) {
                    method = m;
                    break;
                }
            }
        } catch (InstantiationException | IllegalAccessException | SecurityException e) {
        }
        try {
            if (method != null) {
                method.setAccessible(true);
                this.patient.getId().setID(((String) method.invoke(autogen, (Object) args)));
            } else {
                this.patient.getId().setID(PatientIDAutogenerator.defAutogenerator(args));
            }
        } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        }
    }

    /**
     * Returns a list of occupations matching entered characters
     *
     * @param enteredValue
     * @return List
     */
    public List<String> completeOccupation(String enteredValue) {
        this.LOG.log(Level.SEVERE, "In occupation autocomplete");
        List<String> occupations = RegistrationUtility.getOccupations();

        List<String> matches = new ArrayList();
        for (int i = 0; i < occupations.size(); i++) {
            this.LOG.log(Level.SEVERE, occupations.get(i));
            if (((occupations.get(i)).trim()).startsWith(enteredValue)) {
                matches.add((occupations.get(i)).trim());
            }
        }

        return matches;
    }

    /**
     * Invoked when country selection is changed to updatestatesList
     *
     * @param evt AjaxBehaviourEvent
     */
    public void countryChangedAjaxListener(AjaxBehaviorEvent evt) {
        LOG.log(Level.SEVERE, "I am called");
        LOG.log(Level.SEVERE, this.patient.getPinfo().getCountry());
        if (!this.isSelectOptionSelected(this.patient.getPinfo().getCountry())) {
            Country selectedCountry = new Country(this.patient.getPinfo().getCountry());
            this.statesList = selectedCountry.getStates_ProvincesName();
            this.addFirstSelectItem_Two(statesList);
            String s = "Count  is" + statesList.size();
            LOG.log(Level.SEVERE, s);
        } else {
            this.addFirstSelectItem(statesList);
            this.addFirstSelectItem(this.localGovList);
        }
    }

    /**
     * Invoked via ajax when state selection is changed to update local government
     * list
     *
     * @param evt AjaxBehaviourEvent
     */
    public void stateChangedAjaxListener(AjaxBehaviorEvent evt) {
        if (!this.isSelectOptionSelected(this.patient.getPinfo().getStateOfOrigin())) {
            State selectedState = new State(new Country(this.patient.getPinfo().getCountry()), this.patient.getPinfo().getStateOfOrigin());

            this.localGovList = selectedState.getLocalGovernment();
            LOG.log(Level.SEVERE, this.localGovList.toString());
        }
    }

    private void setHospitalInfo() {  //To programmatically add hospitalinfo to session for testing purpose
        // in the real application this will be done at login 

        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext ec = context.getExternalContext();
        Map<String, Object> userSession = ec.getSessionMap();
        this.setHospitalCode(((String) userSession.get(Constants.HOSPITALCODE)));
        Hospital hospital = this.hejb.getHospital(Hospital.class, hospitalCode);
        this.setHospitalName(hospital.getHospitalName());



    }

    /**
     * Adds select option to lists after clearing the list used to reset lists
     */
    private List<String> addFirstSelectItem(List<String> list) {
        if (!list.isEmpty()) {
            list.clear();
        }
        list.add(0, "Select");
        return list;
    }

    /**
     * Adds select option to first location of list
     */
    private List<String> addFirstSelectItem_Two(List<String> list) {

        list.add(0, "Select");
        return list;
    }

    /**
     * Returns true if the value select is the select option
     */
    private boolean isSelectOptionSelected(String selection) {
        LOG.log(Level.SEVERE, this.patient.getPinfo().getCountry());
        if (selection.equalsIgnoreCase("Select")) {
            LOG.log(Level.SEVERE, "True is returned");
            return true;
        }

        LOG.log(Level.SEVERE, "False is returned");
        return false;
    }

    /**
     * Creates dummy hospital for test purpose
     */
    private void createDummyHospital() {
        this.hospitalCode = "1234556";
        this.hospitalName = "PristineHealth Care" ;
        Hospital hos = new Hospital(hospitalCode, hospitalName, "masterplane medical Area Abuja", "08103167855", "pristineHCare@gmail.com", "prishcare.com", "Nigeria", "Abuja", "Abuja Central");
        if (this.hejb == null) {
            this.LOG.log(Level.SEVERE, "I am null");
        }
        this.hejb.persist(Hospital.class, hospitalCode, hos);
    }

    private void addToSession() {

        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        context.getSession(true);
        Map<String, Object> session = context.getSessionMap();
        session.put(Constants.HOSPITALCODE, this.hospitalCode);
        session.put(Constants.HOSPITALNAME,this.hospitalName ) ;
    }

    /**
     * Invoked immediately after object creation
     */
    @PostConstruct
    public void postConstructionInit() {
        FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        this.initializePatient(null);
        this.createDummyHospital();
        this.addToSession();
        //this.setHospitalInfo();
        this.statesList = new ArrayList<>();
        this.localGovList = new ArrayList<>();
        this.addFirstSelectItem(statesList);
        this.addFirstSelectItem(localGovList);
        countryList = RegistrationUtility.getCountries();
        this.addFirstSelectItem_Two(countryList);
        countryList = this.removeWhiteSpace(countryList);

        LOG.log(Level.SEVERE, countryList.toString());
    }

    /**
     * Called during wizard navigation to return next step
     *
     * @param evt
     * @return String next step
     */
    public String onFlowProcess(FlowEvent evt) {
        LOG.log(Level.SEVERE, "Flow listener Called");
        if (this.dialogHidden && !this.dialogShown) {
            return InitStep;
        } else if (!this.dialogHidden && this.dialogShown) {
            return evt.getNewStep();
        }

        return evt.getNewStep();
    }

    /**
     * Removes white space from list entries,became neccessary at some point
     */
    private List<String> removeWhiteSpace(List<String> list) {
        List<String> parsedList = new ArrayList<>();
        for (int x = 0; x < list.size(); x++) {
            String value = list.get(x);
            parsedList.add(x, value);
        }

        return parsedList;

    }

    /**
     * Constructor which takes a Patient instance for use when using the
     * registration bean and view to edit an already existing patient
     * information
     *
     * @param patient
     */
    public PatientRegistrationBean(Patient patient) {
        //
    }

    /**
     * Validate selected maritalstatus
     *
     * @param context
     * @param component
     * @param value
     */
    public void validateMaritalStatus(FacesContext context, UIComponent component, Object value) {
        String ouput = (String) value;
        LOG.log(Level.SEVERE, ouput);
        if (((String) value).equalsIgnoreCase("Select")) {
            LOG.log(Level.SEVERE, "EQUALS Select");
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "", ""));
        }
    }

    /**
     * Validate selected country
     *
     * @param context
     * @param component
     * @param value
     */
    public void validateCountrySelection(FacesContext context, UIComponent component, Object value) {
        if (((String) value).equalsIgnoreCase(PatientRegistrationBean.Select)) {
            this.addFirstSelectItem(statesList);
            this.addFirstSelectItem(this.localGovList);
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "", ""));
        }
    }

    /**
     * Validate selected state
     *
     * @param context
     * @param component
     * @param value
     */
    public void validateStateSelection(FacesContext context, UIComponent component, Object value) {
        if (((String) value).equalsIgnoreCase(PatientRegistrationBean.Select)) {

            this.addFirstSelectItem(this.localGovList);
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "", ""));
        }
    }

    /**
     * Validate Selected local government
     *
     * @param context
     * @param component
     * @param value
     */
    public void validateLocGovSelection(FacesContext context, UIComponent component, Object value) {
        if (((String) value).equalsIgnoreCase(PatientRegistrationBean.Select)) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "", ""));
        }
    }

    public void validateEthicitySelection(FacesContext context, UIComponent component, Object value) {

        if (((String) value).equalsIgnoreCase(PatientRegistrationBean.Select)) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "", ""));
        }
    }

    /**
     * Validate selected occupation
     *
     * @param context
     * @param component
     * @param value
     */
    public void validateOccupationEntry(FacesContext context, UIComponent component, Object value) {
        String val = (String) value;
        if (val.trim().equalsIgnoreCase("")) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "", ""));
        }
    }

    public void savePatient() {
        this.patient.getId().setHospitalName(hospitalName);
        this.patient.getId().setHospitalCode(hospitalCode);
        this.prb.persistPatient(patient);
        this.resetPatient();
        this.reset();
    }

    public void patientRegistrationDialogCloseListener(CloseEvent ce) {
        LOG.log(Level.SEVERE, "Close Event");
        this.patient.getPinfo().setMaritalStatus(null);
        this.dialogHidden = true;
        this.dialogShown = false;
        this.resetPatient();
        this.reset();
    }

    public void dialogOnShowRCMDListener() {
        this.dialogHidden = false;
        this.dialogShown = true;

    }

    public void maritalStatusChangedListener(AjaxBehaviorEvent evt) {
        this.dialogHidden = false;
        this.dialogShown = true;
    }

    /**
     * Reset the patient registration wizard
     */
    private void reset() {
        this.addFirstSelectItem(this.localGovList) ;  //Work aroun for persistent local government field after clossing then re opening patient registration Dialog
        RequestContext.getCurrentInstance().reset("ptRegForm:personalInformationPanel");
        RequestContext.getCurrentInstance().reset("ptRegForm:contactInfoPanel");
        RequestContext.getCurrentInstance().reset("ptRegForm:confirmPanel");

    }

    /**
     * Initialize the patient field with passes in patient object or initialize
     * a new patient if null
     *
     * @param p
     */
    public void initializePatient(Patient p) {
        if (p != null) {
            this.patient = p;
            return;
        }
        this.patient = initializePatientHelper() ;
        
    }
    /**
     * Reset patient instance
     */
    private void resetPatient(){
        this.patient = initializePatientHelper();
    }
    private Patient initializePatientHelper() {
        Patient p = new Patient();
        p.setId(new PatientID());
        p.setPinfo(new PersonalInformation());
        p.setCinfo(new ContactInformation());
        return p;
    }
}
