import { take, call, put } from 'redux-saga/effects';
import { GET_DOC_START } from '../constant';
import { getDocSuccess } from '../actions';
import DocNps from '../../../models/docNps';
import ESign from '../../../models/eSign';

export function* getDocById() {
  while (true) {
    try {
      const data = yield take(GET_DOC_START);
      const doc = yield call(DocNps.getById, data.id);
      let esign;
      if (doc.esignId) {
        esign = yield call(ESign.getById, doc.esignId);
      }

      yield put(getDocSuccess({ ...doc, esign }));
    } catch (error) {
      // ignore
      console.log(error);
    }
  }
}
