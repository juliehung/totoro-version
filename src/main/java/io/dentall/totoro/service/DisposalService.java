package io.dentall.totoro.service;

import io.dentall.totoro.config.TimeConfig;
import io.dentall.totoro.domain.*;
import io.dentall.totoro.domain.enumeration.DisposalStatus;
import io.dentall.totoro.domain.enumeration.PlainDisposalType;
import io.dentall.totoro.repository.*;
import io.dentall.totoro.service.dto.PlainDisposalInfoDTO;
import io.dentall.totoro.service.dto.table.*;
import io.dentall.totoro.service.mapper.*;
import io.dentall.totoro.service.util.ProblemUtil;
import io.dentall.totoro.service.util.StreamUtil;
import io.dentall.totoro.web.rest.vm.DisposalV2VM;
import io.dentall.totoro.web.rest.vm.PlainDisposalInfoVM;
import io.dentall.totoro.web.rest.vm.SameTreatmentVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zalando.problem.Status;

import java.time.Instant;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing Disposal.
 */
@Service
@Transactional
public class DisposalService {

    private final Logger log = LoggerFactory.getLogger(DisposalService.class);

    private final DisposalRepository disposalRepository;

    private final PrescriptionService prescriptionService;

    private final TodoService todoService;

    private final RelationshipService relationshipService;

    private final RegistrationService registrationService;

    private final NhiExtendDisposalRepository nhiExtendDisposalRepository;

    private final PrescriptionRepository prescriptionRepository;

    private final TreatmentProcedureRepository treatmentProcedureRepository;

    private final NhiExtendTreatmentProcedureRepository nhiExtendTreatmentProcedureRepository;

    private final TreatmentDrugRepository treatmentDrugRepository;

    private final ToothRepository toothRepository;

    private final RegistrationRepository registrationRepository;

    private final AccountingRepository accountingRepository;

    private final NhiProcedureRepository nhiProcedureRepository;

    private final NhiExtendTreatmentDrugRepository nhiExtendTreatmentDrugRepository;

    private final DrugRepository drugRepository;

    private final TreatmentProcedureMapper treatmentProcedureMapper;

    private final NhiExtendTreatmentProcedureMapper nhiExtendTreatmentProcedureMapper;

    private final ToothMapper toothMapper;

    private final NhiExtendDisposalMapper nhiExtendDisposalMapper;

    private final PrescriptionMapper prescriptionMapper;

    private final TreatmentDrugMapper treatmentDrugMapper;

    private final NhiExtendTreatmentDrugMapper nhiExtendTreatmentDrugMapper;

    private final AppointmentRepository appointmentRepository;

    private final ProcedureRepository procedureRepository;

    private final ProcedureTypeRepository procedureTypeRepository;

    private final ProcedureMapper procedureMapper;

    private final ProcedureTypeMapper procedureTypeMapper;

    public DisposalService(
        DisposalRepository disposalRepository,
        PrescriptionService prescriptionService,
        TodoService todoService,
        RelationshipService relationshipService,
        RegistrationService registrationService,
        NhiExtendDisposalRepository nhiExtendDisposalRepository,
        PrescriptionRepository prescriptionRepository,
        TreatmentProcedureRepository treatmentProcedureRepository,
        NhiExtendTreatmentProcedureRepository nhiExtendTreatmentProcedureRepository,
        TreatmentDrugRepository treatmentDrugRepository,
        ToothRepository toothRepository,
        RegistrationRepository registrationRepository,
        AccountingRepository accountingRepository,
        NhiProcedureRepository nhiProcedureRepository,
        NhiExtendTreatmentDrugRepository nhiExtendTreatmentDrugRepository,
        DrugRepository drugRepository,
        TreatmentProcedureMapper treatmentProcedureMapper,
        NhiExtendTreatmentProcedureMapper nhiExtendTreatmentProcedureMapper,
        ToothMapper toothMapper,
        NhiExtendDisposalMapper nhiExtendDisposalMapper,
        PrescriptionMapper prescriptionMapper,
        TreatmentDrugMapper treatmentDrugMapper,
        NhiExtendTreatmentDrugMapper nhiExtendTreatmentDrugMapper,
        AppointmentRepository appointmentRepository,
        ProcedureRepository procedureRepository,
        ProcedureTypeRepository procedureTypeRepository,
        ProcedureMapper procedureMapper,
        ProcedureTypeMapper procedureTypeMapper
    ) {
        this.disposalRepository = disposalRepository;
        this.prescriptionService = prescriptionService;
        this.todoService = todoService;
        this.relationshipService = relationshipService;
        this.registrationService = registrationService;
        this.nhiExtendTreatmentDrugRepository = nhiExtendTreatmentDrugRepository;
        this.drugRepository = drugRepository;
        this.nhiExtendTreatmentDrugMapper = nhiExtendTreatmentDrugMapper;
        this.nhiExtendDisposalRepository = nhiExtendDisposalRepository;
        this.prescriptionRepository = prescriptionRepository;
        this.treatmentProcedureRepository = treatmentProcedureRepository;
        this.nhiExtendTreatmentProcedureRepository = nhiExtendTreatmentProcedureRepository;
        this.treatmentDrugRepository = treatmentDrugRepository;
        this.toothRepository = toothRepository;
        this.registrationRepository = registrationRepository;
        this.accountingRepository = accountingRepository;
        this.nhiProcedureRepository = nhiProcedureRepository;
        this.treatmentProcedureMapper = treatmentProcedureMapper;
        this.nhiExtendTreatmentProcedureMapper = nhiExtendTreatmentProcedureMapper;
        this.toothMapper = toothMapper;
        this.nhiExtendDisposalMapper = nhiExtendDisposalMapper;
        this.prescriptionMapper = prescriptionMapper;
        this.treatmentDrugMapper = treatmentDrugMapper;
        this.appointmentRepository = appointmentRepository;
        this.procedureRepository = procedureRepository;
        this.procedureTypeRepository = procedureTypeRepository;
        this.procedureMapper = procedureMapper;
        this.procedureTypeMapper = procedureTypeMapper;
    }

    /**
     * Save a disposal.
     *
     * @param disposal the entity to save
     * @return the persisted entity
     */
    public Disposal save(Disposal disposal) {
        log.debug("Request to save Disposal : {}", disposal);

        Prescription prescription = getPrescription(disposal);
        Todo todo = getTodo(disposal);
        Set<TreatmentProcedure> treatmentProcedures = disposal.getTreatmentProcedures();
        Set<Tooth> teeth = disposal.getTeeth();

        disposal = disposalRepository.save(disposal.treatmentProcedures(null).teeth(null).nhiExtendDisposals(null));

        relationshipService.addRelationshipWithTreatmentProcedures(disposal.treatmentProcedures(treatmentProcedures));
        relationshipService.addRelationshipWithTeeth(disposal.teeth(teeth));
        disposal.setPrescription(prescription);
        disposal.setTodo(todo);
        if (disposal.getRegistration() != null && disposal.getRegistration().getId() != null) {
            disposal.setRegistration(registrationService.update(disposal.getRegistration()));

            Patient patient = disposal.getRegistration().getAppointment().getPatient();
            ExtendUser doctor = disposal.getRegistration().getAppointment().getDoctor();

            disposal.setCreatedBy(doctor.getUser().getLogin());
            patient.setLastDoctor(doctor);
            if (patient.getFirstDoctor() == null) {
                patient.setFirstDoctor(doctor);
            }
        }

        return disposal;
    }

    /**
     * Get all the disposals.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Disposal> findAll(Pageable pageable) {
        log.debug("Request to get all Disposals");
        return disposalRepository.findAll(pageable);
    }



    /**
     *  get all the disposals where Prescription is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Disposal> findAllWherePrescriptionIsNull() {
        log.debug("Request to get all disposals where Prescription is null");
        return StreamSupport
            .stream(disposalRepository.findAll().spliterator(), false)
            .filter(disposal -> disposal.getPrescription() == null)
            .collect(Collectors.toList());
    }


    /**
     *  get all the disposals where Todo is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Disposal> findAllWhereTodoIsNull() {
        log.debug("Request to get all disposals where Todo is null");
        return StreamSupport
            .stream(disposalRepository.findAll().spliterator(), false)
            .filter(disposal -> disposal.getTodo() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one disposal by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Disposal> findOne(Long id) {
        log.debug("Request to get Disposal : {}", id);
        return disposalRepository.findById(id);
    }

    /**
     * Delete the disposal by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Disposal : {}", id);

        disposalRepository.findById(id).ifPresent(disposal -> {
            if (disposal.getStatus() != DisposalStatus.TEMPORARY) {
                throw new ProblemUtil("A non-temporary disposal cannot delete", Status.BAD_REQUEST);
            }

            if (disposal.getNhiExtendDisposals() != null && disposal.getNhiExtendDisposals().size() > 0) {
                throw new ProblemUtil("A disposal which has nhiExtendDisposals cannot delete", Status.BAD_REQUEST);
            }

            StreamUtil.asStream(disposal.getTreatmentProcedures()).forEach(treatmentProcedure -> treatmentProcedure.setDisposal(null));
            relationshipService.deleteTreatmentProcedures(disposal.getTreatmentProcedures());

            StreamUtil.asStream(disposal.getTeeth()).forEach(tooth -> tooth.setDisposal(null));
            relationshipService.deleteTeeth(disposal.getTeeth());

            if (disposal.getPrescription() != null && disposal.getPrescription().getId() != null) {
                prescriptionService.delete(disposal.getPrescription().getId());
            }

            if (disposal.getTodo() != null && disposal.getTodo().getId() != null) {
                todoService.delete(disposal.getTodo().getId());
            }

            disposalRepository.deleteById(id);
        });
    }

    /**
     * Get one disposal by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Disposal> findOneWithEagerRelationships(Long id) {
        log.debug("Request to get Disposal : {}", id);
        return disposalRepository.findWithEagerRelationshipsById(id);
    }

    /**
     * Update the disposal.
     *
     * @param updateDisposal the update entity
     */
    public Disposal update(Disposal updateDisposal) {
        log.debug("Request to update Disposal : {}", updateDisposal);

        return disposalRepository
            .findById(updateDisposal.getId())
            .map(disposal -> {
                if (updateDisposal.getStatus() != null) {
                    disposal.setStatus((updateDisposal.getStatus()));
                }

                if (updateDisposal.getTotal() != null) {
                    disposal.setTotal((updateDisposal.getTotal()));
                }

                if (updateDisposal.getDateTime() != null) {
                    disposal.setDateTime(updateDisposal.getDateTime());
                }

                if (updateDisposal.getChiefComplaint() != null) {
                    disposal.setChiefComplaint(updateDisposal.getChiefComplaint());
                }

                if (updateDisposal.getRevisitContent() != null) {
                    disposal.setRevisitContent(updateDisposal.getRevisitContent());
                }

                if (updateDisposal.getRevisitInterval() != null) {
                    disposal.setRevisitInterval(updateDisposal.getRevisitInterval());
                }

                if (updateDisposal.getRevisitTreatmentTime() != null) {
                    disposal.setRevisitTreatmentTime(updateDisposal.getRevisitTreatmentTime());
                }

                if (updateDisposal.getRevisitComment() != null) {
                    disposal.setRevisitComment(updateDisposal.getRevisitComment());
                }

                if (updateDisposal.getRevisitWillNotHappen() != null) {
                    disposal.setRevisitWillNotHappen(updateDisposal.getRevisitWillNotHappen());
                }

                if (updateDisposal.getPrescription() != null) {
                    disposal.setPrescription(getPrescription(updateDisposal));
                }

                if(updateDisposal.getTreatmentProcedureSignatureNotProvided() != null) {
                    disposal.setTreatmentProcedureSignatureNotProvided(updateDisposal.getTreatmentProcedureSignatureNotProvided());
                }

                if (updateDisposal.getTreatmentProcedures() != null || updateDisposal.getTodo() != null) {
                    Set<TreatmentProcedure> originTxPs = null;
                    if (updateDisposal.getTreatmentProcedures() != null) {
                        originTxPs = disposal.getTreatmentProcedures();
                        relationshipService.addRelationshipWithTreatmentProcedures(disposal.treatmentProcedures(updateDisposal.getTreatmentProcedures()));
                        Set<Long> ids = StreamUtil.asStream(disposal.getTreatmentProcedures()).map(TreatmentProcedure::getId).collect(Collectors.toSet());
                        StreamUtil.asStream(originTxPs)
                            .filter(treatmentProcedure -> !ids.contains(treatmentProcedure.getId()))
                            .forEach(treatmentProcedure -> treatmentProcedure.setDisposal(null));
                    }

                    if (updateDisposal.getTodo() != null) {
                        disposal.setTodo(getTodo(updateDisposal));
                    }

                    relationshipService.deleteTreatmentProcedures(
                        StreamUtil.asStream(originTxPs)
                            .filter(TreatmentProcedureService.isDeletable)
                            .collect(Collectors.toSet())
                    );
                }

                if (updateDisposal.getTeeth() != null) {
                    log.debug("Update teeth({}) of Disposal(id: {})", updateDisposal.getTeeth(), updateDisposal.getId());
                    Set<Long> updateIds = updateDisposal.getTeeth().stream().map(Tooth::getId).collect(Collectors.toSet());
                    relationshipService.deleteTeeth(
                        StreamUtil.asStream(disposal.getTeeth())
                            .filter(tooth -> !updateIds.contains(tooth.getId()))
                            .map(tooth -> tooth.disposal(null))
                            .collect(Collectors.toSet())
                    );
                    relationshipService.addRelationshipWithTeeth(disposal.teeth(updateDisposal.getTeeth()));
                }

                if (updateDisposal.getRegistration() != null && updateDisposal.getRegistration().getId() != null) {
                    disposal.setRegistration(registrationService.update(updateDisposal.getRegistration()));
                }

                if (updateDisposal.getDateTimeEnd() != null) {
                    disposal.setDateTimeEnd(updateDisposal.getDateTimeEnd());
                }

                return disposal;
            })
            .get();
    }

    private Prescription getPrescription(Disposal disposal) {
        Prescription prescription = disposal.getPrescription();
        if (prescription != null) {
            prescription = prescription.getId() == null ? prescriptionService.save(prescription) : prescriptionService.update(prescription);
        }

        return prescription;
    }

    private Todo getTodo(Disposal disposal) {
        Todo todo = disposal.getTodo();
        if (todo != null) {
            todo = todo.getId() == null ? todoService.save(todo) : todoService.update(todo);
        }

        return todo;
    }

    public Disposal getDisposalByProjection(Long id) {
        Disposal disposal = getDisposalProjectionById(id).orElse(null);
        if (disposal == null) {
            return null;
        }

        // Init object
        Set<NhiExtendTreatmentProcedure> nhiExtendTreatmentProcedures = new HashSet<>();
        Prescription prescription = new Prescription();

        // Disposal.TreatmentProcedure...
        Set<TreatmentProcedure> treatmentProcedures = treatmentProcedureRepository.findTreatmentProceduresByDisposal_Id(id).stream()
            .map(treatmentProcedureMapper::TreatmentProcedureTableToTreatmentProcedure)
            .map(treatmentProcedure -> {
                // Nhi procedure
                if (treatmentProcedure.getNhiExtendTreatmentProcedure() != null &&
                    treatmentProcedure.getNhiExtendTreatmentProcedure().getId() != null &&
                    treatmentProcedure.getNhiProcedure() != null &&
                    treatmentProcedure.getNhiProcedure().getId() != null
                ) {
                    // TreatmentProcedure.nhiProcedure
                    Optional<NhiProcedure> optionalNhiProcedure = nhiProcedureRepository.findById(treatmentProcedure.getNhiProcedure().getId());
                    if (optionalNhiProcedure.isPresent()) {
                        treatmentProcedure.setNhiProcedure(optionalNhiProcedure.get());
                    }

                    // TreatmentProcedure.nhiTreatmentProcedure
                    Optional<NhiExtendTreatmentProcedureTable> optionalNhiExtendTreatmentProcedureTable =
                        nhiExtendTreatmentProcedureRepository.findNhiExtendTreatmentProcedureByTreatmentProcedure_Id(treatmentProcedure.getId());
                    if (optionalNhiExtendTreatmentProcedureTable.isPresent()) {
                        NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure =
                            nhiExtendTreatmentProcedureMapper.nhiExtendTreatmentProcedureTableToNhiExtendTreatmentProcedureTable(
                                optionalNhiExtendTreatmentProcedureTable.get()
                            );
                        nhiExtendTreatmentProcedures.add(nhiExtendTreatmentProcedure);
                        nhiExtendTreatmentProcedure.setTreatmentProcedure(treatmentProcedure);
                        treatmentProcedure.setNhiExtendTreatmentProcedure(nhiExtendTreatmentProcedure);
                    }

                    // Add tooth
                    treatmentProcedure.setTeeth(toothMapper.toothSetToToothSet(toothRepository.findToothByTreatmentProcedure_Id(treatmentProcedure.getId())));
                }
                // None Nhi procedure
                else if (treatmentProcedure.getProcedure() != null &&
                    treatmentProcedure.getProcedure().getId() != null
                ) {
                    // TreatmentProcedure.Procedure
                    Optional<ProcedureTable> optionalProcedureTable = procedureRepository.findProcedureById(treatmentProcedure.getProcedure().getId());
                    if (optionalProcedureTable.isPresent()) {
                        Procedure procedure = procedureMapper.procedureTableToProcedure(optionalProcedureTable.get());
                        treatmentProcedure.setProcedure(procedure);
                        if (procedure.getProcedureType() != null &&
                            procedure.getProcedureType().getId() != null
                        ) {
                            procedureTypeRepository
                                .findProcedureTypeById(procedure.getProcedureType().getId())
                                .ifPresent(procedureTypeTable -> procedure.setProcedureType(procedureTypeMapper.procedureTypeTableToProcedureType(procedureTypeTable)));
                        }
                    }

                    // Add tooth
                    treatmentProcedure.setTeeth(toothMapper.toothSetToToothSet(toothRepository.findToothByTreatmentProcedure_Id(treatmentProcedure.getId())));
                }
                treatmentProcedure.setDisposal(disposal);

                return treatmentProcedure;
            })
            .collect(Collectors.toSet());

        // Disposal.Registration
        Registration registration = null;
        Optional<RegistrationTable> optionalRegistrationTable = registrationRepository.findRegistrationByDisposal_Id(id);
        if (optionalRegistrationTable.isPresent()) {
            // Registration.Accounting
            registration = RegistrationMapper.registrationTableToRegistration(optionalRegistrationTable.get());
            if (registration.getAccounting() != null &&
                registration.getAccounting().getId() != null
            ) {
                Optional<AccountingTable> optionalAccountingTable = accountingRepository.findAccountingById(registration.getAccounting().getId());
                if (optionalAccountingTable.isPresent()) {
                    registration.setAccounting(AccountingMapper.accountingTableToAccounting(optionalAccountingTable.get()));
                    registration.setDisposal(disposal);
                }

            }
            // Registration.Appointment
            Optional<AppointmentTable> optionalAppointmentTable = appointmentRepository.findAppointmentByRegistration_Id(registration.getId());
            if (optionalAppointmentTable.isPresent()) {
                registration.setAppointment(AppointmentMapper.appointmentTableToAppointment(optionalAppointmentTable.get()));
                // no appointment.registration because of FE required not seeing this data in vm.
            }
        }

        // Disposal.Prescription
        Optional<PrescriptionTable> optionalPrescriptionTable = prescriptionRepository.findPrescriptionById(disposal.getPrescription().getId());
        if (optionalPrescriptionTable.isPresent()) {
            prescription = prescriptionMapper.prescriptionTableToPrescription(optionalPrescriptionTable.get());
            Set<TreatmentDrug> treatmentDrugs =
                treatmentDrugRepository.findTreatmentDrugByPrescription_Id(disposal.getPrescription().getId())
                    .stream()
                    .map(treatmentDrugMapper::treatmentDrugTableToTreatmentDrug)
                    .collect(Collectors.toSet());
            treatmentDrugs.forEach(treatmentDrug -> {
                // Disposal.Prescription.TreatmentDrug.NhiExtendTreatmentDrug
                if (treatmentDrug.getId() != null) {
                    Optional<NhiExtendTreatmentDrugTable> optionalNhiExtendTreatmentDrug =
                        nhiExtendTreatmentDrugRepository.findNhiExtendTreatmentDrugByTreatmentDrug_Id(treatmentDrug.getId());
                    if (optionalNhiExtendTreatmentDrug.isPresent()) {
                        treatmentDrug.setNhiExtendTreatmentDrug(
                            nhiExtendTreatmentDrugMapper.nhiExtendTreatmentDrugTableToNhiExtendTreatmentDrug(optionalNhiExtendTreatmentDrug.get()));
                    }
                }
                // Disposal.Prescription.TreatmentDrug.Drug
                if (treatmentDrug.getDrug() != null &&
                    treatmentDrug.getDrug().getId() != null
                ) {
                   Optional<Drug> optionalDrug = drugRepository.findById(treatmentDrug.getDrug().getId());
                   if (optionalDrug.isPresent()) {
                       treatmentDrug.setDrug(optionalDrug.get());
                   }
                }
            });
            prescription.treatmentDrugs(treatmentDrugs);
            disposal.setPrescription(prescription);
        }

        // Disposal.NhiExtendDisposal
        List<NhiExtendDisposalTable> nhiExtendDisposalTables = nhiExtendDisposalRepository.findNhiExtendDisposalByDisposal_IdOrderById(id);
        Set<NhiExtendDisposal> nhiExtendDisposals = new HashSet<>();
        if (nhiExtendDisposalTables.size() > 0) {
            NhiExtendDisposal nhiExtendDisposal =
                nhiExtendDisposalMapper.nhiExtendDisposalTableToNhiExtendDisposal(nhiExtendDisposalTables.get(nhiExtendDisposalTables.size() - 1));
            nhiExtendDisposal.setDisposal(disposal);
            nhiExtendDisposals.add(nhiExtendDisposal);
            nhiExtendDisposal.setNhiExtendTreatmentProcedures(nhiExtendTreatmentProcedures);
            nhiExtendTreatmentProcedures.forEach(nhiExtendTreatmentProcedure -> {
                nhiExtendTreatmentProcedure.setNhiExtendDisposal(nhiExtendDisposal);
            });
        }

        // Assemble query result
        disposal.treatmentProcedures(treatmentProcedures)
            .registration(registration)
            .nhiExtendDisposals(nhiExtendDisposals)
            .prescription(prescription);

        return disposal;
    }

    @Transactional(readOnly = true)
    public Optional<Disposal> getDisposalProjectionById(Long id) {
        return disposalRepository.findDisposalById(id)
            .map(DisposalMapper::disposalTableToDisposal);
    }

    @Transactional(readOnly = true)
    public Page<DisposalV2VM> getDisposalProjectionByPatientId(Long patientId, Pageable page) {
        Page<DisposalTable> disposalTablePage = disposalRepository.findDisposalByRegistration_Appointment_Patient_Id(patientId, page);
        List<DisposalV2VM> disposals = disposalTablePage
            .stream()
            .map(disposalTable -> {
                Disposal d = this.getDisposalByProjection(disposalTable.getId());
                DisposalV2VM vm = new DisposalV2VM();
                vm.setDisposal(d);
                if (d != null &&
                    d.getRegistration() != null &&
                    d.getRegistration().getAppointment() != null &&
                    d.getRegistration().getAppointment().getDoctor() != null &&
                    d.getRegistration().getAppointment().getDoctor().getId() != null
                ) {
                    vm.setDoctorId(d.getRegistration().getAppointment().getDoctor().getId());
                }
                return vm;
            })
            .collect(Collectors.toList());

        return  new PageImpl<>(disposals, page, disposalTablePage.getTotalElements());
    }

    @Transactional(readOnly = true)
    public Disposal getSimpleDisposalProjectionById(Long id) {
        Disposal disposal = getDisposalProjectionById(id).orElse(null);
        if (disposal == null) {
            return null;
        }

        Set<TreatmentProcedure> treatmentProcedures = treatmentProcedureRepository.findByDisposal_Id(disposal.getId(), DisposalTreatmentProcedure.class)
            .stream()
            .map(disposalTreatmentProcedure -> {
                TreatmentProcedure treatmentProcedure = new TreatmentProcedure();
                treatmentProcedure.setId(disposalTreatmentProcedure.getId());

                return treatmentProcedure;
            })
            .collect(Collectors.toSet());

        List<NhiExtendDisposalTable> nhiExtendDisposalTables = nhiExtendDisposalRepository.findNhiExtendDisposalByDisposal_IdOrderById(disposal.getId());
        Set<NhiExtendDisposal> nhiExtendDisposals = new HashSet<>();
        if (nhiExtendDisposalTables.size() > 0) {
            NhiExtendDisposal nhiExtendDisposal =
                nhiExtendDisposalMapper.nhiExtendDisposalTableToNhiExtendDisposal(nhiExtendDisposalTables.get(nhiExtendDisposalTables.size() - 1));
            nhiExtendDisposals.add(nhiExtendDisposal);
        }

        return disposal.treatmentProcedures(treatmentProcedures).nhiExtendDisposals(nhiExtendDisposals);
    }

    public interface DisposalTreatmentProcedure {

        Long getId();
    }

    public Page<SameTreatmentVM> findSameTreatment(Long patientId, Instant begin, Instant end, Pageable pageable) {
        return disposalRepository.findByRegistration_Appointment_Patient_IdAndDateTimeBetween(patientId, begin, end, pageable);
    }

    public Page<PlainDisposalInfoVM> findPlainDisposalInfo(
        PlainDisposalType plainDisposalType,
        Instant begin,
        Instant end,
        List<Long> doctorIds,
        Long infoId,
        Pageable page
    ) {
        Page<PlainDisposalInfoDTO> p = new PageImpl<>(new ArrayList<>(), page, 0);
        List<PlainDisposalInfoVM> rvm;
        Instant todayBeginTime = LocalDate.now().atStartOfDay(TimeConfig.ZONE_OFF_SET).toInstant();

        switch (plainDisposalType) {
            case NHI_TX:
                p = treatmentProcedureRepository.findPlainDisposalNhiTx(begin, end, doctorIds, infoId, page);
                break;
            case NONE_NHI_TX:
                p = treatmentProcedureRepository.findPlainDisposalNoneNhiTx(begin, end, doctorIds, infoId, page);
                break;
            case DRUG:
                p = treatmentDrugRepository.findPlainDisposalDrug(begin, end, doctorIds, infoId, page);
                break;
        }

        rvm = p.getContent().stream()
            .map(dto -> {
                PlainDisposalInfoVM vm = new PlainDisposalInfoVM();
                vm.setArrivalTime(dto.getArrivalTime());
                vm.setDoctorName(dto.getDoctorName());
                vm.setPatientId(dto.getPatientId());
                vm.setPatientName(dto.getPatientName());
                vm.setNote(dto.getNote());
                vm.setPhone(dto.getPhone());
                vm.setTargetInfo(dto.getTargetInfo());
                vm.setPatientBirth(dto.getPatientBirth());
                vm.setTargetInfo(dto.getTargetInfo());

                vm.setFutureAppointment(
                    appointmentRepository
                        .findByExpectedArrivalTimeAfterAndPatient_IdAndRegistrationIsNullOrderByExpectedArrivalTime(todayBeginTime, dto.getPatientId()).stream()
                        .map(AppointmentTable::getExpectedArrivalTime)
                        .collect(Collectors.toList())
                );
                return vm;
            })
            .collect(Collectors.toList());

        return new PageImpl<>(rvm, p.getPageable(), p.getTotalElements());
    }
}
