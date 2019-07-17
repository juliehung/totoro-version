package io.dentall.totoro.business.service;

import io.dentall.totoro.business.dto.EsignDTO;
import io.dentall.totoro.business.vm.LobVM;
import io.dentall.totoro.domain.Esign;
import io.dentall.totoro.domain.Patient;
import io.dentall.totoro.domain.enumeration.SourceType;
import io.dentall.totoro.repository.PatientRepository;
import io.dentall.totoro.service.EsignQueryService;
import io.dentall.totoro.service.EsignService;
import io.dentall.totoro.service.dto.EsignCriteria;
import io.dentall.totoro.service.util.ProblemUtil;
import io.dentall.totoro.web.rest.EsignResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.zalando.problem.Status;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LobBusinessService {

    private final Logger log = LoggerFactory.getLogger(EsignResource.class);

    private final EsignQueryService esignQueryService;

    private final EsignService esignService;

    private final PatientRepository patientRepository;

    public LobBusinessService(
        EsignQueryService esignQueryService,
        EsignService esignService,
        PatientRepository patientRepository
    ) {
        this.esignQueryService = esignQueryService;
        this.esignService = esignService;
        this.patientRepository = patientRepository;
    }

    // Services
    public LobVM saveEsign(Long patientId, MultipartFile file) {
        validateCreatable(patientId, file);
        Esign es = esignService.save(esignWrapper(patientId, file));

        return lobVmWrapperForEsign(es);
    }

    public LobVM saveEsignByString64(EsignDTO dto) {
        validateCreatable(dto);
        Esign es = esignService.save(esignWrapper(dto));

        return lobVmWrapperForEsign(es);
    }

    public LobVM findNewestEsign(EsignCriteria c) {
        // Find e-sings
        List<Esign> esigns = findEsings(c);

        // Quick return
        if (esigns.size() == 0) {
            log.debug("Not found esign in db with criteria: {}", c);
            throw new ProblemUtil("Not found", Status.NOT_FOUND);
        }
        if (esigns.size() == 1) {
            return lobVmWrapperForEsign(esigns.get(0));
        }

        // Compare every esign created date in finding result and find newest one. Will select
        // first found one when there exist several same created date esign.
        // Corner case 1: if some how all esign did not has created date, it will return the
        // first element in query result.
        int newestEsignIndex = 0;
        Instant newestEsignDate = Instant.MIN;
        for (int i = 0; i < esigns.size(); i++) {
            Instant indexedDate = esigns.get(i).getCreatedDate();
            if (indexedDate != null && indexedDate.isAfter(newestEsignDate)) {
                newestEsignIndex = i;
                newestEsignDate = indexedDate;
            }
        }

        return lobVmWrapperForEsign(esigns.get(newestEsignIndex));
    }

    public List<LobVM> findEsigns(EsignCriteria c) {
        List<Esign> esigns = this.findEsings(c);

        if (esigns.size() == 0) {
            log.debug("Not found esigns in db with criteria: {}", c);
            throw new ProblemUtil("Not found", Status.NOT_FOUND);
        }

        List<LobVM> vms = new ArrayList<>();
        for (Esign es:esigns) {
            vms.add(lobVmWrapperForEsign(es));
        }

        return vms;
    }

    public List<Esign> findEsings(EsignCriteria c) {
        List<Esign> res = esignQueryService.findByCriteria(c);
        if (res == null || res.size() < 1) {
            return null;
        }

        return res;
    }

    // Validations

    // This method provide checking db data's correctness. In the end, we decide to keep the
    // the method but not to use it. Meanwhile, if `A` was null and A.B would throw
    // exception(StatusCode:500).
    // - It is caused by not handled null object(need to hotfix)
    // - or can be said the db data is been illegally, manually, or some how modified.
    public void validateLobVMWrappable(Esign es) {
        if (es == null) {
            throw new ProblemUtil("Esign object is empty.", Status.BAD_REQUEST);
        }
        if (es.getPatientId() == null) {
            throw new ProblemUtil("Esign.patientId is empty.", Status.BAD_REQUEST);
        }
        if (StringUtils.isEmpty(es.getSourceType())) {
            throw new ProblemUtil("Esign.sourceType is empty.", Status.BAD_REQUEST);
        }
        if (StringUtils.isEmpty(es.getLobContentType())) {
            throw new ProblemUtil("Esign.lobContentType is empty.", Status.BAD_REQUEST);
        }
        if (es.getLob() == null) {
            throw new ProblemUtil("Esign.lob is empty.", Status.BAD_REQUEST);
        }
    }

    public void validateCreatable(Long patientId, MultipartFile file) {
        if (patientId == null) {
            throw new ProblemUtil("patientId is empty.", Status.BAD_REQUEST);
        }
        if (file.isEmpty()) {
            throw new ProblemUtil("File is empty", Status.BAD_REQUEST);
        }
        if (file.getSize() == 0) {
            throw new ProblemUtil("File.bytes is empty", Status.BAD_REQUEST);
        }
        if (StringUtils.isEmpty(file.getContentType())) {
            throw new ProblemUtil("File.contentType is empty", Status.BAD_REQUEST);
        }
        if (!existPatient(patientId)) {
            throw new ProblemUtil("patient is not exist.", Status.NOT_FOUND);
        }
    }

    public void validateCreatable(EsignDTO dto) {
        if (dto.getPatientId() == null) {
            throw new ProblemUtil("Esign.patientId is empty.", Status.BAD_REQUEST);
        }
        if (StringUtils.isEmpty(dto.getContentType())) {
            throw new ProblemUtil("Esign.contentType is empty.", Status.BAD_REQUEST);
        }
        if (StringUtils.isEmpty(dto.getBase64())) {
            throw new ProblemUtil("Esign.base64 is empty.", Status.BAD_REQUEST);
        }
        if (!existPatient(dto.getPatientId())) {
            throw new ProblemUtil("patient is not exist.", Status.NOT_FOUND);
        }
    }

    // [Extract] till it will be use in different object. Then, extract to individual business
    // service
    public boolean existPatient(Long patientId) {
        Optional<Patient> res = patientRepository.findById(patientId);

        return res.isPresent();
    }

    // Wrappers
    public LobVM lobVmWrapperForEsign(Esign es) {
        // Esign.lob maybe save as pure base64 string or byte. Before send it back to frontend
        // , will transform into string with different way.
        String base64;
        switch (es.getSourceType()) {
            case BY_FILE:
                base64 = Base64.getEncoder().encodeToString(es.getLob());
                break;
            case BY_STRING64:
                base64 = new String(es.getLob());
                break;
            default:
                base64 = "";
        }

        return new LobVM()
            .contentType(es.getLobContentType())
            .base64(base64)
            .createdDate(es.getCreatedDate());
    }

    public Esign esignWrapper(Long patientId, MultipartFile file) {
        Esign es = new Esign();

        try {
            es.patientId(patientId)
                .lobContentType(file.getContentType())
                .lob(file.getBytes())
                .sourceType(SourceType.BY_FILE);
        } catch (IOException e) {
            log.debug("File.getBytes problem.");
            log.debug("{}", e.getMessage());
            throw new ProblemUtil(
                "File.getBytes has problem you may want to check request body or" +
                    "backend log.debug for more info.",
                Status.BAD_REQUEST);
        }

        return es;
    }

    public Esign esignWrapper(EsignDTO dto) {
        return new Esign()
            .patientId(dto.getPatientId())
            .lobContentType(dto.getContentType())
            .lob(dto.getBase64().getBytes())
            .sourceType(SourceType.BY_STRING64);
    }
}
