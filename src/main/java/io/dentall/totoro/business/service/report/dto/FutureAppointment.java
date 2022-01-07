package io.dentall.totoro.business.service.report.dto;

import java.util.List;

public interface FutureAppointment {

    Long getPatientId();

    void setFutureAppointmentList(List<FutureAppointmentVo> futureAppointmentList);

}
