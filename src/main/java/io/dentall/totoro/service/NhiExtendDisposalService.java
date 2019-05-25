package io.dentall.totoro.service;

import io.dentall.totoro.domain.NhiExtendDisposal;
import io.dentall.totoro.domain.NhiExtendTreatmentDrug;
import io.dentall.totoro.domain.NhiExtendTreatmentProcedure;
import io.dentall.totoro.domain.enumeration.NhiExtendDisposalUploadStatus;
import io.dentall.totoro.repository.NhiExtendDisposalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Service Implementation for managing NhiExtendDisposal.
 */
@Service
@Transactional
public class NhiExtendDisposalService {

    private final Logger log = LoggerFactory.getLogger(NhiExtendDisposalService.class);

    private final NhiExtendDisposalRepository nhiExtendDisposalRepository;

    private final RelationshipService relationshipService;

    public NhiExtendDisposalService(NhiExtendDisposalRepository nhiExtendDisposalRepository, RelationshipService relationshipService) {
        this.nhiExtendDisposalRepository = nhiExtendDisposalRepository;
        this.relationshipService = relationshipService;
    }

    /**
     * Save a nhiExtendDisposal.
     *
     * @param nhiExtendDisposal the entity to save
     * @return the persisted entity
     */
    public NhiExtendDisposal save(NhiExtendDisposal nhiExtendDisposal) {
        log.debug("Request to save NhiExtendDisposal : {}", nhiExtendDisposal);

        Set<NhiExtendTreatmentProcedure> nhiExtendTreatmentProcedures = nhiExtendDisposal.getNhiExtendTreatmentProcedures();
        Set<NhiExtendTreatmentDrug> nhiExtendTreatmentDrugs = nhiExtendDisposal.getNhiExtendTreatmentDrugs();
        if (nhiExtendDisposal.getUploadStatus() == null) {
            nhiExtendDisposal.setUploadStatus(NhiExtendDisposalUploadStatus.NONE);
        }

        nhiExtendDisposal = nhiExtendDisposalRepository.save(nhiExtendDisposal.nhiExtendTreatmentProcedures(null).nhiExtendTreatmentDrugs(null));

        relationshipService.addRelationshipWithNhiExtendTreatmentProcedures(nhiExtendDisposal, nhiExtendTreatmentProcedures);
        relationshipService.addRelationshipWithNhiExtendTreatmentDrugs(nhiExtendDisposal, nhiExtendTreatmentDrugs);

        return nhiExtendDisposal;
    }

    /**
     * Get all the nhiExtendDisposals.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<NhiExtendDisposal> findAll() {
        log.debug("Request to get all NhiExtendDisposals");
        return nhiExtendDisposalRepository.findAll();
    }

    /**
     * Get the nhiExtendDisposals by date.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<NhiExtendDisposal> findByDate(LocalDate date) {
        log.debug("Request to get all NhiExtendDisposals by date({})", date);
        return nhiExtendDisposalRepository.findByDate(date);
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
                    nhiExtendDisposal.setA11((updateNhiExtendDisposal.getA11()));
                }

                if (updateNhiExtendDisposal.getA12() != null) {
                    nhiExtendDisposal.setA12((updateNhiExtendDisposal.getA12()));
                }

                if (updateNhiExtendDisposal.getA13() != null) {
                    nhiExtendDisposal.setA13((updateNhiExtendDisposal.getA13()));
                }

                if (updateNhiExtendDisposal.getA14() != null) {
                    nhiExtendDisposal.setA14((updateNhiExtendDisposal.getA14()));
                }

                if (updateNhiExtendDisposal.getA15() != null) {
                    nhiExtendDisposal.setA15((updateNhiExtendDisposal.getA15()));
                }

                if (updateNhiExtendDisposal.getA16() != null) {
                    nhiExtendDisposal.setA16((updateNhiExtendDisposal.getA16()));
                }

                if (updateNhiExtendDisposal.getA17() != null) {
                    nhiExtendDisposal.setA17((updateNhiExtendDisposal.getA17()));
                }

                if (updateNhiExtendDisposal.getA18() != null) {
                    nhiExtendDisposal.setA18((updateNhiExtendDisposal.getA18()));
                }

                if (updateNhiExtendDisposal.getA19() != null) {
                    nhiExtendDisposal.setA19((updateNhiExtendDisposal.getA19()));
                }

                if (updateNhiExtendDisposal.getA22() != null) {
                    nhiExtendDisposal.setA22((updateNhiExtendDisposal.getA22()));
                }

                if (updateNhiExtendDisposal.getA23() != null) {
                    nhiExtendDisposal.setA23((updateNhiExtendDisposal.getA23()));
                }

                if (updateNhiExtendDisposal.getA25() != null) {
                    nhiExtendDisposal.setA25((updateNhiExtendDisposal.getA25()));
                }

                if (updateNhiExtendDisposal.getA26() != null) {
                    nhiExtendDisposal.setA26((updateNhiExtendDisposal.getA26()));
                }

                if (updateNhiExtendDisposal.getA27() != null) {
                    nhiExtendDisposal.setA27((updateNhiExtendDisposal.getA27()));
                }

                if (updateNhiExtendDisposal.getA31() != null) {
                    nhiExtendDisposal.setA31((updateNhiExtendDisposal.getA31()));
                }

                if (updateNhiExtendDisposal.getA32() != null) {
                    nhiExtendDisposal.setA32((updateNhiExtendDisposal.getA32()));
                }

                if (updateNhiExtendDisposal.getA41() != null) {
                    nhiExtendDisposal.setA41((updateNhiExtendDisposal.getA41()));
                }

                if (updateNhiExtendDisposal.getA42() != null) {
                    nhiExtendDisposal.setA42((updateNhiExtendDisposal.getA42()));
                }

                if (updateNhiExtendDisposal.getA43() != null) {
                    nhiExtendDisposal.setA43((updateNhiExtendDisposal.getA43()));
                }

                if (updateNhiExtendDisposal.getA44() != null) {
                    nhiExtendDisposal.setA44((updateNhiExtendDisposal.getA44()));
                }

                if (updateNhiExtendDisposal.getA54() != null) {
                    nhiExtendDisposal.setA54((updateNhiExtendDisposal.getA54()));
                }

                if (updateNhiExtendDisposal.getUploadStatus() != null) {
                    nhiExtendDisposal.setUploadStatus(updateNhiExtendDisposal.getUploadStatus());
                }

                log.debug("Update nhiExtendTreatmentProcedures({}) of NhiExtendDisposal(id: {})", updateNhiExtendDisposal.getNhiExtendTreatmentProcedures(), updateNhiExtendDisposal.getId());
                relationshipService.addRelationshipWithNhiExtendTreatmentProcedures(nhiExtendDisposal, updateNhiExtendDisposal.getNhiExtendTreatmentProcedures());

                log.debug("Update nhiExtendTreatmentDrugs({}) of NhiExtendDisposal(id: {})", updateNhiExtendDisposal.getNhiExtendTreatmentDrugs(), updateNhiExtendDisposal.getId());
                relationshipService.addRelationshipWithNhiExtendTreatmentDrugs(nhiExtendDisposal, updateNhiExtendDisposal.getNhiExtendTreatmentDrugs());

                return nhiExtendDisposal;
            })
            .get();
    }
}
