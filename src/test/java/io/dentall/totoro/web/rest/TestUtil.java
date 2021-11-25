package io.dentall.totoro.web.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.dentall.totoro.domain.*;
import io.dentall.totoro.repository.*;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.http.MediaType;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Utility class for testing REST controllers.
 */
public class TestUtil {

    /**
     * MediaType for JSON UTF8
     */
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
        MediaType.APPLICATION_JSON.getType(),
        MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    /**
     * Convert an object to JSON byte array.
     *
     * @param object the object to convert
     * @return the JSON byte array
     * @throws IOException
     */
    public static byte[] convertObjectToJsonBytes(Object object)
        throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        mapper.setAnnotationIntrospector(new IgnoreJacksonWriteOnlyAccess());

        JavaTimeModule module = new JavaTimeModule();
        mapper.registerModule(module);

        return mapper.writeValueAsBytes(object);
    }

    /**
     * Create a byte array with a specific size filled with specified data.
     *
     * @param size the size of the byte array
     * @param data the data to put in the byte array
     * @return the JSON byte array
     */
    public static byte[] createByteArray(int size, String data) {
        byte[] byteArray = new byte[size];
        for (int i = 0; i < size; i++) {
            byteArray[i] = Byte.parseByte(data, 2);
        }
        return byteArray;
    }

    /**
     * A matcher that tests that the examined string represents the same instant as the reference datetime.
     */
    public static class ZonedDateTimeMatcher extends TypeSafeDiagnosingMatcher<String> {

        private final ZonedDateTime date;

        public ZonedDateTimeMatcher(ZonedDateTime date) {
            this.date = date;
        }

        @Override
        protected boolean matchesSafely(String item, Description mismatchDescription) {
            try {
                if (!date.isEqual(ZonedDateTime.parse(item))) {
                    mismatchDescription.appendText("was ").appendValue(item);
                    return false;
                }
                return true;
            } catch (DateTimeParseException e) {
                mismatchDescription.appendText("was ").appendValue(item)
                    .appendText(", which could not be parsed as a ZonedDateTime");
                return false;
            }

        }

        @Override
        public void describeTo(Description description) {
            description.appendText("a String representing the same Instant as ").appendValue(date);
        }
    }

    /**
     * Creates a matcher that matches when the examined string reprensents the same instant as the reference datetime
     *
     * @param date the reference datetime against which the examined string is checked
     */
    public static ZonedDateTimeMatcher sameInstant(ZonedDateTime date) {
        return new ZonedDateTimeMatcher(date);
    }

    /**
     * Verifies the equals/hashcode contract on the domain object.
     */
    public static <T> void equalsVerifier(Class<T> clazz) throws Exception {
        T domainObject1 = clazz.getConstructor().newInstance();
        assertThat(domainObject1.toString()).isNotNull();
        assertThat(domainObject1).isEqualTo(domainObject1);
        assertThat(domainObject1.hashCode()).isEqualTo(domainObject1.hashCode());
        // Test with an instance of another class
        Object testOtherObject = new Object();
        assertThat(domainObject1).isNotEqualTo(testOtherObject);
        assertThat(domainObject1).isNotEqualTo(null);
        // Test with an instance of the same class
        T domainObject2 = clazz.getConstructor().newInstance();
        assertThat(domainObject1).isNotEqualTo(domainObject2);
        // HashCodes are equals because the objects are not persisted yet
        assertThat(domainObject1.hashCode()).isEqualTo(domainObject2.hashCode());
    }

    /**
     * Create a FormattingConversionService which use ISO date format, instead of the localized one.
     *
     * @return the FormattingConversionService
     */
    public static FormattingConversionService createFormattingConversionService() {
        DefaultFormattingConversionService dfcs = new DefaultFormattingConversionService();
        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
        registrar.setUseIsoFormat(true);
        registrar.registerFormatters(dfcs);
        return dfcs;
    }

    public static Patient createPatient(
        EntityManager em,
        UserRepository userRepository,
        TagRepository tagRepository,
        PatientRepository patientRepository) {
        Patient patient = PatientResourceIntTest.createEntity(em);

        User user = UserResourceIntTest.createEntity(em);
        userRepository.save(user);

        ExtendUser extendUser = user.getExtendUser();
        patient.setLastDoctor(extendUser);
        patient.setFirstDoctor(extendUser);

        Tag tag = TagResourceIntTest.createEntity(em);
        patient.addTag(tagRepository.save(tag));

        patient.setQuestionnaire(QuestionnaireResourceIntTest.createEntity(em));

        return patientRepository.save(patient);
    }

    public static User createUser(EntityManager em, UserRepository userRepository) {
        User user = UserResourceIntTest.createEntity(em);
        return userRepository.save(user);
    }

    public static Patient createPatient2(
        User user,
        EntityManager em,
        PatientRepository patientRepository) {
        Patient patient = PatientResourceIntTest.createEntity(em);

        ExtendUser extendUser = user.getExtendUser();
        patient.setLastDoctor(extendUser);
        patient.setFirstDoctor(extendUser);

        return patientRepository.saveAndFlush(patient);
    }

    // https://stackoverflow.com/a/55064740
    private static class IgnoreJacksonWriteOnlyAccess extends JacksonAnnotationIntrospector {

        @Override
        public JsonProperty.Access findPropertyAccess(Annotated m) {
            JsonProperty.Access access = super.findPropertyAccess(m);
            if (access == JsonProperty.Access.WRITE_ONLY) {
                return JsonProperty.Access.AUTO;
            }
            return access;
        }
    }

    private static Appointment createAppointment(User user,
                                                 Patient patient,
                                                 EntityManager em,
                                                 RegistrationRepository registrationRepository,
                                                 AppointmentRepository appointmentRepository) {
        Appointment appointment = AppointmentResourceIntTest.createEntity(em);
        appointment.setPatient(patient);
        appointment.setDoctor(user.getExtendUser());

        Registration registration = registrationRepository.save(RegistrationResourceIntTest.createEntity(em));
        appointment.setRegistration(registration);
        registration.setAppointment(appointment);

        return appointmentRepository.saveAndFlush(appointment);
    }

    public static Disposal createDisposal(User user,
                                          EntityManager em,
                                          RegistrationRepository registrationRepository,
                                          AppointmentRepository appointmentRepository,
                                          DisposalRepository disposalRepository,
                                          PatientRepository patientRepository) {
        Patient patient = createPatient2(user, em, patientRepository);
        return createDisposal(user, patient, em, registrationRepository, appointmentRepository, disposalRepository);
    }

    public static Disposal createDisposal(User user,
                                          Patient patient,
                                          EntityManager em,
                                          RegistrationRepository registrationRepository,
                                          AppointmentRepository appointmentRepository,
                                          DisposalRepository disposalRepository) {
        Disposal disposal = DisposalResourceIntTest.createEntity(em);

        return createDisposal(user, patient, disposal, em, registrationRepository, appointmentRepository, disposalRepository);
    }

    public static Disposal createDisposal(User user,
                                          Patient patient,
                                          Disposal disposal,
                                          EntityManager em,
                                          RegistrationRepository registrationRepository,
                                          AppointmentRepository appointmentRepository,
                                          DisposalRepository disposalRepository) {
        Appointment appointment = createAppointment(user, patient, em, registrationRepository, appointmentRepository);
        disposal.setRegistration(appointment.getRegistration());

        return disposalRepository.saveAndFlush(disposal);
    }
}
