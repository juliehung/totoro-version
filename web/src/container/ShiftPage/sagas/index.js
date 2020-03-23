import { fork } from 'redux-saga/effects';
import { watchGetShift } from './getShift';

export default function* shiftPage() {
  yield fork(watchGetShift);
}
