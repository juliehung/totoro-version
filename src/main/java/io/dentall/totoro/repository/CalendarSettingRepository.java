package io.dentall.totoro.repository;

import io.dentall.totoro.domain.CalendarSetting;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CalendarSetting entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CalendarSettingRepository extends JpaRepository<CalendarSetting, Long>, JpaSpecificationExecutor<CalendarSetting> {

}
