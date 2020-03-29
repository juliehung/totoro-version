import moment from 'moment';

function handleShiftEvtTitle(shifts, defaultShifts) {
  return shifts.map(s => {
    const start = moment(s.start).format('HH:mm');
    const end = moment(s.end).format('HH:mm');
    const defaultShift = defaultShifts.find(ds => ds.origin.range.start === start && ds.origin.range.end === end);
    if (defaultShift) {
      return { ...s, title: defaultShift.origin.name };
    }
    return s;
  });
}

export default handleShiftEvtTitle;
