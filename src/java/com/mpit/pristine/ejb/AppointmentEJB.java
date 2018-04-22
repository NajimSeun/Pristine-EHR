package com.mpit.pristine.ejb;

import com.mpit.pristine.persistence.entity.Appointment;
import com.mpit.pristine.persistence.entity.Patient;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

/**
 *
 * @author najim
 */
@Stateless
@LocalBean
public class AppointmentEJB {

    @PersistenceContext
    private EntityManager em;
    //  @Inject
    //private PristineMedicalRecordManagerBean pmrmb;

    public List<Appointment> getAppointments(String hospitalCode, String specialty, String unit, Date appointmentDate) {
        Query query = em.createNamedQuery("Appointments");
        query.setParameter("date", appointmentDate, TemporalType.DATE);
        query.setParameter("unt", unit);
        query.setParameter("hcode", hospitalCode);
        query.setParameter("dept", specialty);
        List<Appointment> app = null;
        app = (List<Appointment>) getResultList(query);
        return app;
    }

    public boolean scheduleAppointment(Appointment appointment) {

        if (this.doesAppointmentExist(appointment)) {
            return false;
        } else {
            Patient p = appointment.getPatient();
            if(p == null){
                return false ;
            }
            
            

            em.merge(appointment);
            return true;
        }
    }
    
    
    public boolean updateAppointment(Appointment app){
      if(!this.doesAppointmentExist(app)){
         return false ;
      }
      
      em.merge(app);
       return true ;
    }

    public List<Appointment> getAppointmentDeptBased(String hospitalCode, String department, String patientID) {
        Query query = em.createNamedQuery("deptBasedApp");
        query.setParameter("patientID", patientID).setParameter("hcode", hospitalCode).setParameter("dept", department);
        List<Appointment> app = null;
        app = (List<Appointment>) getResultList(query);
        return app;
    }

    public List<Appointment> getAppointmentUnitBased(String hospitalCode, String department, String unit, String patientID) {
        Query query = em.createNamedQuery("unitBasedApp");
        query.setParameter("patientID", patientID).setParameter("hcode", hospitalCode).setParameter("dept", department).setParameter("unit", unit);
        List<Appointment> app = null;
        app = (List<Appointment>) getResultList(query);
        return app;

    }

    private List getResultList(Query query) {
        return query.getResultList();
    }

    private boolean doesAppointmentExist(String hospitalCode, String department, String unit, String patientID, Date appointmentDate) {

        Query query = em.createNamedQuery("doesAppointmentExist");
        query.setParameter("patientID", patientID).setParameter("hcode", hospitalCode).setParameter("dept", department).setParameter("unit", unit).setParameter("date", appointmentDate, TemporalType.DATE);
        List result = query.getResultList();
        if (result == null || result.isEmpty()) {
            return false;
        }

        return true;
    }

    public boolean doesAppointmentExist(Appointment app) {
        
        Query query = em.createNamedQuery("doesAppointmentExist");
        query.setParameter("patientID", app.getPatient().getId().getID()).setParameter("hcode", app.getHospitalCode()).setParameter("dept",app.getDepartment()).setParameter("unit",app.getUnit()).setParameter("date", app.getDateOfAppointment(), TemporalType.DATE);
        List result = query.getResultList();
        if (result == null || result.isEmpty()) {
            return false;
        }

        return true;
       
    }

    public List<Appointment> findAllPatientAppointment(Patient p){
      Query q = em.createNamedQuery("allPatientAppointments");
      q.setParameter("patientID", p.getId()) ;
      return (List<Appointment>)q.getResultList() ;
      
    }
    public void removeAppointment(Appointment app) {
        em.remove(app);
    }
}
