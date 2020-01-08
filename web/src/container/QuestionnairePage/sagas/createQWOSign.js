import { CREATE_Q_WITHOUT_SIGN } from '../constant';
import { call, take, select, put, delay } from 'redux-saga/effects';
import Patient from '../../../models/patient';
import DocNps from '../../../models/docNps';
import { changeCreateQSuccess } from '../actions';
import { handlePatientForApi } from '../utils/handlePatientForApi';

export const getPatientEntity = state => state.questionnairePageReducer.data.patientEntity;

export const getPatient = state => state.questionnairePageReducer.data.patient;

export function* createQWOSign() {
  while (true) {
    try {
      yield take(CREATE_Q_WITHOUT_SIGN);
      const patientEntity = yield select(getPatientEntity);
      const patient = yield select(getPatient);
      const patientForApi = handlePatientForApi(patientEntity, patient);
      const responsePatient = yield call(Patient.put, patientForApi);
      yield call(DocNps.post, { patientId: responsePatient.id, patient: responsePatient });
      yield delay(500);
      yield put(changeCreateQSuccess());
    } catch (error) {
      // ignore
      console.log(error);
    }
  }
}
