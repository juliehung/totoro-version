import moment from 'moment';

export function reverseEvents(shiftEvents, viewType, range) {
  if (shiftEvents && viewType && range.start && range.end) {
    if (viewType === 'dayGridMonth') {
      const filteredEvent = shiftEvents
        .map(e => moment(e.start).startOf('d').format('YYYY-MM-DD HH:mm'))
        .filter((item, pos, self) => self.indexOf(item) === pos);

      let firstDayOfMonth = moment(range.start).startOf('d');
      const endDayOfMonth = moment(range.end).endOf('d');
      const datesOfMonth = [];
      while (firstDayOfMonth.isBefore(endDayOfMonth)) {
        datesOfMonth.push(firstDayOfMonth.format('YYYY-MM-DD HH:mm'));
        firstDayOfMonth = moment(firstDayOfMonth).add(1, 'd');
      }

      return datesOfMonth
        .filter(d => !filteredEvent.includes(d))
        .map(e => ({
          start: moment(e).format('YYYY-MM-DD HH:mm'),
          allDay: true,
          rendering: 'background',
          eventType: 'doctorShift',
          backgroundColor: 'rgba(143,155,179,0.24)',
        }));
    } else if (viewType === 'timeGridWeek' || viewType === 'listWeek') {
      const allTimeEvent = [generateRangeEvent(range)];
      const mergedEvent = mergeEvents(shiftEvents);
      const events = subtractEvent(allTimeEvent, mergedEvent);
      return events;
    } else if (viewType === 'resourceTimeGridDay') {
      const classifiedEvents = {};
      shiftEvents.forEach(s => {
        if (classifiedEvents[s.resourceId]) {
          classifiedEvents[s.resourceId] = [...classifiedEvents[s.resourceId], s];
        } else {
          classifiedEvents[s.resourceId] = [s];
        }
      });
      let combinedEvents = [];
      Object.keys(classifiedEvents)
        .map(e => classifiedEvents[e])
        .forEach(r => {
          const allTimeEvent = [generateRangeEvent(range, r[0].resourceId)];
          const mergedEvent = mergeEvents(r);
          const events = subtractEvent(allTimeEvent, mergedEvent);
          combinedEvents = [...combinedEvents, ...events];
        });
      return combinedEvents;
    }
    return shiftEvents;
  }
  return [];
}

function mergeEvents(shiftEvents) {
  const sortedEvents = shiftEvents.sort((a, b) => moment(a.start).unix() - moment(b.start).unix());

  let mergedEvent = [];
  sortedEvents.forEach(event => {
    if (!mergedEvent.length) {
      mergedEvent = [event];
    } else {
      const overlap = isOverlap(event, mergedEvent[mergedEvent.length - 1]);
      if (!overlap) {
        mergedEvent = [...mergedEvent, event];
      } else if (overlap) {
        mergedEvent = [
          ...mergedEvent.slice(0, mergedEvent.length - 1),
          mergeTwoEvent(event, mergedEvent[mergedEvent.length - 1]),
        ];
      }
    }
  });
  return mergedEvent;
}

export function generateRangeEvent(range, resourceId) {
  return {
    start: moment(range.start).format('YYYY-MM-DD HH:mm'),
    end: moment(range.end).format('YYYY-MM-DD HH:mm'),
    rendering: 'background',
    backgroundColor: 'rgba(143,155,179,0.24)',
    resourceId,
  };
}

function isOverlap(a, b) {
  if (
    (moment(a.start).isBefore(moment(b.end)) || moment(a.start).isSame(moment(b.end))) &&
    (moment(a.end).isAfter(moment(b.start)) || moment(a.end).isSame(moment(b.start)))
  ) {
    return true;
  }
  return false;
}

function mergeTwoEvent(a, b) {
  const start = moment(a.start).unix() > moment(b.start).unix() ? b.start : a.start;
  const end = moment(a.end).unix() > moment(b.end).unix() ? a.end : b.end;
  return { ...a, start, end };
}

function subtractEvent(allTimeEvent, events) {
  const sortedEvents = events.sort((a, b) => moment(a.start).unix() - moment(b.start).unix());

  let wholeDuration = [...allTimeEvent];
  sortedEvents.every(e => {
    const start = moment(e.start);
    const end = moment(e.end);

    const topEvent = wholeDuration[wholeDuration.length - 1];
    if (start.isBefore(moment(topEvent.start)) && end.isBefore(moment(topEvent.end))) {
      wholeDuration = [
        ...wholeDuration.slice(0, wholeDuration.length - 1),
        { ...wholeDuration[wholeDuration.length - 1], start: end.format('YYYY-MM-DD HH:mm') },
      ];
      return true;
    } else if (start.isBefore(moment(topEvent.start)) && end.isAfter(moment(topEvent.end))) {
      wholeDuration = [];
      return false;
    } else if (start.isAfter(moment(topEvent.start)) && end.isBefore(moment(topEvent.end))) {
      wholeDuration = [
        ...wholeDuration.slice(0, wholeDuration.length - 1),
        { ...wholeDuration[wholeDuration.length - 1], end: start.format('YYYY-MM-DD HH:mm') },
        { ...wholeDuration[wholeDuration.length - 1], start: end.format('YYYY-MM-DD HH:mm') },
      ];
      return true;
    }
    return true;
  });

  return wholeDuration;
}
