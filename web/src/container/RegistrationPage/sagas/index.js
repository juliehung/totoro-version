import { fork } from 'redux-saga/effects';
import { watchGetRegistrations } from './getRegistrations';
import { getDoc } from './getDoc';

export default function* registrationPage() {
  yield fork(watchGetRegistrations);
  yield fork(getDoc);
}
