package com.mpit.pristine.bean;

import com.mpit.pristine.bean.interfaces.DepartmentBeanInterface;
import com.mpit.pristine.qualifiers.AppointmentViewer;
import com.mpit.pristine.utility.Constants;
 
import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.model.TreeNode;

/**
 *
 * @author najim
 */
@Named(value = "appointmentViewerDepartmentBean")
@Dependent
@AppointmentViewer
public class AppointmentViewerDepartmentBean extends DepartmentBeanBase implements DepartmentBeanInterface, Serializable {

    private final static String APPOINTMENTVIEWERDEPTTAB = "appViewerDeptTab";
    private final static String APPOINTMENTVIEWERDATETAB = "appViewerDateTab";
    private final static String APPOINTMENTVIEWERSUMMARYTAB = "appViewerSummaryTab";
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(AppointmentViewerDepartmentBean.class.getName());

    /**
     * Creates a new instance of AppointmentViewerDepartmentBean
     */
    public AppointmentViewerDepartmentBean() {
        this.setCurrentStepBase(APPOINTMENTVIEWERDEPTTAB);
    }

    @Override
    public void department_DepartmentSelectButtonClickedListener() {
        RequestContext rcon = RequestContext.getCurrentInstance();
        LOG.log(Level.SEVERE, this.getDepartment_SelectedTreeNode().getType());

        LOG.log(Level.SEVERE, "App View Department select button clicked listener");



        if (this.getDepartment_SelectedTreeNode() == null || (!this.getDepartment_SelectedTreeNode().getType().equals(Constants.DEPARTMENTTREENODEGENERATOR_CLINIC_TYPE))) {
            rcon.execute("wrongDeptConfirmDlg.show()");

        } else {

            this.setCurrentStep(AppointmentViewerDepartmentBean.APPOINTMENTVIEWERDATETAB);
        }
    }

    @Override
    public void department_DateSelectButtonClickedListener() {
        if (!this.department_IsSelectedDateAppropriate()) {
            return;
        }
        this.setCurrentStepBase(APPOINTMENTVIEWERSUMMARYTAB);
        this.department_SetDepartmentAndUnit();
    }

    @Override
    public void department_LoadDepartmentTreeRoot(FacesContext context) {
        this.department_LoadDepartmentTreeRoot_Base(context);
    }

    @Override
    public void department_DatePrevButtonClickedListener() {
        this.setCurrentStepBase(APPOINTMENTVIEWERDEPTTAB);
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
    public void setDepartment_SelectedDepartment(String selectedDepartment) {
        this.setDepartment_SelectedDepartmentBase(selectedDepartment);
    }

    @Override
    public void setDepartment_SelectedUnit(String selectedUnit) {
        this.setDepartment_SelectedUnitBase(selectedUnit);
    }

    @Override
    public String getDepartment_SelectedDepartment() {
         return  this.getDepartment_SelectedDepartmentBase();
    }

    @Override
    public String getDepartment_SelectedUnit() {
        return this.getDepartment_SelectedUnitBase();
    }

    @Override
    public String getAppFromTd() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setAppFromTd(String appFromTd) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void department_Reset() {
        this.setCurrentStepBase(APPOINTMENTVIEWERDEPTTAB);
    }
}
