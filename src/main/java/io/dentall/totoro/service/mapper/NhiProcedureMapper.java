package io.dentall.totoro.service.mapper;

import io.dentall.totoro.domain.NhiIcd9Cm;
import io.dentall.totoro.domain.NhiProcedure;
import io.dentall.totoro.domain.NhiProcedureType;
import io.dentall.totoro.service.dto.table.NhiProcedureTable;
import org.springframework.stereotype.Service;

@Service
public class NhiProcedureMapper {

    public NhiProcedure nhiProcedureTableToNhiProcedure(NhiProcedureTable nhiProcedureTable) {
        NhiProcedure nhiProcedure = new NhiProcedure();

        nhiProcedure.setId(nhiProcedureTable.getId());
        nhiProcedure.setCode(nhiProcedureTable.getCode());
        nhiProcedure.setName(nhiProcedureTable.getName());
        nhiProcedure.setPoint(nhiProcedureTable.getPoint());
        nhiProcedure.setEnglishName(nhiProcedureTable.getEnglishName());
        nhiProcedure.setDefaultIcd10CmId(nhiProcedureTable.getDefaultIcd10CmId());
        nhiProcedure.setDescription(nhiProcedureTable.getDescription());
        nhiProcedure.setExclude(nhiProcedureTable.getExclude());
        nhiProcedure.setFdi(nhiProcedureTable.getFdi());
        nhiProcedure.setSpecificCode(nhiProcedureTable.getSpecificCode());
        nhiProcedure.setChiefComplaint(nhiProcedureTable.getChiefComplaint());

        NhiProcedureType nhiProcedureType  = new NhiProcedureType();
        nhiProcedureType.setId(nhiProcedureTable.getNhiProcedureType_Id());
        nhiProcedure.setNhiProcedureType(nhiProcedureType);

        NhiIcd9Cm nhiIcd9Cm = new NhiIcd9Cm();
        nhiIcd9Cm.setId(nhiProcedureTable.getNhiIcd9Cm_Id());
        nhiProcedure.setNhiIcd9Cm(nhiIcd9Cm);

        return nhiProcedure;
    }

}
