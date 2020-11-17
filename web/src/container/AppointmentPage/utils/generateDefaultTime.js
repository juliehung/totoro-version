import moment from 'moment';

export function generateDefaultTime() {
  const start = moment().startOf('d');
  start.add(8, 'hour');
  const end = moment().add(1, 'd').startOf('d');
  end.subtract(59, 'm');
  const defaultTime = [];
  while (start.isBefore(end)) {
    defaultTime.push(start.clone());
    start.add(15, 'm');
  }
  return defaultTime;
}

export const defaultTimeOption = generateDefaultTime();
