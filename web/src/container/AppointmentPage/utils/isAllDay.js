import moment from 'moment';

export default function isAllDay(start, end) {
  return moment(start).format('HH:mm') === moment().startOf('day').format('HH:mm') &&
    moment(end).format('HH:mm') === moment().endOf('day').format('HH:mm')
    ? true
    : false;
}
