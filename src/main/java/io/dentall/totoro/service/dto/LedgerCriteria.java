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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LedgerCriteria that = (LedgerCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(charge, that.charge) &&
            Objects.equals(arrears, that.arrears);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        amount,
        charge,
        arrears
        );
    }

    @Override
    public String toString() {
        return "LedgerCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (amount != null ? "amount=" + amount + ", " : "") +
                (charge != null ? "charge=" + charge + ", " : "") +
                (arrears != null ? "arrears=" + arrears + ", " : "") +
            "}";
    }

}
