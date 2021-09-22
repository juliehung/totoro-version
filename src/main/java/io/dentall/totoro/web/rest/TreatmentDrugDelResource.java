package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.TreatmentDrug;
import io.dentall.totoro.domain.TreatmentDrugDel;
import io.dentall.totoro.repository.TreatmentDrugDelRepository;
import io.dentall.totoro.service.mapper.TreatmentDrugDelMapper;
import io.dentall.totoro.web.rest.vm.TreatmentDrugDelQueryVM;
import io.dentall.totoro.web.rest.vm.TreatmentDrugDelUpdateVM;
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
public class TreatmentDrugDelResource {

    private final Logger log = LoggerFactory.getLogger(TreatmentDrugDelResource.class);

    private final TreatmentDrugDelRepository treatmentDrugDelRepository;

    public TreatmentDrugDelResource(
        TreatmentDrugDelRepository treatmentDrugDelRepository
    ) {
        this.treatmentDrugDelRepository = treatmentDrugDelRepository;
    }

    @GetMapping("/treatment-drug-dels")
    @Timed
    public ResponseEntity<List<TreatmentDrug>> getTreatmentDrugDels(TreatmentDrugDelQueryVM vm) {
        log.debug("REST request to get treatment procedure drug ");
        return ResponseEntity.ok()
            .body(
                treatmentDrugDelRepository.findByPrescriptionIdAndIcCardEject(
                    vm.getPrescriptionId(),
                    false
                ).stream()
                    .map(TreatmentDrugDelMapper.INSTANCE::convertTreatmentDrugDelToTreatmentDrug)
                    .collect(Collectors.toList())
            );
    }

    @PatchMapping("/treatment-drug-dels")
    @Timed
    @Transactional
    public ResponseEntity<Void> updateTreatmentDrugDels(TreatmentDrugDelUpdateVM vm) {
        log.debug("REST request to update status of treatment drug dels {}", vm.getTreatmentDrugIds());
        List<TreatmentDrugDel> tdds = treatmentDrugDelRepository.findAllById(vm.getTreatmentDrugIds());
        for (TreatmentDrugDel tdd : tdds) {
            tdd.setIcCardEject(true);
        }

        return ResponseEntity.ok(null);
    }
}
