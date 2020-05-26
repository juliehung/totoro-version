import moment from 'moment';

export function weekOfMonth(m) {
  return m.week() - moment(m).startOf('month').week() + 1;
}
