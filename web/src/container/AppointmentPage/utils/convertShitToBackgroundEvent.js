import moment from 'moment';

export function convertShitToBackgroundEvent(shifts) {
  const c = shifts.map(s => ({
    start: moment(s.fromDate).format('YYYY-MM-DD HH:mm'),
    end: moment(s.toDate).format('YYYY-MM-DD HH:mm'),
    rendering: 'background',
    eventType: 'doctorShift',
    resourceId: s.userId,
  }));

  return c;
}
