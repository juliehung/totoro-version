package io.dentall.totoro.service.dto;

import io.dentall.totoro.domain.enumeration.AppointmentStatus;
import io.github.jhipster.service.filter.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Criteria class for the Appointment entity. This class is used in AppointmentResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /appointments?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class NhiExtendDisposalCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer yyyymm;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getYyyymm() {
        return yyyymm;
    }

    public void setYyyymm(Integer yyyymm) {
        this.yyyymm = yyyymm;
    }
}
