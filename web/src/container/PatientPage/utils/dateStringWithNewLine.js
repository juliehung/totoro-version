import moment from 'moment';

export default function dateStringWithNewLine() {
  const now = moment();
  const year = now.year() - 1911;
  const dateString = `${year}${now.format('MMDD')}`;
  return dateString + '\r\n'.repeat(2);
}
