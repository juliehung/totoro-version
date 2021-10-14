package io.dentall.totoro.business.service.nhi.metric.dto;

import java.util.List;

public class CompositeDistrictDto {

    private List<TaipeiDistrictDto> taipeiDistrictDtoList;

    private List<NorthDistrictDto> northDistrictDtoList;

    private List<MiddleDistrictDto> middleDistrictDtoList;

    private List<SouthDistrictDto> southDistrictDtoList;

    private List<EastDistrictDto> eastDistrictDtoList;

    private List<KaoPingDistrictRegularDto> kaoPingDistrictRegularDtoList;

    private List<KaoPingDistrictReductionDto> kaoPingDistrictReductionDtoList;

    public List<TaipeiDistrictDto> getTaipeiDistrictDtoList() {
        return taipeiDistrictDtoList;
    }

    public void setTaipeiDistrictDtoList(List<TaipeiDistrictDto> taipeiDistrictDtoList) {
        this.taipeiDistrictDtoList = taipeiDistrictDtoList;
    }

    public List<NorthDistrictDto> getNorthDistrictDtoList() {
        return northDistrictDtoList;
    }

    public void setNorthDistrictDtoList(List<NorthDistrictDto> northDistrictDtoList) {
        this.northDistrictDtoList = northDistrictDtoList;
    }

    public List<MiddleDistrictDto> getMiddleDistrictDtoList() {
        return middleDistrictDtoList;
    }

    public void setMiddleDistrictDtoList(List<MiddleDistrictDto> middleDistrictDtoList) {
        this.middleDistrictDtoList = middleDistrictDtoList;
    }

    public List<SouthDistrictDto> getSouthDistrictDtoList() {
        return southDistrictDtoList;
    }

    public void setSouthDistrictDtoList(List<SouthDistrictDto> southDistrictDtoList) {
        this.southDistrictDtoList = southDistrictDtoList;
    }

    public List<EastDistrictDto> getEastDistrictDtoList() {
        return eastDistrictDtoList;
    }

    public void setEastDistrictDtoList(List<EastDistrictDto> eastDistrictDtoList) {
        this.eastDistrictDtoList = eastDistrictDtoList;
    }

    public List<KaoPingDistrictRegularDto> getKaoPingDistrictRegularDtoList() {
        return kaoPingDistrictRegularDtoList;
    }

    public void setKaoPingDistrictRegularDtoList(List<KaoPingDistrictRegularDto> kaoPingDistrictRegularDtoList) {
        this.kaoPingDistrictRegularDtoList = kaoPingDistrictRegularDtoList;
    }

    public List<KaoPingDistrictReductionDto> getKaoPingDistrictReductionDtoList() {
        return kaoPingDistrictReductionDtoList;
    }

    public void setKaoPingDistrictReductionDtoList(List<KaoPingDistrictReductionDto> kaoPingDistrictReductionDtoList) {
        this.kaoPingDistrictReductionDtoList = kaoPingDistrictReductionDtoList;
    }
}
