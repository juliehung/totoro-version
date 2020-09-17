import moment from 'moment';
import parser from 'cron-parser';
import isAllDay from './isAllDay';

export default function convertCalendarToEvent(calendarEvents) {
  const events = [];
  const color = 'rgb(230, 124, 115)';

  calendarEvents.forEach(e => {
    if (e.dayOffCron && e.dayOffCron.length !== 0) {
      const options = {
        currentDate: moment(e.start).startOf('day').add(-1, 's'),
        endDate: moment(e.end).endOf('day'),
        iterator: true,
      };
      const interval = parser.parseExpression(e.dayOffCron, options);
      while (true) {
        try {
          const cronObj = interval.next();
          const start = cronObj.value.toDate();
          let end = moment(cronObj.value.toDate()).add(e.duration, 'm').toDate();

          const title = e.doctor ? e.doctor.user.firstName : '診所休假';
          const resourceId = e.doctor ? e.doctor.user.id : null;
          const allDay = isAllDay(start, end);
          if (allDay) {
            end = moment(end).add(1, 'm').toDate();
          }

          events.push({
            start,
            end,
            title,
            color,
            resourceId,
            doctorDayOff: e,
            eventType: 'doctorDayOff',
            editable: false,
            allDay,
          });
        } catch (e) {
          break;
        }
      }
    } else {
      const start = moment(e.start).toDate();
      let end = moment(e.end).toDate();
      const title = e.doctor ? e.doctor.user.firstName : '診所休假';
      const resourceId = e.doctor ? e.doctor.user.id : null;
      const allDay = isAllDay(start, end);
      if (allDay) {
        end = moment(end).add(1, 'm').toDate();
      }
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
