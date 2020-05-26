export function rangeDisplay(range) {
  if (!range || !range.start || !range.end) return '';

  const start = convertMomentToSimpleObject(range.start);
  const end = convertMomentToSimpleObject(range.end.clone().add(-1, 's'));

  return `${range.start.format('YYYY/MM/DD')} - ${start.year !== end.year ? `${end.year}/` : ''}${
    start.month !== end.month ? `${end.month}/` : ''
  }${start.day !== end.day ? `${end.day}` : ''}`;
}

const convertMomentToSimpleObject = momentObject => {
  return { year: momentObject.format('YYYY'), month: momentObject.format('MM'), day: momentObject.format('DD') };
};
