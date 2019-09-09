package io.dentall.totoro.business.service.nhi;

import io.dentall.totoro.business.vm.nhi.NhiAbnormality;
import io.dentall.totoro.business.vm.nhi.NhiAbnormalityDoctor;
import io.dentall.totoro.business.vm.nhi.NhiAbnormalityPatient;
import io.dentall.totoro.domain.*;
import io.dentall.totoro.repository.NhiExtendDisposalRepository;
import io.dentall.totoro.repository.PatientRepository;
import io.dentall.totoro.repository.UserRepository;
import io.dentall.totoro.service.util.ProblemUtil;
import io.dentall.totoro.service.util.StreamUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
public class NhiAbnormalityService {

    private final NhiExtendDisposalRepository nhiExtendDisposalRepository;

    private final PatientRepository patientRepository;

    private final UserRepository userRepository;

    private final Predicate<NhiExtendTreatmentDrug> checkNhiExtendTreatmentDrug = nhiExtendTreatmentDrug ->
        // 01: 自行調劑, 02: 交付調劑
        nhiExtendTreatmentDrug.getA78().equals("01") || nhiExtendTreatmentDrug.getA78().equals("02");

    private final Function<NhiExtendTreatmentDrug, Double> nhiExtendTreatmentDrugPoint = nhiExtendTreatmentDrug ->
        nhiExtendTreatmentDrug.getTreatmentDrug().getDrug().getPrice() * Double.parseDouble(nhiExtendTreatmentDrug.getA77());

    private final Function<NhiExtendDisposal, LocalDate> nhiExtendDisposalDate = nhiExtendDisposal ->
        nhiExtendDisposal.getReplenishmentDate() == null ? nhiExtendDisposal.getDate() : nhiExtendDisposal.getReplenishmentDate();

    public NhiAbnormalityService(
        NhiExtendDisposalRepository nhiExtendDisposalRepository,
        PatientRepository patientRepository,
        UserRepository userRepository
    ) {
        this.nhiExtendDisposalRepository = nhiExtendDisposalRepository;
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
    }

    public NhiAbnormality getNhiAbnormality(int yyyymm) {
        YearMonth ym = YearMonth.of(yyyymm / 100, yyyymm % 100);
        List<NhiExtendDisposal> nhiExtendDisposals = nhiExtendDisposalRepository
            .findByDateBetweenAndUploadStatusNotNone(
                ym.atDay(1),
                ym.atEndOfMonth()
            );

        NhiAbnormality nhiAbnormality = new NhiAbnormality();

        nhiAbnormality.setFrequentDoctors(getDoctorsByFrequency(nhiExtendDisposals));
        nhiAbnormality.setCode92013cAvgPointDoctors(getDoctorsBy92013cAvgPoint(nhiExtendDisposals));
        nhiAbnormality.setRatioOf90004cTo90015cDoctors(getDoctorsByRatioOf90004cTo90015c(nhiExtendDisposals));
        nhiAbnormality.setCode92003cDoctors(getDoctorsByCode(nhiExtendDisposals, "92003C"));
        nhiAbnormality.setCode92071cDoctors(getDoctorsByCode(nhiExtendDisposals, "92071C"));
        nhiAbnormality.setCode91013cDoctors(getDoctorsByCode(nhiExtendDisposals, "91013C"));

        return nhiAbnormality;
    }

    @Transactional(readOnly = true)
    public List<NhiAbnormalityDoctor> getDoctorsByFrequency(List<NhiExtendDisposal> nhiExtendDisposals) {
        Map<Long, Long> map = nhiExtendDisposals
            .stream()
            .collect(groupingBy(NhiExtendDisposal::getPatientId, Collectors.counting()));
        List<NhiAbnormalityDoctor> doctors = new ArrayList<>();
        map.forEach((id, count) -> {
            Patient patient = getPatient(id);
            NhiAbnormalityDoctor nhiAbnormalityDoctor = doctors
                .stream()
                .filter(doctor -> doctor.getId().equals(patient.getLastDoctor().getId()))
                .findFirst()
                .orElseGet(() -> {
                    NhiAbnormalityDoctor doctor = new NhiAbnormalityDoctor()
                        .id(patient.getLastDoctor().getId());
                    doctor.setPatients(new ArrayList<>());
                    doctors.add(doctor);

                    return doctor;
                });

            NhiAbnormalityPatient nhiAbnormalityPatient = new NhiAbnormalityPatient(patient);
            nhiAbnormalityPatient.setCount(count);
            nhiAbnormalityDoctor.getPatients().add(nhiAbnormalityPatient);
        });

        return doctors;
    }

    @Transactional(readOnly = true)
    public List<NhiAbnormalityDoctor> getDoctorsBy92013cAvgPoint(List<NhiExtendDisposal> nhiExtendDisposals) {
        Map<String, List<NhiExtendTreatmentDrug>> map = getDoctorNhiExtendTreatmentDrugsByCode(nhiExtendDisposals, "92013C");
        List<NhiAbnormalityDoctor> doctors = new ArrayList<>();
        map.forEach((login, nhiExtendTreatmentDrugs) -> {
            double avg = nhiExtendTreatmentDrugs
                .stream()
                .mapToDouble(nhiExtendTreatmentDrugPoint::apply)
                .sum()
                /
                nhiExtendTreatmentDrugs
                    .stream()
                    .map(NhiAbstractMedicalArea::getNhiExtendDisposal)
                    .distinct()
                    .flatMap(nhiExtendTreatmentDisposal -> StreamUtil.asStream(nhiExtendTreatmentDisposal.getNhiExtendTreatmentProcedures()))
                    .filter(nhiExtendTreatmentProcedure -> nhiExtendTreatmentProcedure.getA73().equals("92013C"))
                    .count();

            User user = userRepository.findOneByLogin(login).orElseThrow(() -> ProblemUtil.notFoundException("user"));
            NhiAbnormalityDoctor doctor = new NhiAbnormalityDoctor().id(user.getId()).point(avg);
            doctors.add(doctor);

            Map<AbstractMap.SimpleEntry<Long, LocalDate>, List<NhiExtendDisposal>> patientDateNhiExtendDisposals =
                nhiExtendTreatmentDrugs
                    .stream()
                    .map(NhiExtendTreatmentDrug::getNhiExtendDisposal)
                    .distinct()
                    .collect(groupingBy(
                        nhiExtendDisposal ->
                            new AbstractMap.SimpleEntry<>(nhiExtendDisposal.getPatientId(), nhiExtendDisposalDate.apply(nhiExtendDisposal))
                        )
                    );

            if (patientDateNhiExtendDisposals.size() > 0) {
                List<NhiAbnormalityPatient> patients = new ArrayList<>();
                patientDateNhiExtendDisposals.forEach((patientDate, nhiExtDisposals) -> {
                    Patient patient = getPatient(patientDate.getKey());
                    Double sum = nhiExtDisposals
                        .stream()
                        .flatMap(nhiExtendDisposal -> StreamUtil.asStream(nhiExtendDisposal.getNhiExtendTreatmentDrugs()))
                        .filter(checkNhiExtendTreatmentDrug)
                        .mapToDouble(nhiExtendTreatmentDrugPoint::apply)
                        .sum();

                    patients.add(new NhiAbnormalityPatient(patient).date(patientDate.getValue()).code92013cPoint(sum));
                });

                doctor.setPatients(patients);
            }
        });

        return doctors;
    }

    @Transactional(readOnly = true)
    public List<NhiAbnormalityDoctor> getDoctorsByRatioOf90004cTo90015c(List<NhiExtendDisposal> nhiExtendDisposals) {
        Map<Long, Integer> patientTeethCount = getPatientTeethCountByCode(nhiExtendDisposals, "90015C");
        Map<String, Map<AbstractMap.SimpleEntry<Long, LocalDate>, List<NhiExtendTreatmentProcedure>>> map =
            getDoctorPatientDateNhiExtendTreatmentProceduresByCode(nhiExtendDisposals, "90015C");
        List<NhiAbnormalityDoctor> doctors = new ArrayList<>();
        map.forEach((login, nhiExtTxPsByPatientDate) -> {
            User user = userRepository.findOneByLogin(login).orElseThrow(() -> ProblemUtil.notFoundException("user"));
            nhiExtTxPsByPatientDate.forEach((entry, nhiExtendTreatmentProcedures) -> {
                List<NhiExtendDisposal> nhiExtDisposals = nhiExtendDisposalRepository
                    .findByDateGreaterThanEqualAndPatientIdAndUploadStatusNotNone(entry.getValue().minusDays(30), entry.getKey());

                Set<String> positions = nhiExtendTreatmentProcedures
                    .stream()
                    .flatMap(nhiExtendTreatmentProcedure -> Arrays.stream(nhiExtendTreatmentProcedure.getA74().split("(?<=\\G.{2})")))
                    .collect(Collectors.toSet());

                positions.forEach(position -> {
                    long count = getCountByCodeAndPosition(nhiExtDisposals, "90004C", position);
                    Integer teeth = patientTeethCount.get(entry.getKey());
                    if (teeth != null && teeth > 0) {
                        Patient patient = getPatient(entry.getKey());
                        NhiAbnormalityDoctor nhiAbnormalityDoctor = doctors
                            .stream()
                            .filter(doctor -> doctor.getId().equals(user.getId()))
                            .findFirst()
                            .orElseGet(() -> {
                                NhiAbnormalityDoctor doctor = new NhiAbnormalityDoctor().id(user.getId());
                                doctor.setPatients(new ArrayList<>());
                                doctors.add(doctor);

                                return doctor;
                            });

                        NhiAbnormalityPatient nhiAbnormalityPatient = nhiAbnormalityDoctor
                            .getPatients()
                            .stream()
                            .filter(p ->
                                p.getId().equals(patient.getId()) && p.getDate() == entry.getValue())
                            .findFirst()
                            .orElseGet(() -> {
                                NhiAbnormalityPatient p = new NhiAbnormalityPatient(patient).date(entry.getValue()).ratioOf90004cTo90015c(new HashMap<>());
                                nhiAbnormalityDoctor.getPatients().add(p);

                                return p;
                            });

                        nhiAbnormalityPatient.getRatioOf90004cTo90015c().put(position, count / Double.valueOf(teeth));
                    }
                });
            });
        });

        return doctors;
    }

    @Transactional(readOnly = true)
    public List<NhiAbnormalityDoctor> getDoctorsByCode(List<NhiExtendDisposal> nhiExtendDisposals, String code) {
        Map<String, List<NhiExtendTreatmentProcedure>> map = getDoctorNhiExtendTreatmentProceduresByCode(nhiExtendDisposals, code);
        List<NhiAbnormalityDoctor> doctors = new ArrayList<>();
        map.forEach((login, nhiExtendTreatmentProcedures) -> {
            User user = userRepository.findOneByLogin(login).orElseThrow(() -> ProblemUtil.notFoundException("user"));
            NhiAbnormalityDoctor doctor = new NhiAbnormalityDoctor().id(user.getId()).count(nhiExtendTreatmentProcedures.size());
            doctors.add(doctor);

            List<NhiAbnormalityPatient> patients = nhiExtendTreatmentProcedures
                .stream()
                .map(NhiExtendTreatmentProcedure::getNhiExtendDisposal)
                .distinct()
                .map(nhiExtendDisposal -> {
                    Patient patient = getPatient(nhiExtendDisposal.getPatientId());

                    return new NhiAbnormalityPatient(patient).date(nhiExtendDisposalDate.apply(nhiExtendDisposal));
                })
                .collect(Collectors.toList());

            doctor.setPatients(patients);
        });

        return doctors;
    }

    private Map<String, List<NhiExtendTreatmentDrug>> getDoctorNhiExtendTreatmentDrugsByCode(List<NhiExtendDisposal> nhiExtendDisposals, String code) {
        return nhiExtendDisposals
            .stream()
            .filter(nhiExtendDisposal ->
                nhiExtendDisposal
                    .getNhiExtendTreatmentProcedures()
                    .stream()
                    .anyMatch(nhiExtendTreatmentProcedure -> nhiExtendTreatmentProcedure.getA73().equals(code))
            )
            .flatMap(nhiExtendDisposal -> StreamUtil.asStream(nhiExtendDisposal.getNhiExtendTreatmentDrugs()))
            .filter(checkNhiExtendTreatmentDrug)
            .collect(groupingBy(nhiExtendTreatmentDrug -> nhiExtendTreatmentDrug.getNhiExtendDisposal().getDisposal().getCreatedBy()));
    }

    private Map<Long, Integer> getPatientTeethCountByCode(List<NhiExtendDisposal> nhiExtendDisposals, String code) {
        return nhiExtendDisposals
            .stream()
            .flatMap(nhiExtendDisposal -> StreamUtil.asStream(nhiExtendDisposal.getNhiExtendTreatmentProcedures()))
            .filter(nhiExtendTreatmentProcedure -> nhiExtendTreatmentProcedure.getA73().equals(code))
            .collect(
                groupingBy(
                    nhiExtendTreatmentProcedure -> nhiExtendTreatmentProcedure.getNhiExtendDisposal().getPatientId(),
                    Collector.of(
                        () -> new int[1],
                        (a, t) -> {
                            a[0] += t.getA74().split("(?<=\\G.{2})").length;
                            },
                        (a, b) -> {
                            a[0] += b[0];
                            return a;
                            },
                        a -> a[0]
                    )
                )
            );
    }

    private Map<String, Map<AbstractMap.SimpleEntry<Long, LocalDate>, List<NhiExtendTreatmentProcedure>>> getDoctorPatientDateNhiExtendTreatmentProceduresByCode(
        List<NhiExtendDisposal> nhiExtendDisposals, String code
    ) {
        return nhiExtendDisposals
            .stream()
            .flatMap(nhiExtendDisposal -> StreamUtil.asStream(nhiExtendDisposal.getNhiExtendTreatmentProcedures()))
            .filter(nhiExtendTreatmentProcedure -> nhiExtendTreatmentProcedure.getA73().equals(code))
            .collect(
                groupingBy(
                    nhiExtendTreatmentProcedure -> nhiExtendTreatmentProcedure.getNhiExtendDisposal().getDisposal().getCreatedBy(),
                    groupingBy(nhiExtendTreatmentProcedure ->
                        new AbstractMap.SimpleEntry<>(
                            nhiExtendTreatmentProcedure.getNhiExtendDisposal().getPatientId(),
                            nhiExtendDisposalDate.apply(nhiExtendTreatmentProcedure.getNhiExtendDisposal())
                        )
                    )
                )
            );
    }

    private long getCountByCodeAndPosition(List<NhiExtendDisposal> nhiExtendDisposals, String code, String position) {
        return nhiExtendDisposals
            .stream()
            .flatMap(nhiExtendDisposal -> StreamUtil.asStream(nhiExtendDisposal.getNhiExtendTreatmentProcedures()))
            .filter(nhiExtendTreatmentProcedure ->
                nhiExtendTreatmentProcedure.getA73().equals(code) &&
                    Arrays.asList(
                        nhiExtendTreatmentProcedure.getA74().split("(?<=\\G.{2})")
                    ).contains(position)
            )
            .count();
    }

    private Map<String, List<NhiExtendTreatmentProcedure>> getDoctorNhiExtendTreatmentProceduresByCode(List<NhiExtendDisposal> nhiExtendDisposals, String code) {
        return nhiExtendDisposals
            .stream()
            .flatMap(nhiExtendDisposal -> StreamUtil.asStream(nhiExtendDisposal.getNhiExtendTreatmentProcedures()))
            .filter(nhiExtendTreatmentProcedure -> nhiExtendTreatmentProcedure.getA73().equals(code))
            .collect(groupingBy(nhiExtendTreatmentProcedure -> nhiExtendTreatmentProcedure.getNhiExtendDisposal().getDisposal().getCreatedBy()));
    }

    private Patient getPatient(Long id) {
        return patientRepository.findById(id).orElseThrow(() ->
            ProblemUtil.notFoundException("patient")
        );
    }
}
