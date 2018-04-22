package com.mpit.pristine.persistence.entity;

import com.mpit.pristine.utility.Constants;
import com.mpit.pristine.utility.Utility;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import javax.persistence.*;
import org.joda.time.Interval;
import org.joda.time.Period;

/**
 *  //Ensure to use postLoad callback to initialize remark appropriately : ie
 * outcome is null remark should be Onadmission , it could also be dischages
 * dead or dama
 *
 * @author najim
 */
//TODO: All query iD should be embeddableID's PID 
@Entity
@NamedQueries({
    @NamedQuery(name = "isOnAdmission", query = "select adm from Admission adm where adm.patient_Adm.id = :pid and adm.remark = :oa"),
    @NamedQuery(name = "findAdmissionsByDate", query = "select adm from Admission adm  where adm.admissionDate = :admDate"),
    @NamedQuery(name = "findAdmissionsByDateRange", query = "select adm from Admission adm  where adm.admissionDate Between :start And :End")
})
public class Admission implements Serializable  {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(length = 400)
    private String id;
    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "admhospname", referencedColumnName = "hospitalname"),
        @JoinColumn(name = "admhospcode", referencedColumnName = "hospitalcode"),
        @JoinColumn(name = "admpid", referencedColumnName = "pid")})
    private Patient patient_Adm;
    @Temporal(TemporalType.DATE)
    private Date admissionDate;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date admissionInstantDate;
    private String department;
    private String unit;
    private String admttingDoctor;
    private String doctorAdmittedUnder;
    private String ward;
    @Transient
    private int lenght;
    @OneToOne
    @JoinColumn(name = "outcome_id")
    private AdmissionOutcome outcome;
    private String remark;
    @Transient
    private String admissionDateString;

    public String getAdmissionDateString() {
        return admissionDateString;
    }

    public void setAdmissionDateString(String admissionDateString) {
        this.admissionDateString = admissionDateString;
    }

    

    public Date getAdmissionInstantDate() {
        return admissionInstantDate;
    }

    public void setAdmissionInstantDate(Date admissionInstantDate) {
        this.admissionInstantDate = admissionInstantDate;
    }

    @PostLoad
    public void admissionPostLoadCallback() {

        Interval admissionInterval;
        Period admissionPeriod;
        int daysOnAdm = 0;
        if (this.outcome != null && !this.remark.equals(Constants.ON_ADMISSION)) {
            admissionInterval = new Interval(this.admissionDate.getTime(), this.outcome.getOutcomeEventDate().getTime());
            admissionPeriod = admissionInterval.toPeriod();
            daysOnAdm = admissionPeriod.getDays();
        } else if (this.outcome == null && this.remark.equals(Constants.ON_ADMISSION)) {
            admissionInterval = new Interval(this.admissionDate.getTime(), new Date().getTime());
            admissionPeriod = admissionInterval.toPeriod();
            daysOnAdm = admissionPeriod.getDays();
        }

        if (daysOnAdm == 0) {
            this.lenght = 1;
        } else {
            this.lenght = daysOnAdm;
        }


        SimpleDateFormat df = new SimpleDateFormat(Constants.DATE_FORMAT_ONE);
        this.setAdmissionDateString(df.format(this.getAdmissionDate()));
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getLenght() {
        return lenght;
    }

    public void setLenght(int lenght) {
        this.lenght = lenght;
    }

    public Date getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(Date admissionDate) {
        Calendar admissionDateCalendar = Calendar.getInstance();
        admissionDateCalendar.setTime(admissionDate);
        this.admissionDate = Utility.createStandardizedDate(admissionDateCalendar);
        this.setAdmissionInstantDate(new Date());
    }

    public String getAdmttingDoctor() {
        return admttingDoctor;
    }

    public void setAdmttingDoctor(String admttingDoctor) {
        this.admttingDoctor = admttingDoctor;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDoctorAdmittedUnder() {
        return doctorAdmittedUnder;
    }

    public void setDoctorAdmittedUnder(String doctorAdmittedUnder) {
        this.doctorAdmittedUnder = doctorAdmittedUnder;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AdmissionOutcome getOutcome() {
        return outcome;
    }

    public void setOutCome(AdmissionOutcome outCome) {
        this.outcome = outcome;
    }

    public Patient getPatient_Adm() {
        return patient_Adm;
    }

    public void setPatient_Adm(Patient patient) {
        this.patient_Adm = patient;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Admission other = (Admission) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @PrePersist
    public void generateId() {
        id = this.patient_Adm.getId().toString() + this.department + Constants.UNDERSCORE + this.unit + Constants.UNDERSCORE + this.ward + Constants.UNDERSCORE + this.admissionDate;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.id);
        hash = 41 * hash + Objects.hashCode(this.admissionDate);
        hash = 41 * hash + Objects.hashCode(this.department);
        hash = 41 * hash + Objects.hashCode(this.unit);
        return hash;
    }

     
    
}
