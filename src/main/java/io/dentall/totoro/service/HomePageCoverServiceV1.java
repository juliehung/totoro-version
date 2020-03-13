package io.dentall.totoro.service;

import io.dentall.totoro.domain.HomePageCover;
import io.dentall.totoro.domain.enumeration.HomePageCoverSourceTable;
import io.dentall.totoro.domain.enumeration.TotoroErrorKey;
import io.dentall.totoro.repository.DocNpRepository;
import io.dentall.totoro.repository.HomePageCoverRepository;
import io.dentall.totoro.repository.ImageRepository;
import io.dentall.totoro.repository.PatientRepository;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing HomePageCover.
 */
@Service
@Transactional
public class HomePageCoverServiceV1 {

    private final Logger log = LoggerFactory.getLogger(HomePageCoverServiceV1.class);

    private static final String ENTITY_NAME = "homePageCover";

    private final HomePageCoverRepository homePageCoverRepository;

    private final PatientRepository patientRepository;

    private final DocNpRepository docNpRepository;

    private final ImageRepository imageRepository;

    public HomePageCoverServiceV1(
        HomePageCoverRepository homePageCoverRepository,
        PatientRepository patientRepository,
        DocNpRepository docNpRepository,
        ImageRepository imageRepository
    ) {
        this.homePageCoverRepository = homePageCoverRepository;
        this.patientRepository = patientRepository;
        this.docNpRepository = docNpRepository;
        this.imageRepository = imageRepository;
    }

    private void validateNotNull(HomePageCover homePageCover) {
        if (homePageCover == null ||
            homePageCover.getPatientId() == null ||
            homePageCover.getSourceTable() == null ||
            homePageCover.getSourceId() == null
        ) {
            throw new BadRequestAlertException("Must fulfil patient id, source table, and source id",
                ENTITY_NAME,
                TotoroErrorKey.NOT_NULL.toString());
        }
    }

    private void validateNotFoundData(HomePageCover homePageCover) {
        if (!patientRepository.findById(homePageCover.getPatientId()).isPresent()) {
            throw new BadRequestAlertException("Patient id not exist",
                ENTITY_NAME,
                TotoroErrorKey.NOT_FOUND_DATA.toString());
        }

        switch (homePageCover.getSourceTable()) {
            case DOC_NP: {
                if (!docNpRepository.findById(homePageCover.getSourceId()).isPresent()) {
                    throw new BadRequestAlertException("DocNp id not exist",
                        ENTITY_NAME,
                        TotoroErrorKey.NOT_FOUND_DATA.toString());
                }

                break;
            }
            case IMAGE: {
                if (!imageRepository.findById(homePageCover.getSourceId()).isPresent()) {
                    throw new BadRequestAlertException("Image id not exist",
                        ENTITY_NAME,
                        TotoroErrorKey.NOT_FOUND_DATA.toString());
                }

                break;
            }
            default: {
                break;
            }
        }

    }

    private void validateData(HomePageCover homePageCover) {
        if (!HomePageCoverSourceTable.DOC_NP.equals(homePageCover.getSourceTable()) &&
            !HomePageCoverSourceTable.IMAGE.equals(homePageCover.getSourceTable())
        ) {
            throw new BadRequestAlertException("Source table must include in " + HomePageCoverSourceTable.listAll(),
                ENTITY_NAME,
                TotoroErrorKey.NOT_AVAILABLE_DATA.toString());
        }

    }

    private void validateExistance(HomePageCover homePageCover) {
        if (homePageCoverRepository.findById(homePageCover.getPatientId()).isPresent()) {
            throw new BadRequestAlertException("Already mapping coverage to patient. Try PUT api",
                ENTITY_NAME,
                TotoroErrorKey.NOT_AVAILABLE_DATA.toString());

        }
    }

    /**
     * Save a homePageCover.
     *
     * @param homePageCover the entity to save
     * @return the persisted entity
     */
    public HomePageCover save(HomePageCover homePageCover) {
        log.debug("Request to save HomePageCover : {}", homePageCover);
        validateNotNull(homePageCover);
        validateNotFoundData(homePageCover);
        validateData(homePageCover);
        validateExistance(homePageCover);

        return homePageCoverRepository.save(homePageCover);
    }

    public HomePageCover update(HomePageCover homePageCover) {
        log.debug("Request to update HomePageCover : {}", homePageCover);
        validateNotNull(homePageCover);
        validateNotFoundData(homePageCover);
        validateData(homePageCover);

        return homePageCoverRepository.save(homePageCover);
    }

    /**
     * Get all the homePageCovers.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<HomePageCover> findAll() {
        log.debug("Request to get all HomePageCovers");
        return homePageCoverRepository.findAll();
    }


    /**
     * Get one homePageCover by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<HomePageCover> findOne(Long id) {
        log.debug("Request to get HomePageCover : {}", id);
        return homePageCoverRepository.findById(id);
    }

    /**
     * Delete the homePageCover by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete HomePageCover : {}", id);
        homePageCoverRepository.deleteById(id);
    }
}
