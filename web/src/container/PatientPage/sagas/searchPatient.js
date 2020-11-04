import { call, take, put, all } from 'redux-saga/effects';
import { SEARCH_PATIENTS_START } from '../constant';
import Patient from '../../../models/patient';
import { searchPatientSuccess } from '../actions';

export function* searchPatient() {
  while (true) {
    try {
      const action = yield take(SEARCH_PATIENTS_START);
      const { text } = action;

      const patients = yield all([
        call(Patient.searchByName, text),
        call(Patient.searchByBirth, text),
        call(Patient.searchByPhone, text),
        call(Patient.searchByMedicalId, text),
        call(Patient.searchByNationalId, text),
      ]);

      yield put(searchPatientSuccess(patients, text));
    } catch (error) {
      // ignore
    }
  }
}
