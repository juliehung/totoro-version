import moment from 'moment';

export function handleEventDrop(eventDropInfo) {
  const oldShift = JSON.parse(JSON.stringify(eventDropInfo.event.extendedProps.event));
  const durationInMiniutes = moment(oldShift.toDate).diff(moment(oldShift.fromDate), 'm');
  const oldFromTime = moment(oldShift.fromDate).format('HH:mm');

  const getOldDate = moment(eventDropInfo.oldEvent.start).format('YYYY-MM-DD');
  const getOldFromDate = moment(getOldDate + ' ' + oldFromTime).toISOString();
  const getOldToDate = moment(getOldFromDate).add(durationInMiniutes, 'm').toISOString();
  oldShift.fromDate = getOldFromDate;
  oldShift.toDate = getOldToDate;

  const newDate = moment(eventDropInfo.event.start).format('YYYY-MM-DD');
  const fromDate = moment(newDate + ' ' + oldFromTime).toISOString();
  const toDate = moment(fromDate).add(durationInMiniutes, 'm').toISOString();
  const userId = parseInt(
    eventDropInfo.newResource && eventDropInfo.newResource.id ? eventDropInfo.newResource.id : oldShift?.userId,
  );

  const newShift = { fromDate, toDate, userId };
  return { oldShift, newShift };
}
