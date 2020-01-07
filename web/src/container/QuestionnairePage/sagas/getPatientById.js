import { take, call, put } from 'redux-saga/effects';
import { GET_PATIENT_START } from '../constant';
import { getPatientSuccess } from '../actions';
import Patient from '../../../models/patient';

export function* getPatientById() {
  while (true) {
    try {
      const data = yield take(GET_PATIENT_START);
      const patient = yield call(Patient.getById, data.pid);
      yield put(getPatientSuccess(patient));
    } catch (error) {
      // ignore
    }
  }
}
