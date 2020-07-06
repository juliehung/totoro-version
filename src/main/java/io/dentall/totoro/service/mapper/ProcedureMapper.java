package io.dentall.totoro.service.mapper;

import io.dentall.totoro.domain.ExtendUser;
import io.dentall.totoro.domain.Procedure;
import io.dentall.totoro.domain.ProcedureType;
import io.dentall.totoro.service.dto.table.ProcedureTable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ProcedureMapper {
    public Procedure procedureTableToProcedure(ProcedureTable procedureTable) {

        Procedure procedure = new Procedure();

        procedure.setId(procedureTable.getId());
        procedure.setContent(procedureTable.getContent());
        procedure.setPrice(procedureTable.getPrice());

        // Procedure.ExtendUser
        if (procedureTable.getDoctor_Id() != null) {
            ExtendUser extendUser = new ExtendUser();
            extendUser.setId(procedureTable.getDoctor_Id());

            Set<Procedure> set = new HashSet<>();
            set.add(procedure);

            extendUser.setProcedures(set);
            procedure.setDoctor(extendUser);
        }

        // Procedure.ProcedureType
        if (procedureTable.getProcedureType_Id() != null) {
            ProcedureType procedureType = new ProcedureType();
            procedureType.setId(procedureTable.getProcedureType_Id());

            procedure.setProcedureType(procedureType);
        }

        return procedure;
    }
}
