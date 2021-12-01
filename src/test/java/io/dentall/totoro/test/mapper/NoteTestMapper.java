package io.dentall.totoro.test.mapper;

import io.dentall.totoro.domain.Note;
import io.dentall.totoro.domain.Patient;
import io.dentall.totoro.domain.User;
import io.dentall.totoro.web.rest.vm.NoteCreateVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Map;

@Mapper
public interface NoteTestMapper {

    NoteTestMapper INSTANCE = Mappers.getMapper( NoteTestMapper.class );

    @Mapping(target="type", expression="java( io.dentall.totoro.domain.enumeration.NoteType.valueOf(map.get(\"type\")) )")
    @Mapping(target="content", expression="java( map.get(\"content\") )")
    Note mapToNote(Map<String, String> map);

    @Mapping(target = "patientId", source = "note.patient.id")
    @Mapping(target = "userId", source = "note.user.id")
    NoteCreateVM noteToNoteCreateVM(Note note);

}
