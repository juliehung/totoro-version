import { PRE_CHANGE_CAREER } from '../constant';
import { takeLatest, put, delay } from 'redux-saga/effects';
import { nextPage, changeCareer as cc } from '../actions';

function* changeCareer(a) {
  yield put(cc(a.career));
  yield delay(300);
  yield put(nextPage());
}

export function* watchChangeCareer() {
  yield takeLatest(PRE_CHANGE_CAREER, changeCareer);
}
