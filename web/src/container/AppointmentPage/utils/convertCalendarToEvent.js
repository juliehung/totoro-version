import moment from 'moment';
import parser from 'cron-parser';
import isAllDay from './isAllDay';

export default function convertCalendarToEvent(calendarEvents) {
  let events = [];
  const color = '#dfdfdf';

  calendarEvents.forEach(e => {
    if (e.dayOffCron && e.dayOffCron.length !== 0) {
      const options = {
        currentDate: moment(e.start).startOf('day'),
        endDate: moment(e.end).endOf('day'),
        iterator: true,
      };
      const interval = parser.parseExpression(e.dayOffCron, options);
      while (true) {
        try {
          const cronObj = interval.next();
          const start = cronObj.value.toDate();
          const end = moment(cronObj.value.toDate())
            .add(e.duration, 'm')
            .toDate();
          const title = e.doctor ? e.doctor.user.firstName : '診所休假';
          const resourceId = e.doctor ? e.doctor.user.id : null;

          events.push({
            start,
            end,
            title,
            color,
            resourceId,
            doctorDayOff: e,
            eventType: 'doctorDayOff',
            editable: false,
          });
        } catch (e) {
          break;
        }
      }
    } else {
      const start = moment(e.start).toDate();
      const end = moment(e.end).toDate();
      const title = e.doctor ? e.doctor.user.firstName : '診所休假';
      const resourceId = e.doctor ? e.doctor.user.id : null;
      const allDay = isAllDay(start, end);

      events.push({
        start,
        end,
        title,
        color,
        resourceId,
        doctorDayOff: e,
        eventType: 'doctorDayOff',
        editable: false,
        resourceEditable: false,
        allDay,
      });
    }
  });

  return events;
}
