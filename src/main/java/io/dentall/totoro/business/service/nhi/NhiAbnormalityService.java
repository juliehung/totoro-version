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

import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

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
        nhiAbnormality.setCode91013cDoctors(getDoctorsByCode(nhiExtendDisposals, "92003C", 20));
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
            .collect(Collectors.groupingBy(
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
            .collect(Collectors.groupingBy(
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
