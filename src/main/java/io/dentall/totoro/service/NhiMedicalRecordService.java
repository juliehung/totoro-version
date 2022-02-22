package io.dentall.totoro.service;

import io.dentall.totoro.domain.NhiExtendPatient;
import io.dentall.totoro.domain.NhiMedicalRecord;
import io.dentall.totoro.domain.NhiProcedure;
import io.dentall.totoro.repository.NhiMedicalRecordRepository;
import io.dentall.totoro.service.util.DateTimeUtil;
import io.dentall.totoro.web.rest.vm.NhiMedicalRecordVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing NhiMedicalRecord.
 */
@Service
@Transactional
public class NhiMedicalRecordService {

    private final Logger log = LoggerFactory.getLogger(NhiMedicalRecordService.class);

    private final NhiMedicalRecordRepository nhiMedicalRecordRepository;

    public NhiMedicalRecordService(NhiMedicalRecordRepository nhiMedicalRecordRepository) {
        this.nhiMedicalRecordRepository = nhiMedicalRecordRepository;
    }

    public List<NhiMedicalRecord> ignoreCancelledRecord(List<NhiMedicalRecord> nhiMedicalRecords) {
        List<NhiMedicalRecord> result = new ArrayList<>();
        Pattern cancelNhiCategoryPattern = Pattern.compile("[a-eA-E]");
        Map<String, List<NhiMedicalRecord>> nmrMap = nhiMedicalRecords.stream()
            .collect(
                Collectors.groupingBy(d ->
                    d.getDate()
                        .concat(d.getNhiCode())
                        .concat(d.getPart())
                        .concat(d.getUsage())
                )
            );

        List<String> sortedKey = nmrMap.keySet().stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        for (String key : sortedKey) {
            try {
                boolean isNotCancelled = true;
                NhiMedicalRecord maxIdRecord = null;
                for (NhiMedicalRecord nmr : nmrMap.get(key)) {
                    if (cancelNhiCategoryPattern.matcher(nmr.getNhiCategory()).matches()) {
                        isNotCancelled = false;
                        break;
                    }
                    if (maxIdRecord != null) {
                        if (maxIdRecord.getId() < nmr.getId()) {
                            maxIdRecord = nmr;
                        }
                    } else {
                        maxIdRecord = nmr;
                    }
                }

                if (isNotCancelled) {
                    result.add(maxIdRecord);
                }
            } catch (Exception e) {
                log.error("NhiMedicalRecord ignore cancelled record throw exception during comparing.");
                log.error(e.getMessage());
            }
        }

        return result;
    }

    public List<NhiMedicalRecord> findByPatientId(Long pid) {
        return nhiMedicalRecordRepository.findByNhiExtendPatient_Patient_IdOrderByDateDesc(pid);
    }

    public List<NhiMedicalRecord> findByPatientIdAndNhiCodes(Long pid, List<String> nhiCodes) {
        return nhiMedicalRecordRepository.findByNhiExtendPatient_Patient_IdAndNhiCodeInOrderByDateDesc(pid, nhiCodes);
    }

    /**
     * Save a nhiMedicalRecord.
     *
     * @param nhiMedicalRecord the entity to save
     * @return the persisted entity
     */
    public NhiMedicalRecord save(NhiMedicalRecord nhiMedicalRecord) {
        log.debug("Request to save NhiMedicalRecord : {}", nhiMedicalRecord);
        return nhiMedicalRecordRepository.save(nhiMedicalRecord);
    }

    /**
     * Get all the nhiMedicalRecords.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<NhiMedicalRecord> findAll() {
        log.debug("Request to get all NhiMedicalRecords");
        return nhiMedicalRecordRepository.findAll();
    }


    /**
     * Get one nhiMedicalRecord by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<NhiMedicalRecord> findOne(Long id) {
        log.debug("Request to get NhiMedicalRecord : {}", id);
        return nhiMedicalRecordRepository.findById(id);
    }

    /**
     * Delete the nhiMedicalRecord by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete NhiMedicalRecord : {}", id);

        nhiMedicalRecordRepository.findById(id).ifPresent(nhiMedicalRecord -> {
            if (nhiMedicalRecord.getNhiExtendPatient() != null) {
                NhiExtendPatient nhiExtendPatient = nhiMedicalRecord.getNhiExtendPatient();
                nhiExtendPatient.getNhiMedicalRecords().remove(nhiMedicalRecord);
            }

            nhiMedicalRecordRepository.deleteById(id);
        });
    }

    /**
     * Update the nhiMedicalRecord.
     *
     * @param updateNhiMedicalRecord the update entity
     */
    public NhiMedicalRecord update(NhiMedicalRecord updateNhiMedicalRecord) {
        log.debug("Request to update NhiMedicalRecord : {}", updateNhiMedicalRecord);

        return nhiMedicalRecordRepository
            .findById(updateNhiMedicalRecord.getId())
            .map(nhiMedicalRecord -> {
                if (updateNhiMedicalRecord.getDate() != null) {
                    nhiMedicalRecord.setDate(updateNhiMedicalRecord.getDate());
                }

                if (updateNhiMedicalRecord.getNhiCategory() != null) {
                    nhiMedicalRecord.setNhiCategory(updateNhiMedicalRecord.getNhiCategory());
                }

                if (updateNhiMedicalRecord.getNhiCode() != null) {
                    nhiMedicalRecord.setNhiCode(updateNhiMedicalRecord.getNhiCode());
                }

                if (updateNhiMedicalRecord.getPart() != null) {
                    nhiMedicalRecord.setPart(updateNhiMedicalRecord.getPart());
                }

                if (updateNhiMedicalRecord.getUsage() != null) {
                    nhiMedicalRecord.setUsage(updateNhiMedicalRecord.getUsage());
                }

                if (updateNhiMedicalRecord.getTotal() != null) {
                    nhiMedicalRecord.setTotal(updateNhiMedicalRecord.getTotal());
                }

                if (updateNhiMedicalRecord.getNote() != null) {
                    nhiMedicalRecord.setNote(updateNhiMedicalRecord.getNote());
                }

                if (updateNhiMedicalRecord.getDays() != null) {
                    nhiMedicalRecord.setDays(updateNhiMedicalRecord.getDays());
                }

                return nhiMedicalRecord;
            })
            .get();
    }


    @Transactional(readOnly = true)
    public void updateNhiMedicalRecordWithoutDuplicated(Long pid, List<NhiMedicalRecord> nmrs) {
        List<NhiMedicalRecord> newNhiMedicalRecords = new ArrayList<>();
        Set<String> existedIcRecordSet = new HashSet<>();
        NhiExtendPatient nep = new NhiExtendPatient();
        nep.setId(pid);

        nhiMedicalRecordRepository.findByNhiExtendPatient_Patient_IdOrderByDateDesc(pid)
            .forEach(d -> {
                existedIcRecordSet.add(
                    "".concat(d.getDate())
                        .concat(d.getNhiCategory())
                        .concat(d.getNhiCode())
                        .concat(d.getPart())
                );
            });

        nmrs.forEach(d -> {
            String key = "".concat(d.getDate())
                .concat(d.getNhiCategory())
                .concat(d.getNhiCode())
                .concat(d.getPart());

            if (!existedIcRecordSet.contains(key)) {
                existedIcRecordSet.add(key);
                d.setNhiExtendPatient(nep);
                newNhiMedicalRecords.add(d);
            }
        });

        nhiMedicalRecordRepository.saveAll(newNhiMedicalRecords);
    }

    public NhiMedicalRecordVM setValidatedNhiProcedure(
        NhiMedicalRecordVM vm,
        Map<String, List<NhiProcedure>> idCodeToNhiProcedureMap
    ) {
        NhiMedicalRecord nmr = vm.getNhiMedicalRecord();
        // 轉出來的 Instant 無法利用 offset 轉到正確的時間點，因此採取 -8hrs 強制對齊 db timestamp
        Instant nmrTxTime = DateTimeUtil.transformROCDateToInstant(nmr.getDate()).minus(8L, ChronoUnit.HOURS);
        if (idCodeToNhiProcedureMap.containsKey(nmr.getNhiCode())) {
            idCodeToNhiProcedureMap.get(nmr.getNhiCode())
                .stream()
                .filter(np -> {
                    if (np.getEffectiveTime() != null &&
                        nmrTxTime.isBefore(np.getEffectiveTime())
                    ) {
                        return false;
                    }

                    if (np.getExpirationTime() != null &&
                        nmrTxTime.isAfter(np.getExpirationTime())
                    ) {
                        return false;
                    }

                    return true;
                })
                .findFirst()
                .ifPresent(np -> vm.setMandarin(np.getName()));
        }
        return vm;
    };
}
