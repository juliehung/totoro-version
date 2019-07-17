package io.dentall.totoro.business.service;

import io.dentall.totoro.domain.DocNp;
import io.dentall.totoro.repository.DocNpRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DocNpBusinessService {

    private DocNpRepository docNpRepository;

    public DocNpBusinessService(
        DocNpRepository docNpRepository
    ) {
        this.docNpRepository = docNpRepository;
    }

    @Transactional(readOnly = true)
    public List<DocNp> findDocNp(Long patientId, Long esignId) {
        if (esignId == null) {
            return docNpRepository.findByPatientId(patientId);
        }else {
            return docNpRepository.findByPatientIdAndEsignId(patientId, esignId);
        }
    }

}
