package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.Disposal;
import io.dentall.totoro.domain.NhiExtendDisposal;
import io.dentall.totoro.domain.NhiExtendTreatmentDrug;
import io.dentall.totoro.domain.NhiExtendTreatmentProcedure;
import io.dentall.totoro.service.DisposalService;
import io.dentall.totoro.service.NhiExtendDisposalService;
import io.dentall.totoro.service.dto.NhiExtendDisposalCriteriaV2;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.vm.MonthDisposalVM;
import io.dentall.totoro.web.rest.vm.NhiExtendDisposalVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * REST controller for managing NhiExtendDisposal.
 */
@RestController
@RequestMapping("/api/v2")
public class NhiExtendDisposalResourceV2 {

    private final Logger log = LoggerFactory.getLogger(NhiExtendDisposal.class);

    private static final String ENTITY_NAME = "nhiExtendDisposal";

    private final NhiExtendDisposalResource nhiExtendDisposalResource;

    private final NhiExtendDisposalService nhiExtendDisposalService;

    private final DisposalService disposalService;

    public NhiExtendDisposalResourceV2(
        NhiExtendDisposalResource nhiExtendDisposalResource,
        NhiExtendDisposalService nhiExtendDisposalService,
        DisposalService disposalService
    ) {
        this.nhiExtendDisposalResource = nhiExtendDisposalResource;
        this.nhiExtendDisposalService = nhiExtendDisposalService;
        this.disposalService = disposalService;
    }

    @GetMapping("/nhi-extend-disposals/daily-upload/{date}")
    @Timed
    public ResponseEntity<List<NhiExtendDisposalVM>> getDailyUploadNhiExtendDisposal(@PathVariable LocalDate date) {
        log.debug("REST request to get daily upload nhi ext dis in date");
        return ResponseEntity.ok(nhiExtendDisposalService.findByDate(date)
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
            .collect(Collectors.toList()));
    }

    @GetMapping("/nhi-extend-disposals/yearmonth/{yyyymm}")
    @Timed
    public ResponseEntity<List<MonthDisposalVM>> getAllByYYYYMM(@PathVariable Integer yyyymm) {
        log.debug("REST request to get all nhi ext dis all in year month");

        YearMonth ym;
        try {
            ym = YearMonth.of(yyyymm / 100, yyyymm % 100);
        } catch (DateTimeException e) {
            throw new BadRequestAlertException("Can not parse yyyymm", ENTITY_NAME, "invalidate");
        }
        return ResponseEntity.ok().body(nhiExtendDisposalService.findByYearMonthForLazyNhiExtDis(ym));
    }

    /**
     * Create nhi ext dis proxy service using v1 api's code.
     *
     * @param nhiExtendDisposal
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("/nhi-extend-disposals")
    @Timed
    public ResponseEntity<NhiExtendDisposal> createNhiExtendDisposal(@RequestBody NhiExtendDisposal nhiExtendDisposal) throws URISyntaxException {
        log.debug("REST request to save NhiExtendDisposal v2 : {}", nhiExtendDisposal);
        return nhiExtendDisposalResource.createNhiExtendDisposal(nhiExtendDisposal);
    }

    /**
     * Put nhi ext dis proxy service using v1 api's code.
     *
     * @param nhiExtendDisposal
     * @return
     * @throws URISyntaxException
     */
    @PutMapping("/nhi-extend-disposals")
    @Timed
    public ResponseEntity<NhiExtendDisposal> updateNhiExtendDisposal(@RequestBody NhiExtendDisposal nhiExtendDisposal) throws URISyntaxException {
        log.debug("REST request to update NhiExtendDisposal v2 : {}", nhiExtendDisposal);
        return nhiExtendDisposalResource.updateNhiExtendDisposal(nhiExtendDisposal);
    }

    /**
     * Get nhi ext dis proxy service using v1 api's code.
     *
     * @param criteria
     * @return
     * @throws URISyntaxException
     */
    @GetMapping("/nhi-extend-disposals")
    @Timed
    public ResponseEntity<NhiExtendDisposalVM> getNhiExtendDisposal(
        NhiExtendDisposalCriteriaV2 criteria
    ) {
        log.debug("REST request to get NhiExtendDisposal v2 : {}", criteria);
        return nhiExtendDisposalResource.getNhiExtendDisposal(criteria.getId());
    }

    /**
     * Delete nhi ext dis proxy service using v1 api's code.
     *
     * @param id
     * @return
     * @throws URISyntaxException
     */
    @DeleteMapping("/nhi-extend-disposals/{id}")
    @Timed
    public ResponseEntity<Void> deleteNhiExtendDisposal(@PathVariable Long id) {
        log.debug("REST request to delete NhiExtendDisposal v2 : {}", id);

        return nhiExtendDisposalResource.deleteNhiExtendDisposal(id);
    }

}
