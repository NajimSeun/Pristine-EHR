package com.mpit.pristine.bean;

import com.mpit.pristine.ejb.AdmissionEJB;
import com.mpit.pristine.persistence.entity.Discharge;
import com.mpit.pristine.utility.Constants;
import com.mpit.pristine.utility.ELResolver;
import com.mpit.pristine.utility.Utility;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author najim
 */
@Named(value = "dischargesBean")
@RequestScoped
public class DischargesBean {

    /**
     * Creates a new instance of Discharges
     */
    private List<Discharge> discharges;
    @Inject
    private AdmissionEJB admEJB;
    @Inject
    private MonthSelectBean msb;

    public DischargesBean() {
    }

    public AdmissionEJB getAdmEJB() {
        return admEJB;
    }

    public void setAdmEJB(AdmissionEJB admEJB) {
        this.admEJB = admEJB;
    }

    public MonthSelectBean getMsb() {
        return msb;
    }

    public void setMsb(MonthSelectBean msb) {
        this.msb = msb;
    }

    public List<Discharge> getDischarges() {
        return discharges;
    }

    public void setDischarges(List<Discharge> discharges) {
        this.discharges = discharges;
    }

    public void discharges_DischargesShowDischargesButtonClickedListener() {  //Todo: Requries better error handling
        FacesContext fcon = FacesContext.getCurrentInstance();
        String dischargesButtonId = fcon.getExternalContext().getRequestParameterMap().get("viewDischargeBID");

        String monthIntString = Constants.MONTHS_OF_YEAR_MAP.get(this.msb.getSelectedMonth());
        if (monthIntString == null) {
            fcon.addMessage(dischargesButtonId, new FacesMessage(FacesMessage.SEVERITY_ERROR, ELResolver.resolve(fcon, "#{mrmp.selectApproMonth}"), ELResolver.resolve(fcon, "#{mrmp.selectApproMonth}")));
            return;
        }
        String yearString = this.msb.getSelectedYear();
        Calendar monthStart = Calendar.getInstance();

        monthStart.set(Integer.parseInt(yearString), Integer.parseInt(monthIntString), 1);
        Date monthStartDate = Utility.createStandardizedDate(monthStart);

        Calendar monthEnd = Calendar.getInstance();
        monthEnd.set(Integer.parseInt(yearString), Integer.parseInt(monthIntString), monthStart.getMaximum(Calendar.MONTH));
        Date monthEndDate = Utility.createStandardizedDate(monthEnd);

        this.discharges = admEJB.findAllDischarges(monthStartDate, monthEndDate);
        if (this.discharges == null) {
            fcon.addMessage(dischargesButtonId, new FacesMessage(FacesMessage.SEVERITY_ERROR, ELResolver.resolve(fcon, "#{mrmp.noAdmForMonth}"), ELResolver.resolve(fcon, "#{mrmp.noAdmForMonth}")));
            return;
        }


    }
}
