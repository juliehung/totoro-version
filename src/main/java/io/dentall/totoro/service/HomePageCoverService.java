package io.dentall.totoro.service;

import io.dentall.totoro.domain.HomePageCover;
import io.dentall.totoro.repository.HomePageCoverRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing HomePageCover.
 */
@Deprecated
@Service
@Transactional
public class HomePageCoverService {

    private final Logger log = LoggerFactory.getLogger(HomePageCoverService.class);

    private final HomePageCoverRepository homePageCoverRepository;

    public HomePageCoverService(HomePageCoverRepository homePageCoverRepository) {
        this.homePageCoverRepository = homePageCoverRepository;
    }

    /**
     * Save a homePageCover.
     *
     * @param homePageCover the entity to save
     * @return the persisted entity
     */
    public HomePageCover save(HomePageCover homePageCover) {
        log.debug("Request to save HomePageCover : {}", homePageCover);
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
