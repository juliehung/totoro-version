package io.dentall.totoro.business.service;

import io.dentall.totoro.business.dto.EsignDTO;
import io.dentall.totoro.business.vm.LobVM;
import io.dentall.totoro.domain.Esign;
import io.dentall.totoro.domain.Patient;
import io.dentall.totoro.domain.enumeration.SourceType;
import io.dentall.totoro.repository.EsignRepository;
import io.dentall.totoro.repository.PatientRepository;
import io.dentall.totoro.service.EsignQueryService;
import io.dentall.totoro.service.EsignService;
import io.dentall.totoro.service.dto.EsignCriteria;
import io.dentall.totoro.service.util.ProblemUtil;
import io.dentall.totoro.web.rest.EsignResource;
import io.github.jhipster.service.filter.LongFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.ws.http.HTTPException;
import java.io.IOException;
import java.time.Instant;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LobService {

    private final Logger log = LoggerFactory.getLogger(EsignResource.class);

    private final EsignQueryService esignQueryService;

    private final EsignService esignService;

    private final EsignRepository esignRepository;

    private final PatientRepository patientRepository;

    public LobService(
        EsignQueryService esignQueryService,
        EsignService esignService,
        EsignRepository esignRepository,
        PatientRepository patientRepository
    ) {
        this.esignQueryService = esignQueryService;
        this.esignService = esignService;
        this.esignRepository = esignRepository;
        this.patientRepository = patientRepository;
    }

    // Services
    public LobVM saveEsign(Long patientId, MultipartFile file) throws HTTPException {
        validateCreatableEsign(patientId);
        validateFile(file);
        Esign e = esignService.save(esignWrapper(patientId, file));
        return lobVmWrapperForEsign(e);
    }

    public LobVM saveEsignByString64(EsignDTO dto){
        validateCreatableEsign(dto.getPatientId());
        validateEsignDTO(dto);
        Esign e = esignService.save(esignWrapper(dto));
        return lobVmWrapperForEsign(e);
    }

    public LobVM findEsign(EsignCriteria c){
        Esign esign = findEsignByCriteria(c);
        return lobVmWrapperForEsign(esign);
    }

    public LobVM updateEsign(EsignCriteria c, MultipartFile file) throws HTTPException {
        // If API can not find it, meanwhile not call the right
        // API for this job. For making everything will stick with general
        // rules. IT SHOULD NOT PASS!!!(will pop an error message rather
        // than update esign for this call.)
        Esign oldEsign = findEsignByCriteria(c);

        return esignRepository
            .findById(oldEsign.getId())
            .map(newEsign -> {
                LobVM vm;
                try {
                    newEsign.setLobContentType(file.getContentType());
                    newEsign.setLob(file.getBytes());
                    vm = lobVmWrapperForEsign(newEsign);
                } catch (IOException ex) {
                    log.error("Some error while get file bytes");
                    log.error("{}", ex.getMessage());
                    throw new HTTPException(500);
                }

                return vm;
            })
            .get();
    }

    // Validations
    public void validateCreatableEsign(Long patientId) {
        if (!existPatient(patientId)) {
            throw ProblemUtil.notFoundException("Can not find patient with patientId.");
        }

        if (existEsign(patientId)) {
            throw ProblemUtil.notFoundException("Patient already has e-sign.");
        }
    }

    public void validateEsignDTO(EsignDTO dto) {
        if (StringUtils.isEmpty(dto.getContentType())) {
            throw ProblemUtil.notFoundException("ContentType(contentType) is empty.");
        }

        if (StringUtils.isEmpty(dto.getBase64())) {
            throw ProblemUtil.notFoundException("Content(base64) is empty.");
        }
    }

    public void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw ProblemUtil.notFoundException("Upload empty file.");
        }
    }

    public boolean existPatient(Long patientId) {
        Optional<Patient> res = patientRepository.findById(patientId);
        return res.isPresent();
    }

    public boolean existEsign(Long patientId) {
        EsignCriteria c = new EsignCriteria();
        LongFilter lf = new LongFilter();
        lf.setEquals(patientId);
        c.setPatientId(lf);
        List<Esign> e = esignQueryService.findByCriteria(c);
        return e.size() > 0 && e.get(0) != null;
    }
    
    public Esign findEsignByCriteria(EsignCriteria c) {
        List<Esign> res = esignQueryService.findByCriteria(c);
        if (res == null || res.size() < 1) {
            throw ProblemUtil.notFoundException("Can not find e-sign.");
        }

        if (res.size() > 1) {
            log.warn("The patientId: {} has more than 1 e-sign", c.getPatientId());
        }
        
        return res.get(0);
    }

    // Wrappers
    public LobVM lobVmWrapperForEsign(Esign es) throws HTTPException {
        String base64;
        try {
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
        } catch (Exception e) {
            log.error("Wrapping file content can not be translate into base64.");
            log.error("{}", e.getMessage());
            throw new HTTPException(500);
        }
        return new LobVM(es.getLobContentType(), base64);
    }

    public Esign esignWrapper(Long patientId, MultipartFile file) throws HTTPException {
        Esign es = new Esign();
        try {
            es.patientId(patientId)
                .lobContentType(file.getContentType())
                .lob(file.getBytes())
                .createTime(Instant.now())
                .updateTime(Instant.now())
                .sourceType(SourceType.BY_FILE);
        } catch (Exception e) {
            log.error("Wrapping file content can not be translate into base64.");
            log.error("{}", e.getMessage());
            throw new HTTPException(500);
        }

        return es;
    }

    public Esign esignWrapper(EsignDTO dto) throws HTTPException {
        return new Esign().patientId(dto.getPatientId())
            .lobContentType(dto.getContentType())
            .lob(dto.getBase64().getBytes())
            .createTime(Instant.now())
            .updateTime(Instant.now())
            .sourceType(SourceType.BY_STRING64);
    }
}
