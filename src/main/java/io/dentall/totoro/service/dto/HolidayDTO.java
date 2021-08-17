package io.dentall.totoro.service.dto;

public class HolidayDTO {

    private String date;

    private String name;

    private String isHoliday;

    private String holidayCategory;

    private String description;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsHoliday() {
        return isHoliday;
    }

    public void setIsHoliday(String isHoliday) {
        this.isHoliday = isHoliday;
    }

    public String getHolidayCategory() {
        return holidayCategory;
    }

    public void setHolidayCategory(String holidayCategory) {
        this.holidayCategory = holidayCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
