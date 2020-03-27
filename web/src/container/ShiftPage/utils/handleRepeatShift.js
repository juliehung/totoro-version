import moment from 'moment';

export const generateApiObj = (userId, date, timeRange, repeatWeek) => {
  let shift = [];
  if (repeatWeek === 0) {
    shift = [...shift, { userId, ...combineDateAndRange(date, timeRange) }];
  } else if (repeatWeek > 0) {
    let currentDate = moment(date);
    const lastDayOfMonth = moment(date).endOf('month');
    while (currentDate.isBefore(lastDayOfMonth)) {
      shift = [...shift, { userId, ...combineDateAndRange(currentDate, timeRange) }];
      currentDate.add(repeatWeek, 'week');
    }
  }
  return shift;
};

function combineDateAndRange(date, range) {
  const dateString = moment(date).format('YYYY-MM-DD');
  const startTime = moment(range.start).format('HH:mm');
  const endEnd = moment(range.end).format('HH:mm');

  const fromDate = moment(dateString + ' ' + startTime).toISOString();
  const toDate = moment(dateString + ' ' + endEnd).toISOString();
  return { fromDate, toDate };
}

export const handleRepeatShift = (data, defaultShift) => {
  console.log(666, defaultShift, data);

  let shift = [];
  if (data.radioValue === 1) {
    data.selectedShift
      .map(s => defaultShift.find(ds => ds.origin.id === s))
      .forEach(ds => {
        const range = { start: moment(ds.origin.range.start, 'HH:mm'), end: moment(ds.origin.range.end, 'HH:mm') };
        shift = [...shift, ...generateApiObj(data.userId, data.date, range, data.week)];
      });
  } else if (data.radioValue === 2) {
    const range = { start: data.customRange[0], end: data.customRange[1] };
    shift = generateApiObj(data.userId, data.date, range, data.week);
  }

  return shift;
};
