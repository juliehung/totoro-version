package io.dentall.totoro.service.mapper;

import io.dentall.totoro.domain.*;
import io.dentall.totoro.repository.*;
import io.dentall.totoro.service.dto.table.*;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class TreatmentProcedureMapper {

    private final NhiProcedureRepository nhiProcedureRepository;

    private final NhiExtendTreatmentProcedureRepository nhiExtendTreatmentProcedureRepository;

    private final ProcedureRepository procedureRepository;

    private final ToothRepository toothRepository;

    private final DisposalRepository disposalRepository;

    private final AppointmentRepository appointmentRepository;

    private final ExtendUserRepository extendUserRepository;

    private final NhiProcedureMapper nhiProcedureMapper;

    private final NhiExtendTreatmentProcedureMapper nhiExtendTreatmentProcedureMapper;

    private final ProcedureMapper procedureMapper;

    private final ToothMapper toothMapper;

    public TreatmentProcedureMapper(
        NhiProcedureRepository nhiProcedureRepository,
        NhiExtendTreatmentProcedureRepository nhiExtendTreatmentProcedureRepository,
        ProcedureRepository procedureRepository,
        ToothRepository toothRepository,
        DisposalRepository disposalRepository,
        AppointmentRepository appointmentRepository,
        ExtendUserRepository extendUserRepository,
        NhiProcedureMapper nhiProcedureMapper,
        NhiExtendTreatmentProcedureMapper nhiExtendTreatmentProcedureMapper,
        ProcedureMapper procedureMapper,
        ToothMapper toothMapper
    ) {
        this.nhiProcedureRepository = nhiProcedureRepository;
        this.nhiExtendTreatmentProcedureRepository = nhiExtendTreatmentProcedureRepository;
        this.procedureRepository = procedureRepository;
        this.toothRepository = toothRepository;
        this.disposalRepository = disposalRepository;
        this.appointmentRepository = appointmentRepository;
        this.extendUserRepository = extendUserRepository;
        this.nhiProcedureMapper = nhiProcedureMapper;
        this.nhiExtendTreatmentProcedureMapper = nhiExtendTreatmentProcedureMapper;
        this.procedureMapper = procedureMapper;
        this.toothMapper = toothMapper;
    }

    public TreatmentProcedure TreatmentProcedureTableToTreatmentProcedure(TreatmentProcedureTable treatmentProcedureTable) {
        TreatmentProcedure treatmentProcedure = new TreatmentProcedure();

        treatmentProcedure.setId(treatmentProcedureTable.getId());
        treatmentProcedure.setStatus(treatmentProcedureTable.getStatus());
        treatmentProcedure.setQuantity(treatmentProcedureTable.getQuantity());
        treatmentProcedure.setTotal(treatmentProcedureTable.getTotal());
        treatmentProcedure.setNote(treatmentProcedureTable.getNote());
        treatmentProcedure.setCompletedDate(treatmentProcedureTable.getCompletedDate());
        treatmentProcedure.setPrice(treatmentProcedureTable.getPrice());
        treatmentProcedure.setNhiCategory(treatmentProcedureTable.getNhiCategory());
        treatmentProcedure.setNhiDescription(treatmentProcedureTable.getNhiDescription());
        treatmentProcedure.setNhiIcd10Cm(treatmentProcedureTable.getNhiIcd10Cm());
        treatmentProcedure.setCreatedDate(treatmentProcedureTable.getCreatedDate());
        treatmentProcedure.setCreatedBy(treatmentProcedureTable.getCreatedBy());
        treatmentProcedure.setLastModifiedDate(treatmentProcedureTable.getLastModifiedDate());
        treatmentProcedure.setLastModifiedBy(treatmentProcedureTable.getLastModifiedBy());
        treatmentProcedure.setProxiedInspectionHospitalCode(treatmentProcedureTable.getProxiedInspectionHospitalCode());
        treatmentProcedure.setMode(treatmentProcedureTable.getMode());

        if (treatmentProcedureTable.getNhiProcedure_Id() != null) {
            NhiProcedure nhiProcedure = new NhiProcedure();
            nhiProcedure.setId(treatmentProcedureTable.getNhiProcedure_Id());
            treatmentProcedure.setNhiProcedure(nhiProcedure);
        }

        if (treatmentProcedureTable.getProcedure_Id() != null) {
            Procedure procedure = new Procedure();
            procedure.setId(treatmentProcedureTable.getProcedure_Id());
            treatmentProcedure.setProcedure(procedure);
        }

        if (treatmentProcedureTable.getDoctor_Id() != null) {
            ExtendUser extendUser = new ExtendUser();
            extendUser.setId(treatmentProcedureTable.getDoctor_Id());
            treatmentProcedure.setDoctor(extendUser);
        }

        if (treatmentProcedureTable.getNhiExtendTreatmentProcedure_Id() != null) {
            NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure = new NhiExtendTreatmentProcedure();
            nhiExtendTreatmentProcedure.setId(treatmentProcedureTable.getNhiExtendTreatmentProcedure_Id());
            treatmentProcedure.setNhiExtendTreatmentProcedure(nhiExtendTreatmentProcedure);
        }

        if (treatmentProcedureTable.getDisposal_Id() != null) {
            Disposal disposal = new Disposal();
            disposal.setId(treatmentProcedureTable.getDisposal_Id());
            treatmentProcedure.setDisposal(disposal);
        }

        return treatmentProcedure;
    }

    public void attachNhiProcedureToDomain(TreatmentProcedure tp) {
        if (tp.getNhiProcedure() != null &&
            tp.getNhiProcedure().getId() != null
        ) {
            Optional<NhiProcedureTable> opt = nhiProcedureRepository.findNhiProcedureById(tp.getNhiProcedure().getId());
            opt.ifPresent(table ->
                tp.setNhiProcedure(nhiProcedureMapper.nhiProcedureTableToNhiProcedure(table)));
        }
    }

    public void attachNhiTreatmentProcedureToDomain(TreatmentProcedure tp) {
        if (tp.getNhiExtendTreatmentProcedure() != null &&
            tp.getNhiExtendTreatmentProcedure().getId() != null
        ) {
            Optional<NhiExtendTreatmentProcedureTable> opt = nhiExtendTreatmentProcedureRepository.findById(tp.getNhiExtendTreatmentProcedure().getId(), NhiExtendTreatmentProcedureTable.class);
            opt.ifPresent(table ->
                tp.setNhiExtendTreatmentProcedure(nhiExtendTreatmentProcedureMapper.nhiExtendTreatmentProcedureTableToNhiExtendTreatmentProcedureTable(table)));
        }
    }

    public void attachProcedureToDomain(TreatmentProcedure tp) {
        if (tp.getProcedure() != null &&
            tp.getProcedure().getId() != null
        ) {
            Optional<ProcedureTable> opt = procedureRepository.findProcedureById(tp.getProcedure().getId());
            opt.ifPresent(table ->
                tp.setProcedure(procedureMapper.procedureTableToProcedure(table)));
        }
    }

    public void attachToothToDomain(TreatmentProcedure tp) {
        if (tp.getId() != null) {
            Set<ToothTable> tooth = toothRepository.findToothByTreatmentProcedure_Id(tp.getId());
            tp.setTeeth(toothMapper.toothSetToToothSet(tooth));
        }
    }


    public void attachDisposalToDomain(TreatmentProcedure tp) {
        if (tp.getDisposal() != null &&
            tp.getDisposal().getId() != null
        ) {
            Optional<DisposalTable> opt = disposalRepository.findDisposalById(tp.getDisposal().getId());
            if (opt.isPresent()) {
                Optional<AppointmentTable> optionalAppointmentTable = appointmentRepository
                    .findAppointmentByRegistration_Id(opt.get().getRegistration_Id());

                tp.setDoctor(extendUserRepository.findById(optionalAppointmentTable.get().getDoctorUser_Id()).orElse(null));
            }
        }
    }
}
