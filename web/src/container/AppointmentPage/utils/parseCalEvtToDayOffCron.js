import moment from 'moment';

export function parseCalEvtToDayOffCron(repeat, start) {
  const startHour = moment(start).format('H');
  const startMin = moment(start).format('m');

  let dayOffCron;
  switch (repeat) {
    case 'day':
      dayOffCron = `${startMin} ${startHour} * * *`;
      break;
    case 'week':
      const weekDay = moment(start).day();
      dayOffCron = `${startMin} ${startHour} * * ${weekDay}`;
      break;
    case 'month':
      const date = moment(start).format('D');
      dayOffCron = `${startMin} ${startHour} ${date} * *`;
      break;
    default:
      break;
  }
  return dayOffCron;
}
