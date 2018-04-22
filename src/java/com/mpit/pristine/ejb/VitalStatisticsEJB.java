package com.mpit.pristine.ejb;

import com.mpit.pristine.persistence.entity.Birth;
import com.mpit.pristine.persistence.entity.Patient;
import com.mpit.pristine.utility.Constants;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author najim
 */
@Stateless
@LocalBean
public class VitalStatisticsEJB {

    @PersistenceContext(unitName = "mpit_pristine_PU_one")
    private EntityManager em;
    @Inject
    private AdmissionEJB admEjb;

    public void persist(Object object) {
        em.persist(object);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public boolean registerBirth(Birth birth) { 
        Patient mother = birth.getMother();
        if (!this.admEjb.isOnAdmission(mother)) {
            return false;
        }

        

        em.merge(birth);
        return true;

    }
    
    public List<Birth> findBirthByDateRange(Date start, Date end){
      Query query = em.createNamedQuery("findBirthsByDateRange");
      query.setParameter("start", start) ;
      query.setParameter("end", end);
      return query.getResultList();
    }
    //TODO: Check method
    public boolean  hasRecentBirth(Patient mother){
        
        Query query = em.createNamedQuery("findPatientBirthsOrderByDOB");
        query.setParameter("pid",mother.getId() );
        List<Birth> births = query.getResultList();
        if(births.isEmpty())
            return false ;
        
        Calendar dobPlusCalendar = Calendar.getInstance() ;
        dobPlusCalendar.setTime(births.get(0).getBabyDateOfBirth());
        dobPlusCalendar.add(Calendar.MONTH, Constants.FOUR);
        if(dobPlusCalendar.compareTo(Calendar.getInstance()) <= 0 ){
          return false ;
        }
        
        return true;
                
        
    }
}
