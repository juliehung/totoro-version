import { CREATE_Q_WITH_SIGN } from '../constant';
import { takeLatest, call, select, put, delay } from 'redux-saga/effects';
import ESign from '../../../models/eSign';
import { getPatientEnity } from './createQWOSign';
import { changeCreateQSuccess } from '../actions';
import Patient from '../../../models/patient';
import DocNps from '../../../models/docNps';

function* postESign(a) {
  try {
    // temp
    const patientEnity = yield select(getPatientEnity);
    const esign = yield call(ESign.create, { lob: a.sign, patientId: patientEnity.id });
    const esignId = esign.id;
    yield call(Patient.put, { ...patientEnity });
    yield call(DocNps.post, { esignId, patientId: patientEnity.id, patient: patientEnity });
    yield delay(500);
    yield put(changeCreateQSuccess());
  } catch (error) {}
}

export function* watchCreateQWSign() {
  yield takeLatest(CREATE_Q_WITH_SIGN, postESign);
}
