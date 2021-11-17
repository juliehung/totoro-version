package io.dentall.totoro.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the Ledger entity. This class is used in LedgerResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /ledgers?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LedgerCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter amount;

    private DoubleFilter charge;

    private DoubleFilter arrears;

    private StringFilter note;

    private LongFilter gid;

    private LongFilter patientId;

    private StringFilter type;

    private InstantFilter date;

    private StringFilter displayName;

    private LongFilter doctorId;

    private StringFilter doctor;

    private StringFilter projectCode;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public DoubleFilter getAmount() {
        return amount;
    }

    public void setAmount(DoubleFilter amount) {
        this.amount = amount;
    }

    public DoubleFilter getCharge() {
        return charge;
    }

    public void setCharge(DoubleFilter charge) {
        this.charge = charge;
    }

    public DoubleFilter getArrears() {
        return arrears;
    }

    public void setArrears(DoubleFilter arrears) {
        this.arrears = arrears;
    }

    public StringFilter getNote() {
        return note;
    }

    public void setNote(StringFilter note) {
        this.note = note;
    }

    public LongFilter getGid() {
        return gid;
    }

    public void setGid(LongFilter gid) {
        this.gid = gid;
    }

    public LongFilter getPatientId() {
        return patientId;
    }

    public void setPatientId(LongFilter patientId) {
        this.patientId = patientId;
    }

    public StringFilter getType() {
        return type;
    }

    public void setType(StringFilter type) {
        this.type = type;
    }

    public InstantFilter getDate() {
        return date;
    }

    public void setDate(InstantFilter date) {
        this.date = date;
    }

    public StringFilter getDisplayName() {
        return displayName;
    }

    public void setDisplayName(StringFilter displayName) {
        this.displayName = displayName;
    }

    public LongFilter getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(LongFilter doctorId) {
        this.doctorId = doctorId;
    }

    public StringFilter getDoctor() {
        return doctor;
    }

    public void setDoctor(StringFilter doctor) {
        this.doctor = doctor;
    }

    public StringFilter getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(StringFilter projectCode) {
        this.projectCode = projectCode;
    }
}
