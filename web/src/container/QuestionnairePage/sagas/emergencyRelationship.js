import { PRE_CHANGE_EMERGENCY_RELATIONSHIP } from '../constant';
import { takeLatest, put, delay } from 'redux-saga/effects';
import { nextPage, changeEmergencyRelationship as cer } from '../actions';

function* changeEmergencyRelationship(a) {
  yield put(cer(a.relationship));
  yield delay(300);
  yield put(nextPage());
}

export function* watchChangeEmergencyRelationship() {
  yield takeLatest(PRE_CHANGE_EMERGENCY_RELATIONSHIP, changeEmergencyRelationship);
}
