package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.business.service.DocNpBusinessService;
import io.dentall.totoro.domain.DocNp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/business")
public class DocNpBusinessResource {

    private final Logger log = LoggerFactory.getLogger(DocNpResource.class);

    private DocNpBusinessService docNpBusinessService;

    public DocNpBusinessResource(
        DocNpBusinessService docNpBusinessService
    ) {
        this.docNpBusinessService = docNpBusinessService;
    }

    @GetMapping("/doc-nps")
    @Timed
    public ResponseEntity<List<DocNp>> getDocNp(
        @RequestParam(name = "patientId") Long patientId,
        @RequestParam(name = "esignId", required = false) Long esignId
    ) {
        log.debug("REST request to find doc np by patient id or both");

        List<DocNp> vm = docNpBusinessService.findDocNp(patientId, esignId);
        if (vm.isEmpty()) {
            return new ResponseEntity<>(vm, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(vm, HttpStatus.OK);
    }

}
