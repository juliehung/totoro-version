package io.dentall.totoro.business.service.nhi.metric.filter;

import io.dentall.totoro.business.service.nhi.metric.meta.Meta;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.dentall.totoro.business.service.nhi.metric.filter.FilterKey.Subject;


public class Collector {

    private final Map<String, List<?>> cached = new HashMap<>();

    private final Map<String, Meta> metaMap = new HashMap<>();

    public Collector(List<NhiMetricRawVM> nhiMetricRawVMList) {
        this.cached.put(Subject.input(), nhiMetricRawVMList);
    }

    public <S, T> Collector apply(Source<S, T> source) {
        if (!isSourceExist(source.outputKey())) {
            List<?> filtered = source.doFilter(retrieveSource(source.inputKey()));
            cacheSource(source.outputKey(), filtered);
        }
        return this;
    }

    public <T> List<T> retrieveSource(String name) {
        return (List<T>) this.cached.get(name);
    }

    public void cacheSource(String key, List<?> value) {
        this.cached.put(key, value);
    }

    public boolean isSourceExist(String key) {
        return this.cached.containsKey(key);
    }

    public <T> Meta<T> retrieveMeta(String key) {
        return (Meta<T>)this.metaMap.get(key);
    }

    public void storeMeta(String key, Meta meta) {
        this.metaMap.put(key, meta);
    }

    public boolean isMetaExist(String key) {
        return this.metaMap.containsKey(key);
    }

}
