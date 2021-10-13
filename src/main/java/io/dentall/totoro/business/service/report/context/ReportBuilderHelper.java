package io.dentall.totoro.business.service.report.context;

import io.dentall.totoro.business.service.report.dto.*;
import io.dentall.totoro.repository.AppointmentRepository;
import io.dentall.totoro.repository.ReportDataRepository;
import io.dentall.totoro.repository.ToothRepository;
import io.dentall.totoro.service.dto.table.AppointmentTable;
import io.dentall.totoro.service.dto.table.ToothTable;

import java.time.Instant;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.*;

public class ReportBuilderHelper {

    public static Map<Long, List<NhiVo>> findSubsequentNhi(LocalDate firstAvailableSubsequentDate, List<Long> patientIds, ReportDataRepository reportDataRepository) {
        return patientIds.isEmpty() ? emptyMap() : reportDataRepository.findNhiVoAfterAndPatient(firstAvailableSubsequentDate, patientIds)
            .stream().collect(groupingBy(NhiVo::getPatientId));
    }

    public static Map<Long, List<OwnExpenseVo>> findSubsequentOwnExpense(LocalDate firstAvailableSubsequentDate, List<Long> patientIds, ReportDataRepository reportDataRepository) {
        return patientIds.isEmpty() ? emptyMap() : reportDataRepository.findOwnExpenseVoAfterAndPatient(firstAvailableSubsequentDate, patientIds)
            .stream().collect(groupingBy(OwnExpenseVo::getPatientId));
    }

    public static Map<Long, List<DrugVo>> findSubsequentDrug(LocalDate firstAvailableSubsequentDate, List<Long> patientIds, ReportDataRepository reportDataRepository) {
        return patientIds.isEmpty() ? emptyMap() : reportDataRepository.findDrugVoAfterAndPatient(firstAvailableSubsequentDate, patientIds)
            .stream().collect(groupingBy(DrugVo::getPatientId));
    }

    public static <T extends DisposalDto> Predicate<T> hasFollowupDisposalInGapMonths(
        LocalDate beginDate, LocalDate endDate, List<Long> patientIds, int gapMonths, ReportDataRepository reportDataRepository) {
        Map<Long, List<DisposalVo>> history =
            patientIds.isEmpty() ? emptyMap() :
                reportDataRepository.findDisposalVoBetweenAndPatient(beginDate, endDate, new ArrayList<>(patientIds))
                    .stream().collect(groupingBy(DisposalVo::getPatientId));
        return (obj) -> {
            // 歷史記錄裡有拔牙日期之後的gapMonths個月內有其他看診記錄的要被排除掉，留下gapMonths月內都沒有回診的
            List<DisposalVo> historyByPatient = history.get(obj.getPatientId());
            if (isNull(historyByPatient) || historyByPatient.isEmpty()) {
                return true;
            }
            LocalDate threeMonthEndDate = obj.getDisposalDate().plusMonths(gapMonths);
            return historyByPatient.stream()
                .filter(historyVo -> historyVo.getDisposalDate().isAfter(obj.getDisposalDate()))
                .noneMatch(historyVo -> historyVo.getDisposalDate().isBefore(threeMonthEndDate) || historyVo.getDisposalDate().isEqual(threeMonthEndDate));
        };
    }

    public static <T extends FutureAppointment> Consumer<T> futureAppointmentList(
        Map<Long, List<FutureAppointmentVo>> futureAppointmentMap, Instant todayBeginTime, AppointmentRepository appointmentRepository) {
        return (obj) -> {
            List<FutureAppointmentVo> futureAppointmentVoList =
                futureAppointmentMap.computeIfAbsent(obj.getPatientId(), (patientId) -> {
                    List<AppointmentTable> appointmentTables =
                        appointmentRepository.findByExpectedArrivalTimeAfterAndPatient_IdAndRegistrationIsNullOrderByExpectedArrivalTime(todayBeginTime, patientId);
                    return ReportMapper.INSTANCE.mapToFutureAppointmentVo(appointmentTables);
                });
            obj.setFutureAppointmentList(futureAppointmentVoList);
        };
    }

    public static <T extends SubsequentDisposal> Consumer<T> availableSubsequentNhiList(Map<Long, List<NhiVo>> subsequentList, int gapMonths) {
        return (obj) -> {
            LocalDate endDate = obj.getDisposalDate().plusMonths(gapMonths);
            List<NhiVo> list = subsequentList.get(obj.getPatientId());
            List<NhiVo> availableSubsequentList = ofNullable(list)
                .map(Collection::stream)
                .map(stream -> stream.filter(subsequent -> subsequent.getDisposalDate().isAfter(endDate)).collect(toList()))
                .orElse(emptyList());
            obj.setSubsequentNhiList(availableSubsequentList);
        };
    }

    public static <T extends SubsequentDisposal> Consumer<T> availableSubsequentOwnExpenseList(
        Map<Long, List<OwnExpenseVo>> subsequentList, int gapMonths, ToothRepository toothRepository) {
        return (obj) -> {
            LocalDate endDate = obj.getDisposalDate().plusMonths(gapMonths);
            List<OwnExpenseVo> list = subsequentList.get(obj.getPatientId());
            List<OwnExpenseVo> availableSubsequentList = ofNullable(list)
                .map(Collection::stream)
                .map(stream -> stream
                    .filter(subsequent -> subsequent.getDisposalDate().isAfter(endDate))
                    .peek(findTooth(toothRepository))
                    .collect(toList()))
                .orElse(emptyList());
            obj.setSubsequentOwnExpenseList(availableSubsequentList);
        };
    }

    public static Consumer<OwnExpenseVo> findTooth(ToothRepository toothRepository) {
        return (obj) -> {
            Set<ToothTable> toothTables = toothRepository.findToothByTreatmentProcedure_Id(obj.getTreatmentProcedureId());
            obj.setProcedureTooth(toothTables.stream().map(ToothTable::getPosition).collect(joining()));
            obj.setProcedureSurface(toothTables.stream().map(ToothTable::getSurface).collect(joining()));
        };
    }

    public static Consumer<SubsequentDisposal> availableSubsequentDrugList(Map<Long, List<DrugVo>> subsequentList, int gapMonths) {
        return (obj) -> {
            LocalDate endDate = obj.getDisposalDate().plusMonths(gapMonths);
            List<DrugVo> list = subsequentList.get(obj.getPatientId());
            List<DrugVo> availableSubsequentList = ofNullable(list)
                .map(Collection::stream)
                .map(stream -> stream.filter(subsequent -> subsequent.getDisposalDate().isAfter(endDate)).collect(toList()))
                .orElse(emptyList());
            obj.setSubsequentDrugList(availableSubsequentList);
        };
    }

}
