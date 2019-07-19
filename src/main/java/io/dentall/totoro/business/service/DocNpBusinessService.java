package io.dentall.totoro.business.service;

import io.dentall.totoro.domain.DocNp;
import io.dentall.totoro.repository.DocNpRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<DocNp> findDocNp(Long patientId, Long esignId, boolean latest) {
        List<DocNp> docNps;

        if (esignId == null) {
            docNps = docNpRepository.findByPatientId(patientId);
        }else {
            docNps = docNpRepository.findByPatientIdAndEsignId(patientId, esignId);
        }

        if (latest) {
            return docNps
                .stream()
                .sorted(Comparator.comparing(DocNp::getCreatedDate).reversed())
                .limit(1)
                .collect(Collectors.toList());
        }

        return docNps;
    }

}
