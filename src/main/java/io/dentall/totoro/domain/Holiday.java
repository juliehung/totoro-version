package io.dentall.totoro.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "holiday")
public class Holiday implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "year", nullable = false)
    private String year;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "day_off", nullable = false)
    private String dayOff;

    @Column(name = "day_name")
    private String dayName;

    @Column(name = "category")
    private String category;

    @Column(name = "description")
    private String description;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getYear() {
        return year;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDayOff(String dayOff) {
        this.dayOff = dayOff;
    }

    public String getDayOff() {
        return dayOff;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public String getDayName() {
        return dayName;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Holiday{" +
            "id=" + id + '\'' +
            "year=" + year + '\'' +
            "date=" + date + '\'' +
            "dayOff=" + dayOff + '\'' +
            "dayName=" + dayName + '\'' +
            "category=" + category + '\'' +
            "description=" + description + '\'' +
            '}';
    }
}
