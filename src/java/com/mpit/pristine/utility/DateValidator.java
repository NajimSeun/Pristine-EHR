
package com.mpit.pristine.utility;

import java.util.Date;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author najim
 */
@FacesValidator("com.mpit.pristine.utility.DateValidator")
public class DateValidator implements Validator {
    private static final Logger LOG = Logger.getLogger(DateValidator.class.getName());

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Date dValue =   (Date) value ;
        CalendarUtil  cutil = new CalendarUtil(dValue) ;
         try{
          cutil.validateCalendarFields();
         }
         catch(Exception  e){
           
             throw new ValidatorException(new FacesMessage("Invalide Date")) ;
         }
    }
    
    
}
