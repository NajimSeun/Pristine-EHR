package com.mpit.pristine.persistence.entity;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;

/**
 *
 * @author Hp
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "findByID", query = "select px from Patient px where px.id=:patientID"),
    @NamedQuery(name = "findByName", query = "select px from Patient px where px.name=:name"),
    @NamedQuery(name = "findBySurname", query = "select px from Patient px where px.surname=:surname"),
    @NamedQuery(name = "findByIDAndName", query = "select px from Patient px where px.id=:patientID And px.name=:name"),
    @NamedQuery(name = "findByIDAndSurname", query = "select px from Patient px where px.id=:patientID And px.surname=:surname"),
    @NamedQuery(name = "findByNameAndSurname", query = "select px from Patient px where px.name=:name And px.surname=:surname "),
    @NamedQuery(name = "findByIDNameAndSurname", query = "select px from Patient px where px.id=:patientID And px.name=:name And px.surname=:surname")
})
public class Patient implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private PatientID id;
    private String name;
    private String surname;
    @Column()
    private String middleName;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateOfBirth;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private ContactInformation cinfo;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private PersonalInformation pinfo;
    @Transient
    private int age;
    
    @OneToMany(mappedBy = "patient")
    private Collection<Appointment> appointments ;
    @Transient
    private String vist;  //visit type could be new visit or appointment visit 
    @OneToMany(mappedBy = "patient_Adm")
    private Collection <Admission> admissions ;
    @OneToMany(mappedBy= "mother")
    private Collection<Birth> births  ;
    @PostLoad
    public void calculateAge() {



        GregorianCalendar birthDate = new GregorianCalendar();
        birthDate.setTime(dateOfBirth);
        GregorianCalendar today = new GregorianCalendar();
        
        age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);
        
    }

    public Collection<Birth> getBirths() {
        return births;
    }

    public void setBirths(Collection<Birth> births) {
        this.births = births;
    }

    
    public Collection<Admission> getAdmissions() {
        return admissions;
    }

    public void setAdmissions(Collection<Admission> admissions) {
        this.admissions = admissions;
    }

    
   
    public String getVist() {
        return vist;
    }

    public void setVist(String vist) {
        this.vist = vist;
    }

    public Collection<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(Collection<Appointment> appointments) {
        this.appointments = appointments;
    }

    /**
     * Get the value of age
     *
     * @return the value of age
     */
    public int getAge() {
        return age;
    }

    /**
     * Set the value of age
     *
     * @param age new value of age
     */
    public void setAge(int age) {
        this.age = age;
    }

    public ContactInformation getCinfo() {
        return cinfo;
    }

    public void setCinfo(ContactInformation cinfo) {
        this.cinfo = cinfo;
    }

    public PersonalInformation getPinfo() {
        return pinfo;
    }

    public void setPinfo(PersonalInformation pinfo) {
        this.pinfo = pinfo;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    public PatientID getId() {
        return id;
    }

    public void setId(PatientID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Patient)) {
            return false;
        }
        Patient other = (Patient) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mpit.pristine.persistence.entity.Patient[ id=" + id + " ]";
    }
}
