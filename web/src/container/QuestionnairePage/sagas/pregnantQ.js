import { PRE_CHANGE_PREGANT } from '../constant';
import { takeLatest, put, delay } from 'redux-saga/effects';
import { nextPage, changePregnant as cp, gotoPage } from '../actions';

function* changePregnant(a) {
  yield put(cp(a.pregnant));
  yield delay(300);
  if (a.pregnant === 'A') {
    yield put(gotoPage(25));
  } else if (a.pregnant === 'B') {
    yield put(nextPage());
  }
}

export function* watchChangePregnantQ() {
  yield takeLatest(PRE_CHANGE_PREGANT, changePregnant);
}
