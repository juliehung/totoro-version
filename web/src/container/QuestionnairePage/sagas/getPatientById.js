import { take, call, put } from 'redux-saga/effects';
import { GET_PATIENT_START, GET_EXIST_NATIONAL_ID } from '../constant';
import { getPatientSuccess, getExistNationalIdSuccess } from '../actions';
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

export function* getExistNationalId() {
  while (true) {
    try {
      const { nationalId } = yield take(GET_EXIST_NATIONAL_ID);
      const isPatientExist = yield call(Patient.getExistNationalId, nationalId);
      yield put(getExistNationalIdSuccess(isPatientExist));
    } catch (error) {
      // ignore
    }
  }
}
