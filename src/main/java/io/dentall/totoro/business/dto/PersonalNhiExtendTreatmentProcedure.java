package io.dentall.totoro.business.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Stack;
import java.util.stream.Stream;

public class PersonalNhiExtendTreatmentProcedure {
    // 健保代碼
    private String code;

    // 該健保代碼申報時間 確保資料已被排序過
    private Stack<LocalDate> declarationDates = new Stack<>();

    public PersonalNhiExtendTreatmentProcedure code(String code) {
        this.code = code;
        return this;
    }

    public PersonalNhiExtendTreatmentProcedure declarationDate(LocalDate declarationDate) {
        this.declarationDates.push(declarationDate);
        return this;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void pushDeclarationDate(LocalDate declarationDate) {
        this.declarationDates.push(declarationDate);
    }

    public Stream<LocalDate> getSortedDeclarationDates() {
        return this.declarationDates.stream().sorted();
    }
}
