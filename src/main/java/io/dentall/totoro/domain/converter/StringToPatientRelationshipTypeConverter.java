package io.dentall.totoro.domain.converter;

import io.dentall.totoro.domain.enumeration.PatientRelationshipType;
import org.springframework.core.convert.converter.Converter;

public class StringToPatientRelationshipTypeConverter implements Converter<String, PatientRelationshipType> {
    @Override
    public PatientRelationshipType convert(String source) {
        return PatientRelationshipType.valueOf(source.toUpperCase());
    }
}
