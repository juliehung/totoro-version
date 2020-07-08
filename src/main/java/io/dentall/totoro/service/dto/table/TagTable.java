package io.dentall.totoro.service.dto.table;

import io.dentall.totoro.domain.enumeration.TagType;

public interface TagTable {

    Long getId();
    TagType getType();
    String getName();
    Boolean getModifiable();
    Integer getOrder();
}
