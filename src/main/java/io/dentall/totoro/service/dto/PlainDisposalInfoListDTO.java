package io.dentall.totoro.service.dto;

import io.dentall.totoro.domain.NhiProcedure;

public class PlainDisposalInfoListDTO {

    private Long id;

    private String code;

    private String name;

    public PlainDisposalInfoListDTO() {}

    public PlainDisposalInfoListDTO(NhiProcedure nhiProcedure) {
        this.id = nhiProcedure.getId();
        this.name = nhiProcedure.getName();
        this.code = nhiProcedure.getCode();
    }

    public PlainDisposalInfoListDTO id(Long id) {
        this.id = id;
        return this;
    }

    public PlainDisposalInfoListDTO code(String code) {
        this.code = code;
        return this;
    }

    public PlainDisposalInfoListDTO name(String name) {
        this.name = name;
        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
