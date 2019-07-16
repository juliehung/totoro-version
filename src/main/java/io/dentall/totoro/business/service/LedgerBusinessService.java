package io.dentall.totoro.business.service;

import io.dentall.totoro.domain.Ledger;
import io.dentall.totoro.repository.LedgerRepository;
import io.dentall.totoro.service.LedgerQueryService;
import io.dentall.totoro.service.dto.LedgerCriteria;
import io.dentall.totoro.service.util.ProblemUtil;
import io.github.jhipster.service.filter.LongFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zalando.problem.Status;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class LedgerBusinessService {

    private final Logger log = LoggerFactory.getLogger(LedgerQueryService.class);

    private final LedgerRepository ledgerRepository;

    private final LedgerQueryService ledgerQueryService;

    public LedgerBusinessService(
        LedgerRepository ledgerRepository,
        LedgerQueryService ledgerQueryService
    ){
        this.ledgerRepository = ledgerRepository;
        this.ledgerQueryService = ledgerQueryService;
    }

    // Services
    public Ledger saveLedgerRecord(Ledger l) {
        validateCreatable(l);
        Ledger res = ledgerRepository.save(l);

        return res;
    }

    public List<Ledger> findHeadRecord(Long id) {
        // Generate criteria and find head result
        LedgerCriteria c = ledgerCriteriaIdWrapper(id);
        List<Ledger> ledgers = ledgerQueryService.findByCriteria(c);

        if (ledgers.size() < 1) {
            throw new ProblemUtil("Not found ledger head with id: "+id, Status.NOT_FOUND);
        }

        if (ledgers.size() > 1) {
            log.error("Ledger data is polluted more then one head record at id: {}", id);
        }

        return ledgers;
    }

    public List<Ledger> findRecords(Long id) {
        // Generate criteria and find head result assign to records
        LedgerCriteria headC = ledgerCriteriaIdWrapper(id);
        List<Ledger> records = ledgerQueryService.findByCriteria(headC);

        if (records.size() > 1) {
            log.error("Ledger data is polluted more then one head record at id: {}", id);
        }

        // Generate criteria and find body result add to records
        LedgerCriteria bodyC = ledgerCriteriaGidWrapper(id);
        List<Ledger> body = ledgerQueryService.findByCriteria(bodyC);
        records.addAll(body);

        return records;
    }

    // Validations
    public void  validateCreatable(Ledger l) {
        if (l == null) {
            throw new ProblemUtil("Empty ledger", Status.BAD_REQUEST);
        }
        if (l.getId() == null && l.getGid() == null) {
            return;
        }
        else if (l.getId() == null && l.getGid() != null) {
            return;
        }else {
            throw new ProblemUtil(
                "Only accept while id and gid are null(head), or id is null and " +
                    "gid is not null(body)",
                Status.BAD_REQUEST
            );
        }
    }

    // Wrappers
    public LedgerCriteria ledgerCriteriaIdWrapper(Long id){
        LongFilter idFilter = new LongFilter();
        idFilter.setEquals(id);
        LongFilter mustNull = new LongFilter();
        mustNull.setSpecified(false);

        LedgerCriteria c = new LedgerCriteria();
        c.setId(idFilter);
        c.setGid(mustNull);

        return c;
    }

    public LedgerCriteria ledgerCriteriaGidWrapper(Long gid){
        LongFilter idFilter = new LongFilter();
        idFilter.setEquals(gid);

        LedgerCriteria c = new LedgerCriteria();
        c.setGid(idFilter);

        return c;
    }

}
