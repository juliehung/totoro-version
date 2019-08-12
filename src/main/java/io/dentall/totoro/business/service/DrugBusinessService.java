package io.dentall.totoro.business.service;

import io.dentall.totoro.repository.DrugRepository;
import io.dentall.totoro.repository.TreatmentDrugRepository;
import io.dentall.totoro.service.util.ProblemUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.zalando.problem.Status;

@Service
public class DrugBusinessService {

    private final Logger log = LoggerFactory.getLogger(DrugBusinessService.class);

    private final DrugRepository drugRepository;

    private final TreatmentDrugRepository treatmentDrugRepository;

    public DrugBusinessService(
        DrugRepository drugRepository,
        TreatmentDrugRepository treatmentDrugRepository
    ) {
        this.drugRepository = drugRepository;
        this.treatmentDrugRepository = treatmentDrugRepository;
    }

    public void delete(Long id) {
        log.debug("Request to delete Drug : {}", id);
        if (!treatmentDrugRepository.existsByDrugId(id)) {
            drugRepository.deleteById(id);
        } else {
            throw new ProblemUtil(String.format(
                "Drug(%s) is used in treatments can not be delete.", id),
                Status.BAD_REQUEST);
        }
    }

}
