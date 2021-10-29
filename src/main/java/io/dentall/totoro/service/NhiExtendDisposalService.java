package io.dentall.totoro.service;

import io.dentall.totoro.config.TimeConfig;
import io.dentall.totoro.domain.*;
import io.dentall.totoro.domain.enumeration.NhiExtendDisposalUploadStatus;
import io.dentall.totoro.repository.*;
import io.dentall.totoro.service.dto.MonthDisposalDTO;
import io.dentall.totoro.service.dto.table.*;
import io.dentall.totoro.service.mapper.*;
import io.dentall.totoro.service.util.StreamUtil;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.vm.MonthDisposalCollectorVM;
import io.dentall.totoro.web.rest.vm.MonthDisposalVM;
import io.dentall.totoro.web.rest.vm.NhiExtendDisposalVM;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Service Implementation for managing NhiExtendDisposal.
 */
@Service
@Transactional
public class NhiExtendDisposalService {

    private final Logger log = LoggerFactory.getLogger(NhiExtendDisposalService.class);

    private final NhiExtendDisposalRepository nhiExtendDisposalRepository;

    private final RelationshipService relationshipService;

    private final DisposalRepository disposalRepository;

    private final PatientRepository patientRepository;

    private final RegistrationRepository registrationRepository;

    private final AppointmentRepository appointmentRepository;

    private final ExtendUserRepository extendUserRepository;

    private final NhiExtendDisposalMapper nhiExtendDisposalMapper;

    private final NhiExtendTreatmentProcedureRepository nhiExtendTreatmentProcedureRepository;

    private final TreatmentProcedureRepository treatmentProcedureRepository;

    private final TreatmentProcedureMapper treatmentProcedureMapper;

    private final NhiExtendTreatmentProcedureMapper nhiExtendTreatmentProcedureMapper;

    private final NhiExtendTreatmentDrugRepository nhiExtendTreatmentDrugRepository;

    private final NhiExtendTreatmentDrugMapper nhiExtendTreatmentDrugMapper;

    private final PrescriptionRepository prescriptionRepository;

    private final PrescriptionMapper prescriptionMapper;

    private final ToothRepository toothRepository;

    private final ToothMapper toothMapper;

    public NhiExtendDisposalService(
        NhiExtendDisposalRepository nhiExtendDisposalRepository,
        RelationshipService relationshipService,
        DisposalRepository disposalRepository,
        PatientRepository patientRepository,
        RegistrationRepository registrationRepository,
        AppointmentRepository appointmentRepository,
        ExtendUserRepository extendUserRepository,
        NhiExtendDisposalMapper nhiExtendDisposalMapper,
        NhiExtendTreatmentProcedureRepository nhiExtendTreatmentProcedureRepository,
        TreatmentProcedureRepository treatmentProcedureRepository,
        TreatmentProcedureMapper treatmentProcedureMapper,
        NhiExtendTreatmentProcedureMapper nhiExtendTreatmentProcedureMapper,
        NhiExtendTreatmentDrugRepository nhiExtendTreatmentDrugRepository,
        NhiExtendTreatmentDrugMapper nhiExtendTreatmentDrugMapper,
        PrescriptionRepository prescriptionRepository,
        PrescriptionMapper prescriptionMapper,
        ToothRepository toothRepository,
        ToothMapper toothMapper
    ) {
        this.nhiExtendDisposalRepository = nhiExtendDisposalRepository;
        this.relationshipService = relationshipService;
        this.disposalRepository = disposalRepository;
        this.patientRepository = patientRepository;
        this.registrationRepository = registrationRepository;
        this.appointmentRepository = appointmentRepository;
        this.extendUserRepository = extendUserRepository;
        this.nhiExtendDisposalMapper = nhiExtendDisposalMapper;
        this.nhiExtendTreatmentProcedureRepository = nhiExtendTreatmentProcedureRepository;
        this.treatmentProcedureRepository = treatmentProcedureRepository;
        this.treatmentProcedureMapper = treatmentProcedureMapper;
        this.nhiExtendTreatmentProcedureMapper = nhiExtendTreatmentProcedureMapper;
        this.nhiExtendTreatmentDrugRepository = nhiExtendTreatmentDrugRepository;
        this.nhiExtendTreatmentDrugMapper = nhiExtendTreatmentDrugMapper;
        this.prescriptionRepository = prescriptionRepository;
        this.prescriptionMapper = prescriptionMapper;
        this.toothRepository = toothRepository;
        this.toothMapper = toothMapper;
    }

    /**
     * Save a nhiExtendDisposal.
     *
     * @param nhiExtendDisposal the entity to save
     * @return the persisted entity
     */
    public NhiExtendDisposal save(NhiExtendDisposal nhiExtendDisposal) {
        log.debug("Request to save NhiExtendDisposal : {}", nhiExtendDisposal);

        if (nhiExtendDisposal.getUploadStatus() == null) {
            nhiExtendDisposal.setUploadStatus(NhiExtendDisposalUploadStatus.NONE);
        }

        if (nhiExtendDisposal.getDisposal() == null ||
            nhiExtendDisposal.getDisposal().getId() == null
        ) {
            throw new BadRequestAlertException("NhiExtendDisposal must include disposal id",
                "nhiExtendDisposal",
                "require.pk");
        }

        Optional<Disposal> optionalDisposal = disposalRepository.findById(nhiExtendDisposal.getDisposal().getId());

        if (!optionalDisposal.isPresent()) {
            throw new BadRequestAlertException("NhiExtendDisposal must create disposal first",
                "nhiExtendDisposal",
                "require.pk");
        }

        Disposal disposal = optionalDisposal.get();

        if (disposal.getRegistration() == null ||
            disposal.getRegistration().getAppointment() == null ||
            disposal.getRegistration().getAppointment().getPatient() == null ||
            disposal.getRegistration().getAppointment().getPatient().getId() == null
        ) {
            throw new BadRequestAlertException("NhiExtendDisposal related disposal must include full relation chain (disposal.registration.appointment.patient.id)",
                "nhiExtendDisposal",
                "require.essential.data");
        }

        nhiExtendDisposal.setPatientId(disposal.getRegistration().getAppointment().getPatient().getId());
        nhiExtendDisposalRepository.save(nhiExtendDisposal);

        return nhiExtendDisposal;
    }

    /**
     * Get all the nhiExtendDisposalVMs.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<NhiExtendDisposalVM> findAll() {
        log.debug("Request to get all NhiExtendDisposalVMs");

        return nhiExtendDisposalRepository
            .findAll()
            .stream()
            .map(NhiExtendDisposalVM::new)
            .collect(Collectors.toList());
    }

    /**
     * Get the nhiExtendDisposalVMs by date or replenishmentDate.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<NhiExtendDisposalVM> findByDate(LocalDate date) {
        log.debug("Request to get all NhiExtendDisposalVMs by date({})", date);

        return nhiExtendDisposalRepository
            .findNhiExtendDisposalByDate(date)
            .stream()
            .map(nhiExtendDisposalTable -> {
                NhiExtendDisposalVM vm = new NhiExtendDisposalVM();
                Long patientId = nhiExtendDisposalTable.getPatientId();
                Long disposalId = nhiExtendDisposalTable.getDisposal_Id();

                // Assemble patient
                Optional<PatientTable> optionalPatientTable = patientRepository.findPatientById(patientId);
                if (optionalPatientTable.isPresent()) {
                    vm.setMedicalId(optionalPatientTable.get().getMedicalId());
                    vm.setName(optionalPatientTable.get().getName());
                    vm.setVipPatient(optionalPatientTable.get().getVipPatient());
                }

                // Assemble doctor
                Optional<RegistrationTable> optionalRegistrationTable = registrationRepository.findRegistrationByDisposal_Id(disposalId);
                if (optionalRegistrationTable.isPresent()) {
                    Optional<AppointmentTable> optionalAppointmentTable = appointmentRepository.findAppointmentByRegistration_Id(optionalRegistrationTable.get().getId());
                    if (optionalAppointmentTable.isPresent()) {
                        Optional<ExtendUser> optionalExtendUser = extendUserRepository.findById(optionalAppointmentTable.get().getDoctorUser_Id());
                        if (optionalExtendUser.isPresent()) {
                            vm.setDoctor(optionalExtendUser.get().getUser().getFirstName());
                        }
                    }
                }

                // Assemble nhi_ext_disp
                vm.setNhiExtendDisposal(nhiExtendDisposalMapper.nhiExtendDisposalTableToNhiExtendDisposal(nhiExtendDisposalTable));

                return vm;
            })
            .collect(Collectors.toList());
    }

    /**
     * Get the nhiExtendDisposalVMs by yyyymm.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<NhiExtendDisposalVM> findByYearMonth(Integer yyyymm) {
        log.debug("Request to get all NhiExtendDisposalVMs by yyyymm({})", yyyymm);
        YearMonth ym = YearMonth.of(yyyymm / 100, yyyymm % 100);

        return nhiExtendDisposalRepository
            .findByDateBetween(
                ym.atDay(1).atStartOfDay().toInstant(TimeConfig.ZONE_OFF_SET),
                ym.atEndOfMonth().atTime(LocalTime.MAX).toInstant(TimeConfig.ZONE_OFF_SET)
            )
            .stream()
            .map(NhiExtendDisposalVM::new)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<NhiExtendDisposalVM> findByYearMonth(
        Integer yyyymm,
        Pageable pageable
    ) {
        log.debug("Request to get paged NhiExtendDisposalVMs by yyyymm({})", yyyymm);
        YearMonth ym = YearMonth.of(yyyymm / 100, yyyymm % 100);

        return nhiExtendDisposalRepository
            .findNhiMonthContentByDateBetween(
                ym.atDay(1).atStartOfDay().toInstant(TimeConfig.ZONE_OFF_SET),
                ym.atEndOfMonth().atTime(LocalTime.MAX).toInstant(TimeConfig.ZONE_OFF_SET),
                pageable
            )
            .map(nhiExtendDisposalTable -> {
                NhiExtendDisposalVM vm = new NhiExtendDisposalVM();
                Set<NhiExtendTreatmentProcedure> nhiExtendTreatmentProcedures = new HashSet<>();
                Long patientId = nhiExtendDisposalTable.getPatientId();
                Long disposalId = nhiExtendDisposalTable.getDisposal_Id();

                // Assemble patient
                Optional<PatientTable> optionalPatientTable = patientRepository.findPatientById(patientId);
                if (optionalPatientTable.isPresent()) {
                    vm.setMedicalId(optionalPatientTable.get().getMedicalId());
                    vm.setName(optionalPatientTable.get().getName());
                    vm.setVipPatient(optionalPatientTable.get().getVipPatient());
                }

                // Assemble doctor
                Optional<RegistrationTable> optionalRegistrationTable = registrationRepository.findRegistrationByDisposal_Id(disposalId);
                if (optionalRegistrationTable.isPresent()) {
                    Optional<AppointmentTable> optionalAppointmentTable = appointmentRepository.findAppointmentByRegistration_Id(optionalRegistrationTable.get().getId());
                    if (optionalAppointmentTable.isPresent()) {
                        Optional<ExtendUser> optionalExtendUser = extendUserRepository.findById(optionalAppointmentTable.get().getDoctorUser_Id());
                        if (optionalExtendUser.isPresent()) {
                            vm.setDoctor(optionalExtendUser.get().getUser().getFirstName());
                        }
                    }
                }

                // Assemble treatment procedure
                Set<TreatmentProcedure> treatmentProcedures = treatmentProcedureRepository.findTreatmentProceduresByDisposal_Id(nhiExtendDisposalTable.getDisposal_Id()).stream()
                    .map(treatmentProcedureMapper::TreatmentProcedureTableToTreatmentProcedure)
                    .map(treatmentProcedure -> {
                        Set<Tooth> toothSet = toothRepository.findToothByTreatmentProcedure_Id(treatmentProcedure.getId())
                            .stream()
                            .map(toothMapper::toothTableToTooth)
                            .collect(Collectors.toSet());
                        treatmentProcedure.setTeeth(toothSet);
                        if (treatmentProcedure.getNhiExtendTreatmentProcedure() != null &&
                            treatmentProcedure.getNhiExtendTreatmentProcedure().getId() != null &&
                            treatmentProcedure.getNhiProcedure() != null &&
                            treatmentProcedure.getNhiProcedure().getId() != null
                        ) {
                            // Add nhi treament procedure
                            Optional<NhiExtendTreatmentProcedureTable> optionalNhiExtendTreatmentProcedureTable =
                                nhiExtendTreatmentProcedureRepository.
                                    findNhiExtendTreatmentProcedureByTreatmentProcedure_Id(treatmentProcedure.getNhiExtendTreatmentProcedure().getId());
                            if (optionalNhiExtendTreatmentProcedureTable.isPresent()) {
                                NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure =
                                    nhiExtendTreatmentProcedureMapper.nhiExtendTreatmentProcedureTableToNhiExtendTreatmentProcedureTable(
                                        optionalNhiExtendTreatmentProcedureTable.get()
                                    );
                                nhiExtendTreatmentProcedures.add(nhiExtendTreatmentProcedure);
                                treatmentProcedure.setNhiExtendTreatmentProcedure(nhiExtendTreatmentProcedure);
                            }
                        }

                        return treatmentProcedure;
                    })
                    .collect(Collectors.toSet());

                // Assembel througth disposal
                Disposal d = new Disposal().treatmentProcedures(treatmentProcedures);
                d.setId(disposalId);

                // Assemble treatment drug
                Set<NhiExtendTreatmentDrugTable> nhiExtendTreatmentDrugTableSet =
                    nhiExtendTreatmentDrugRepository.findNhiExtendTreatmentDrugsByTreatmentDrug_Prescription_Disposal_Id(disposalId);
                if (!nhiExtendTreatmentDrugTableSet.isEmpty()) {
                    Set<TreatmentDrug> tds = nhiExtendTreatmentDrugTableSet.stream()
                        .map(nhiExtendTreatmentDrugMapper::nhiExtendTreatmentDrugTableToNhiExtendTreatmentDrug)
                        .map(netd -> {
                            TreatmentDrug td = new TreatmentDrug();
                            td.setNhiExtendTreatmentDrug(netd);

                            return td;
                        })
                        .collect(Collectors.toSet());

                    Prescription p = prescriptionRepository.findPrescriptionByDisposal_Id(disposalId)
                        .map(prescriptionMapper::prescriptionTableToPrescription)
                        .filter(Objects::nonNull)
                        .get()
                        .treatmentDrugs(tds);

                    if (p != null) {
                        d.setPrescription(p);
                    }
                }

                // Fit frontend export monthly xml format
                NhiExtendDisposal nhiExtendDisposal = nhiExtendDisposalMapper.nhiExtendDisposalTableToNhiExtendDisposal(nhiExtendDisposalTable);
                nhiExtendDisposal.setNhiExtendTreatmentProcedures(nhiExtendTreatmentProcedures);
                nhiExtendDisposal.setDisposal(d);

                vm.setNhiExtendDisposal(nhiExtendDisposal);

                return vm;
            });
    }

    @Transactional(readOnly = true)
    public List<MonthDisposalCollectorVM> findByYearMonthForLazyNhiExtDis(YearMonth ym) {
        return nhiExtendDisposalRepository.findDisposalIdAndNhiExtendDisposalPrimByDateBetween(
            ym.atDay(1).atStartOfDay().toInstant(TimeConfig.ZONE_OFF_SET),
            ym.atEndOfMonth().atTime(LocalTime.MAX).toInstant(TimeConfig.ZONE_OFF_SET)
        ).stream()
            .filter(d -> d != null &&
                d.getDisposalDateTime() != null &&
                StringUtils.isNotBlank(d.getA18())
            )
            .collect(Collectors.groupingBy(MonthDisposalDTO::getDisposalId))
            .entrySet()
            .stream()
            .map(d -> new MonthDisposalCollectorVM(
                    d.getKey(),
                    d.getValue().stream()
                        .map(MonthDisposalVMMapper.INSTANCE::convertMonthDisposalDTO)
                        .collect(Collectors.toList())
                )
            )
            .collect(Collectors.toList());
    }

    /**
     * Get one nhiExtendDisposal by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<NhiExtendDisposal> findOne(Long id) {
        log.debug("Request to get NhiExtendDisposal : {}", id);
        return nhiExtendDisposalRepository.findById(id);
    }

    /**
     * Delete the nhiExtendDisposal by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete NhiExtendDisposal : {}", id);
        nhiExtendDisposalRepository.deleteById(id);
    }

    /**
     * Update the nhiExtendDisposal.
     *
     * @param updateNhiExtendDisposal the update entity
     */
    public NhiExtendDisposal update(NhiExtendDisposal updateNhiExtendDisposal) {
        log.debug("Request to update NhiExtendDisposal : {}", updateNhiExtendDisposal);

        return nhiExtendDisposalRepository
            .findById(updateNhiExtendDisposal.getId())
            .map(nhiExtendDisposal -> {
                if (updateNhiExtendDisposal.getA11() != null) {
                    nhiExtendDisposal.setA11(updateNhiExtendDisposal.getA11());
                }

                if (updateNhiExtendDisposal.getA12() != null) {
                    nhiExtendDisposal.setA12(updateNhiExtendDisposal.getA12());
                }

                if (updateNhiExtendDisposal.getA13() != null) {
                    nhiExtendDisposal.setA13(updateNhiExtendDisposal.getA13());
                }

                if (updateNhiExtendDisposal.getA14() != null) {
                    nhiExtendDisposal.setA14(updateNhiExtendDisposal.getA14());
                }

                if (updateNhiExtendDisposal.getA15() != null) {
                    nhiExtendDisposal.setA15(updateNhiExtendDisposal.getA15());
                }

                if (updateNhiExtendDisposal.getA16() != null) {
                    nhiExtendDisposal.setA16(updateNhiExtendDisposal.getA16());
                }

                if (updateNhiExtendDisposal.getA17() != null) {
                    nhiExtendDisposal.setA17(updateNhiExtendDisposal.getA17());
                }

                if (updateNhiExtendDisposal.getA18() != null) {
                    nhiExtendDisposal.setA18(updateNhiExtendDisposal.getA18());
                }

                if (updateNhiExtendDisposal.getA19() != null) {
                    nhiExtendDisposal.setA19(updateNhiExtendDisposal.getA19());
                }

                if (updateNhiExtendDisposal.getA22() != null) {
                    nhiExtendDisposal.setA22(updateNhiExtendDisposal.getA22());
                }

                if (updateNhiExtendDisposal.getA23() != null) {
                    nhiExtendDisposal.setA23(updateNhiExtendDisposal.getA23());
                }

                if (updateNhiExtendDisposal.getA25() != null) {
                    nhiExtendDisposal.setA25(updateNhiExtendDisposal.getA25());
                }

                if (updateNhiExtendDisposal.getA26() != null) {
                    nhiExtendDisposal.setA26(updateNhiExtendDisposal.getA26());
                }

                if (updateNhiExtendDisposal.getA27() != null) {
                    nhiExtendDisposal.setA27(updateNhiExtendDisposal.getA27());
                }

                if (updateNhiExtendDisposal.getA31() != null) {
                    nhiExtendDisposal.setA31(updateNhiExtendDisposal.getA31());
                }

                if (updateNhiExtendDisposal.getA32() != null) {
                    nhiExtendDisposal.setA32(updateNhiExtendDisposal.getA32());
                }

                if (updateNhiExtendDisposal.getA41() != null) {
                    nhiExtendDisposal.setA41(updateNhiExtendDisposal.getA41());
                }

                if (updateNhiExtendDisposal.getA42() != null) {
                    nhiExtendDisposal.setA42(updateNhiExtendDisposal.getA42());
                }

                if (updateNhiExtendDisposal.getA43() != null) {
                    nhiExtendDisposal.setA43(updateNhiExtendDisposal.getA43());
                }

                if (updateNhiExtendDisposal.getA44() != null) {
                    nhiExtendDisposal.setA44(updateNhiExtendDisposal.getA44());
                }

                if (updateNhiExtendDisposal.getA54() != null) {
                    nhiExtendDisposal.setA54(updateNhiExtendDisposal.getA54());
                }

                if (updateNhiExtendDisposal.getUploadStatus() != null) {
                    nhiExtendDisposal.setUploadStatus(updateNhiExtendDisposal.getUploadStatus());
                }

                if (updateNhiExtendDisposal.getExaminationCode() != null) {
                    nhiExtendDisposal.setExaminationCode(updateNhiExtendDisposal.getExaminationCode());
                }

                if (updateNhiExtendDisposal.getExaminationPoint() != null) {
                    nhiExtendDisposal.setExaminationPoint(updateNhiExtendDisposal.getExaminationPoint());
                }

                if (updateNhiExtendDisposal.getPatientIdentity() != null) {
                    nhiExtendDisposal.setPatientIdentity(updateNhiExtendDisposal.getPatientIdentity());
                }

                if (updateNhiExtendDisposal.getSerialNumber() != null) {
                    nhiExtendDisposal.setSerialNumber(updateNhiExtendDisposal.getSerialNumber());
                }

                if (updateNhiExtendDisposal.getCategory() != null) {
                    nhiExtendDisposal.setCategory(updateNhiExtendDisposal.getCategory());
                }

                if (updateNhiExtendDisposal.getCheckedMonthDeclaration() != null) {
                    nhiExtendDisposal.setCheckedMonthDeclaration(updateNhiExtendDisposal.getCheckedMonthDeclaration());
                }

                if (updateNhiExtendDisposal.getCheckedAuditing() != null) {
                    nhiExtendDisposal.setCheckedAuditing(updateNhiExtendDisposal.getCheckedAuditing());
                }

                if (updateNhiExtendDisposal.getReferralHospitalCode() != null) {
                    nhiExtendDisposal.setReferralHospitalCode(updateNhiExtendDisposal.getReferralHospitalCode());
                }

                if (updateNhiExtendDisposal.getNhiExtendTreatmentProcedures() != null) {
                    Set<Long> updateIds = updateNhiExtendDisposal.getNhiExtendTreatmentProcedures().stream()
                        .filter(Objects::nonNull)
                        .map(NhiExtendTreatmentProcedure::getId)
                        .collect(Collectors.toSet());
                    StreamUtil.asStream(nhiExtendDisposal.getNhiExtendTreatmentProcedures())
                        .filter(Objects::nonNull)
                        .filter(nhiExtendTreatmentProcedure -> !updateIds.contains(nhiExtendTreatmentProcedure.getId()))
                        .forEach(nhiExtendTreatmentProcedure -> {
                            nhiExtendTreatmentProcedure.getTreatmentProcedure().setDisposal(null);
                            nhiExtendTreatmentProcedure.setNhiExtendDisposal(null);
                        });

                    log.debug("Update nhiExtendTreatmentProcedures({}) of NhiExtendDisposal(id: {})", updateNhiExtendDisposal.getNhiExtendTreatmentProcedures(), updateNhiExtendDisposal.getId());
                    relationshipService.addRelationshipWithNhiExtendTreatmentProcedures(nhiExtendDisposal, updateNhiExtendDisposal.getNhiExtendTreatmentProcedures());
                }

                if (updateNhiExtendDisposal.getNhiExtendTreatmentDrugs() != null) {
                    Set<Long> updateIds = updateNhiExtendDisposal.getNhiExtendTreatmentDrugs().stream().map(NhiExtendTreatmentDrug::getId).collect(Collectors.toSet());
                    StreamUtil.asStream(nhiExtendDisposal.getNhiExtendTreatmentDrugs())
                        .filter(nhiExtendTreatmentDrug -> !updateIds.contains(nhiExtendTreatmentDrug.getId()))
                        .forEach(nhiExtendTreatmentDrug -> {
                            nhiExtendTreatmentDrug.getTreatmentDrug().setPrescription(null);
                            nhiExtendTreatmentDrug.setNhiExtendDisposal(null);
                        });

                    log.debug("Update nhiExtendTreatmentDrugs({}) of NhiExtendDisposal(id: {})", updateNhiExtendDisposal.getNhiExtendTreatmentDrugs(), updateNhiExtendDisposal.getId());
                    relationshipService.addRelationshipWithNhiExtendTreatmentDrugs(nhiExtendDisposal, updateNhiExtendDisposal.getNhiExtendTreatmentDrugs());
                }

                if (updateNhiExtendDisposal.getDependedTreatmentProcedureId() != null) {
                    nhiExtendDisposal.setDependedTreatmentProcedureId(updateNhiExtendDisposal.getDependedTreatmentProcedureId());
                }

                return nhiExtendDisposal;
            })
            .get();
    }

    /**
     * Get the nhiExtendDisposalVMs by patientId.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<NhiExtendDisposalVM> findByPatientId(Long patientId) {
        log.debug("Request to get all NhiExtendDisposalVMs by patientId({})", patientId);

        return nhiExtendDisposalRepository
            .findByPatientId(patientId)
            .stream()
            .map(NhiExtendDisposalVM::new)
            .collect(Collectors.toList());
    }

    @Transactional
    public long count(int yyyymm) {
        log.debug("Request to get all NhiExtendDisposalVMs by yyyymm({})", yyyymm);
        YearMonth ym = YearMonth.of(yyyymm / 100, yyyymm % 100);
        return nhiExtendDisposalRepository.countByDateBetween(ym.atDay(1), ym.atEndOfMonth());
    }

    @Transactional(readOnly = true)
    public Optional<NhiExtendDisposal> getNhiExtendDisposalProjectionByDisposalId(Long id) {
        List<NhiExtendDisposalTable> nhiExtendDisposalTables = nhiExtendDisposalRepository.findNhiExtendDisposalByDisposal_IdOrderById(id);
        return nhiExtendDisposalTables.stream()
            .skip(nhiExtendDisposalTables.size() > 1 ? nhiExtendDisposalTables.size() - 1 : 0)
            .findFirst()
            .map(nhiExtendDisposalMapper::nhiExtendDisposalTableToNhiExtendDisposal);
    }

    @Transactional(readOnly = true)
    public Optional<NhiExtendDisposalSimple> getSimpleByDisposalId(Long id) {
        return nhiExtendDisposalRepository.findByDisposal_IdOrderByIdDesc(id, NhiExtendDisposalSimple.class)
            .stream()
            .findFirst();
    }

    public interface NhiExtendDisposalSimple {

        Long getId();

        String getA17();

        String getA18();

        String getA23();

        String getA54();
    }
}
