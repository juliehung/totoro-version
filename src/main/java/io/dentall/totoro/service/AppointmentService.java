package io.dentall.totoro.service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.dentall.totoro.domain.Accounting;
import io.dentall.totoro.domain.Appointment;
import io.dentall.totoro.domain.Disposal;
import io.dentall.totoro.domain.ExtendUser;
import io.dentall.totoro.domain.Patient;
import io.dentall.totoro.domain.Registration;
import io.dentall.totoro.domain.Tag;
import io.dentall.totoro.domain.TreatmentProcedure;
import io.dentall.totoro.domain.User;
import io.dentall.totoro.domain.enumeration.Blood;
import io.dentall.totoro.domain.enumeration.Gender;
import io.dentall.totoro.domain.enumeration.RegistrationStatus;
import io.dentall.totoro.domain.enumeration.TagType;
import io.dentall.totoro.repository.AccountingRepository;
import io.dentall.totoro.repository.AppointmentRepository;
import io.dentall.totoro.repository.DisposalRepository;
import io.dentall.totoro.repository.ExtendUserRepository;
import io.dentall.totoro.repository.PatientRepository;
import io.dentall.totoro.repository.RegistrationRepository;
import io.dentall.totoro.repository.TagRepository;
import io.dentall.totoro.service.dto.AppointmentDTO;
import io.dentall.totoro.service.dto.AppointmentSplitRelationshipDTO;
import io.dentall.totoro.service.dto.TagDTO;
import io.dentall.totoro.service.dto.table.AccountingTable;
import io.dentall.totoro.service.dto.table.AppointmentTable;
import io.dentall.totoro.service.dto.table.PatientTable;
import io.dentall.totoro.service.dto.table.RegistrationTable;
import io.dentall.totoro.service.mapper.AccountingMapper;
import io.dentall.totoro.service.mapper.AppointmentMapper;
import io.dentall.totoro.service.mapper.PatientMapper;
import io.dentall.totoro.service.mapper.RegistrationMapper;
import io.dentall.totoro.service.mapper.TagMapper;
import io.dentall.totoro.service.util.MapperUtil;
import io.dentall.totoro.service.util.StreamUtil;
import io.dentall.totoro.web.rest.vm.MonthAppointmentVM;
import io.dentall.totoro.web.rest.vm.UWPRegistrationPageVM;

/**
 * Service Implementation for managing Appointment.
 */
@Service
@Transactional
public class AppointmentService {

    private final Logger log = LoggerFactory.getLogger(AppointmentService.class);

    private final AppointmentRepository appointmentRepository;

    private final ExtendUserRepository extendUserRepository;

    private final PatientService patientService;

    private final RegistrationService registrationService;

    private final RelationshipService relationshipService;

    private final DisposalRepository disposalRepository;

    private final RegistrationRepository registrationRepository;

    private final PatientRepository patientRepository;

    private final TagRepository tagRepository;

    private final AccountingRepository accountingRepository;

    public AppointmentService(
        AppointmentRepository appointmentRepository,
        ExtendUserRepository extendUserRepository,
        PatientService patientService,
        @Lazy RegistrationService registrationService,
        RelationshipService relationshipService,
        DisposalRepository disposalRepository,
        RegistrationRepository registrationRepository,
        PatientRepository patientRepository,
        TagRepository tagRepository,
        AccountingRepository accountingRepository
    ) {
        this.appointmentRepository = appointmentRepository;
        this.extendUserRepository = extendUserRepository;
        this.patientService = patientService;
        this.registrationService = registrationService;
        this.relationshipService = relationshipService;
        this.disposalRepository = disposalRepository;
        this.registrationRepository = registrationRepository;
        this.patientRepository = patientRepository;
        this.tagRepository = tagRepository;
        this.accountingRepository = accountingRepository;
    }

    /**
     * Save a appointment.
     *
     * @param appointment the entity to save
     * @return the persisted entity
     */
    public Appointment save(Appointment appointment) {
        log.debug("Request to save Appointment : {}", appointment);

        Patient patient = getPatient(appointment);

        Set<TreatmentProcedure> treatmentProcedures = appointment.getTreatmentProcedures();
        appointment = appointmentRepository.save(appointment.treatmentProcedures(null));
        relationshipService.addRelationshipWithTreatmentProcedures(appointment.treatmentProcedures(treatmentProcedures));
        appointment.setPatient(patient);
        patient.getAppointments().add(appointment);
        Registration registration = getRegistration(appointment);
        appointment.setRegistration(registration);

        patientService.setNewPatient(patient);

        return appointment;
    }

    /**
     * Get all the appointments.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Appointment> findAll(Pageable pageable) {
        log.debug("Request to get all Appointments");
        return appointmentRepository.findAll(pageable);
    }


    /**
     * Get one appointment by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Appointment> findOne(Long id) {
        log.debug("Request to get Appointment : {}", id);
        return appointmentRepository.findById(id);
    }

    /**
     * Delete the appointment by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Appointment : {}", id);

        appointmentRepository.findById(id).ifPresent(appointment -> {
            StreamUtil.asStream(appointment.getTreatmentProcedures()).forEach(treatmentProcedure -> treatmentProcedure.setAppointment(null));
            relationshipService.deleteTreatmentProcedures(appointment.getTreatmentProcedures());

            if (appointment.getPatient() != null) {
                Patient patient = appointment.getPatient();
                patient.getAppointments().remove(appointment);
            }

            appointmentRepository.deleteById(id);
        });
    }

    public Appointment update(Appointment updateAppointment) {
        return appointmentRepository
            .findById(updateAppointment.getId())
            .map(appointment -> {
                if (updateAppointment.getStatus() != null) {
                    appointment.setStatus((updateAppointment.getStatus()));
                }

                if (updateAppointment.getSubject() != null) {
                    appointment.setSubject((updateAppointment.getSubject()));
                }

                if (updateAppointment.getNote() != null) {
                    appointment.setNote((updateAppointment.getNote()));
                }

                if (updateAppointment.getExpectedArrivalTime() != null) {
                    appointment.setExpectedArrivalTime((updateAppointment.getExpectedArrivalTime()));
                }

                if (updateAppointment.getRequiredTreatmentTime() != null) {
                    appointment.setRequiredTreatmentTime((updateAppointment.getRequiredTreatmentTime()));
                }

                if (updateAppointment.isMicroscope() != null) {
                    appointment.setMicroscope((updateAppointment.isMicroscope()));
                }

                if (updateAppointment.isBaseFloor() != null) {
                    appointment.setBaseFloor((updateAppointment.isBaseFloor()));
                }

                if (updateAppointment.getColorId() != null) {
                    appointment.setColorId((updateAppointment.getColorId()));
                }

                if (updateAppointment.isArchived() != null) {
                    appointment.setArchived((updateAppointment.isArchived()));
                }

                if (updateAppointment.isContacted() != null) {
                    appointment.setContacted((updateAppointment.isContacted()));
                }

                if (updateAppointment.isFirstVisit() != null) {
                    appointment.setFirstVisit(updateAppointment.isFirstVisit());
                }

                // doctor
                if (updateAppointment.getDoctor() != null && updateAppointment.getDoctor().getId() != null) {
                    log.debug("Update Doctor({}) of Appointment(id: {})", updateAppointment.getDoctor(), updateAppointment.getId());
                    extendUserRepository.findById(updateAppointment.getDoctor().getId()).ifPresent(appointment::setDoctor);
                }

                // patient
                if (updateAppointment.getPatient() != null) {
                    log.debug("Update Patient({}) of Appointment(id: {})", updateAppointment.getPatient(), updateAppointment.getId());
                    appointment.setPatient(getPatient(updateAppointment));
                }

                // registration
                if (updateAppointment.getRegistration() != null) {
                    log.debug("Update Registration({}) of Appointment(id: {})", updateAppointment.getRegistration(), updateAppointment.getId());
                    appointment.setRegistration(getRegistration(updateAppointment.patient(appointment.getPatient())));
                }

                // treatmentProcedures
                if (updateAppointment.getTreatmentProcedures() != null) {
                    Set<Long> updateIds = updateAppointment.getTreatmentProcedures().stream().map(TreatmentProcedure::getId).collect(Collectors.toSet());
                    relationshipService.deleteTreatmentProcedures(
                        StreamUtil.asStream(appointment.getTreatmentProcedures())
                            .filter(treatmentProcedure -> !updateIds.contains(treatmentProcedure.getId()))
                            .map(treatmentProcedure -> treatmentProcedure.appointment(null))
                            .collect(Collectors.toSet())
                    );
                    relationshipService.addRelationshipWithTreatmentProcedures(appointment.treatmentProcedures(updateAppointment.getTreatmentProcedures()));
                }

                patientService.setNewPatient(appointment.getPatient());

                return appointment;
            })
            .get();
    }

    private Patient getPatient(Appointment appointment) {
        Patient patient = appointment.getPatient();
        if (patient != null) {
            patient = patient.getId() == null ? patientService.save(patient) : patientService.update(patient);
        }

        return patient;
    }

    private Registration getRegistration(Appointment appointment) {
        Registration registration = appointment.getRegistration();
        if (registration != null) {
            registration = registration.getId() == null ? registrationService.save(registration, appointment.getPatient().getName()) : registrationService.update(registration);
        }

        return registration;
    }

    public List<UWPRegistrationPageVM> findAppointmentWithTonsOfDataForUWPRegistrationPage(
        Instant beginDate,
        Instant endDate
    ) {
        return appointmentRepository.findAppointmentWithTonsOfDataForUWPRegistrationPage(beginDate, endDate);
    }

    public List<MonthAppointmentVM> findAppointmentBetween(
        Instant beginDate,
        Instant endDate
    ) {
        List<AppointmentDTO> dtoList = appointmentRepository.findMonthAppointment(beginDate, endDate);
        List<Long> patientIds = dtoList.stream().map(AppointmentDTO::getPatientId).distinct()
                .collect(Collectors.toList());
        List<TagDTO> tags = tagRepository.findByPatientIds(patientIds);

        return dtoList.stream()
                .map(appointmentDTO -> {
                    MonthAppointmentVM monthAppointmentVM = new MonthAppointmentVM(appointmentDTO);
                    monthAppointmentVM.setTags(handlePatientTag(tags, appointmentDTO));
                    return monthAppointmentVM;
                })
                .collect(Collectors.toList());
    }

    private Set<Tag> handlePatientTag(List<TagDTO> tags, AppointmentDTO appointmentDTO) {
        Set<Tag> tagList = tags.stream()
                .filter(tag -> tag.getPatientId().equals(appointmentDTO.getPatientId()))
                .map(tag -> {
                    Tag tagNew = new Tag();
                    tagNew.setId(tag.getId());
                    tagNew.setName(tag.getName());
                    tagNew.setType(tag.getType());
                    tagNew.setModifiable(tag.getModifiable());
                    tagNew.setOrder(tag.getOrder());
                    return tagNew;
                }).collect(Collectors.toSet());

        if (appointmentDTO.getMicroscope() != null && appointmentDTO.getMicroscope()) {
            Tag tag = new Tag();
            tag.setId(9999L);
            tag.setName("micro");
            tag.setType(TagType.OTHER);
            tag.setModifiable(false);
            tagList.add(tag);
        }

        if (appointmentDTO.getBaseFloor() != null && appointmentDTO.getBaseFloor()) {
            Tag tag = new Tag();
            tag.setId(9998L);
            tag.setName("行動不便");
            tag.setType(TagType.OTHER);
            tag.setModifiable(false);
            tagList.add(tag);
        }

        return tagList;
    }

    public List<AppointmentSplitRelationshipDTO> findAppointmentWithRelationshipBetween(
        Instant beginDate,
        Instant endDate
    ) {
        return appointmentRepository.findAppointmentWithRelationshipBetween(beginDate, endDate).stream()
            .map(AppointmentSplitRelationshipDTO::new)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<Appointment> getAppointmentProjectionByPatientId(Long id, Pageable page) {
        return appointmentRepository.findByPatient_Id(id, page)
            .map(AppointmentMapper::appointmentTableToAppointment)
            .map(appointment -> {
                Registration registration = appointment.getRegistration();
                if (registration != null && registration.getId() != null) {
                    getDisposalByRegistrationId(registration.getId())
                        .ifPresent(registration::setDisposal);

                    registrationRepository.findById(registration.getId(), AppointmentRegistration.class)
                        .ifPresent(appointmentRegistration -> registration.setArrivalTime(appointmentRegistration.getArrivalTime()));
                }

                return appointment;
            });
    }

    @Transactional(readOnly = true)
    public List<Appointment> getBasicAppointmentProjectionByExpectedArrivalTime(Instant start, Instant end) {
        return appointmentRepository.findByExpectedArrivalTimeBetweenOrderByExpectedArrivalTimeAsc(start, end)
            .stream()
            .map(AppointmentMapper::appointmentTableToAppointment)
            .map(this::getBasicAppointment)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Appointment> getAppointmentProjectionByExpectedArrivalTime(Instant start, Instant end) {
        return appointmentRepository.findByExpectedArrivalTimeBetweenOrderByExpectedArrivalTimeAsc(start, end)
            .stream()
            .map(AppointmentMapper::appointmentTableToAppointment)
            .map(this::getBasicAppointment)
            .map(appointment -> {
                Registration registration = appointment.getRegistration();
                if (registration != null && registration.getId() != null) {
                    getDisposalByRegistrationId(registration.getId())
                        .ifPresent(registration::setDisposal);

                    Accounting accounting = registration.getAccounting();
                    if (accounting != null && accounting.getId() != null) {
                        accountingRepository.findAccountingById(accounting.getId())
                            .ifPresent(accountingTable -> registration.setAccounting(AccountingMapper.accountingTableToAccounting(accountingTable)));
                    }
                }

                Patient patient = appointment.getPatient();
                if (patient != null && patient.getId() != null) {
                    Set<Tag> tags = tagRepository.findByPatientsId(patient.getId())
                        .stream()
                        .map(TagMapper::tagTableToTag)
                        .collect(Collectors.toSet());

                    patient.setTags(tags);
                }

                return appointment;
            })
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Appointment> getSimpleAppointmentProjectionByExpectedArrivalTime(Instant start, Instant end) {
        return appointmentRepository.findByExpectedArrivalTimeBetweenOrderByExpectedArrivalTimeAsc(start, end, Appointment1To1.class)
            .stream()
            .map(this::getSimpleAppointment)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Appointment> getSimpleAppointmentProjectionByExpectedArrivalTimeAndRegIsNull(Instant start, Instant end) {
        return appointmentRepository.findByRegistrationIsNullAndExpectedArrivalTimeBetweenOrderByExpectedArrivalTimeAsc(start, end, Appointment1To1.class)
            .stream()
            .map(this::getSimpleAppointment)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Appointment> getSimpleAppointmentProjectionByExpectedArrivalTimeAndRegIsNotNull(Instant start, Instant end) {
        return appointmentRepository.findByRegistrationIsNotNullAndExpectedArrivalTimeBetweenOrderByExpectedArrivalTimeAsc(start, end, Appointment1To1.class)
            .stream()
            .map(this::getSimpleAppointment)
            .collect(Collectors.toList());
    }

    private Appointment getBasicAppointment(Appointment appointment) {
        Patient patient = appointment.getPatient();
        if (patient != null && patient.getId() != null) {
            patientRepository.findPatientById(patient.getId())
                .ifPresent(patientTable -> appointment.setPatient(PatientMapper.patientTableToPatient(patientTable)));
        }

        Registration registration = appointment.getRegistration();
        if (registration != null && registration.getId() != null) {
            registrationRepository.findById(registration.getId(), RegistrationTable.class)
                .ifPresent(registrationTable -> appointment.setRegistration(RegistrationMapper.registrationTableToRegistration(registrationTable)));
        }

        ExtendUser extendUser = appointment.getDoctor();
        if (extendUser != null && extendUser.getId() != null) {
            extendUserRepository
                .findById(extendUser.getId(), AppointmentDoctor.class)
                .ifPresent(appointmentDoctor -> {
                    User user = new User();
                    user.setId(appointmentDoctor.getUser_Id());
                    user.setFirstName(appointmentDoctor.getUser_FirstName());
                    MapperUtil.setNullAuditing(user);

                    extendUser.setUser(user);
                });
        }

        return appointment;
    }

    private Appointment getSimpleAppointment(Appointment1To1 appointment1To1) {
        Appointment appointment = AppointmentMapper.appointmentTableToAppointment(appointment1To1);

        // patient
        if (appointment1To1.getPatient_Id() != null) {
            Patient patient = PatientMapper.patientTableToPatient(getPatientTable(appointment1To1));
            appointment.setPatient(patient);

            // tag
            Set<Tag> tags = tagRepository.findByPatientsId(patient.getId())
                .stream()
                .map(TagMapper::tagTableToTag)
                .collect(Collectors.toSet());

            patient.setTags(tags);
        }

        // registration
        if (appointment1To1.getRegistration_Id() != null) {
            Registration registration = RegistrationMapper.registrationTableToRegistration(getRegistrationTable(appointment1To1));
            appointment.setRegistration(registration);

            // disposal
            if (appointment1To1.getRegistration_Disposal_Id() != null) {
                Disposal disposal = new Disposal();
                disposal.setId(appointment1To1.getRegistration_Disposal_Id());
                MapperUtil.setNullAuditing(disposal);

                registration.setDisposal(disposal);
            }

            // accounting
            if (appointment1To1.getRegistration_Accounting_Id() != null) {
                Accounting accounting = AccountingMapper.accountingTableToAccounting(getAccountingTable(appointment1To1));
                registration.setAccounting(accounting);
            }
        }

        // doctor
        if (appointment1To1.getDoctorUser_Id() != null) {
            ExtendUser extendUser = new ExtendUser();
            extendUser.setId(appointment1To1.getDoctorUser_Id());

            User user = new User();
            user.setId(appointment1To1.getDoctor_User_Id());
            user.setFirstName(appointment1To1.getDoctor_User_FirstName());
            MapperUtil.setNullAuditing(user);

            extendUser.setUser(user);
            appointment.setDoctor(extendUser);
        }

        return appointment;
    }

    private Optional<Disposal> getDisposalByRegistrationId(Long id) {
        return disposalRepository
            .findByRegistration_Id(id)
            .map(appointmentRegistrationDisposal -> {
                Disposal disposal = new Disposal();
                disposal.setId(appointmentRegistrationDisposal.getId());
                MapperUtil.setNullAuditing(disposal);

                return disposal;
            });
    }

    private PatientTable getPatientTable(Appointment1To1 appointment1To1) {
        return new PatientTable() {

            @Override
            public Long getId() {
                return appointment1To1.getPatient_Id();
            }

            @Override
            public String getDisplayName() {
                return null;
            }

            @Override
            public String getName() {
                return appointment1To1.getPatient_Name();
            }

            @Override
            public String getPhone() {
                return appointment1To1.getPatient_Phone();
            }

            @Override
            public Gender getGender() {
                return appointment1To1.getPatient_Gender();
            }

            @Override
            public LocalDate getBirth() {
                return appointment1To1.getPatient_Birth();
            }

            @Override
            public String getNationalId() {
                return appointment1To1.getPatient_NationalId();
            }

            @Override
            public String getMedicalId() {
                return appointment1To1.getPatient_MedicalId();
            }

            @Override
            public String getAddress() {
                return appointment1To1.getPatient_Address();
            }

            @Override
            public String getEmail() {
                return appointment1To1.getPatient_Email();
            }

            @Override
            public Blood getBlood() {
                return appointment1To1.getPatient_Blood();
            }

            @Override
            public String getCardId() {
                return appointment1To1.getPatient_CardId();
            }

            @Override
            public String getVip() {
                return appointment1To1.getPatient_Vip();
            }

            @Override
            public String getEmergencyName() {
                return appointment1To1.getPatient_EmergencyName();
            }

            @Override
            public String getEmergencyPhone() {
                return appointment1To1.getPatient_EmergencyPhone();
            }

            @Override
            public Instant getDeleteDate() {
                return appointment1To1.getPatient_DeleteDate();
            }

            @Override
            public LocalDate getScaling() {
                return appointment1To1.getPatient_Scaling();
            }

            @Override
            public String getLineId() {
                return appointment1To1.getPatient_LineId();
            }

            @Override
            public String getFbId() {
                return appointment1To1.getPatient_FbId();
            }

            @Override
            public String getNote() {
                return appointment1To1.getPatient_Note();
            }

            @Override
            public String getClinicNote() {
                return appointment1To1.getPatient_ClinicNote();
            }

            @Override
            public Instant getWriteIcTime() {
                return appointment1To1.getPatient_WriteIcTime();
            }

            @Override
            public String getAvatarContentType() {
                return appointment1To1.getPatient_AvatarContentType();
            }

            @Override
            public Boolean getNewPatient() {
                return appointment1To1.getPatient_NewPatient();
            }

            @Override
            public String getEmergencyAddress() {
                return appointment1To1.getPatient_EmergencyAddress();
            }

            @Override
            public String getEmergencyRelationship() {
                return appointment1To1.getPatient_EmergencyRelationship();
            }

            @Override
            public String getMainNoticeChannel() {
                return appointment1To1.getPatient_MainNoticeChannel();
            }

            @Override
            public String getCareer() {
                return appointment1To1.getPatient_Career();
            }

            @Override
            public String getMarriage() {
                return appointment1To1.getPatient_Marriage();
            }

            @Override
            public String getTeethGraphPermanentSwitch() {
                return appointment1To1.getPatient_TeethGraphPermanentSwitch();
            }

            @Override
            public String getIntroducer() {
                return appointment1To1.getPatient_Introducer();
            }

            @Override
            public LocalDate getDueDate() {
                return appointment1To1.getPatient_DueDate();
            }

            @Override
            public Long getQuestionnaire_Id() {
                return appointment1To1.getPatient_Questionnaire_Id();
            }

            @Override
            public Long getPatientIdentity_Id() {
                return appointment1To1.getPatient_PatientIdentity_Id();
            }

            @Override
            public Long getLastDoctorUser_Id() {
                return appointment1To1.getPatient_LastDoctorUser_Id();
            }

            @Override
            public Long getFirstDoctorUser_Id() {
                return appointment1To1.getPatient_FirstDoctorUser_Id();
            }

            @Override
            public String getCreatedBy() {
                return appointment1To1.getPatient_CreatedBy();
            }

            @Override
            public String getLastModifiedBy() {
                return appointment1To1.getPatient_LastModifiedBy();
            }

            @Override
            public Instant getCreatedDate() {
                return appointment1To1.getPatient_CreatedDate();
            }

            @Override
            public Instant getLastModifiedDate() {
                return appointment1To1.getPatient_LastModifiedDate();
            }

            @Override
            public String getCaseManager() {
                return appointment1To1.getPatient_CaseManager();
            }

        };
    }

    private RegistrationTable getRegistrationTable(Appointment1To1 appointment1To1) {
        return new RegistrationTable() {

            @Override
            public Long getId() {
                return appointment1To1.getRegistration_Id();
            }

            @Override
            public RegistrationStatus getStatus() {
                return appointment1To1.getRegistration_Status();
            }

            @Override
            public Instant getArrivalTime() {
                return appointment1To1.getRegistration_ArrivalTime();
            }

            @Override
            public String getType() {
                return appointment1To1.getRegistration_Type();
            }

            @Override
            public Boolean getOnSite() {
                return appointment1To1.getRegistration_OnSite();
            }

            @Override
            public Boolean getNoCard() {
                return appointment1To1.getRegistration_NoCard();
            }

            @Override
            public String getAbnormalCode() {
                return appointment1To1.getRegistration_AbnormalCode();
            }

            @Override
            public Long getAccounting_Id() {
                return appointment1To1.getRegistration_Accounting_Id();
            }

            @Override
            public String getCreatedBy() {
                return appointment1To1.getRegistration_CreatedBy();
            }

            @Override
            public String getLastModifiedBy() {
                return appointment1To1.getRegistration_LastModifiedBy();
            }

            @Override
            public Instant getCreatedDate() {
                return appointment1To1.getRegistration_CreatedDate();
            }

            @Override
            public Instant getLastModifiedDate() {
                return appointment1To1.getRegistration_LastModifiedDate();
            }
        };
    }

    private AccountingTable getAccountingTable(Appointment1To1 appointment1To1) {
        return new AccountingTable() {

            @Override
            public Long getId() {
                return appointment1To1.getRegistration_Accounting_Id();
            }

            @Override
            public Double getRegistrationFee() {
                return appointment1To1.getRegistration_Accounting_RegistrationFee();
            }

            @Override
            public Double getPartialBurden() {
                return appointment1To1.getRegistration_Accounting_PartialBurden();
            }

            @Override
            public Double getDeposit() {
                return appointment1To1.getRegistration_Accounting_Deposit();
            }

            @Override
            public Double getOwnExpense() {
                return appointment1To1.getRegistration_Accounting_OwnExpense();
            }

            @Override
            public Double getOther() {
                return appointment1To1.getRegistration_Accounting_Other();
            }

            @Override
            public String getPatientIdentity() {
                return appointment1To1.getRegistration_Accounting_PatientIdentity();
            }

            @Override
            public String getDiscountReason() {
                return appointment1To1.getRegistration_Accounting_DiscountReason();
            }

            @Override
            public Double getDiscount() {
                return appointment1To1.getRegistration_Accounting_Discount();
            }

            @Override
            public Double getWithdrawal() {
                return appointment1To1.getRegistration_Accounting_Withdrawal();
            }

            @Override
            public Instant getTransactionTime() {
                return appointment1To1.getRegistration_Accounting_TransactionTime();
            }

            @Override
            public String getStaff() {
                return appointment1To1.getRegistration_Accounting_Staff();
            }

            @Override
            public Long getHospital_Id() {
                return appointment1To1.getRegistration_Accounting_Hospital_Id();
            }

            @Override
            public Boolean getCopaymentExemption() {
                return appointment1To1.getRegistration_Accounting_CopaymentExemption();
            }
        };
    }

    public interface AppointmentRegistrationDisposal {

        Long getId();
    }

    public interface AppointmentRegistration {

        Instant getArrivalTime();
    }

    public interface AppointmentDoctor {

        Long getId();

        Long getUser_Id();

        String getUser_FirstName();
    }

    public interface Appointment1To1 extends AppointmentTable {

        // Patient
        String getPatient_Name();
        String getPatient_Phone();
        Gender getPatient_Gender();
        LocalDate getPatient_Birth();
        String getPatient_NationalId();
        String getPatient_MedicalId();
        String getPatient_Address();
        String getPatient_Email();
        Blood getPatient_Blood();
        String getPatient_CardId();
        String getPatient_Vip();
        String getPatient_EmergencyName();
        String getPatient_EmergencyPhone();
        Instant getPatient_DeleteDate();
        LocalDate getPatient_Scaling();
        String getPatient_LineId();
        String getPatient_FbId();
        String getPatient_Note();
        String getPatient_ClinicNote();
        Instant getPatient_WriteIcTime();
        String getPatient_AvatarContentType();
        Boolean getPatient_NewPatient();
        String getPatient_EmergencyAddress();
        String getPatient_EmergencyRelationship();
        String getPatient_MainNoticeChannel();
        String getPatient_Career();
        String getPatient_Marriage();
        String getPatient_TeethGraphPermanentSwitch();
        String getPatient_Introducer();
        LocalDate getPatient_DueDate();
        String getPatient_CreatedBy();
        String getPatient_LastModifiedBy();
        Instant getPatient_CreatedDate();
        Instant getPatient_LastModifiedDate();
        Long getPatient_Questionnaire_Id();
        Long getPatient_PatientIdentity_Id();
        Long getPatient_LastDoctorUser_Id();
        Long getPatient_FirstDoctorUser_Id();
        String getPatient_CaseManager();

        // Registration
        RegistrationStatus getRegistration_Status();
        Instant getRegistration_ArrivalTime();
        String getRegistration_Type();
        Boolean getRegistration_OnSite();
        Boolean getRegistration_NoCard();
        String getRegistration_AbnormalCode();
        String getRegistration_CreatedBy();
        String getRegistration_LastModifiedBy();
        Instant getRegistration_CreatedDate();
        Instant getRegistration_LastModifiedDate();

        // Disposal
        Long getRegistration_Disposal_Id();

        // Accounting
        Long getRegistration_Accounting_Id();
        Double getRegistration_Accounting_RegistrationFee();
        Double getRegistration_Accounting_PartialBurden();
        Double getRegistration_Accounting_Deposit();
        Double getRegistration_Accounting_OwnExpense();
        Double getRegistration_Accounting_Other();
        String getRegistration_Accounting_PatientIdentity();
        String getRegistration_Accounting_DiscountReason();
        Double getRegistration_Accounting_Discount();
        Double getRegistration_Accounting_Withdrawal();
        Instant getRegistration_Accounting_TransactionTime();
        String getRegistration_Accounting_Staff();
        Long getRegistration_Accounting_Hospital_Id();
        Boolean getRegistration_Accounting_CopaymentExemption();

        // Doctor
        Long getDoctor_User_Id();
        String getDoctor_User_FirstName();
    }
}
