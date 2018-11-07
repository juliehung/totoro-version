package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for checking the system status.
 */
@RestController
@RequestMapping("/api")
public class StatusController {

    public StatusController() {
    }

    /**
     * GET /greeting : check the server is alive.
     */
    @GetMapping("/greeting")
    @Timed
    @ResponseStatus(HttpStatus.OK)
    public void greeting() { }
}
