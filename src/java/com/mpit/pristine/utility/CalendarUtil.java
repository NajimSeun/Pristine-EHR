
package com.mpit.pristine.utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Logger;

/**
 *
 * @author najim
 */
public class CalendarUtil extends GregorianCalendar {
    private static final long serialVersionUID = 1L;

    public CalendarUtil() {
        this.setLenient(false);

    }

    public Date toDate() {


        return this.getTime();

    }
    public CalendarUtil(Date date){
      this.setTime(date);
    }
    public CalendarUtil(String dateString) {
        this();
        this.setFields(dateString);
    }

    private void setFields(String dateString) {

        int day = Integer.parseInt(dateString.substring(0, 2));
        this.set(Calendar.DAY_OF_MONTH, day);
        int month = Integer.parseInt(dateString.substring(3, 5));
        this.set(Calendar.MONTH, (month - 1));
        int year = Integer.parseInt(dateString.substring(6));
        this.set(Calendar.YEAR, year);
    }

    public void  validateCalendarFields() {
        this.computeTime();
    }

    public String dateToString(Date date) {
        return this.dateToStringHelper(date);

    }

    public String dateToString() {
        return this.dateToStringHelper(this.getTime());

    }

    private String dateToStringHelper(Date date) {

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String format1 = format.format(date);
        System.out.print(format1);
        return format1;
    }
    private static final Logger LOG = Logger.getLogger(CalendarUtil.class.getName());
}
