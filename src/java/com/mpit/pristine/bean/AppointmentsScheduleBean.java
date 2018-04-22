package com.mpit.pristine.bean;

import com.mpit.pristine.bean.interfaces.DepartmentBeanInterface;
import com.mpit.pristine.ejb.AppointmentEJB;
import com.mpit.pristine.persistence.entity.Appointment;
import com.mpit.pristine.qualifiers.ScheduleAppointment;
import com.mpit.pristine.utility.Constants;
import com.mpit.pristine.utility.ELResolver;
import com.mpit.pristine.utility.Utility;
import java.io.Serializable;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import org.jboss.weld.context.ConversationContext;
import org.jboss.weld.context.ManagedConversation;
import org.jboss.weld.context.http.Http;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author najim
 */
@Named("appScheduleBean")
@ConversationScoped
public class AppointmentsScheduleBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new instance of AppointmentsScheduleBean
     */
    public AppointmentsScheduleBean() {
    }
    /**
     *
     * SCHEDULE APPOINTMENT VARIABLES AND FUNCTION just changed its scope but
     * yet the check what side effects that will have
     * ********************************************************************************
     *
     *
     *
     * @param evt
     */
    private Date minDate;
    private Date maxDate;
    private boolean schedule_DepartmentSelectButtonDisabled = true;
    private static final String SCHEDULEAPPOINTMENTCID = "SACID";
    @Inject
    private AppointmentEJB aEJB;
    @Inject
    private MedicalRecordManagerBean mrmb;
    @Inject
    @ScheduleAppointment
    private DepartmentBeanInterface deptBean;
    @Inject
    private Conversation conversation;
    @Inject
    private ResourceLoadingHelperBean rlhc;
    private static final Logger LOG = Logger.getLogger(AppointmentsScheduleBean.class.getName());
    @Http
    @Inject
    private ConversationContext cc;

    public ResourceLoadingHelperBean getRlhc() {
        return rlhc;
    }

    public void setRlhc(ResourceLoadingHelperBean rlhc) {
        this.rlhc = rlhc;
    }

    public void initConversation() {
        LOG.log(Level.SEVERE, "Before App{0}", new Object[]{conversation.getId()});
        if (this.conversation.isTransient()) {
            if (this.mrmb.getDialogConversationID() == null) {
                conversation.begin(SCHEDULEAPPOINTMENTCID);
                this.mrmb.setDialogConversationID(SCHEDULEAPPOINTMENTCID);


            } else {
                conversation.begin();
            }

        }
        LOG.log(Level.SEVERE, "After App{0}", new Object[]{conversation.getId()});
    }

    @PostConstruct
    public void init() {

        this.initializeDate();
        FacesContext con = FacesContext.getCurrentInstance();
        LOG.log(Level.SEVERE, "Post construct Called");
        if (!this.rlhc.isDeptRootSet()) {
            LOG.log(Level.SEVERE, "Is dept set Called");
            this.deptBean.department_LoadDepartmentTreeRoot(con);
            this.rlhc.setDeptRoot(this.deptBean.getDepartment_Root());
            this.rlhc.setDeptRootSet(true);
            LOG.log(Level.SEVERE, "Leaving is dept Set");
        }




    }

    public DepartmentBeanInterface getDeptBean() {
        return deptBean;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public MedicalRecordManagerBean getMrmb() {
        return mrmb;
    }

    public void setMrmb(MedicalRecordManagerBean mrmb) {
        this.mrmb = mrmb;
    }

    public void setDeptBean(DepartmentBeanInterface deptBean) {
        this.deptBean = deptBean;
    }

    public boolean isSchedule_DepartmentSelectButtonDisabled() {
        return schedule_DepartmentSelectButtonDisabled;
    }

    public void setSchedule_DepartmentSelectButtonDisabled(boolean schedule_DepartmentSelectButtonDisabled) {
        this.schedule_DepartmentSelectButtonDisabled = schedule_DepartmentSelectButtonDisabled;
    }

    //A PostConstruct method
    public void initializeDate() {
        Calendar minCal = Calendar.getInstance();
        Calendar maxCal = Calendar.getInstance();
        minDate = minCal.getTime();
        maxCal.set(2016, 1, 1);
        maxDate = maxCal.getTime();
    }

    // Marked for removal
    public void schedule_ScheduleAppointmentDateSelectedListener(SelectEvent evt) {
        Date date = (Date) evt.getObject();
        if (date.before(new Date(System.currentTimeMillis()))) {
            FacesContext context = FacesContext.getCurrentInstance();
            String msg = ELResolver.resolve(context, "#{mrmp.dateselecterror}");
            context.addMessage(evt.getComponent().getClientId(context), new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg));
            this.setSchedule_DepartmentSelectButtonDisabled(true);
            return;
        }
        this.setSchedule_DepartmentSelectButtonDisabled(false);
    }

    public Date getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
    }

    public Date getMinDate() {
        return minDate;
    }

    public void setMinDate(Date minDate) {
        this.minDate = minDate;
    }

    public void schedule_ScheduleAppointmentDialogRequestedListener() { // Listner for schedule appointment on main window
        RequestContext rContext = RequestContext.getCurrentInstance();
        FacesContext fContext = FacesContext.getCurrentInstance();
        if (mrmb.getSelectedPatients().length > 1 || mrmb.getSelectedPatients() == null) {
            rContext.addCallbackParam("WrongSelection", true);
            String value = ELResolver.resolve(fContext, "#{mrmp.scheduleAppSelectionError}");
            String clientID = fContext.getExternalContext().getRequestParameterMap().get("schAppMIID");
            fContext.addMessage(clientID, new FacesMessage(FacesMessage.SEVERITY_ERROR, value, value));
            return;
        }
        rContext.addCallbackParam("WrongSelection", false);

    }
    //TODO: Needs edit so edit functionality is not needed
    public void schedule_ScheduleAppointment() {
        LOG.log(Level.SEVERE, "SCHEDULEAPPOINTMENTCID");
        FacesContext con = FacesContext.getCurrentInstance();
        Map<String, Object> map = con.getExternalContext().getSessionMap();
        String hospitalCode =  this.mrmb.getHospitalCode();
        if (map.containsKey(Constants.APPOINTMENT_TO_EDIT)) {
            Appointment app = (Appointment) map.get(Constants.APPOINTMENT_TO_EDIT);
            if (aEJB.doesAppointmentExist(app)) {
                aEJB.removeAppointment(app);
            }
        }
        RequestContext rcon = RequestContext.getCurrentInstance();
        Calendar unStandardizedCal = Calendar.getInstance();
        unStandardizedCal.setTime(this.deptBean.getDepartment_SelectedDate());
        Appointment app = new Appointment(this.deptBean.getDepartment_SelectedDepartment().trim(), Utility.createStandardizedDate(unStandardizedCal), Constants.REMARK_SCHEDULED, this.mrmb.getSelectedPatient(), null, this.deptBean.getDepartment_SelectedUnit().trim(), hospitalCode);
        if (this.aEJB.scheduleAppointment(app)) {
            rcon.execute("appScheduleConfirmDlg.show()");
        } else {
            rcon.execute("appNotScheduledConfirnDlg.show()");
        }

         
    }

    public void schedule_ScheduleAppointmentCancelButtonClickedListener() {
        RequestContext rcon = RequestContext.getCurrentInstance();
        rcon.execute("appNotScheduledConfirnDlg.show()");
        this.endConversations();
         
    }

    private void endConversations() {
        Collection<ManagedConversation> conversations = cc.getConversations();
        for (Iterator<ManagedConversation> it = conversations.iterator(); it.hasNext();) {
            ManagedConversation mc = it.next();
            if (!mc.getId().equals(AppointmentsScheduleBean.SCHEDULEAPPOINTMENTCID) && !mc.isTransient()) {
                mc.end();
            }
            this.conversation.end();
        }
        this.mrmb.setDialogConversationID(null);
    }

    
}
