import moment from 'moment';

export default function parseDateToString(date, isRoc) {
  if (date) {
    const momentBirth = moment(date);
    const isvalid = momentBirth.isValid();
    if (isvalid) {
      let year = momentBirth.year();
      if (isRoc) {
        year = year - 1911;
      }
      return `${year}-${momentBirth.format('MM-DD')}`;
    }
    return date;
  }
  return '';
}
