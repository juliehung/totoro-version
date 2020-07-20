package io.dentall.totoro.service.mapper;

import io.dentall.totoro.domain.Tag;
import io.dentall.totoro.service.dto.table.TagTable;

public class TagMapper {

    public static Tag tagTableToTag(TagTable tagTable) {
        Tag tag = new Tag();

        tag.setId(tagTable.getId());
        tag.setType(tagTable.getType());
        tag.setName(tagTable.getName());
        tag.setModifiable(tagTable.getModifiable());
        tag.setOrder(tagTable.getOrder());

        return tag;
    }
}
