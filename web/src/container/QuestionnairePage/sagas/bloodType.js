import { PRE_CHANGE_BLOOD_TYPE } from '../constant';
import { takeLatest, put, delay } from 'redux-saga/effects';
import { nextPage, changeBloodType as cb } from '../actions';

function* changeBloodType(a) {
  yield put(cb(a.bloodType));
  yield delay(300);
  yield put(nextPage());
}

export function* watchChangeBloodType() {
  yield takeLatest(PRE_CHANGE_BLOOD_TYPE, changeBloodType);
}
