package com.mpit.pristine.bean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Named;

/**
 *
 * @author najim
 */
@Named(value = "monthSelectBean")
@Dependent
public class MonthSelectBean {

    /**
     * Creates a new instance of MonthSelectBean
     */
    private List<String> years;
    private static final int offset = 10;
    private String selectedMonthYear ;
    private String selectedMonth  ;
    private String selectedYear  ;
     
    
    public MonthSelectBean() {
        this.CreateYearRange(offset);
    }

    public String getSelectedMonth() {
        return selectedMonth;
    }

    public void setSelectedMonth(String selectedMonth) {
        this.selectedMonth = selectedMonth;
    }

    public String getSelectedYear() {
        return selectedYear;
    }

    public void setSelectedYear(String selectedYear) {
        this.selectedYear = selectedYear;
    }

     

     

    public String getSelectedMonthYear() {
        return selectedMonthYear;
    }

    public void setSelectedMonthYear(String selectedMonthYear) {
        this.selectedMonthYear = selectedMonthYear;
        String [] monthYearArray = this.selectedMonthYear.split("-");
        this.selectedMonth= monthYearArray[0] ;
        this.selectedYear = monthYearArray[1] ;
        
    }

    
    
    public List<String> getYears() {
        return years;
    }

    public void setYears(List<String> years) {
        this.years = years;
    }

    private void CreateYearRange(int offset) {

        years = new ArrayList<String>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int x = offset; x > 0; x--) {
            years.add(String.valueOf(currentYear));
            currentYear--;
        }
    }
    
    
}
