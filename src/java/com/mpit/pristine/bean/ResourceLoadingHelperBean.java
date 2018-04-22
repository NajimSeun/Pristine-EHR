package com.mpit.pristine.bean;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import org.primefaces.model.TreeNode;

/**
 *
 * @author najim
 */
@Named(value = "resourceLoadingHelperBean")
@SessionScoped
public class ResourceLoadingHelperBean implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new instance of ResourceLoadingHelperBean
     */
    public ResourceLoadingHelperBean() {
    }
    
    private TreeNode deptRoot ;
    private boolean deptRootSet = false ;

    public TreeNode getDeptRoot() {
        return deptRoot;
    }

    public void setDeptRoot(TreeNode deptRoot) {
        this.deptRoot = deptRoot;
    }

    public boolean isDeptRootSet() {
        return deptRootSet;
    }

    public void setDeptRootSet(boolean deptRootSet) {
        this.deptRootSet = deptRootSet;
    }
    
    
    
    
}
