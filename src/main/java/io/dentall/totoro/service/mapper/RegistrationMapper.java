package io.dentall.totoro.service.mapper;

import io.dentall.totoro.domain.Accounting;
import io.dentall.totoro.domain.Registration;
import io.dentall.totoro.service.dto.table.RegistrationTable;
import org.springframework.stereotype.Service;

@Service
public class RegistrationMapper {
    public Registration registrationTableToRegistration(RegistrationTable registrationTable) {
        Registration registration = new Registration();

        registration.setId(registrationTable.getId());
        registration.setStatus(registrationTable.getStatus());
        registration.setArrivalTime(registrationTable.getArrivalTime());
        registration.setType(registrationTable.getType());
        registration.setOnSite(registrationTable.getOnSite());
        registration.setNoCard(registrationTable.getNoCard());
        registration.setAbnormalCode(registrationTable.getAbnormalCode());

        Accounting accounting = new Accounting();
        accounting.setId(registrationTable.getAccounting_Id());
        registration.setAccounting(accounting);

        return registration;
    }
}
