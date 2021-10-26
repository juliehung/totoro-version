package io.dentall.totoro.business.service.nhi.code;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;

public interface NhiCode {

    Set<NhiCodeAttribute> getAttribute();

    default boolean contain(NhiCodeAttribute attribute) {
        return getAttribute().stream().anyMatch(attr -> attr == attribute);
    }

    default String name() {
        return this.getClass().getSimpleName().replaceFirst("Nhi", "");
    }

    List<NhiCode> CODES = Collections.unmodifiableList(asList(
        new Nhi00121C(),
        new Nhi00122C(),
        new Nhi00123C(),
        new Nhi00124C(),
        new Nhi00125C(),
        new Nhi00126C(),
        new Nhi00128C(),
        new Nhi00129C(),
        new Nhi00130C(),
        new Nhi00133C(),
        new Nhi00134C(),
        new Nhi00301C(),
        new Nhi00302C(),
        new Nhi00303C(),
        new Nhi00304C(),
        new Nhi00305C(),
        new Nhi00306C(),
        new Nhi00307C(),
        new Nhi00308C(),
        new Nhi00309C(),
        new Nhi00310C(),
        new Nhi00311C(),
        new Nhi00312C(),
        new Nhi00313C(),
        new Nhi00314C(),
        new Nhi00315C(),
        new Nhi00316C(),
        new Nhi00317C(),
        new Nhi01271C(),
        new Nhi01272C(),
        new Nhi01273C(),
        new Nhi89001C(),
        new Nhi89002C(),
        new Nhi89003C(),
        new Nhi89004C(),
        new Nhi89005C(),
        new Nhi89006C(),
        new Nhi89007C(),
        new Nhi89008C(),
        new Nhi89009C(),
        new Nhi89010C(),
        new Nhi89011C(),
        new Nhi89012C(),
        new Nhi89013C(),
        new Nhi89014C(),
        new Nhi89015C(),
        new Nhi89101C(),
        new Nhi89102C(),
        new Nhi89103C(),
        new Nhi89104C(),
        new Nhi89105C(),
        new Nhi89108C(),
        new Nhi89109C(),
        new Nhi89110C(),
        new Nhi89111C(),
        new Nhi89112C(),
        new Nhi89113C(),
        new Nhi89114C(),
        new Nhi89115C(),
        new Nhi90001C(),
        new Nhi90002C(),
        new Nhi90003C(),
        new Nhi90004C(),
        new Nhi90005C(),
        new Nhi90006C(),
        new Nhi90007C(),
        new Nhi90008C(),
        new Nhi90009C(),
        new Nhi90011C(),
        new Nhi90013C(),
        new Nhi90014C(),
        new Nhi90015C(),
        new Nhi90016C(),
        new Nhi90017C(),
        new Nhi90018C(),
        new Nhi90019C(),
        new Nhi90020C(),
        new Nhi90091C(),
        new Nhi90095C(),
        new Nhi90096C(),
        new Nhi90097C(),
        new Nhi90098C(),
        new Nhi91003C(),
        new Nhi91004C(),
        new Nhi92002C(),
        new Nhi92013C(),
        new Nhi92014C(),
        new Nhi92015C(),
        new Nhi92016C(),
        new Nhi92030C(),
        new Nhi92031C(),
        new Nhi92032C(),
        new Nhi92033C(),
        new Nhi92050C(),
        new Nhi92055C(),
        new Nhi92063C(),
        new Nhi92092C()
    ));

}
