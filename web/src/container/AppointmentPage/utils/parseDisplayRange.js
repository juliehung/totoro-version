export function parseDisplayRange({ start, end }) {
  const separator = '-';
  if (start && end) {
    const startYear = start.year() - 1911;
    const startMonth = start.month() + 1;
    const startDate = start.date();

    end = end.clone().add(-1, 'd');
    const endYear = end.year() - 1911;
    const endMonth = end.month() + 1;
    const endDate = end.date();

    const startStr = startYear + separator + startMonth + separator + startDate;
    const endStr = endYear + separator + endMonth + separator + endDate;

    return startStr === endStr ? startStr : `${startStr} ~ ${endStr}`;
  }
  return '';
}
