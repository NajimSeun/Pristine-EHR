package com.mpit.pristine.bean;

import com.mpit.pristine.bean.interfaces.DepartmentBeanInterface;
import com.mpit.pristine.qualifiers.Admission;
import com.mpit.pristine.utility.Constants;
import java.io.Serializable;
import java.util.Date;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.model.TreeNode;

/**
 *
 * @author najim
 */
@Named(value = "admissionDepartmentBean")
@Dependent
@Admission
public class AdmissionDepartmentBean extends DepartmentBeanBase implements DepartmentBeanInterface , Serializable{
    private static final long serialVersionUID = 1L;

    public static final String ADMISSIONDEPTTAB = "admissionDeptTab" ;
    public static final String ADMISSIONDATEWARDTAB = "admissionDateWardTab" ;
    public static final String ADMISSIONSUMMARYTAB = "admissionSummaryTab" ;
    /**
     * Creates a new instance of AdmissionDepartmentBean
     */
    public AdmissionDepartmentBean() {
        this.setCurrentStepBase(ADMISSIONDEPTTAB);
    }

    @Override
    public void department_DepartmentSelectButtonClickedListener() {
        RequestContext context  = RequestContext.getCurrentInstance();
        String selectedNodeType = this.getDepartment_SelectedTreeNode().getType() ;
         if(!selectedNodeType.equals(Constants.DEPARTMENTTREENODEGENERATOR_UNIT_TYPE)){
          context.execute("wrongDeptConfirmDlg.show()");
        }
         else{
             this.department_SetDepartmentAndUnit();
            this.setCurrentStep(ADMISSIONDATEWARDTAB);
         }
    }

    @Override
    public void department_DateSelectButtonClickedListener() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void department_LoadDepartmentTreeRoot(FacesContext context) {
        this.department_LoadDepartmentTreeRoot_Base(context);
    }

    @Override
    public void department_DatePrevButtonClickedListener() {
        this.setCurrentStepBase(ADMISSIONDEPTTAB);
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setDepartment_SelectedDate(Date date) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setDepartment_SelectedDepartment(String selectedDepartment) {
        this.setDepartment_SelectedDepartmentBase(selectedDepartment);
    }

    @Override
    public void setDepartment_SelectedUnit(String selectedUnit) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getDepartment_SelectedDepartment() {
        return this.getDepartment_SelectedDepartmentBase() ;
    }

    @Override
    public String getDepartment_SelectedUnit() {
        throw new UnsupportedOperationException("Not supported yet.");
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
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
