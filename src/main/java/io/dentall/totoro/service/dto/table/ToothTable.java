package io.dentall.totoro.service.dto.table;


import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

public interface ToothTable extends AuditingElement {
    Long getId();
    String getPosition();
    String getSurface();
    String getStatus();

    // 由於 projection 目前不支援這種形態的資料，且加入此項後會導致 treatmentProcedure_Id 這種 nested 類型的功能失效，因此此需利用 EsPL 去取得該資料
    // Ref: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#projections.interfaces.open
    @Value("#{target.metadata}")
    Map<String, Object> getMetadata();

    // Relationship
    @Value("#{target.treatmentProcedure.id}")
    Long getTreatmentProcedure_Id();

}
