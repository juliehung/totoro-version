package io.dentall.totoro.business.service.nhi.metric.mapper;

import io.dentall.totoro.business.service.nhi.metric.vm.TimeLineData;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper
public interface TimeLineDataMapper {

    TimeLineDataMapper INSTANCE = Mappers.getMapper(TimeLineDataMapper.class);

    List<TimeLineData> mapToTimeLineData(Set<Map.Entry<LocalDate, BigDecimal>> source);

    default TimeLineData map(Map.Entry<LocalDate, BigDecimal> entry) {
        TimeLineData timeLineData = new TimeLineData();
        timeLineData.setDate(entry.getKey());
        timeLineData.setValue(entry.getValue());
        return timeLineData;
    }
}
