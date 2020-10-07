import { take, call, put } from 'redux-saga/effects';
import { GET_NHI_PROCEDURE } from '../constant';
import { getNhiProcedureSuccess } from '../actions';
import NhiProcedure from '../../../models/nhiProcedure';

export function* getNhiProcedure() {
  while (true) {
    try {
      yield take(GET_NHI_PROCEDURE);
      const nhiProcedure = yield call(NhiProcedure.get);
      yield put(getNhiProcedureSuccess(nhiProcedure));
    } catch (error) {
      // ignore
      console.log(error);
    }
  }
}
