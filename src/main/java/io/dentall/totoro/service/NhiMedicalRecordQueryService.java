package io.dentall.totoro.service;

import io.dentall.totoro.domain.*;
import io.dentall.totoro.domain.enumeration.NhiMedicalRecordCategory;
import io.dentall.totoro.repository.NhiMedicalRecordRepository;
import io.dentall.totoro.repository.NhiMedicineRepository;
import io.dentall.totoro.repository.NhiTxRepository;
import io.dentall.totoro.service.dto.NhiMedicalRecordCriteria;
import io.dentall.totoro.web.rest.vm.NhiMedicalRecordVM;
import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.JoinType;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for executing complex queries for NhiMedicalRecord entities in the database.
 * The main input is a {@link NhiMedicalRecordCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NhiMedicalRecord} or a {@link Page} of {@link NhiMedicalRecord} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NhiMedicalRecordQueryService extends QueryService<NhiMedicalRecord> {

    private final Logger log = LoggerFactory.getLogger(NhiMedicalRecordQueryService.class);

    private final NhiMedicalRecordRepository nhiMedicalRecordRepository;

    private final NhiMedicineRepository nhiMedicineRepository;

    private final NhiTxRepository nhiTxRepository;

    private final NhiMedicalRecordService nhiMedicalRecordService;

    public NhiMedicalRecordQueryService(
        NhiMedicalRecordRepository nhiMedicalRecordRepository,
        NhiMedicineRepository nhiMedicineRepository,
        NhiTxRepository nhiTxRepository,
        NhiMedicalRecordService nhiMedicalRecordService
    ) {
        this.nhiMedicalRecordRepository = nhiMedicalRecordRepository;
        this.nhiMedicineRepository = nhiMedicineRepository;
        this.nhiTxRepository = nhiTxRepository;
        this.nhiMedicalRecordService = nhiMedicalRecordService;
    }

    /**
     * Return a {@link List} of {@link NhiMedicalRecord} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NhiMedicalRecord> findByCriteria(NhiMedicalRecordCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<NhiMedicalRecord> specification = createSpecification(criteria);
        return nhiMedicalRecordRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link NhiMedicalRecord} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NhiMedicalRecord> findByCriteria(NhiMedicalRecordCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<NhiMedicalRecord> specification = createSpecification(criteria);
        return nhiMedicalRecordRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NhiMedicalRecordCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<NhiMedicalRecord> specification = createSpecification(criteria);
        return nhiMedicalRecordRepository.count(specification);
    }

    /**
     * Function to convert NhiMedicalRecordCriteria to a {@link Specification}
     */
    private Specification<NhiMedicalRecord> createSpecification(NhiMedicalRecordCriteria criteria) {
        Specification<NhiMedicalRecord> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), NhiMedicalRecord_.id));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDate(), NhiMedicalRecord_.date));
            }
            if (criteria.getNhiCategory() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNhiCategory(), NhiMedicalRecord_.nhiCategory));
            }
            if (criteria.getNhiCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNhiCode(), NhiMedicalRecord_.nhiCode));
            }
            if (criteria.getPart() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPart(), NhiMedicalRecord_.part));
            }
            if (criteria.getUsage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsage(), NhiMedicalRecord_.usage));
            }
            if (criteria.getTotal() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTotal(), NhiMedicalRecord_.total));
            }
            if (criteria.getNote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNote(), NhiMedicalRecord_.note));
            }
            if (criteria.getDays() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDays(), NhiMedicalRecord_.days));
            }
            if (criteria.getNhiExtendPatientId() != null) {
                specification = specification.and(buildSpecification(criteria.getNhiExtendPatientId(),
                    root -> root.join(NhiMedicalRecord_.nhiExtendPatient, JoinType.LEFT).get(NhiExtendPatient_.id)));
            }
        }
        return specification;
    }

    @Transactional(readOnly = true)
    public Page<NhiMedicalRecordVM> findVmByCriteria(NhiMedicalRecordCriteria criteria, Pageable pageable) {
        log.debug("find by criteria : {}", criteria);
        final Specification<NhiMedicalRecord> specification = createSpecification(criteria);
        List<NhiMedicalRecordVM> entityList = new ArrayList<>();
        Page<NhiMedicalRecord> queryResult = nhiMedicalRecordRepository.findAll(specification, pageable);
        List<NhiMedicalRecord> content = nhiMedicalRecordService.ignoreCancelledRecord(queryResult.getContent());
        content.forEach(e -> {
            NhiMedicalRecordVM vm = new NhiMedicalRecordVM();
            vm.setNhiMedicalRecord(e);
            if (e.getNhiCategory() != null && e.getNhiCategory().equals(NhiMedicalRecordCategory.MEDICINE.getNumber())) {
                NhiMedicine m = nhiMedicineRepository.findTop1ByMedicineCodeAndMedicineMandarinIsNotNullOrderByIdDesc(e.getNhiCode());
                if (m != null) {
                    vm.setMandarin(m.getMedicineMandarin());
                }
            } else {
                NhiTx t = nhiTxRepository.findTop1ByNhiCodeAndNhiMandarinIsNotNullOrderByIdDesc(e.getNhiCode());
                if (t != null) {
                    vm.setMandarin(t.getNhiMandarin());
                }
            }
            entityList.add(vm);
        });

        return new PageImpl<>(entityList, pageable, queryResult.getTotalElements());
    }

    @Transactional(readOnly = true)
    public Page<NhiMedicalRecordVM> findVmByCriteria(NhiMedicalRecordCriteria criteria) {
        final Specification<NhiMedicalRecord> specification = createSpecification(criteria);
        List<NhiMedicalRecordVM> entityList = new ArrayList<>();
        Sort sort = new Sort(Sort.Direction.DESC, "date");
        List<NhiMedicalRecord> nmrs = nhiMedicalRecordRepository.findAll(specification, sort);
        nhiMedicalRecordService.ignoreCancelledRecord(nmrs).forEach(e -> {
            NhiMedicalRecordVM vm = new NhiMedicalRecordVM();
            vm.setNhiMedicalRecord(e);
            if (e.getNhiCategory() != null && e.getNhiCategory().equals(NhiMedicalRecordCategory.MEDICINE.getNumber())) {
                NhiMedicine m = nhiMedicineRepository.findTop1ByMedicineCodeAndMedicineMandarinIsNotNullOrderByIdDesc(e.getNhiCode());
                if (m != null) {
                    vm.setMandarin(m.getMedicineMandarin());
                }
            } else {
                NhiTx t = nhiTxRepository.findTop1ByNhiCodeAndNhiMandarinIsNotNullOrderByIdDesc(e.getNhiCode());
                if (t != null) {
                    vm.setMandarin(t.getNhiMandarin());
                }
            }
            entityList.add(vm);
        });

        return new PageImpl<>(entityList);
    }
}