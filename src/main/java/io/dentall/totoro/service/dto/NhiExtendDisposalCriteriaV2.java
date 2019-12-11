package io.dentall.totoro.service.dto;

import io.github.jhipster.service.filter.Filter;

import java.io.Serializable;

/**
 * Criteria class for the Appointment entity. This class is used in AppointmentResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /appointments?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class NhiExtendDisposalCriteriaV2 implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
