import { fork } from 'redux-saga/effects';
import { watchGetRegistrations } from './getRegistrations';
import { getDoc } from './getDoc';
import { watchOpenXray } from './watchOpenXray';

export default function* registrationPage() {
  yield fork(watchGetRegistrations);
  yield fork(getDoc);
  yield fork(watchOpenXray);
}
