package io.dentall.totoro.service.mapper;

import io.dentall.totoro.domain.Note;
import io.dentall.totoro.web.rest.vm.NoteCreateVM;
import io.dentall.totoro.web.rest.vm.NoteVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    uses = { PatientDomainMapper.class }
)
public interface NoteMapper {

    NoteMapper INSTANCE = Mappers.getMapper(NoteMapper.class);

    @Mapping(target = "patientId", source = "note.patient.id")
    @Mapping(target = "userId", source = "note.user.id")
    NoteVM convertNoteDomainToVm(Note note);

    Note convertNoteCreateVmToDomain(NoteCreateVM noteCreateVM);

}
