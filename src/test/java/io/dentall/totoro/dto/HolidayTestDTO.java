package io.dentall.totoro.dto;

import com.univocity.parsers.annotations.Convert;
import com.univocity.parsers.annotations.Parsed;
import com.univocity.parsers.conversions.ObjectConversion;

import java.time.LocalDate;

public class HolidayTestDTO {

    @Parsed
    private Long id;

    @Parsed
    private String year;

    @Parsed
    @Convert(conversionClass = convertToLocalDate.class)
    private LocalDate date;

    @Parsed
    private String dayOff;

    @Parsed
    private String dayName;

    @Parsed
    private String category;

    @Parsed
    private String description;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDayOff() {
        return dayOff;
    }

    public void setDayOff(String dayOff) {
        this.dayOff = dayOff;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static class convertToLocalDate extends ObjectConversion<LocalDate> {
        @Override
        protected LocalDate fromString(String input) {
            return LocalDate.parse(input);
        }
    }
}
