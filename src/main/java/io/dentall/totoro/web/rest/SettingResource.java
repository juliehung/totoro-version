package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.Setting;
import io.dentall.totoro.repository.SettingRepository;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Setting.
 */
@RestController
@RequestMapping("/api")
public class SettingResource {

    private final Logger log = LoggerFactory.getLogger(SettingResource.class);

    private static final String ENTITY_NAME = "setting";

    private final SettingRepository settingRepository;

    public SettingResource(SettingRepository settingRepository) {
        this.settingRepository = settingRepository;
    }

    /**
     * PUT  /settings : Updates an existing setting.
     *
     * @param setting the setting to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated setting,
     * or with status 400 (Bad Request) if the setting is not valid,
     * or with status 500 (Internal Server Error) if the setting couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/settings")
    @Timed
    public ResponseEntity<Setting> updateSetting(@Valid @RequestBody Setting setting) throws URISyntaxException {
        log.debug("REST request to update Setting : {}", setting);
        if (setting.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Setting result = settingRepository.save(setting);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, setting.getId().toString()))
            .body(result);
    }

    /**
     * GET  /settings : get all the settings.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of settings in body
     */
    @GetMapping("/settings")
    @Timed
    public List<Setting> getAllSettings() {
        log.debug("REST request to get all Settings");
        return settingRepository.findAll();
    }

    /**
     * GET  /settings/:id : get the "id" setting.
     *
     * @param id the id of the setting to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the setting, or with status 404 (Not Found)
     */
    @GetMapping("/settings/{id}")
    @Timed
    public ResponseEntity<Setting> getSetting(@PathVariable Long id) {
        log.debug("REST request to get Setting : {}", id);
        Optional<Setting> setting = settingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(setting);
    }
}
