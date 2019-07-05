package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.business.dto.EsignDTO;
import io.dentall.totoro.business.service.LobService;
import io.dentall.totoro.business.vm.LobVM;
import io.dentall.totoro.service.dto.EsignCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.ws.http.HTTPException;

/**
 *
 * REST controller for managing Esign.
 */
@RestController
@RequestMapping("/api")
public class LobResource {
    private final Logger log = LoggerFactory.getLogger(EsignResource.class);

    private final LobService lobService;

    public LobResource(LobService lobService) {
        this.lobService = lobService;
    }

    @PostMapping("/lob/esign/{patientId}/file")
    @Timed
    public ResponseEntity<LobVM> createEsign(@PathVariable Long patientId, @RequestParam("file") MultipartFile file) {
        log.debug("REST request to create Esign by patientId: {} and file.", patientId);
        try {
            LobVM vm = lobService.saveEsign(patientId, file);
            return new ResponseEntity<>(vm, HttpStatus.OK);
        } catch (HTTPException ex) {
            int statusCode = ex.getStatusCode();
            return new ResponseEntity<>(null, HttpStatus.valueOf(statusCode));
        }
    }

    @PostMapping("/lob/esign/string64")
    @Timed
    public ResponseEntity<LobVM> createEsignByString64(@RequestBody EsignDTO dto) {
        log.debug("REST request to create Esign by EsignDTO: {} with pure string of base64.", dto);
        try {
            LobVM vm = lobService.saveEsignByString64(dto);
            return new ResponseEntity<>(vm, HttpStatus.OK);
        } catch (HTTPException ex) {
            int statusCode = ex.getStatusCode();
            return new ResponseEntity<>(null, HttpStatus.valueOf(statusCode));
        }
    }

    @GetMapping("/lob/esign")
    @Timed
    public ResponseEntity<LobVM> getEsign(EsignCriteria c) {
        log.debug("REST request to get Esign by criteria: {}", c);
        try {
            LobVM vm = lobService.findEsign(c);
            return new ResponseEntity<>(vm, HttpStatus.OK);
        } catch (HTTPException ex) {
            int statusCode = ex.getStatusCode();
            return new ResponseEntity<>(null, HttpStatus.valueOf(statusCode));
        }
    }

    @PutMapping("/lob/esign")
    @Timed
    public ResponseEntity<LobVM> updateEsign(EsignCriteria c, @RequestParam("file") MultipartFile file) {
        log.debug("REST request to update Esign by criteria: {}", c);
        try {
            LobVM vm = lobService.updateEsign(c, file);
            return new ResponseEntity<>(vm, HttpStatus.OK);
        } catch (HTTPException ex) {
            int statusCode = ex.getStatusCode();
            return new ResponseEntity<>(null, HttpStatus.valueOf(statusCode));
        }
    }
}

