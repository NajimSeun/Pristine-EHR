package com.mpit.pristine.ejb;

import com.mpit.pristine.persistence.entity.Admission;
import com.mpit.pristine.persistence.entity.AdmissionOutcome;
import com.mpit.pristine.persistence.entity.Discharge;
import com.mpit.pristine.persistence.entity.Patient;
import com.mpit.pristine.utility.Constants;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author najim
 */
@Stateless
@LocalBean
public class AdmissionEJB {

    @PersistenceContext(unitName = "mpit_pristine_PU_one")
    private EntityManager em;

    public boolean isOnAdmission(Patient patient) {
        Query q = em.createNamedQuery("isOnAdmission");
        q.setParameter("pid", patient.getId()).setParameter("oa", Constants.ON_ADMISSION);
        List result = q.getResultList();
        if (result != null) {
            return true;
        }

        return false;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public boolean persist(Admission admission) {
        Patient p = admission.getPatient_Adm();
        if (this.isOnAdmission(p)) {
            return false;
        }

       
       
        em.merge(admission);
        return true;
    }

    public List<Admission> findAdmissionsByDateRange() {
        return this.getAdmissions("findAdmissionsByDateRange");
    }

    public List<Admission> findAdmissionsByDate() {

        return this.getAdmissions("findAdmissionsByDate");
    }

    public List<Admission> getAdmissions(String namedQuery) {

        Query query = em.createNamedQuery(namedQuery);
        return query.getResultList();

    }

    public void merge(Admission admission) {
        em.merge(admission);
    }
    
    public  List<Discharge> findAllDischarges(Date start , Date end){
       Query q = em.createNamedQuery("findDischargesByDateRange");
       q.setParameter("start", start);
       q.setParameter("end", end);
       List <Discharge> ao = (List<Discharge>)q.getResultList();
       return ao ;

    }
}
