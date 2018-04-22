package com.mpit.pristine.json;

import com.google.common.io.Files;
import com.mpit.pristine.utility.Constants;
import java.io.*;
import javax.faces.context.FacesContext;

/**
 *
 * @author najim
 */
public class JsonResourceReader {
    
    private static final String HOSPITAL_DEPT_RESX = "Dept_Config.json";
    private static final String HOSPITAL_DEF_RESX = "Hospital.json" ;
    /**
     * Get the value of HOSPITAL_DEPT_RESX
     *
     * @return the value of HOSPITAL_DEPT_RESX
     */
    public static String getHOSPITAL_DEPT_RESX() {
        return HOSPITAL_DEPT_RESX;
    }
    
    public BufferedReader getHospitalDeptConfigReader(FacesContext context ,String hospitalCode)   {
        String resxPath;
        if (hospitalCode == null) {
            resxPath = Constants.DEFAULT + "_" + HOSPITAL_DEPT_RESX;
        } else {
            resxPath = hospitalCode + "_" + HOSPITAL_DEPT_RESX;
        }
        return getReader(context ,resxPath);
    }
    
    private BufferedReader getReader(FacesContext context ,String path) {
        
        BufferedReader br = null ;
        
            String fullPath = Constants.JSON_RESX_PATH + path;
             ClassLoader loader = Thread.currentThread().getContextClassLoader() ;
             
            
            br = new BufferedReader(new InputStreamReader(loader.getResourceAsStream(fullPath)));
            
        return br;
    }
    
    public boolean checkHospitalDefinationJSONResx(){
        
    String path =   Constants.JSON_RESX_PATH + JsonResourceReader.HOSPITAL_DEF_RESX ;
    ClassLoader loader = Thread.currentThread().getContextClassLoader() ;
        InputStream resx = loader.getResourceAsStream(path);
        return resx !=null;
    }
    
    public  BufferedReader getHospitalDefinitionReader(){
       BufferedReader reader = null ;
       String path =   Constants.JSON_RESX_PATH + JsonResourceReader.HOSPITAL_DEF_RESX ;
       ClassLoader loader = Thread.currentThread().getContextClassLoader() ;
       reader = new BufferedReader(new InputStreamReader(loader.getResourceAsStream(path))) ;
       return reader ;
       
    }
}
