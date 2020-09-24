package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.Disposal;
import io.dentall.totoro.domain.NhiExtendDisposal;
import io.dentall.totoro.domain.NhiExtendTreatmentDrug;
import io.dentall.totoro.domain.NhiExtendTreatmentProcedure;
import io.dentall.totoro.service.DisposalService;
import io.dentall.totoro.service.NhiExtendDisposalService;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.util.HeaderUtil;
import io.dentall.totoro.web.rest.util.PaginationUtil;
import io.dentall.totoro.web.rest.vm.NhiExtendDisposalVM;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * REST controller for managing NhiExtendDisposal.
 */
@RestController
@RequestMapping("/api")
public class NhiExtendDisposalResource {

    private final Logger log = LoggerFactory.getLogger(NhiExtendDisposal.class);

    private static final String ENTITY_NAME = "nhiExtendDisposal";

    private final NhiExtendDisposalService nhiExtendDisposalService;

    private final DisposalService disposalService;

    public NhiExtendDisposalResource(
        NhiExtendDisposalService nhiExtendDisposalService,
        DisposalService disposalService
    ) {
        this.nhiExtendDisposalService = nhiExtendDisposalService;
        this.disposalService = disposalService;
    }

    /**
     * POST  /nhi-extend-disposals : Create a new nhiExtendDisposal.
     *
     * @param nhiExtendDisposal the nhiExtendDisposal to create
     * @return the ResponseEntity with status 201 (Created) and with body the new nhiExtendDisposal, or with status 400 (Bad Request) if the nhiExtendDisposal has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/nhi-extend-disposals")
    @Timed
    public ResponseEntity<NhiExtendDisposal> createNhiExtendDisposal(@RequestBody NhiExtendDisposal nhiExtendDisposal) throws URISyntaxException {
        log.debug("REST request to save NhiExtendDisposal : {}", nhiExtendDisposal);
        if (nhiExtendDisposal.getId() != null) {
            throw new BadRequestAlertException("A new nhiExtendDisposal cannot already have an ID", ENTITY_NAME, "idexists");
        }

        if (nhiExtendDisposal.getDisposal() != null &&
            nhiExtendDisposal.getDisposal().getId() != null
        ) {
            Optional<Disposal> optionalDisposal = disposalService.findOne(nhiExtendDisposal.getDisposal().getId());
            if (optionalDisposal.isPresent() &&
                optionalDisposal.get().getNhiExtendDisposals().size() > 0
            ) {

                throw new BadRequestAlertException(
                    "Already exist nhi_extend_disposal with disposal id " + nhiExtendDisposal.getDisposal().getId(),
                    ENTITY_NAME,
                    "notonetoonerelationship");
            }
        }

        NhiExtendDisposal result = nhiExtendDisposalService.save(nhiExtendDisposal);
        log.debug("REST request to save NhiExtendDisposal result : {}", result);
        return ResponseEntity.created(new URI("/api/nhi-extend-disposals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /nhi-extend-disposals : Updates an existing nhiExtendDisposal.
     *
     * @param nhiExtendDisposal the nhiExtendDisposal to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated nhiExtendDisposal,
     * or with status 400 (Bad Request) if the nhiExtendDisposal is not valid,
     * or with status 500 (Internal Server Error) if the nhiExtendDisposal couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/nhi-extend-disposals")
    @Timed
    public ResponseEntity<NhiExtendDisposal> updateNhiExtendDisposal(@RequestBody NhiExtendDisposal nhiExtendDisposal) throws URISyntaxException {
        log.debug("REST request to update NhiExtendDisposal : {}", nhiExtendDisposal);
        if (nhiExtendDisposal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NhiExtendDisposal result = nhiExtendDisposalService.update(nhiExtendDisposal);
        log.debug("REST request to update NhiExtendDisposal result : {}", result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, nhiExtendDisposal.getId().toString()))
            .body(result);
    }

    /**
     * GET  /nhi-extend-disposals : get all the nhiExtendDisposalVMs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of nhiExtendDisposalVMs in body
     */
    @GetMapping("/nhi-extend-disposals")
    @Timed
    public List<NhiExtendDisposalVM> getAllNhiExtendDisposals(
        @RequestParam(required = false) LocalDate date,
        @RequestParam(required = false) Integer yyyymm,
        @RequestParam(required = false) Long patientId
    ) {
        log.debug("REST request to get all NhiExtendDisposalVMs");

        if (date != null) {
            return nhiExtendDisposalService.findByDate(date)
                .stream()
                .map(nhiExtendDisposalVM -> {
                    // Query and assemble disposal.* to nhi_ext_disposal
                    if (nhiExtendDisposalVM.getNhiExtendDisposal() != null &&
                        nhiExtendDisposalVM.getNhiExtendDisposal().getDisposal() != null &&
                        nhiExtendDisposalVM.getNhiExtendDisposal().getDisposal().getId() != null
                    ) {
                        Disposal disposal = disposalService.getDisposalByProjection(nhiExtendDisposalVM.getNhiExtendDisposal().getDisposal().getId());
                        if (disposal != null) {
                            nhiExtendDisposalVM.getNhiExtendDisposal().setDisposal(disposal);
                        }

                        Set<NhiExtendTreatmentProcedure> nhiExtendTreatmentProcedures = new HashSet<>();
                        Set<NhiExtendTreatmentDrug> nhiExtendTreatmentDrugs = new HashSet<>();
                        if (disposal.getTreatmentProcedures() != null) {
                            // Assemble nhi_treatment_procedure
                            disposal.getTreatmentProcedures()
                                .stream()
                                .forEach(treatmentProcedure -> {
                                    if (treatmentProcedure.getNhiExtendTreatmentProcedure() !=  null) {
                                        nhiExtendTreatmentProcedures.add(treatmentProcedure.getNhiExtendTreatmentProcedure());
                                    }
                                });
                            nhiExtendDisposalVM.getNhiExtendDisposal().setNhiExtendTreatmentProcedures(nhiExtendTreatmentProcedures);

                            // Assemble nhi_treatment_drug
                            disposal.getPrescription().getTreatmentDrugs()
                                .stream()
                                .forEach(treatmentDrug -> {
                                    if (treatmentDrug.getNhiExtendTreatmentDrug() != null) {
                                        nhiExtendTreatmentDrugs.add(treatmentDrug.getNhiExtendTreatmentDrug());
                                    }
                                });
                            nhiExtendDisposalVM.getNhiExtendDisposal().setNhiExtendTreatmentDrugs(nhiExtendTreatmentDrugs);

                        }
                    }

                    return nhiExtendDisposalVM;
                })
                .collect(Collectors.toList());

        } else if (yyyymm != null) {
            return nhiExtendDisposalService.findByYearMonth(yyyymm);
        } else if (patientId != null) {
            return nhiExtendDisposalService.findByPatientId(patientId);
        } else {
            return nhiExtendDisposalService.findAll();
        }
    }

    @Deprecated
    @GetMapping("/nhi-extend-disposals/page")
    @Timed
    public ResponseEntity<List<NhiExtendDisposalVM>> getAllNhiExtendDisposals(
        @RequestParam Integer yyyymm,
        @PageableDefault(size = 50, sort = "id") Pageable pageable
    ) {
        log.debug("REST request to get paged NhiExtendDisposalVMs");

        Page<NhiExtendDisposalVM> pagedNhiDis = nhiExtendDisposalService.findByYearMonth(yyyymm, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(pagedNhiDis, "/api/nhi-extend-disposals/page");

        return ResponseEntity.ok().headers(headers).body(pagedNhiDis.getContent());
    }

    /**
     * GET  /nhi-extend-disposals/:id : get the "id" nhiExtendDisposalVM.
     *
     * @param id the id of the nhiExtendDisposal to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the nhiExtendDisposalVM, or with status 404 (Not Found)
     */
    @GetMapping("/nhi-extend-disposals/{id}")
    @Timed
    public ResponseEntity<NhiExtendDisposalVM> getNhiExtendDisposal(@PathVariable Long id) {
        log.debug("REST request to get NhiExtendDisposal : {}", id);
        Optional<NhiExtendDisposal> nhiExtendDisposal = nhiExtendDisposalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nhiExtendDisposal.map(NhiExtendDisposalVM::new));
    }

    /**
     * DELETE  /nhi-extend-disposals/:id : delete the "id" nhiExtendDisposal.
     *
     * @param id the id of the nhiExtendDisposal to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/nhi-extend-disposals/{id}")
    @Timed
    public ResponseEntity<Void> deleteNhiExtendDisposal(@PathVariable Long id) {
        log.debug("REST request to delete NhiExtendDisposal : {}", id);
        nhiExtendDisposalService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/nhi-extend-disposals/simple/disposals/{id}")
    @Timed
    public ResponseEntity<NhiExtendDisposalService.NhiExtendDisposalSimple> getSimpleByDisposalId(@PathVariable Long id) {
        log.debug("REST request to get NhiExtendDisposalSimple by Disposal[{}]", id);

        return ResponseUtil.wrapOrNotFound(nhiExtendDisposalService.getSimpleByDisposalId(id));
    }
}
