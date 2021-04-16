package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.business.service.nhi.util.NhiRuleCheckUtil;
import io.dentall.totoro.business.vm.nhi.NhiRuleCheckBody;
import io.dentall.totoro.business.vm.nhi.NhiRuleCheckResultVM;
import io.dentall.totoro.business.vm.nhi.NhiRuleCheckTxSnapshot;
import io.dentall.totoro.domain.*;
import io.dentall.totoro.repository.NhiExtendDisposalRepository;
import io.dentall.totoro.service.DisposalQueryService;
import io.dentall.totoro.service.DisposalService;
import io.dentall.totoro.service.NhiService;
import io.dentall.totoro.service.dto.DisposalCriteria;
import io.dentall.totoro.service.dto.HybridRuleCheckDisposal;
import io.dentall.totoro.service.dto.HybridRuleCheckTreatmentProcedure;
import io.dentall.totoro.service.dto.table.NhiExtendDisposalTable;
import io.dentall.totoro.service.util.DateTimeUtil;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.util.HeaderUtil;
import io.dentall.totoro.web.rest.util.PaginationUtil;
import io.dentall.totoro.web.rest.vm.DisposalV2VM;
import io.dentall.totoro.web.rest.vm.SameTreatmentVM;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.*;

/**
 * REST controller for managing Disposal.
 */
@RestController
@RequestMapping("/api")
public class DisposalResource {

    private static final String ENTITY_NAME = "disposal";

    private final Logger log = LoggerFactory.getLogger(DisposalResource.class);

    private final DisposalService disposalService;

    private final DisposalQueryService disposalQueryService;

    private final NhiService nhiService;

    private final NhiRuleCheckUtil nhiRuleCheckUtil;

    private final NhiExtendDisposalRepository nhiExtendDisposalRepository;

    public DisposalResource(
        DisposalService disposalService,
        DisposalQueryService disposalQueryService,
        NhiService nhiService,
        NhiRuleCheckUtil nhiRuleCheckUtil,
        NhiExtendDisposalRepository nhiExtendDisposalRepository
    ) {
        this.disposalService = disposalService;
        this.nhiExtendDisposalRepository = nhiExtendDisposalRepository;
        this.disposalQueryService = disposalQueryService;
        this.nhiService = nhiService;
        this.nhiRuleCheckUtil = nhiRuleCheckUtil;
    }

    /**
     * POST  /disposals : Create a new disposal.
     *
     * @param disposal the disposal to create
     * @return the ResponseEntity with status 201 (Created) and with body the new disposal, or with status 400 (Bad Request) if the disposal has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/disposals")
    @Timed
    public ResponseEntity<Disposal> createDisposal(@Valid @RequestBody Disposal disposal) throws URISyntaxException {
        log.debug("REST request to save Disposal : {}", disposal);
        if (disposal.getId() != null) {
            throw new BadRequestAlertException("A new disposal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (disposal.getNhiExtendDisposals() != null && disposal.getNhiExtendDisposals().size() > 1) {
            throw new BadRequestAlertException("Disposal and nhi extend disposal must be one-to-one relationship.", ENTITY_NAME, "relationconflict");
        }
        Disposal result = disposalService.save(disposal);
        log.debug("REST request to save Disposal result : {}", result);
        return ResponseEntity.created(new URI("/api/disposals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /disposals : Updates an existing disposal.
     *
     * @param disposal the disposal to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated disposal,
     * or with status 400 (Bad Request) if the disposal is not valid,
     * or with status 500 (Internal Server Error) if the disposal couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/disposals")
    @Timed
    public ResponseEntity<Disposal> updateDisposal(@Valid @RequestBody Disposal disposal) throws URISyntaxException {
        log.debug("REST request to update Disposal : {}", disposal);
        if (disposal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (disposal.getNhiExtendDisposals() != null && disposal.getNhiExtendDisposals().size() > 1) {
            throw new BadRequestAlertException("Disposal and nhi extend disposal must be one-to-one relationship.", ENTITY_NAME, "relationconflict");
        }
        Disposal result = disposalService.update(disposal);
        log.debug("REST request to update Disposal result : {}", result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, disposal.getId().toString()))
            .body(result);
    }

    /**
     * GET  /disposals : get all the disposals.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of disposals in body
     */
    @GetMapping("/disposals")
    @Timed
    public ResponseEntity<List<DisposalV2VM>> getAllDisposals(DisposalCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Disposals by criteria: {}", criteria);
        Page<DisposalV2VM> page = null;
        HttpHeaders headers = null;

        if (criteria.isOnlyPatientId()) {
            page = disposalService.getDisposalProjectionByPatientId(criteria.getPatientId().getEquals(), pageable);
        } else {
            page = disposalQueryService.findByCriteria(criteria, pageable);
        }

        if (page != null) {
            headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/disposals");
        }

        return ResponseEntity.ok().headers(headers).body(page != null? page.getContent(): null);
    }

    /**
     * GET  /disposals/count : count all the disposals.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the count in body
     */
    @GetMapping("/disposals/count")
    @Timed
    public ResponseEntity<Long> countDisposals(DisposalCriteria criteria) {
        log.debug("REST request to count Disposals by criteria: {}", criteria);
        return ResponseEntity.ok().body(disposalQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /disposals/:id : get the "id" disposal.
     *
     * @param id the id of the disposal to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the disposal, or with status 404 (Not Found)
     */
    @GetMapping("/disposals/{id}")
    @Timed
    public ResponseEntity<Disposal> getDisposal(@PathVariable Long id) {
        log.debug("REST request to get Disposal : {}", id);

        Optional<Disposal> optionalDisposal = Optional.ofNullable(disposalService.getDisposalByProjection(id));

        return ResponseUtil.wrapOrNotFound(optionalDisposal);
    }

    @GetMapping("/disposals/simple/{id}")
    @Timed
    public ResponseEntity<Disposal> getSimpleDisposal(@PathVariable Long id) {
        log.debug("REST request to get simple Disposal : {}", id);

        Optional<Disposal> optionalDisposal = Optional.ofNullable(disposalService.getSimpleDisposalProjectionById(id));

        return ResponseUtil.wrapOrNotFound(optionalDisposal);
    }


    /**
     * DELETE  /disposals/:id : delete the "id" disposal.
     *
     * @param id the id of the disposal to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/disposals/{id}")
    @Timed
    public ResponseEntity<Void> deleteDisposal(@PathVariable Long id) {
        log.debug("REST request to delete Disposal : {}", id);
        disposalService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/disposals/rules-checked/{id}")
    @Timed
    public ResponseEntity<HybridRuleCheckDisposal> getDisposalWithRulesCheckedNhiExtTxProc2(@PathVariable Long id) {
        log.debug("REST request to get Disposal rules-checked : {}", id);
        // Origin useing -> findOneWithEagerRelationships
        Disposal disposal = disposalService.getDisposalByProjection(id);

        if (disposal == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            // Assemble treatment family for check rule
            Treatment treatment = new Treatment();
            TreatmentPlan treatmentPlan = new TreatmentPlan();
            TreatmentTask treatmentTask = new TreatmentTask();
            Patient patient = new Patient();

            // two way relationship no. 1
            treatmentTask.setTreatmentPlan(treatmentPlan);
            treatmentPlan.setTreatment(treatment);
            treatment.setPatient(patient);
            patient.setId(disposal.getNhiExtendDisposals().iterator().next().getPatientId());

            // two way relationship no. 2
            Set<TreatmentTask> treatmentTaskSet = new HashSet<>();
            Set<TreatmentPlan> treatmentPlanSet = new HashSet<>();
            treatmentTaskSet.add(treatmentTask);
            treatmentPlanSet.add(treatmentPlan);
            treatmentPlan.setTreatmentTasks(treatmentTaskSet);
            treatment.setTreatmentPlans(treatmentPlanSet);

            // Add back to disposal
            disposal.getTreatmentProcedures().forEach(treatmentProcedure -> {
                treatmentProcedure.setTreatmentTask(treatmentTask);
                treatmentProcedure.setDisposal(disposal);
                if (treatmentProcedure.getNhiProcedure() != null) {
                    treatmentProcedure.getNhiExtendTreatmentProcedure().setTreatmentProcedure(treatmentProcedure);
                }
            });

            nhiService.checkNhiExtendTreatmentProcedures(disposal);

            // 混用新舊 rule check
            List<NhiExtendDisposalTable> neds = new ArrayList<>(nhiExtendDisposalRepository.findByDisposal_IdOrderByIdDesc(disposal.getId(), NhiExtendDisposalTable.class));

            HybridRuleCheckDisposal hd = new HybridRuleCheckDisposal(disposal);
            Set<HybridRuleCheckTreatmentProcedure> htp = new HashSet<>();

            NhiRuleCheckBody body = new NhiRuleCheckBody();
            List<NhiRuleCheckTxSnapshot> txSnapshots = new ArrayList<>();

            body.setDisposalId(disposal.getId());
            body.setDisposalTime(DateTimeUtil.transformLocalDateToRocDate(disposal.getDateTime()));
            body.setPatientId(patient.getId());
            body.setTxSnapshots(txSnapshots);
            if (neds.size() > 0) {
                body.setNhiCategory(neds.get(0).getA23());
            }

            disposal.getTreatmentProcedures().forEach(tp -> {
                NhiRuleCheckTxSnapshot tx = new NhiRuleCheckTxSnapshot();
                tx.setId(tp.getId());
                tx.setNhiCode(tp.getNhiExtendTreatmentProcedure().getA73());
                tx.setTeeth(tp.getNhiExtendTreatmentProcedure().getA74());
                tx.setSurface(tp.getNhiExtendTreatmentProcedure().getA75());
                txSnapshots.add(tx);
            });

            disposal.getTreatmentProcedures().forEach(tp -> {
                try {
                    NhiRuleCheckResultVM rvm = (NhiRuleCheckResultVM) nhiRuleCheckUtil.dispatch(tp.getNhiProcedure().getCode(), body);
                    HybridRuleCheckTreatmentProcedure hybridRuleCheckTreatmentProcedure = new HybridRuleCheckTreatmentProcedure(tp);
                    hybridRuleCheckTreatmentProcedure.setCheckHistory(rvm.getCheckHistory());
                    htp.add(hybridRuleCheckTreatmentProcedure);
                } catch (Exception e) {
                    htp.add(new HybridRuleCheckTreatmentProcedure(tp));
                }
            });

            hd.setHybridRuleCheckTreatmentProcedures(htp);

            return ResponseEntity.ok(hd);
        }
    }

    @GetMapping("/disposals/same-treatment")
    @Timed
    @Transactional
    public List<SameTreatmentVM> findSameTreatment(
        @RequestParam(name = "patientId") Long patientId,
        @RequestParam(name = "begin") Instant begin,
        @RequestParam(name = "end") Instant end
    ) {
       return disposalService.findSameTreatment(patientId, begin, end);
    }

}
