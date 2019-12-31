export function parseDayOffCronToRepeat(dayOffCron) {
  const dayOffCronArr = dayOffCron.split(' ');
  if (dayOffCronArr[2] === '*' && dayOffCronArr[3] === '*' && dayOffCronArr[4] === '*') {
    return 'day';
  } else if (dayOffCronArr[4] !== '*') {
    return 'week';
  } else if (dayOffCronArr[2] !== '*') {
    return 'month';
  }
}
