package com.mpit.pristine.bean;

import com.mpit.pristine.ejb.PatientManagerEJB;
import com.mpit.pristine.persistence.entity.Patient;
import com.mpit.pristine.persistence.entity.PatientID;
import com.mpit.pristine.utility.CharFilter;
import com.mpit.pristine.utility.Constants;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
 
import javax.inject.Inject;
import org.jboss.weld.context.ConversationContext;
import org.jboss.weld.context.ManagedConversation;
import org.jboss.weld.context.http.Http;
import org.primefaces.context.RequestContext;

/**
 *
 * @author najim
 */
@Named(value = "searchBean")
@ConversationScoped
public class SearchBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<String> search_SearchCriteria;
    private List<String> search_SelectedCriterias;
    private boolean search_SeachByNameChecked = false;
    private boolean search_SeachBySurnameChecked = false;
    private boolean search_SeachByIDChecked = false;
    private String search_Name;
    private String search_Surname;
    private String search_ID;
    private List<Patient> search_FoundMatches = null;
    private Patient search_SelectedPatient;
    private static final String searchSummaryTabID = "searchSummaryTab";
    private static final String searchTabID = "searchTab";
    private static final String searchCriteriaTabID = "searchCriteriaTab";
    private String currentStep;
    @Inject
    private MedicalRecordManagerBean mrmb;
    @Inject
    private PatientManagerEJB pmEJB;
    private static final Logger LOG = Logger.getLogger(SearchBean.class.getName());
    @Inject
    private Conversation conversation;
    private static final String searchDialogConversationID = "SDCID";
    @Http  
    @Inject
    private ConversationContext cc;
    
    private void resetFields() {
        this.currentStep = SearchBean.searchCriteriaTabID;
        this.search_FoundMatches = null;
        this.search_ID = null;
        this.search_Name = null;
        this.search_Surname = null;
        this.search_SeachByIDChecked = false;
        this.search_SeachByNameChecked = false;
        this.search_SeachBySurnameChecked = false;
        this.search_SelectedCriterias = null;
        this.search_SelectedPatient = null;
    }

    public String getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(String currentStep) {
        this.currentStep = currentStep;
    }

    /**
     * Creates a new instance of SearchBean
     */
    public SearchBean() {
    }

    public MedicalRecordManagerBean getMrmb() {
        return mrmb;
    }

    public void setMrmb(MedicalRecordManagerBean mrmb) {
        this.mrmb = mrmb;
    }

    public PatientManagerEJB getPmEJB() {
        return pmEJB;
    }

    public void setPmEJB(PatientManagerEJB pmEJB) {
        this.pmEJB = pmEJB;
    }

    public Patient getSearch_SelectedPatient() {
        return search_SelectedPatient;
    }

    public void setSearch_SelectedPatient(Patient search_SelectedPatient) {
        this.search_SelectedPatient = search_SelectedPatient;
    }

    public String getSearch_ID() {
        return search_ID;
    }

    public void setSearch_ID(String search_ID) {
        this.search_ID = search_ID;
    }

    public String getSearch_Name() {
        return search_Name;
    }

    public void setSearch_Name(String search_Name) {
        this.search_Name = search_Name;
    }

    public String getSearch_Surname() {
        return search_Surname;
    }

    public void setSearch_Surname(String search_Surname) {
        this.search_Surname = search_Surname;
    }

    public boolean isSearch_SeachByIDChecked() {
        return search_SeachByIDChecked;
    }

    public void setSearch_SeachByIDChecked(boolean search_SeachByIDChecked) {
        this.search_SeachByIDChecked = search_SeachByIDChecked;
    }

    public boolean isSearch_SeachByNameChecked() {
        return search_SeachByNameChecked;
    }

    public void setSearch_SeachByNameChecked(boolean search_SeachByNameChecked) {
        this.search_SeachByNameChecked = search_SeachByNameChecked;
    }

    public boolean isSearch_SeachBySurnameChecked() {
        return search_SeachBySurnameChecked;
    }

    public void setSearch_SeachBySurnameChecked(boolean search_SeachBySurnameChecked) {
        this.search_SeachBySurnameChecked = search_SeachBySurnameChecked;
    }

    public List<String> getSearch_SelectedCriterias() {
        return search_SelectedCriterias;
    }

    public void setSearch_SelectedCriterias(List<String> search_SelectedCriterias) {
        this.search_SelectedCriterias = search_SelectedCriterias;
    }

    public void initConversation() {
        if (this.conversation.isTransient()) {
            if (this.mrmb.getDialogConversationID() == null) {
                conversation.begin(SearchBean.searchDialogConversationID);
                this.mrmb.setDialogConversationID(conversation.getId());
            } else {
                conversation.begin();
            }
        }

    }

    @PostConstruct
    public void init() {
        LOG.log(Level.SEVERE, "Search Post Constructor called");
        this.currentStep = SearchBean.searchCriteriaTabID;
        this.search_SearchCriteria = new ArrayList<>();
        this.search_SearchCriteria.add(Constants.Search_BY_ID);
        this.search_SearchCriteria.add(Constants.Search_BY_NAME);
        this.search_SearchCriteria.add(Constants.Search_BY_SURNAME);
    }

    public List<String> getSearch_SearchCriteria() {
        return search_SearchCriteria;
    }

    public void setSearch_SearchCriteria(List<String> search_SearchCriteria) {
        this.search_SearchCriteria = search_SearchCriteria;
    }

    public void search_SearchCriteriasNextButtonRCMDListener() {
        if (conversation.isTransient()) {
            LOG.log(Level.SEVERE, "transient");
        } else {
            LOG.log(Level.SEVERE, "Long Running");
        }
        LOG.log(Level.SEVERE, "Next Called");
        if (this.search_SelectedCriterias != null && (!this.search_SelectedCriterias.isEmpty())) {
            this.search_ActivateCheckedCriterias();
            LOG.log(Level.SEVERE, "Activate Called");
            this.setCurrentStep(SearchBean.searchTabID);
        } else {
            return;

        }

    }

    private void search_ActivateCheckedCriterias() {
        if (this.search_SelectedCriterias.contains(Constants.Search_BY_ID)) {
            LOG.log(Level.SEVERE, "ID");
            this.search_SeachByIDChecked = true;
        }
        if (this.search_SelectedCriterias.contains(Constants.Search_BY_NAME)) {
            LOG.log(Level.SEVERE, "Name");
            this.search_SeachByNameChecked = true;
        }
        if (this.search_SelectedCriterias.contains(Constants.Search_BY_SURNAME)) {
            LOG.log(Level.SEVERE, "Surname");
            this.search_SeachBySurnameChecked = true;
        }

    }

    // For removal
    private void setMDSelectionError(FacesContext con, int level) {

        LOG.log(Level.SEVERE, "Error occured nothing   checked");
        ELContext elCon = con.getELContext();
        javax.el.ELResolver elRes = elCon.getELResolver();
        SelectLevelListener dMDSL = (SelectLevelListener) elRes.getValue(elCon, null, "selectLevelListener");
        dMDSL.setErrorOccured(true);
        if (dMDSL == null) {
            LOG.log(Level.SEVERE, "dmdsl is null");
        }
        if (level != 2) {
            dMDSL.setLevel(level);
        }
    }

    public void search_ShowSearchResults() {
        LOG.log(Level.SEVERE, "Search {0}", conversation.getId());
        FacesContext con = FacesContext.getCurrentInstance();
        RequestContext rcon = RequestContext.getCurrentInstance();
        List<Patient> patients = null;
        if (this.search_SeachByIDChecked) {
            LOG.log(Level.SEVERE, search_ID);
            String id = CharFilter.filerAlphaNumHyph(this.search_ID.trim());

            PatientID pid = new PatientID(this.mrmb.getHospitalName(), this.mrmb.getHospitalCode(), id.trim());

            patients = this.pmEJB.findById(pid);

            this.search_AnalyseSearchResult(patients, con, rcon);
        } else if (this.search_SeachByNameChecked && !this.search_SeachBySurnameChecked) {
            String name = CharFilter.filerAlphaNumHyph(this.search_Name.trim());
            patients = this.pmEJB.findByName(name);
            this.search_AnalyseSearchResult(patients, con, rcon);
        } else if (!this.search_SeachByNameChecked && this.search_SeachBySurnameChecked) {
            String surname = CharFilter.filerAlphaNumHyph(this.search_Surname.trim());
            patients = this.pmEJB.findBySurname(surname);
            this.search_AnalyseSearchResult(patients, con, rcon);
        } else if (this.search_SeachByNameChecked && this.search_SeachBySurnameChecked) {
            String surname = CharFilter.filerAlphaNumHyph(this.search_Surname.trim());
            String name = CharFilter.filerAlphaNumHyph(this.search_Name.trim());
            patients = this.pmEJB.findByNameAndSurname(name, surname);
            this.search_AnalyseSearchResult(patients, con, rcon);
        } else {
            patients = null;
            this.search_AnalyseSearchResult(patients, con, rcon);
        }
    }

    private void search_AnalyseSearchResult(List<Patient> patients, FacesContext fcon, RequestContext rcon) {

        if (patients == null || patients.isEmpty()) {
            rcon.execute("noMatchSearch.show()");
        } else {

            this.search_FoundMatches = new ArrayList<>();
            this.search_FoundMatches.addAll(patients);
        }
    }

    public void search_OpenPatientSearch() {
        if (!this.mrmb.medicalRecordManager_AddPatient(search_SelectedPatient)) {
            RequestContext rcon = RequestContext.getCurrentInstance();
            rcon.execute("cannotOPenPatientDlg.show()");
            return;
        }
    }

    public List<Patient> getSearch_FoundMatches() {
        return search_FoundMatches;
    }

    public void setSearch_FoundMatches(List<Patient> search_FoundMatches) {
        this.search_FoundMatches = search_FoundMatches;
    }

    /*
     * public String flowHandler(FlowEvent evt) {
     *
     * LOG.log(Level.SEVERE, "Flow Called"); if (this.search_SeachByIDChecked) {
     * LOG.log(Level.SEVERE, "ID true"); } if (this.search_SeachByNameChecked) {
     * LOG.log(Level.SEVERE, "Name true"); } if
     * (this.search_SeachBySurnameChecked) { LOG.log(Level.SEVERE, "Surname
     * true"); } if (evt == null) { LOG.log(Level.SEVERE, "Fllow Called With
     * null"); LOG.log(Level.SEVERE, "NUll {0}", conversation.getId()); return
     * ""; } else { LOG.log(Level.SEVERE, "Not NULL {0}", conversation.getId());
     * } if (this.conversation.isTransient()) { LOG.log(Level.SEVERE,
     * "Transient"); } return evt.getNewStep(); }
     */
    public void search_SearchDialogClosedListener() {
        RequestContext rCon = RequestContext.getCurrentInstance();
        this.resetDialog(rCon);
        this.mrmb.setDialogConversationID(null);
        this.endConversations();
    }

    private void resetDialog(RequestContext rCon) {
        rCon.reset("searchForm:searchCriteriaTabPanel");
        rCon.reset("searchForm:searchTabPanel");
        rCon.reset("searchForm:summaryTabPanel");
    }
    /*
     * SearchBean bean
     * =(SearchBean)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("conBean");
     * this.search_FoundMatches = bean.search_FoundMatches ; this.search_ID =
     * bean.search_ID ; this.search_Name = bean.search_Name ;
     * this.search_Surname = bean.search_Surname; this.search_SeachByIDChecked =
     * bean.search_SeachByIDChecked; this.search_SeachByNameChecked =
     * bean.search_SeachByNameChecked ; this.search_SeachBySurnameChecked =
     * bean.search_SeachBySurnameChecked ; this.search_SelectedCriterias =
     * bean.search_SelectedCriterias ; this.search_SelectedPatient =
     * bean.search_SelectedPatient ;
     */

    public void search_SearchPreviousButtonClickedListener() {

        this.setCurrentStep(SearchBean.searchCriteriaTabID);
    }

    public void search_SearchNextButtonClickedListener() {

        this.setCurrentStep(SearchBean.searchSummaryTabID);
    }

    public void search_SearchSummaryResetButtonClickedListener() {
        RequestContext rCon = RequestContext.getCurrentInstance();
        this.resetFields();
        this.resetDialog(rCon);
    }
    

    public void isnull() {
        Collection<ManagedConversation> conversations = cc.getConversations();
        for (Iterator<ManagedConversation> it = conversations.iterator(); it.hasNext();) {
            ManagedConversation mc = it.next();
            LOG.log(Level.SEVERE, mc.getId());
        }
        if (this.search_SelectedPatient == null) {
            LOG.log(Level.SEVERE, "Null i am");
        }
        LOG.log(Level.SEVERE, " cid is {0}  mrmrp cid is {1}", new Object[]{this.conversation.getId(), this.mrmb.getDialogConversationID()});
    }

    private void endConversations() {
        Collection<ManagedConversation> conversations = cc.getConversations();
        for (Iterator<ManagedConversation> it = conversations.iterator(); it.hasNext();) {
            ManagedConversation mc = it.next();
            if (!mc.getId().equals(SearchBean.searchDialogConversationID) && !mc.isTransient()) {
                mc.end();
            }
            this.conversation.end();
        }
    }
}
