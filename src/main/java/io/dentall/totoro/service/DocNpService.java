package io.dentall.totoro.service;

import io.dentall.totoro.domain.DocNp;
import io.dentall.totoro.repository.DocNpRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing DocNp.
 */
@Service
@Transactional
public class DocNpService {

    private final Logger log = LoggerFactory.getLogger(DocNpService.class);

    private final DocNpRepository docNpRepository;

    public DocNpService(DocNpRepository docNpRepository) {
        this.docNpRepository = docNpRepository;
    }

    /**
     * Save a docNp.
     *
     * @param docNp the entity to save
     * @return the persisted entity
     */
    public DocNp save(DocNp docNp) {
        log.debug("Request to save DocNp : {}", docNp);
        return docNpRepository.save(docNp);
    }

    /**
     * Get all the docNps.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<DocNp> findAll() {
        log.debug("Request to get all DocNps");
        return docNpRepository.findAll();
    }


    /**
     * Get one docNp by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<DocNp> findOne(Long id) {
        log.debug("Request to get DocNp : {}", id);
        return docNpRepository.findById(id);
    }

    /**
     * Delete the docNp by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DocNp : {}", id);
        docNpRepository.deleteById(id);
    }
}
