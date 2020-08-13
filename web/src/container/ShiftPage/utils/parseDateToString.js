import moment from 'moment';

export function parseDateToString(date, isRoc) {
  const momentDate = moment(date);
  let year = momentDate.year();
  if (isRoc) {
    year = year - 1911;
  }
  return year + '年' + moment(date).format(' 第 w 週, MMM') + '第' + weekOfMonth(moment(date)) + '週';
}

function weekOfMonth(m) {
  return m.week() - moment(m).startOf('month').week() + 1;
}
