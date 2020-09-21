import moment from 'moment';

export function handleEventDrop(eventDropInfo) {
  const oldShift = JSON.parse(JSON.stringify(eventDropInfo.event.extendedProps.event));
  const durationInMiniutes = moment(oldShift.toDate).diff(moment(oldShift.fromDate), 'm');
  const oldFromTime = moment(oldShift.fromDate).format('HH:mm');
  const newDate = moment(eventDropInfo.event.start).format('YYYY-MM-DD');
  const fromDate = moment(newDate + ' ' + oldFromTime).toISOString();
  const toDate = moment(fromDate).add(durationInMiniutes, 'm').toISOString();
  let { userId } = oldShift;

  if (eventDropInfo.newResource && eventDropInfo.newResource.id) {
    userId = eventDropInfo.newResource.id;
  }

  const newShift = { fromDate, toDate, userId };
  return { oldShift, newShift };
}
