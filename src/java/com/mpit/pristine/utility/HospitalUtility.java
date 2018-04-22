
package com.mpit.pristine.utility;

import java.util.List;

/**
 * I intend to allow hospital specific info to be put in packages with ext extension
 * @author najim
 */
public class HospitalUtility {
    
    
    private static final String HOSPITALUTILITYCONTEXTPATH = "hospital/config/" ;
    
    public static List<String> readHospitalWards(String hospitalName){
         String path = null  ;
        if(hospitalName == null) {
            path =  HospitalUtility.HOSPITALUTILITYCONTEXTPATH + Constants.DEFAULT + Constants.UNDERSCORE + Constants.WARDS;
        }
        else {
            path =  HospitalUtility.HOSPITALUTILITYCONTEXTPATH + hospitalName.trim()+ Constants.UNDERSCORE + Constants.WARDS ;
        }
        return Utility.read(path);
    }
}
