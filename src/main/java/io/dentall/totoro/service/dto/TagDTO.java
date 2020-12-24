package io.dentall.totoro.service.dto;

import io.dentall.totoro.domain.enumeration.TagType;

public class TagDTO {

    private Long id;

    private TagType type;

    private String name;

    private Boolean modifiable;

    private Integer order;

    private Long patientId;

    public TagDTO(Long id, TagType type, String name, Boolean modifiable, Integer order, Long patientId) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.modifiable = modifiable;
        this.order = order;
        this.patientId = patientId;
    }

    public Long getId() {
        return id;
    }

    public TagType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Boolean getModifiable() {
        return modifiable;
    }

    public Integer getOrder() {
        return order;
    }

    public Long getPatientId() {
        return patientId;
    }

}
