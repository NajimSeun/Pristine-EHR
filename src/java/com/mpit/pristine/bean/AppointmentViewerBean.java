package com.mpit.pristine.bean;

import com.mpit.pristine.bean.interfaces.DepartmentBeanInterface;
import com.mpit.pristine.ejb.AppointmentEJB;
import com.mpit.pristine.persistence.entity.Appointment;
import com.mpit.pristine.persistence.entity.Patient;
import com.mpit.pristine.qualifiers.AppointmentViewer;
import com.mpit.pristine.utility.Utility;
import java.io.Serializable;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.jboss.weld.context.ConversationContext;
import org.jboss.weld.context.http.Http;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author najim
 */
@Named(value = "appointmentViewerBean")
@ConversationScoped
public class AppointmentViewerBean implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new instance of AppointmentViewerBean
     */
    public AppointmentViewerBean() {
    }
    @Inject
    private AppointmentEJB appEJB;
    @Inject
    private MedicalRecordManagerBean mrmb;    
    @Inject
    @AppointmentViewer
    private DepartmentBeanInterface deptBean;
    private List<Appointment> appointments;
    @Inject
    private Conversation conversation;
    private final String APPVIEWERCONVERSATIONID = "APPVIEWCID";
    @Inject
    private ResourceLoadingHelperBean rlhb;    
    @Http
    @Inject
    private ConversationContext cc;

    public void initConversation() {
        if (conversation.isTransient()) {
            if (this.mrmb.getDialogConversationID() == null) {
                conversation.begin(APPVIEWERCONVERSATIONID);
                this.mrmb.setDialogConversationID(APPVIEWERCONVERSATIONID);
            }
        }
    }
    
    @PostConstruct
    public void init() {
        FacesContext con = FacesContext.getCurrentInstance();
        if (!rlhb.isDeptRootSet()) {
            deptBean.department_LoadDepartmentTreeRoot(con);
            this.rlhb.setDeptRoot(deptBean.getDepartment_Root());
            this.rlhb.setDeptRootSet(true);
        }
        
    }
    
    public ResourceLoadingHelperBean getRlhb() {
        return rlhb;
    }
    
    public void setRlhb(ResourceLoadingHelperBean rlhb) {
        this.rlhb = rlhb;
    }
    
    public List<Appointment> getAppointments() {
        return appointments;
    }
    
    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
    
    public DepartmentBeanInterface getDeptBean() {
        return deptBean;
    }
    
    public void setDeptBean(DepartmentBeanInterface deptBean) {
        this.deptBean = deptBean;
    }
    
    public void appointmentViewer_AppointmentViewShowAppButtonClickedListener() {
        
        Calendar unStandardizedCal = Calendar.getInstance();
        unStandardizedCal.setTime(this.deptBean.getDepartment_SelectedDate());//this.deptBean.getAppView_SelectedDate()
        Date date = Utility.createStandardizedDate(unStandardizedCal);
        FacesContext con = FacesContext.getCurrentInstance();
        RequestContext rcon = RequestContext.getCurrentInstance();
        Map<String, Object> map = con.getExternalContext().getSessionMap();
        List<Appointment> Appointments = this.appEJB.getAppointments(this.mrmb.getHospitalCode(), this.deptBean.getDepartment_SelectedDepartment().trim(),
                this.deptBean.getDepartment_SelectedUnit().trim(), date);
        
        if (appointments == null) {
            rcon.execute("noAppConfirmDlg.show()");
            
        } else {
            rcon.execute("appointmentViewerSlider()");
            this.setAppointments(Appointments);
            
        }
    }
    
    private List<Patient> getPatientsToView(List<Appointment> app) {
        List<Patient> ps = new ArrayList<>();
        for (Appointment a : app) {
            Patient p = a.getPatient();
            ps.add(p);
        }
        return ps;
    }
    
    public void appointmentViewer_AppointmentViewCloseButtonClickedListener() {
        Utility.endConversations(APPVIEWERCONVERSATIONID, cc, conversation, mrmb);
        
    }
    
    
    
    public void appointmentViewer_AppointmentViewDateSelectListener(SelectEvent sevt){
    
    }
}
