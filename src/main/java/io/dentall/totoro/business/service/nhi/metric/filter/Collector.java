package io.dentall.totoro.business.service.nhi.metric.filter;

import io.dentall.totoro.business.service.nhi.metric.meta.Calculator;
import io.dentall.totoro.business.service.nhi.metric.meta.Meta;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.dentall.totoro.business.service.nhi.metric.filter.FilterKey.Subject;


public class Collector {

    private Map<String, List<NhiMetricRawVM>> cached = new HashMap<>();

    private Map<String, Meta> metaMap = new HashMap<>();

    public Collector(List<NhiMetricRawVM> nhiMetricRawVMList) {
        this.cached.put(Subject.input(), nhiMetricRawVMList);
    }

    public Collector apply(Filter filter) {
        if (!isSourceExist(filter.filterKey().output())) {
            List<NhiMetricRawVM> filtered = filter.doFilter(retrieveSource(filter.filterKey().input()));
            cacheSource(filter.filterKey().output(), filtered);
        }

        return this;
    }

    public Collector apply(Calculator calculator) {
        calculator.calculate(this);
        return this;
    }

    public List<NhiMetricRawVM> retrieveSource(String name) {
        return this.cached.get(name);
    }

    public void cacheSource(String key, List<NhiMetricRawVM> value) {
        this.cached.put(key, value);
    }

    public boolean isSourceExist(String key) {
        return this.cached.containsKey(key);
    }

    public Meta retrieveMeta(String key) {
        return this.metaMap.get(key);
    }

    public void storeMeta(String key, Meta meta) {
        this.metaMap.put(key, meta);
    }

    public boolean isMetaExist(String key) {
        return this.metaMap.containsKey(key);
    }

}
