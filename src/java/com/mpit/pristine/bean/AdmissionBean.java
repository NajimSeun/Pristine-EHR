package com.mpit.pristine.bean;


import com.mpit.pristine.bean.interfaces.DepartmentBeanInterface;
import com.mpit.pristine.ejb.AdmissionEJB;
import com.mpit.pristine.persistence.entity.Admission;
import com.mpit.pristine.utility.Constants;
import com.mpit.pristine.utility.HospitalUtility;
import com.mpit.pristine.utility.Utility;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import javax.inject.Named;

import org.jboss.weld.context.ConversationContext;
import org.jboss.weld.context.http.Http;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.primefaces.model.TreeNode;

/**
 *
 * @author najim
 */
@Named(value = "admissionBean")
@ConversationScoped
public class AdmissionBean  implements Serializable{

    /**
     * Creates a new instance of AdmissionBean
     */
    @Inject
    @com.mpit.pristine.qualifiers.Admission
    private DepartmentBeanInterface deptBean;
    @Inject
    private ResourceLoadingHelperBean rlhb;
    @Http
    @Inject
    private ConversationContext cc;
    @Inject
    MedicalRecordManagerBean mrmb ;
    @Inject
    Conversation conversation ;
    private static final String ADMISSIONCID = "ADMCID" ;
    private List<String> wards  ;
    @Inject
    private AdmissionEJB admEJB ;
    
    public AdmissionBean() {
    }

    private Admission admission ;
    
    
    public void initConversation() {
        if (conversation.isTransient()) {
            if (this.mrmb.getDialogConversationID() == null) {
                conversation.begin(ADMISSIONCID);
                this.mrmb.setDialogConversationID(ADMISSIONCID);
            }
        }
    }
    @PostConstruct
    public void init() {
        FacesContext con = FacesContext.getCurrentInstance();
        if (!rlhb.isDeptRootSet()) {
            deptBean.department_LoadDepartmentTreeRoot(con);
            TreeNode root_node = deptBean.getDepartment_Root() ;
            Utility.removeClinicNodes(root_node);
            this.rlhb.setDeptRoot(root_node);
            this.rlhb.setDeptRootSet(true);
            wards = HospitalUtility.readHospitalWards(this.mrmb.getHospitalName());
            this.addSelect(wards);
        }

    }

    public Admission getAdmission() {
        return admission;
    }

    public void setAdmission(Admission admission) {
        this.admission = admission;
    }

    
    public DepartmentBeanInterface getDeptBean() {
        return deptBean;
    }

    public void setDeptBean(DepartmentBeanInterface deptBean) {
        this.deptBean = deptBean;
    }

    public ConversationContext getCc() {
        return cc;
    }

    public void setCc(ConversationContext cc) {
        this.cc = cc;
    }

    public ResourceLoadingHelperBean getRlhb() {
        return rlhb;
    }

    public void setRlhb(ResourceLoadingHelperBean rlhb) {
        this.rlhb = rlhb;
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

    public List<String> getWards() {
        return wards;
    }

    public void setWards(List<String> wards) {
        this.wards = wards;
    }
    
    public void addSelect(List<String> wards){
       wards.add(0, Constants.SELECT);
    }
    
    public void admission_AdmissionWardDateSelectButtonClickedListener(){
     RequestContext rcon = RequestContext.getCurrentInstance();
        if(this.admission.getWard().equals(Constants.SELECT) || this.admission.getAdmissionDate() == null){
         rcon.execute("noAppropFieldsSelected.show()");
         return ;
        }
        
        this.deptBean.setCurrentStep(AdmissionDepartmentBean.ADMISSIONSUMMARYTAB);
    }
    
    public void admission_AdmissionAdmit(){
    
        
        this.admission.setDepartment(this.deptBean.getDepartment_SelectedDepartment());
        this.admission.setUnit(this.deptBean.getDepartment_SelectedUnit());
        this.admission.setRemark(Constants.ON_ADMISSION);
        this.admission.setPatient_Adm(this.mrmb.getSelectedPatient());
        this.admission.generateId();
        RequestContext rcon = RequestContext.getCurrentInstance() ;
        if(this.admEJB.persist(admission)){
             rcon.execute("admSuccessfulDlg.show()");
        }
        
        else{
           rcon.execute("onAdmissionDlg.show()");
        }
    }
    
    public void admission_AdmissionCloseListener(CloseEvent evt){
     
        Utility.endConversations(ADMISSIONCID, cc, conversation, mrmb);
    }
}
