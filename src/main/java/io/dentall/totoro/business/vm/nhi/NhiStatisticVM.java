package io.dentall.totoro.business.vm.nhi;

import java.util.ArrayList;
import java.util.List;

public class NhiStatisticVM {
    private List<NhiStatisticDashboard> dashboards;

    public List<NhiStatisticDashboard> getDashboards() {
        return dashboards;
    }

    public void setDashboards(List<NhiStatisticDashboard> dashboards) {
        this.dashboards = dashboards;
    }
}
