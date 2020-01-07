import { CREATE_Q_WITHOUT_SIGN } from '../constant';
import { call, take, select, put, delay } from 'redux-saga/effects';
import Patient from '../../../models/patient';
import DocNps from '../../../models/docNps';
import { changeCreateQSuccess } from '../actions';

export const getPatientEnity = state => state.questionnairePageReducer.data.patientEnity;

export function* createQWOSign() {
  while (true) {
    try {
      yield take(CREATE_Q_WITHOUT_SIGN);
      const patientEnity = yield select(getPatientEnity);
      yield call(Patient.put, { ...patientEnity });
      yield call(DocNps.post, { patientId: patientEnity.id, patient: patientEnity });
      yield delay(500);
      yield put(changeCreateQSuccess());
    } catch (error) {
      // ignore
    }
  }
}
