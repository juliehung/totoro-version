import { fork } from 'redux-saga/effects';
import { initPatientDetail } from './initPatientDetail';
import { updateClinicNote } from './updateClinicNote';
import { searchPatient } from './searchPatient';

export default function* homePage() {
  yield fork(initPatientDetail);
  yield fork(updateClinicNote);
  yield fork(searchPatient);
}
