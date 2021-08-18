package io.dentall.totoro.business.service.nhi.metric.util;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;
import io.dentall.totoro.business.service.nhi.metric.mapper.NhiMetricRawMapper;
import io.dentall.totoro.business.service.nhi.metric.meta.Exclude;
import io.dentall.totoro.business.service.nhi.metric.meta.MetaConfig;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.function.Function;
import java.util.function.Predicate;

import static io.dentall.totoro.business.service.nhi.metric.mapper.NhiMetricRawMapper.INSTANCE;
import static java.util.Optional.ofNullable;

public class NhiMetricHelper {

    private NhiMetricHelper() {
    }

    public static long purgePoint(String code, long examPoint) {
        if ("00305".equals(code) || "00306".equals(code)) {
            return 230L;
        } else if ("00307".equals(code) || "00308".equals(code)) {
            return 120L;
        } else if ("00309".equals(code) || "00310".equals(code)) {
            return 260L;
        } else if ("00311".equals(code)) {
            return 520L;
        } else if ("00312".equals(code)) {
            return 420L;
        } else if ("00313".equals(code)) {
            return 320L;
        } else if ("00314".equals(code)) {
            return 320L;
        } else if ("00315".equals(code)) {
            return 600L;
        } else if ("00316".equals(code)) {
            return 600L;
        } else if ("00317".equals(code)) {
            return 600L;
        }
        return examPoint;
    }

    public Function<NhiMetricRawVM, Long> newmapper(MetaConfig config) {
        if (config.isUseOriginPoint()) {
          return  NhiMetricRawVM::getNhiOriginPoint;
        }

        return  NhiMetricRawVM::getTreatmentProcedureTotal;
    }

    public static Predicate<NhiMetricRawVM> applyExcludeByVM(Exclude exclude) {
        return vm -> ofNullable(exclude).map(exclude1 -> exclude1.test(NhiMetricRawMapper.INSTANCE.mapToExcludeDto(vm))).orElse(true);
    }

    public static Predicate<OdDto> applyExcludeByDto(Exclude exclude) {
        return vm -> ofNullable(exclude).map(exclude1 -> exclude1.test(NhiMetricRawMapper.INSTANCE.mapToExcludeDto(vm))).orElse(true);
    }
}
