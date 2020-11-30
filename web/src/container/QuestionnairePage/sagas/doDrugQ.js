import { PRE_CHANGE_DO_DRUG } from '../constant';
import { takeLatest, put, delay } from 'redux-saga/effects';
import { nextPage, changeDoDrug as cdd, gotoPage } from '../actions';

function* changeDoDrug(a) {
  yield put(cdd(a.doDrug));
  yield delay(300);
  if (a.doDrug === 'A') {
    yield put(gotoPage(23));
  } else if (a.doDrug === 'B') {
    yield put(nextPage());
  }
}

export function* watchChangeDoDrugQ() {
  yield takeLatest(PRE_CHANGE_DO_DRUG, changeDoDrug);
}
