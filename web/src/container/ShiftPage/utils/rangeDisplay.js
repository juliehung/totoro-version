export function rangeDisplay(range) {
  if (!range || !range.start || !range.end) return '';

  const start = convertMomentToSimpleObject(range.start);
  const end = convertMomentToSimpleObject(range.end.clone().add(-1, 's'));

  return `${start.year}/${start.month}/${start.day} - ${start.year !== end.year ? `${end.year}/` : ''}${
    start.month !== end.month ? `${end.month}/` : ''
  }${start.day !== end.day ? `${end.day}` : ''}`;
}

const convertMomentToSimpleObject = momentObject => {
  const year = momentObject.year() - 1911;
  return { year, month: momentObject.format('MM'), day: momentObject.format('DD') };
};
