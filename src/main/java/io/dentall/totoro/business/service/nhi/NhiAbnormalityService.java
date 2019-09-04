package io.dentall.totoro.business.service.nhi;

import io.dentall.totoro.business.vm.nhi.NhiAbnormality;
import io.dentall.totoro.business.vm.nhi.NhiAbnormalityDoctor;
import io.dentall.totoro.business.vm.nhi.NhiAbnormalityPatient;
import io.dentall.totoro.domain.*;
import io.dentall.totoro.domain.enumeration.NhiExtendDisposalUploadStatus;
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
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
public class NhiAbnormalityService {

    private final NhiExtendDisposalRepository nhiExtendDisposalRepository;

    private final PatientRepository patientRepository;

    private final UserRepository userRepository;

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
            .findByDateBetweenAndUploadStatusNot(
                ym.atDay(1),
                ym.atEndOfMonth(),
                NhiExtendDisposalUploadStatus.NONE
            );

        NhiAbnormality nhiAbnormality = new NhiAbnormality();

        nhiAbnormality.setFrequentDoctors(getDoctorsByFrequency(nhiExtendDisposals, 9));
        nhiAbnormality.setRatioOf90004cTo90015cDoctors(
            getDoctorsByRatioOf90004cTo90015c(nhiExtendDisposals, 0.4)
        );
        nhiAbnormality.setCode92003cDoctors(getDoctorsByCode(nhiExtendDisposals, "92003C", 20));
        nhiAbnormality.setCode92071cDoctors(getDoctorsByCode(nhiExtendDisposals, "92071C", 40));
        nhiAbnormality.setCode91013cDoctors(getDoctorsByCode(nhiExtendDisposals, "91013C", 24));

        return nhiAbnormality;
    }

    @Transactional(readOnly = true)
    public List<NhiAbnormalityDoctor> getDoctorsByFrequency(
        List<NhiExtendDisposal> nhiExtendDisposals, int limit
    ) {
        Map<Long, Long> map = nhiExtendDisposals
            .stream()
            .collect(groupingBy(
                NhiExtendDisposal::getPatientId, Collectors.counting()
            ));
        List<NhiAbnormalityDoctor> doctors = new ArrayList<>();
        map.forEach((id, count) -> {
            if (count > limit) {
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
            }
        });

        return doctors;
    }

    @Transactional(readOnly = true)
    public List<NhiAbnormalityDoctor> getDoctorsByRatioOf90004cTo90015c(
        List<NhiExtendDisposal> nhiExtendDisposals, Double limit
    ) {
        Map<Long, Integer> patientTeethCount = getPatientTeethCountByCode(
            nhiExtendDisposals, "90015C"
        );
        Map<
            String,
            Map<AbstractMap.SimpleEntry<Long, LocalDate>,
                List<NhiExtendTreatmentProcedure>
                >
            >
            map = getDoctorPatientDateNhiExtendTreatmentProceduresByCode(
                nhiExtendDisposals, "90015C"
        );
        List<NhiAbnormalityDoctor> doctors = new ArrayList<>();
        map.forEach((login, nhiExtTxPsByPatientDate) -> {
            User user = userRepository.findOneByLogin(login).orElseThrow(() ->
                ProblemUtil.notFoundException("user")
            );
            nhiExtTxPsByPatientDate.forEach((entry, nhiExtendTreatmentProcedures) -> {
                List<NhiExtendDisposal> nhiExtDisposals = nhiExtendDisposalRepository
                    .findByDateGreaterThanEqualAndPatientIdAndUploadStatusNot(
                        entry.getValue().minusDays(30),
                        entry.getKey(),
                        NhiExtendDisposalUploadStatus.NONE
                    );

                Set<String> positions = nhiExtendTreatmentProcedures
                    .stream()
                    .flatMap(nhiExtendTreatmentProcedure ->
                        Arrays.stream(
                            nhiExtendTreatmentProcedure.getA74().split("(?<=\\G.{2})")
                        )
                    )
                    .collect(Collectors.toSet());

                positions.forEach(position -> {
                    long count = getCountByCodeAndPosition(nhiExtDisposals, "90004C", position);
                    if (count == 0) {
                        return;
                    }

                    Integer teeth = patientTeethCount.get(entry.getKey());
                    if (teeth != null && teeth > 0) {
                        Double ratio = count / Double.valueOf(teeth);
                        if (ratio > limit) {
                            Patient patient = getPatient(entry.getKey());
                            NhiAbnormalityDoctor nhiAbnormalityDoctor = doctors
                                .stream()
                                .filter(doctor -> doctor.getId().equals(user.getId()))
                                .findFirst()
                                .orElseGet(() -> {
                                    NhiAbnormalityDoctor doctor = new NhiAbnormalityDoctor()
                                        .id(user.getId());
                                    doctor.setPatients(new ArrayList<>());
                                    doctors.add(doctor);

                                    return doctor;
                                });

                            NhiAbnormalityPatient nhiAbnormalityPatient = nhiAbnormalityDoctor
                                .getPatients()
                                .stream()
                                .filter(p ->
                                    p.getId().equals(patient.getId()) &&
                                        p.getDate() == entry.getValue())
                                .findFirst()
                                .orElseGet(() -> {
                                    NhiAbnormalityPatient p = new
                                        NhiAbnormalityPatient(patient)
                                        .date(entry.getValue())
                                        .ratioOf90004cTo90015c(new HashMap<>());
                                    nhiAbnormalityDoctor.getPatients().add(p);

                                    return p;
                                });

                            nhiAbnormalityPatient
                                .getRatioOf90004cTo90015c()
                                .put(position, ratio);
                        }
                    }
                });
            });
        });

        return doctors;
    }

    @Transactional(readOnly = true)
    public List<NhiAbnormalityDoctor> getDoctorsByCode(
        List<NhiExtendDisposal> nhiExtendDisposals, String code, int limit
    ) {
        Map<String, List<NhiExtendTreatmentProcedure>> map =
            getDoctorNhiExtendTreatmentProceduresByCode(nhiExtendDisposals, code);
        List<NhiAbnormalityDoctor> doctors = new ArrayList<>();
        map.forEach((login, nhiExtendTreatmentProcedures) -> {
            int count = nhiExtendTreatmentProcedures.size();
            if (count > limit) {
                User user = userRepository.findOneByLogin(login).orElseThrow(() ->
                    ProblemUtil.notFoundException("user")
                );
                NhiAbnormalityDoctor doctor = new NhiAbnormalityDoctor()
                    .id(user.getId())
                    .count(count);

                List<NhiAbnormalityPatient> patients = nhiExtendTreatmentProcedures
                    .stream()
                    .map(NhiExtendTreatmentProcedure::getNhiExtendDisposal)
                    .distinct()
                    .map(nhiExtendDisposal -> {
                        Patient patient = getPatient(nhiExtendDisposal.getPatientId());
                        NhiAbnormalityPatient nhiAbnormalityPatient =
                            new NhiAbnormalityPatient(patient);
                        nhiAbnormalityPatient.setDate(nhiExtendDisposal.getDate());

                        return nhiAbnormalityPatient;
                    })
                    .collect(Collectors.toList());

                doctor.setPatients(patients);
                doctors.add(doctor);
            }
        });

        return doctors;
    }

    private Map<Long, Integer> getPatientTeethCountByCode(
        List<NhiExtendDisposal> nhiExtendDisposals, String code
    ) {
        return nhiExtendDisposals
            .stream()
            .flatMap(nhiExtendDisposal ->
                StreamUtil.asStream(nhiExtendDisposal.getNhiExtendTreatmentProcedures())
            )
            .filter(nhiExtendTreatmentProcedure ->
                nhiExtendTreatmentProcedure.getA73().equals(code)
            )
            .collect(groupingBy(
                nhiExtendTreatmentProcedure ->
                nhiExtendTreatmentProcedure.getNhiExtendDisposal().getPatientId(),
                Collector.of(
                    () -> new int[1],
                    (a, t) -> {
                        a[0] += t.getA74().split("(?<=\\G.{2})").length;
                        },
                    (a, b) -> {
                        a[0] += b[0];
                        return a;
                        },
                    a -> a[0])
            ));
    }

    private Map<
        String, Map<AbstractMap.SimpleEntry<Long, LocalDate>, List<NhiExtendTreatmentProcedure>>
        >
    getDoctorPatientDateNhiExtendTreatmentProceduresByCode(
        List<NhiExtendDisposal> nhiExtendDisposals, String code
    ) {
        return nhiExtendDisposals
            .stream()
            .flatMap(nhiExtendDisposal ->
                StreamUtil.asStream(nhiExtendDisposal.getNhiExtendTreatmentProcedures())
            )
            .filter(nhiExtendTreatmentProcedure ->
                nhiExtendTreatmentProcedure.getA73().equals(code)
            )
            .collect(groupingBy(
                nhiExtendTreatmentProcedure ->
                    nhiExtendTreatmentProcedure
                        .getNhiExtendDisposal()
                        .getDisposal()
                        .getCreatedBy(),
                groupingBy(nhiExtendTreatmentProcedure ->
                    new AbstractMap.SimpleEntry<>(
                        nhiExtendTreatmentProcedure.getNhiExtendDisposal().getPatientId(),
                        nhiExtendTreatmentProcedure.getNhiExtendDisposal().getDate()
                    )
                ))
            );
    }

    private long getCountByCodeAndPosition(
        List<NhiExtendDisposal> nhiExtendDisposals, String code, String position
    ) {
        return nhiExtendDisposals
            .stream()
            .flatMap(nhiExtendDisposal ->
                StreamUtil.asStream(nhiExtendDisposal.getNhiExtendTreatmentProcedures())
            )
            .filter(nhiExtendTreatmentProcedure ->
                nhiExtendTreatmentProcedure.getA73().equals(code) &&
                    Arrays.asList(
                        nhiExtendTreatmentProcedure.getA74().split("(?<=\\G.{2})")
                    ).contains(position)
            )
            .count();
    }

    private Map<String, List<NhiExtendTreatmentProcedure>>
    getDoctorNhiExtendTreatmentProceduresByCode(
        List<NhiExtendDisposal> nhiExtendDisposals, String code
    ) {
        return nhiExtendDisposals
            .stream()
            .flatMap(nhiExtendDisposal ->
                StreamUtil.asStream(nhiExtendDisposal.getNhiExtendTreatmentProcedures())
            )
            .filter(nhiExtendTreatmentProcedure ->
                nhiExtendTreatmentProcedure.getA73().equals(code)
            )
            .collect(groupingBy(
                nhiExtendTreatmentProcedure ->
                    nhiExtendTreatmentProcedure
                        .getNhiExtendDisposal()
                        .getDisposal()
                        .getCreatedBy()
                )
            );
    }

    private Patient getPatient(Long id) {
        return patientRepository.findById(id).orElseThrow(() ->
            ProblemUtil.notFoundException("patient")
        );
    }
}
