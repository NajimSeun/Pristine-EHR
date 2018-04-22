package com.mpit.pristine.model;

import java.util.List;

/**
 *
 * @author najim
 */
public class Departments {
    
    private String Name  = "Departments";
    
    private List<Department> departments ;

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public Departments(List<Department> departments) {
        this.departments = departments;
    }

   

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }
    
    
}
