package io.dentall.totoro.business.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dentall.totoro.service.dto.SmsEventDTO;

import java.util.List;

public class SmsEventsPagination {

    @JsonProperty
    private List<SmsEventDTO> events;

    @JsonProperty
    private Long total;

    public List<SmsEventDTO> getEvents() {
        return events;
    }

    public Long getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return "SmsEventsPagination{" +
            "events='" + events + '\'' +
            ", total=" + total +
            "}";
    }
}
