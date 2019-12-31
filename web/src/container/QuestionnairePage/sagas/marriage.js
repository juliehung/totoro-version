import { PRE_CHANGE_MARRIAGE } from '../constant';
import { takeLatest, put, delay } from 'redux-saga/effects';
import { nextPage, changeMarriage as cm } from '../actions';

function* changeMarriage(a) {
  yield put(cm(a.marriage));
  yield delay(300);
  yield put(nextPage());
}

export function* watchChangeMarriage() {
  yield takeLatest(PRE_CHANGE_MARRIAGE, changeMarriage);
}
