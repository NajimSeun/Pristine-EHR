package com.mpit.pristine.bean;

import com.mpit.pristine.ejb.AppointmentEJB;
import com.mpit.pristine.model.DepartmentTreeNodeItem;
import com.mpit.pristine.persistence.entity.Appointment;
import com.mpit.pristine.utility.Constants;
import com.mpit.pristine.utility.ELResolver;
import com.mpit.pristine.utility.Utility;
import javax.inject.Named;
import javax.enterprise.context.ConversationScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.Conversation;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.context.RequestContext;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.TreeNode;

/**
 *
 * @author najim
 */
@Named(value = "patientAppointmentBean")
@ConversationScoped
public class PatientAppointmentBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new instance of PatientAppointmentBean
     */
    @Inject
    private MedicalRecordManagerBean mrmb;
    @Inject
    private Conversation conversation;
    @Inject
    private AppointmentEJB appEJB;
    private List<Appointment> previousAppointments = new ArrayList<>();
    private List<Appointment> currentAppointments = new ArrayList<>();  // contains just one appointment
    private static final String PATIENTAPPOINTMENTCID = "PACID";
    private TreeNode selectedDepartmentNode;
    private String selectedDepartment;
    private String selectedUnit;
    private boolean unitNode = false;
    private boolean deptNode = false;
    private Appointment appForEdit;
    private String selectedDepartment_Edit;
    private String selectedUnit_Edit;

    private TreeNode selectedNode_Edit;
    private boolean updateButtonEnabled = false;
    private Date selectedDate_Edit;
    private String selectedClinicSchedule_Edit;

    public String getSelectedClinicSchedule_Edit() {
        return selectedClinicSchedule_Edit;
    }

    public void setSelectedClinicSchedule_Edit(String selectedClinicSchedule_Edit) {
        this.selectedClinicSchedule_Edit = selectedClinicSchedule_Edit;
    }

    public Date getSelectedDate_Edit() {
        return selectedDate_Edit;
    }

    public void setSelectedDate_Edit(Date selectedDate_Edit) {
        this.selectedDate_Edit = selectedDate_Edit;
    }

    public boolean isUpdateButtonEnabled() {
        return updateButtonEnabled;
    }

    public void setUpdateButtonEnabled(boolean updateButtonEnabled) {
        this.updateButtonEnabled = updateButtonEnabled;
    }

    public TreeNode getSelectedNode_Edit() {
        return selectedNode_Edit;
    }

    public void setSelectedNode_Edit(TreeNode selectedNode_Edit) {
        this.selectedNode_Edit = selectedNode_Edit;
    }

    public String getSelectedDepartment_Edit() {
        return selectedDepartment_Edit;
    }

    public void setSelectedDepartment_Edit(String selectedDepartment_Edit) {
        this.selectedDepartment_Edit = selectedDepartment_Edit;
    }

    public String getSelectedUnit_Edit() {
        return selectedUnit_Edit;
    }

    public void setSelectedUnit_Edit(String selectedUnit_Edit) {
        this.selectedUnit_Edit = selectedUnit_Edit;
    }

    public Appointment getAppForEdit() {
        return appForEdit;
    }

    public void setAppForEdit(Appointment appForEdit) {
        this.appForEdit = appForEdit;
    }

    public boolean isUnitNode() {
        return unitNode;
    }

    public void setUnitNode(boolean unitNode) {
        this.unitNode = unitNode;
    }

    public boolean isDeptNode() {
        return deptNode;
    }

    public void setDeptNode(boolean deptNode) {
        this.deptNode = deptNode;
    }

    private TreeNode departmentRoot;

    public TreeNode getDepartmentRoot() {
        return departmentRoot;
    }

    public void setDepartmentRoot(TreeNode departmentRoot) {
        this.departmentRoot = departmentRoot;
    }

    public PatientAppointmentBean() {
    }

    public void init() {
        Utility.initConversation(conversation, mrmb, PATIENTAPPOINTMENTCID);
    }

    public TreeNode getSelectedDepartmentNode() {
        return selectedDepartmentNode;
    }

    public void setSelectedDepartmentNode(TreeNode selectedDepartmentNode) {
        this.selectedDepartmentNode = selectedDepartmentNode;
    }

    public MedicalRecordManagerBean getMrmb() {
        return mrmb;
    }

    public void setMrmb(MedicalRecordManagerBean mrmb) {
        this.mrmb = mrmb;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public AppointmentEJB getAppEJB() {
        return appEJB;
    }

    public void setAppEJB(AppointmentEJB appEJB) {
        this.appEJB = appEJB;
    }

    public List<Appointment> getPreviousAppointments() {
        return previousAppointments;
    }

    public void setPreviousAppointments(List<Appointment> previousAppointments) {
        this.previousAppointments = previousAppointments;
    }

    public List<Appointment> getCurrentAppointments() {
        return currentAppointments;
    }

    public void setCurrentAppointments(List<Appointment> currentAppointments) {
        this.currentAppointments = currentAppointments;
    }

    public String getSelectedDepartment() {
        return selectedDepartment;
    }

    public void setSelectedDepartment(String selectedDepartment) {
        this.selectedDepartment = selectedDepartment;
    }

    public String getSelectedUnit() {
        return selectedUnit;
    }

    public void setSelectedUnit(String selectedUnit) {
        this.selectedUnit = selectedUnit;
    }

    public void patientAppointment_PatientAppointmentDepartmentNodeSelectListener(NodeSelectEvent nse) {
        TreeNode selectedTreeNode = nse.getTreeNode();

        if (selectedTreeNode.getType().equals(Constants.DEPARTMENTTREENODEGENERATOR_DEPT_TYPE)) {
            this.deptNode = true;
            this.unitNode = false;
            DepartmentTreeNodeItem nodeData = (DepartmentTreeNodeItem) selectedTreeNode.getData();
            this.setSelectedDepartment(nodeData.getName());
        } else if (selectedTreeNode.getType().equals(Constants.DEPARTMENTTREENODEGENERATOR_UNIT_TYPE)) {
            this.deptNode = false;
            this.unitNode = true;
            TreeNode departmentNode = selectedTreeNode.getParent();
            DepartmentTreeNodeItem deptNodeItem = (DepartmentTreeNodeItem) departmentNode.getData();
            DepartmentTreeNodeItem unitNodeItem = (DepartmentTreeNodeItem) this.selectedDepartmentNode.getData();
            this.selectedDepartment = deptNodeItem.getName();
            this.selectedUnit = unitNodeItem.getName();
        }
    }

    public void patientAppointment_PatientAppointmentDepartmentNodeSelectListener_Edit(NodeSelectEvent nse) {
        TreeNode selectedNode = nse.getTreeNode();
        FacesContext fcon = FacesContext.getCurrentInstance();

        if (!selectedNode.getType().equals(Constants.DEPARTMENTTREENODEGENERATOR_CLINIC_TYPE)) {
            String msg = ELResolver.resolve(fcon, "#{mrmp.deptselectionerror}");
            fcon.addMessage("patientAppointmentForm:deptSelectButtonPtAppEdit", new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return;
        }

        this.selectedUnit_Edit = ((DepartmentTreeNodeItem) ((selectedNode.getParent()).getData())).getName();
        this.selectedDepartment_Edit = ((DepartmentTreeNodeItem) ((selectedNode.getParent().getParent()).getData())).getName();
        this.selectedClinicSchedule_Edit = ((DepartmentTreeNodeItem) selectedNode.getData()).getSchedule();
        this.updateButtonEnabled = true;

    }

    public void patientAppointment_ShowAppointmentsButtonClickedListener() {
        FacesContext con = FacesContext.getCurrentInstance();
        RequestContext rcon = RequestContext.getCurrentInstance();

        List<Appointment> appointments = null;
        if (this.deptNode) {
            appointments = this.appEJB.getAppointmentDeptBased(this.mrmb.getHospitalCode(), selectedDepartment, this.mrmb.getSelectedPatient().getId().getID());
        } else if (this.unitNode) {
            appointments = this.appEJB.getAppointmentUnitBased(this.mrmb.getHospitalCode(), selectedDepartment, selectedUnit, this.mrmb.getSelectedPatient().getId().getID());
        } else if (!this.unitNode && !this.unitNode) {
            appointments = this.appEJB.findAllPatientAppointment(this.mrmb.getSelectedPatient());
        }

        if (appointments == null) {
            return;
        }
        this.oganiseAppointments(appointments);

    }

    public void patientientAppointments_PatientAppointmentUpdateAppointment() {
        FacesContext fcon = FacesContext.getCurrentInstance();
        RequestContext rcon = RequestContext.getCurrentInstance();
        Calendar appCalendar = Calendar.getInstance();

        appCalendar.setTime(selectedDate_Edit);
        String valueOf = String.valueOf(appCalendar.get(Calendar.DAY_OF_WEEK));

        String dayOfWeek = Constants.DAYS_OF_WEEK_MAP.get(valueOf);
        if (!this.selectedClinicSchedule_Edit.trim().contains(dayOfWeek)) {
            String msg = ELResolver.resolve(fcon, "#{mrmp.appdiscrepancy}");
            fcon.addMessage("patientAppointmentForm:deptSelectButtonPtAppEdit", new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return ;
        }
        this.appForEdit.setUnit(this.selectedUnit_Edit);
        this.appForEdit.setDepartment(this.selectedDepartment_Edit);
        this.appForEdit.setDateOfAppointment(Utility.createStandardizedDate(appCalendar));
        if(!this.appEJB.updateAppointment(appForEdit)){
           rcon.execute("updaeAppErrorDlg.show()");
        }
        else{
            rcon.execute("updaeAppConfirmDlg.show()");
        }
    }

    private void oganiseAppointments(List<Appointment> sortedApp) {
        Calendar today = Calendar.getInstance();

        for (Appointment app : sortedApp) {
            Date tDate = Utility.createStandardizedDate(today);
            Date appDate = app.getDateOfAppointment();
            if (tDate.before(appDate) || tDate.equals(appDate)) {
                this.currentAppointments.add(app);
            } else {
                this.previousAppointments.add(app);
            }
        }
        Collections.sort(this.previousAppointments);
        Collections.sort(this.currentAppointments);
    }
}
