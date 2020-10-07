import { call, put, take, select } from 'redux-saga/effects';
import { UPDATE_CLINIC_NOTE } from '../constant';
import Patient from '../../../models/patient';
import { updateClinicNoteSuccess } from '../actions';

const patientSelector = state => state.patientPageReducer.patient.patient;
const clinicNoteSelector = state => state.patientPageReducer.patient.editedClinicNote;

export function* updateClinicNote() {
  while (true) {
    try {
      yield take(UPDATE_CLINIC_NOTE);
      const patient = yield select(patientSelector);
      const clinicNote = yield select(clinicNoteSelector);
      const { id } = patient;
      yield call(Patient.put, { id, clinicNote });
      yield put(updateClinicNoteSuccess());
    } catch (error) {
      console.log(error);
    }
  }
}
