
package com.mpit.pristine.utility;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hp
 */
public class RegistrationUtility {
    
    
    private  static String OccupationsFile = "occupations" ;
    private static String CountriesFile = "countries" ;
    
    public static List<String> getOccupations(){
    
      String fileToRead = OccupationsFile + Utility.XMLExtension ;
      return Utility.read(fileToRead);
      
     
     
    }
    

    public static  List<String> getCountries(){
        String countryPathPlusXtens = CountriesFile + Utility.XMLExtension ;
        return LocationUtility.getCountryListAsStr(countryPathPlusXtens) ;
    }
}
