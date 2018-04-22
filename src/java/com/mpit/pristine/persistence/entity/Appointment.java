package com.mpit.pristine.persistence.entity;

import com.mpit.pristine.utility.Constants;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.*;

/**
 *
 * @author najim
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Appointments", query = "select app from Appointment app where app.dateOfAppointment = :date AND app.hospitalCode = :hcode AND app.department = :dept AND app.unit = :unt"),
    @NamedQuery(name = "allPatientAppointments", query = "select app from Appointment app where app.Patient.id = :patientID"),
    @NamedQuery(name = "deptBasedApp", query = "select app from Appointment app where app.patient.id.ID = :patientID AND app.hospitalCode = :hcode  AND app.department = :dept"),
    @NamedQuery(name = "unitBasedApp", query = "select app from Appointment app where app.patient.id.ID = :patientID AND app.hospitalCode = :hcode  AND app.department = :dept AND app.unit = :unit"),
    @NamedQuery(name = "doesAppointmentExist", query = "select app from Appointment app where app.dateOfAppointment = :date AND app.hospitalCode = :hcode AND app.department = :dept AND app.unit = :unt AND app.patient.id.ID = :patientID")
})
public class Appointment implements Serializable, Comparable<Appointment> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String department;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateOfAppointment;
    private String remark;
    @ManyToOne

    @JoinColumns({
        @JoinColumn(name = "apphospname", referencedColumnName = "hospitalname", nullable = false),
        @JoinColumn(name = "apphospcode", referencedColumnName = "hospitalcode", nullable = false),
        @JoinColumn(name = "apppid", referencedColumnName = "pid", nullable = false)
    })
    private Patient patient;
    private String attendinDoctor;
    private String unit;
    private String hospitalCode;
    @Transient
    private String openStatus ;
    
    public Appointment() {
    }

    public Appointment(String departmnt, Date dateOfAppointment, String remark, Patient patient, String attendinDoctor, String unit, String hospitalCode) {

        this.department = departmnt;
        this.dateOfAppointment = dateOfAppointment;
        this.remark = remark;
        this.patient = patient;
        this.attendinDoctor = attendinDoctor;
        this.unit = unit;
        this.hospitalCode = hospitalCode;
    }

    @PostLoad
    public void postLoadCallback(){
       this.openStatus = Constants.CLOSED_APP_STATUS ;
    }
    public String getOpenStatus() {
        return openStatus;
    }

    public void setOpenStatus(String openStatus) {
        this.openStatus = openStatus;
    }

    
    public String getHospitalCode() {
        return hospitalCode;
    }

    public void setHospitalCode(String hospitalCode) {
        this.hospitalCode = hospitalCode;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAttendinDoctor() {
        return attendinDoctor;
    }

    public void setAttendinDoctor(String attendinDoctor) {
        this.attendinDoctor = attendinDoctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Date getDateOfAppointment() {
        return dateOfAppointment;
    }

    public void setDateOfAppointment(Date dateOfAppointment) {
        this.dateOfAppointment = dateOfAppointment;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Appointment other = (Appointment) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "onetomany.Appointment[ id=" + id + " ]";
    }

    @Override
    public int compareTo(Appointment o) {

        if (this.id.equals(o.id)) {
            return 0;
        }
        Long this_Time = this.dateOfAppointment.getTime();
        Long other_Time = o.dateOfAppointment.getTime();

        int com = this_Time.compareTo(other_Time);
        return (com < 0) ? 1 : ((com == 0) ? 0 : -1);

    }

}
