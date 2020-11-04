import { fork } from 'redux-saga/effects';
import { initPatientDetail } from './initPatientDetail';
import { updateClinicNote } from './updateClinicNote';
import { searchPatient } from './searchPatient';
import { getRegistrationToday } from './getRegistrationToday';

export default function* homePage() {
  yield fork(initPatientDetail);
  yield fork(updateClinicNote);
  yield fork(searchPatient);
  yield fork(getRegistrationToday);
}
