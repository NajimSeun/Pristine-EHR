
package com.mpit.pristine.utility;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author neemarh
 */
public class Constants {

    public static final String REMARK_SCHEDULED = "Scheduled";
    public static final String OPENED = "Opened";
    public static final String NOTOPENED = "Not Opened";
    public static final String HOSPITALCODE = "HospitalCode";
    public static final String DEFAULT = "Default";
    public static final String JSON_RESX_PATH = "/com/mpit/pristine/resources/config/json/";
    public static final int MAX_CALENDAR_YEAR = 2016;
    public static final int MAX_CALENDAR_MONTH = 0;
    public static final int MAX_CALENDAR_DAY = 0;
    public static final String HOSPITALNAME = "HospitalName";
    public static final Map<String, String> DAYS_OF_WEEK_MAP = new HashMap<>();
    public static final String APPVIEW = "appView";
    public static final String SCHAPP = "schApp";
    public static final String CLINIC_SESSION_TYPE = "Clinic" ;
    public static final String DEPARTMENTTREENODEGENERATOR_DEPT_TYPE = "DEPARTMENT";
    public static final String DEPARTMENTTREENODEGENERATOR_UNIT_TYPE = "UNIT";
    public static final String DEPARTMENTTREENODEGENERATOR_CLINIC_TYPE = "CLINIC";
    public static final String DEPARTMENTTREENODEGENERATOR_ROOT_TYPE = "ROOT";
    public static final String Search_BY_NAME = "Name";
    public static final String Search_BY_SURNAME = "Surname";
    public static final String Search_BY_ID = "ID";
    public static final String APPOINTMENT_TO_EDIT = "APP_EDIT";
    public static final String SPACE = " ";
    public static final String FORWARDSLASH = "/";
    public static final String TWELVE = "12";
    public static final String SEVEN = "7";
    public static final String FIFTYTWO = "52";
    public static final String YEARS = "yrs";
    public static final String HOURS = "hrs";
    public static final String HOUR = "hour";
    public static final String WARDS = "Wards";
    public static final String UNDERSCORE = "_";
    public static final String XML_EXT = ".xml";
    public static final String SELECT = "Select";
    public static final String ON_ADMISSION = "On Admission";
    public static final String DISCHARGED = "Discharged";
    public static final String SAMA = "DAMA";
    public static final String DEAD = "Dead";
    public static final String LIVE = "Live" ;
    public static final Map<String, String> MONTHS_OF_YEAR_MAP = new HashMap<>();
    public static final String DATE_FORMAT_ONE = "d MMM yyyy";
    public static final String DATE_FORMAT_TWO = "h:mm a";
    public static final String FEMALE = "Female";
    public static final String MALE = "Male";
    public static final int FOUR = 4 ;
    public static final String NO = "NO" ;
    public static final String YES = "YES" ;
    public static final String MULTIPLE = "Multiple";
    public static final String SINGLE = "Single" ;
    public static final String MORTALITY = "Mortality" ;
    public static final String URL_USER_ID = "user_id";
    public static final String PROFESSIONALTYPE_QUERY = "professionaltype";
    public static final String CLOSED_APP_STATUS = "Closed";
    public static final String OPENED_APP_STATUS = "Opened" ;
    public static final String ENQUEUED_APP_STATUS = "Enqueed" ;
    public static final String ATTENDED_TO_APP_STATUS = "AttendedTo" ;
    public static final String SINGLE_SELECTION ="single" ;
    public static final String MULTIPLE_SELECTION = "multiple" ;
    static {
        DAYS_OF_WEEK_MAP.put("1", "Sunday");
        DAYS_OF_WEEK_MAP.put("2", "Monday");
        DAYS_OF_WEEK_MAP.put("3", "Tuesday");
        DAYS_OF_WEEK_MAP.put("4", "Wednesday");
        DAYS_OF_WEEK_MAP.put("5", "Thursday");
        DAYS_OF_WEEK_MAP.put("6", "Friday");
        DAYS_OF_WEEK_MAP.put("7", "Saturday");

        MONTHS_OF_YEAR_MAP.put("January", "0");
        MONTHS_OF_YEAR_MAP.put("Febuary", "1");
        MONTHS_OF_YEAR_MAP.put("March", "2");
        MONTHS_OF_YEAR_MAP.put("April", "3");
        MONTHS_OF_YEAR_MAP.put("May", "4");
        MONTHS_OF_YEAR_MAP.put("June", "5");
        MONTHS_OF_YEAR_MAP.put("July", "6");
        MONTHS_OF_YEAR_MAP.put("August", "7");
        MONTHS_OF_YEAR_MAP.put("September", "8");
        MONTHS_OF_YEAR_MAP.put("October", "9");
        MONTHS_OF_YEAR_MAP.put("November", "10");
        MONTHS_OF_YEAR_MAP.put("December", "11");
    }
    
    
    
    //From login Application remove after merge
    
    
    
    public static final String HOSPITAL_NAME_QUERY = "hospitalname" ; 
    public static final String HOSPITAL_CODE_QUERY = "hospitalcode" ;
    public static final String USER_ID_QUERY = "userid" ;
    public static final String DEPARTMENT_QUERY = "dept" ;
    public static final String UNIT_QUERY = "unit" ;
    public static final char  AMPERSAND = '&' ;
    public static final char EQUALS = '=' ;
    public static final String SESSION_TYPE_QUERY = "clinictype" ;
}
