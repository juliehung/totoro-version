import moment from 'moment';

export function parseDateToString(date) {
  const momentDate = moment(date);
  const moment7Date = moment(date).add(6, 'day');
  const year = momentDate.year() - 1911;
  const moment7Year = moment7Date.year() - 1911;
  const currentYearWeek =
    moment7Year !== year ? parseInt(moment(date).subtract(6, 'day').format('w')) + 1 : momentDate.format('w');
  return `${year}年 第 ${currentYearWeek} 週, ${moment(date).format('M')}月第 ${weekOfMonth(
    moment7Year !== year,
    momentDate,
  )} 週`;
}

function weekOfMonth(isNextYear, m) {
  return isNextYear
    ? m.subtract(6, 'day').week() - moment(m).startOf('month').week() + 2
    : m.week() - moment(m).startOf('month').week() + 1;
}
