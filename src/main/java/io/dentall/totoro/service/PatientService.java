package io.dentall.totoro.service;

import io.dentall.totoro.business.service.nhi.NhiRuleCheckDTO;
import io.dentall.totoro.business.service.nhi.util.NhiRuleCheckFormat;
import io.dentall.totoro.business.service.nhi.util.NhiRuleCheckUtil;
import io.dentall.totoro.business.vm.nhi.NhiRuleCheckBody;
import io.dentall.totoro.business.vm.nhi.NhiRuleCheckResultVM;
import io.dentall.totoro.business.vm.nhi.NhiRuleCheckTxSnapshot;
import io.dentall.totoro.domain.*;
import io.dentall.totoro.domain.enumeration.TreatmentType;
import io.dentall.totoro.repository.*;
import io.dentall.totoro.service.dto.PatientCriteria;
import io.dentall.totoro.service.dto.table.DisposalTable;
import io.dentall.totoro.service.mapper.PatientMapper;
import io.dentall.totoro.service.util.DateTimeUtil;
import io.dentall.totoro.service.util.FilterUtil;
import io.dentall.totoro.service.util.StreamUtil;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.vm.PatientFirstLatestVisitDateVM;
import io.github.jhipster.service.QueryService;
import io.github.jhipster.service.filter.InstantFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.criteria.*;
import java.lang.reflect.Method;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for managing patients.
 */
@Service
@Transactional
public class PatientService extends QueryService<Patient> {

    private static final String ENTITY_NAME = "patient";

    private final Logger log = LoggerFactory.getLogger(PatientService.class);

    private final PatientRepository patientRepository;

    private final TagRepository tagRepository;

    private final QuestionnaireRepository questionnaireRepository;

    private final ExtendUserRepository extendUserRepository;

    private final PatientIdentityRepository patientIdentityRepository;

    private final TreatmentRepository treatmentRepository;

    private final TreatmentPlanRepository treatmentPlanRepository;

    private final TreatmentTaskRepository treatmentTaskRepository;

    private final RelationshipService relationshipService;

    private final NhiExtendPatientRepository nhiExtendPatientRepository;

    private final NhiRuleCheckUtil nhiRuleCheckUtil;

    private final DisposalRepository disposalRepository;

    public PatientService(
        PatientRepository patientRepository,
        TagRepository tagRepository,
        QuestionnaireRepository questionnaireRepository,
        ExtendUserRepository extendUserRepository,
        PatientIdentityRepository patientIdentityRepository,
        TreatmentRepository treatmentRepository,
        TreatmentPlanRepository treatmentPlanRepository,
        TreatmentTaskRepository treatmentTaskRepository,
        RelationshipService relationshipService,
        NhiExtendPatientRepository nhiExtendPatientRepository,
        NhiRuleCheckUtil nhiRuleCheckUtil,
        DisposalRepository disposalRepository) {
        this.patientRepository = patientRepository;
        this.tagRepository = tagRepository;
        this.questionnaireRepository = questionnaireRepository;
        this.extendUserRepository = extendUserRepository;
        this.patientIdentityRepository = patientIdentityRepository;
        this.treatmentRepository = treatmentRepository;
        this.treatmentPlanRepository = treatmentPlanRepository;
        this.treatmentTaskRepository = treatmentTaskRepository;
        this.relationshipService = relationshipService;
        this.nhiExtendPatientRepository = nhiExtendPatientRepository;
        this.nhiRuleCheckUtil = nhiRuleCheckUtil;
        this.disposalRepository = disposalRepository;
    }

    /**
     * Return a {@link Page} of {@link Patient} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Patient> findByCriteria(PatientCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Patient> specification = createSpecification(criteria);
        return patientRepository.findAll(specification, page);
    }

    /**
     * Save a patient.
     *
     * @param patient the entity to save
     * @return the persisted entity
     */
    public Patient save(Patient patient) {
        log.debug("Request to save Patient : {}", patient);

        patient = patientRepository.save(patient.newPatient(true));
        patient.setNhiExtendPatient(nhiExtendPatientRepository.save(new NhiExtendPatient().patient(patient)));
        patient.setMedicalId(String.format("%05d", patient.getId()));
        createGeneralTreatmentAndPlanAndTask(patient);

        return patient;
    }

    public Patient update(Patient updatePatient) {
        return patientRepository
            .findById(updatePatient.getId())
            .map(patient -> {
                // questionnaire
                if (updatePatient.getQuestionnaire() != null) {
                    log.debug("Update questionnaire({}) of Patient(id: {})", updatePatient.getQuestionnaire(), updatePatient.getId());
                    Questionnaire questionnaire = patient.getQuestionnaire();
                    Questionnaire updateQuestionnaire = updatePatient.getQuestionnaire();
                    patient.setQuestionnaire(questionnaire == null ? questionnaireRepository.save(updateQuestionnaire) : updateQuestionnaire(questionnaire, updateQuestionnaire));
                }

                // tags
                if (updatePatient.getTags() != null) {
                    log.debug("Update tags({}) of Patient(id: {})", updatePatient.getTags(), updatePatient.getId());
                    patient.getTags().clear();
                    updatePatient
                        .getTags()
                        .stream()
                        .map(tag -> tagRepository.findById(tag.getId()))
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .forEach(patient.getTags()::add);
                }

                // basic info.
                if (updatePatient.getName() != null) {
                    patient.setName(updatePatient.getName());
                }

                if (updatePatient.getPhone() != null) {
                    patient.setPhone(updatePatient.getPhone());
                }

                if (updatePatient.getGender() != null) {
                    patient.setGender(updatePatient.getGender());
                }

                if (updatePatient.getBirth() != null) {
                    patient.setBirth(updatePatient.getBirth());
                }

                if (updatePatient.getNationalId() != null) {
                    patient.setNationalId(updatePatient.getNationalId());
                }

                if (updatePatient.getAddress() != null) {
                    patient.setAddress(updatePatient.getAddress());
                }

                if (updatePatient.getEmail() != null) {
                    patient.setEmail(updatePatient.getEmail());
                }

                if (updatePatient.getBlood() != null) {
                    patient.setBlood(updatePatient.getBlood());
                }

                if (updatePatient.getCardId() != null) {
                    patient.setCardId(updatePatient.getCardId());
                }

                if (updatePatient.getVip() != null) {
                    patient.setVip(updatePatient.getVip());
                }

                if (updatePatient.getEmergencyName() != null) {
                    patient.setEmergencyName(updatePatient.getEmergencyName());
                }

                if (updatePatient.getEmergencyPhone() != null) {
                    patient.setEmergencyPhone(updatePatient.getEmergencyPhone());
                }

                if (updatePatient.getEmergencyAddress() != null) {
                    patient.setEmergencyAddress(updatePatient.getEmergencyAddress());
                }

                if (updatePatient.getEmergencyRelationship() != null) {
                    patient.setEmergencyRelationship(updatePatient.getEmergencyRelationship());
                }

                if (updatePatient.getCareer() != null) {
                    patient.setCareer(updatePatient.getCareer());
                }

                if (updatePatient.getMarriage() != null) {
                    patient.setMarriage(updatePatient.getMarriage());
                }

                if (updatePatient.getMainNoticeChannel() != null) {
                    patient.setMainNoticeChannel(updatePatient.getMainNoticeChannel());
                }

                if (updatePatient.getDeleteDate() != null) {
                    patient.setDeleteDate(updatePatient.getDeleteDate());
                }

                if (updatePatient.getScaling() != null) {
                    patient.setScaling(updatePatient.getScaling());
                }

                if (updatePatient.getLineId() != null) {
                    patient.setLineId(updatePatient.getLineId());
                }

                if (updatePatient.getFbId() != null) {
                    patient.setFbId(updatePatient.getFbId());
                }

                if (updatePatient.getNote() != null) {
                    patient.setNote(updatePatient.getNote());
                }

                if (updatePatient.getClinicNote() != null) {
                    patient.setClinicNote(updatePatient.getClinicNote());
                }

                if (updatePatient.getWriteIcTime() != null) {
                    patient.setWriteIcTime(updatePatient.getWriteIcTime());
                }

                if (updatePatient.getCustomizedDisease() != null) {
                    patient.setCustomizedDisease(updatePatient.getCustomizedDisease());
                }

                if (updatePatient.getCustomizedBloodDisease() != null) {
                    patient.setCustomizedBloodDisease(updatePatient.getCustomizedBloodDisease());
                }

                if (updatePatient.getCustomizedAllergy() != null) {
                    patient.setCustomizedAllergy(updatePatient.getCustomizedAllergy());
                }

                if (updatePatient.getCustomizedOther() != null) {
                    patient.setCustomizedOther(updatePatient.getCustomizedOther());
                }

                // introducer
                if (updatePatient.getIntroducer() != null) {
                    patient.setIntroducer(updatePatient.getIntroducer());
                }

                // dueDate
                if (updatePatient.getDueDate() != null) {
                    patient.setDueDate(updatePatient.getDueDate());
                }

                // lastDoctor
                if (updatePatient.getLastDoctor() != null) {
                    log.debug("Update lastDoctor({}) of Patient(id: {})", updatePatient.getLastDoctor(), updatePatient.getId());
                    patient.setLastDoctor(extendUserRepository.findById(updatePatient.getLastDoctor().getId()).orElse(null));
                }

                // firstDoctor
                if (updatePatient.getFirstDoctor() != null) {
                    log.debug("Update firstDoctor({}) of Patient(id: {})", updatePatient.getFirstDoctor(), updatePatient.getId());
                    patient.setFirstDoctor(extendUserRepository.findById(updatePatient.getFirstDoctor().getId()).orElse(null));
                }

                // patientIdentity
                if (updatePatient.getPatientIdentity() != null) {
                    log.debug("Update patientIdentity({}) of Patient(id: {})", updatePatient.getPatientIdentity(), updatePatient.getId());
                    patient.setPatientIdentity(patientIdentityRepository.findById(updatePatient.getPatientIdentity().getId()).orElse(null));
                }

                // teeth
                if (updatePatient.getTeeth() != null) {
                    log.debug("Update teeth({}) of Patient(id: {})", updatePatient.getTeeth(), updatePatient.getId());
                    Set<Long> updateIds = updatePatient.getTeeth().stream().map(Tooth::getId).collect(Collectors.toSet());
                    relationshipService.deleteTeeth(
                        StreamUtil.asStream(patient.getTeeth())
                            .filter(tooth -> !updateIds.contains(tooth.getId()))
                            .map(tooth -> tooth.patient(null))
                            .collect(Collectors.toSet())
                    );
                    relationshipService.addRelationshipWithTeeth(patient.teeth(updatePatient.getTeeth()));
                }

                if (updatePatient.getDisplayName() != null) {
                    log.debug("Update patientIdentity({}) of Patient(id: {})", updatePatient.getDisplayName(), updatePatient.getId());
                    patient.setDisplayName(updatePatient.getDisplayName());
                }

                if (updatePatient.getTeethGraphPermanentSwitch() != null) {
                    log.debug("Update teethGraphPermanentSwitch({}) of Patient(id: {})", updatePatient.getTeethGraphPermanentSwitch(), updatePatient.getId());
                    patient.setTeethGraphPermanentSwitch(updatePatient.getTeethGraphPermanentSwitch());
                }

                if (updatePatient.getCaseManager() != null) {
                    patient.setCaseManager(updatePatient.getCaseManager());
                }

                if (updatePatient.getVipPatient() != null) {
                    patient.setVipPatient(updatePatient.getVipPatient());
                }

                if (updatePatient.getDisabled() != null) {
                    patient.setDisabled(updatePatient.getDisabled());
                }

                return patient;
            })
            .get();
    }

    private Questionnaire updateQuestionnaire(Questionnaire questionnaire, Questionnaire updateQuestionnaire) {
        if (updateQuestionnaire.isDrug() != null) {
            questionnaire.setDrug(updateQuestionnaire.isDrug());
        }

        if (updateQuestionnaire.getDrugName() != null) {
            questionnaire.setDrugName(updateQuestionnaire.getDrugName());
        }

        if (updateQuestionnaire.getGlycemicAC() != null) {
            questionnaire.setGlycemicAC(updateQuestionnaire.getGlycemicAC());
        }

        if (updateQuestionnaire.getGlycemicPC() != null) {
            questionnaire.setGlycemicPC(updateQuestionnaire.getGlycemicPC());
        }

        if (updateQuestionnaire.getSmokeNumberADay() != null) {
            questionnaire.setSmokeNumberADay(updateQuestionnaire.getSmokeNumberADay());
        }

        if (updateQuestionnaire.getOtherInTreatment() != null) {
            questionnaire.setOtherInTreatment(updateQuestionnaire.getOtherInTreatment());
        }

        return questionnaire;
    }

    private Treatment createGeneralTreatmentAndPlanAndTask(Patient patient) {
        log.debug("Request to create general Treatment");

        Treatment treatment = new Treatment().name("General Treatment").type(TreatmentType.GENERAL).patient(patient);
        treatment = treatmentRepository.save(treatment);

        TreatmentPlan treatmentPlan = new TreatmentPlan().activated(true).name("General TreatmentPlan").treatment(treatment);
        treatmentPlan = treatmentPlanRepository.save(treatmentPlan);
        treatment.getTreatmentPlans().add(treatmentPlan);

        TreatmentTask treatmentTask = new TreatmentTask().name("General TreatmentTask").treatmentPlan(treatmentPlan);
        treatmentTask = treatmentTaskRepository.save(treatmentTask);
        treatmentPlan.getTreatmentTasks().add(treatmentTask);

        return treatment;
    }

    /**
     * Function to convert PatientCriteria to a {@link Specification}
     */
    private Specification<Patient> createSpecification(PatientCriteria criteria) {
        Specification<Patient> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getQuestionnaireId() != null) {
                specification = specification.and(buildSpecification(criteria.getQuestionnaireId(),
                    root -> root.join(Patient_.questionnaire, JoinType.LEFT).get(Questionnaire_.id)));
            }

            if (criteria.getNewPatient() != null) {
                specification = specification.and(buildSpecification(criteria.getNewPatient(), Patient_.newPatient));
            }

            if (criteria.getDeleteDate() != null) {
                specification = specification.and(buildSpecification(criteria.getDeleteDate(), Patient_.deleteDate));
            }

            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Patient_.createdDate));
            } else {
                if (FilterUtil.predicateIsNull.test(criteria.getQuestionnaireId())) {
                    // default createdDate is today
                    Instant start = DateTimeUtil.localTimeMin.get();
                    Instant end = DateTimeUtil.localTimeMax.get();
                    specification = specification.and(buildRangeSpecification(new InstantFilter().setGreaterOrEqualThan(start).setLessOrEqualThan(end), Patient_.createdDate));
                }
            }

            if (criteria.getSearch() != null && criteria.getSearch().getContains() != null) {
                String like = criteria.getSearch().getContains();
                specification = specification.and(
                    ((Specification<Patient>) (root, query, builder) -> builder.like(getFormatBirthExpression(builder, root.get(Patient_.birth)), wrapLikeQuery(like)))
                        .or(buildStringSpecification(criteria.getSearch(), Patient_.phone))
                        .or(buildStringSpecification(criteria.getSearch(), Patient_.name))
                        .or(buildStringSpecification(criteria.getSearch(), Patient_.medicalId))
                        .or((root, query, builder) -> builder.like(getWithoutLeadingZerosMedicalIdExpression(builder, root.get(Patient_.medicalId)), wrapLikeQuery(like)))
                ).and(searchOrderSpecification(like));
            }
        }

        return specification;
    }

    private Specification<Patient> searchOrderSpecification(String like) {
        return new Specification<Patient>() {

            @Override
            public Predicate toPredicate(Root<Patient> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                query.orderBy(criteriaBuilder.asc(searchOrder(root, criteriaBuilder, like)));
                return criteriaBuilder.and();
            }
        };
    }

    private Expression searchOrder(Root<Patient> root, CriteriaBuilder builder, String like) {
        return builder.selectCase()
            // equal
            .when(builder.equal(getFormatBirthExpression(builder, root.get(Patient_.birth)), like), 1)
            .when(builder.equal(root.get(Patient_.phone), like), 1)
            .when(builder.equal(root.get(Patient_.name), like), 1)
            .when(builder.equal(root.get(Patient_.medicalId), like), 1)
            .when(builder.equal(getWithoutLeadingZerosMedicalIdExpression(builder, root.get(Patient_.medicalId)), like), 1)
            // like%
            .when(builder.like(getFormatBirthExpression(builder, root.get(Patient_.birth)), wrapPostfixLikeQuery(like)), 2)
            .when(builder.like(root.get(Patient_.phone), wrapPostfixLikeQuery(like)), 2)
            .when(builder.like(root.get(Patient_.name), wrapPostfixLikeQuery(like)), 2)
            .when(builder.like(root.get(Patient_.medicalId), wrapPostfixLikeQuery(like)), 2)
            .when(builder.like(getWithoutLeadingZerosMedicalIdExpression(builder, root.get(Patient_.medicalId)), wrapPostfixLikeQuery(like)), 2)
            // %like%
            .when(builder.like(getFormatBirthExpression(builder, root.get(Patient_.birth)), wrapLikeQuery(like)), 3)
            .when(builder.like(root.get(Patient_.phone), wrapLikeQuery(like)), 3)
            .when(builder.like(root.get(Patient_.name), wrapLikeQuery(like)), 3)
            .when(builder.like(root.get(Patient_.medicalId), wrapLikeQuery(like)), 3)
            .when(builder.like(getWithoutLeadingZerosMedicalIdExpression(builder, root.get(Patient_.medicalId)), wrapLikeQuery(like)), 3)
            .otherwise(4);
    }

    private Expression<String> getFormatBirthExpression(CriteriaBuilder builder, Path<LocalDate> expression) {
        return builder.function("TO_CHAR", String.class, expression, builder.literal("yyyyMMdd"));
    }

    private Expression<String> getWithoutLeadingZerosMedicalIdExpression(CriteriaBuilder builder, Path<String> expression) {
        return builder.function(
            "regexp_replace",
            String.class,
            expression,
            builder.literal("^0*|-"), // replace 0* and -
            builder.literal(""),
            builder.literal("g")
        );
    }

    private String wrapPostfixLikeQuery(String txt) {
        return txt.toUpperCase() + '%';
    }

    public Patient findPatientById(Long patientId) {
        return PatientMapper.patientTableToPatient(
            patientRepository.findPatientById(patientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to found resource")));
    }

    public HashSet<Patient> getPatientRelationship(Long id, Method m) {
        try {
            // 由於 prod 下，transition 機制跟 dev 不同，造成 object 出 service 後，尚未被註記成 initialized，使得 json wrapping 時
            // 會把 lazy 的資料拉出來，但這時候已經不在 session 下了，導致 exception。為了避免此事發生，將查詢結果(PersistentSet)
            // 異動為 HashSet，就不會在後續有自動查詢的狀況。
            Optional<Patient> optP = patientRepository.findById(id);
            Set<Patient> set = optP.isPresent() ? (Set<Patient>) m.invoke(optP.get()) : new HashSet<>();
            HashSet rset = new HashSet(set);
            return rset;
        } catch (Exception e) {
            log.error(e.toString());
            return new HashSet<Patient>();
        }
    }

    public Set<Patient> createPatientRelationship(
        Long mainId,
        Long subId,
        String mainRelationshipSetter,
        String subRelationshipSetter
    ) {
        try {
            Optional<Patient> optMainP = patientRepository.findById(mainId);
            Optional<Patient> optSubP = patientRepository.findById(subId);

            if (!optMainP.isPresent() || !optSubP.isPresent()) {
                throw new BadRequestAlertException("Can not found by mainId or subId", ENTITY_NAME, "patient.not.found");
            }

            Patient mainP = optMainP.get();
            Patient subP = optSubP.get();

            HashSet<Patient> mainS = new HashSet<>();
            HashSet<Patient> subS = new HashSet<>();
            mainS.add(mainP);
            subS.add(subP);

            Method mainM = Patient.class.getMethod(mainRelationshipSetter,Set.class);
            Method subM = Patient.class.getMethod(subRelationshipSetter, Set.class);

            mainM.invoke(optSubP.get(), mainS);
            subM.invoke(optMainP.get(), subS);

            return subS;
        } catch (Exception e) {
            e.printStackTrace();
            return new HashSet<Patient>();
        }
    }

    public HashSet<Patient> deletePatientRelationship(
        Long mainId,
        Long subId,
        String mainRelationshipSetter,
        String subRelationshipSetter
    ) {
        try {
            Optional<Patient> optMainP = patientRepository.findById(mainId);
            Optional<Patient> optSubP = patientRepository.findById(subId);

            if (!optMainP.isPresent() || !optSubP.isPresent()) {
                throw new BadRequestAlertException("Can not found by mainId or subId", ENTITY_NAME, "patient.not.found");
            }

            HashSet<Patient> es = new HashSet<>();

            Method mainM = Patient.class.getMethod(mainRelationshipSetter, Set.class);
            Method subM = Patient.class.getMethod(subRelationshipSetter, Set.class);

            mainM.invoke(optSubP.get(), es);
            subM.invoke(optMainP.get(), es);
        } catch (Exception e) {
            log.error(e.toString());
        }

        return new HashSet<Patient>();
    }

    public Patient getFirstLastDoctor(Patient p) {
        if (p != null && p.getId() != null) {
            ExtendUser fd = extendUserRepository.findPatientFirstDoctor(p.getId());
            ExtendUser ld = extendUserRepository.findPatientLastDoctor(p.getId());
            if (fd != null && fd.getId() != null) {
                p.setFirstDoctor(fd);
            }
            if (ld != null && ld.getId() != null) {
                p.setLastDoctor(ld);
            }
        }

        return p;
    }

    /**
     * 特殊 rule check 只實作指定代碼(81/91004C)，部分檢核，若原生代碼有異動，則需一併調整
     */
    public NhiRuleCheckResultVM getPatientNhiStatus(
        String code,
        Long patientId
    ) {
        NhiRuleCheckBody body = new NhiRuleCheckBody();
        List<NhiRuleCheckTxSnapshot> snapshots = new ArrayList();
        NhiRuleCheckTxSnapshot snapshot = new NhiRuleCheckTxSnapshot();
        snapshot.setTargetTx(true);
        snapshots.add(snapshot);
        body.setTxSnapshots(snapshots);
        body.setPatientId(patientId);
        body.setDisposalTime(
            DateTimeUtil.transformLocalDateToRocDate(
                Instant.now()
            )
        );

        NhiRuleCheckDTO dto = null;
        NhiRuleCheckResultVM vm = new NhiRuleCheckResultVM();
        snapshot.setTargetTx(true);

        switch(code) {
            case "81":
                // 檢查 81
                snapshot.setNhiCode("81");
                dto = nhiRuleCheckUtil.convertVmToDto(code, body);

                if (vm.isValidated()) {
                    nhiRuleCheckUtil.addResultToVm(
                        nhiRuleCheckUtil.lessThanAge6(dto),
                        vm
                    );
                }

                if (vm.isValidated()) {
                    nhiRuleCheckUtil.addResultToVm(
                        nhiRuleCheckUtil.isCodeBeforeDateV2(
                            dto,
                            null,
                            Arrays.asList("81"),
                            nhiRuleCheckUtil.specialMonthDurationCalculation(dto, DateTimeUtil.NUMBERS_OF_MONTH_6),
                            String.valueOf(DateTimeUtil.NUMBERS_OF_MONTH_6),
                            1,
                            NhiRuleCheckFormat.D4_1_3
                        ),
                        vm
                    );
                }

                if (vm.isValidated()) {
                    nhiRuleCheckUtil.addResultToVm(
                        nhiRuleCheckUtil.appendSuccessSourceInfo(
                            dto,
                            "81"
                        ),
                        vm
                    );
                }

                break;
            case "91004C":
                // 檢查 91004C
                snapshot.setNhiCode("91004C");
                dto = nhiRuleCheckUtil.convertVmToDto(code, body);

                if (vm.isValidated()) {
                    nhiRuleCheckUtil.addResultToVm(
                        nhiRuleCheckUtil.isCodeBeforeDateV2(
                            dto,
                            null,
                            Arrays.asList("91004C"),
                            nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_180_DAY),
                            String.valueOf(DateTimeUtil.NHI_180_DAY.getDays()),
                            1,
                            NhiRuleCheckFormat.D4_1_2
                        ),
                        vm
                    );
                }

                if (vm.isValidated()) {
                    if (nhiRuleCheckUtil.getPatientAge(dto).getYears() < 12) {
                        nhiRuleCheckUtil.addResultToVm(
                            nhiRuleCheckUtil.addNotification(
                                String.format(
                                    NhiRuleCheckFormat.XRAY.getFormat(),
                                    dto.getNhiExtendTreatmentProcedure().getA73()
                                )
                            ),
                            vm
                        );
                    }
                }

                if (vm.isValidated()) {
                    nhiRuleCheckUtil.addResultToVm(
                        nhiRuleCheckUtil.isCodeBeforeDateV2(
                            dto,
                            null,
                            Arrays.asList("91003C", "91104C", "91015C", "91016C", "91017C", "91018C", "91005C", "91103C"),
                            nhiRuleCheckUtil.regularDayDurationCalculation(dto, DateTimeUtil.NHI_90_DAY),
                            String.valueOf(DateTimeUtil.NHI_90_DAY.getDays()),
                            1,
                            NhiRuleCheckFormat.D1_2_4
                        ),
                        vm
                    );
                }

                if (vm.isValidated()) {
                    nhiRuleCheckUtil.addResultToVm(
                        nhiRuleCheckUtil.appendSuccessSourceInfo(
                            dto,
                            "91004C"
                        ),
                        vm
                    );
                }
                break;
            default:
                break;
        }

        return vm;
    }

    public void setNewPatient(Patient patient) {
        patient.setNewPatient(
            patient
                .getAppointments()
                .stream()
                .filter(appointment -> appointment.getRegistration() != null)
                .count() < 1
        );
    }

    public PatientFirstLatestVisitDateVM findPatientFirstLatestVisitDate(Long patientId) {
        PatientFirstLatestVisitDateVM vm = new PatientFirstLatestVisitDateVM();

        Optional<DisposalTable> fd = disposalRepository.findFirstByRegistration_Appointment_Patient_IdOrderByDateTime(patientId);
        Optional<DisposalTable> ld = disposalRepository.findFirstByRegistration_Appointment_Patient_IdOrderByDateTimeDesc(patientId);

        if (fd.isPresent() && ld.isPresent()) {
            vm.setFirstVisitDate(fd.get().getDateTime());
            vm.setLatestVisitDate(ld.get().getDateTime());
        }

        return vm;
    }
}
