package io.dentall.totoro.service.util;


import io.dentall.totoro.domain.AbstractAuditingEntity;
import io.dentall.totoro.service.dto.table.AuditingElement;

public final class MapperUtil {

    public static void setAuditing(AbstractAuditingEntity entity, AuditingElement auditingElement) {
        entity.setCreatedBy(auditingElement.getCreatedBy());
        entity.setCreatedDate(auditingElement.getCreatedDate());
        entity.setLastModifiedBy(auditingElement.getLastModifiedBy());
        entity.setLastModifiedDate(auditingElement.getLastModifiedDate());
    }

    public static void setNullAuditing(AbstractAuditingEntity entity) {
        entity.setCreatedBy(null);
        entity.setCreatedDate(null);
        entity.setLastModifiedBy(null);
        entity.setLastModifiedDate(null);
    }
}
