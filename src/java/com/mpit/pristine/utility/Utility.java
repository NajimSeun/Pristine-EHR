
package com.mpit.pristine.utility;

import com.mpit.pristine.bean.BirthsBean;
import com.mpit.pristine.bean.MedicalRecordManagerBean;
import java.io.IOException;
import java.util.*;
import javax.enterprise.context.Conversation;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.xml.parsers.ParserConfigurationException;
import org.jboss.weld.context.ConversationContext;
import org.jboss.weld.context.ManagedConversation;
import org.primefaces.model.TreeNode;
import org.xml.sax.SAXException;

/**
 *
 * @author neemarh
 */
public class Utility {

    public static final String XMLExtension = ".xml";

    public static ArrayList<String> read(String fileToRead) {


        ArrayList<String> stringCollection = null;
        try {
            stringCollection = new XMLReader().readXml(fileToRead);
        } catch (ParserConfigurationException | SAXException | IOException e) {
        }


        return stringCollection;
    }

    public static Date createStandardizedDate(Calendar unStandardizedCalendar) {
        Calendar standardizedCalendar = Calendar.getInstance();
        standardizedCalendar.set(unStandardizedCalendar.get(Calendar.YEAR), unStandardizedCalendar.get(Calendar.MONTH), unStandardizedCalendar.get(Calendar.DAY_OF_MONTH));
        return standardizedCalendar.getTime();
    }

    public static String getHospitalCode(FacesContext con) {
        return (String) con.getExternalContext().getSessionMap().get(Constants.HOSPITALCODE);
    }

    public static String getHospitalName(FacesContext con) {
        return (String) con.getExternalContext().getSessionMap().get(Constants.HOSPITALNAME);
    }

    public static String convertMSToWeekString(long ms) {
        long weekInMS = 7 * 24 * 60 * 60 * 1000;
        long dayInMS = 1 * 24 * 60 * 60 * 1000;
        long weeks = (ms / weekInMS);
        long days = (ms % weekInMS) / dayInMS;
        StringBuilder builder = new StringBuilder();
        builder.append(weeks).append(" ").append("Week(s)").append(" ").append(days).append(" ").append("day(s)");
        return builder.toString();

    }

    public static Calendar resetCalendar(Calendar unset) {
        Calendar set = Calendar.getInstance();
       
        set.set(Calendar.YEAR, unset.get(Calendar.YEAR));
        set.set(Calendar.MONTH, unset.get(Calendar.MONTH));
        set.set(Calendar.DAY_OF_MONTH, unset.get(Calendar.DAY_OF_MONTH));
        set.set(Calendar.HOUR, 0);
        set.set(Calendar.MINUTE, 0);
        set.set(Calendar.SECOND, 2);
        set.set(Calendar.AM_PM, Calendar.AM);
        return set;
    }

    public static void endConversations(String cid, ConversationContext cc, Conversation conversation, MedicalRecordManagerBean mrmb) {
        Collection<ManagedConversation> conversations = cc.getConversations();
        for (Iterator<ManagedConversation> it = conversations.iterator(); it.hasNext();) {
            ManagedConversation mc = it.next();
            if (!mc.getId().equals(cid) && !mc.isTransient()) {
                mc.end();
            }
            conversation.end();
        }
        mrmb.setDialogConversationID(null);
    }

    public static void removeClinicNodes(TreeNode node) {


        if (node.getType().equals(Constants.DEPARTMENTTREENODEGENERATOR_CLINIC_TYPE) && node.isLeaf()) {
            node = null;
            return;
        } else {
            for (int x = 0; x < node.getChildCount(); x++) {
                removeClinicNodes(node.getChildren().get(x));
            }
        }
    }
    
    
    public static Calendar mergeDates(Date date , Date time){
    
        Calendar dateCalendarUnStandardized = Calendar.getInstance();
        dateCalendarUnStandardized.setTime(date);
        Calendar dateCalendarStandardized = Calendar.getInstance();
        dateCalendarStandardized.setTime(Utility.createStandardizedDate(dateCalendarUnStandardized));
        Calendar timeCalendar = Calendar.getInstance();
        timeCalendar.setTime(time);
        dateCalendarStandardized.set(Calendar.HOUR, timeCalendar.get(Calendar.HOUR));
        dateCalendarStandardized.set(Calendar.MINUTE, timeCalendar.get(Calendar.SECOND));
        return dateCalendarStandardized ;
    }
    
    
    
     public static void addMessage(FacesContext fcon , String parameterName  , String messageEL ){
      
        String clientID =  fcon.getExternalContext().getRequestParameterMap().get(parameterName);
        fcon.addMessage(clientID, new FacesMessage(FacesMessage.SEVERITY_INFO,ELResolver.resolve(fcon, messageEL),  ELResolver.resolve(fcon, messageEL)));
    }
     
     public static void initConversation(Conversation conversation , MedicalRecordManagerBean mrmb,String cid){
    if (conversation.isTransient()) {
            if (mrmb.getDialogConversationID() == null) {
                conversation.begin(cid);
                mrmb.setDialogConversationID(conversation.getId());
            } else {
                conversation.begin();
            }
        }
}
}
