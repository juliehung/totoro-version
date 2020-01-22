package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.Esign;
import io.dentall.totoro.domain.Version;
import io.dentall.totoro.service.EsignQueryService;
import io.dentall.totoro.service.EsignService;
import io.dentall.totoro.service.dto.EsignCriteria;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Version.
 */
@RestController
@RequestMapping("/api")
public class VersionResource {

    private final Logger log = LoggerFactory.getLogger(VersionResource.class);

    public VersionResource() { }

    @GetMapping("/version")
    @Timed
    public ResponseEntity<String> getVersion() {
        log.debug("REST request to get Version by criteria");
        return ResponseEntity.ok().body(Version.versionSingleton().getVersion());
    }

}
