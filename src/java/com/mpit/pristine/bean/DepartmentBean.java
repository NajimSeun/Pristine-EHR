package com.mpit.pristine.bean;

import com.mpit.pristine.model.DepartmentTreeNodeItem;
import com.mpit.pristine.utility.Constants;
import com.mpit.pristine.utility.DepartmentTreeGenerator;
import com.mpit.pristine.utility.ELResolver;
import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELContext;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.model.TreeNode;

/**
 *
 * @author najim
 */
@Named(value = "departmentBean")
@Dependent
public class DepartmentBean implements Serializable {

    /**
     *
     *
     *
     * DEPARTMENT VARIABLES AND FUNCTIONS
     * ************************************************************************************8
     *
     *
     *
     *
     */
    private TreeNode department_SelectedTreeNode = null;
    private TreeNode department_Root;
    private Date schedule_selectedDate;
    private String department_SelectedDepartment;
    private String department_SelectedUnit;
    private String summary;
    private String currentStep;
    private Date appView_SelectedDate;
    private static final String SCHEDULEAPPDEPTTAB = "scheduleAppDeptTab";
    private static final String SCHEDULEAPPDATETAB = "scheduleAppDateTab";
    private static final String SCHEDULEAPPSUMMARYTAB = "scheduleAppSummaryTab";
    private static final Logger LOG = Logger.getLogger(DepartmentBean.class.getName());
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new instance of DepartmentBean
     */
    public DepartmentBean() {
    }

    public String getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(String currentStep) {
        this.currentStep = currentStep;
    }

    public Date getAppView_SelectedDate() {
        return appView_SelectedDate;
    }

    public void setAppView_SelectedDate(Date appView_SelectedDate) {
        this.appView_SelectedDate = appView_SelectedDate;
    }

    public String getDepartment_SelectedDepartment() {
        return department_SelectedDepartment;
    }

    public void setDepartment_SelectedDepartment(String department_SelectedDepartment) {
        this.department_SelectedDepartment = department_SelectedDepartment;
    }

    public String getDepartment_SelectedUnit() {
        return department_SelectedUnit;
    }

    public void setDepartment_SelectedUnit(String department_SelectedUnit) {
        this.department_SelectedUnit = department_SelectedUnit;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String Summary) {
        this.summary = Summary;
    }

    public Date getSchedule_selectedDate() {
        return schedule_selectedDate;
    }

    public void setSchedule_selectedDate(Date schedule_selectedDate) {
        this.schedule_selectedDate = schedule_selectedDate;
    }

    public TreeNode getDepartment_SelectedTreeNode() {
        return department_SelectedTreeNode;
    }

    public void setDepartment_SelectedTreeNode(TreeNode department_SelectedTreeNode) {
        this.department_SelectedTreeNode = department_SelectedTreeNode;
    }

    public void department_LoadDepartmentTreeRoot(FacesContext context) {
        

            Map<String, Object> map = context.getExternalContext().getSessionMap();
            String hospitalCode = (String) map.get(Constants.HOSPITALCODE);
            department_Root = new DepartmentTreeGenerator().generateTree(context,hospitalCode);

         
            this.department_Root = null;
             
         

    }

    public TreeNode getDepartment_Root() {
        return department_Root;
    }

    public void setDepartment_Root(TreeNode department_Root) {
        this.department_Root = department_Root;
    }

    public void department_DepartmentSelectListener() {
        FacesContext con = FacesContext.getCurrentInstance();
        RequestContext rcon = RequestContext.getCurrentInstance();
        LOG.log(Level.SEVERE, this.department_SelectedTreeNode.getType());




        if (this.department_SelectedTreeNode == null || (!this.department_SelectedTreeNode.getType().equals(Constants.DEPARTMENTTREENODEGENERATOR_CLINIC_TYPE))) {
            rcon.execute("wrongDeptConfirmDlg.show()");

        } else {

            this.currentStep = DepartmentBean.SCHEDULEAPPDATETAB;
        }


    }

    public void scheduleAppointment_ScheduleAppointmentDateSelectButtonClicked() {
        RequestContext rcon = RequestContext.getCurrentInstance();
        FacesContext fcon = FacesContext.getCurrentInstance();
        if (this.schedule_selectedDate == null) {
            rcon.execute("noDateSelectedConfirmDlg.show()");
            this.setMDSelectionError(fcon, 3);
            return;
        }

        Calendar appCalendar = Calendar.getInstance();
        appCalendar.setTime(this.schedule_selectedDate);
        String appDay = Constants.DAYS_OF_WEEK_MAP.get(Integer.toString(appCalendar.get(Calendar.DAY_OF_WEEK)));
        DepartmentTreeNodeItem clinicNodeItem = (DepartmentTreeNodeItem) this.department_SelectedTreeNode.getData();
        String clinicAppDay = clinicNodeItem.getSchedule();
        if (!clinicAppDay.contains(appDay)) {
            this.setMDSelectionError(fcon, 3);
            rcon.execute("appDicreConfirmDlg.show()");
        }

        this.department_SetDepartmentAndUnit();
    }

    private void department_SetDepartmentAndUnit() {
        DepartmentTreeNodeItem unitNodeItem = (DepartmentTreeNodeItem) this.department_SelectedTreeNode.getParent().getData();
        DepartmentTreeNodeItem deptNodeItem = (DepartmentTreeNodeItem) ((this.department_SelectedTreeNode.getParent()).getParent()).getData();
        this.department_SelectedDepartment = deptNodeItem.getName();
        this.department_SelectedUnit = unitNodeItem.getName();
    }

    public void appointmentViewer_AppointmentViewerDateSelectButtonClicked() {

        RequestContext rcon = RequestContext.getCurrentInstance();
        FacesContext fcon = FacesContext.getCurrentInstance();
        if (this.appView_SelectedDate == null) {
            rcon.execute("noDateSelectedConfirmDlg.show()");
            this.setMDSelectionError(fcon, 3);
            return;
        }

        this.department_SetDepartmentAndUnit();
    }

    public void department_ClearSelection() {
        this.department_SelectedTreeNode = null;
    }

    public void patientAppointment_PatientAppNextButtonClickedListener() {
        TreeNode tNode = this.getDepartment_SelectedTreeNode();
        FacesContext con = FacesContext.getCurrentInstance();
        ELContext elCon = con.getELContext();
        javax.el.ELResolver elRes = elCon.getELResolver();
        SelectLevelListener dMDSL;
        RequestContext rcon = RequestContext.getCurrentInstance();
        if (tNode != null) {
            String nodeType = tNode.getType();
            switch (nodeType) {
                case Constants.DEPARTMENTTREENODEGENERATOR_DEPT_TYPE: {
                    DepartmentTreeNodeItem deptItem = (DepartmentTreeNodeItem) tNode.getData();
                    this.department_SelectedDepartment = deptItem.getName();
                    this.department_SelectedUnit = Constants.SPACE;
                    break;
                }
                case Constants.DEPARTMENTTREENODEGENERATOR_UNIT_TYPE: {
                    DepartmentTreeNodeItem deptItem = (DepartmentTreeNodeItem) tNode.getParent().getData();
                    DepartmentTreeNodeItem unitItem = (DepartmentTreeNodeItem) tNode.getData();
                    this.department_SelectedDepartment = deptItem.getName();
                    this.department_SelectedUnit = unitItem.getName();
                    break;
                }
                case Constants.DEPARTMENTTREENODEGENERATOR_CLINIC_TYPE: {
                    this.setMDSelectionError(con, 2);   // i changed this
                    String msg = ELResolver.resolve(con, "#{wrongDeptNodeSelection}");
                    String id = con.getExternalContext().getRequestParameterMap().get("nextButtonPtApp");
                    con.addMessage(id, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
                }
            }

        } else {
            this.setMDSelectionError(con, 2);
            rcon.execute("noDeptSelectedConfirmDlg.show()");  // i changed this
        }


    }

    private void setMDSelectionError(FacesContext con, int level) {
        ELContext elCon = con.getELContext();
        javax.el.ELResolver elRes = elCon.getELResolver();
        SelectLevelListener dMDSL = (SelectLevelListener) elRes.getValue(elCon, null, "selectLevelListener");
        dMDSL.setErrorOccured(true);
        if (level != 2) {
            dMDSL.setLevel(level);
        }
    }
}