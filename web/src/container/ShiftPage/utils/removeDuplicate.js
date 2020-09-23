export function removeDuplicate(events) {
  events = events.filter(
    (event, index, self) =>
      index ===
      self.findIndex(e => e.fromDate === event.fromDate && e.endDate === event.endDate && e.userId === event.userId),
  );

  return events;
}
