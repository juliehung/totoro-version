package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.NhiIcCardOperationRecord;
import io.dentall.totoro.repository.NhiIcCardOperationRecordRepository;
import io.dentall.totoro.web.rest.vm.NhiIcCardOperationRecordPatchVM;
import io.dentall.totoro.web.rest.vm.NhiIcCardOperationRecordQueryVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

/**
 * REST controller for managing NhiMedicalRecord.
 */
@RestController
@RequestMapping("/api")
public class NhiIcCardOperationRecordResource {

    private final Logger log = LoggerFactory.getLogger(NhiIcCardOperationRecordResource.class);

    private final NhiIcCardOperationRecordRepository nhiIcCardOperationRecordRepository;

    public NhiIcCardOperationRecordResource(
        NhiIcCardOperationRecordRepository nhiIcCardOperationRecordRepository
    ) {
        this.nhiIcCardOperationRecordRepository = nhiIcCardOperationRecordRepository;
    }

    @PostMapping("/nhi-ic-card-operation-records")
    @Timed
    @Transactional
    public List<NhiIcCardOperationRecord> getNoneEjectedAndSuccessWriteCardRecord(
        @RequestBody List<NhiIcCardOperationRecord> nhiIcCardOperationRecords
    ) {
        return nhiIcCardOperationRecordRepository.saveAll(nhiIcCardOperationRecords);
    }

    @GetMapping("/nhi-ic-card-operation-records")
    @Timed
    public List<NhiIcCardOperationRecord> getNoneEjectedAndSuccessWriteCardRecord(
        NhiIcCardOperationRecordQueryVM vm
    ) {
        return nhiIcCardOperationRecordRepository.findByEjectIcCardRecordFalseAndDisposalIdAndNhiResponseCodeIs(vm.getDisposalId(), "0");
    }

    @PatchMapping("/nhi-ic-card-operation-records")
    @Timed
    @Transactional
    public ResponseEntity<Void> getNoneEjectedAndSuccessWriteCardRecords(
        @RequestBody NhiIcCardOperationRecordPatchVM vm
    ) {
        nhiIcCardOperationRecordRepository.findByEjectIcCardRecordFalseAndDisposalIdAndNhiResponseCodeIs(vm.getDisposalId(), "0")
            .forEach(d -> {
                if (vm.getNhiIcCardOperationRecordIds() != null &&
                    vm.getNhiIcCardOperationRecordIds().contains(d.getId())
                ) {
                    d.setEjectIcCardRecord(true);
                }
            });

        return ResponseEntity.ok(null);
    }

}
