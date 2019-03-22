package io.dentall.totoro.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.dentall.totoro.domain.enumeration.RegistrationStatus;
import io.dentall.totoro.domain.enumeration.RegistrationType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the RegistrationDel entity. This class is used in RegistrationDelResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /registration-dels?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RegistrationDelCriteria implements Serializable {
    /**
     * Class for filtering RegistrationStatus
     */
    public static class RegistrationStatusFilter extends Filter<RegistrationStatus> {
    }
    /**
     * Class for filtering RegistrationType
     */
    public static class RegistrationTypeFilter extends Filter<RegistrationType> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private RegistrationStatusFilter status;

    private InstantFilter arrivalTime;

    private RegistrationTypeFilter type;

    private BooleanFilter onSite;

    private LongFilter appointmentId;

    private LongFilter accountingId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public RegistrationStatusFilter getStatus() {
        return status;
    }

    public void setStatus(RegistrationStatusFilter status) {
        this.status = status;
    }

    public InstantFilter getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(InstantFilter arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public RegistrationTypeFilter getType() {
        return type;
    }

    public void setType(RegistrationTypeFilter type) {
        this.type = type;
    }

    public BooleanFilter getOnSite() {
        return onSite;
    }

    public void setOnSite(BooleanFilter onSite) {
        this.onSite = onSite;
    }

    public LongFilter getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(LongFilter appointmentId) {
        this.appointmentId = appointmentId;
    }

    public LongFilter getAccountingId() {
        return accountingId;
    }

    public void setAccountingId(LongFilter accountingId) {
        this.accountingId = accountingId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RegistrationDelCriteria that = (RegistrationDelCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(status, that.status) &&
            Objects.equals(arrivalTime, that.arrivalTime) &&
            Objects.equals(type, that.type) &&
            Objects.equals(onSite, that.onSite) &&
            Objects.equals(appointmentId, that.appointmentId) &&
            Objects.equals(accountingId, that.accountingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        status,
        arrivalTime,
        type,
        onSite,
        appointmentId,
        accountingId
        );
    }

    @Override
    public String toString() {
        return "RegistrationDelCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (arrivalTime != null ? "arrivalTime=" + arrivalTime + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (onSite != null ? "onSite=" + onSite + ", " : "") +
                (appointmentId != null ? "appointmentId=" + appointmentId + ", " : "") +
                (accountingId != null ? "accountingId=" + accountingId + ", " : "") +
            "}";
    }

}
