import { PRE_CHANGE_SMOKING } from '../constant';
import { takeLatest, put, delay } from 'redux-saga/effects';
import { nextPage, changeSmoking as cs, gotoPage } from '../actions';

function* changeSmoking(a) {
  yield put(cs(a.smoking));
  yield delay(300);
  if (a.smoking === 'A') {
    yield put(gotoPage(23));
  } else if (a.smoking === 'B') {
    yield put(nextPage());
  }
}

export function* watchChangeSmokingQ() {
  yield takeLatest(PRE_CHANGE_SMOKING, changeSmoking);
}
