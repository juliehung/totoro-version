package io.dentall.totoro.web.rest.vm;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.dentall.totoro.domain.NhiMedicalRecord;

import javax.persistence.Column;

public interface NhiMedicalRecordVM2 {

    String getDate();

    String getNhiCategory();

    String getNhiCode();

    String getPart();

    String getUsage();

    String getNote();

    String getDays();

    String getTotal();

    String getMandarin();

}
