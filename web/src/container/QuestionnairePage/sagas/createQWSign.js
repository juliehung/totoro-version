import { CREATE_Q_WITH_SIGN } from '../constant';
import { call, select, put, delay, take } from 'redux-saga/effects';
import ESign from '../../../models/eSign';
import { getPatientEntity, getPatient } from './createQWOSign';
import { changeCreateQSuccess } from '../actions';
import Patient from '../../../models/patient';
import DocNps from '../../../models/docNps';
import { handlePatientForApi } from '../utils/handlePatientForApi';

export function* createQWSign() {
  while (true) {
    try {
      const a = yield take(CREATE_Q_WITH_SIGN);
      const patientEntity = yield select(getPatientEntity);
      const esign = yield call(ESign.create, { ...a.sign, patientId: patientEntity.id });
      const esignId = esign.id;
      const patient = yield select(getPatient);
      const patientForApi = handlePatientForApi(patientEntity, patient);
      const responsePatient = yield call(Patient.put, patientForApi);
      yield call(DocNps.post, { esignId, patientId: responsePatient.id, patient: responsePatient });
      yield delay(500);
      yield put(changeCreateQSuccess());
    } catch (error) {}
  }
}
