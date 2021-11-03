package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.LedgerGroup;
import io.dentall.totoro.repository.LedgerGroupRepository;
import io.dentall.totoro.service.mapper.LedgerGroupMapper;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api")
public class LedgerGroupResource {

    private final Logger log = LoggerFactory.getLogger(LedgerGroupResource.class);

    private static final String ENTITY_NAME = "ledger_group";

    private final LedgerGroupRepository ledgerGroupRepository;

    public LedgerGroupResource(
        LedgerGroupRepository ledgerGroupRepository
    ) {
        this.ledgerGroupRepository = ledgerGroupRepository;
    }

    @PostMapping("/ledger-groups")
    @Timed
    public LedgerGroup createLedgerGroup(@RequestBody @Valid LedgerGroup ledgerGroup) {
        return ledgerGroupRepository.save(ledgerGroup);
    }

    @PatchMapping("/ledger-groups")
    @Timed
    @Transactional
    public LedgerGroup updateLedgerGroup(@RequestBody @Valid LedgerGroup updateLedgerGroup) {
        log.info("Patch ledger group by id {} ", updateLedgerGroup.getId());
        if (updateLedgerGroup.getId() == null) {
            throw new BadRequestAlertException("Must include id", ENTITY_NAME, "noid");
        }

        LedgerGroup ledgerGroup = ledgerGroupRepository.findById(updateLedgerGroup.getId())
            .orElseThrow(() -> new BadRequestAlertException("Can not found by id", ENTITY_NAME, "notfound"));

        LedgerGroupMapper.INSTANCE.patching(ledgerGroup, updateLedgerGroup);

        return ledgerGroup;
    }

}
