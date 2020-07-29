import { CREATE_Q_WITHOUT_SIGN } from '../constant';
import { call, take, select, put, delay } from 'redux-saga/effects';
import Patient from '../../../models/patient';
import DocNps from '../../../models/docNps';
import { changeCreateQSuccess, changeCreateQFailure } from '../actions';
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
      const result = yield call(DocNps.post, { patientId: responsePatient.id, patient: responsePatient });
      const { id } = result;
      yield delay(500);
      yield put(changeCreateQSuccess(id));
    } catch (error) {
      console.log(error);
      yield put(changeCreateQFailure());
    }
  }
}
