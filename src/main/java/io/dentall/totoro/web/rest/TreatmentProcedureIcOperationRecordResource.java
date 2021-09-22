package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.repository.TreatmentProcedureIcOperationRecordRepository;
import io.dentall.totoro.web.rest.vm.TreatmentProcedureIcOperationRecordVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class TreatmentProcedureIcOperationRecordResource {

    private final Logger log = LoggerFactory.getLogger(TreatmentProcedureIcOperationRecordResource.class);

    private final TreatmentProcedureIcOperationRecordRepository treatmentProcedureIcOperationRecordRepository;

    public TreatmentProcedureIcOperationRecordResource(
        TreatmentProcedureIcOperationRecordRepository treatmentProcedureIcOperationRecordRepository
    ) {
        this.treatmentProcedureIcOperationRecordRepository = treatmentProcedureIcOperationRecordRepository;
    }

    @PostMapping("/treatment-procedure-ic-operation-records")
    @Timed
    @Transactional
    public ResponseEntity<Void> createTreatmentProcedureIcOperationRecords(
        @RequestBody @Valid TreatmentProcedureIcOperationRecordVM vm
    ) {
        log.debug("REST request to update status of treatment procedure ic operation record");
        treatmentProcedureIcOperationRecordRepository.saveAll(vm.getTreatmentProcedureIcOperationRecords());

        return ResponseEntity.ok(null);
    }
}
