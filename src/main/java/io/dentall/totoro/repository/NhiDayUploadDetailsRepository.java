package io.dentall.totoro.repository;

import io.dentall.totoro.domain.NhiDayUploadDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the NhiDayUploadDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NhiDayUploadDetailsRepository extends JpaRepository<NhiDayUploadDetails, Long>, JpaSpecificationExecutor<NhiDayUploadDetails> {

}
