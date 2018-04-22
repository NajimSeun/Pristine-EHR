package com.mpit.pristine.location;

import com.mpit.pristine.utility.LocationUtility;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author Hp
 */
public class Country implements  Location{
    private static final Logger LOG = Logger.getLogger(Country.class.getName());
    private String Name ;

    @Override
    public String getName() {
       return Name ;
    }
    
    public List<State> getStates_Provinces(){
       return null ;
    }

    public Country(String Name) {
        this.Name = Name;
    }
    
    public List<String> getStates_ProvincesName(){
       return LocationUtility.getStatesListAsStr(this) ;
    }
    
}
