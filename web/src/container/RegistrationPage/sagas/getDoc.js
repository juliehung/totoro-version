import { ON_SELECT_PATIENT } from '../constant';
import { call, take, put } from 'redux-saga/effects';
import DocNps from '../../../models/docNps';
import { getDocSuccess } from '../actions';

export function* getDoc() {
  while (true) {
    try {
      const a = yield take(ON_SELECT_PATIENT);
      const pid = a.patient.id;
      const doc = yield call(DocNps.getByPid, pid);
      yield put(getDocSuccess(doc));
    } catch (error) {
      // ignore
      console.log(error);
    }
  }
}
