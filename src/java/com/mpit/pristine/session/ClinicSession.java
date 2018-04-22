
package com.mpit.pristine.session;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.SetMultimap;
import com.mpit.pristine.persistence.entity.Appointment;
import com.mpit.pristine.persistence.entity.Patient;
import com.mpit.pristine.persistence.entity.PatientID;
import java.util.*;

/**
 *
 * @author najim
 */
public class ClinicSession extends HospitalSession {

    
    private Map<Long,Appointment> appointments ;
     private SetMultimap<String, Appointment> PersonnelAppointmentMap;  //represent opened unseen patients
    public Map<Long, Appointment> getAppointments() {
        return appointments;
    }

    public ClinicSession(String HospitalCode, String Specialty, String Unit, String Type , String tag) {
        super(HospitalCode, Specialty, Unit, Type , tag);
        this.PersonnelAppointmentMap = Multimaps.synchronizedSetMultimap(HashMultimap.<String, Appointment>create());
    }
    public void setAppointments(Map<Long, Appointment> appointments) {
        this.appointments = appointments;
    }

    public SetMultimap<String, Appointment> getPersonnelAppointmentMap() {
        return PersonnelAppointmentMap;
    }

    public void setPersonnelAppointmentMap(SetMultimap<String, Appointment> PersonnelAppointmentMap) {
        this.PersonnelAppointmentMap = PersonnelAppointmentMap;
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
   
    private Map<PatientID ,Patient> PatientMap_Sync = null;   // represents then unseen patients
    private Map<PatientID , Patient> AttendedPatient ;  // represents seen patients
  
    private List<Appointment> appointments_Sync = null ;
    
    public static final String ALREADYOPENED = "Some selected patients appear to have already been opened" ;
    public List<Appointment> getAppointments_Sync() {
        return appointments_Sync;
    }

    public void setAppointments_Sync(List<Appointment> appointments_Sync) {
        this.appointments_Sync = appointments_Sync;
        
    }

    public Map<PatientID, Patient> getAttendedPatient() {
        return AttendedPatient;
    }

    public void setAttendedPatient(Map<PatientID, Patient> AttendedPatient) {
        this.AttendedPatient = AttendedPatient;
    }

    
     
    
    

    public ClinicSession() {
        
        this.AttendedPatient =  Collections.synchronizedMap(new HashMap<PatientID , Patient>());
    }

     

     

   
    public List<Patient> getPatientOnAppointmentList(){
          return  createPatientList();
    }
    public void setPatientsAndAppointmentsSync(List<Appointment> appointments, Map<PatientID ,Patient> patientMap) {
       this.PatientMap_Sync = Collections.synchronizedMap(patientMap) ;
        this.appointments_Sync = Collections.synchronizedList(appointments);
    }
    
    private List<Patient> createPatientList(){
       Collection <Patient> pc= this.PatientMap_Sync.values();
        List<Patient> pl  = new ArrayList<>() ;
        for (Iterator<Patient> it = pc.iterator(); it.hasNext();) {
            Patient p = it.next();
            pl.add(p);
        }
        return pl ;
    }

    public Map<PatientID, Patient> getPatientMap_Sync() {
        return PatientMap_Sync;
    }

    public void setPatientMap_Sync(Map<PatientID, Patient> PatientMap_Sync) {
        this.PatientMap_Sync = PatientMap_Sync;
    }
    
    
}
