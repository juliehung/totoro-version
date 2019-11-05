package io.dentall.totoro.business.service;

import io.dentall.totoro.business.dto.TeethGraphConfigDTO;
import io.dentall.totoro.business.vm.PatientVM;
import io.dentall.totoro.business.vm.TeethGraphConfigVM;
import io.dentall.totoro.domain.Patient;
import io.dentall.totoro.repository.PatientRepository;
import io.dentall.totoro.repository.TreatmentProcedureRepository;
import io.dentall.totoro.service.PatientService;
import io.dentall.totoro.service.TreatmentQueryService;
import io.dentall.totoro.service.util.ProblemUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.zalando.problem.Status;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PatientBusinessService {

    private final PatientRepository patientRepository;

    private final PatientService patientService;

    private final TreatmentQueryService treatmentQueryService;

    private final TreatmentProcedureRepository treatmentProcedureRepository;

    public PatientBusinessService(
        PatientRepository patientRepository,
        PatientService patientService,
        TreatmentQueryService treatmentQueryService,
        TreatmentProcedureRepository treatmentProcedureRepository
    ) {
        this.patientRepository = patientRepository;
        this.patientService = patientService;
        this.treatmentQueryService = treatmentQueryService;
        this.treatmentProcedureRepository = treatmentProcedureRepository;
    }

    public Patient validatePatientExistence(Long patientId) {
        Optional<Patient> patient = patientRepository.findById(patientId);
        if (!patient.isPresent()) {
            throw new ProblemUtil(
                String.format("Not found patient(id: %s)", patientId),
                Status.NOT_FOUND);
        }

        return patient.get();
    }

    public TeethGraphConfigVM updateTeethGraphConfig(Patient patient, TeethGraphConfigDTO dto) {
        if (dto.getIsPermanent().size() != 32) {
            throw new ProblemUtil(
                String.format("IsPermanent must have 32 elements"),
                Status.BAD_REQUEST);
        }

        patient.setTeethGraphPermanentSwitch(
            dto.getIsPermanent()
                .stream()
                .collect(Collectors.joining("")));

        patientService.update(patient);

        return getTeethGraphConfig(patient);
    }

    public TeethGraphConfigVM getTeethGraphConfig(Patient patient) {
        if (StringUtils.isBlank(patient.getTeethGraphPermanentSwitch())) {
            throw new ProblemUtil(
                String.format("Not found TeethGrapConfigs in patient(id: %s)", patient.getId()),
                Status.NOT_FOUND);
        }

        TeethGraphConfigVM vm = new TeethGraphConfigVM();

        return vm.isPermanent(
            patient
                .getTeethGraphPermanentSwitch()
                .chars()
                .mapToObj(i -> (char) i)
                .collect(Collectors.toList()));
    }

    public PatientVM findPatientWithFirstVisitDate(Long id) {
        PatientVM patientVM = new PatientVM();

        Optional<Patient> optionalPatient = patientRepository.findById(id);
        if (optionalPatient.isPresent()) {
            patientVM.setPatient(optionalPatient.get());
        }

        patientVM.setFirstVisitDate(treatmentProcedureRepository.findPatientFirstProcedure(id));
        return patientVM;
    }

}
