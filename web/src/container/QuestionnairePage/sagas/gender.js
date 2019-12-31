import { PRE_CHANGE_GENDER } from '../constant';
import { takeLatest, put, delay } from 'redux-saga/effects';
import { nextPage, changeGender as cg } from '../actions';

function* changeGender(a) {
  yield put(cg(a.gender));
  yield delay(300);
  yield put(nextPage());
}

export function* watchChangeGender() {
  yield takeLatest(PRE_CHANGE_GENDER, changeGender);
}
