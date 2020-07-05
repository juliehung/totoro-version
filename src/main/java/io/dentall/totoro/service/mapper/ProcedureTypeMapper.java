package io.dentall.totoro.service.mapper;

import io.dentall.totoro.domain.ProcedureType;
import io.dentall.totoro.service.dto.table.ProcedureTypeTable;
import org.springframework.stereotype.Service;

@Service
public class ProcedureTypeMapper {
    public ProcedureType procedureTypeTableToProcedureType(ProcedureTypeTable procedureTypeTable) {

        ProcedureType procedureType = new ProcedureType();

        procedureType.setId(procedureTypeTable.getId());
        procedureType.setMajor(procedureTypeTable.getMajor());
        procedureType.setMinor(procedureTypeTable.getMinor());

        return procedureType;
    }
}
