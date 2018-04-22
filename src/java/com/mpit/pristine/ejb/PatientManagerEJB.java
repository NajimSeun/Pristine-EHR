package com.mpit.pristine.ejb;

import com.mpit.pristine.persistence.entity.ContactInformation;
import com.mpit.pristine.persistence.entity.Patient;
import com.mpit.pristine.persistence.entity.PatientID;
import com.mpit.pristine.persistence.entity.PersonalInformation;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author najim
 */

@Stateless
@LocalBean
public class PatientManagerEJB {

    @PersistenceContext
    private EntityManager em;

    public boolean doesHospitalNumberExist(PatientID patientID) {

        if (em.find(Patient.class, patientID) != null) {
            return true;
        }

        return false;
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private static final Logger LOG = Logger.getLogger(PatientManagerEJB.class.getName());

    public Patient openPatient(PatientID patientID) {

        return (Patient) em.find(Patient.class, patientID);

    }
    
    

    public List<Patient> findByIdAndName(PatientID id, String name) {
        Query query = em.createNamedQuery("findByIDAndName");
        query.setParameter("patientID", id).setParameter("name", name);
        return query.getResultList();
    }

    public List<Patient> findByIdAndSurname(PatientID id, String surname) {
        Query query = em.createNamedQuery("findByIDAndSurame");
        query.setParameter("patientID", id).setParameter("name", surname);
        return (List<Patient>) query.getResultList();
    }

    public List<Patient> findByNameAndSurname(String name, String surname) {
        Query query = em.createNamedQuery("findByNameAndSurname");
        query.setParameter("name", name).setParameter("surname", surname);
        return (List<Patient>) query.getResultList();
    }

    public List<Patient> findById(PatientID id) {
        Query query = em.createNamedQuery("findByID");
        query.setParameter("patientID", id);
        return (List<Patient>) query.getResultList();
    }

    public List<Patient> findByName(String name) {
        Query query = em.createNamedQuery("findByName");
        query.setParameter("name", name);
        return (List<Patient>) query.getResultList();
    }

    public List<Patient> findBySurname(String surname) {
        Query query = em.createNamedQuery("findBySurname");
        query.setParameter("surname", surname);
        return (List<Patient>) query.getResultList();
    }

    public List<Patient> findByIDNameAndSurname(PatientID id, String name, String surname) {
        Query query = em.createNamedQuery("findByIDNameAndSurname");
        query.setParameter("patientID", id);
        query.setParameter("name", name);
        query.setParameter("surname", surname);
        return (List<Patient>) query.getResultList();



    }

    public void persistPatient(Patient patient) {
        if(this.doesHospitalNumberExist(patient.getId())) {
            return ;
        }
        
        ContactInformation cinfo = patient.getCinfo();
        PersonalInformation pinfo = patient.getPinfo() ;
        
        em.persist(patient);
        em.persist(cinfo);
        em.persist(pinfo);
    }
        
}
