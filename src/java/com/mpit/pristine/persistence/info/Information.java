package com.mpit.pristine.persistence.info;

import java.util.Map;

/**
 *
 * @author najim
 */
public interface Information {
    
    public abstract String getInformationType() ;
    public abstract Map<Object,Object> getCompleteInfo() ;
    
    
}
