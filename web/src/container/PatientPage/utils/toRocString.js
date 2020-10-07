import moment from 'moment';

export default function toRocString(dateString) {
  const momentObj = moment(dateString);
  const year = momentObj.year() - 1911;
  const date = momentObj.format('-MM-DD');
  return `${year}${date}`;
}
