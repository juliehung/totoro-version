import { fork } from 'redux-saga/effects';
import { watchGetShift } from './getShift';
import { createShift } from './createShift';
import { deleteShift } from './deleteShift';
import { getDefaultShift } from './getDefaultShift';
import { editShift } from './editShift';
import { createDefaultShift } from './createDefaultShift';
import { deleteDefaultShift } from './deleteDefaultShift';
import { watchDropShift } from './watchDropShift';
import { changeResourceColor } from './changeResourceColor';
import { getResourceColor } from './getResourceColor';
import { copyShift } from './copyShift';

export default function* shiftPage() {
  yield fork(watchGetShift);
  yield fork(createShift);
  yield fork(getDefaultShift);
  yield fork(editShift);
  yield fork(createDefaultShift);
  yield fork(deleteDefaultShift);
  yield fork(watchDropShift);
  yield fork(changeResourceColor);
  yield fork(getResourceColor);
  yield fork(deleteShift);
  yield fork(copyShift);
}
