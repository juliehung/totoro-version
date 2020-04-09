import moment from 'moment';
import { generateHash } from './generateHash';

export function handleShiftForApi(shift) {
  const name = shift.name;
  const time = shift.range.start + ' ' + shift.range.end;
  const now = moment().unix().toString();
  const id = generateHash(name + time + now);
  return { id, name, time };
}
