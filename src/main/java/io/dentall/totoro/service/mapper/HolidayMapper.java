package io.dentall.totoro.service.mapper;

import io.dentall.totoro.domain.Holiday;
import io.dentall.totoro.service.dto.HolidayDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.List;

import static java.lang.Integer.parseInt;

@Mapper
public interface HolidayMapper {

    HolidayMapper INSTANCE = Mappers.getMapper(HolidayMapper.class);

    @Mappings({
        @Mapping(target = "year", source = "date", qualifiedByName = "mapToYear"),
        @Mapping(target = "date", source = "date"),
        @Mapping(target = "dayName", source = "name"),
        @Mapping(target = "dayOff", source = "isHoliday", qualifiedByName = "mapToDayOff"),
        @Mapping(target = "category", source = "holidayCategory"),
    })
    Holiday mapToHoliday(HolidayDTO dto);

    List<Holiday> mapToHoliday(List<HolidayDTO> dto);

    @Named("mapToDayOff")
    default String mapToDayOff(String text) {
        if ("是".equals(text)) {
            return "Y";
        } else {
            return "N";
        }
    }

    @Named("mapToYear")
    default String mapToYear(String text) {
        return text.substring(0, 4);
    }

    default LocalDate mapToDate(String text) {
        String[] dateText = text.split("/");
        return LocalDate.of(parseInt(dateText[0]), parseInt(dateText[1]), parseInt(dateText[2]));
    }

}
