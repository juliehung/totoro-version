package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.business.dto.EsignDTO;
import io.dentall.totoro.business.service.LobBusinessService;
import io.dentall.totoro.business.vm.LobVM;
import io.dentall.totoro.service.dto.EsignCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 *
 * REST controller for managing Esign.
 */
@RestController
@RequestMapping("/api/business")
public class LobBusinessResource {
    private final Logger log = LoggerFactory.getLogger(LobBusinessResource.class);

    private final LobBusinessService lobService;

    public LobBusinessResource(LobBusinessService lobService) {
        this.lobService = lobService;
    }

    @PostMapping("/esigns/file")
    @Timed
    public ResponseEntity<LobVM> createEsign(@RequestParam("patientId") Long patientId, @RequestParam("file") MultipartFile file) {
        log.debug("REST request to create Esign by patientId: {} and file.", patientId);
        LobVM vm = lobService.saveEsign(patientId, file);
        return new ResponseEntity<>(vm, HttpStatus.CREATED);
    }

    @PostMapping("/esigns/string64")
    @Timed
    public ResponseEntity<LobVM> createEsignByString64(@RequestBody EsignDTO dto) {
        log.debug("REST request to create Esign by EsignDTO: {} with pure string of base64.", dto);
        LobVM vm = lobService.saveEsignByString64(dto);
        return new ResponseEntity<>(vm, HttpStatus.CREATED);
    }

    @GetMapping("/esigns")
    @Timed
    public ResponseEntity<LobVM> getEsign(EsignCriteria c) {
        log.debug("REST request to get Esign by criteria: {}", c);
        LobVM vm = lobService.findNewestEsign(c);
        return new ResponseEntity<>(vm, HttpStatus.OK);
    }

    @GetMapping("/esigns/history")
    @Timed
    public ResponseEntity<List<LobVM>> getEsignHistory(EsignCriteria c) {
        log.debug("REST request to get Esign by criteria: {}", c);
        List<LobVM> vm = lobService.findEsigns(c);
        return new ResponseEntity<>(vm, HttpStatus.OK);
    }

}
