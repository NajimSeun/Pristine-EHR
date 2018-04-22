package com.mpit.pristine.ejb;

import com.mpit.pristine.persistence.entity.Hospital;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author neemarh
 */
@Stateless
@LocalBean
public class HospitalEJB {
    @PersistenceContext
    EntityManager em ;
    
    
    public Hospital getHospital(Class hostitalClass, String hospitalCode) {
       
        return (Hospital)em.find(hostitalClass, hospitalCode) ;
        
        
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    public boolean doesHospitalExist(Class hospitalClass, String hospitalCode) {
        if(((Hospital)em.find(hospitalClass, hospitalCode))!= null)
            return true ;
        
        
        return false ;
    }

    public void persist(Class hospitalClass, String hospitalCode , Hospital hospital) {
        
        if(this.doesHospitalExist(hospitalClass, hospitalCode))
            return ;
        
        em.persist(hospital);
    }
    
    
     
    
    
}
