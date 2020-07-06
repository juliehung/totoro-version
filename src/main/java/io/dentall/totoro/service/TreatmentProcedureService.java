package io.dentall.totoro.service;

import io.dentall.totoro.domain.*;
import io.dentall.totoro.repository.*;
import io.dentall.totoro.service.dto.table.AppointmentTable;
import io.dentall.totoro.service.dto.table.DisposalTable;
import io.dentall.totoro.service.dto.table.NhiExtendTreatmentProcedureTable;
import io.dentall.totoro.service.dto.table.ProcedureTable;
import io.dentall.totoro.service.mapper.*;
import io.dentall.totoro.service.util.StreamUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing TreatmentProcedure.
 */
@Service
@Transactional
public class TreatmentProcedureService {

    private final Logger log = LoggerFactory.getLogger(TreatmentProcedureService.class);

    private final TreatmentProcedureRepository treatmentProcedureRepository;

    private final ProcedureRepository procedureRepository;

    private final AppointmentRepository appointmentRepository;

    private final RelationshipService relationshipService;

    private final TreatmentTaskRepository treatmentTaskRepository;

    private final DisposalRepository disposalRepository;

    private final NhiProcedureRepository nhiProcedureRepository;

    private final NhiExtendTreatmentProcedureService nhiExtendTreatmentProcedureService;

    private final TreatmentProcedureMapper treatmentProcedureMapper;

    private final NhiProcedureMapper nhiProcedureMapper;

    private final ProcedureMapper procedureMapper;

    private final ToothMapper toothMapper;

    private final NhiExtendTreatmentProcedureMapper nhiExtendTreatmentProcedureMapper;

    private final ExtendUserRepository extendUserRepository;

    private final NhiExtendTreatmentProcedureRepository nhiExtendTreatmentProcedureRepository;

    private final ToothRepository toothRepository;

    static Predicate<TreatmentProcedure> isDeletable = treatmentProcedure -> {
        if (treatmentProcedure.getProcedure() == null && treatmentProcedure.getNhiProcedure() == null) {
            return true;
        }

        if (treatmentProcedure.getProcedure() != null && StringUtils.isBlank(treatmentProcedure.getProcedure().getContent())) {
            return true;
        }

        if (treatmentProcedure.getNhiProcedure() != null && StringUtils.isBlank(treatmentProcedure.getNhiProcedure().getCode())) {
            return true;
        }

        return false;
    };

    public TreatmentProcedureService(
        TreatmentProcedureRepository treatmentProcedureRepository,
        ProcedureRepository procedureRepository,
        AppointmentRepository appointmentRepository,
        RelationshipService relationshipService,
        TreatmentTaskRepository treatmentTaskRepository,
        DisposalRepository disposalRepository,
        NhiProcedureRepository nhiProcedureRepository,
        NhiExtendTreatmentProcedureService nhiExtendTreatmentProcedureService,
        TreatmentProcedureMapper treatmentProcedureMapper,
        NhiProcedureMapper nhiProcedureMapper,
        ProcedureMapper procedureMapper,
        ToothMapper toothMapper,
        NhiExtendTreatmentProcedureMapper nhiExtendTreatmentProcedureMapper,
        ExtendUserRepository extendUserRepository,
        NhiExtendTreatmentProcedureRepository nhiExtendTreatmentProcedureRepository,
        ToothRepository toothRepository) {
        this.treatmentProcedureRepository = treatmentProcedureRepository;
        this.procedureRepository = procedureRepository;
        this.appointmentRepository = appointmentRepository;
        this.relationshipService = relationshipService;
        this.treatmentTaskRepository = treatmentTaskRepository;
        this.disposalRepository = disposalRepository;
        this.nhiProcedureRepository = nhiProcedureRepository;
        this.nhiExtendTreatmentProcedureService = nhiExtendTreatmentProcedureService;
        this.treatmentProcedureMapper = treatmentProcedureMapper;
        this.nhiProcedureMapper = nhiProcedureMapper;
        this.procedureMapper = procedureMapper;
        this.toothMapper = toothMapper;
        this.nhiExtendTreatmentProcedureMapper = nhiExtendTreatmentProcedureMapper;
        this.extendUserRepository = extendUserRepository;
        this.nhiExtendTreatmentProcedureRepository = nhiExtendTreatmentProcedureRepository;
        this.toothRepository = toothRepository;
    }

    /**
     * Save a treatmentProcedure.
     *
     * @param treatmentProcedure the entity to save
     * @return the persisted entity
     */
    public TreatmentProcedure save(TreatmentProcedure treatmentProcedure) {
        log.debug("Request to save TreatmentProcedure : {}", treatmentProcedure);

        NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure = treatmentProcedure.getNhiExtendTreatmentProcedure();
        Set<Tooth> teeth = treatmentProcedure.getTeeth();
        Instant createdDate = treatmentProcedure.getCreatedDate();
        Set<Todo> todos = treatmentProcedure.getTodos();
        TreatmentProcedure txP = treatmentProcedureRepository.save(treatmentProcedure.teeth(null).nhiExtendTreatmentProcedure(null).todos(null));
        relationshipService.addRelationshipWithTeeth(txP.teeth(teeth));
        relationshipService.addRelationshipWithTodos(txP, todos);

        if (nhiExtendTreatmentProcedure != null) {
            txP.setNhiExtendTreatmentProcedure(getNhiExtendTreatmentProcedure(nhiExtendTreatmentProcedure.treatmentProcedure(txP)));
        }

        if (createdDate != null) {
            txP.setCreatedDate(createdDate);
        }

        if (txP.getAppointment() != null && txP.getAppointment().getId() != null) {
            appointmentRepository.findById(txP.getAppointment().getId()).ifPresent(appointment -> appointment.getTreatmentProcedures().add(txP));
        }

        if (txP.getTreatmentTask() != null && txP.getTreatmentTask().getId() != null) {
            treatmentTaskRepository.findById(txP.getTreatmentTask().getId()).ifPresent(treatmentTask -> treatmentTask.getTreatmentProcedures().add(txP));
        }

        if (txP.getDisposal() != null && txP.getDisposal().getId() != null) {
            disposalRepository.findById(txP.getDisposal().getId()).ifPresent(disposal -> disposal.getTreatmentProcedures().add(txP));
        }

        return txP;
    }

    /**
     * Get all the treatmentProcedures.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TreatmentProcedure> findAll(Pageable pageable) {
        log.debug("Request to get all TreatmentProcedures");
        return treatmentProcedureRepository.findAll(pageable);
    }


    /**
     * Get one treatmentProcedure by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<TreatmentProcedure> findOne(Long id) {
        log.debug("Request to get TreatmentProcedure : {}", id);
        return treatmentProcedureRepository.findById(id);
    }

    /**
     * Delete the treatmentProcedure by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TreatmentProcedure : {}", id);

        treatmentProcedureRepository.findById(id).ifPresent(treatmentProcedure -> {
            if (treatmentProcedure.getTodos() != null) {
                treatmentProcedure.getTodos().forEach(todo -> {
                    todo.treatmentProcedures(null);
                });
            }

            if (treatmentProcedure.getAppointment() != null) {
                treatmentProcedure.getAppointment().removeTreatmentProcedure(treatmentProcedure);
            }

            if (treatmentProcedure.getDisposal() != null) {
                treatmentProcedure.getDisposal().removeTreatmentProcedure(treatmentProcedure);
            }

            if (treatmentProcedure.getTreatmentTask() != null) {
                treatmentProcedure.getTreatmentTask().removeTreatmentProcedure(treatmentProcedure);
            }

            treatmentProcedureRepository.deleteById(id);
        });
    }

    /**
     * Update the treatmentProcedure.
     *
     * @param updateTreatmentProcedure the update entity
     * @return the entity
     */
    public TreatmentProcedure update(TreatmentProcedure updateTreatmentProcedure) {
        log.debug("Request to update TreatmentProcedure : {}", updateTreatmentProcedure);

        return treatmentProcedureRepository
            .findById(updateTreatmentProcedure.getId())
            .map(treatmentProcedure -> {
                if (updateTreatmentProcedure.getStatus() != null) {
                    treatmentProcedure.setStatus((updateTreatmentProcedure.getStatus()));
                }

                if (updateTreatmentProcedure.getQuantity() != null) {
                    treatmentProcedure.setQuantity((updateTreatmentProcedure.getQuantity()));
                }

                if (updateTreatmentProcedure.getTotal() != null) {
                    treatmentProcedure.setTotal((updateTreatmentProcedure.getTotal()));
                }

                if (updateTreatmentProcedure.getNote() != null) {
                    treatmentProcedure.setNote((updateTreatmentProcedure.getNote()));
                }

                if (updateTreatmentProcedure.getCompletedDate() != null) {
                    treatmentProcedure.setCompletedDate((updateTreatmentProcedure.getCompletedDate()));
                }

                if (updateTreatmentProcedure.getPrice() != null) {
                    treatmentProcedure.setPrice((updateTreatmentProcedure.getPrice()));
                }

                if (updateTreatmentProcedure.getNhiCategory() != null) {
                    treatmentProcedure.setNhiCategory(updateTreatmentProcedure.getNhiCategory());
                }

                if (updateTreatmentProcedure.getNhiDescription() != null) {
                    treatmentProcedure.setNhiDescription(updateTreatmentProcedure.getNhiDescription());
                }

                if (updateTreatmentProcedure.getNhiIcd10Cm() != null) {
                    treatmentProcedure.setNhiIcd10Cm(updateTreatmentProcedure.getNhiIcd10Cm());
                }

                if (updateTreatmentProcedure.getProcedure() != null && updateTreatmentProcedure.getProcedure().getId() != null) {
                    procedureRepository.findById(updateTreatmentProcedure.getProcedure().getId()).ifPresent(treatmentProcedure::setProcedure);
                }

                if (updateTreatmentProcedure.getNhiProcedure() != null && updateTreatmentProcedure.getNhiProcedure().getId() != null) {
                    nhiProcedureRepository.findById(updateTreatmentProcedure.getNhiProcedure().getId()).ifPresent(treatmentProcedure::setNhiProcedure);
                }

                if (updateTreatmentProcedure.getAppointment() != null && updateTreatmentProcedure.getAppointment().getId() != null) {
                    appointmentRepository.findById(updateTreatmentProcedure.getAppointment().getId()).ifPresent(appointment -> {
                        if (treatmentProcedure.getAppointment() != null && !treatmentProcedure.getAppointment().getId().equals(appointment.getId())) {
                            treatmentProcedure.getAppointment().getTreatmentProcedures().remove(treatmentProcedure);
                        }

                        appointment.getTreatmentProcedures().add(treatmentProcedure.appointment(appointment));
                    });
                }

                if (updateTreatmentProcedure.getTreatmentTask() != null && updateTreatmentProcedure.getTreatmentTask().getId() != null) {
                    treatmentTaskRepository.findById(updateTreatmentProcedure.getTreatmentTask().getId()).ifPresent(treatmentTask -> {
                        if (treatmentProcedure.getTreatmentTask() != null && !treatmentProcedure.getTreatmentTask().getId().equals(treatmentTask.getId())) {
                            treatmentProcedure.getTreatmentTask().getTreatmentProcedures().remove(treatmentProcedure);
                        }

                        treatmentTask.getTreatmentProcedures().add(treatmentProcedure.treatmentTask(treatmentTask));
                    });
                }

                if (updateTreatmentProcedure.getDisposal() != null && updateTreatmentProcedure.getDisposal().getId() != null) {
                    disposalRepository.findById(updateTreatmentProcedure.getDisposal().getId()).ifPresent(disposal -> {
                        if (treatmentProcedure.getDisposal() != null && !treatmentProcedure.getDisposal().getId().equals(disposal.getId())) {
                            treatmentProcedure.getDisposal().getTreatmentProcedures().remove(treatmentProcedure);
                        }

                        disposal.getTreatmentProcedures().add(treatmentProcedure.disposal(disposal));
                    });
                }

                if (updateTreatmentProcedure.getTeeth() != null) {
                    log.debug("Update teeth({}) of TreatmentProcedure(id: {})", updateTreatmentProcedure.getTeeth(), updateTreatmentProcedure.getId());
                    Set<Long> updateIds = updateTreatmentProcedure.getTeeth().stream().map(Tooth::getId).collect(Collectors.toSet());
                    relationshipService.deleteTeeth(
                        treatmentProcedure
                            .getTeeth()
                            .stream()
                            .filter(tooth -> !updateIds.contains(tooth.getId()))
                            .map(tooth -> tooth.treatmentProcedure(null))
                            .collect(Collectors.toSet())
                    );
                    relationshipService.addRelationshipWithTeeth(treatmentProcedure.teeth(updateTreatmentProcedure.getTeeth()));
                }

                if (updateTreatmentProcedure.getNhiExtendTreatmentProcedure() != null) {
                    NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure = updateTreatmentProcedure.getNhiExtendTreatmentProcedure();
                    treatmentProcedure.setNhiExtendTreatmentProcedure(getNhiExtendTreatmentProcedure(nhiExtendTreatmentProcedure.treatmentProcedure(treatmentProcedure)));
                }

                if (updateTreatmentProcedure.getTodos() != null) {
                    Set<Todo> originTodos = treatmentProcedure.getTodos();
                    relationshipService.addRelationshipWithTodos(treatmentProcedure, updateTreatmentProcedure.getTodos());
                    Set<Long> ids = StreamUtil.asStream(treatmentProcedure.getTodos()).map(Todo::getId).collect(Collectors.toSet());

                    StreamUtil.asStream(originTodos)
                        .filter(todo -> !ids.contains(todo.getId()))
                        .forEach(todo -> todo.getTreatmentProcedures().remove(treatmentProcedure));
                }

                return treatmentProcedure;
            })
            .get();
    }

    private NhiExtendTreatmentProcedure getNhiExtendTreatmentProcedure(NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure) {
        return nhiExtendTreatmentProcedure.getId() == null ? nhiExtendTreatmentProcedureService.save(nhiExtendTreatmentProcedure) : nhiExtendTreatmentProcedureService.update(nhiExtendTreatmentProcedure);
    }

    public List<TreatmentProcedure> findRecent6TreatmentProceduresByPatient(Long patient) {
        return treatmentProcedureRepository.findTop6ByAppointment_Patient_IdOrderByCreatedDateDesc(patient).stream()
            .map(treatmentProcedureMapper::TreatmentProcedureTableToTreatmentProcedure)
            .map(treatmentProcedure -> {
                // NhiProcedure
                if (treatmentProcedure.getNhiProcedure() != null &&
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
                        nhiExtendTreatmentProcedure.setTreatmentProcedure(treatmentProcedure);
                        treatmentProcedure.setNhiExtendTreatmentProcedure(nhiExtendTreatmentProcedure);
                    }

                }
                // TreatmentProcedure.Procedure
                else if (treatmentProcedure.getProcedure() != null &&
                    treatmentProcedure.getProcedure().getId() != null
                ) {
                    Optional<ProcedureTable> optionalProcedureTable = procedureRepository.findProcedureById(treatmentProcedure.getProcedure().getId());
                    if (optionalProcedureTable.isPresent()) {
                        treatmentProcedure.setProcedure(procedureMapper.procedureTableToProcedure(optionalProcedureTable.get()));
                    }
                }

                // TreatmentProcedure.Tooth
                treatmentProcedure.setTeeth(toothMapper.toothSetToToothSet(toothRepository.findToothByTreatmentProcedure_Id(treatmentProcedure.getId())));
                // Treatment.Disposal.Doctor
                if (treatmentProcedure.getDisposal() != null &&
                    treatmentProcedure.getDisposal().getId() != null
                ) {
                    Optional<DisposalTable> optionalDisposalTable = disposalRepository.findDisposalById(treatmentProcedure.getDisposal().getId());
                    if (optionalDisposalTable.isPresent()) {
                        Optional<AppointmentTable> optionalAppointmentTable = appointmentRepository
                            .findAppointmentByRegistration_Id(optionalDisposalTable.get().getRegistration_Id());

                        treatmentProcedure.setDoctor(extendUserRepository.findById(optionalAppointmentTable.get().getDoctorUser_Id()).orElse(null));
                    }
                }

                return treatmentProcedure;
            })
            .collect(Collectors.toList());
    }
}
