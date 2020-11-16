import moment from 'moment';

export default function parseDateToString(date, separator = '/') {
  if (date) {
    const momentBirth = moment(date);
    const isvalid = momentBirth.isValid();
    if (isvalid) {
      const year = momentBirth.year() - 1911;
      const yearString = ('0' + year).slice(-3);
      if (separator) {
        return `${yearString}${momentBirth.format(`/MM/DD`)}`;
      } else {
        return `${yearString}${momentBirth.format(`MMDD`)}`;
      }
    }
    return date;
  }
  return '生日未填';
}
