import moment from 'moment';

export default function toRocString(dateString) {
  const momentObj = moment(dateString);
  const year = momentObj.year() - 1911;
  const yearString = ('0' + year).slice(-3);
  const date = momentObj.format('/MM/DD');
  return `${yearString}${date}`;
}
