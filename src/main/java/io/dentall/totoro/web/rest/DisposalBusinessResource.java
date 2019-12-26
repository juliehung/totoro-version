package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.business.service.DisposalBusinessService;
import io.dentall.totoro.domain.Todo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/business")
public class DisposalBusinessResource {

    private final Logger log = LoggerFactory.getLogger(DisposalBusinessResource.class);

    private DisposalBusinessService disposalBusinessService;

    public DisposalBusinessResource(DisposalBusinessService disposalBusinessService) {
        this.disposalBusinessService = disposalBusinessService;
    }

    @GetMapping("/disposals/todos")
    @Timed
    public ResponseEntity<List<Todo>> getTodos(@RequestParam(name = "start") Instant start, @RequestParam(name = "end") Instant end) {
        log.debug("REST request to find todos by permanent disposal created datetime");

        return new ResponseEntity<>(
            disposalBusinessService.findTodosBetweenDisposalCreatedDate(start, end),
            HttpStatus.OK
        );
    }

}
