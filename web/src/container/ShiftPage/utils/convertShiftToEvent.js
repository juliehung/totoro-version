import moment from 'moment';

const convertShiftToEvent = shifts => {
  const event = shifts.map(s => ({
    id: s.id,
    resourceId: s.userId,
    title: '',
    start: moment(s.fromDate).toDate(),
    end: moment(s.toDate).toDate(),
    event: s,
  }));
  return event;
};

export default convertShiftToEvent;
