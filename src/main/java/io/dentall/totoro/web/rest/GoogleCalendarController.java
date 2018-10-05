package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.*;
import com.google.common.base.Strings;
import io.dentall.totoro.domain.CalendarEvent;
import io.dentall.totoro.domain.ExtendUser;
import io.dentall.totoro.domain.Link;
import io.dentall.totoro.service.UserService;
import io.dentall.totoro.service.dto.UserDTO;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.errors.InternalServerErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
public class GoogleCalendarController {

    private final Logger log = LoggerFactory.getLogger(AccountResource.class);

    private final Environment env;

    private final UserService userService;

    private static final String ENTITY_NAME = "googlecalendar";

    private static final String APPLICATION_NAME = "dentall-his/1.0";

    private static HttpTransport httpTransport;

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    public GoogleCalendarController(
        Environment env,
        UserService userService
    ) throws IOException, GeneralSecurityException {
        this.env = env;
        this.userService = userService;
        httpTransport = GoogleNetHttpTransport.newTrustedTransport();
    }

    @PostMapping("/calendar/google/link")
    @Timed
    public ResponseEntity<Boolean> link(@Valid @RequestBody Link link) throws IOException {
        // Check the link data object instance
        String gmail = link.getGmail();
        String calendarName = link.getCalendarName();
        if (Strings.isNullOrEmpty(gmail) || Strings.isNullOrEmpty(calendarName)) {
            throw new BadRequestAlertException("The gmail address or schedule id is empty", ENTITY_NAME, "linkempty");
        }

        // Get user information
        UserDTO user = userService.getUserWithAuthorities()
            .map(UserDTO::new)
            .orElseThrow(() -> new InternalServerErrorException("User could not be found"));
        ExtendUser extendUser = user.getExtendUser();

        // Check whether or not the calendar id exists
        String calendarId = extendUser.getCalendarId();
        if (!Strings.isNullOrEmpty(calendarId)) {
            throw new BadRequestAlertException("The account has already linked a calendar", ENTITY_NAME, "calendarexists");
        }

        // create a google calendar using schedule name by service account
        com.google.api.services.calendar.Calendar client = buildCalendarApiClient();
        Calendar entry = new Calendar().setSummary(calendarName);
        Calendar result = client.calendars().insert(entry).execute();

        // share calendar to gmail
        client.acl().insert(result.getId(), newReaderRule(gmail)).execute();

        userService.updateExtendUser(gmail, result.getId());
        return ResponseEntity.ok(true);
    }

    @PostMapping("/calendar/google/unlink")
    @Timed
    public ResponseEntity<Void> unlink() throws IOException {
        String calendarId = getCalendarId();
        com.google.api.services.calendar.Calendar client = buildCalendarApiClient();
        client.calendars().delete(calendarId).execute();
        userService.updateExtendUser("", "");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/calendar/google/listEvents")
    @Timed
    public List<Event> listEvents() throws IOException {
        String calendarId = getCalendarId();
        com.google.api.services.calendar.Calendar client = buildCalendarApiClient();
        Events feed = client.events().list(calendarId).execute();
        return feed.getItems();
    }

    @PostMapping("/calendar/google/addEvent")
    @Timed
    public Event addEvent(@RequestBody CalendarEvent calendarEvent) throws IOException {
        // Check the link data object instance
        String eventName = calendarEvent.getEventName();
        if (Strings.isNullOrEmpty(eventName)) {
            throw new BadRequestAlertException("The event name is empty", ENTITY_NAME, "eventempty");
        }
        Date start = calendarEvent.getStart();
        if (start == null) {
            throw new BadRequestAlertException("The event start date is empty", ENTITY_NAME, "eventstartempty");
        }
        Date end = calendarEvent.getEnd();
        if (end == null) {
            throw new BadRequestAlertException("The event end date is empty", ENTITY_NAME, "eventendempty");
        }
        String calendarId = getCalendarId();
        com.google.api.services.calendar.Calendar client = buildCalendarApiClient();
        Event event = newEvent(eventName, start, end);
        // TODO: save event id to appointment
        return client.events().insert(calendarId, event).execute();
    }

    @DeleteMapping("/calendar/google/deleteEvent")
    @Timed
    public ResponseEntity<Void> deleteEvent() throws IOException {
        // TODO: delete event from google and server
        throw new InternalServerErrorException("DeleteEvent api is not supported yet.");
    }

    private String getCalendarId() {
        ExtendUser user = userService.getUserWithAuthorities()
            .map(UserDTO::new)
            .map(UserDTO::getExtendUser)
            .orElseThrow(() -> new InternalServerErrorException("User could not be found"));

        // Check whether or not the calendar id exists
        String calendarId = user.getCalendarId();
        if (Strings.isNullOrEmpty(calendarId)) {
            throw new BadRequestAlertException("The account did not link to a calendar", "link", "calendarempty");
        }
        return calendarId;
    }

    private GoogleCredential loadCredential() throws IOException {
        String path = env.getProperty("calendar.secret.google");
        if (Strings.isNullOrEmpty(path)) {
            throw new InternalServerErrorException("Failed to load secret of google calendar api");
        }
        return GoogleCredential.fromStream(new FileInputStream(path))
            .createScoped(Collections.singleton(CalendarScopes.CALENDAR));
    }

    private com.google.api.services.calendar.Calendar buildCalendarApiClient() throws IOException {
        GoogleCredential credential = loadCredential();
        return new com.google.api.services.calendar.Calendar.Builder(
            httpTransport, JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME).build();
    }

    private static AclRule newReaderRule(String gmail) {
        return new AclRule()
            .setRole("reader")
            .setScope(new AclRule.Scope().setType("user").setValue(gmail));
    }

    private static Event newEvent(String eventName, Date startDate, Date endDate) {
        Event event = new Event();
        event.setSummary(eventName);
        DateTime start = new DateTime(startDate, TimeZone.getTimeZone("CST"));
        event.setStart(new EventDateTime().setDateTime(start));
        DateTime end = new DateTime(endDate, TimeZone.getTimeZone("CST"));
        event.setEnd(new EventDateTime().setDateTime(end));
        return event;
    }

}
