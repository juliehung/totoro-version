import moment from 'moment';

export function handleEventDrop(eventDropInfo) {
  const oldEvent = JSON.parse(JSON.stringify(eventDropInfo.event.extendedProps.event));
  const fromDate = moment(eventDropInfo.event.start).toISOString();
  const toDate = moment(eventDropInfo.event.end).toISOString();
  if (eventDropInfo.newResource && eventDropInfo.newResource.id) {
    const userId = eventDropInfo.newResource.id;
    return { ...oldEvent, fromDate, toDate, userId };
  } else {
    return { ...oldEvent, fromDate, toDate };
  }
}
