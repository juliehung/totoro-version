package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.TreatmentProcedure;
import io.dentall.totoro.domain.TreatmentProcedureDel;
import io.dentall.totoro.repository.TreatmentProcedureDelRepository;
import io.dentall.totoro.service.mapper.TreatmentProcedureDelMapper;
import io.dentall.totoro.web.rest.vm.TreatmentProcedureDelQueryVM;
import io.dentall.totoro.web.rest.vm.TreatmentProcedureDelUpdateVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TreatmentProcedureDelResource {

    private final Logger log = LoggerFactory.getLogger(TreatmentProcedureDelResource.class);

    private final TreatmentProcedureDelRepository treatmentProcedureDelRepository;

    public TreatmentProcedureDelResource(
        TreatmentProcedureDelRepository treatmentProcedureDelRepository
    ) {
        this.treatmentProcedureDelRepository = treatmentProcedureDelRepository;
    }

    @GetMapping("/treatment-procedure-dels")
    @Timed
    public ResponseEntity<List<TreatmentProcedure>> getTreatmentProcedureDels(TreatmentProcedureDelQueryVM vm) {
        log.debug("REST request to get treatment procedure dels ");
        return ResponseEntity.ok()
            .body(
                treatmentProcedureDelRepository.findByDisposalIdAndIcCardEject(
                    vm.getDisposalId(),
                    false
                ).stream()
                    .map(TreatmentProcedureDelMapper.INSTANCE::convertTreatmentProcedureDelToTreatmentProcedure)
                    .collect(Collectors.toList())
            );
    }

    @PatchMapping("/treatment-procedure-dels")
    @Timed
    @Transactional
    public ResponseEntity<Void> updateTreatmentProcedureDels(TreatmentProcedureDelUpdateVM vm) {
        log.debug("REST request to update status of treatment procedure dels {}", vm.getTreatmentProcedureIds());
        List<TreatmentProcedureDel> tpds = treatmentProcedureDelRepository.findAllById(vm.getTreatmentProcedureIds());
        for (TreatmentProcedureDel tpd : tpds) {
            tpd.setIcCardEject(true);
        }

        return ResponseEntity.ok(null);
    }
}
