import { GET_OD_INDEXES } from '../constant';
import { call, put, take } from 'redux-saga/effects';
import OdIndexes from '../../../models/odIndexes';
import { getOdIndexesFail, getOdIndexesSuccess } from '../actions';

export default function* getOdIndexes() {
  while (true) {
    try {
      const nhiPage = yield take(GET_OD_INDEXES);
      const odIndexes = yield call(OdIndexes.get, nhiPage.begin, nhiPage.end);
      yield put(getOdIndexesSuccess(odIndexes));
    } catch (error) {
      yield put(getOdIndexesFail([]));
    }
  }
}
