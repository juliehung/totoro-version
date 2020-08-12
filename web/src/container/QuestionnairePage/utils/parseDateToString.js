import moment from 'moment';

export function parseDateToString(date, isRoc) {
  if (date) {
    const momentDate = moment(date);
    let year = momentDate.year();
    if (isRoc) {
      year = year - 1911;
    }
    return year + momentDate.format('-MM-DD');
  }
  return '';
}
