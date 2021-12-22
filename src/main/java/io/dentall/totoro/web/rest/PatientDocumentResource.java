package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.PatientDocument;
import io.dentall.totoro.domain.User;
import io.dentall.totoro.service.PatientDocumentService;
import io.dentall.totoro.service.UserService;
import io.dentall.totoro.service.mapper.PatientDocumentMapper;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.vm.PatientDocumentVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.ForkJoinPool;

import static io.dentall.totoro.web.rest.util.PaginationUtil.generatePaginationHttpHeaders;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(PatientDocumentResource.apiLocation)
public class PatientDocumentResource {

    public final static String apiLocation = "/api/patient-document";

    private final static Logger log = LoggerFactory.getLogger(PatientDocumentResource.class);

    private static final String ENTITY_NAME = "PATIENT_DOCUMENT";

    private final UserService userService;

    private final PatientDocumentService patientDocumentService;

    private final PatientDocumentMapper patientDocumentMapper;

    public PatientDocumentResource(UserService userService, PatientDocumentService patientDocumentService, PatientDocumentMapper patientDocumentMapper) {
        this.userService = userService;
        this.patientDocumentService = patientDocumentService;
        this.patientDocumentMapper = patientDocumentMapper;
    }

    @PostMapping("/{patientId}")
    @Timed
    public DeferredResult<ResponseEntity<PatientDocumentVM>> createDocument(
        @PathVariable("patientId") Long patientId,
        @RequestParam("disposalId") Long disposalId,
        @RequestParam("file") MultipartFile file
    ) {
        log.debug("REST request to create Patient's documents by patient id : {}, disposal id: {}", patientId, disposalId);
        String userName = userService.getUserWithAuthorities()
            .map(User::getFirstName)
            .orElseThrow(() -> new BadRequestAlertException("user id is not found", ENTITY_NAME, "userId.not.found"));
        DeferredResult<ResponseEntity<PatientDocumentVM>> result = new DeferredResult<>();
        ForkJoinPool.commonPool().submit(() -> {
            try {
                PatientDocument patientDocument = patientDocumentService.createDocument(patientId, disposalId, file, userName);
                result.setResult(ResponseEntity.ok(patientDocumentMapper.mapToPatientDocumentVM(patientDocument)));
            } catch (BadRequestAlertException b) {
                result.setErrorResult(b);
            } catch (Exception e) {
                log.error("create document error: {}", e.toString());
                result.setErrorResult(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.toString()));
            }
        });

        return result;
    }

    @GetMapping("/{patientId}")
    @Timed
    public ResponseEntity<List<PatientDocumentVM>> getDocuments(
        @PathVariable("patientId") Long patientId,
        @RequestParam(value = "disposalId", required = false) Long disposalId,
        @RequestParam(value = "search", required = false) String search,
        @PageableDefault(size = 50)
        @SortDefault.SortDefaults({@SortDefault(sort = "document.title")}) Pageable pageable
    ) {
        log.debug("REST request to get Patient's documents by patient id : {}, disposal id: {}", patientId, disposalId);
        Page<PatientDocument> page = patientDocumentService.findDocument(patientId, disposalId, search, pageable);
        HttpHeaders headers = generatePaginationHttpHeaders(page, apiLocation);
        return ResponseEntity.ok().headers(headers).body(page.getContent().stream().map(patientDocumentMapper::mapToPatientDocumentVM).collect(toList()));
    }

    @PutMapping("/{patientId}")
    @Timed
    public ResponseEntity<PatientDocumentVM> updateDocument(
        @PathVariable("patientId") Long patientId,
        @RequestBody PatientDocument patientDocument
    ) {
        log.debug("REST request to update Patient's documents by patient id : {}", patientId);
        return ResponseEntity.ok(patientDocumentMapper.mapToPatientDocumentVM(patientDocumentService.updateDocument(patientId, patientDocument)));
    }

    @DeleteMapping("/{patientId}")
    @Timed
    public ResponseEntity<PatientDocumentVM> deleteDocument(
        @PathVariable("patientId") Long patientId,
        @RequestParam("patientDocumentId") Long patientDocumentId
    ) {
        log.debug("REST request to delete Patient's documents by patient id : {}, patient document id: {}", patientId, patientDocumentId);
        return ResponseEntity.ok(patientDocumentMapper.mapToPatientDocumentVM(patientDocumentService.deleteDocument(patientId, patientDocumentId)));
    }
}
