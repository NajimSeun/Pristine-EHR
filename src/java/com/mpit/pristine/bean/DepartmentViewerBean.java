package com.mpit.pristine.bean;

import javax.inject.Named;
import javax.enterprise.context.ConversationScoped;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author najim
 */
@Named(value = "departmentViewerBean")
@ConversationScoped
public class DepartmentViewerBean implements Serializable {

    /**
     * Creates a new instance of DepartmentViewerBean
     */
    @Inject
    private Conversation conversation;
    @Inject
    private DepartmentBean deptBean;

    public DepartmentViewerBean() {
    }

    @PostConstruct
    public void init() {
        FacesContext con = FacesContext.getCurrentInstance();
        this.deptBean.department_LoadDepartmentTreeRoot(con);
        conversation.begin();
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public DepartmentBean getDeptBean() {
        return deptBean;
    }

    public void setDeptBean(DepartmentBean deptBean) {
        this.deptBean = deptBean;
    }
    
}
