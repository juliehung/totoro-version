package io.dentall.totoro.repository;

import io.dentall.totoro.domain.SmsView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SmsView entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SmsViewRepository extends JpaRepository<SmsView, Long>, JpaSpecificationExecutor<SmsView> {

}
