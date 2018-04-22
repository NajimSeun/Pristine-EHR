package com.mpit.pristine.ejb;

import com.google.common.collect.SetMultimap;
import com.mpit.pristine.persistence.entity.Appointment;
import com.mpit.pristine.session.ClinicSession;
import com.mpit.pristine.session.HospitalSession;
import com.mpit.pristine.utility.Constants;
import com.mpit.pristine.utility.Utility;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;

/**
 *
 * @author neemarh
 */
@Singleton
public class HospitalSessionEJB {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private final Map<String, HospitalSession> HospitalSessions;
    @Inject
    private AppointmentEJB appEJB;

    public HospitalSessionEJB() {
        this.HospitalSessions = Collections.synchronizedMap(new HashMap<String, HospitalSession>());
    }

    public void initializeSession(String hospitalCode, String department, String unit, String type, String tag) {
       
        if (type.equals(Constants.CLINIC_SESSION_TYPE)) {

           ClinicSession cs = new ClinicSession(hospitalCode, department, unit, type, tag);
            this.HospitalSessions.put(tag, cs);
            cs.setAppointments(this.loadClinicAppointment(cs));
        }
    }

    public boolean doesClinicSessionExist(String sessionTag) {

        return this.HospitalSessions.containsKey(sessionTag);
    }

    public boolean doesClinicSessionExist(ClinicSession cs) {

        return this.HospitalSessions.containsKey(cs.getTag());
    }

    private Map<Long,Appointment> loadClinicAppointment(ClinicSession cs) {
        
        Date date = Utility.createStandardizedDate(Calendar.getInstance());

       return this.createAppointmentsMap(appEJB.getAppointments(cs.getHospitalCode(), cs.getSpecialty(), cs.getUnit(), date));

    }

    public void addToSessionPersonnelMap(String proType, String userid, String sessionTag) {
        HospitalSession hSession = this.HospitalSessions.get(sessionTag);
        hSession.getPersonnelTypeToIDMap().put(proType, userid); //Todo: watch this
    }

    public List<Appointment> getAppointments(String sessionTag) {
        Map<Long, Appointment> appointments = ((ClinicSession) this.HospitalSessions.get(sessionTag)).getAppointments();
        return this.createAppointmentList(appointments);
    }

    public List<Appointment> openAppointments(List<Appointment> appToOpen, String userid, String sessionTag) {
        ClinicSession cs = (ClinicSession) this.HospitalSessions.get(sessionTag);
        List<Appointment> toOpened = new ArrayList<>();
        Map<Long, Appointment> allApp = cs.getAppointments();
        for (Appointment app : appToOpen) {
            Appointment get = allApp.get(app.getId());
            if (get.getOpenStatus().equals(Constants.CLOSED_APP_STATUS)) {
                get.setOpenStatus(Constants.OPENED_APP_STATUS);
                toOpened.add(get);
            }

        }
        this.registerWithUser(cs, userid, toOpened);
        return this.createAppointmentList(allApp);

    }

    private void registerWithUser(ClinicSession cs, String userid, List<Appointment> toOpen) {
        SetMultimap<String, Appointment> personnelAppointmentMap = cs.getPersonnelAppointmentMap();
        for (Appointment app : toOpen) {
            personnelAppointmentMap.put(userid, app);
        }
    }

    private List<Appointment> createAppointmentList(Map<Long, Appointment> app) {
        List<Appointment> lApp = new ArrayList<>();
        Collection<Appointment> cApp = app.values();
        lApp.addAll(cApp);
        return lApp;

    }

    public List<Appointment> openedAppointments(String sessionTag) {
        ClinicSession cs = (ClinicSession) this.HospitalSessions.get(sessionTag);
        List<Appointment> apps = new ArrayList<>();
        Map<Long, Appointment> appointments = cs.getAppointments();
        Iterator<Appointment> iterator = appointments.values().iterator();
        while (iterator.hasNext()) {
            Appointment nextApp = iterator.next();
            if (nextApp.getOpenStatus().equals(Constants.OPENED_APP_STATUS)) {
                apps.add(nextApp);
            }
        }
        return apps;
    }

    public void enQueueAppointments(List<Appointment> appToEnQueue, String userDocId, String sessionTag) { // called from main window
        ClinicSession cs = (ClinicSession) this.HospitalSessions.get(sessionTag);
        for (Appointment app : appToEnQueue) {
            if (app.getOpenStatus().equals(Constants.OPENED_APP_STATUS)) {
                cs.getPersonnelAppointmentMap().put(userDocId, app);
                app.setOpenStatus(Constants.ENQUEUED_APP_STATUS);
            }
        }

    }

    public List<Appointment> enQueuedAppointments(String sessionTag) {
        ClinicSession cs = (ClinicSession) this.HospitalSessions.get(sessionTag);
        List<Appointment> enQueued = new ArrayList<>();
        Map<Long, Appointment> appointments = cs.getAppointments();
        Iterator<Appointment> iterator = appointments.values().iterator();
        while (iterator.hasNext()) {
            Appointment next = iterator.next();
            if (next.getOpenStatus().equals(Constants.ENQUEUED_APP_STATUS)) {
                enQueued.add(next);
            }
        }
        return enQueued;
    }

    public List<Appointment> allAppointments(String sessionTag) {
                ClinicSession cs = (ClinicSession) this.HospitalSessions.get(sessionTag);
                return this.createAppointmentList(cs.getAppointments());

    }

    
    
    public List<Appointment> attendedToAppointments(String sessionTag) {
        //Todoimplement
        return null ;//for now
    }

    
    
    private Map<Long, Appointment> createAppointmentsMap(List<Appointment> apps) {
        Map<Long, Appointment> appMap = new HashMap<>();
        for (Appointment app : apps) {
            appMap.put(app.getId(), app);
        }
        return appMap;

    }
    
    @Schedule(minute="30" , hour="*")
    public void updateClinicSessions(){
        if(this.HospitalSessions.isEmpty()) {
            return ;
        }
        Iterator<HospitalSession> sessionIterator = this.HospitalSessions.values().iterator();
        while(sessionIterator.hasNext()){
            HospitalSession nextHs = sessionIterator.next();
            if(nextHs instanceof ClinicSession){
             ClinicSession cs = (ClinicSession) nextHs ;
             this.reloadClinicSessionAppointments(cs);
            }
        }
        
        
 }
    
    public void reloadClinicSessionAppointments(String sessionTag){
         this.reloadClinicSessionAppointmentsHelper((ClinicSession)this.HospitalSessions.get(sessionTag));
    }
    
    public void reloadClinicSessionAppointments(ClinicSession cs){
         this.reloadClinicSessionAppointmentsHelper(cs);
    }
    
    private void reloadClinicSessionAppointmentsHelper(ClinicSession cs){
        Map<Long, Appointment> csm = this.loadClinicAppointment(cs);
        if(csm.size() ==  cs.getAppointments().size())  // if lenght is equal unlikelythings have changed
            return ;
        
        Iterator<Appointment> iterator = csm.values().iterator();
        while(iterator.hasNext()){
            Appointment next = iterator.next();
            if(!cs.getAppointments().containsKey(next.getId())) {
                cs.getAppointments().put(next.getId(), next);
            }
            
    }
}
}