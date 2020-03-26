import { fork } from 'redux-saga/effects';
import { watchGetShift } from './getShift';
import { createShift } from './createShift';
import { editShift } from './editShift';

export default function* shiftPage() {
  yield fork(watchGetShift);
  yield fork(createShift);
  yield fork(editShift);
}
