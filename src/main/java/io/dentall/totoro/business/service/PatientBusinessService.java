package io.dentall.totoro.business.service;

import io.dentall.totoro.business.dto.TeethGraphConfigDTO;
import io.dentall.totoro.business.vm.PatientSearchVM;
import io.dentall.totoro.business.vm.PatientVM;
import io.dentall.totoro.business.vm.TeethGraphConfigVM;
import io.dentall.totoro.domain.Patient;
import io.dentall.totoro.repository.PatientRepository;
import io.dentall.totoro.repository.TreatmentProcedureRepository;
import io.dentall.totoro.service.PatientService;
import io.dentall.totoro.service.dto.table.PatientTable;
import io.dentall.totoro.service.mapper.PatientDomainMapper;
import io.dentall.totoro.service.mapper.PatientMapper;
import io.dentall.totoro.service.util.ProblemUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.zalando.problem.Status;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.dentall.totoro.service.util.DateTimeUtil.convertLocalDateToBeginOfDayInstant;
import static io.dentall.totoro.service.util.DateTimeUtil.convertLocalDateToEndOfDayInstant;

@Service
public class PatientBusinessService {

    private final Logger log = LoggerFactory.getLogger(PatientBusinessService.class);

    private final PatientRepository patientRepository;

    private final PatientService patientService;

    private final TreatmentProcedureRepository treatmentProcedureRepository;

    public PatientBusinessService(
        PatientRepository patientRepository,
        PatientService patientService,
        TreatmentProcedureRepository treatmentProcedureRepository
    ) {
        this.patientRepository = patientRepository;
        this.patientService = patientService;
        this.treatmentProcedureRepository = treatmentProcedureRepository;
    }

    public Patient validatePatientExistence(Long patientId) {
        Optional<PatientTable> patient = patientRepository.findPatientById(patientId);
        if (!patient.isPresent()) {
            throw new ProblemUtil(
                String.format("Not found patient(id: %s)", patientId),
                Status.NOT_FOUND);
        }

        return PatientMapper.patientTableToPatient(patient.get());
    }

    public TeethGraphConfigVM updateTeethGraphConfig(Patient patient, TeethGraphConfigDTO dto) {
        if (dto.getIsPermanent().size() != 32) {
            throw new ProblemUtil(
                String.format("IsPermanent must have 32 elements"),
                Status.BAD_REQUEST);
        }

        Patient updatePatient = new Patient();
        updatePatient.setId(patient.getId());
        updatePatient.setTeethGraphPermanentSwitch(
            dto.getIsPermanent()
                .stream()
                .collect(Collectors.joining("")));

        patientService.update(updatePatient);

        return getTeethGraphConfig(updatePatient);
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

    public Page<PatientSearchVM> findByBirth(String search, String format, Pageable page) {
        if (format.toUpperCase().equals("CE")) {
            return patientRepository.findByBirthCE(search, page);
        } else if (format.toUpperCase().equals("ROC")) {
            return patientRepository.findByBirthROC(search, page);
        }

        return Page.empty();
    }

    public Page<PatientSearchVM> findByName(String search, Pageable page) {
        return patientRepository.findByName(search.toUpperCase(), page);
    }

    public Page<PatientSearchVM> findByPhone(String search, Pageable page) {
        return patientRepository.findByPhone(search, page);
    }

    public Page<PatientSearchVM> findByNationalId(String search, Pageable page) {
        return patientRepository.findByNationalId(search.toUpperCase(), page);
    }

    public Page<PatientSearchVM> findByMedicalId(String search, Pageable page) {
        String result = search.replaceAll("^0*", "");
        log.debug("search.replaceAll result: {}", result);

        return patientRepository.findByMedicalId(result, page);
    }

    public List<PatientSearchVM> findByRegistration(Instant begin, Instant end) {
        if (begin == null || end == null) {
            begin = convertLocalDateToBeginOfDayInstant(LocalDate.now());
            end = convertLocalDateToEndOfDayInstant(LocalDate.now());
        }

        return patientRepository.findAllByRegistration(begin, end)
            .stream().map(PatientDomainMapper.INSTANCE::mapToSearchVM).collect(Collectors.toList());
    }
}
