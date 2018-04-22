package com.mpit.pristine.persistence.entity;

import com.mpit.pristine.utility.Constants;
import com.mpit.pristine.utility.Utility;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import javax.persistence.*;

/**
 *
 * @author najim
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "findBirthsByDateRange", query = "select birth from Birth birth where birth.babyDateOfBirth Between :start And :end"),
    @NamedQuery(name = "findPatientBirthsOrderByDOB", query = "Select birth from Birth birth where birth.mother.id = : pid OrderBy birth.babyDateOfBirth DESC")})
public class Birth implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String babyName;
    @Column(nullable = false)
    private String babySurname;
    @Column(nullable = false)
    private String babySex;
    @Column(nullable = false)
    private double babyWeight;
    private String fatherName;
    private String delieverdBy;
    @Column(nullable = false)
    @Temporal(value = TemporalType.DATE)
    private Date babyDateOfBirth;
    @Column(nullable = false)
    private String modeOfDelivery;
    @Column(nullable = false)
    private int apgarAtOne ;
    @Column(nullable = false)
    private int apgarAtFive ;
    @Column(nullable = false)
    private int apgarAtTen ;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "birthhospname", referencedColumnName = "hospitalname", nullable = false),
        @JoinColumn(name = "bithhospcode", referencedColumnName = "hospitalcode", nullable = false),
        @JoinColumn(name = "motherid", referencedColumnName = "pid", nullable = false)})
    private Patient mother;
    private String gestationType;
    private String delivered;
    @Transient
    private String birthDateString;
    @Transient
    private String birthTimeString;
    @Transient
    private Date babyTimeOfBirth;

    public String getDelivered() {
        return delivered;
    }

    public void setDelivered(String delivered) {
        this.delivered = delivered;
    }

    public String getGestationType() {
        return gestationType;
    }

    public void setGestationType(String gestationType) {
        this.gestationType = gestationType;
    }

     
    @PostLoad
    public void birthPostLoadCallback() {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT_TWO);
        this.birthTimeString = sdf.format(babyDateOfBirth);
        sdf.applyPattern(Constants.DATE_FORMAT_ONE);
        this.birthDateString = sdf.format(babyDateOfBirth);
        this.babyTimeOfBirth = this.babyDateOfBirth;
    }

    public String getBirthDateString() {
        return birthDateString;
    }

    public Date getBabyTimeOfBirth() {
        return babyTimeOfBirth;
    }

    public void setBabyTimeOfBirth(Date babyTimeOfBirth) {
        this.babyTimeOfBirth = babyTimeOfBirth;
    }

    public void setBirthDateString(String birthDateString) {
        this.birthDateString = birthDateString;
    }

    public String getBirthTimeString() {
        return birthTimeString;
    }

    public void setBirthTimeString(String birthTimeString) {
        this.birthTimeString = birthTimeString;
    }

    public Patient getMother() {
        return mother;
    }

    public void setMother(Patient mother) {
        this.mother = mother;
    }

    public String getModeOfDelivery() {
        return modeOfDelivery;
    }

    public void setModeOfDelivery(String modeOfDelivery) {
        this.modeOfDelivery = modeOfDelivery;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getApgarAtFive() {
        return apgarAtFive;
    }

    public void setApgarAtFive(int apgarAtFive) {
        this.apgarAtFive = apgarAtFive;
    }

    public int getApgarAtOne() {
        return apgarAtOne;
    }

    public void setApgarAtOne(int apgarAtOne) {
        this.apgarAtOne = apgarAtOne;
    }

    public int getApgarAtTen() {
        return apgarAtTen;
    }

    public void setApgarAtTen(int apgarAtTen) {
        this.apgarAtTen = apgarAtTen;
    }

    public Date getBabyDateOfBirth() {
        return babyDateOfBirth;
    }

    public void setBabyDateOfBirth(Date babyDateOfBirth) {
        this.babyDateOfBirth = babyDateOfBirth;
    }

    public String getBabyName() {
        return babyName;
    }

    public void setBabyName(String babyName) {
        this.babyName = babyName;
    }

    public String getBabySex() {
        return babySex;
    }

    public void setBabySex(String babySex) {
        this.babySex = babySex;
    }

    public String getBabySurname() {
        return babySurname;
    }

    public void setBabySurname(String babySurname) {
        this.babySurname = babySurname;
    }

    public double getBabyWeight() {
        return babyWeight;
    }

    public void setBabyWeight(double babyWeight) {
        this.babyWeight = babyWeight;
    }

    public String getDelieverdBy() {
        return delieverdBy;
    }

    public void setDelieverdBy(String delieverdBy) {
        this.delieverdBy = delieverdBy;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public void computeBirthTimeString() {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT_TWO);
        this.setBirthTimeString(sdf.format(this.getBabyTimeOfBirth()));
    }

    public void computeActualDateOfBirth() {
        this.setBabyDateOfBirth(Utility.mergeDates(this.babyDateOfBirth, this.babyTimeOfBirth).getTime());
    }
}
