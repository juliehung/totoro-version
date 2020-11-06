import moment from 'moment';

export function parseDateToString(date) {
  if (date) {
    const momentDate = moment(date);
    const year = momentDate.year() - 1911;
    const yearString = ('0' + year).slice(-3);
    return yearString + momentDate.format('/MM/DD');
  }
  return '';
}
