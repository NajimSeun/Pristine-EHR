
package com.mpit.pristine.utility;

import com.mpit.pristine.ejb.PatientManagerEJB;
import com.mpit.pristine.persistence.entity.PatientID;
import java.util.Random;
import javax.naming.InitialContext;

/**
 *
 * @author Hp
 */
public class PatientIDAutogenerator {

    public static String defAutogenerator(String [] args) {
        
        Random random = new Random();
        PatientManagerEJB pre = null;
        StringBuilder builder = new StringBuilder();
        String returnString;
        for (int i = 0; i < 6; i++) {
            int randomValue = random.nextInt(9);
            builder.append(randomValue);
        }
        returnString = builder.toString();
        PatientID patientID = new PatientID(args[0],args[1],returnString) ;
        try {
            pre = (PatientManagerEJB) (new InitialContext().lookup("java:global/MedicalRecordManagerConsole/PatientManagerEJB"));
        } catch (Exception e) {
        }
        if (pre.doesHospitalNumberExist(patientID)) {
            returnString = defAutogenerator(args);
        }

        return returnString;



    }
}
