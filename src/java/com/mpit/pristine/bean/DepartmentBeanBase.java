package com.mpit.pristine.bean;

import com.mpit.pristine.model.DepartmentTreeNodeItem;
import com.mpit.pristine.utility.Constants;
import com.mpit.pristine.utility.DepartmentTreeGenerator;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.model.TreeNode;

/**
 *
 * @author najim
 */
public class DepartmentBeanBase {
    
    
    
    private TreeNode department_SelectedTreeNode = null ;
    private TreeNode department_Root;
    private String currentStep;
    private Date department_SelectedDate ;
    private String department_SelectedDepartment;
    private String department_SelectedUnit;
    private String appFromTd ;
    private Logger LOG = Logger.getLogger(DepartmentBeanBase.class.getName());
    public String getDepartment_SelectedDepartmentBase() {
        return department_SelectedDepartment;
    }

    public void setDepartment_SelectedDepartmentBase(String department_SelectedDepartment) {
        this.department_SelectedDepartment = department_SelectedDepartment;
    }

    public String getAppFromTdBase() {
        return appFromTd;
    }

    public void setAppFromTdBase(String appFromTd) {
        this.appFromTd = appFromTd;
    }


    public String getDepartment_SelectedUnitBase() {
        return department_SelectedUnit;
    }

    public void setDepartment_SelectedUnitBase(String department_SelectedUnit) {
        this.department_SelectedUnit = department_SelectedUnit;
    }
    

    
    public Date getDepartment_SelectedDateBase() {
        return department_SelectedDate;
    }

    public void setDepartment_SelectedDateBase(Date department_SelectedDate) {
        this.department_SelectedDate = department_SelectedDate;
    }

     
    

    public String getCurrentStepBase() {
        return currentStep;
    }

    public void setCurrentStepBase(String currentStep) {
        this.currentStep = currentStep;
    }
    
    
    
    
    public TreeNode getDepartment_RootBase() {
        return department_Root;
    }

    public void setDepartment_RootBase(TreeNode department_Root) {
        this.department_Root = department_Root;
    }
    
    
    
    public TreeNode getDepartment_SelectedTreeNodeBase() {
        return department_SelectedTreeNode;
    }

    public void setDepartment_SelectedTreeNodeBase(TreeNode department_SelectedTreeNode) {
        this.department_SelectedTreeNode = department_SelectedTreeNode;
    }
    
    public void department_LoadDepartmentTreeRoot_Base(FacesContext context) {
        

            Map<String, Object> map = context.getExternalContext().getSessionMap();
            String hospitalCode = (String) map.get(Constants.HOSPITALCODE); //TODE: Just Changed
            department_Root = new DepartmentTreeGenerator().generateTree(context ,hospitalCode);
    }
    
    public boolean department_IsSelectedDateAppropriate(){
        RequestContext rcon = RequestContext.getCurrentInstance();
        if (this.getDepartment_SelectedDateBase() == null) {
            rcon.execute("noDateSelectedConfirmDlg.show()");
            return false;
        }

        Calendar appCalendar = Calendar.getInstance();
        appCalendar.setTime(this.getDepartment_SelectedDateBase());
        String appDay = Constants.DAYS_OF_WEEK_MAP.get(Integer.toString(appCalendar.get(Calendar.DAY_OF_WEEK)));
        DepartmentTreeNodeItem clinicNodeItem = (DepartmentTreeNodeItem) this.getDepartment_SelectedTreeNodeBase().getData();
        String clinicAppDay = clinicNodeItem.getSchedule();
        if (!clinicAppDay.contains(appDay)) {

            rcon.execute("appDicreConfirmDlg.show()");
            return false;
        }
        
        return true ;
    }

    public void department_SetDepartmentAndUnit() {
        DepartmentTreeNodeItem unitNodeItem = (DepartmentTreeNodeItem) this.department_SelectedTreeNode.getParent().getData();
        DepartmentTreeNodeItem deptNodeItem = (DepartmentTreeNodeItem) ((this.department_SelectedTreeNode.getParent()).getParent()).getData();
        this.department_SelectedDepartment = deptNodeItem.getName();
        this.department_SelectedUnit = unitNodeItem.getName();
    }
    
}
