import { CREATE_Q_WITH_SIGN } from '../constant';
import { takeLatest, call } from 'redux-saga/effects';
import ESign from '../../../models/eSign';

function* postESign(a) {
  try {
    // temp
    const patientId = 102;
    const data = yield call(ESign.create, { lob: a.sign, patientId });
    console.log(data);
  } catch (error) {}
}

export function* watchCreateQWSign() {
  yield takeLatest(CREATE_Q_WITH_SIGN, postESign);
}
