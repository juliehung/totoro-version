import moment from 'moment';
import { toRocString } from './';

export default function dateStringWithNewLine() {
  const now = moment();
  const dateString = toRocString(now);
  return dateString + '\r\n'.repeat(2);
}
