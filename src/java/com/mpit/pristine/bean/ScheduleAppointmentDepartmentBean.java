package com.mpit.pristine.bean;

import com.mpit.pristine.bean.interfaces.DepartmentBeanInterface;
import com.mpit.pristine.model.DepartmentTreeNodeItem;
import com.mpit.pristine.qualifiers.ScheduleAppointment;
import com.mpit.pristine.utility.Constants;
import com.mpit.pristine.utility.Utility;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.model.TreeNode;

/**
 *
 * @author najim
 */
@Named(value = "scheduleAppointmentDeptBean")
@Dependent
@ScheduleAppointment
public class ScheduleAppointmentDepartmentBean extends DepartmentBeanBase implements DepartmentBeanInterface, Serializable {

    /**
     * Creates a new instance of ScheduleAppointmentDepartmentBean
     */
    private static final Logger LOG = Logger.getLogger(ScheduleAppointmentDepartmentBean.class.getName());
    private static final String SCHEDULEAPPDEPTTAB = "scheduleAppDeptTab";
    private static final String SCHEDULEAPPDATETAB = "scheduleAppDateTab";
    private static final String SCHEDULEAPPSUMMARYTAB = "scheduleAppSummaryTab";
    private static final long serialVersionUID = 1L;

    public ScheduleAppointmentDepartmentBean() {
    }

    @PostConstruct
    public void init() {
        this.setCurrentStep(SCHEDULEAPPDEPTTAB);
        
    }

    @Override
    public void department_DateSelectButtonClickedListener() {

        RequestContext rcon = RequestContext.getCurrentInstance();
        if (this.getDepartment_SelectedDate() == null) {
            rcon.execute("noDateSelectedConfirmDlg.show()");
            return;
        }

        Calendar appCalendar = Calendar.getInstance();
        appCalendar.setTime(this.getDepartment_SelectedDate());
        String appDay = Constants.DAYS_OF_WEEK_MAP.get(Integer.toString(appCalendar.get(Calendar.DAY_OF_WEEK)));
        DepartmentTreeNodeItem clinicNodeItem = (DepartmentTreeNodeItem) this.getDepartment_SelectedTreeNode().getData();
        String clinicAppDay = clinicNodeItem.getSchedule();
        if (!clinicAppDay.contains(appDay)) {

            rcon.execute("appDicreConfirmDlg.show()");
            return ;
        }
 
        Calendar today =  Calendar.getInstance();
        long appDuration = Utility.resetCalendar(appCalendar).getTimeInMillis() -  Utility.resetCalendar(today).getTimeInMillis() ;
        this.setAppFromTd(Utility.convertMSToWeekString(appDuration));
        this.setCurrentStep(SCHEDULEAPPSUMMARYTAB );
        super.department_SetDepartmentAndUnit();

    }

    @Override
    public void department_DepartmentSelectButtonClickedListener() {

        RequestContext rcon = RequestContext.getCurrentInstance();
        LOG.log(Level.SEVERE, this.getDepartment_SelectedTreeNode().getType());




        if (this.getDepartment_SelectedTreeNode() == null || (!this.getDepartment_SelectedTreeNode().getType().equals(Constants.DEPARTMENTTREENODEGENERATOR_CLINIC_TYPE))) {
            rcon.execute("wrongDeptConfirmDlg.show()");

        } else {

            this.setCurrentStep(ScheduleAppointmentDepartmentBean.SCHEDULEAPPDATETAB);
        }
    }

    @Override
    public void department_LoadDepartmentTreeRoot(FacesContext context) {
        super.department_LoadDepartmentTreeRoot_Base(context);
    }

     

     

    @Override
    public String getCurrentStep() {
        return this.getCurrentStepBase();
    }

    @Override
    public void setCurrentStep(String step) {
        this.setCurrentStepBase(step);
    }

    @Override
    public void setDepartment_Root(TreeNode department_Root) {
        this.setDepartment_RootBase(department_Root);
    }

    @Override
    public TreeNode getDepartment_Root() {
        return this.getDepartment_RootBase();
    }

    @Override
    public TreeNode getDepartment_SelectedTreeNode() {
        return this.getDepartment_SelectedTreeNodeBase();
    }

    @Override
    public void setDepartment_SelectedTreeNode(TreeNode node) {
        this.setDepartment_SelectedTreeNodeBase(node);
    }

    @Override
    public Date getDepartment_SelectedDate() {
        return this.getDepartment_SelectedDateBase();
    }

    @Override
    public void setDepartment_SelectedDate(Date date) {
        this.setDepartment_SelectedDateBase(date);
    }

    @Override
    public String getDepartment_SelectedDepartment() {
       return this.getDepartment_SelectedDepartmentBase();
    }

    @Override
    public void setDepartment_SelectedDepartment(String selectedDepartment) {
        this.setDepartment_SelectedDepartmentBase(selectedDepartment);
    }

    @Override
    public void setDepartment_SelectedUnit(String selectedUnit) {
           this.setDepartment_SelectedUnitBase(selectedUnit);
    }

    @Override
    public String getDepartment_SelectedUnit() {
       return this.getDepartment_SelectedUnitBase();
    }

    @Override
    public void department_DatePrevButtonClickedListener() {
        this.setCurrentStep(SCHEDULEAPPDEPTTAB);
    }

    @Override
    public String getAppFromTd() {
        return this.getAppFromTdBase() ;
    }

    @Override
    public void setAppFromTd(String appFromTd) {
        this.setAppFromTdBase(appFromTd);
    }

    @Override
    public void department_Reset() {
        this.setCurrentStep(SCHEDULEAPPDEPTTAB);
    }
}
