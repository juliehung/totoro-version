package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
