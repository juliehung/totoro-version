import { fork } from 'redux-saga/effects';
import { watchGetShift } from './getShift';
import { createShift } from './createShift';

export default function* shiftPage() {
  yield fork(watchGetShift);
  yield fork(createShift);
}
