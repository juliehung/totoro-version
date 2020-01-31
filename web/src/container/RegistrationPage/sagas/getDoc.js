import { GET_DOC_START } from '../constant';
import { call, take, put } from 'redux-saga/effects';
import DocNps from '../../../models/docNps';
import { getDocSuccess } from '../actions';

export function* getDoc() {
  while (true) {
    try {
      const a = yield take(GET_DOC_START);
      const pid = a.pid;
      const doc = yield call(DocNps.getByPid, pid);
      yield put(getDocSuccess(doc));
    } catch (error) {
      // ignore
      console.log(error);
    }
  }
}
