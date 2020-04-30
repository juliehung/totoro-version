import { fork } from 'redux-saga/effects';
import { getEvents, saveEvent, executeEvent, deleteEvent, saveEventAndSendImmediately, getClinicRemaining } from './event';
import { getAppointments } from './appointment';
import { getSettings } from './settings';
import { getUsers } from './user';

export default function* smsPage() {
  yield fork(getEvents);
  yield fork(getAppointments);
  yield fork(getSettings);
  yield fork(saveEvent);
  yield fork(executeEvent);
  yield fork(deleteEvent);
  yield fork(saveEventAndSendImmediately);
  yield fork(getClinicRemaining);
  yield fork(getUsers);
}
