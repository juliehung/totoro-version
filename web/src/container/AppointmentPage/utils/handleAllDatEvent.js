import moment from 'moment';

export function handleAllDatEvent(bfEvent, viewType) {
  if (viewType === 'dayGridMonth') {
    return bfEvent
      .map(e => moment(e.start).format('YYYY-MM-DD'))
      .filter((item, pos, self) => self.indexOf(item) == pos)
      .map(e => ({
        start: moment(e).format('YYYY-MM-DD HH:mm'),
        allDay: true,
        rendering: 'background',
        eventType: 'doctorShift',
      }));
  }
  return bfEvent;
}
