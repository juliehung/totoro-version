package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.Drug;
import io.dentall.totoro.repository.DrugRepository;
import io.dentall.totoro.service.DrugQueryService;
import io.dentall.totoro.service.DrugService;
import io.dentall.totoro.service.dto.DrugCriteria;
import io.dentall.totoro.service.dto.PlainDisposalInfoListDTO;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Drug.
 */
@RestController
@RequestMapping("/api")
public class DrugResource {

    private final Logger log = LoggerFactory.getLogger(DrugResource.class);

    private static final String ENTITY_NAME = "drug";

    private final DrugService drugService;

    private final DrugQueryService drugQueryService;

    private final DrugRepository drugRepository;

    public DrugResource(
        DrugService drugService,
        DrugQueryService drugQueryService,
        DrugRepository drugRepository
    ) {
        this.drugService = drugService;
        this.drugQueryService = drugQueryService;
        this.drugRepository = drugRepository;
    }

    /**
     * POST  /drugs : Create a new drug.
     *
     * @param drug the drug to create
     * @return the ResponseEntity with status 201 (Created) and with body the new drug, or with status 400 (Bad Request) if the drug has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/drugs")
    @Timed
    public ResponseEntity<Drug> createDrug(@Valid @RequestBody Drug drug) throws URISyntaxException {
        log.debug("REST request to save Drug : {}", drug);
        if (drug.getId() != null) {
            throw new BadRequestAlertException("A new drug cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Drug result = drugService.save(drug);
        return ResponseEntity.created(new URI("/api/drugs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /drugs : Updates an existing drug.
     *
     * @param drug the drug to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated drug,
     * or with status 400 (Bad Request) if the drug is not valid,
     * or with status 500 (Internal Server Error) if the drug couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/drugs")
    @Timed
    public ResponseEntity<Drug> updateDrug(@Valid @RequestBody Drug drug) throws URISyntaxException {
        log.debug("REST request to update Drug : {}", drug);
        if (drug.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Drug result = drugService.save(drug);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, drug.getId().toString()))
            .body(result);
    }

    /**
     * GET  /drugs : get all the drugs.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of drugs in body
     */
    @GetMapping("/drugs")
    @Timed
    public ResponseEntity<List<Drug>> getAllDrugs(DrugCriteria criteria) {
        log.debug("REST request to get Drugs by criteria: {}", criteria);
        List<Drug> entityList = drugQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * GET  /drugs/count : count all the drugs.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/drugs/count")
    @Timed
    public ResponseEntity<Long> countDrugs(DrugCriteria criteria) {
        log.debug("REST request to count Drugs by criteria: {}", criteria);
        return ResponseEntity.ok().body(drugQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /drugs/:id : get the "id" drug.
     *
     * @param id the id of the drug to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the drug, or with status 404 (Not Found)
     */
    @GetMapping("/drugs/{id}")
    @Timed
    public ResponseEntity<Drug> getDrug(@PathVariable Long id) {
        log.debug("REST request to get Drug : {}", id);
        Optional<Drug> drug = drugService.findOne(id);
        return ResponseUtil.wrapOrNotFound(drug);
    }

    @GetMapping("/drugs/brief")
    @Timed
    public List<PlainDisposalInfoListDTO> getBriefDrug() {
        log.debug("REST request to get brief Drug");
        return drugRepository.findAll(new Sort(Sort.Direction.ASC, "order")).stream()
            .map(drug -> {
                PlainDisposalInfoListDTO vm = new PlainDisposalInfoListDTO();
                return vm.id(drug.getId()).code("").name(drug.getChineseName());
            })
            .collect(Collectors.toList());
    }
}
