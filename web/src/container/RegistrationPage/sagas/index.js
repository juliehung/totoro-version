import { fork } from 'redux-saga/effects';
import { watchGetRegistrations } from './getRegistrations';

export default function* registrationPage() {
    yield fork(watchGetRegistrations);
}
