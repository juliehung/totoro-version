import { fork } from 'redux-saga/effects';
import { watchGetShift } from './getShift';
import { createShift } from './createShift';
import { getDefaultShift } from './getDefaultShift';
import { editShift } from './editShift';
import { createDefaultShift } from './createDefaultShift';
import { watchDropShift } from './watchDropShift';

export default function* shiftPage() {
  yield fork(watchGetShift);
  yield fork(createShift);
  yield fork(getDefaultShift);
  yield fork(editShift);
  yield fork(createDefaultShift);
  yield fork(watchDropShift);
}
