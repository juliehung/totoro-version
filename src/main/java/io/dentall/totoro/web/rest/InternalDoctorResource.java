package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.config.Constants;
import io.dentall.totoro.service.UserService;
import io.dentall.totoro.service.dto.DoctorVM;
import io.dentall.totoro.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for managing users.
 * <p>
 * This class accesses the User entity, and needs to fetch its collection of authorities.
 * <p>
 * For a normal use-case, it would be better to have an eager relationship between User and Authority,
 * and send everything to the client side: there would be no View Model and DTO, a lot less code, and an outer-join
 * which would be good for performance.
 * <p>
 * We use a View Model and a DTO for 3 reasons:
 * <ul>
 * <li>We want to keep a lazy association between the user and the authorities, because people will
 * quite often do relationships with the user, and we don't want them to get the authorities all
 * the time for nothing (for performance reasons). This is the #1 goal: we should not impact our users'
 * application because of this use-case.</li>
 * <li> Not having an outer join causes n+1 requests to the database. This is not a real issue as
 * we have by default a second-level cache. This means on the first HTTP call we do the n+1 requests,
 * but then all authorities come from the cache, so in fact it's much better than doing an outer join
 * (which will get lots of data from the database, for each HTTP call).</li>
 * <li> As this manages users, for security reasons, we'd rather have a DTO layer.</li>
 * </ul>
 * <p>
 * Another option would be to have a specific JPA entity graph to handle this case.
 */
@RestController
@RequestMapping("/api/prerogative/doctor")
public class InternalDoctorResource {

    private static Logger log = LoggerFactory.getLogger(InternalDoctorResource.class);

    private final UserService userService;

    public InternalDoctorResource(UserService userService) {
        this.userService = userService;
    }

    /**
     * GET /all : get all doctors.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and with body all doctors
     */
    @GetMapping("/all")
    @Timed
    public ResponseEntity<List<DoctorVM>> getAllDoctors(Pageable pageable) {
        final Page<DoctorVM> page = userService.getAllDoctors(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/prerogative/doctor");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /:doctorId : get the doctor.
     *
     * @param doctorId the login of the user to find
     * @return the ResponseEntity with status 200 (OK) and with body the doctor, or with status 404 (Not Found)
     */
    @GetMapping("/{doctorId:" + Constants.LOGIN_REGEX + "}")
    @Timed
    public ResponseEntity<DoctorVM> getDoctor(@PathVariable("doctorId") String doctorId) {
        log.debug("REST request to get Doctor : {}", doctorId);
        return ResponseUtil.wrapOrNotFound(userService.getDoctor(doctorId));
    }

}
