
package com.mpit.pristine.utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mpit.pristine.json.JsonResourceReader;
import com.mpit.pristine.model.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author najim
 */
public class DepartmentTreeGenerator {
    
    private TreeNode  root ;
    private JsonResourceReader JsonResxReader;
    private static final String HYPHEN = "-" ;
    private Logger LOG  = Logger.getLogger(DepartmentTreeGenerator.class.getName());
    
    /**
     * Get the value of JsonResxReader
     *
     * @return the value of JsonResxReader
     */
    public JsonResourceReader getJsonResxReader() {
        return JsonResxReader;
    }

    /**
     * Set the value of JsonResxReader
     *
     * @param JsonResxReader new value of JsonResxReader
     */
    public void setJsonResxReader(JsonResourceReader JsonResxReader) {
        this.JsonResxReader = JsonResxReader;
    }

   

    

    public DepartmentTreeGenerator() {
        this.root =  new   DefaultTreeNode(Constants.DEPARTMENTTREENODEGENERATOR_ROOT_TYPE , "Root" , null);
        this.JsonResxReader =  new JsonResourceReader();
        
    }
    
    public  TreeNode  generateTree(FacesContext context ,String hospitalCode)   {
    
        if(hospitalCode == null) {
            LOG.log(Level.SEVERE, "logged it is null");
        }
        else if("".equals(hospitalCode)) {
            LOG.log(Level.SEVERE, "logged it is empty");
        }
        BufferedReader reader = JsonResxReader.getHospitalDeptConfigReader(context ,hospitalCode);
        
        Gson gs = new GsonBuilder().setPrettyPrinting().create();
        Departments depts = gs.fromJson(reader, Departments.class);
        LOG.log(Level.SEVERE, depts.getDepartments().toString());
        return this.generateDepartmentNode(depts) ;
    }
    
    private  TreeNode  generateDepartmentNode(Departments departments){
    
        List<Department> dept = departments.getDepartments() ;
        for (Iterator<Department> it = dept.iterator(); it.hasNext();) {
            Department d = it.next();
            LOG.log(Level.SEVERE, d.getName());
            DepartmentTreeNodeItem item  = new DepartmentTreeNodeItem(d.getName(),DepartmentTreeGenerator.HYPHEN , DepartmentTreeGenerator.HYPHEN);
            TreeNode node = new DefaultTreeNode(Constants.DEPARTMENTTREENODEGENERATOR_DEPT_TYPE, item , root  );
            this.createUnitNode(node, d);
        }
        
        return root ;
    }
    
    private  void  createUnitNode(TreeNode parentNode , Department parentDept ){
    
        List<Unit> units = parentDept.getUnits() ;
        for (Iterator<Unit> it = units.iterator(); it.hasNext();) {
            Unit u = it.next();
            LOG.log(Level.SEVERE, u.getName());
            DepartmentTreeNodeItem item  = new DepartmentTreeNodeItem(u.getName(),DepartmentTreeGenerator.HYPHEN , u.getHeadOfUnit());
            TreeNode node = new DefaultTreeNode(Constants.DEPARTMENTTREENODEGENERATOR_UNIT_TYPE, item , parentNode );
            this.createClinicNode(node, u);
        }   
         
    }
    
    private void createClinicNode(TreeNode parentNode , Unit parentUnit){
    
       List<Clinic> clinics  = parentUnit.getClinics();
        for (Iterator<Clinic> it = clinics.iterator(); it.hasNext();) {
            Clinic c = it.next();
            LOG.log(Level.SEVERE, c.getDayOfWeek());
            DepartmentTreeNodeItem  item = new DepartmentTreeNodeItem(DepartmentTreeGenerator.HYPHEN , (c.getDayOfWeek() + " " + c.getStart()) , DepartmentTreeGenerator.HYPHEN) ;
            TreeNode node = new DefaultTreeNode(Constants.DEPARTMENTTREENODEGENERATOR_CLINIC_TYPE, item , parentNode );
        }
         
    }
}
