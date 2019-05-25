package io.dentall.totoro.repository;

import io.dentall.totoro.domain.NhiDayUpload;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the NhiDayUpload entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NhiDayUploadRepository extends JpaRepository<NhiDayUpload, Long> {

}
