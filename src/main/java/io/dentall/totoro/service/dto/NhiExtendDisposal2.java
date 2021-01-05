package io.dentall.totoro.service.dto;

import java.time.LocalDate;

import io.dentall.totoro.domain.NhiExtendDisposal;

/**
 * 因為要設定date和replenishmentDate，而NhiExtendDisposal沒辦法直接設定，又不想去動到NhiExtendDisposal
 * 所以新增此Type
 */
public class NhiExtendDisposal2 extends NhiExtendDisposal {

    private LocalDate date;

    private LocalDate replenishmentDate;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getReplenishmentDate() {
        return replenishmentDate;
    }

    public void setReplenishmentDate(LocalDate replenishmentDate) {
        this.replenishmentDate = replenishmentDate;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
