export function parseDisplayRange({ start, end }, isRoc) {
  const separator = '-';
  if (start && end) {
    let startYear = start.year();
    const startMonth = start.month() + 1;
    const startDate = start.date();

    end = end.clone().add(-1, 'd');
    let endYear = end.year();
    const endMonth = end.month() + 1;
    const endDate = end.date();

    if (isRoc) {
      startYear = startYear - 1911;
      endYear = endYear - 1911;
    }

    const startStr = startYear + separator + startMonth + separator + startDate;
    const endStr = endYear + separator + endMonth + separator + endDate;

    return startStr === endStr ? startStr : `${startStr} ~ ${endStr}`;
  }
  return '';
}
