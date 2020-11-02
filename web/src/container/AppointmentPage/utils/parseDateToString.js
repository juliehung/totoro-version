import moment from 'moment';

export default function parseDateToString(date) {
  if (date) {
    const momentBirth = moment(date);
    const isvalid = momentBirth.isValid();
    if (isvalid) {
      const year = momentBirth.year() - 1911;
      return `${year}-${momentBirth.format('MM-DD')}`;
    }
    return date;
  }
  return '';
}
