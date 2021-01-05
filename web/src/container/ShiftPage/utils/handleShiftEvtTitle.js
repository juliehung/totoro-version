import moment from 'moment';

function handleShiftEvtTitle(shifts, defaultShifts) {
  return shifts.map(s => {
    const start = moment(s.start).format('HH:mm');
    const end = moment(s.end).format('HH:mm');
    const defaultShift = defaultShifts.find(
      ds =>
        moment(ds.origin.range.start, 'HH:mm').format('HH:mm') === start &&
        moment(ds.origin.range.end, 'HH:mm').format('HH:mm') === end,
    );

    if (defaultShift) {
      return { ...s, title: moment(s.start).valueOf(), displayText: defaultShift.origin.name };
    }
    return {
      ...s,
      title: moment(s.start).valueOf(),
      displayText: `${moment(s.start).format('H:mm')}~${moment(s.end).format('H:mm')}`,
    };
  });
}

export default handleShiftEvtTitle;
