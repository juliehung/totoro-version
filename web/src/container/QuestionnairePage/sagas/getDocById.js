import { take, call, put } from 'redux-saga/effects';
import { GET_DOC_START } from '../constant';
import { getDocSuccess } from '../actions';
import DocNps from '../../../models/docNps';

export function* getDocById() {
  while (true) {
    try {
      const data = yield take(GET_DOC_START);
      const doc = yield call(DocNps.getById, data.id);
      yield put(getDocSuccess(doc));
    } catch (error) {
      // ignore
      console.log(error);
    }
  }
}
