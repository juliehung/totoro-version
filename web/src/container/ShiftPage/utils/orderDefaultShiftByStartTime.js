import moment from 'moment';

export function orderDefaultShiftByStartTime(defaultShift) {
  if (defaultShift.find(e => e.isNew)) {
    const newShift = defaultShift.find(e => e.isNew);
    return [newShift, ...defaultShift.filter(e => !e.isNew).sort(compareMoment)];
  }
  return defaultShift.slice().sort(compareMoment);
}

function compareMoment(a, b) {
  return moment(a.origin.range.start, 'HH:mm').unix() - moment(b.origin.range.start, 'HH:mm').unix();
}
