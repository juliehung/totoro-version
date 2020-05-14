import moment from 'moment';

export function generateDefaultTime() {
  const start = moment().startOf('d');
  const end = moment().add(1, 'd').startOf('d');
  const defaultTime = [];
  while (start.isBefore(end)) {
    defaultTime.push(start.clone());
    start.add(15, 'm');
  }
  return defaultTime;
}

export const defaultTimeOption = generateDefaultTime();
