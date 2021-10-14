package io.dentall.totoro.business.service.nhi.metric.vm;

import io.dentall.totoro.business.service.nhi.metric.dto.DisposalSummaryDto;
import io.dentall.totoro.business.service.nhi.metric.dto.DoctorSummaryDto;

import java.util.List;

public class Section18 {

    private int count;

    private long total;

    List<DoctorSummaryDto> doctorSummary;

    List<DisposalSummaryDto> disposalSummary;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<DoctorSummaryDto> getDoctorSummary() {
        return doctorSummary;
    }

    public void setDoctorSummary(List<DoctorSummaryDto> doctorSummary) {
        this.doctorSummary = doctorSummary;
    }

    public List<DisposalSummaryDto> getDisposalSummary() {
        return disposalSummary;
    }

    public void setDisposalSummary(List<DisposalSummaryDto> disposalSummary) {
        this.disposalSummary = disposalSummary;
    }
}
