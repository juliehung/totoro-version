// it's a workaround

export function addAllDay(event) {
  return { ...event, allDay: true };
}
