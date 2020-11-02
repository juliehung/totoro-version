import moment from 'moment';

export function parseDateToString(date) {
  if (date) {
    const momentDate = moment(date);
    const year = momentDate.year() - 1911;
    return year + momentDate.format('-MM-DD');
  }
  return '';
}
