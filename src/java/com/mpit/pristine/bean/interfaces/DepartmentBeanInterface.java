package com.mpit.pristine.bean.interfaces;

import java.util.Date;
import javax.faces.context.FacesContext;
import org.primefaces.model.TreeNode;

/**
 *
 * @author najim
 */
public interface DepartmentBeanInterface {

    public void department_DepartmentSelectButtonClickedListener();

    public void department_DateSelectButtonClickedListener();

    public void department_LoadDepartmentTreeRoot(FacesContext context);
    
    public void department_DatePrevButtonClickedListener();
 
    public String getCurrentStep();

    public void setCurrentStep(String step);

    public void setDepartment_Root(TreeNode department_Root);

    public TreeNode getDepartment_Root();

    public TreeNode getDepartment_SelectedTreeNode();

    public void setDepartment_SelectedTreeNode(TreeNode node);
    
    public Date getDepartment_SelectedDate();
    
    public void setDepartment_SelectedDate(Date date);
    
    public void  setDepartment_SelectedDepartment(String selectedDepartment);
    
    public void  setDepartment_SelectedUnit(String selectedUnit);
    
    public String getDepartment_SelectedDepartment();
    
    public String getDepartment_SelectedUnit();
    
    public String getAppFromTd();
    
    public void setAppFromTd(String appFromTd);
    
    public void department_Reset();
}
