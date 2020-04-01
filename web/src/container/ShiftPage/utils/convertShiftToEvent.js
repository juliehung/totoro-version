import moment from 'moment';

const convertShiftToEvent = (shifts, resourceColor) => {
  const event = shifts.map(s => {
    const id = Object.keys(resourceColor).find(id => `${id}` === `${s.userId}`);
    let backgroundColor;
    if (id) {
      backgroundColor = resourceColor[id];
    }

    return {
      id: s.id,
      resourceId: s.userId,
      start: moment(s.fromDate).toDate(),
      end: moment(s.toDate).toDate(),
      event: s,
      backgroundColor,
      borderColor: backgroundColor,
    };
  });
  return event;
};

export default convertShiftToEvent;
