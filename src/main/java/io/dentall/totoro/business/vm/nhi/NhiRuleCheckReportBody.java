package io.dentall.totoro.business.vm.nhi;

import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public class NhiRuleCheckReportBody {
    List<Long> excludeDisposals;

    public List<Long> getExcludeDisposals() {
        return excludeDisposals;
    }

    public void setExcludeDisposals(List<Long> excludeDisposals) {
        this.excludeDisposals = excludeDisposals;
    }
}
