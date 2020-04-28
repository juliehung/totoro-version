export function parseDisplayRange({ start, end }) {
  if (start && end) {
    const startStr = start.clone().format('YYYY-MM-DD');
    const endStr = end.clone().add(-1, 'd').format('YYYY-MM-DD');
    return startStr === endStr ? startStr : `${startStr} ~ ${endStr}`;
  }
  return '';
}
