package io.dentall.totoro.business.service.nhi.util;

import io.dentall.totoro.domain.NhiExtendDisposal;
import io.dentall.totoro.domain.NhiExtendTreatmentProcedure;
import io.dentall.totoro.service.util.StreamUtil;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collector;

import static java.util.stream.Collectors.groupingBy;

public class NhiStatisticUtil {

    public static final Predicate<NhiExtendDisposal> checkCategory14 = nhiExtendDisposal ->
        nhiExtendDisposal.getCategory() != null && nhiExtendDisposal.getCategory().equals("14");

    public static final Predicate<NhiExtendDisposal> checkCategory16 = nhiExtendDisposal ->
        nhiExtendDisposal.getCategory() != null && nhiExtendDisposal.getCategory().equals("16");

    public static final Predicate<NhiExtendDisposal> checkCategoryA3 = nhiExtendDisposal ->
        nhiExtendDisposal.getCategory() != null && nhiExtendDisposal.getCategory().equals("A3");

    public static final Predicate<NhiExtendDisposal> checkCategoryB6 = nhiExtendDisposal ->
        nhiExtendDisposal.getCategory() != null && nhiExtendDisposal.getCategory().equals("B6");

    public static final Predicate<NhiExtendDisposal> checkExaminationPointGreaterThan0 = nhiExtendDisposal ->
        nhiExtendDisposal.getExaminationPoint() != null && nhiExtendDisposal.getExaminationPoint() > 0;

    public static Map<Long, Integer> getPatientTeethCountByCode(List<NhiExtendDisposal> nhiExtendDisposals, String code) {
        return nhiExtendDisposals
            .stream()
            .flatMap(nhiExtendDisposal -> StreamUtil.asStream(nhiExtendDisposal.getNhiExtendTreatmentProcedures()))
            .filter(nhiExtendTreatmentProcedure -> nhiExtendTreatmentProcedure.getA73().equals(code))
            .collect(
                groupingBy(
                    nhiExtendTreatmentProcedure -> nhiExtendTreatmentProcedure.getTreatmentProcedure().getTreatmentTask().getTreatmentPlan().getTreatment().getPatient().getId(),
                    teethCounter
                )
            );
    }

    public static Map<String, Integer> getDoctorTeethCountByCode(List<NhiExtendDisposal> nhiExtendDisposals, String code) {
        return nhiExtendDisposals
            .stream()
            .flatMap(nhiExtendDisposal -> StreamUtil.asStream(nhiExtendDisposal.getNhiExtendTreatmentProcedures()))
            .filter(nhiExtendTreatmentProcedure -> nhiExtendTreatmentProcedure.getA73().equals(code))
            .collect(
                groupingBy(
                    nhiExtendTreatmentProcedure -> nhiExtendTreatmentProcedure.getTreatmentProcedure().getDisposal().getCreatedBy(),
                    teethCounter
                )
            );
    }

    private static Collector<NhiExtendTreatmentProcedure, ?, Integer> teethCounter = Collector.of(
        () -> new int[1],
        (a, t) -> {
            a[0] += t.getA74().split("(?<=\\G.{2})").length;
        },
        (a, b) -> {
            a[0] += b[0];
            return a;
        },
        a -> a[0]
    );
}
