import moment from 'moment';

export function handleEventDrop(eventDropInfo) {
  const oldEvent = JSON.parse(JSON.stringify(eventDropInfo.event.extendedProps.event));
  const durationInMiniutes = moment(oldEvent.toDate).diff(moment(oldEvent.fromDate), 'm');
  const oldFromTime = moment(oldEvent.fromDate).format('HH:mm');
  const newDate = moment(eventDropInfo.event.start).format('YYYY-MM-DD');
  const fromDate = moment(newDate + ' ' + oldFromTime).toISOString();
  const toDate = moment(fromDate).add(durationInMiniutes, 'm').toISOString();

  if (eventDropInfo.newResource && eventDropInfo.newResource.id) {
    const userId = eventDropInfo.newResource.id;
    return { ...oldEvent, fromDate, toDate, userId };
  } else {
    return { ...oldEvent, fromDate, toDate };
  }
}
